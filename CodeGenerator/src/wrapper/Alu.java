package wrapper;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
		String muxes = "";
		muxes += ComponentBuilder.generateMux("p_inputASelect", "s_inputAInput", "p_inputA", inputsA.size());
		muxes += ComponentBuilder.generateMux("p_inputBSelect", "s_inputBInput", "p_inputB", inputsB.size());
		muxes += "  -- Behavior" + System.lineSeparator();
		List<String> imports = prepareFilesAndImports();
		for (int i = 0; i < imports.size(); i++) {
			component.AddImport(imports.get(i));
		}
		component.setBehavior(muxes + generateBehavior());
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public String generateBehavior() {
		String behavior = "";
		return behavior;
	}
	
	public List<String> prepareFilesAndImports() {
		List<String> imports = new ArrayList<String>();
		try {
			Boolean copyComponents[] = new Boolean[] { false, false, false, false, false, false };
			for (String op : operations) {
				if (op.matches("ADD|ADD_U|SUB|SUB_U")) {
					copyComponents[0] = true;
				}
				if (op.matches("AND|OR|XOR|NOT")) {
					copyComponents[1] = true;
				}
				if (op.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
					copyComponents[2] = true;
				}
				if (op.matches("DIV|DIV_U")) {
					copyComponents[3] = true;
				}
				if (op.matches("MUL|MUL_U")) {
					copyComponents[4] = true;
				}
				if (op.matches("RR|RL|SRL|SLL|SRA")) {
					copyComponents[5] = true;
				}
			}
			for (String cond : conditions) {
				if (cond.matches("GT|GT_U|LT|LT_U|GEQ|GEQ_U|LEQ|LEQ_U|EQ")) {
					copyComponents[2] = true;
				}
			}
			if (copyComponents[0]) {
				copy(new File("processors/components/alu/adder"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT carry_select_adder" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_block_size : integer := 7;" + System.lineSeparator();
				imprt += "      g_blocks     : integer := 3;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd   : in  std_logic;" + System.lineSeparator();
				imprt += "      p_sub    : in  std_logic;" + System.lineSeparator();
				imprt += "      p_op_1   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result : out std_logic_vector((g_block_size + 1) * (g_blocks + 1) - 1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_ovflw  : out std_logic" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (copyComponents[1]) {
				copy(new File("processors/components/alu/bitwise_logic"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT bit_manipulator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_cmd    : in  std_logic_vector(1 DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (copyComponents[2]) {
				copy(new File("processors/components/alu/comparator"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT tree_comparator" + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_op_1 : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2 : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_sgnd : in  std_logic;" + System.lineSeparator();				
				imprt += "      p_g    : out std_logic;" + System.lineSeparator();				
				imprt += "      p_l    : out std_logic" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (copyComponents[3]) {
				copy(new File("processors/components/alu/divider"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT divider " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd     : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_dividend : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_divisor  : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_remain   : out std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_result   : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (copyComponents[4]) {
				copy(new File("processors/components/alu/multiplier"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT four_quadrant_multiplier " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_sgnd      : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_op_1      : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2      : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_add       : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_result_lo : out std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_result_hi : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
			if (copyComponents[5]) {
				copy(new File("processors/components/alu/shifter"), new File("processors/mips/code/components/alu"));
				String imprt = "";
				imprt += "  COMPONENT barrel_shifter " + System.lineSeparator();
				imprt += "    GENERIC (" + System.lineSeparator();
				imprt += "      g_size : integer := 31;" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "    PORT (" + System.lineSeparator();
				imprt += "      p_cmd    : in  std_logic;" + System.lineSeparator();
				imprt += "      p_arith  : in  std_logic;" + System.lineSeparator();
				imprt += "      p_rotate : in  std_logic;" + System.lineSeparator();	
				imprt += "      p_op_1   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_op_2   : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();
				imprt += "      p_add    : in  std_logic_vector(g_size DOWNTO 0);" + System.lineSeparator();	
				imprt += "      p_result : out std_logic_vector(g_size DOWNTO 0)" + System.lineSeparator();
				imprt += "    );" + System.lineSeparator();
				imprt += "  END COMPONENT;";
				imports.add(imprt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imports;
	}
	
	public void copy(File sourceLocation, File targetLocation) throws IOException {
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
