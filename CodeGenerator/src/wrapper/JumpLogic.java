package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.VhdlComponent;

public class JumpLogic {

	private List<Connector> programTargetA;
	private List<Connector> programTargetB;
	private Connector inputFlags;
	private Connector output;
	private String id;
	private int wordSize;
	private int inputFlagsCnt;
	
	public JumpLogic(jaxb.JumpLogic jl) {
		id = jl.getId();
		wordSize = jl.getWordSize();
		inputFlagsCnt = jl.getInputFlagsCnt();
		output = new Connector(jl.getOutput(), wordSize);
		inputFlags = new Connector(jl.getInputFlags(), inputFlagsCnt);
		programTargetA = new ArrayList<Connector>();
		for (int i = 0; i < jl.getProgramTargetA().getInput().size(); i++) {
			programTargetA.add(new Connector(jl.getProgramTargetA().getInput().get(i), wordSize));
		}	
		programTargetB = new ArrayList<Connector>();
		for (int i = 0; i < jl.getProgramTargetB().getInput().size(); i++) {
			programTargetB.add(new Connector(jl.getProgramTargetB().getInput().get(i), wordSize));
		}
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_pt1_isel", (int)Math.ceil(Math.log(programTargetA.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_pt2_isel", (int)Math.ceil(Math.log(programTargetB.size()) / Math.log(2))));
		cv.add(Wrapper.IntToRange(id + "_jcsel", (int)Math.ceil(Math.log(inputFlagsCnt) / Math.log(2))));
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public void generateComponent(String targetFile) {
		VhdlComponent component = new VhdlComponent(id);
		component.AddGeneric("g_wordSize : integer := " + (wordSize - 1));
		for (int i = 0; i < programTargetA.size(); i++) {
			component.AddPort("p_pathA" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		for (int i = 0; i < programTargetB.size(); i++) {
			component.AddPort("p_pathB" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		int adrSizeA = (int)Math.ceil(Math.log(programTargetA.size()) / Math.log(2));
		if (programTargetA.size() > 1) {
			if (adrSizeA > 1) {
				component.AddPort("p_pathASelect : in std_logic_vector(" + (adrSizeA - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_pathASelect : in std_logic");			
			}			
		}
		int adrSizeB = (int)Math.ceil(Math.log(programTargetB.size()) / Math.log(2));
		if (programTargetB.size() > 1) {
			if (adrSizeB > 1) {
				component.AddPort("p_pathBSelect : in std_logic_vector(" + (adrSizeB - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_pathBSelect : in std_logic");			
			}			
		}
		component.AddPort("p_ctrl : in std_logic_vector(" + (inputFlagsCnt - 1) + " DOWNTO 0)");
		component.AddPort("p_ctrlSelect : in std_logic_vector(" + ((int)Math.ceil(Math.log(inputFlagsCnt) / Math.log(2)) - 1) + " DOWNTO 0");
		component.AddPort("p_pathOut : out std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddSignal("s_pathAInput : std_logic_vector(g_wordSize DOWNTO 0");
		component.AddSignal("s_pathBInput : std_logic_vector(g_wordSize DOWNTO 0");
		component.AddSignal("s_pathSelect : std_logic");
		String behavior = "";
		behavior += VhdlComponent.generateMux("p_pathASelect", "s_pathAInput", "p_pathA", programTargetA.size());
		behavior += VhdlComponent.generateMux("p_pathBSelect", "s_pathBInput", "p_pathB", programTargetB.size());
		behavior += "  -- Behavior" + System.lineSeparator();		
		behavior += "  WITH p_ctrlSelect SELECT s_pathSelect <=" + System.lineSeparator();
		for (int i = 0; i < inputFlagsCnt; i++) {
			behavior += "    p_ctrl(" + i + ") " + " WHEN \"" + String.format("%" + ((int)Math.ceil(Math.log(inputFlagsCnt) / Math.log(2))) + "s", Integer.toBinaryString(i)).replace(' ', '0');
			if (i < inputFlagsCnt - 1) {
				behavior += "\"," + System.lineSeparator();				
			} else {
				behavior += "\";" + System.lineSeparator();				
			}
		}
		
		behavior += "  WITH s_pathSelect SELECT p_pathOut <=" + System.lineSeparator();
		behavior += "    s_pathAInput WHEN \'0\'," + System.lineSeparator();
		behavior += "    s_pathBInput WHEN \'1\';";
		component.setBehavior(behavior);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public List<Connector> getProgramTargetsA() {
		return programTargetA;
	}

	public List<Connector> getProgramTargetsB() {
		return programTargetB;
	}

	public Connector getInputFlags() {
		return inputFlags;
	}

	public Connector getOutput() {
		return output;
	}

	public String getId() {
		return id;
	}

	public int getWordSize() {
		return wordSize;
	}

	public int getInputFlagsCnt() {
		return inputFlagsCnt;
	}
	
}
