package microcode;

import java.util.HashMap;
import java.util.Map;

public class MicrocodeField {
	private Map<String, Integer> fields;
	private Boolean single;
	private String id;
	private int cvStart;
	private int cvEnd;

	public MicrocodeField(String id, int cvStart, int cvEnd, Boolean single) {
		this.fields = new HashMap<String, Integer>();
		this.single = single;
		this.id = id;
		this.cvStart = cvStart;
		this.cvEnd = cvEnd;
	}
	
	public void addKeyVal(String key, int val) {
		fields.put(key, val);
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

	public Boolean getSingle() {
		return single;
	}
}
