package tool;

import java.util.*;

public class ControlField {
	private Map<String, Integer> parameters;
	private String name;
	private int start;
	private int size;
	private int end;
	
	public ControlField(String name, int start, int end) {
		parameters = new HashMap<String, Integer>();
		this.name = name;
		this.start = start;
		this.end = end;
		this.size = end - start + 1;
	}
	
	public void addParameter(String key, int value) {
		if (!parameters.containsKey(key)) {
			parameters.put(key, value);
		}
	}

	public Map<String, Integer> getParameters() {
		return parameters;
	}

	public int getStart() {
		return start;
	}

	public int getSize() {
		return size;
	}

	public int getEnd() {
		return end;
	}

	public String getName() {
		return name;
	}
}
