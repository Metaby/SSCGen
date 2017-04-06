package wrapper;

import java.util.ArrayList;
import java.util.List;

public class RegisterFile {

	private List<Port> ports;
	private String id;
	private int addressSize;
	private int wordSize;
	
	public RegisterFile(jaxb.RegisterFile rf) {
		id = rf.getId();
		addressSize = rf.getAddressSize();
		addressSize = rf.getWordSize();
		ports = new ArrayList<Port>();
		for (int i = 0; i < rf.getPorts().getPort().size(); i++) {
			ports.add(new Port(rf.getPorts().getPort().get(i), addressSize, wordSize, id));
		}
	}

	public List<Port> getPorts() {
		return ports;
	}

	public String getId() {
		return id;
	}

	public int getAddressSize() {
		return addressSize;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
