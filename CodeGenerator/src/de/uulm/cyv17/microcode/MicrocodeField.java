package de.uulm.cyv17.microcode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class containing one field of the microcode with
 * its key-value pairs and start/end positions within
 * the control vector.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class MicrocodeField {
	private Map<String, Integer> fields;
	private String id;
	private int cvStart;
	private int cvEnd;
	
	/**
	 * Adds a key-value pair to the microcode field.
	 * 
	 * @param key the key
	 * @param val the value
	 */
	public void addKeyVal(String key, int val) {
		fields.put(key.trim(), val);
	}

	/**
	 * Returns the id of the microcode field.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the value of a specific key.
	 * 
	 * @param key the key
	 * @return the value
	 */
	public int getValue(String key) {
		if (fields.containsKey(key)) {
			return fields.get(key);
		} else {
			return -1;
		}
	}

	/**
	 * Returns the start of the microcode field in the control
	 * vector.
	 * 
	 * @return the start position in the control vector
	 */
	public int getCvStart() {
		return cvStart;
	}

	/**
	 * Returns the end of the microcode field in the control
	 * vector.
	 * 
	 * @return the end position in the control vector
	 */
	public int getCvEnd() {
		return cvEnd;
	}

	/**
	 * Setter for the key-value pairs of the microcode field.
	 * 
	 * @param fields the fields to be set
	 */
	public void setFields(Map<String, Integer> fields) {
		this.fields = fields;
	}

	/**
	 * Sets the start of the microcode field in the control
	 * vector.
	 * 
	 * @param cvStart the start position in the control vector
	 */
	public void setCvStart(int cvStart) {
		this.cvStart = cvStart;
	}

	/**
	 * Sets the end of the microcode field in the control
	 * vector.
	 * 
	 * @param cvEnd the end position in the control vector
	 */
	public void setCvEnd(int cvEnd) {
		this.cvEnd = cvEnd;
	}

	/**
	 * Sets the id of the microcode field. The id is referred 
	 * as the name of the field and have to be unique.
	 * 
	 * @param id the id/name of the microcode field
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Transforms the microcode field into a human readable
	 * String.
	 * 
	 * @return the human readable String
	 */
	public String toString() {
		String params = "";
		Set<String> keys = fields.keySet();
		for (String key : keys) {
			params += key + "=" + fields.get(key) + ", "; 
		}
		return id + ": " + params;
	}
}
