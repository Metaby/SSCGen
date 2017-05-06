package wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Multiplexer {

	private List<Connector> inputs;
	private Connector output;
	private Connector control;
	private String id;
	private int wordSize;
	
	Multiplexer(jaxb.Multiplexer mux) {
		id = mux.getId();
		wordSize = mux.getWordSize();
		output = new Connector(mux.getOutput(), wordSize);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < mux.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(mux.getInputs().getInput().get(i), wordSize));
		}
		control = new Connector(mux.getControl(), (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)));
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_input", (int)Math.ceil(Math.log(inputs.size()) / Math.log(2))));
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public List<Connector> getInputs() {
		return inputs;
	}
	public Connector getOutput() {
		return output;
	}

	public String getId() {
		return id;
	}

	public int getWordSize() {
		return wordSize;
	}

	public Connector getControl() {
		return control;
	}
	
}
