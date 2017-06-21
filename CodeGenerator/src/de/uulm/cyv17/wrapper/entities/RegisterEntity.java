package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;

public class RegisterEntity extends BaseEntity {
	
	private List<Connector> inputs;
	private Connector output;
	
	public RegisterEntity(de.uulm.cyv17.jaxb.Register reg) {
		id = reg.getId();
		wordSize = reg.getSize();
		output = new Connector(reg.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < reg.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(reg.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(reg.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)) + 1);
	}
	
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		output.size = wordSize;
		for (Connector c : inputs) {
			c.size = wordSize;
		}
	}

	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int iselSize = Wrapper.log2(inputs.size());
			ControlVector cv = new ControlVector(iselSize + 1);
			ControlField writeField = new ControlField(id + "_write", 0, 0);
			writeField.addParameter("H", 1);
			writeField.addParameter("L", 0);
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
