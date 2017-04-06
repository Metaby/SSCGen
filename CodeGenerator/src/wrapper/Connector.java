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

	public String toCleanString() {
		String str = toString();
		if (str.contains("[")) {
			return str.substring(0, str.indexOf("["));
		} else {
			return str;
		}
	}
	
	@Override
	public String toString() {
		return origin + "." + pin;
	}
	
	// TODO: HashSet enthält doppelte Werte -> Fixen
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Connector) {
			return ((Connector)obj).toCleanString() == this.toCleanString();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.toCleanString().hashCode();
	}
	
}
