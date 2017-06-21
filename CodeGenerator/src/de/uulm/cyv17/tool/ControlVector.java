package de.uulm.cyv17.tool;

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
			cvString += "    [" + cf.getStart() + "," + cf.getEnd() + "]" + cf.getParameters().toString() + System.lineSeparator();
		}
		return cvString;
	}
	
	public static ControlVector concatenate(ControlVector cv1, ControlVector cv2) {
		ControlVector cv = new ControlVector(cv1.size + cv2.size);
		for (ControlField cf : cv1.fields) {
			cv.fields.add(cf);
		}
		for (ControlField cf : cv2.fields) {
			cf.move(cv1.size);
			cv.fields.add(cf);
		}
		return cv;
	}
}
