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

/**
 * This class represents the custom entity.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class CustomEntity extends BaseEntity {
	private Map<String, Integer> generics;
	private List<Connector> inputConnectors;	
	private List<Connector> outputConnectors;
	private String filePath;
	private String name;
	
	/**
	 * The constructor of the custom entity. It takes
	 * an custom object of the jaxb packages and converts
	 * it into an object of the wrapper.entities 
	 * package.
	 * 
	 * @param custom the jaxb custom object
	 */
	public CustomEntity(de.uulm.cyv17.jaxb.Custom custom) {
		id = custom.getId();
		filePath = custom.getDefinition();
		name = filePath;
		if (name.contains("/")) {
			name = name.substring(0, name.lastIndexOf("/"));
		}
		if (name.contains("\\")) {
			name = name.substring(0, name.lastIndexOf("\\"));			
		}
		if (name.contains(".")) {
			name = name.substring(0, name.indexOf("."));			
		}
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

	/**
	 * Returns the name of the custom entity.
	 * 
	 * @return a string containing the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is used to set the word size of the entity.
	 * 
	 * @param wordSize the word size to be set
	 */
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
	}
	/**
	 * Returns the control vector of the entity
	 * 
	 * @return the control vector
	 */
	@Override
	public ControlVector getControlVector() {
		if (control != null) {
			ControlVector cv = new ControlVector(control.size);
			cv.addField(new ControlField(id + "_custom", 0, control.size - 1));
			return cv;
		}
		return new ControlVector(0);
	}

	/**
	 * Returns the input connectors of the custom entity.
	 * 
	 * @return a list containing the input connectors
	 */
	public List<Connector> getInputConnectors() {
		return inputConnectors;
	}
	
	/**
	 * Returns the output connectors of the custom entity.
	 * 
	 * @return a list containing the output connectors
	 */
	public List<Connector> getOutputConnectors() {
		return outputConnectors;
	}

	/**
	 * Returns the file path of the vhdl definition of the custom entity.
	 * 
	 * @return a string containing the file path
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Returns the generics of the custom entity.
	 * 
	 * @return a map containing the string-integer pairs of the generics
	 */
	public Map<String, Integer> getGenerics() {
		return generics;
	}

}
