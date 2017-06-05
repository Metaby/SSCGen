package microcode;

import java.util.ArrayList;
import java.util.List;

public class Microcode {
	private List<MicrocodeFunction> functions;
	private List<MicrocodeField> fields;
	
	public Microcode() {
		functions = new ArrayList<MicrocodeFunction>();
		fields = new ArrayList<MicrocodeField>();
	}

	public void addFunction(MicrocodeFunction mf) {
		if (mf != null) {
			functions.add(mf);
		}
	}
	
	public void addField(MicrocodeField field) {
		if (field != null) {
			fields.add(field);
		}
	}
	
	public List<MicrocodeFunction> getFunctions() {
		return functions;
	}

	public List<MicrocodeField> getFields() {
		return fields;
	}

}
