package de.uulm.cyv17.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information for a specific
 * vhdl component and represents it. It can generate
 * a complete vhdl file out of these information.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
class VhdlComponent {

	private List<String> libraries;
	private List<String> generics;
	private List<String> signals;
	private List<String> imports;
	private List<String> types;
	private List<String> ports;
	private String behavior;
	private String name;
	private String nl;
	
	/**
	 * The constructor for the vhdl component class.
	 * 
	 * @param name the name of the represented vhdl component
	 */
	VhdlComponent(String name) {
		libraries = new ArrayList<String>();
		generics = new ArrayList<String>();
		signals = new ArrayList<String>();
		imports = new ArrayList<String>();
		types = new ArrayList<String>();
		ports = new ArrayList<String>();
		this.name = name;
		behavior = "";
		nl = System.lineSeparator();
	}

	/**
	 * Adds a library import to the vhdl component.
	 * 
	 * @param library the library to be added
	 */
	void AddLibrary(String library) {
		if (!libraries.contains(library)) {
			libraries.add(library);			
		}
	}
	
	/**
	 * Adds a generic value to the vhdl component.
	 * 
	 * @param generic the name of the generic to be added
	 */
	void AddGeneric(String generic) {
		if (!generics.contains(generic)) {
			generics.add(generic);			
		}
	}

	/**
	 * Adds a port to the vhdl component.
	 * 
	 * @param port the port name
	 * @param size the size of the port
	 */
	void AddPort(String port, int size) {
		if (size > 1) {
			port += " std_logic_vector(" + (size - 1) + " DOWNTO 0)";
		} else {
			port += " std_logic";
		}
		if (!ports.contains(port)) {
			ports.add(port);			
		}
	}
	/**
	 * Adds a port to the vhdl component.
	 * 
	 * @param port the port name
	 * @param size the generic for the size of the port
	 */
	void AddPort(String port, String upperBound) {
		port += " std_logic_vector(" + upperBound + " DOWNTO 0)";
		if (!ports.contains(port)) {
			ports.add(port);			
		}
	}
	
	/**
	 * Adds a signal to the vhdl component.
	 * 
	 * @param signal the signal name to be added
	 * @param size the size of the signal
	 */
	void AddSignal(String signal, int size) {
		if (size > 1) {
			signal += " : std_logic_vector(" + (size - 1) + " DOWNTO 0)";
		} else {
			signal += " : std_logic";
		}
		if (!signals.contains(signal)) {
			signals.add(signal);			
		}
	}
	
	/**	 
	 * Adds a signal to the vhdl component. A type
	 * can be used instead of std_logic or std_logic_vectors.
	 * 
	 * @param signal the signal name to be added
	 * @param type the type to be used, if not specified std_logic and std_logic_vector will be used
	 * @param upperBound the upper bound of the signal, can be a generic string or an integer value
	 */
	void AddSignal(String signal, String type, String upperBound) {
		if (type.equals("")) {
			signal += " : std_logic_vector(" + upperBound + " DOWNTO 0)";
		} else {
			signal += " : " + type;			
		}
		if (!signals.contains(signal)) {
			signals.add(signal);			
		}
	}
	
	/**
	 * Adds an import to the vhdl component.
	 * 
	 * @param imprt the import to be added
	 */
	void AddImport(String imprt) {
		if (!imports.contains(imprt)) {
			imports.add(imprt);			
		}
	}
	
	/**
	 * Adds a type definition to the vhdl component.
	 * 
	 * @param type the type definition to be added
	 */
	void AddType(String type) {
		if (!types.contains(type)) {
			types.add(type);			
		}
	}
	
	/**
	 * Builds the vhdl code of the component out of the
	 * information stored in this class and returns it.
	 * 
	 * @return a string containing the complete vhdl code
	 */
	public String getComponent() {
		StringBuilder component = new StringBuilder();
		component.append("-- Auto generated" + nl + nl);
		component.append("LIBRARY ieee;" + nl);
		component.append("USE ieee.std_logic_1164.all;" + nl);
		for (String lib : libraries) {
			component.append(lib + nl);
		}
		component.append(nl + "ENTITY " + name + " IS" + nl);
		if (generics.size() > 0) {
			component.append("  GENERIC (" + nl);
			for (int i = 0; i < generics.size(); i++) {
				if (i < generics.size() - 1) {
					component.append("    " + generics.get(i) + ";" + nl);
				} else {
					component.append("    " + generics.get(i) + nl);					
				}
			}
			component.append("  );" + nl);
		}	
		if (ports.size() > 0) {
			component.append("  PORT (" + nl);
			for (int i = 0; i < ports.size(); i++) {
				if (i < ports.size() - 1) {
					component.append("    " + ports.get(i) + ";" + nl);
				} else {
					component.append("    " + ports.get(i) + nl);					
				}
			}
			component.append("  );" + nl);
		}	
		component.append("END " + name + ";" + nl + nl);
		component.append("ARCHITECTURE behavior OF " + name + " IS" + nl);
		if (imports.size() > 0) {
			for (int i = 0; i < imports.size(); i++) {
				component.append(imports.get(i) + nl);
			}
		}
		if (types.size() > 0) {
			for (int i = 0; i < types.size(); i++) {
				component.append("  TYPE " + types.get(i) + ";" + nl);
			}
		}	
		if (signals.size() > 0) {
			for (int i = 0; i < signals.size(); i++) {
				component.append("  SIGNAL " + signals.get(i) + ";" + nl);
			}
		}
		component.append("BEGIN" + nl);
		component.append(behavior + nl);		
		component.append("END behavior;");
		return component.toString();
	}

	/**
	 * Sets the behavior of the vhdl component
	 * 
	 * @param behavior
	 */
	public void setBehavior(String behavior) {
		this.behavior = behavior;
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
	 * wordSize := 8
	 * 
	 * Generated vhdl code:
	 * WITH adr SELECT output <=
	 * 	inp0 WHEN "00",
	 * 	inp1 WHEN "01",
	 * 	inp2 WHEN "10",
	 * 	inp3 WHEN "11",
	 * 	(others => '0') WHEN others;
	 * 
	 * In case that the wordSize is 1, the last line will change to
	 * 	'0' WHEN others;
	 * 
	 * @param adr the signal or port to be used for the condition
	 * @param outp the signal or port specifying the output
	 * @param inputName the signal or port used to connect to the output
	 * @param inputs the number of inputs
	 * @param wordSize the word size of the multiplexer
	 * @return a functioning vhdl multiplexer
	 */
	public static String generateMux(String adress, String output, String inputName, int inputs, int wordSize) {
		String behavior = "";
		if (inputs == 2) {
			behavior += "  WITH " + adress + " SELECT " + output + " <=" + System.lineSeparator();
			behavior += "    " + inputName + "0 WHEN \'0\'," + System.lineSeparator();
			behavior += "    " + inputName + "1 WHEN \'1\'," + System.lineSeparator();
			if (wordSize == 1) {
				behavior += "    '0' WHEN OTHERS;" + System.lineSeparator();
			} else {
				behavior += "    (OTHERS => '0') WHEN OTHERS;" + System.lineSeparator();
			}
		} else if (inputs > 2) {
			behavior += "  WITH " + adress + " SELECT " + output + " <=" + System.lineSeparator();
			int adrSize = (int)Math.ceil(Math.log(inputs) / Math.log(2));
			for (int i = 0; i < inputs; i++) {
				behavior += "    " + inputName + i + " WHEN \"" + String.format("%" + adrSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"," + System.lineSeparator();
			}
			if (wordSize == 1) {
				behavior += "    '0' WHEN OTHERS;" + System.lineSeparator();
			} else {
				behavior += "    (OTHERS => '0') WHEN OTHERS;" + System.lineSeparator();
			}
		} else {
			behavior += "  " + output + " <= " + inputName + "0;" + System.lineSeparator();
		}
		return behavior;
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
	 * wordSize := 8
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
	public static String generateMux(String adress, String output, String inputName, int inputs) {
		return generateMux(adress, output, inputName, inputs, 2);
	}
	
	/**
	 * Generates an appropriate import definition of the vhdl component
	 * and returns it.
	 * 
	 * @return a string containing the import definition
	 */
	public String getImport() {
		String imprt = "";
		imprt += "  COMPONENT " + name + System.lineSeparator();
		if (generics.size() > 0) {
			imprt += "    GENERIC (" + System.lineSeparator();
			for (int i = 0; i < generics.size(); i++) {
				if (i < generics.size() - 1) {
					imprt += "      " + generics.get(i) + ";" + System.lineSeparator();					
				} else {
					imprt += "      " + generics.get(i) + System.lineSeparator();
				}
			}
			imprt += "    );" + System.lineSeparator();
		}
		if (ports.size() > 0) {
			imprt += "    PORT (" + System.lineSeparator();
			for (int i = 0; i < ports.size(); i++) {
				if (i < ports.size() - 1) {
					imprt += "      " + ports.get(i) + ";" + System.lineSeparator();
				} else {
					imprt += "      " + ports.get(i) + System.lineSeparator();					
				}
			}
			imprt += "    );" + System.lineSeparator();
		}
		imprt += "  END COMPONENT;";
		return imprt;
	}
	
	/**
	 * Returns the generics of the vhdl component.
	 * 
	 * @return a list of strings containing the generics
	 */
	public List<String> getGenerics() {
		return generics;
	}
	
	/**
	 * Returns the ports of the vhdl component.
	 * 
	 * @return a list of strings containing the ports
	 */
	public List<String> getPorts() {
		return ports;
	}
	
	/**
	 * Returns the name of the vhdl component.
	 * 
	 * @return a strings containing the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the signals of the vhdl component.
	 * 
	 * @return a list of strings containing the signals
	 */
	public List<String> getSignals() {
		return signals;
	}
}
