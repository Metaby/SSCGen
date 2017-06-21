package de.uulm.cyv17.wrapper.entities;

import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.tool.ControlField;
import de.uulm.cyv17.tool.ControlVector;
import de.uulm.cyv17.wrapper.Connector;
import de.uulm.cyv17.wrapper.ConnectorType;
import de.uulm.cyv17.wrapper.Wrapper;

public class AluEntity extends BaseEntity {
	private List<Connector> inputsA;
	private List<Connector> inputsB;
	private List<String> operations;
	private List<String> conditions;
	private Connector output1;
	private Connector output2;
	private Connector status;
	
	public AluEntity(de.uulm.cyv17.jaxb.Alu alu) {
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
		control = new Connector(alu.getControl(), Wrapper.log2(inputsA.size()) + Wrapper.log2(inputsB.size()) + Wrapper.log2(operations.size() + conditions.size()));
	}
	
	@Override
	public void setWordSize(int wordSize) {
		this.wordSize = wordSize;
		output1.size = wordSize;
		output2.size = wordSize;
		for (Connector c : inputsA) {
			c.size = wordSize;
		}
		for (Connector c : inputsB) {
			c.size = wordSize;
		}
	}
	
	public ControlVector getControlVector() {
		if (control.type == ConnectorType.SYSTEM_AUTO) {
			int iselASize = Wrapper.log2(inputsA.size());
			int iselBSize = Wrapper.log2(inputsB.size());
			int cselSize = Wrapper.log2(operations.size() + conditions.size());
			ControlVector cv = new ControlVector(iselASize + iselBSize + cselSize);
			if (iselASize > 0) {
				ControlField field = new ControlField(id + "_iselA", 0, iselASize - 1);
				for (int i = 0; i < inputsA.size(); i++) {
					field.addParameter(inputsA.get(i).toString(), i);		
				}
				cv.addField(field);
			}
			if (iselBSize > 0) {
				ControlField field = new ControlField(id + "_iselB", iselASize, iselASize + iselBSize - 1);
				for (int i = 0; i < inputsB.size(); i++) {
					field.addParameter(inputsB.get(i).toString(), i);		
				}
				cv.addField(field);
			}
			if (cselSize > 0) {
				ControlField field = new ControlField(id + "_csel", iselASize + iselBSize, iselASize + iselBSize + cselSize - 1);
				for (int i = 0; i < operations.size(); i++) {
					field.addParameter(operations.get(i).toString(), i);		
				}
				for (int i = 0; i < conditions.size(); i++) {
					field.addParameter(conditions.get(i), i + operations.size());
				}
				cv.addField(field);
			}
			return cv;
		}
		return new ControlVector(0);
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