package wrapper;

import java.util.ArrayList;
import java.util.List;

public class Alu {

	private List<Connector> inputsA;
	private List<Connector> inputsB;
	private List<String> operations;
	private List<String> conditions;
	private Connector output;
	private Connector status;
	private String id;
	private int wordSize;
	
	public Alu(jaxb.Alu alu) {
		id = alu.getId();
		wordSize = alu.getWordSize();
		operations = new ArrayList<String>();
		for (int i = 0; i < alu.getOperations().getOperation().size(); i++) {
			operations.add(alu.getOperations().getOperation().get(i).name());
		}
		int conditionsCnt = 0;
		conditions = new ArrayList<String>();
		for (int i = 0; i < alu.getConditions().getCondition().size(); i++) {
			conditions.add(alu.getConditions().getCondition().get(i).name());
		}
		Connector outCon = new Connector();
		outCon.origin = alu.getId();
		outCon.pin = alu.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		Connector flagsCon = new Connector();
		flagsCon.origin = alu.getId();
		flagsCon.pin = alu.getStatusFlags();
		flagsCon.size = conditionsCnt;
		flagsCon.id = Connector.getNewId();
		status = flagsCon;
		inputsA = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandA().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = alu.getInputsOperandA().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			inputsA.add(inCon);
		}
		inputsB = new ArrayList<Connector>();
		for (int i = 0; i < alu.getInputsOperandB().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = alu.getInputsOperandB().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			inputsB.add(inCon);
		}
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int i = 0; i < (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2)); i++) {
			cv.add(id + "_op1_isel_" + i);
		}
		for (int i = 0; i < (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2)); i++) {
			cv.add(id + "_op2_isel_" + i);
		}
		for (int i = 0; i < (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2)); i++) {
			cv.add(id + "_csel_" + i);			
		}
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

	public Connector getOutput() {
		return output;
	}

	public Connector getStatus() {
		return status;
	}

	public String getId() {
		return id;
	}

	public int getWordSize() {
		return wordSize;
	}
}
