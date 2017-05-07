package wrapper.entities;

import tool.ControlVector;
import wrapper.Connector;

public abstract class BaseEntity {
	Connector control;
	String id;
	int wordSize;
	
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
