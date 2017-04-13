import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import wrapper.Alu;
import wrapper.Architecture;
import wrapper.Connector;
import wrapper.JumpLogic;
import wrapper.Memory;
import wrapper.PortDirection;
import wrapper.RegisterFile;
import wrapper.Rom;

import static java.nio.file.StandardCopyOption.*;

public class ComponentFactory {	

	public void GenerateROM(String targetFile, Rom rom) {
		int addressSize = rom.getAddressSize();
		int wordSize = rom.getWordSize();
		int content[] = new int[] { 1 };
		File inputFile = new File(rom.getContentFile());
		if (!inputFile.exists()) {
			System.out.println("Error: Content file does not exist. (" + rom.getContentFile() + ")");
			return;
		}
		try {
			List<String> strContent = Files.readAllLines(inputFile.toPath());
			if (strContent.size() > 0 && strContent.get(0).equals("v2.0 raw")) {
				String strBytes = "";
				for (int i = 1; i < strContent.size(); i++) {
					strBytes += strContent.get(i) + " ";
				}
				String strBytesArray[] = strBytes.split("\\s+");
				content = new int[strBytesArray.length];
				for (int i = 0; i < strBytesArray.length; i++) {
					content[i] = Integer.parseInt(strBytesArray[i], 16);
				}
			} else {
				System.out.println("Error: Wrong file format. (" + rom.getContentFile() + ")");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Reading content file. (" + rom.getContentFile() + ")");
			return;
		}
		ComponentBuilder component = new ComponentBuilder(rom.getId());
		component.AddPort("p_address  :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0)");
		component.AddPort("p_word     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		String behavior = "  WITH p_address SELECT p_word <=" + System.lineSeparator();
		for (int i = 0; i < content.length; i++) {
			behavior += "    \"" + String.format("%" + wordSize + "s", Integer.toBinaryString(content[i])).replace(' ', '0') + "\" WHEN \""
						+ String.format("%" + addressSize + "s",Integer.toBinaryString(i)).replace(' ', '0');
			behavior += "\",";
			behavior += System.lineSeparator();
		}
		if (content.length < Math.pow(2, addressSize)) {
			behavior += "    \"" + String.format("%" + wordSize + "s", Integer.toBinaryString(0)).replace(' ', '0') + "\" WHEN others;" + System.lineSeparator();			
		} else {
			char ca[] = behavior.toCharArray();
			ca[behavior.length() - 3] = ';';
			behavior = new String(ca);
		}
		component.setBehavior(behavior.substring(0, behavior.length() - 2));
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateMux(String targetFile, String muxName, int addressSize, int wordSize) {
		ComponentBuilder component = new ComponentBuilder(muxName);
		if (addressSize > 1) {
			component.AddPort("p_address    :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0)");
		} else {
			component.AddPort("p_address    :  in  std_logic;");
		}
		for (int i = 0; i < Math.pow(2, addressSize); i++) {						
			component.AddPort("p_input" + i + "     :  in  std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		}
		component.AddPort("p_output     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		String behavior = "  WITH p_address SELECT p_output <=" + System.lineSeparator();
		for (int i = 0; i < Math.pow(2, addressSize); i++) {
			behavior += "    p_input" + i + " WHEN \"" + String.format("%" + addressSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"," + System.lineSeparator();
		}
		char ca[] = behavior.toCharArray();
		ca[behavior.length() - 3] = ';';
		behavior = new String(ca);
		component.setBehavior(behavior.substring(0, behavior.length() - 2));
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateAlu(String targetFile, Alu alu) {
		ComponentBuilder component = new ComponentBuilder(alu.getId());
		component.AddGeneric("g_wordSize : integer := " + (alu.getWordSize() - 1));
		int cmdBits = (int)Math.ceil(Math.log(alu.getOperations().size() + alu.getConditions().size()) / Math.log(2));
		if (cmdBits == 1) {		
			component.AddPort("p_operation : in std_logic");
		} else if (cmdBits > 1) {
			component.AddPort("p_operation : in std_logic_vector(" + (cmdBits - 1) + " DOWNTO 0)");
		}
		component.AddPort("p_operandA : in std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddPort("p_operandB : in std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddPort("p_result : out std_logic_vector(g_wordSize DOWNTO 0)");
		if (alu.getStatus() != null) {
			int statusSize = alu.getConditions().size() + 2; // + 2 für carry und over/underflow
			component.AddPort("p_status : out std_logic_vector(" + (statusSize - 1) + " DOWNTO 0)");
		}
		// TODO: Behavior!
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}		
	}
	
	public void GenerateRegisterFile(String targetFile, RegisterFile rf) {
		ComponentBuilder component = new ComponentBuilder(rf.getId());
		component.AddGeneric("g_addressSize : integer := " + (rf.getAddressSize() - 1));
		component.AddGeneric("g_wordSize : integer := " + (rf.getWordSize() - 1));
		component.AddPort("p_clk : in std_logic");
		for (int i = 0; i < rf.getPorts().size(); i++) {
			if (rf.getPorts().get(i).getDirection() == PortDirection.IN) {
				component.AddPort("p_port" + i + "_write : in std_logic");
				component.AddPort("p_port" + i + "_word : in _std_logic_vector(g_wordSize DOWNTO 0)");
				component.AddPort("p_port" + i + "_address : in _std_logic_vector(g_addressSize DOWNTO 0)");
			} else if (rf.getPorts().get(i).getDirection() == PortDirection.OUT) {
				component.AddPort("p_port" + i + "_address : in _std_logic_vector(g_addressSize DOWNTO 0)");
				component.AddPort("p_port" + i + "_word : out _std_logic_vector(g_wordSize DOWNTO 0)");				
			}
		}
		// TODO: Behavior!
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public void GenerateMemory(String targetFile, Memory mem) {
		ComponentBuilder component = new ComponentBuilder(mem.getId());
		component.AddGeneric("g_addressSize : integer := " + (mem.getAddressSize() - 1));
		component.AddGeneric("g_wordSize : integer := " + (mem.getWordSize() - 1));
		component.AddPort("p_clk : in std_logic");
		for (int i = 0; i < mem.getPorts().size(); i++) {
			if (mem.getPorts().get(i).getDirection() == PortDirection.IN) {
				component.AddPort("p_port" + i + "_write : in std_logic");
				component.AddPort("p_port" + i + "_word : in _std_logic_vector(g_wordSize DOWNTO 0)");
				component.AddPort("p_port" + i + "_address : in _std_logic_vector(g_addressSize DOWNTO 0)");
			} else if (mem.getPorts().get(i).getDirection() == PortDirection.OUT) {
				component.AddPort("p_port" + i + "_address : in _std_logic_vector(g_addressSize DOWNTO 0)");
				component.AddPort("p_port" + i + "_word : out _std_logic_vector(g_wordSize DOWNTO 0)");				
			}
		}
		// TODO: Behavior!
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public void GenerateJumpLogic(String targetFile, JumpLogic jl) {
		ComponentBuilder component = new ComponentBuilder(jl.getId());
		component.AddGeneric("g_wordSize : integer := " + (jl.getWordSize() - 1));
		component.AddPort("p_targetA : in std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddPort("p_targetB : in std_logic_vector(g_wordSize DOWNTO 0)");
		if (jl.getInputFlagsCnt() > 1) {
			component.AddPort("p_ctrl : in std_logic_vector(" + (jl.getInputFlagsCnt() - 1) + " DOWNTO 0)");
		} else {
			component.AddPort("p_ctrl : in std_logic");			
		}
		int cmdBits = (int)Math.ceil(Math.log(jl.getInputFlagsCnt()) / Math.log(2));
		if (cmdBits == 1) {
			component.AddPort("p_ctrlSelect : in std_logic");
		} else if (cmdBits > 1){
			component.AddPort("p_ctrlSelect : in std_logic_vector(" + (cmdBits - 1) + " DOWNTO 0)"); 
		}
		component.AddPort("p_result : out std_logic_vector(g_wordSize DOWNTO 0)");
		// TODO: Behavior!
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public void GenerateArchitecture(String directory, Architecture arch) {
		new File(directory + "/components/").mkdirs();
		if (arch.getRegisters().size() > 0) {
			try {
				Files.copy(new File("processors/components/Register.vhdl").toPath(), new File(directory + "/components/Register.vhdl").toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (arch.getStacks().size() > 0) {
			try {
				Files.copy(new File("processors/components/Stack.vhdl").toPath(), new File(directory + "/components/Stack.vhdl").toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		for (Rom rom : arch.getRoms()) {
			GenerateROM(directory + "/components/" + rom.getId() + "_Rom.vhdl", rom);			
		}
		for (Alu alu : arch.getAlus()) {
			ConfigureAlu(directory + "components/", alu);
			GenerateAlu(directory + "/components/" + alu.getId() + "_Alu.vhdl", alu);
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			GenerateRegisterFile(directory + "/components/" + rf.getId() + "_RegisterFile.vhdl", rf);
		}		
		for (Memory mem : arch.getMemories()) {
			GenerateMemory(directory + "/components/" + mem.getId() + "_Memory.vhdl", mem);
		}
		for (JumpLogic jl : arch.getJumpLogics()) {
			GenerateJumpLogic(directory + "/components/" + jl.getId() + "_JumpLogic.vhdl", jl);
		}
		ComponentBuilder topLevelEntity = new ComponentBuilder("processor");
		for (Connector con : arch.getOutputConnectors()) {
			if (con != null && con.size > 0 && !con.pin.startsWith(":")) {
				String signal = "s_" + con.origin + "_" + con.pin + " : ";
				if (con.size == 1) {
					signal += "std_logic";
				} else {
					signal += "std_logic_vector(" + (con.size - 1) + " DOWNTO 0)";
				}
				topLevelEntity.AddSignal(signal);				
			}
		}
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + directory + "processor.vhdl" + ")");
		}	
	}

	final String needsAdder[] = { "ADD", "ADD_U", "SUB", "SUB_U" };
	final int ADDER = 0;
	final String needsMultiplier[] = { "MUL", "MUL_U" };
	final int MULTIPLIER = 1;
	final String needsDivider[] = { "DIV", "DIV_U" };
	final int DIVIDER = 2;
	final String needsShifter[] = { "RR", "RL", "SRL", "SLL", "SRA" };
	final int SHIFTER = 3;
	final String needsBitwise[] = { "AND", "OR", "XOR", "NOT" };
	final int BITWISE = 4;
	final String needsComparator[] = { "ZERO", "GT", "GT_U", "LT", "LT_U", "GEQ", "GEQ_U", "LEQ", "LEQ_U", "EQ" };
	final int COMPARATOR = 5;
	
	public Boolean ArrayContains(String array[], String key) {
		for (String str : array) {
			if (str.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public void ConfigureAlu(String directory, Alu alu) {
		Boolean componentsNeeded[] = { false, false, false, false, false, false };
		for (String operation : alu.getOperations()) {
			System.out.println(operation);
			if (ArrayContains(needsAdder, operation)) {
				componentsNeeded[ADDER] = true;
			}
			if (ArrayContains(needsMultiplier, operation)) {
				componentsNeeded[MULTIPLIER] = true;
			}
			if (ArrayContains(needsDivider, operation)) {
				componentsNeeded[DIVIDER] = true;
			}
			if (ArrayContains(needsShifter, operation)) {
				componentsNeeded[SHIFTER] = true;
			}
			if (ArrayContains(needsBitwise, operation)) {
				componentsNeeded[BITWISE] = true;
			}
			if (ArrayContains(needsComparator, operation)) {
				componentsNeeded[COMPARATOR] = true;
			}
		}
		if (componentsNeeded[ADDER]) {
			copyFile("processors/components/alu/adder/half_adder.vhd", directory);
			copyFile("processors/components/alu/adder/full_adder.vhd", directory);
			copyFile("processors/components/alu/adder/carry_ripple_adder.vhd", directory);
			copyFile("processors/components/alu/adder/carry_select_block.vhd", directory);
			copyFile("processors/components/alu/adder/carry_select_adder.vhd", directory);
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
	
	String GenerateAluBehavior(Alu alu) {
		String behavior = "";
		return behavior;
	}
	
}
