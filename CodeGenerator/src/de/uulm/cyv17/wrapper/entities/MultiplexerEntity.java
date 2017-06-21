package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;

public class MultiplexerEntity extends BaseEntity {
	private List<Connector> inputs;
	private Connector output;
	
	public MultiplexerEntity(de.uulm.cyv17.jaxb.Multiplexer mux) {
		id = mux.getId();
		wordSize = mux.getWordSize();
		output = new Connector(mux.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < mux.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(mux.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(mux.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)));
	}
	
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		for (Connector c : inputs) {
			c.size = wordSize;
		}
	}
	
	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int iselSize = Wrapper.log2(inputs.size());
			ControlVector cv = new ControlVector(iselSize);
			if (iselSize > 0) {
				ControlField iselField = new ControlField(id + "_isel", 0, iselSize - 1);
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
