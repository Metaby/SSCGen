package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;

public class CustomEntity extends BaseEntity {	
	private List<Connector> inputConnectors;	
	private List<Connector> outputConnectors;
	private Connector control;
	private Boolean hasClock;
	private Boolean hasControl;
	private String filePath;
	private int wordSize;
	
	public CustomEntity() {
		inputConnectors = new ArrayList<Connector>();
		outputConnectors = new ArrayList<Connector>();
		hasClock = false;
		hasControl = false;
	}

	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}

	@Override
	public ControlVector getControlVector() {
		if (hasControl && control.type == ConnectorType.SYSTEM_AUTO ) {
			ControlVector cv = new ControlVector(control.size);
			cv.addField(new ControlField(id + "_custom", 0, control.size));
			return cv;
		}
		return new ControlVector(0);
	}

}
