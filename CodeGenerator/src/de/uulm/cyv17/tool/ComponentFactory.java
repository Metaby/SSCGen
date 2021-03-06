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

import javafx.util.Pair;

import de.uulm.cyv17.wrapper.*;
import de.uulm.cyv17.wrapper.entities.AluEntity;
import de.uulm.cyv17.wrapper.entities.CustomEntity;
import de.uulm.cyv17.wrapper.entities.MultiplexerEntity;
import de.uulm.cyv17.wrapper.entities.RegisterEntity;
import de.uulm.cyv17.wrapper.entities.RegisterFileEntity;
import de.uulm.cyv17.wrapper.entities.RomEntity;

/**
 * Factory class for generating the components of
 * an architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
class ComponentFactory {
	
	private String targetDirectory;
	
	/**
	 * The constructor of the factory class.
	 * 
	 * @param targetDirectory the target directory in which to store the generated components
	 */
	ComponentFactory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}
	
	/**
	 * Function for generating the vhdl component out
	 * of a register file entity.
	 * 
	 * @param rf the register file entity to be converted
	 * @return a working vhdl component
	 */
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
					component.AddSignal("s_port" + j + "_isel", 1);
				} else if (p.getInputs().size() > 2) {
					ctrlBinding += "  s_port" + j + "_isel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_isel", adrBits);				
				}
				adrBits = (int)Math.ceil(Math.log(p.getAddresses().size()) / Math.log(2));
				if (p.getAddresses().size() == 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + ctrlSize++ + ");" + System.lineSeparator();
					component.AddSignal("s_port" + j + "_asel", 1);
				} else if (p.getAddresses().size() > 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_asel", adrBits);				
				}
				component.AddSignal("s_port" + j + "_inputSelect", "", "g_word_size");
				muxes += VhdlComponent.generateMux("s_port" + j + "_isel", "s_port" + j + "_inputSelect", "p_port" + j +  "_input", p.getInputs().size());
				component.AddSignal("s_port" + j + "_addressSelect", "", "g_address_size");
				muxes += VhdlComponent.generateMux("s_port" + j + "_asel", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddSignal("s_port" + j + "_write", 1);
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
					component.AddSignal("s_port" + j + "_asel", 1);
				} else if (p.getAddresses().size() > 2) {
					ctrlBinding += "  s_port" + j + "_asel <= p_ctrl(" + (ctrlSize + adrBits - 1) + " DOWNTO " + ctrlSize + ");" + System.lineSeparator();
					ctrlSize += adrBits;
					component.AddSignal("s_port" + j + "_asel", adrBits);				
				}
				component.AddSignal("s_port" + j + "_addressSelect", "", "g_address_size");
				muxes += VhdlComponent.generateMux("s_port" + j + "_addressSelect", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddPort("p_port" + j + "_output : out", "g_word_size");
			}
		}
		component.AddType("registerArray IS ARRAY(" + ((int)Math.pow(2, rf.getAddressSize()) - 1) + " DOWNTO 0) OF std_logic_vector(g_word_size DOWNTO 0)");
		if (tmp == 1) {
			component.AddPort("p_ctrl : in", 1);
		} else if (tmp > 1) {
			component.AddPort("p_ctrl : in", ctrlSize);
		}
		component.AddSignal("s_registers", "registerArray", "");
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
			System.err.println(targetFile);
			ErrorHandler.throwError(5);
		}
		return component;
	}
	
	/**
	 * Function for generating the vhdl component out
	 * of a multiplexer entity.
	 * 
	 * @param mux the multiplexer entity to be converted
	 * @return a working vhdl component
	 */
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
			System.err.println(targetFile);
			ErrorHandler.throwError(5);
		}	
		return component;
	}
	
	/**
	 * Function for generating the vhdl component out
	 * of a rom entity.
	 * 
	 * @param rom the rom entity to be converted
	 * @return a working vhdl component
	 */
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
				System.err.println(contentFile);
				ErrorHandler.throwError(15);
			}
			content = HexGenerator.ReadIntelHexFile(contentFile, (int)Math.ceil(wordSize / 8));
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
		component.AddSignal("s_address", "", "g_address_size");		
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
		behavior += "    \"" + getBinaryString(0, wordSize) + "\" WHEN OTHERS;" + System.lineSeparator();			
		component.setBehavior(behavior);
		String targetFile = targetDirectory + "/components/" + rom.getId() + ".vhdl";
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(targetFile);
			ErrorHandler.throwError(5);
		}		
		return component;	
	}
	
	/**
	 * Function for generating the vhdl component out
	 * of a register entity.
	 * 
	 * @param register the register entity to be converted
	 * @return a working vhdl component
	 */
	VhdlComponent generateComponent(RegisterEntity register) {
		VhdlComponent component = new VhdlComponent(register.getId());
		component.AddPort("p_clk : in", 1);
		component.AddPort("p_rst : in", 1);
		component.AddSignal("s_write", 1);
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
			component.AddPort("p_input" + i + " : in", register.getWordSize());
		}
		if (register.getInputs().size() > 1) {
			if (adrSize > 1) {
				component.AddSignal("s_isel", adrSize);				
			} else {
				component.AddSignal("s_isel", 1);			
			}			
		}
		component.AddPort("p_word : out", register.getWordSize());
		component.AddSignal("s_input", register.getWordSize());
		component.AddSignal("s_out", register.getWordSize());
		behavior += VhdlComponent.generateMux("s_isel", "s_input", "p_input", register.getInputs().size(), register.getWordSize());
		behavior += "  p_word <= s_out;" + System.lineSeparator();
		behavior += "  PROCESS (p_clk) BEGIN" + System.lineSeparator();
		behavior += "    IF rising_edge(p_clk) THEN" + System.lineSeparator();
		behavior += "      IF p_rst = '1' THEN" + System.lineSeparator();
		if (register.getWordSize() > 1) {
			behavior += "        s_out <= (OTHERS => '0');" + System.lineSeparator();
		} else {
			behavior += "        s_out <= '0';" + System.lineSeparator();			
		}
		behavior += "      ELSIF s_write = '1' THEN" + System.lineSeparator();
		behavior += "        s_out <= s_input;" + System.lineSeparator();
		behavior += "      ELSE" + System.lineSeparator();
		behavior += "        s_out <= s_out;" + System.lineSeparator();
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
			System.err.println(targetFile);
			ErrorHandler.throwError(5);
		}			
		return component;
	}
	
	/**
	 * Function for generating the vhdl component out
	 * of a alu entity.
	 * 
	 * @param alu the alu entity to be converted
	 * @return a working vhdl component
	 */
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
			component.AddSignal("s_isel_A", iselASize);
		}
		if (alu.getInputsB().size() > 1) {
			component.AddSignal("s_isel_B", iselBSize);		
		}
		if (cselSize > 0) {
			component.AddSignal("s_csel", cselSize);
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
				ctrlBinding += "  s_isel_A <= p_ctrl(" + cselSize + ");" + System.lineSeparator();			
			} else {	
				ctrlBinding += "  s_isel_A <= p_ctrl;" + System.lineSeparator();
			}
		} else if (iselASize > 1) {
			ctrlBinding += "  s_isel_A <= p_ctrl(" + (cselSize + iselASize - 1) + " DOWNTO " + cselSize + ");" + System.lineSeparator();
		}
		if (iselBSize == 1) {
			if (ctrlSize > 1) {
				ctrlBinding += "  s_isel_B <= p_ctrl(" + (cselSize + iselASize) + ");" + System.lineSeparator();			
			} else {	
				ctrlBinding += "  s_isel_B <= p_ctrl;" + System.lineSeparator();	
			}
		} else if (iselBSize > 1) {
			ctrlBinding += "  s_isel_B <= p_ctrl(" + (cselSize + iselASize + iselBSize - 1) + " DOWNTO " + (cselSize + iselASize) + ");" + System.lineSeparator();
		}
		List<String> subComponents = getAluSubComponents(alu);
		if (conditionsCnt > 0 || subComponents.contains("ADDER")) {
			component.AddPort("p_flag : out", 1);
		}
		component.AddPort("p_output_1 : out", "g_word_size");
		component.AddPort("p_output_2 : out", "g_word_size");
		// Signals
		component.AddSignal("s_input_A", "", "g_word_size");
		component.AddSignal("s_input_B", "", "g_word_size");
		component.AddSignal("s_sgnd", 1);
		if (subComponents.contains("ADDER")) {
			component.AddSignal("s_adder_sub", 1);
			component.AddSignal("s_adder_ovflw", 1);
			component.AddSignal("s_adder_result", "", "g_word_size");			
		}
		if (subComponents.contains("BITLOGIC")) {
			component.AddSignal("s_logic_cmd", 2);
			component.AddSignal("s_logic_result", "", "g_word_size");
		}
		if (subComponents.contains("DIVIDER")) {
			component.AddSignal("s_div_remain", "", "g_word_size");
			component.AddSignal("s_div_result", "", "g_word_size");			
		}
		if (subComponents.contains("MULTIPLIER")) {
			component.AddSignal("s_mul_result_hi", "", "g_word_size");
			component.AddSignal("s_mul_result_lo", "", "g_word_size");			
		}
		if (subComponents.contains("COMPARATOR")) {
			component.AddSignal("s_comp_cmd", 3);
			component.AddSignal("s_comp_result", 1);
		}
		if (subComponents.contains("SHIFTER")) {
			component.AddSignal("s_shft_ari", 1);
			component.AddSignal("s_shft_rot", 1);
			component.AddSignal("s_shft_cmd", 2);
			component.AddSignal("s_shft_ctrl", 4);
			component.AddSignal("s_shft_result", "", "g_word_size");
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
			System.err.println(targetFile);
			ErrorHandler.throwError(5);
		}
		return component;
	}
	
	/**
	 * Converts the given bit string to a bit string in 
	 * vhdl syntax by adding '' or "".
	 * 
	 * @param bits the bit string
	 * @return the converted bit string
	 */
	private String getVhdlBitString(String bits) {
		if (bits.length() == 1) {
			return "\'" + bits + "\'";
		} else {
			return "\"" + bits + "\"";
		}
	}
	
	/**
	 * This function creates the command table for a given alu.
	 * 
	 * @param alu the alu entity to use
	 * @return a string containing the command table
	 */
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
				table += "  s_shft_cmd(0) <= s_shft_ctrl(0);" + System.lineSeparator();
				table += "  s_shft_cmd(1) <= s_shft_ctrl(1);" + System.lineSeparator();
				table += "  s_shft_ari <= s_shft_ctrl(2);" + System.lineSeparator();
				table += "  s_shft_rot <= s_shft_ctrl(3);" + System.lineSeparator();
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
	
	/**
	 * Generates the output multiplexer for a given alu.
	 * 
	 * @param alu the alu entity to use
	 * @return a string containing the multiplexing mechanism of the alu outputs
	 */
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

	/**
	 * Generates the imports used within the vhdl component of the alu.
	 * 
	 * @param alu the alu entity to be used
	 * @return a list of strings containing the import definitions
	 */
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
				imprt += "  COMPONENT word_comparator " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "		p_cmd    : in  std_logic_vector(2 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_sgnd   : in  std_logic;" + System.lineSeparator();					
				imprt += "      p_result : out std_logic" + System.lineSeparator();
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
	
	/**
	 * Determines the output signal of a command for the alu.
	 * 
	 * @param cmd the command to be evaluated
	 * @return a string containing the appropriate output signal
	 */
	private String getAluOutputSignal(String cmd) {
		if (cmd.matches("ADD|ADD_U|SUB|SUB_U")) {
			return "s_adder_result";
		}
		if (cmd.matches("AND|OR|XOR|NOT")) {
			return "s_logic_result";
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
	
	/**
	 * Generates the instances for the components used within the alu.
	 * 
	 * @param alu the alu entity to be used
	 * @return a string containing the the instance declarations
	 */
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
			instances += "  shft : barrel_shifter GENERIC MAP (g_size => g_word_size) PORT MAP (s_shft_ari, s_shft_rot, s_shft_cmd, s_input_A, s_input_B, s_shft_result);" + System.lineSeparator();
		}
		return instances;
	}
	
	/**
	 * Creates a list of the needed alu sub components.
	 * 
	 * @param alu the alu entity to be used
	 * @return a list of strings containing the needed sub components
	 */
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

	/**
	 * This function generates a complete multiplexer in vhdl code.
	 * Various parameters are needed in order to successfully generate
	 * an appropriate multiplexer.
	 * 
	 * The scheme is as followed:
	 * The multiplexer uses one input name for all cases. This input name
	 * is extended by an indicating number for each case. An example is
	 * given beneath.
	 * 
	 * Parameters:
	 * inputName := inp
	 * inputs := 4
	 * 
	 * Generated vhdl code:
	 * WITH adr SELECT output <=
	 * 	inp0 WHEN "00",
	 * 	inp1 WHEN "01",
	 * 	inp2 WHEN "10",
	 * 	inp3 WHEN "11",
	 * 	(others => '0') WHEN others;
	 * 
	 * @param adr the signal or port to be used for the condition
	 * @param outp the signal or port specifying the output
	 * @param inputName the signal or port used to connect to the output
	 * @param inputs the number of inputs
	 * @return a functioning vhdl multiplexer
	 */
	private String generateMux(String adr, String outp, String inputName, int inputs) {
		String behavior = "";
		if (inputs == 2) {
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			behavior += "    " + inputName + "0 WHEN \'0\'," + System.lineSeparator();
			behavior += "    " + inputName + "1 WHEN \'1\'," + System.lineSeparator();
			behavior += "    (others => '0') WHEN others;" + System.lineSeparator();
		} else if (inputs > 2) {	
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			int adrSize = (int)Math.ceil(Math.log(inputs) / Math.log(2));
			for (int i = 0; i < inputs; i++) {
				behavior += "    " + inputName + i + " WHEN \"" + getBinaryString(i, adrSize) + "\"," + System.lineSeparator();
			}			
			behavior += "    (others => '0') WHEN others;" + System.lineSeparator();
		} else {
			behavior += "  " + outp + " <= " + inputName + "0;" + System.lineSeparator();
		}
		return behavior;
	}
	
	/**
	 * Calculates the logarithm to the base 2 of a given value.
	 * 
	 * @param value the integer value to be used
	 * @return the base two logarithm of the integer
	 */
	private int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
	
	/**
	 * Converts a given integer to a binary string under the
	 * restriction of a given number of digits. This is needed
	 * due to the syntax of vhdl.
	 * 
	 * @param value the value to be converted
	 * @param digits the number of digits
	 * @return a string containing the binary representation
	 */
	private String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
	/**
	 * This method is used to copy complete directories with
	 * the files from the source to the target.
	 * 
	 * @param sourceLocation the source location
	 * @param targetLocation the target location
	 * @throws IOException
	 */
	private void copy(File sourceLocation, File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}
	
	/**
	 * This method is used to copy directories from the source to the target.
	 * 
	 * @param source the source file
	 * @param target the target file
	 * @throws IOException
	 */
	private void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }
	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}
	
	/**
	 * This method is used to copy files from the source to the target.
	 * 
	 * @param source the source file
	 * @param target the target file
	 * @throws IOException
	 */
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
