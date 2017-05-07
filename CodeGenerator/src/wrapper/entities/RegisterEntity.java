package wrapper.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ControlField;
import tool.ControlVector;
import wrapper.Connector;
import wrapper.ConnectorType;
import wrapper.Wrapper;

public class RegisterEntity extends BaseEntity {
	
	private List<Connector> inputs;
	private Connector output;
	
	public RegisterEntity(jaxb.Register reg) {
		id = reg.getId();
		wordSize = reg.getSize();
		output = new Connector(reg.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < reg.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(reg.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(reg.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)) + 1);
	}

	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int iselSize = Wrapper.log2(inputs.size());
			ControlVector cv = new ControlVector(iselSize + 1);
			ControlField writeField = new ControlField(id + "_write", 0, 0);
			cv.addField(writeField);
			if (iselSize > 0) {
				ControlField iselField = new ControlField(id + "_isel", 1, iselSize);
				for (int i = 0; i < inputs.size(); i++) {
					iselField.addParameter(inputs.get(i).toString(), i);		
				}
				cv.addField(iselField);
			}
			return cv;			
		}
		return new ControlVector(0);
	}
	
	public List<Connector> getInputs() {
		return inputs;
	}

	public Connector getOutput() {
		return output;
	}
}