package wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stack {
	
	private List<Connector> inputs;
	private Connector output;
	private String id;
	private int size;
	
	public Stack(jaxb.Stack stk) {
		id = stk.getId();
		size = stk.getSize();
		Connector outCon = new Connector();
		outCon.origin = stk.getId();
		outCon.pin = stk.getOutput();
		outCon.size = size;
		outCon.id = Connector.getNewId();
		output = outCon;
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < stk.getInputs().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = stk.getInputs().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = size;
			inCon.id = Connector.getNewId();
			inputs.add(inCon);
		}
	}

	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_isel", (int)Math.ceil(Math.log(inputs.size()) / Math.log(2))));
//		for (int i = 0; i < (int)Math.ceil(Math.log(inputs.size()) / Math.log(2)); i++) {
//			cv.add(id + "_isel_" + i);
//		}
		cv.add(id + "_incr");
		cv.add(id + "_decr");
		cv.add(id + "_write");
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

	public int getSize() {
		return size;
	}
	
}