package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uulm.cyv17.jaxb.PortDirection;
import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;

public class CustomEntity extends BaseEntity {
	private Map<String, Integer> generics;
	private List<Connector> inputConnectors;	
	private List<Connector> outputConnectors;
	private String filePath;
	private int wordSize;
	
	public CustomEntity(de.uulm.cyv17.jaxb.Custom custom) {
		id = custom.getId();
		filePath = custom.getDefinition();
		generics = new HashMap<String, Integer>();
		inputConnectors = new ArrayList<Connector>();
		outputConnectors = new ArrayList<Connector>();
		if (custom.getGenerics() != null) {
			for (de.uulm.cyv17.jaxb.Generic g : custom.getGenerics().getGeneric()) {
				generics.put(g.getName(), g.getSize());
			}			
		}
		for (de.uulm.cyv17.jaxb.Connector c : custom.getConnectors().getConnector()) {
			if (c.getType() == PortDirection.IN) {
				if (c.getName().equals("system.auto")) {
					control = new Connector(c.getName(), c.getSize());
					inputConnectors.add(control);
				} else {
					inputConnectors.add(new Connector(c.getName(), c.getSize()));						
				}
			} else {
				outputConnectors.add(new Connector(c.getName(), c.getSize()));				
			}
		}
	}

	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}

	@Override
	public ControlVector getControlVector() {
		if (control != null) {
			ControlVector cv = new ControlVector(control.size);
			cv.addField(new ControlField(id + "_custom", 0, control.size));
			return cv;
		}
		return new ControlVector(0);
	}

	public List<Connector> getInputConnectors() {
		return inputConnectors;
	}

	public List<Connector> getOutputConnectors() {
		return outputConnectors;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getWordSize() {
		return wordSize;
	}
	
	public Map<String, Integer> getGenerics() {
		return generics;
	}

}
