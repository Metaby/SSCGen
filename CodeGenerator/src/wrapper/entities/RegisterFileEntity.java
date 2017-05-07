package wrapper.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ControlVector;
import wrapper.Connector;
import wrapper.Port;
import wrapper.PortDirection;
import wrapper.Wrapper;

public class RegisterFileEntity extends BaseEntity {

	private List<Port> ports;
	private int addressSize;
	
	public RegisterFileEntity(jaxb.RegisterFile rf) {
		id = rf.getId();
		addressSize = rf.getAddressSize();
		wordSize = rf.getWordSize();
		ports = new ArrayList<Port>();
		int ctrlSize = 0;
		for (int i = 0; i < rf.getPorts().getPort().size(); i++) {
			Port p = new Port(rf.getPorts().getPort().get(i), wordSize, addressSize, id);
			ports.add(p);
			if (p.getDirection() == PortDirection.IN) {
				ctrlSize += log2(p.getAddresses().size());
				ctrlSize += log2(p.getInputs().size());
				ctrlSize++;
			} else {
				ctrlSize += log2(p.getAddresses().size());				
			}
		}
		control = new Connector(rf.getControl(), ctrlSize);
	}
	
	private int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
	
	public ControlVector getControlVector() {
		ControlVector cv = new ControlVector(0);
		return cv;
	}
	
	public List<Port> getPorts() {
		return ports;
	}

	public int getAddressSize() {
		return addressSize;
	}	
}
