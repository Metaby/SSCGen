package de.uulm.cyv17.tool;

import java.util.List;

import de.uulm.cyv17.wrapper.*;
import de.uulm.cyv17.wrapper.entities.AluEntity;
import de.uulm.cyv17.wrapper.entities.CustomEntity;
import de.uulm.cyv17.wrapper.entities.MultiplexerEntity;
import de.uulm.cyv17.wrapper.entities.RegisterEntity;
import de.uulm.cyv17.wrapper.entities.RegisterFileEntity;
import de.uulm.cyv17.wrapper.entities.RomEntity;

/**
 * A factory class to generate the instances used
 * in the top level entity of the architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
class InstanceFactory {

	private List<Connector> outputConnectors;
	private int cvCounter;
	
	/**
	 * The constructor of the instance factory. It takes the
	 * architecture containing the sub components out of which
	 * the instances are generated.
	 * 
	 * @param arch the architecture
	 */
	InstanceFactory(Architecture arch) {
		outputConnectors = arch.getOutputConnectors(true);
		cvCounter = 0;
	}
	
	/**
	 * Generates the instance for a given reigster.
	 * 
	 * @param reg the register entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	String generateInstance(RegisterEntity reg) {
		String instance = "  " + reg.getId() + "_instance : " + reg.getId() + System.lineSeparator();
		instance += "    PORT MAP (" + System.lineSeparator();
		instance += "      p_clk," + System.lineSeparator() + "      p_reset," + System.lineSeparator();
		instance += "      " + generateInputSignal(reg.getControl()) + "," + System.lineSeparator();
		for (Connector con : reg.getInputs()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		instance += "      " + generateOutputSignal(reg.getOutput()) + System.lineSeparator();
		instance += "    );";
		return instance;
	}
	
	/**
	 * Generates the instance for a given rom
	 * 
	 * @param rom the rom entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	String generateInstance(RomEntity rom) {
		String instance = "  " + rom.getId() + "_instance : " + rom.getId() + System.lineSeparator();
		instance += "    GENERIC MAP (g_address_size => " + (rom.getAddressSize() - 1) + ", " + "g_word_size => " + (rom.getWordSize() - 1) + ")" + System.lineSeparator();
		instance += "    PORT MAP (" + System.lineSeparator();
		for (Connector con : rom.getAddresses()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		if (rom.getAddresses().size() > 1) {
			instance += "      " + generateInputSignal(rom.getControl()) + "," + System.lineSeparator();			
		}
		instance += "      " + generateOutputSignal(rom.getOutput()) + System.lineSeparator();
		instance += "    );";
		return instance;		
	}
	
	/**
	 * Generates the instance for a given multiplexer
	 * 
	 * @param mux the multiplexer entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	String generateInstance(MultiplexerEntity mux) {
		String instance = "  " + mux.getId() + "_instance : " + mux.getId() + System.lineSeparator();
		instance += "    GENERIC MAP (g_word_size => " + (mux.getWordSize() - 1) + ")" + System.lineSeparator();
		instance += "    PORT MAP (" + System.lineSeparator();
		for (Connector con : mux.getInputs()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		if (mux.getInputs().size() > 1) {
			instance += "      " + generateInputSignal(mux.getControl()) + "," + System.lineSeparator();			
		}
		instance += "      " + generateOutputSignal(mux.getOutput()) + System.lineSeparator();
		instance += "    );";
		return instance;		
	}
	
	/**
	 * Generates the instance for a given alu
	 * 
	 * @param alu the alu entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	String generateInstance(AluEntity alu) {
		String instance = "  " + alu.getId() + "_instance : " + alu.getId() + System.lineSeparator();
		instance += "    GENERIC MAP (g_word_size => " + (alu.getWordSize() - 1) + ")" + System.lineSeparator();
		instance += "    PORT MAP (" + System.lineSeparator();
		for (Connector con : alu.getInputsA()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		for (Connector con : alu.getInputsB()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		if (alu.getConditions().size() > 1 || alu.getOperations().size() > 1 || alu.getInputsA().size() > 1 || alu.getInputsB().size() > 1) {
			instance += "      " + generateInputSignal(alu.getControl()) + "," + System.lineSeparator();		
		}
		if (alu.getConditions().size() > 0 || alu.getOperations().contains("ADD") || alu.getOperations().contains("ADD_U") || alu.getOperations().contains("SUB") || alu.getOperations().contains("SUB_U")) {
			instance += "      " + generateInputSignal(alu.getStatus()) + "," + System.lineSeparator();
		}
		instance += "      " + generateInputSignal(alu.getOutput1()) + "," + System.lineSeparator();
		instance += "      " + generateOutputSignal(alu.getOutput2()) + System.lineSeparator();
		instance += "    );";
		return instance;
	}
	
	/**
	 * Generates the instance for a given register file
	 * 
	 * @param rf the register file entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	String generateInstance(RegisterFileEntity rf) {
		String instance = "  " + rf.getId() + "_instance : " + rf.getId() + System.lineSeparator();
		instance += "    GENERIC MAP (g_address_size => " + (rf.getAddressSize() - 1) + ", " + "g_word_size => " + (rf.getWordSize() - 1) + ")" + System.lineSeparator();
		instance += "    PORT MAP (" + System.lineSeparator();
		instance += "      p_clk," + System.lineSeparator() + "      p_reset," + System.lineSeparator();
		for (Port p : rf.getPorts()) {
			if (p.getDirection() == PortDirection.IN) {
				for (Connector con : p.getInputs()) {
					instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
				}
				for (Connector con : p.getAddresses()) {
					instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
				}
			} else {
				for (Connector con : p.getAddresses()) {
					instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
				}
				instance += "      " + generateOutputSignal(p.getOutput()) + "," + System.lineSeparator();
			}
		}
		instance += "      " + generateInputSignal(rf.getControl()) + System.lineSeparator();
		instance += "    );";
		return instance;		
	}
	
	/**
	 * Generates the instance for a given custom entity
	 * 
	 * @param cus the custom entity to be used
	 * @return a string containing the instance as vhdl code
	 */
	public String generateInstance(CustomEntity cus) {
		String instance = "  " + cus.getId() + "_instance : " + cus.getName() + System.lineSeparator();
		if (cus.getGenerics().size() > 0) {
			instance += "    GENERIC MAP (";
			for (String key : cus.getGenerics().keySet()) {
				instance += key + " => " + cus.getGenerics().get(key) + ", ";
			}
			instance = instance.substring(0, instance.length() - 2) + ")" + System.lineSeparator();
		}
		instance += "    PORT MAP(" + System.lineSeparator();
		for (Connector con : cus.getInputConnectors()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		for (Connector con : cus.getOutputConnectors()) {
			instance += "      " + generateInputSignal(con) + "," + System.lineSeparator();
		}
		return instance.substring(0, instance.length() - 3) + System.lineSeparator() + "    );";
	}
	
	/**
	 * A function which creates the appropriate vhdl code
	 * for an input signal out of a given connector.
	 * 
	 * @param con the connector to be used
	 * @return a string containing the correct input signal
	 */
	private String generateInputSignal(Connector con) {
		String signal = "";
		int sSize = getSourceSize(con);
		int cSize = con.size;
		int boundingWidth = con.upperBound - con.lowerBound + 1;
		if (con.type == ConnectorType.STANDARD) {
			if (sSize < cSize) {
				int diff = cSize - sSize;
				if (diff == 1) {
					signal += "\'0\' & ";
				} else if (diff > 1) {
					signal += "\"";
					for (int i = 0; i < diff; i++) signal += "0";
					signal += "\" & ";
				}
			}
			if (con.lowerBound != -1 && con.upperBound != -1) {
				if (boundingWidth < cSize) {
					int diff = cSize - boundingWidth;
					if (diff == 1) {
						signal += "\'0\' & ";
					} else if (diff > 1) {
						signal += "\"";
						for (int i = 0; i < diff; i++) signal += "0";
						signal += "\" & ";
					}				
				}
			}	
		}
		if (con.type == ConnectorType.SYSTEM_AUTO) {
			if (con.size == 1) {
				signal += "s_ctrl_vector(" + (cvCounter++) + ")";
			} else if (con.size > 1) {
				signal += "s_ctrl_vector(" + (cvCounter + con.size - 1) + " DOWNTO " + cvCounter + ")";
				cvCounter += con.size;
			}
		} else if (con.type == ConnectorType.SYSTEM_CONST || con.type == ConnectorType.SYSTEM_IN) {
			signal += con.toSignal();
	    } else {
			if (con.lowerBound != -1 && con.upperBound != -1) {
				if (con.lowerBound == con.upperBound) {
					signal += con.toSignal() + "(" + con.upperBound + ")";					
				} else {
					signal += con.toSignal() + "(" + con.upperBound + " DOWNTO " + con.lowerBound + ")";					
				}
			} else {
				signal += con.toSignal();
			}
		}
		return signal;
	}
	
	/**
	 * A function which creates the appropriate vhdl code
	 * for an output signal out of a given connector.
	 * 
	 * @param con the connector to be used
	 * @return a string containing the correct output signal
	 */
	private String generateOutputSignal(Connector con) {
		if (con.type == ConnectorType.SYSTEM_OUT || con.type == ConnectorType.STANDARD) {
			return con.toSignal();
		} else if (con.type == ConnectorType.SYSTEM_CONTROL) {
			return "s_ctrl_vector";
		}
		return "OPEN";
	}
	
	/**
	 * Determines and returns the expected size of the
	 * source connector.
	 * 
	 * @param con the connector which refers to the source connector
	 * @return the size of the source connector
	 */
	private int getSourceSize(Connector con) {
		for (Connector c : outputConnectors) {
			if (c != null && c.toString().equals(con.toString())) {
				return c.size;
			}
		}
		return 0;
	}
}
