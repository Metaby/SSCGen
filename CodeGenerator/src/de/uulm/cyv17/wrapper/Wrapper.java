package de.uulm.cyv17.wrapper;

public class Wrapper {
	
	public static String IntToRange(String prefix, int size) {
		if (size == 1) {
			return prefix + "(0)";
		}
		if (size > 0) {
			return prefix + "(0:" + (size - 1) + ")";
		}
		return "";
	}
	
	public static int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
	
	public static String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
}