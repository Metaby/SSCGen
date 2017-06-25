package de.uulm.cyv17.microcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing one microcode function with its
 * lines of code, position, name and virtual status.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class MicrocodeFunction {
	private List<String> funcRow;
	private Boolean virtual;
	private String name;
	private int position;

	/**
	 * Constructor for the microcode function which takes
	 * a Boolean specifying if the microcode function is
	 * virtual or not.
	 * 
	 * If the function is virtual, it will be only used to solve calls
	 * in the microcode and will NOT be translated into the hex-file.
	 * This is to give convenience while saving bytes at the hex-file.
	 * 
	 * @param isVirtual true if the function is virtual, false otherwise
	 */
	public MicrocodeFunction(Boolean isVirtual) {
		this.funcRow = new ArrayList<String>();
		this.name = "";
		this.position = -1;
		this.virtual = isVirtual;
	}
	
	/**
	 * Adds the given code line to the microcode function.
	 * 
	 * @param code the code to be added
	 */
	public void addFunctionLine(String code) {
		funcRow.add(code);
	}

	/**
	 * Returns all code lines of the microcode function.
	 * 
	 * @return the code
	 */
	public List<String> getFunctionLines() {
		return funcRow;
	}

	/**
	 * Returns the name of the microcode function.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the position of the microcode function.
	 * 
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the name of the microcode function.
	 * 
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the position of the microcode function.
	 * The position is given as a Hex-String and will be
	 * parsed into an integer. If an empty String is given
	 * the position will be set to -1.
	 * 
	 * @param position the position as a hexadecimal String
	 */
	public void setPosition(String position) {
		if (position.length() > 0) {
			this.position = Integer.parseInt(position.substring(2), 16);			
		} else {
			this.position = -1;
		}
	}
	
	/**
	 * Sets the position of the microcode function.
	 * 
	 * @param position the position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Returns wether the microcode function is virtual or not.
	 * 
	 * @return true if the function is virtual, false otherwise
	 */
	public Boolean isVirtual() {
		return virtual;
	}
}