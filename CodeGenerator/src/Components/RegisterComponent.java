package Components;

import java.util.LinkedList;
import java.util.List;

public class RegisterComponent extends BaseComponent {

	private List<String> inputs;
	private String output;
	
	public RegisterComponent(String Id) {
		super(Id);
		inputs = new LinkedList<String>();
		output = "out";
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
