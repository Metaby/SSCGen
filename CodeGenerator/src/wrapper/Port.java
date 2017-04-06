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
		} else {
			direction = PortDirection.IN_OUT;
		}
		if (p.getOutput() != null) {
			Connector outCon = new Connector();
			outCon.origin = parentId;
			outCon.pin = p.getOutput();
			outCon.size = wordSize;
			outCon.id = Connector.getNewId();
			output = outCon;			
		}
		inputs = new ArrayList<Connector>();
		if (p.getInputs() != null) {
			for (int i = 0; i < p.getInputs().getInput().size(); i++) {
				Connector inCon = new Connector();
				String input = p.getInputs().getInput().get(i);
				inCon.origin = input.substring(0, input.indexOf("."));
				inCon.pin = input.substring(input.indexOf(".") + 1);
				inCon.size = wordSize;
				inCon.id = Connector.getNewId();
				inputs.add(inCon);			
			}
		}
		addresses = new ArrayList<Connector>();
		if (p.getAddresses() != null) {
			for (int i = 0; i < p.getAddresses().getAddress().size(); i++) {
				Connector inCon = new Connector();
				String input = p.getAddresses().getAddress().get(i);
				inCon.origin = input.substring(0, input.indexOf("."));
				inCon.pin = input.substring(input.indexOf(".") + 1);
				inCon.size = addressSize;
				inCon.id = Connector.getNewId();
				addresses.add(inCon);
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
