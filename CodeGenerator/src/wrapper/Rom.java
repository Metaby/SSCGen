package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.VhdlComponent;

public class Rom {

	private List<Connector> addresses;
	private Connector output;
	private String contentFile;
	private String id;
	private int addressSize;
	private int wordSize;
	
	public Rom(jaxb.Rom rom) {
		id = rom.getId();
		contentFile = rom.getContentFile();
		addressSize = rom.getAddressSize();
		wordSize = rom.getWordSize();
		output = new Connector(rom.getOutput(), wordSize);
		addresses = new ArrayList<Connector>();
		for (int i = 0; i < rom.getAddresses().getAddress().size(); i++) {
			addresses.add(new Connector(rom.getAddresses().getAddress().get(i), addressSize));
		}		
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_asel", (int)Math.ceil(Math.log(addresses.size()) / Math.log(2))));
		cv.add(id + "_write");
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public List<Connector> getAddresses() {
		return addresses;
	}

	public Connector getOutput() {
		return output;
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

	public String getContentFile() {
		return contentFile;
	}
	
}
