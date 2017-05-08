package wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import tool.ControlField;
import tool.ControlVector;
import wrapper.Connector;
import wrapper.ConnectorType;
import wrapper.Port;
import wrapper.PortDirection;
import wrapper.Wrapper;

public class RegisterFileEntity extends BaseEntity {
	private List<Port> ports;
	private int addressSize;
	
	public RegisterFileEntity(jaxb.RegisterFile rf) {
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
	
	public List<Port> getPorts() {
		return ports;
	}

	public int getAddressSize() {
		return addressSize;
	}	
}
