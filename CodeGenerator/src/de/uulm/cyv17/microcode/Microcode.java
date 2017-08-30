package de.uulm.cyv17.microcode;

import java.util.ArrayList;
import java.util.List;

/**
 * General class for containing the complete microcode
 * with its fields and functions of a specific
 * computer architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 */
public class Microcode {
	private List<MicrocodeFunction> functions;
	private List<MicrocodeField> fields;
	
	/**
	 * The constructor of the Microcode class
	 */
	public Microcode() {
		functions = new ArrayList<MicrocodeFunction>();
		fields = new ArrayList<MicrocodeField>();
	}

	/**
	 * Adds the given MicrocodeFunction to the Microcode.
	 * 
	 * @param mf the MicrocodeFunction to add
	 */
	public void addFunction(MicrocodeFunction mf) {
		if (mf != null) {
			functions.add(mf);
		}
	}
	
	/**
	 * Adds the given MicrocodeField to the Microcode.
	 * 
	 * @param field the MicrocodeField to add
	 */
	public void addField(MicrocodeField field) {
		if (field != null) {
			fields.add(field);
		}
	}
	
	/**
	 * Returns all functions of the Microcode which have been added before.
	 * 
	 * @return a List<> of MicrocodeFuntion
	 */
	public List<MicrocodeFunction> getFunctions() {
		return functions;
	}

	/**
	 * Returns all fields of the Microcode which have been added before.
	 * 
	 * @return a List<> of MicrocodeField
	 */
	public List<MicrocodeField> getFields() {
		return fields;
	}

}
