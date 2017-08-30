package de.uulm.cyv17.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the ports used in
 * the register file entity class.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class Port {

	private PortDirection direction;
	private List<Connector> inputs;
	private List<Connector> addresses;
	private Connector output;
	
	/**
	 * The constructor of the port class. It takes the word size,
	 * address size and parent id. Beside this, it takes a port
	 * object of the jaxb package and converts it into an object
	 * of the wrapper port class.
	 * 
	 * @param p the jaxb port object
	 * @param wordSize the word size
	 * @param addressSize the address size
	 * @param parentId the id of the parent entity which uses this port
	 */
	public Port(de.uulm.cyv17.jaxb.Port p, int wordSize, int addressSize, String parentId) {
		if (p.getType() == de.uulm.cyv17.jaxb.PortDirection.IN) {
			direction = PortDirection.IN;
		} else if (p.getType() == de.uulm.cyv17.jaxb.PortDirection.OUT) {
			direction = PortDirection.OUT;
		}
		if (p.getOutput() != null) {
			output = new Connector(p.getOutput(), wordSize);			
		}
		inputs = new ArrayList<Connector>();
		if (p.getInputs() != null) {
			for (int i = 0; i < p.getInputs().getInput().size(); i++) {
				inputs.add(new Connector(p.getInputs().getInput().get(i), wordSize));			
			}
		}
		addresses = new ArrayList<Connector>();
		if (p.getAddresses() != null) {
			for (int i = 0; i < p.getAddresses().getAddress().size(); i++) {
				addresses.add(new Connector(p.getAddresses().getAddress().get(i), addressSize));
			}			
		}
	}
	
	/**
	 * Returns the direction of the port.
	 * 
	 * @return an enum object containing the direction information
	 */
	public PortDirection getDirection() {
		return direction;
	}
	
	/**
	 * Returns the input connectors of the port.
	 * 
	 * @return a list containing the input connectors
	 */
	public List<Connector> getInputs() {
		return inputs;
	}

	/**
	 * Returns the address connectors of the port.
	 * 
	 * @return a list containing the address connectors
	 */
	public List<Connector> getAddresses() {
		return addresses;
	}
	
	/**
	 * Returns the output connector of the port.
	 * 
	 * @return the output connector
	 */
	public Connector getOutput() {
		return output;
	}
	
}
