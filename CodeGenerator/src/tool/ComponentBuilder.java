package tool;
import java.util.ArrayList;
import java.util.List;

public class ComponentBuilder {

	private List<String> libraries;
	private List<String> generics;
	private List<String> signals;
	private List<String> imports;
	private List<String> types;
	private List<String> ports;
	private String behavior;
	private String name;
	private String nl;
	
	public ComponentBuilder(String name) {
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

	public void AddLibrary(String library) {
		libraries.add(library);
	}
	
	public void AddGeneric(String generic) {
		generics.add(generic);
	}

	public void AddPort(String port) {
		ports.add(port);
	}
	
	public void AddSignal(String signal) {
		signals.add(signal);
	}
	
	public void AddImport(String imprt) {
		imports.add(imprt);
	}
	
	public void AddType(String type) {
		types.add(type);
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
			component.append("  GENERIC" + nl + "  (" + nl);
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
			component.append("  PORT" + nl + "  (" + nl);
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
		if (signals.size() > 0) {
			for (int i = 0; i < signals.size(); i++) {
				component.append("  SIGNAL " + signals.get(i) + ";" + nl);
			}
		}
		if (types.size() > 0) {
			for (int i = 0; i < types.size(); i++) {
				component.append("  TYPE " + types.get(i) + ";" + nl);
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
			behavior += "  -- input multiplexer" + System.lineSeparator();
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			behavior += "    " + inputName + "0 WHEN \'0\'," + System.lineSeparator();
			behavior += "    " + inputName + "1 WHEN \'1\';" + System.lineSeparator();
		} else if (inputs > 2) {
			behavior += "  -- input multiplexer" + System.lineSeparator();		
			behavior += "  WITH " + adr + " SELECT " + outp + " <=" + System.lineSeparator();
			int adrSize = (int)Math.ceil(Math.log(inputs) / Math.log(2));
			for (int i = 0; i < inputs; i++) {
				behavior += "    " + inputName + i + " WHEN \"" + String.format("%" + adrSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"";
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

	public static String generateIf(String condition, String ifCmd, String elseCmd, String elseIfCondition[], String elseIfCmd[]) {
		return generateIf(condition, ifCmd, elseCmd, elseIfCondition, elseIfCmd, 0);
	}
	
	public static String generateIf(String ifCondition, String ifCmd, String elseCmd, String elseIfCondition[], String elseIfCmd[], int indent) {
		String ifStr = "";
		String indentStr = "";
		Boolean complexCmd = (ifCmd.split("\n").length > 1);
		while (indent-- > 0) indentStr += " ";
		ifStr += indentStr + "IF " + ifCondition + " THEN" + System.lineSeparator();
		ifStr += indentStr + (complexCmd ? "" : "  ") + ifCmd + (complexCmd ? "" : ";") + System.lineSeparator();
		if (elseIfCondition != null && elseIfCmd != null && elseIfCondition.length == elseIfCmd.length) {
			for (int i = 0; i < elseIfCondition.length; i++) {
				complexCmd =(elseIfCmd[i].split("\n").length > 1);
				ifStr += indentStr + "ELSIF " + elseIfCondition[i] + " THEN" + System.lineSeparator();
				ifStr += indentStr + (complexCmd ? "" : "  ") + elseIfCmd[i] + (complexCmd ? "" : ";") + System.lineSeparator();
			}
		}
		complexCmd = (elseCmd.split("\n").length > 1);
		if (elseCmd != null && !elseCmd.equals("")) {
			ifStr += indentStr + "ELSE" + System.lineSeparator();
			ifStr += indentStr + (complexCmd ? "" : "  ") + elseCmd + (complexCmd ? "" : ";") + System.lineSeparator();
		}
		ifStr += indentStr + "END IF;";
		return ifStr;
	}
}
