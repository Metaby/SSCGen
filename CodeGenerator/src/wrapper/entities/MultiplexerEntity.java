package wrapper.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ControlVector;
import wrapper.Connector;
import wrapper.Wrapper;

public class MultiplexerEntity extends BaseEntity {

	private List<Connector> inputs;
	private Connector output;
	
	public MultiplexerEntity(jaxb.Multiplexer mux) {
		id = mux.getId();
		wordSize = mux.getWordSize();
		output = new Connector(mux.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < mux.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(mux.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(mux.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)));
	}
	
	public ControlVector getControlVector() {
		ControlVector cv = new ControlVector(0);
		return cv;
	}
	
	public List<Connector> getInputs() {
		return inputs;
	}
	
	public Connector getOutput() {
		return output;
	}
}
