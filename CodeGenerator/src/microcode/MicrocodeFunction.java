package microcode;

import java.util.ArrayList;
import java.util.List;

public class MicrocodeFunction {
	private List<String> funcRow;
	private String name;
	private int position;
	
	public MicrocodeFunction() {
		this.funcRow = new ArrayList<String>();
		this.name = "";
		this.position = -1;
	}
	
	public void addFunctionLine(String call) {
		funcRow.add(call);
	}

	public List<String> getFunctionLines() {
		return funcRow;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(String position) {
		if (position.length() > 0) {
			this.position = Integer.parseInt(position.substring(2), 16);			
		} else {
			this.position = -1;
		}
	}
}