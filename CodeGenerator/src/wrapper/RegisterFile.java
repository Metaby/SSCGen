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

	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int j = 0; j < ports.size(); j++) {
			for (int i = 0; i < (int)Math.ceil(Math.log(ports.get(j).getInputs().size()) / Math.log(2)); i++) {
				cv.add(id + "_p_isel_" + i);
			}
			for (int i = 0; i < (int)Math.ceil(Math.log(ports.get(j).getAddresses().size()) / Math.log(2)); i++) {
				cv.add(id + "_p_asel_" + i);
			}
			if (ports.get(j).getDirection() == PortDirection.IN || ports.get(j).getDirection() == PortDirection.IN_OUT) {
				cv.add(id + "_p" + j + "_write");				
			}
		}
		return cv;
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
