package wrapper;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ComponentBuilder;

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
		if (alu.getConditions() != null) {
			for (int i = 0; i < alu.getConditions().getCondition().size(); i++) {
				conditions.add(alu.getConditions().getCondition().get(i).name());
			}			
		}
		Connector outCon = new Connector();
		outCon.origin = alu.getId();
		outCon.pin = alu.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		if (alu.getStatusFlags() != null) {
			Connector flagsCon = new Connector();
			flagsCon.origin = alu.getId();
			flagsCon.pin = alu.getStatusFlags();
			flagsCon.size = conditionsCnt;
			flagsCon.id = Connector.getNewId();
			status = flagsCon;			
		}
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
		cv.add(Wrapper.IntToRange(id + "_op1", (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_op2", (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_csel", (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2))));
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}

	public void generateComponent(String targetFile) {
		ComponentBuilder component = new ComponentBuilder(id);
		component.AddGeneric("g_wordSize : integer := " + (wordSize - 1));
		for (int i = 0; i < inputsA.size(); i++) {
			component.AddPort("p_inputA" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		for (int i = 0; i < inputsB.size(); i++) {
			component.AddPort("p_inputB" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		int adrSizeA = (int)Math.ceil(Math.log(inputsA.size()) / Math.log(2));
		if (inputsA.size() > 1) {
			if (adrSizeA > 1) {
				component.AddPort("p_inputASelect : in std_logic_vector(" + (adrSizeA - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_inputASelect : in std_logic");			
			}			
		}
		int adrSizeB = (int)Math.ceil(Math.log(inputsB.size()) / Math.log(2));
		if (inputsB.size() > 1) {
			if (adrSizeB > 1) {
				component.AddPort("p_inputBSelect : in std_logic_vector(" + (adrSizeB - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_inputBSelect : in std_logic");			
			}			
		}
		int cmdBits = (int)Math.ceil(Math.log(operations.size() + conditions.size()) / Math.log(2));
		if (cmdBits == 1) {		
			component.AddPort("p_operation : in std_logic");
		} else if (cmdBits > 1) {
			component.AddPort("p_operation : in std_logic_vector(" + (cmdBits - 1) + " DOWNTO 0)");
		}
		if (status != null) {
			int statusSize = conditions.size() + 2; // + 2 für carry und over/underflow
			component.AddPort("p_status : out std_logic_vector(" + (statusSize - 1) + " DOWNTO 0)");
		}
		component.AddPort("p_output : out std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddSignal("s_inputAInput : std_logic_vector(g_wordSize DOWNTO 0");
		component.AddSignal("s_inputBInput : std_logic_vector(g_wordSize DOWNTO 0");
		String behavior = "";
		behavior += ComponentBuilder.generateMux("p_inputASelect", "s_inputAInput", "p_inputA", inputsA.size());
		behavior += ComponentBuilder.generateMux("p_inputBSelect", "s_inputBInput", "p_inputB", inputsB.size());
		behavior += "  -- Behavior" + System.lineSeparator();
		component.setBehavior(behavior);
		// TODO: Behavior
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void copyFile(String file, String dest) {
		try {
			dest += file.substring(file.lastIndexOf("/") + 1);
			System.out.println(file);
			System.out.println(dest);
			Files.copy(new File(file).toPath(), new File(dest).toPath(), REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
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
