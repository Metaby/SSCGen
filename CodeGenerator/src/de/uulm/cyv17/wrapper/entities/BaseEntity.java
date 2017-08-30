package de.uulm.cyv17.wrapper.entities;

import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;

/**
 * This class represents the alu entity.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public abstract class BaseEntity {
	
	Connector control;
	String id;
	int wordSize;
	
	/**
	 * This method is used to set the word size of the entity.
	 * 
	 * @param wordSize the word size to be set
	 */
	public abstract void setWordSize(int wordSize);

	/**
	 * Returns the connector for the control of the entity.
	 * 
	 * @return the control connector
	 */
	public Connector getControl() {
		return control;
	}
	
	/**
	 * Returns the id of the entity.
	 * 
	 * @return a string containing the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the word size of the entity.
	 * 
	 * @return an integer containing the word size
	 */
	public int getWordSize() {
		return wordSize;
	}
	
	/**
	 * Returns the control vector of the entity
	 * 
	 * @return the control vector
	 */
	public abstract ControlVector getControlVector();
}
