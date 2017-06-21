package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;

public class RomEntity extends BaseEntity{

	private List<Connector> addresses;
	private Connector output;
	private String contentFile;
	private int addressSize;
	
	public RomEntity(de.uulm.cyv17.jaxb.Rom rom) {
		id = rom.getId();
		contentFile = rom.getContentFile();
		addressSize = rom.getAddressSize();
		wordSize = rom.getWordSize();
		output = new Connector(rom.getOutput(), wordSize);
		addresses = new ArrayList<Connector>();
		for (int i = 0; i < rom.getAddresses().getAddress().size(); i++) {
			addresses.add(new Connector(rom.getAddresses().getAddress().get(i), addressSize));
		}	
		control = new Connector(rom.getControl(), (int)Math.ceil(Math.log(addresses.size()) / Math.log(2)));	
	}
	
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		output.size = wordSize;
	}
	
	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int aselSize = Wrapper.log2(addresses.size());
			ControlVector cv = new ControlVector(aselSize);
			if (aselSize > 0) {
				ControlField aselField = new ControlField(id + "_asel", 0, aselSize - 1);
				for (int i = 0; i < addresses.size(); i++) {
					aselField.addParameter(addresses.get(i).toString(), i);		
				}
				cv.addField(aselField);
			}
			return cv;			
		}
		return new ControlVector(0);
	}
	
	public List<Connector> getAddresses() {
		return addresses;
	}

	public Connector getOutput() {
		return output;
	}

	public int getAddressSize() {
		return addressSize;
	}
	
	public String getContentFile() {
		return contentFile;
	}	
}
