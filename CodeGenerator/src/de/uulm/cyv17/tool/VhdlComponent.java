package de.uulm.cyv17.tool;

import java.util.ArrayList;
import java.util.List;

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

	void AddLibrary(String library) {
		if (!libraries.contains(library)) {
			libraries.add(library);			
		}
	}
	
	void AddGeneric(String generic) {
		if (!generics.contains(generic)) {
			generics.add(generic);			
		}
	}

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
	
	void AddPort(String port, String upperBound) {
		port += " std_logic_vector(" + upperBound + " DOWNTO 0)";
		if (!ports.contains(port)) {
			ports.add(port);			
		}
	}
	
//	void AddSignal(String signal) {
//		if (!signals.contains(signal)) {
//			signals.add(signal);			
//		}
//	}
	
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
	
	void AddImport(String imprt) {
		if (!imports.contains(imprt)) {
			imports.add(imprt);			
		}
	}
	
	void AddType(String type) {
		if (!types.contains(type)) {
			types.add(type);			
		}
	}
	
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

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public static String generateMux(String adr, String outp, String inputName, int inputs) {
		String behavior = "";
		if (inputs == 2) {
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			behavior += "    " + inputName + "0 WHEN \'0\'," + System.lineSeparator();
			behavior += "    " + inputName + "1 WHEN \'1\'," + System.lineSeparator();
			behavior += "    (OTHERS => '0') WHEN OTHERS;" + System.lineSeparator();
		} else if (inputs > 2) {
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			int adrSize = (int)Math.ceil(Math.log(inputs) / Math.log(2));
			for (int i = 0; i < inputs; i++) {
				behavior += "    " + inputName + i + " WHEN \"" + String.format("%" + adrSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"," + System.lineSeparator();
			}	
			behavior += "    (OTHERS => '0') WHEN OTHERS;" + System.lineSeparator();
		} else {
			behavior += "  " + outp + " <= " + inputName + "0;" + System.lineSeparator();
		}
		return behavior;
	}
	
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
	
	public List<String> getGenerics() {
		return generics;
	}

	public List<String> getPorts() {
		return ports;
	}

	public String getName() {
		return name;
	}

	public List<String> getSignals() {
		return signals;
	}
}
