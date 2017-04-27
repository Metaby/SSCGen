package tool;

import java.util.ArrayList;
import java.util.List;

import wrapper.*;

public class ComponentBuilder {
	
	static ComponentString generateComponent(Alu alu) {
		ComponentString component = new ComponentString(alu.getId());
		// Variables
		int operationsCnt = alu.getOperations().size();
		int conditionsCnt = alu.getConditions().size();
		int cmdCnt = operationsCnt + conditionsCnt;
		int cselBits = log2(cmdCnt);
		int iselABits = log2(alu.getInputsA().size());
		int iselBBits = log2(alu.getInputsB().size());
		// Generics and Port
		component.AddGeneric("g_word_size : integer := " + (alu.getWordSize() - 1));
		for (int i = 0; i < alu.getInputsA().size(); i++) {
			component.AddPort("p_input_A" + i + " : in std_logic_vector(g_word_size DOWNTO 0)");			
		}
		for (int i = 0; i < alu.getInputsB().size(); i++) {
			component.AddPort("p_input_B" + i + " : in std_logic_vector(g_word_size DOWNTO 0)");			
		}
		if (alu.getInputsA().size() > 1) {
			if (iselABits > 1) {
				component.AddPort("p_isel_A : in std_logic_vector(" + (iselABits - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_isel_A : in std_logic");			
			}
		}
		if (alu.getInputsA().size() > 1) {
			if (iselBBits > 1) {
				component.AddPort("p_isel_B : in std_logic_vector(" + (iselBBits - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_isel_B: in std_logic");			
			}
		}
		if (cselBits == 1) {		
			component.AddPort("p_csel : in std_logic");
		} else if (cselBits > 1) {
			component.AddPort("p_csel : in std_logic_vector(" + (cselBits - 1) + " DOWNTO 0)");
		}
		if (conditionsCnt > 0) {
			// p_conditionFlag = overflow falls (unisigned/signed) Add/Sub als Befehl
			component.AddPort("p_flag : out std_logic");
		}
		component.AddPort("p_output_1 : out std_logic_vector(g_word_size DOWNTO 0)");
		component.AddPort("p_output_2 : out std_logic_vector(g_word_size DOWNTO 0)");
		// Signals
		component.AddSignal("s_input_A : std_logic_vector(g_word_size DOWNTO 0");
		component.AddSignal("s_input_B : std_logic_vector(g_word_size DOWNTO 0");
		component.AddSignal("s_sgnd : std_logic");
		List<String> subComponents = getAluSubComponents(alu);
		if (subComponents.contains("ADDER")) {
			component.AddSignal("s_adder_sub : std_logic");
			component.AddSignal("s_adder_ovflw : std_logic");
			component.AddSignal("s_adder_result : std_logic_vector(g_word_size DOWNTO 0)");			
		}
		if (subComponents.contains("BITLOGIC")) {
			component.AddSignal("s_logic_cmd : std_logic_vector(1 DOWNTO 0");
			component.AddSignal("s_logic_result : std_logic_vector(g_word_size DOWNTO 0)");
		}
		if (subComponents.contains("DIVIDER")) {
			component.AddSignal("s_div_remain : std_logic_vector(g_word_size DOWNTO 0)");
			component.AddSignal("s_div_result : std_logic_vector(g_word_size DOWNTO 0)");			
		}
		if (subComponents.contains("MULTIPLIER")) {
			component.AddSignal("s_mul_result_hi : std_logic_vector(g_word_size DOWNTO 0)");
			component.AddSignal("s_mul_result_lo : std_logic_vector(g_word_size DOWNTO 0)");			
		}
		if (subComponents.contains("COMPARATOR")) {
			component.AddSignal("s_comp_cmd : std_logic_vector(2 DOWNTO 0)");
			component.AddSignal("s_comp_result : std_logic");
		}
		if (subComponents.contains("SHIFTER")) {
			component.AddSignal("s_shft_cmd : std_logic");
			component.AddSignal("s_shft_ari : std_logic");
			component.AddSignal("s_shft_rot : std_logic");
			component.AddSignal("s_shft_result : std_logic_vector(g_word_size DOWNTO 0)");
		}
		String behavior = "";
		behavior += "  -- Input A Multiplexing" + System.lineSeparator();
		behavior += generateMux("p_isel_A", "s_input_A", "p_input_A", alu.getInputsA().size());
		behavior += "  -- Input B Multiplexing" + System.lineSeparator();
		behavior += generateMux("p_isel_B", "s_input_B", "p_input_B", alu.getInputsB().size());
		behavior += "  -- Instances of Sub-Components" + System.lineSeparator();
		behavior += generateAluSubComponentsInstances(alu);
		behavior += "  -- Command Translation Table" + System.lineSeparator();
		behavior += generateAluCommandTranslationTable(alu);
		behavior += "  -- Flag Multiplexing" + System.lineSeparator();
		component.setBehavior(behavior);
		return component;
	}
	
	static private String generateAluCommandTranslationTable(Alu alu) {
		int operationsCnt = alu.getOperations().size();
		int conditionsCnt = alu.getConditions().size();
		int cmdCnt = operationsCnt + conditionsCnt;
		if (cmdCnt > 1) {
			String cmdTable = "  --" + System.lineSeparator() + "  -- Command\tBitvector" + System.lineSeparator();
			int cselBits = log2(cmdCnt);
			String cmd[] = new String[operationsCnt + conditionsCnt];
			String cmdBits[] = new String[operationsCnt + conditionsCnt];
			for (int i = 0; i < operationsCnt; i++) {
				cmd[i] = alu.getOperations().get(i);
				cmdBits[i] = getBinaryString(i, cselBits);
				cmdTable += "  -- " + cmd[i] + " \t" + cmdBits[i] + System.lineSeparator();
			}
			for (int i = 0; i < conditionsCnt; i++) {
				cmd[i + operationsCnt] = alu.getConditions().get(i);
				cmdBits[i + operationsCnt] = getBinaryString(i + operationsCnt, cselBits);
				cmdTable += "  -- " + cmd[i + operationsCnt] + " \t" + cmdBits[i + operationsCnt] + System.lineSeparator();
			}
			cmdTable += "  WITH p_csel SELECT p_output_1 <=" + System.lineSeparator();
			for (int i = 0; i < cmd.length; i++) {
				cmdTable += "    " + getAluOutputSignal(cmd[i]) + " WHEN " + cmdBits[i] + "," + System.lineSeparator();				
			}
			return cmdTable + System.lineSeparator();
		} else {
			return "" + System.lineSeparator();
		}
	}
	
	static private String getAluOutputSignal(String cmd) {
		if (cmd.matches("ADD|ADD_U|SUB|SUB_U")) {
			return "s_adder_result";
		}
		if (cmd.matches("AND|OR|XOR|NOT")) {
			return "s_logic_resul";
		}
		if (cmd.matches("DIV|DIV_U")) {
			return "s_div_result";
		}
		if (cmd.matches("MUL|MUL_U")) {
			return "s_mul_result";
		}
		if (cmd.matches("RR|RL|SRL|SLL|SRA")) {
			return "s_shft_result";
		}
		return "";
	}
	
	static private String generateAluSubComponentsInstances(Alu alu) {
		String instances = "";
		List<String> subComponents = getAluSubComponents(alu);
		if (subComponents.contains("ADDER")) {
			int blocks = (alu.getWordSize() / 8) - 1;
			instances += "  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => " + blocks + ") PORT MAP (s_sgnd, s_adder_sub, s_inputAInput, s_inputBinput, s_adder_ovflw, s_adder_result);" + System.lineSeparator();
		}
		if (subComponents.contains("BITLOGIC")) {
			instances += "  logic : bit_manipulator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_logic_cmd, s_logic_result);" + System.lineSeparator();
		}
		if (subComponents.contains("DIVIDER")) {
			instances += "  div : divider GENERIC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, s_div_remain, s_div_result);" + System.lineSeparator();
		}
		if (subComponents.contains("MULTIPLIER")) {
			instances += "  mul : four_quadrant_multiplier GENEIRC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, 0, s_mul_result_lo, s_mul_result_hi);" + System.lineSeparator();
		}
		if (subComponents.contains("COMPARATOR")) {
			instances += "  comp : word_comparator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_comp_cmd, s_sgnd, s_comp_result);" + System.lineSeparator();
		}
		if (subComponents.contains("SHIFTER")) {
			instances += "  shft : barrel_shifter GENERIC MAP (g_size => g_word_size) PORT MAP (s_shft_cmd, s_shft_ari, s_shft_rot, s_input_A, s_input_B, s_shft_result);" + System.lineSeparator();
		}
		return instances;
	}
	
	static private List<String> getAluSubComponents(Alu alu) {
		List<String> components = new ArrayList<String>();
		for (String op : alu.getOperations()) {
			if (op.matches("ADD|ADD_U|SUB|SUB_U")) {
				if (!components.contains("ADDER")) {
					components.add("ADDER");
				}
			}
			if (op.matches("AND|OR|XOR|NOT")) {
				if (!components.contains("BITLOGIC")) {
					components.add("BITLOGIC");
				}
			}
			if (op.matches("DIV|DIV_U")) {
				if (!components.contains("DIVIDER")) {
					components.add("DIVIDER");
				}
			}
			if (op.matches("MUL|MUL_U")) {
				if (!components.contains("MULTIPLIER")) {
					components.add("");
				}
			}
			if (op.matches("RR|RL|SRL|SLL|SRA")) {
				if (!components.contains("SHIFTER")) {
					components.add("SHIFTER");
				}
			}
		}
		for (String cond : alu.getConditions()) {
			if (cond.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
				if (!components.contains("COMPARATOR")) {
					components.add("COMPARATOR");
				}
			}
		}
		return components;
	}
	
	private static String generateMux(String adr, String outp, String inputName, int inputs) {
		String behavior = "";
		if (inputs == 2) {
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			behavior += "    " + inputName + "0 WHEN \'0\'," + System.lineSeparator();
			behavior += "    " + inputName + "1 WHEN \'1\';" + System.lineSeparator();
		} else if (inputs > 2) {	
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			int adrSize = (int)Math.ceil(Math.log(inputs) / Math.log(2));
			for (int i = 0; i < inputs; i++) {
				behavior += "    " + inputName + i + " WHEN \"" + getBinaryString(i, adrSize) + "\"";
				if (i < inputs - 1) {
					behavior += "," + System.lineSeparator();
				} else {
					behavior += ";" + System.lineSeparator();					
				}
			}			
		} else {
			behavior += "  " + outp + " <= " + inputName + "0;" + System.lineSeparator();
		}
		return behavior;
	}
	
	private static int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
	
	private static String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
}
