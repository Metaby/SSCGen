package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Port;
import de.uulm.cyv17.wrapper.PortDirection;
import de.uulm.cyv17.wrapper.Wrapper;

/**
 * This class represents the register file entity.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class RegisterFileEntity extends BaseEntity {
	
	private List<Port> ports;
	private int addressSize;
	
	/**
	 * The constructor of the register file entity. It takes
	 * an register file object of the jaxb packages and converts
	 * it into an object of the wrapper.entities 
	 * package.
	 * 
	 * @param alu the jaxb register file object
	 */
	public RegisterFileEntity(de.uulm.cyv17.jaxb.RegisterFile rf) {
		id = rf.getId();
		addressSize = rf.getAddressSize();
		wordSize = rf.getWordSize();
		ports = new ArrayList<Port>();
		int ctrlSize = 0;
		for (int i = 0; i < rf.getPorts().getPort().size(); i++) {
			Port p = new Port(rf.getPorts().getPort().get(i), wordSize, addressSize, id);
			ports.add(p);
			if (p.getDirection() == PortDirection.IN) {
				ctrlSize += Wrapper.log2(p.getAddresses().size());
				ctrlSize += Wrapper.log2(p.getInputs().size());
				ctrlSize++;
			} else {
				ctrlSize += Wrapper.log2(p.getAddresses().size());				
			}
		}
		control = new Connector(rf.getControl(), ctrlSize);
	}

	/**
	 * This method is used to set the word size of the entity.
	 * 
	 * @param wordSize the word size to be set
	 */
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		for (Port p : ports) {
			if (p.getDirection() == PortDirection.IN) {
				for (Connector c : p.getInputs()) {
					c.size = wordSize;
				}
			} else {
				p.getOutput().size = wordSize;
			}
		}
	}

	/**
	 * Returns the control vector of the entity
	 * 
	 * @return the control vector
	 */
	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int cvSize = 0;
			for (int i = 0; i < ports.size(); i++) {
				if (ports.get(i).getDirection() == PortDirection.IN) {
					cvSize += Wrapper.log2(ports.get(i).getInputs().size());
					cvSize += Wrapper.log2(ports.get(i).getAddresses().size());
					cvSize++;
				} else {
					cvSize += Wrapper.log2(ports.get(i).getAddresses().size());					
				}
			}
			int offset = 0;
			ControlVector cv = new ControlVector(cvSize);
			for (int i = 0; i < ports.size(); i++) {
				if (ports.get(i).getDirection() == PortDirection.IN) {
					int iselSize = Wrapper.log2(ports.get(i).getInputs().size());
					int aselSize = Wrapper.log2(ports.get(i).getAddresses().size());
					if (iselSize > 0) {
						ControlField iselField = new ControlField(id + "_port" + i + "_isel", offset, offset + iselSize - 1);
						offset += iselSize;
						for (int j = 0; j < ports.get(i).getInputs().size(); j++) {
							iselField.addParameter(ports.get(i).getInputs().get(j).toString(), j);		
						}
						cv.addField(iselField);
					}
					if (aselSize > 0) {
						ControlField aselField = new ControlField(id + "_port" + i + "_asel", offset, offset + aselSize - 1);
						offset += aselSize;
						for (int j = 0; j < ports.get(i).getAddresses().size(); j++) {
							aselField.addParameter(ports.get(i).getAddresses().get(j).toString(), j);		
						}
						cv.addField(aselField);
					}
					ControlField writeField = new ControlField(id + "_port" + i + "_write", offset, offset);
					writeField.addParameter("H", 1);
					writeField.addParameter("L", 0);
					offset++;
					cv.addField(writeField);
				} else {
					int aselSize = Wrapper.log2(ports.get(i).getAddresses().size());
					if (aselSize > 0) {
						ControlField aselField = new ControlField(id + "_port" + i + "_asel", offset, offset + aselSize - 1);
						offset += aselSize;
						for (int j = 0; j < ports.get(i).getAddresses().size(); j++) {
							aselField.addParameter(ports.get(i).getAddresses().get(j).toString(), j);		
						}
						cv.addField(aselField);
					}					
				}
			}
			return cv;			
		}
		return new ControlVector(0);
	}
	
	/**
	 * Returns the ports of the register file entity.
	 * 
	 * @return a list containing the ports
	 */
	public List<Port> getPorts() {
		return ports;
	}

	/**
	 * Returns the address size of the register file entity.
	 * 
	 * @return an integer containing the address size
	 */
	public int getAddressSize() {
		return addressSize;
	}	
}
