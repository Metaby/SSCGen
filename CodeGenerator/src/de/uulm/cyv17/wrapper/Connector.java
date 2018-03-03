package de.uulm.cyv17.wrapper;

/**
 * This class implements the connectors used in
 * the architecture and entity classes
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class Connector {

	private int constValue;
	public ConnectorType type;
	public String origin;
	public String pin;
	public int lowerBound;
	public int upperBound;
	public int size;
	
	/**
	 * The constructor of the connector class. It takes
	 * the value and the bit size of the connector.
	 * 
	 * @param value the value of the connector
	 * @param size the size of the connector
	 */
	public Connector(String value, int size) {
		// TODO: Fehler abfangen falls connector leer oder falsches format hat
		this.size = size;
		if (value.startsWith("system.auto")) {
			type = ConnectorType.SYSTEM_AUTO;
			origin = "";
			pin = "";
			constValue = -1;
			lowerBound = -1;
			upperBound = -1;
		} else if (value.startsWith("system.control")) {
			type = ConnectorType.SYSTEM_CONTROL;
			origin = "";
			pin = "";
			constValue = -1;
			lowerBound = -1;
			upperBound = -1;
		} else if (value.startsWith("system.const")) {
			type = ConnectorType.SYSTEM_CONST;
			origin = "";
			pin = "";
			constValue = Integer.parseInt(value.substring(value.lastIndexOf('.') + 1));
			lowerBound = -1;
			upperBound = -1;
		} else if (value.startsWith("system.in")) {
			type = ConnectorType.SYSTEM_IN;
			origin = "system";
			pin = removeSubSel(value.split("\\.")[2]);
			constValue = -1;
			lowerBound = getLowerBound(value);
			upperBound = getUpperBound(value);
		} else if (value.startsWith("system.out")) {
			type = ConnectorType.SYSTEM_OUT;
			origin = "system";
			pin = removeSubSel(value.split("\\.")[2]);
			constValue = -1;
			lowerBound = getLowerBound(value);
			upperBound = getUpperBound(value);
		}  else if (value.startsWith("system.open")) {
			type = ConnectorType.SYSTEM_OPEN;
			origin = "";
			pin = "";
			constValue = -1;
			lowerBound = -1;
			upperBound = -1;
		} else if (value.startsWith("system.clock")) {
			type = ConnectorType.SYSTEM_CLOCK;
			origin = "";
			pin = "";
			constValue = -1;
			lowerBound = -1;
			upperBound = -1;
		} else {
			type = ConnectorType.STANDARD;
			origin = value.split("\\.")[0];
			pin = removeSubSel(value.split("\\.")[1]);
			constValue = -1;
			lowerBound = getLowerBound(value);
			upperBound = getUpperBound(value);
		}
	}

	/**
	 * If the given pin specifies a subset of bits. This
	 * subset is extracted and the lower bound returned.
	 * 
	 * @param pin the pin value
	 * @return an integer containing the lower bound
	 */
	private int getLowerBound(String pin) {
		if (pin.contains("[")) {
			int lp = pin.indexOf('[');
			int sep = pin.indexOf(',');
			return Integer.parseInt(pin.substring(lp + 1, sep));
		} else {
			return -1;
		}
	}

	/**
	 * If the given pin specifies a subset of bits. This
	 * subset is extracted and the upper bound returned.
	 * 
	 * @param pin the pin value
	 * @return an integer containing the upper bound
	 */
	private int getUpperBound(String pin) {
		if (pin.contains("[")) {
			int rp = pin.indexOf(']');
			int sep = pin.indexOf(',');
			return Integer.parseInt(pin.substring(sep + 1, rp));
		} else {
			return -1;
		}
	}
	
	/**
	 * Removes the specification of the bit subset and
	 * returns the pin value.
	 * 
	 * @param pin the pin value
	 * @return a string containing the pin value without the subset specification
	 */
	private String removeSubSel(String pin) {
		if (pin.contains("[")) {
			return pin.substring(0, pin.indexOf('['));			
		} else {
			return pin;
		}
	}
	
	/**
	 * Converts a given integer to a binary string under the
	 * restriction of a given number of digits. This is needed
	 * due to the syntax of vhdl.
	 * 
	 * @param value the value to be converted
	 * @param digits the number of digits
	 * @return a string containing the binary representation
	 */
	private String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
	/**
	 * Creates a vhdl appropriate specifier of the
	 * connector and returns it.
	 * 
	 * @return a string containing the vhdl specifier
	 */
	public String toSignal() {
		if (type == ConnectorType.STANDARD) {
			return "s_" + toString().replace('.', '_');
		} else if (type == ConnectorType.SYSTEM_CONST) {
			if (size > 1) {
				return "\"" + getBinaryString(constValue, size).substring(0, size) + "\"";
			} else {
				return "\'" + getBinaryString(constValue, size).substring(0, size) + "\'";
			}
		} else if (type == ConnectorType.SYSTEM_IN) {
			return "p_" + pin.replace('.', '_');	
		} else if (type == ConnectorType.SYSTEM_OUT) {
			return "ps_" + pin.replace('.', '_');
		} else if (type == ConnectorType.SYSTEM_OPEN) {
			return "OPEN";
		} else if (type == ConnectorType.SYSTEM_CLOCK) {
			return "p_clk";
		}
		return "";
	}
	
	/**
	 * Creates and returns a human readable string belonging
	 * to the connector.
	 * 
	 * @return the human readable string
	 */
	@Override
	public String toString() {
		if (type == ConnectorType.SYSTEM_AUTO) {
			return "SYSTEM_AUTO";
		}
		if (type == ConnectorType.SYSTEM_CONTROL) {
			return "SYSTEM_CONTROL";
		}
		if (type == ConnectorType.SYSTEM_CONST) {
			//return "CONST(" + constValue + ")";
			return "const." + constValue;
		}
		return origin + "." + pin;
	}
	
	/**
	 * Compares the connector with another connector to
	 * determine if they are equal or not.
	 * 
	 * @param obj the second connector to be tested with
	 * @return true if the connectors are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return obj.hashCode() == this.hashCode();
	}

	/**
	 * Creates and returns the hash code of the connector.
	 * 
	 * @return an integer containing the hash code
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
