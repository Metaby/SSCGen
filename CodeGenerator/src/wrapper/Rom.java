package wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		Connector outCon = new Connector();
		outCon.origin = rom.getId();
		outCon.pin = rom.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		addresses = new ArrayList<Connector>();
		for (int i = 0; i < rom.getAddresses().getAddress().size(); i++) {
			Connector inCon = new Connector();
			String input = rom.getAddresses().getAddress().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = addressSize;
			inCon.id = Connector.getNewId();
			addresses.add(inCon);
		}		
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
//		for (int i = 0; i < (int)Math.ceil(Math.log(addresses.size()) / Math.log(2)); i++) {
//			cv.add(id + "_asel_" + i);
//		}
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
