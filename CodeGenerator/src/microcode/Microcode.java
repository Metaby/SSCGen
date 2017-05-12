package microcode;

import java.util.ArrayList;
import java.util.List;

public class Microcode {
	private List<MicrocodeFunction> functions;
	private List<String> imports;
	
	public Microcode() {
		functions = new ArrayList<MicrocodeFunction>();
		imports = new ArrayList<String>();		
	}

	public void addFunction(MicrocodeFunction mf) {
		if (mf != null) {
			functions.add(mf);
		}
	}
	
	public void addImport(String imp) {
		if (imp != null && imp.length() > 0) {
			imports.add(imp);
		}
	}
	
	public List<MicrocodeFunction> getFunctions() {
		return functions;
	}

	public List<String> getImports() {
		return imports;
	}

}
