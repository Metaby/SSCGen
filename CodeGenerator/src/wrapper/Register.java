package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.VhdlComponent;

public class Register {
	
	private List<Connector> inputs;
	private Connector control;
	private Connector output;
	private String id;
	private int size;
	
	public Register(jaxb.Register reg) {
		id = reg.getId();
		size = reg.getSize();
		output = new Connector(reg.getOutput(), size);
		control = new Connector(reg.getControl(), -1);
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < reg.getInputs().getInput().size(); i++) {
			inputs.add(new Connector(reg.getInputs().getInput().get(i), size));
		}
	}

	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_isel", (int)Math.ceil(Math.log(inputs.size()) / Math.log(2))));
		cv.add(id + "_write");
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public String getImport() {
		return "";
	}
	
	public String getInstance() {
		return "";
	}
	
	public List<Connector> getInputs() {
		return inputs;
	}

	public Connector getOutput() {
		return output;
	}

	public String getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	public Connector getControl() {
		return control;
	}
	
}
