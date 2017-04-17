package wrapper;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ComponentBuilder;

public class Alu {

	private List<Connector> inputsA;
	private List<Connector> inputsB;
	private List<String> operations;
	private List<String> conditions;
	private Connector output;
	private Connector status;
	private String id;
	private int wordSize;
	
	public Alu(jaxb.Alu alu) {
		id = alu.getId();
		wordSize = alu.getWordSize();
		operations = new ArrayList<String>();
		for (int i = 0; i < alu.getOperations().getOperation().size(); i++) {
			operations.add(alu.getOperations().getOperation().get(i).name());
		}
		int conditionsCnt = 0;
		conditions = new ArrayList<String>();
		if (alu.getConditions() != null) {
			for (int i = 0; i < alu.getConditions().getCondition().size(); i++) {
				conditions.add(alu.getConditions().getCondition().get(i).name());
			}			
		}
		Connector outCon = new Connector();
		outCon.origin = alu.getId();
		outCon.pin = alu.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		if (alu.getStatusFlags() != null) {
			Connector flagsCon = new Connector();
			flagsCon.origin = alu.getId();
			flagsCon.pin = alu.getStatusFlags();
			flagsCon.size = conditionsCnt;
			flagsCon.id = Connector.getNewId();
			status = flagsCon;			
		}
		inputsA = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandA().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = alu.getInputsOperandA().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			inputsA.add(inCon);
		}
		inputsB = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandB().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = alu.getInputsOperandB().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			inputsB.add(inCon);
		}
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_op1", (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_op2", (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_csel", (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2))));
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}

	public void generateComponent(String targetFile) {
		ComponentBuilder component = new ComponentBuilder(id);
		component.AddGeneric("g_wordSize : integer := " + (wordSize - 1));
		for (int i = 0; i < inputsA.size(); i++) {
			component.AddPort("p_inputA" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		for (int i = 0; i < inputsB.size(); i++) {
			component.AddPort("p_inputB" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		int adrSizeA = (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2));
		if (inputsA.size() > 1) {
			if (adrSizeA > 1) {
				component.AddPort("p_inputASelect : in std_logic_vector(" + (adrSizeA - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_inputASelect : in std_logic");			
			}			
		}
		int adrSizeB = (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2));
		if (inputsB.size() > 1) {
			if (adrSizeB > 1) {
				component.AddPort("p_inputBSelect : in std_logic_vector(" + (adrSizeB - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_inputBSelect : in std_logic");			
			}			
		}
		int cmdBits = (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2));
		if (cmdBits == 1) {		
			component.AddPort("p_operation : in std_logic");
		} else if (cmdBits > 1) {
			component.AddPort("p_operation : in std_logic_vector(" + (cmdBits - 1) + " DOWNTO 0)");
		}
		if (status != null) {
			int statusSize = conditions.size() + 1; // + 1 over/underflow
			component.AddPort("p_status : out std_logic_vector(" + (statusSize - 1) + " DOWNTO 0)");
		}
		component.AddPort("p_output : out std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddSignal("s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0");
		component.AddSignal("s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0");
		component.AddSignal("s_sgnd : std_logic");
		Boolean[] neededComponents = getNeededComponents();
		if (neededComponents[ADDER]) {
			component.AddSignal("s_adder_sub : std_logic");
			component.AddSignal("s_adder_ovflw : std_logic");
			component.AddSignal("s_adder_result : std_logic_vector(g_wordSize DOWNTO 0");
		}
		if (neededComponents[BITLOGIC]) {
			component.AddSignal("s_logic_cmd : std_logic_vector(1 DOWNTO 0");
			component.AddSignal("s_logic_result : std_logic_vector(g_wordSize DOWNTO 0");
		}
		if (neededComponents[DIVIDER]) {
			component.AddSignal("s_div_remain : std_logic_vector(g_wordSize DOWNTO 0)");
			component.AddSignal("s_div_result : std_logic_vector(g_wordSize DOWNTO 0)");
		}
		if (neededComponents[MULTIPLIER]) {
			component.AddSignal("s_mul_result_hi : std_logic_vector(g_wordSize DOWNTO 0)");
			component.AddSignal("s_mul_result_lo : std_logic_vector(g_wordSize DOWNTO 0)");
		}
		if (neededComponents[COMPARATOR]) {
			component.AddSignal("s_comp_g : std_logic");
			component.AddSignal("s_comp_l : std_logic");
		}
		if (neededComponents[SHIFTER]) {
			component.AddSignal("s_shft_cmd : std_logic");
			component.AddSignal("s_shft_ari : std_logic");
			component.AddSignal("s_shft_rot : std_logic");
			component.AddSignal("s_shft_result : std_logic_vector(g_wordSize DOWNTO 0");
		}
		String muxes = "";
		muxes += ComponentBuilder.generateMux("p_inputASelect", "s_inputAInput", "p_inputA", inputsA.size());
		muxes += ComponentBuilder.generateMux("p_inputBSelect", "s_inputBInput", "p_inputB", inputsB.size());
		muxes += "  -- Behavior" + System.lineSeparator();
		List<String> imports = prepareFilesAndImports();
		for (int i = 0; i < imports.size(); i++) {
			component.AddImport(imports.get(i));
		}
		component.setBehavior(muxes + generateBehavior());
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public Boolean[] getNeededComponents() {
		Boolean neededComponents[] = new Boolean[] { false, false, false, false, false, false };
		for (String op : operations) {
			if (op.matches("ADD|ADD_U|SUB|SUB_U")) {
				neededComponents[0] = true;
			}
			if (op.matches("AND|OR|XOR|NOT")) {
				neededComponents[1] = true;
			}
			if (op.matches("DIV|DIV_U")) {
				neededComponents[3] = true;
			}
			if (op.matches("MUL|MUL_U")) {
				neededComponents[4] = true;
			}
			if (op.matches("RR|RL|SRL|SLL|SRA")) {
				neededComponents[5] = true;
			}
		}
		for (String cond : conditions) {
			if (cond.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
				neededComponents[2] = true;
			}
		}
		return neededComponents;
	}
	
	public String generateBehavior() {
		String instances = "";
		String behavior = "";
		Boolean[] neededComponents = getNeededComponents();
		int flagCount = 0;
		instances += "  -- Instances of ALU-Components" + System.lineSeparator();
		if (neededComponents[ADDER]) {
			int blocks = (wordSize / 8) - 1;
			instances += "  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => " + blocks + ") PORT MAP (s_sgnd, s_adder_sub, s_inputAInput, s_inputBinput, s_adder_ovflw, s_adder_result);" + System.lineSeparator();
			behavior += "  -- Adder" + System.lineSeparator();
			behavior += "  p_status(" + (flagCount++) + ") <= s_adder_ovflw;" + System.lineSeparator();
		}
		if (neededComponents[BITLOGIC]) {
			instances += "  logic : bit_manipulator GENERIC MAP (g_size => g_wordSize) PORT MAP (s_inputAInput, s_inputBInput, s_logic_cmd, s_logic_result);" + System.lineSeparator();
		}
		if (neededComponents[DIVIDER]) {
			instances += "  div : divider GENERIC MAP (g_size => g_wordSize) PORT MAP (s_sgnd, s_inputAInput, s_inputBInput, s_div_remain, s_div_result);" + System.lineSeparator();
		}
		if (neededComponents[MULTIPLIER]) {
			instances += "  mul : four_quadrant_multiplier GENEIRC MAP (g_size => g_wordSize) PORT MAP (s_sgnd, s_inputAInput, s_inputBInput, 0, s_mul_result_lo, s_mul_result_hi);" + System.lineSeparator();
		}
		if (neededComponents[COMPARATOR]) {
			instances += "  comp : tree_comparator GENERIC MAP (g_size => g_wordSize) PORT MAP (s_inputAInput, s_inputBInput, s_sgnd, s_comp_g, s_comp_l);" + System.lineSeparator();
			behavior += "  -- Comparator" + System.lineSeparator();
			//	G	L	OP
			//	1	0	>
			//	0	1	<
			//	X	0	>=
			//	0	X	<=
			//	0	0	=
			for (String str : conditions) {
				if (str.equals("ZERO")) {
					behavior += "  p_status(" + (flagCount++) + ") <= AND \"";
					int cnt = wordSize;
					while (cnt-- > 0) behavior += "0";
					behavior += "\"; -- A == 0" + System.lineSeparator();
				} else if (str.equals("GT")) {
					behavior += "  p_status(" + (flagCount++) + ") <= s_comp_g AND NOT s_comp_l; -- A > B" + System.lineSeparator();
				} else if (str.equals("LT")) {
					behavior += "  p_status(" + (flagCount++) + ") <= NOT s_comp_g AND s_comp_l; -- A < B" + System.lineSeparator();
				} else if (str.equals("GEQ")) {
					behavior += "  p_status(" + (flagCount++) + ") <= NOT s_comp_l; -- A >= B" + System.lineSeparator();
				} else if (str.equals("LEQ")) {
					behavior += "  p_status(" + (flagCount++) + ") <= NOT s_comp_g; -- A <= B" + System.lineSeparator();
				} else if (str.equals("EQ")) {
					behavior += "  p_status(" + (flagCount++) + ") <= NOT s_comp_g AND NOT s_comp_l; -- A == B" + System.lineSeparator();
				}
			}
		}
		if (neededComponents[SHIFTER]) {
			instances += "  shft : barrel_shifter GENERIC MAP (g_size => g_wordSize) PORT MAP (s_shft_cmd, s_shft_ari, s_shft_rot, s_inputAInput, s_inputBInput, s_shft_result);" + System.lineSeparator();
		}
		return instances + behavior;
	}
	
	public List<String> prepareFilesAndImports() {
		List<String> imports = new ArrayList<String>();
		try {
			Boolean[] neededComponents = getNeededComponents();
			if (neededComponents[ADDER]) {
				copy(new File("processors/components/alu/adder"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT carry_select_adder" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_block_size : integer := 7;" + System.lineSeparator();
				imprt += "      g_blocks     : integer := 3;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd   : in  std_logic;" + System.lineSeparator();
				imprt += "      p_sub    : in  std_logic;" + System.lineSeparator();
				imprt += "      p_op_1   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result : out std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_ovflw  : out std_logic" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (neededComponents[BITLOGIC]) {
				copy(new File("processors/components/alu/bitwise_logic"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT bit_manipulator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_cmd    : in  std_logic_vector(1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (neededComponents[COMPARATOR]) {
				copy(new File("processors/components/alu/comparator"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT tree_comparator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_op_1 : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2 : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_sgnd : in  std_logic;" + System.lineSeparator();				
				imprt += "      p_g    : out std_logic;" + System.lineSeparator();				
				imprt += "      p_l    : out std_logic" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (neededComponents[DIVIDER]) {
				copy(new File("processors/components/alu/divider"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT divider " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd     : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_dividend : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_divisor  : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_remain   : out std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_result   : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (neededComponents[MULTIPLIER]) {
				copy(new File("processors/components/alu/multiplier"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT four_quadrant_multiplier " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd      : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_op_1      : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2      : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_add       : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result_lo : out std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_result_hi : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (neededComponents[SHIFTER]) {
				copy(new File("processors/components/alu/shifter"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT barrel_shifter " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_cmd    : in  std_logic;" + System.lineSeparator();
				imprt += "      p_arith  : in  std_logic;" + System.lineSeparator();
				imprt += "      p_rotate : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imports;
	}
	
	public void copy(File sourceLocation, File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}

	private void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}

	private void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}

	public List<Connector> getInputsA() {
		return inputsA;
	}

	public List<Connector> getInputsB() {
		return inputsB;
	}

	public List<String> getOperations() {
		return operations;
	}

	public List<String> getConditions() {
		return conditions;
	}

	public Connector getOutput() {
		return output;
	}

	public Connector getStatus() {
		return status;
	}

	public String getId() {
		return id;
	}

	public int getWordSize() {
		return wordSize;
	}
	
	final int ADDER = 0;
	final int BITLOGIC = 1;
	final int COMPARATOR = 2;
	final int DIVIDER = 3;
	final int MULTIPLIER = 4;
	final int SHIFTER = 5;
}