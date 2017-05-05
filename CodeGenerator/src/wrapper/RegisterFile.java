package wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterFile {

	private List<Port> ports;
	private Connector control;
	private String id;
	private int addressSize;
	private int wordSize;
	
	RegisterFile(jaxb.RegisterFile rf) {
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
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int j = 0; j < ports.size(); j++) {
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_isel", (int)Math.ceil(Math.log(ports.get(j).getInputs().size()) / Math.log(2))));
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_asel", (int)Math.ceil(Math.log(ports.get(j).getAddresses().size()) / Math.log(2))));
			if (ports.get(j).getDirection() == PortDirection.IN) {
				cv.add(id + "_p" + j + "_write");				
			}
		}
		cv.removeAll(Arrays.asList("", null));
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

	public Connector getControl() {
		return control;
	}
	
}
