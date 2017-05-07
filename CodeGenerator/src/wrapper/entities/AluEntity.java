package wrapper.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ControlVector;
import wrapper.Connector;
import wrapper.Wrapper;

public class AluEntity extends BaseEntity {

	private List<Connector> inputsA;
	private List<Connector> inputsB;
	private List<String> operations;
	private List<String> conditions;
	private Connector output1;
	private Connector output2;
	private Connector status;
	
	public AluEntity(jaxb.Alu alu) {
		id = alu.getId();
		wordSize = alu.getWordSize();
		operations = new ArrayList<String>();
		for (int i = 0; i < alu.getOperations().getOperation().size(); i++) {
			operations.add(alu.getOperations().getOperation().get(i).name());
		}
		conditions = new ArrayList<String>();
		if (alu.getConditions() != null) {
			for (int i = 0; i < alu.getConditions().getCondition().size(); i++) {
				conditions.add(alu.getConditions().getCondition().get(i).name());
			}			
		}
		output1 = new Connector(alu.getOutput1(), wordSize);
		output2 = new Connector(alu.getOutput2(), wordSize);
		status = new Connector(alu.getStatus(), 1);
		inputsA = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandA().getInput().size(); i++) {
			inputsA.add(new Connector(alu.getInputsOperandA().getInput().get(i), wordSize));
		}
		inputsB = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandB().getInput().size(); i++) {
			inputsB.add(new Connector(alu.getInputsOperandB().getInput().get(i), wordSize));
		}
		control = new Connector(alu.getControl(), (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2)) + (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2)) + (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2)));
	}
	
	public ControlVector getControlVector() {
		ControlVector cv = new ControlVector(0);
		return cv;
	}

	public List<Connector> getInputsA() {
		return inputsA;
	}

	public List<Connector> getInputsB() {
		return inputsB;
	}

	public List<String> getOperations() {
		return operations;
	}

	public List<String> getConditions() {
		return conditions;
	}

	public Connector getOutput1() {
		return output1;
	}

	public Connector getOutput2() {
		return output2;
	}

	public Connector getStatus() {
		return status;
	}
}