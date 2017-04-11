package wrapper;

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
	
}