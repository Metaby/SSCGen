package de.uulm.cyv17.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.wrapper.*;
import de.uulm.cyv17.wrapper.entities.AluEntity;
import de.uulm.cyv17.wrapper.entities.MultiplexerEntity;
import de.uulm.cyv17.wrapper.entities.RegisterEntity;
import de.uulm.cyv17.wrapper.entities.RegisterFileEntity;
import de.uulm.cyv17.wrapper.entities.RomEntity;

class ComponentFactory {
	
	private String targetDirectory;
	
	ComponentFactory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}
	
	VhdlComponent generateComponent(RegisterFileEntity rf) {
		VhdlComponent component = new VhdlComponent(rf.getId());
		component.AddLibrary("USE ieee.numeric_std.all;");
		component.AddGeneric("g_address_size : integer := " + (rf.getAddressSize() - 1));
		component.AddGeneric("g_word_size : integer := " + (rf.getWordSize() - 1));
		component.AddPort("p_clk : in", 1);
		component.AddPort("p_rst : in", 1);
		String muxes = "";
		String ctrlBinding = "";
		int ctrlSize = 0;
		int tmp = 0;
		for (Port p : rf.getPorts()) {
			if (p.getDirection() == PortDirection.IN) {
				tmp += log2(p.getInputs().size());
				tmp += log2(p.getAddresses().size());
				tmp++;
			} else {
				tmp += log2(p.getAddresses().size());				
			}
		}
		for (int j = 0; j < rf.getPorts().size(); j++) {
			Port p = rf.getPorts().get(j);
			if (p.getDirection() == PortDirection.IN) {
				for (int i = 0; i < p.getInputs().size(); i++) {
					component.AddPort("p_port" + j +  "_input" + i + " : in", "g_word_size");
				}
				for (int i = 0; i < p.getAddresses().size(); i++) {
					component.AddPort("p_port" + j +  "_address" + i + " : in", "g_address_size");					
				}
				int adrBits = (int)Math.ceil(Math.log(p.getInputs().size()) / Math.log(2));
				if (p.getInputs().size() == 2) {
					ctrlBinding += "  s_port" + j + "_isel <= p_ctrl(" + ctrlSize++ + ");" + System.lineSeparator();
					component.AddSignal("s_port" + j + "_isel : std_logic");
				} else if (p.getInputs().size() > 2) {
					ctrlBinding += "  s_port" + j + "_isel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_isel : std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");				
				}
				adrBits = (int)Math.ceil(Math.log(p.getAddresses().size()) / Math.log(2));
				if (p.getAddresses().size() == 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + ctrlSize++ + ");" + System.lineSeparator();
					component.AddSignal("s_port" + j + "_asel : std_logic");
				} else if (p.getAddresses().size() > 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_asel : std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");				
				}
				component.AddSignal("s_port" + j + "_inputSelect : std_logic_vector(g_word_size DOWNTO 0)");
				muxes += VhdlComponent.generateMux("s_port" + j + "_isel", "s_port" + j + "_inputSelect", "p_port" + j +  "_input", p.getInputs().size());
				component.AddSignal("s_port" + j + "_addressSelect : std_logic_vector(g_address_size DOWNTO 0)");
				muxes += VhdlComponent.generateMux("s_port" + j + "_asel", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddSignal("s_port" + j + "_write : std_logic");
				if (tmp > 1) {	
					ctrlBinding += "  s_port" + j + "_write <= p_ctrl(" + ctrlSize++ + ");" + System.lineSeparator();			
				} else {
					ctrlBinding += "  s_port" + j + "_write <= p_ctrl;" + System.lineSeparator();	
				}
			} else {
				for (int i = 0; i < p.getAddresses().size(); i++) {
					component.AddPort("p_port" + j +  "_address" + i + " : in", "g_address_size");					
				}
				int adrBits = (int)Math.ceil(Math.log(p.getAddresses().size()) / Math.log(2));
				if (p.getAddresses().size() == 2) {
					if (tmp > 1) {
						ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + ctrlSize++ + ");" + System.lineSeparator();						
					} else {
						ctrlBinding += "  s_port" + j + "_asel <= p_ctrl;" + System.lineSeparator();
					}
					component.AddSignal("s_port" + j + "_asel : std_logic");
				} else if (p.getAddresses().size() > 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_asel : std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");				
				}
				component.AddSignal("s_port" + j + "_addressSelect : std_logic_vector(g_address_size DOWNTO 0)");
				muxes += VhdlComponent.generateMux("s_port" + j + "_addressSelect", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddPort("p_port" + j + "_output : out", "g_word_size");
			}
		}
		component.AddType("registerArray IS ARRAY(" + (int)Math.pow(2, rf.getAddressSize()) + " DOWNTO 0) OF std_logic_vector(g_word_size DOWNTO 0)");
		if (tmp == 1) {
			component.AddPort("p_ctrl : in", 1);
		} else if (tmp > 1) {
			component.AddPort("p_ctrl : in", ctrlSize);
		}
		component.AddSignal("s_registers : registerArray");
		String behavior = ctrlBinding;
		behavior += "  PROCESS (p_clk) BEGIN" + System.lineSeparator();
		behavior += "    IF rising_edge(p_clk) THEN" + System.lineSeparator();
		for (int i = 0; i < rf.getPorts().size(); i++) {
			Port p = rf.getPorts().get(i);
			if (p.getDirection() == PortDirection.IN) {
				behavior += "      IF s_port" + i + "_write = \'1\' THEN" + System.lineSeparator();
				behavior += "        s_registers(to_integer(unsigned(s_port" + i + "_addressSelect))) <= s_port" + i + "_inputSelect;" + System.lineSeparator();
				behavior += "      END IF;" + System.lineSeparator();
			} else {
				behavior += "      p_port" + i + "_output <= s_registers(to_integer(unsigned(s_port" + i + "_addressSelect)));" + System.lineSeparator();
			}
		}
		behavior += "    END IF;" + System.lineSeparator();
		behavior += "  END PROCESS;" + System.lineSeparator();
		component.setBehavior(muxes + behavior);
		String targetFile = targetDirectory + "/components/" + rf.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
		return component;
	}
	
	VhdlComponent generateComponent(MultiplexerEntity mux) {
		VhdlComponent component = new VhdlComponent(mux.getId());
		int inputsCnt = mux.getInputs().size();
		component.AddGeneric("g_word_size : integer := " + (mux.getWordSize() - 1));
		for (int i = 0; i < inputsCnt; i++) {
			component.AddPort("p_input" + i + " : in", "g_word_size");
		}
		int iselSize = log2(inputsCnt);
		if (inputsCnt > 1) {
			component.AddPort("p_isel : in", iselSize);
		}
		component.AddPort("p_word : out", "g_word_size");
		String behavior = "";
		behavior += VhdlComponent.generateMux("p_isel", "p_word", "p_input", inputsCnt);
		component.setBehavior(behavior);
		String targetFile = targetDirectory + "/components/" + mux.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
		return component;
	}
	
	VhdlComponent generateComponent(RomEntity rom) {	
		int content[] = new int[] { };
		String contentFile = rom.getContentFile();
		int addressSize = rom.getAddressSize();
		int wordSize = rom.getWordSize();
		if (contentFile.equals("blank")) {
			content = new int[(int)Math.pow(2, addressSize)];
			for (int i = 0; i < content.length; i++) {
				content[i] = 0;
			}
		} else {
			File inputFile = new File(contentFile);
			if (!inputFile.exists()) {
				System.out.println("Error: Content file does not exist. (" + contentFile + ")");
				System.exit(-1);
				return null;
			}
			try {
				List<String> strContent = Files.readAllLines(inputFile.toPath());
				if (strContent.size() > 0 && strContent.get(0).equals("v2.0 raw")) {
					String strBytes = "";
					for (int i = 1; i < strContent.size(); i++) {
						strBytes += strContent.get(i) + " ";
					}
					String strBytesArray[] = strBytes.split("\\s+");
					content = new int[strBytesArray.length];
					for (int i = 0; i < strBytesArray.length; i++) {
						content[i] = Integer.parseInt(strBytesArray[i], 16);
					}
				} else {
					System.out.println("Error: Wrong file format. (" + contentFile + ")");
					System.exit(-1);
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error: Reading content file. (" + contentFile + ")");
				System.exit(-1);
				return null;
			}		
		}
		VhdlComponent component = new VhdlComponent(rom.getId());
		component.AddGeneric("g_address_size : integer := " + (addressSize - 1));
		component.AddGeneric("g_word_size : integer := " + (wordSize - 1));
		for (int i = 0; i < rom.getAddresses().size(); i++) {
			component.AddPort("p_address" + i + " : in", "g_address_size");
		}
		int adrSize = log2(rom.getAddresses().size());
		if (rom.getAddresses().size() > 1) {
			component.AddPort("p_addressSelect : in", adrSize);
		}
		component.AddPort("p_word : out", "g_word_size");
		component.AddSignal("s_address : std_logic_vector(g_address_size DOWNTO 0)");		
		String behavior = "";
		behavior += VhdlComponent.generateMux("p_addrSelect", "s_address", "p_address", rom.getAddresses().size());
		behavior += "  -- Behavior" + System.lineSeparator();
		behavior += "  WITH s_address SELECT p_word <=" + System.lineSeparator();
		for (int i = 0; i < content.length; i++) {
			behavior += "    \"" + getBinaryString(content[i], wordSize) + "\" WHEN \""
						+ getBinaryString(i, addressSize);
			behavior += "\",";
			behavior += System.lineSeparator();
		}
//		if (content.length < Math.pow(2, addressSize)) {
			behavior += "    \"" + getBinaryString(0, wordSize) + "\" WHEN OTHERS;" + System.lineSeparator();			
//		} else {
//			char ca[] = behavior.toCharArray();
//			ca[behavior.length() - 3] = ';';
//			behavior = new String(ca);
//		}
		component.setBehavior(behavior);
		String targetFile = targetDirectory + "/components/" + rom.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}		
		return component;	
	}
	
	VhdlComponent generateComponent(RegisterEntity register) {
		VhdlComponent component = new VhdlComponent(register.getId());
		component.AddGeneric("g_word_size : integer := " + (register.getWordSize() - 1));
		component.AddPort("p_clk : in", 1);
		component.AddPort("p_rst : in", 1);
		component.AddSignal("s_write : std_logic");
		String behavior = "  -- Behavior" + System.lineSeparator();
		int adrSize = log2(register.getInputs().size());
		if (register.getInputs().size() == 1) {
			component.AddPort("p_ctrl : in", 1);
			behavior += "  s_write <= p_ctrl;" + System.lineSeparator();
		} else {
			component.AddPort("p_ctrl : in ", adrSize + 1);
			behavior += "  s_write <= p_ctrl(0);" + System.lineSeparator();
			if (register.getInputs().size() == 2) {
				behavior += "  s_isel <= p_ctrl(" + adrSize + ");" + System.lineSeparator();				
			} else {
				behavior += "  s_isel <= p_ctrl(" + adrSize + " DOWNTO 1);" + System.lineSeparator();				
			}
		}
		for (int i = 0; i < register.getInputs().size(); i++) {
			component.AddPort("p_input" + i + " : in", "g_word_size");
		}
		if (register.getInputs().size() > 1) {
			if (adrSize > 1) {
				component.AddSignal("s_isel : std_logic_vector(" + (adrSize - 1) + " DOWNTO 0)");				
			} else {
				component.AddSignal("s_isel : std_logic");			
			}			
		}
		component.AddPort("p_word : out", "g_word_size");
		component.AddSignal("s_input : std_logic_vector(g_word_size DOWNTO 0)");
		behavior += VhdlComponent.generateMux("s_isel", "s_input", "p_input", register.getInputs().size());
		behavior += "  PROCESS (p_clk) BEGIN" + System.lineSeparator();
		behavior += "    IF rising_edge(p_clk) THEN" + System.lineSeparator();
		behavior += "      IF p_rst = '1' THEN" + System.lineSeparator();
		behavior += "        p_word <= (OTHERS => '0');" + System.lineSeparator();
		behavior += "      ELSIF s_write = '1' THEN" + System.lineSeparator();
		behavior += "        p_word <= s_input;" + System.lineSeparator();
		behavior += "      END IF;" + System.lineSeparator();
		behavior += "    END IF;" + System.lineSeparator();
		behavior += "  END PROCESS;";
		component.setBehavior(behavior);
		String targetFile = targetDirectory + "/components/" + register.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}			
		return component;
	}
	
	VhdlComponent generateComponent(AluEntity alu) {
		VhdlComponent component = new VhdlComponent(alu.getId());
		// Variables
		int operationsCnt = alu.getOperations().size();
		int conditionsCnt = alu.getConditions().size();
		int cmdCnt = operationsCnt + conditionsCnt;
		int cselSize = log2(cmdCnt);
		int iselASize = log2(alu.getInputsA().size());
		int iselBSize = log2(alu.getInputsB().size());
		int ctrlSize = cselSize + iselASize + iselBSize;
		// Generics and Port
		component.AddGeneric("g_word_size : integer := " + (alu.getWordSize() - 1));
		for (int i = 0; i < alu.getInputsA().size(); i++) {
			component.AddPort("p_input_A" + i + " : in", "g_word_size");			
		}
		for (int i = 0; i < alu.getInputsB().size(); i++) {
			component.AddPort("p_input_B" + i + " : in", "g_word_size");			
		}
		if (alu.getInputsA().size() > 1) {
			if (iselASize > 1) {
				component.AddSignal("s_isel_A : std_logic_vector(" + (iselASize - 1) + " DOWNTO 0)");				
			} else {
				component.AddSignal("s_isel_A : std_logic");			
			}
		}
		if (alu.getInputsA().size() > 1) {
			if (iselBSize > 1) {
				component.AddSignal("s_isel_B : std_logic_vector(" + (iselBSize - 1) + " DOWNTO 0)");				
			} else {
				component.AddSignal("s_isel_B: std_logic");			
			}
		}
		if (cselSize == 1) {
			component.AddSignal("s_csel : std_logic");
		} else if (cselSize > 1) {
			component.AddSignal("s_csel : std_logic_vector(" + (cselSize - 1) + " DOWNTO 0)");
		}
		if (ctrlSize > 0) {
			component.AddPort("p_ctrl : in", ctrlSize);
		}
		String ctrlBinding = "  -- Control Vector Binding" + System.lineSeparator();
		if (cselSize == 1) {
			if (ctrlSize > 1) {
				ctrlBinding += "  s_csel <= p_ctrl(0);" + System.lineSeparator();				
			} else {
				ctrlBinding += "  s_csel <= p_ctrl;" + System.lineSeparator();				
			}
		} else if (cselSize > 1) {
			ctrlBinding += "  s_csel <= p_ctrl(" + (cselSize - 1) + " DOWNTO 0);" + System.lineSeparator();
		}
		if (iselASize == 1) {
			if (ctrlSize > 1) {
				ctrlBinding += "  s_iselA <= p_ctrl(" + cselSize + ");" + System.lineSeparator();			
			} else {	
				ctrlBinding += "  s_iselA <= p_ctrl;" + System.lineSeparator();
			}
		} else if (iselASize > 1) {
			ctrlBinding += "  s_iselA <= p_ctrl(" + (cselSize + iselASize - 1) + " DOWNTO " + cselSize + ");" + System.lineSeparator();
		}
		if (iselBSize == 1) {
			if (ctrlSize > 1) {
				ctrlBinding += "  s_iselB <= p_ctrl(" + (cselSize + iselASize) + ");" + System.lineSeparator();			
			} else {	
				ctrlBinding += "  s_iselB <= p_ctrl;" + System.lineSeparator();	
			}
		} else if (iselBSize > 1) {
			ctrlBinding += "  s_iselB <= p_ctrl(" + (cselSize + iselASize + iselBSize - 1) + " DOWNTO " + (cselSize + iselASize) + ");" + System.lineSeparator();
		}
		List<String> subComponents = getAluSubComponents(alu);
		if (conditionsCnt > 0 || subComponents.contains("ADDER")) {
			component.AddPort("p_flag : out", 1);
		}
		component.AddPort("p_output_1 : out", "g_word_size");
		component.AddPort("p_output_2 : out", "g_word_size");
		// Signals
		component.AddSignal("s_input_A : std_logic_vector(g_word_size DOWNTO 0)");
		component.AddSignal("s_input_B : std_logic_vector(g_word_size DOWNTO 0)");
		component.AddSignal("s_sgnd : std_logic");
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
			component.AddSignal("s_shft_ari : std_logic");
			component.AddSignal("s_shft_rot : std_logic");
			component.AddSignal("s_shft_cmd : std_logic_vector(1 DOWNTO 0");
			component.AddSignal("s_shft_ctrl : std_logic_vector(3 DOWNTO 0)");
			component.AddSignal("s_shft_result : std_logic_vector(g_word_size DOWNTO 0)");
		}
		String behavior = "";
		behavior += ctrlBinding;
		behavior += "  -- Input A Multiplexing" + System.lineSeparator();
		behavior += generateMux("s_isel_A", "s_input_A", "p_input_A", alu.getInputsA().size());
		behavior += "  -- Input B Multiplexing" + System.lineSeparator();
		behavior += generateMux("s_isel_B", "s_input_B", "p_input_B", alu.getInputsB().size());
		behavior += "  -- Instances of Sub-Components" + System.lineSeparator();
		behavior += generateAluSubComponentsInstances(alu);
		behavior += "  -- Output Multiplexers" + System.lineSeparator();
		behavior += generateAluOutputMultiplexers(alu);
		behavior += "  -- Command Tables" + System.lineSeparator();
		behavior += generateAluCommandTable(alu);
		component.setBehavior(behavior);
		for (String imprt : getAluImports(alu)) {
			component.AddImport(imprt);
		}
		String targetFile = targetDirectory + "/components/" + alu.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
		return component;
	}
	
	private String getVhdlBitString(String bits) {
		if (bits.length() == 1) {
			return "\'" + bits + "\'";
		} else {
			return "\"" + bits + "\"";
		}
	}
	
	private String generateAluCommandTable(AluEntity alu) {
		String table = "";
		List<String> subComponents = getAluSubComponents(alu);
		int operationsCnt = alu.getOperations().size();
		int conditionsCnt = alu.getConditions().size();
		int cmdCnt = operationsCnt + conditionsCnt;
		int cselBits = log2(cmdCnt);
		String cmd[] = new String[operationsCnt + conditionsCnt];
		String cmdBits[] = new String[operationsCnt + conditionsCnt];
		if (cmdCnt > 1) {
			for (int i = 0; i < operationsCnt; i++) {
				cmd[i] = alu.getOperations().get(i);
				cmdBits[i] = getBinaryString(i, cselBits);
			}
			for (int i = 0; i < conditionsCnt; i++) {
				cmd[i + operationsCnt] = alu.getConditions().get(i);
				cmdBits[i + operationsCnt] = getBinaryString(i + operationsCnt, cselBits);
			}
			String sgnTable = "  WITH s_csel SELECT s_sgnd <=" + System.lineSeparator();
			if (subComponents.contains("ADDER")) {
				table += "  -- Adder Control" + System.lineSeparator();
				table += "  WITH s_csel SELECT s_adder_sub <=" + System.lineSeparator();
				for (int i = 0; i < cmd.length; i++) {
					if (cmd[i].equals("ADD")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \'0\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("ADD_U")) {
						table += "    \'0\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SUB")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SUB_U")) {
						table += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					}
				}
				table += "    \'0\' WHEN OTHERS;" + System.lineSeparator();
			}
			if (subComponents.contains("BITLOGIC")) {
				table += "  -- Bitlogic Control" + System.lineSeparator();
				table += "  WITH s_csel SELECT s_logic_cmd <=" + System.lineSeparator();
				for (int i = 0; i < cmd.length; i++) {
					if (cmd[i].equals("AND")) {
						table += "    \"00\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("OR")) {
						table += "    \"01\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("XOR")) {
						table += "    \"10\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("NOT")) {
						table += "    \"11\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					}
				}
				table += "    \"00\" WHEN OTHERS;" + System.lineSeparator();
			}
			if (subComponents.contains("DIVIDER") || subComponents.contains("MULTIPLIER")) {
				for (int i = 0; i < cmd.length; i++) {
					if (cmd[i].equals("DIV")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("DIV_U")) {
						sgnTable += "    \'0\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("MUL")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("MUL_U")) {
						sgnTable += "    \'0\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					}
				}
			}
			if (subComponents.contains("SHIFTER")) {
				table += "  -- Shifter/Rotater Control" + System.lineSeparator();
				table += "  s_shft_cmd(0) <= s_shft_ctrl(0)" + System.lineSeparator();
				table += "  s_shft_cmd(1) <= s_shft_ctrl(1)" + System.lineSeparator();
				table += "  s_shft_ari <= s_shft_ctrl(2)" + System.lineSeparator();
				table += "  s_shft_rot <= s_shft_ctrl(3)" + System.lineSeparator();
				table += "  WITH s_csel SELECT s_shft_ctrl <=" + System.lineSeparator();
				for (int i = 0; i < cmd.length; i++) {
					if (cmd[i].equals("RR")) {
						table += "    \"1010\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("RL")) {
						table += "    \"1001\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SRL")) {
						table += "    \"0010\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SLL")) {
						table += "    \"0001\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SRA")) {
						table += "    \"0110\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("SLA")) {
						table += "    \"0101\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					}
				}
				table += "    \"0000\" WHEN OTHERS;" + System.lineSeparator();
			}
			if (subComponents.contains("COMPARATOR")) {
				table += "  -- Comparator Control" + System.lineSeparator();
				table += "  WITH s_csel SELECT s_comp_cmd <=" + System.lineSeparator();
				for (int i = 0; i < cmd.length; i++) {
					if (cmd[i].equals("GT")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \"000\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("GT_U")) {
						table += "    \"000\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("LT")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \"001\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("LT_U")) {
						table += "    \"001\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("GEQ")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \"010\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("GEQ_U")) {
						table += "    \"010\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("LEQ")) {
						sgnTable += "    \'1\' WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
						table += "    \"011\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("LEQ_U")) {
						table += "    \"011\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					} else if (cmd[i].equals("EQ")) {
						table += "    \"100\" WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();
					}
				}
				table += "    \"000\" WHEN OTHERS;" + System.lineSeparator();
			}
			sgnTable += "    \'0\' WHEN OTHERS;" + System.lineSeparator();
			return sgnTable + table;			
		} else {
			if (operationsCnt > 0) {
				String op = alu.getOperations().get(0);
				if (op.equals("ADD")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_adder_sub <= \'0\';" + System.lineSeparator();
				} else if (op.equals("ADD_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_adder_sub <= \'0\';" + System.lineSeparator();
				} else if (op.equals("SUB")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_adder_sub <= \'1\';" + System.lineSeparator();					
				} else if (op.equals("SUB_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_adder_sub <= \'1\';" + System.lineSeparator();					
				} else if (op.equals("DIV") || op.equals("MUL")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();					
				} else if (op.equals("DIV_U") || op.equals("MUL_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();					
				} else if (op.equals("RR")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_shft_rot <= \'1\';" + System.lineSeparator();
					table += "  s_shft_ari <= \'0\';" + System.lineSeparator();
					table += "  s_shft_cmd <= \"10\";" + System.lineSeparator();
				} else if (op.equals("RL")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_shft_rot <= \'1\';" + System.lineSeparator();
					table += "  s_shft_ari <= \'0\';" + System.lineSeparator();
					table += "  s_shft_cmd <= \"01\";" + System.lineSeparator();
				} else if (op.equals("SRL")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_shft_rot <= \'0\';" + System.lineSeparator();
					table += "  s_shft_ari <= \'0\';" + System.lineSeparator();
					table += "  s_shft_cmd <= \"10\";" + System.lineSeparator();
				} else if (op.equals("SLL")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_shft_rot <= \'0\';" + System.lineSeparator();
					table += "  s_shft_ari <= \'0\';" + System.lineSeparator();
					table += "  s_shft_cmd <= \"01\";" + System.lineSeparator();
				} else if (op.equals("SRA")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_shft_rot <= \'0\';" + System.lineSeparator();
					table += "  s_shft_ari <= \'1\';" + System.lineSeparator();
					table += "  s_shft_cmd <= \"10\";" + System.lineSeparator();					
				} else if (op.equals("AND")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_logic_cmd <= \"00\";" + System.lineSeparator();
				} else if (op.equals("OR")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_logic_cmd <= \"01\";" + System.lineSeparator();					
				} else if (op.equals("XOR")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_logic_cmd <= \"10\";" + System.lineSeparator();					
				} else if (op.equals("NOT")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_logic_cmd <= \"11\";" + System.lineSeparator();					
				}
			} else if (conditionsCnt > 0) {
				String op = alu.getConditions().get(0);
				if (op.equals("GT")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"000\"" + System.lineSeparator();
				} else if (op.equals("GT_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"000\"" + System.lineSeparator();
				} else if (op.equals("LT")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"001\"" + System.lineSeparator();					
				} else if (op.equals("LT_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"001\"" + System.lineSeparator();					
				} else if (op.equals("GEQ")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"010\"" + System.lineSeparator();					
				} else if (op.equals("GEQ_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"010\"" + System.lineSeparator();					
				} else if (op.equals("LEQ")) {
					table += "  s_sgnd <= \'1\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"011\"" + System.lineSeparator();					
				} else if (op.equals("LEQ_U")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"011\"" + System.lineSeparator();					
				} else if (op.equals("EQ")) {
					table += "  s_sgnd <= \'0\';" + System.lineSeparator();
					table += "  s_comp_cmd <= \"100\"" + System.lineSeparator();					
				}
			}
			return table;
		}
	}
	
	private String generateAluOutputMultiplexers(AluEntity alu) {
		int operationsCnt = alu.getOperations().size();
		int conditionsCnt = alu.getConditions().size();
		int cmdCnt = operationsCnt + conditionsCnt;
		if (cmdCnt > 1) {
			String cmdTable = "  --" + System.lineSeparator() + "  -- Command\tIndex\tBitvector" + System.lineSeparator();
			int cselBits = log2(cmdCnt);
			String cmd[] = new String[operationsCnt + conditionsCnt];
			String cmdBits[] = new String[operationsCnt + conditionsCnt];
			for (int i = 0; i < operationsCnt; i++) {
				cmd[i] = alu.getOperations().get(i);
				cmdBits[i] = getBinaryString(i, cselBits);
				cmdTable += "  -- " + cmd[i] + " \t" + i + "\t" + cmdBits[i] + System.lineSeparator();
			}
			for (int i = 0; i < conditionsCnt; i++) {
				cmd[i + operationsCnt] = alu.getConditions().get(i);
				cmdBits[i + operationsCnt] = getBinaryString(i + operationsCnt, cselBits);
				cmdTable += "  -- " + cmd[i + operationsCnt] + " \t" + (i + operationsCnt) + "\t" + cmdBits[i + operationsCnt] + System.lineSeparator();
			}
			cmdTable += "  --" + System.lineSeparator() + "  -- Output 1 Multiplexing" + System.lineSeparator();
			cmdTable += "  WITH s_csel SELECT p_output_1 <=" + System.lineSeparator();
			for (int i = 0; i < operationsCnt; i++) {
				cmdTable += "    " + getAluOutputSignal(cmd[i]) + " WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();				
			}
			cmdTable += "    \"" + getBinaryString(0, alu.getWordSize()) + "\" WHEN OTHERS;" + System.lineSeparator();
			cmdTable += "  -- Output 2 Multiplexing" + System.lineSeparator();
			cmdTable += "  WITH s_csel SELECT p_output_2 <=" + System.lineSeparator();
			for (int i = 0; i < operationsCnt; i++) {
				if (cmd[i].matches("MUL|MUL_U")) {
					cmdTable += "    s_mul_result_hi WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();						
				}
				if (cmd[i].matches("DIV|DIV_U")) {
					cmdTable += "    s_div_remain WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();							
				}			
			}
			cmdTable += "    \"" + getBinaryString(0, alu.getWordSize()) + "\" WHEN OTHERS;" + System.lineSeparator();
			cmdTable += "  -- Flag Multiplexing" + System.lineSeparator();
			cmdTable += "  WITH s_csel SELECT p_flag <=" + System.lineSeparator();
			for (int i = 0; i < operationsCnt; i++) {
				if (cmd[i].matches("ADD|ADD_U|SUB|SUB_U")) {
					cmdTable += "    s_adder_ovflw WHEN " + getVhdlBitString(cmdBits[i]) + "," + System.lineSeparator();						
				}
			}
			for (int i = 0; i < operationsCnt; i++) {
				if (cmd[i + conditionsCnt].matches("ZERO|GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
					cmdTable += "    s_comp_result WHEN " + getVhdlBitString(cmdBits[i + conditionsCnt]) + "," + System.lineSeparator();						
				}
			}
			cmdTable += "    \'0\' WHEN OTHERS;" + System.lineSeparator();
			return cmdTable;
		} else {
			if (operationsCnt > 0) {
				String cmd = alu.getOperations().get(0);
				if (cmd.matches("ADD|ADD_U|SUB|SUB_U")) {
					String beh = "";
					beh += "  p_output_1 <= s_adder_result;" + System.lineSeparator();
					beh += "  p_flag <= s_adder_ovflw;" + System.lineSeparator();
					return beh;
				}
				if (cmd.matches("AND|OR|XOR|NOT")) {
					String beh = "";
					beh += "  p_output_1 <= s_logic_result;" + System.lineSeparator();
					return beh;
				}
				if (cmd.matches("DIV|DIV_U")) {
					String beh = "";
					beh += "  p_output_1 <= s_div_result;" + System.lineSeparator();
					beh += "  p_output_2 <= s_div_remain;" + System.lineSeparator();
					return beh;
				}
				if (cmd.matches("MUL|MUL_U")) {
					String beh = "";
					beh += "  p_output_1 <= s_mul_result_lo;" + System.lineSeparator();
					beh += "  p_output_2 <= s_mul_result_hi;" + System.lineSeparator();
					return beh;
				}
				if (cmd.matches("RR|RL|SRL|SLL|SRA")) {
					String beh = "";
					beh += "  p_output_1 <= s_shft_result;" + System.lineSeparator();
					return beh;
				}
			} else if (conditionsCnt > 0) {
				String cmd = alu.getConditions().get(0);
				if (cmd.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
					String beh = "";
					beh += "  p_flag <= s_comp_result;" + System.lineSeparator();
					return beh;
				}
			}
			return "";
		}
	}

	private List<String> getAluImports(AluEntity alu) {
		List<String> imports = new ArrayList<String>();
		List<String> subComponents = getAluSubComponents(alu);
		try {
			if (subComponents.contains("ADDER")) {
				copy(new File("processors/components/alu/adder"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT carry_select_adder" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_block_size : integer := 7;" + System.lineSeparator();
				imprt += "      g_blocks     : integer := 3" + System.lineSeparator();
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
			if (subComponents.contains("BITLOGIC")) {
				copy(new File("processors/components/alu/bitwise_logic"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT bit_manipulator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
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
			if (subComponents.contains("COMPARATOR")) {
				copy(new File("processors/components/alu/comparator"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT tree_comparator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
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
			if (subComponents.contains("DIVIDER")) {
				copy(new File("processors/components/alu/divider"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT divider " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
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
			if (subComponents.contains("MULTIPLIER")) {
				copy(new File("processors/components/alu/multiplier"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT four_quadrant_multiplier " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
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
			if (subComponents.contains("SHIFTER")) {
				copy(new File("processors/components/alu/shifter"), new File(targetDirectory + "/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT barrel_shifter " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_arith  : in  std_logic;" + System.lineSeparator();
				imprt += "      p_rotate : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_cmd    : in  std_logic_vector(1 DOWNTO 0);" + System.lineSeparator();
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
	
	private String getAluOutputSignal(String cmd) {
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
			return "s_mul_result_lo";
		}
		if (cmd.matches("RR|RL|SRL|SLL|SRA")) {
			return "s_shft_result";
		}
		return "";
	}
	
	private String generateAluSubComponentsInstances(AluEntity alu) {
		String instances = "";
		List<String> subComponents = getAluSubComponents(alu);
		if (subComponents.contains("ADDER")) {
			int blocks = (alu.getWordSize() / 8) - 1;
			instances += "  adder : carry_select_adder GENERIC MAP (g_block_size => 7, g_blocks => " + blocks + ") PORT MAP (s_sgnd, s_adder_sub, s_input_A, s_input_B, s_adder_result, s_adder_ovflw);" + System.lineSeparator();
		}
		if (subComponents.contains("BITLOGIC")) {
			instances += "  logic : bit_manipulator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_logic_cmd, s_logic_result);" + System.lineSeparator();
		}
		if (subComponents.contains("DIVIDER")) {
			instances += "  div : divider GENERIC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, s_div_remain, s_div_result);" + System.lineSeparator();
		}
		if (subComponents.contains("MULTIPLIER")) {
			instances += "  mul : four_quadrant_multiplier GENERIC MAP (g_size => g_word_size) PORT MAP (s_sgnd, s_input_A, s_input_B, (OTHERS => '0'), s_mul_result_lo, s_mul_result_hi);" + System.lineSeparator();
		}
		if (subComponents.contains("COMPARATOR")) {
			instances += "  comp : word_comparator GENERIC MAP (g_size => g_word_size) PORT MAP (s_input_A, s_input_B, s_comp_cmd, s_sgnd, s_comp_result);" + System.lineSeparator();
		}
		if (subComponents.contains("SHIFTER")) {
			instances += "  shft : barrel_shifter GENERIC MAP (g_size => g_word_size) PORT MAP (s_shft_cmd, s_shft_ari, s_shft_rot, s_input_A, s_input_B, s_shft_result);" + System.lineSeparator();
		}
		return instances;
	}
	
	private List<String> getAluSubComponents(AluEntity alu) {
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
					components.add("MULTIPLIER");
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

	private String generateMux(String adr, String outp, String inputName, int inputs) {
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
	
	private int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
	
	private String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
	private void copy(File sourceLocation, File targetLocation) throws IOException {
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
}