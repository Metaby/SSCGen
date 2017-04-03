package Components;

import java.util.LinkedList;
import java.util.List;

public class RegisterFile extends BaseComponent {

	private List<Port> ports;
	private int addressSize;
	
	public RegisterFile(String Id) {
		super(Id);
		ports = new LinkedList<Port>();
		addressSize = 0;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	public int getAddressSize() {
		return addressSize;
	}

	public void setAddressSize(int addressSize) {
		this.addressSize = addressSize;
	}

}
