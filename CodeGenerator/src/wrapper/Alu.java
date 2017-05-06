package wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.VhdlComponent;

public class Alu {

	private List<Connector> inputsA;
	private List<Connector> inputsB;
	private List<String> operations;
	private List<String> conditions;
	private Connector output1;
	private Connector output2;
	private Connector status;
	private Connector control;
	private String id;
	private int wordSize;
	
	Alu(jaxb.Alu alu) {
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
		if (alu.getStatus() != null) {
			status = new Connector(alu.getStatus(), 1);			
		}
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
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_op1", (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_op2", (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_csel", (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2))));
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}

	
	
	public Boolean[] getNeededComponents() {
		Boolean neededComponents[] = new Boolean[] { false, false, false, false, false, false };
		for (String op : operations) {
			if (op.matches("ADD|ADD_U|SUB|SUB_U")) {
				neededComponents[0] = true;
			}
			if (op.matches("AND|OR|XOR|NOT")) {
				neededComponents[1] = true;
			}
			if (op.matches("DIV|DIV_U")) {
				neededComponents[3] = true;
			}
			if (op.matches("MUL|MUL_U")) {
				neededComponents[4] = true;
			}
			if (op.matches("RR|RL|SRL|SLL|SRA")) {
				neededComponents[5] = true;
			}
		}
		for (String cond : conditions) {
			if (cond.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
				neededComponents[2] = true;
			}
		}
		return neededComponents;
	}
	
	private void copy(File sourceLocation, File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}

	private void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}

	private void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
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

	public Connector getStatus() {
		return status;
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

	public Connector getOutput2() {
		return output2;
	}
}