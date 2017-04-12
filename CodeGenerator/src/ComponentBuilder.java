import java.util.ArrayList;
import java.util.List;

public class ComponentBuilder {

	private List<String> libraries;
	private List<String> generics;
	private List<String> signals;
	private List<String> ports;
	private String behavior;
	private String name;
	private String nl;
	
	public ComponentBuilder(String name) {
		libraries = new ArrayList<String>();
		generics = new ArrayList<String>();
		signals = new ArrayList<String>();
		ports = new ArrayList<String>();
		this.name = name;
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
		if (signals.size() > 0) {
			for (int i = 0; i < signals.size(); i++) {
				component.append("  " + signals.get(i) + ";" + nl);
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
}
