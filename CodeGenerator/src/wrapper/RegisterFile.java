package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.VhdlComponent;

public class RegisterFile {

	private List<Port> ports;
	private String id;
	private int addressSize;
	private int wordSize;
	
	public RegisterFile(jaxb.RegisterFile rf) {
		id = rf.getId();
		addressSize = rf.getAddressSize();
		wordSize = rf.getWordSize();
		ports = new ArrayList<Port>();
		for (int i = 0; i < rf.getPorts().getPort().size(); i++) {
			ports.add(new Port(rf.getPorts().getPort().get(i), wordSize, addressSize, id));
		}
	}

	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int j = 0; j < ports.size(); j++) {
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_isel", (int)Math.ceil(Math.log(ports.get(j).getInputs().size()) / Math.log(2))));
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_asel", (int)Math.ceil(Math.log(ports.get(j).getAddresses().size()) / Math.log(2))));
			if (ports.get(j).getDirection() == PortDirection.IN || ports.get(j).getDirection() == PortDirection.IN_OUT) {
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
	
}
