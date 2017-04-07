package wrapper;

import java.util.ArrayList;
import java.util.List;

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
		Connector outCon = new Connector();
		outCon.origin = jl.getId();
		outCon.pin = jl.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		Connector flagsCon = new Connector();
		flagsCon.origin = jl.getId();
		flagsCon.pin = jl.getInputFlags();
		flagsCon.size = inputFlagsCnt;
		flagsCon.id = Connector.getNewId();
		inputFlags = flagsCon;		
		programTargetA = new ArrayList<Connector>();
		for (int i = 0; i < jl.getProgramTargetA().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = jl.getProgramTargetA().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			programTargetA.add(inCon);
		}	
		programTargetB = new ArrayList<Connector>();
		for (int i = 0; i < jl.getProgramTargetB().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = jl.getProgramTargetB().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = wordSize;
			inCon.id = Connector.getNewId();
			programTargetB.add(inCon);
		}
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int i = 0; i < (int)Math.ceil(Math.log(programTargetA.size()) / Math.log(2)); i++) {
			cv.add(id + "_pt1_isel_" + i);
		}
		for (int i = 0; i < (int)Math.ceil(Math.log(programTargetB.size()) / Math.log(2)); i++) {
			cv.add(id + "_pt2_isel_" + i);
		}
		for (int i = 0; i < (int)Math.ceil(Math.log(inputFlagsCnt) / Math.log(2)); i++) {
			cv.add(id + "_jcsel_" + i);			
		}
		return cv;
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
