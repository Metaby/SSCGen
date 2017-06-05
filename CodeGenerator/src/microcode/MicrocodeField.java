package microcode;

import java.util.Map;

public class MicrocodeField {
	private Map<String, Integer> fields;
	private String id;
	private int cvStart;
	private int cvEnd;

	public MicrocodeField() {
	}
	
	public void addKeyVal(String key, int val) {
		fields.put(key.trim(), val);
	}

	public String getId() {
		return id;
	}

	public int getValue(String key) {
		if (fields.containsKey(key)) {
			return fields.get(key);
		} else {
			return -1;
		}
	}

	public int getCvStart() {
		return cvStart;
	}

	public int getCvEnd() {
		return cvEnd;
	}

	public void setFields(Map<String, Integer> fields) {
		this.fields = fields;
	}

	public void setCvStart(int cvStart) {
		this.cvStart = cvStart;
	}

	public void setCvEnd(int cvEnd) {
		this.cvEnd = cvEnd;
	}

	public void setId(String id) {
		this.id = id;
	}
}
