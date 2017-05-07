package wrapper;

import java.util.ArrayList;
import java.util.List;

public class Port {

	private PortDirection direction;
	private List<Connector> inputs;
	private List<Connector> addresses;
	private Connector output;
	
	public Port(jaxb.Port p, int wordSize, int addressSize, String parentId) {
		if (p.getType() == jaxb.PortDirection.IN) {
			direction = PortDirection.IN;
		} else if (p.getType() == jaxb.PortDirection.OUT) {
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
	
	public PortDirection getDirection() {
		return direction;
	}
	public List<Connector> getInputs() {
		return inputs;
	}
	public List<Connector> getAddresses() {
		return addresses;
	}
	public Connector getOutput() {
		return output;
	}
	
}
