package Components;

import java.util.LinkedList;
import java.util.List;

public class ALU extends BaseComponent {

	private List<String> operations;
	private List<String> conditions;
	private List<String> inputsA;
	private List<String> inputsB;
	private String output;
	
	public ALU(String Id) {
		super(Id);
		operations = new LinkedList<String>();
		conditions = new LinkedList<String>();
		inputsA = new LinkedList<String>();
		inputsB = new LinkedList<String>();
		output = "out";
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

	public List<String> getConditions() {
		return conditions;
	}

	public void setConditions(List<String> conditions) {
		this.conditions = conditions;
	}

	public List<String> getInputsA() {
		return inputsA;
	}

	public void setInputsA(List<String> inputsA) {
		this.inputsA = inputsA;
	}

	public List<String> getInputsB() {
		return inputsB;
	}

	public void setInputsB(List<String> inputsB) {
		this.inputsB = inputsB;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
