package de.uulm.cyv17.wrapper.entities;

import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;

public abstract class BaseEntity {
	Connector control;
	String id;
	int wordSize;
	
	public abstract void setWordSize(int wordSize);

	public Connector getControl() {
		return control;
	}
	
	public String getId() {
		return id;
	}

	public int getWordSize() {
		return wordSize;
	}
	
	public abstract ControlVector getControlVector();
}
