package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;


/**
 * This class represents the rom entity.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class RomEntity extends BaseEntity{

	private List<Connector> addresses;
	private Connector output;
	private String contentFile;
	private int addressSize;
	
	/**
	 * The constructor of the rom entity. It takes
	 * an rom object of the jaxb packages and converts
	 * it into an object of the wrapper.entities 
	 * package.
	 * 
	 * @param alu the jaxb register object
	 */
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

	/**
	 * This method is used to set the word size of the entity.
	 * 
	 * @param wordSize the word size to be set
	 */
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		output.size = wordSize;
	}

	/**
	 * Returns the control vector of the entity
	 * 
	 * @return the control vector
	 */
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

	/**
	 * Returns the address connectors of the entity.
	 * 
	 * @return a list containing the address connectors
	 */
	public List<Connector> getAddresses() {
		return addresses;
	}

	/**
	 * Returns the output connector of the entity.
	 * 	
	 * @return the output connector
	 */
	public Connector getOutput() {
		return output;
	}

	/**
	 * Returns the address size of the register file entity.
	 * 
	 * @return an integer containing the address size
	 */
	public int getAddressSize() {
		return addressSize;
	}

	/**
	 * Returns the file path of the content definition of the rom entity.
	 * 
	 * @return a string containing the file path
	 */
	public String getContentFile() {
		return contentFile;
	}	
}
