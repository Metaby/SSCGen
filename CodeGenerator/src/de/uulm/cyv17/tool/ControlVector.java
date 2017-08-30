package de.uulm.cyv17.tool;

import java.util.*;

/**
 * A class containing information of a control vector.
 * The control vector is build by several control fields.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class ControlVector {
	private List<ControlField> fields;
	private int size;
	
	/**
	 * The constructor of the control vector with its size.
	 * 
	 * @param size the size of the control vector
	 */
	public ControlVector(int size) {
		this.fields = new ArrayList<ControlField>();
		this.size = size;
	}
	
	/**
	 * Method to add a control field to the control vector.
	 *
	 * @param field the control field to be added
	 */
	public void addField(ControlField field) {
		fields.add(field);
	}

	/**
	 * A function returning the control fields of the control vector.
	 * 
	 * @return a list containing the control fields
	 */
	public List<ControlField> getFields() {
		return fields;
	}

	/**
	 * Returns the size of the control vector.
	 * 
	 * @return an integer containing the size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Converts the control vector into a human readable string
	 */
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
	
	/**
	 * A static function to concatenate two given control vectors.
	 * 
	 * @param cv1 the first control vector
	 * @param cv2 the second control vector
	 * @return a new control vector consisting of the given control vectors: cv1 + cv2
	 */
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
