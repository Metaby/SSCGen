package de.uulm.cyv17.tool;

import java.util.*;

/**
 * A class containing information for a control field
 * which is part of a complete control vector.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class ControlField {
	private Map<String, Integer> parameters;
	private String name;
	private int start;
	private int size;
	private int end;
	
	/**
	 * The constructor of the control field. To be created
	 * the name, start on the control vector and end on the
	 * control vector are needed.
	 * 
	 * @param name the name of the control field
	 * @param start the starting position on the control vector
	 * @param end the ending position on the control vector
	 */
	public ControlField(String name, int start, int end) {
		parameters = new HashMap<String, Integer>();
		this.name = name;
		this.start = start;
		this.end = end;
		this.size = end - start + 1;
	}
	
	/**
	 * Adds a key-value pair to the control field. These parameters
	 * define the values which can be assigned to the part of the
	 * control vector this field is belonging to. The keys are used
	 * to assign the values.
	 * 
	 * @param key the key for the value
	 * @param value the value belonging to the key
	 */
	public void addParameter(String key, int value) {
		if (!parameters.containsKey(key)) {
			parameters.put(key, value);
		}
	}
	
	/**
	 * Moves the start and end position of this control field on the
	 * control vector by a given offset.
	 * 
	 * @param offset the offeset to be used
	 */
	public void move(int offset) {
		start += offset;
		end += offset;
	}

	/**
	 * Returns the key-value parameters defining the control field.
	 * 
	 * @return a map containing the parameters as string-integer pairs
	 */
	public Map<String, Integer> getParameters() {
		return parameters;
	}

	/**
	 * Returns the start of the control field on the control vector.
	 * 
	 * @return an integer containing the starting position
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Returns the size of the control field on the control vector.
	 * 
	 * @return an integer containing the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the end of the control field on the control vector.
	 * 
	 * @return an integer containing the ending position
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Returns the name of the control field.
	 * 
	 * @return a string containing the name
	 */
	public String getName() {
		return name;
	}
}
