package wrapper;

public class Connector {

	private static int c = 0;
	
	public String origin;
	public String pin;
	public int size;
	public int id;
	
	public static int getNewId() {
		c++;
		return c - 1;
	}
	
	@Override
	public String toString() {
		String str = origin + "." + pin;
		if (str.contains("[")) {
			return str.substring(0, str.indexOf("["));
		} else {
			return str;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return obj.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
}
