package tool;

import java.util.*;

public class ControlVector {
	private List<ControlField> fields;
	private int size;
	
	public ControlVector(int size) {
		this.fields = new ArrayList<ControlField>();
		this.size = size;
	}
	
	public void addField(ControlField field) {
		fields.add(field);
	}

	public List<ControlField> getFields() {
		return fields;
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		String cvString = "";
		cvString += "size: " + size + System.lineSeparator();
		for (ControlField cf : fields) {
			cvString += "  " + cf.getName() + System.lineSeparator();
			cvString += "    " + cf.getParameters().toString() + System.lineSeparator();
		}
		return cvString;
	}
}
