package tool;

import java.util.ArrayList;
import java.util.List;

public class MicrocodeFunction {
	private List<String> calls;
	private List<String> sets;
	private String name;
	private int position;
	
	public MicrocodeFunction() {
		this.calls = new ArrayList<String>();
		this.sets = new ArrayList<String>();
		this.name = "";
		this.position = -1;
	}
	
	public void addCall(String call) {
		calls.add(call);
	}
	
	public void addSet(String set) {
		sets.add(set);
	}

	public List<String> getCalls() {
		return calls;
	}

	public List<String> getSets() {
		return sets;
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