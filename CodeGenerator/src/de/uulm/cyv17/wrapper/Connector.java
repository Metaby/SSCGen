package de.uulm.cyv17.wrapper;

public class Connector {

	private int constValue;
	public ConnectorType type;
	public String origin;
	public String pin;
	public int lowerBound;
	public int upperBound;
	public int size;
	
	
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

	private int getLowerBound(String pin) {
		if (pin.contains("[")) {
			int lp = pin.indexOf('[');
			int sep = pin.indexOf(',');
			return Integer.parseInt(pin.substring(lp + 1, sep));
		} else {
			return -1;
		}
	}
	
	private int getUpperBound(String pin) {
		if (pin.contains("[")) {
			int rp = pin.indexOf(']');
			int sep = pin.indexOf(',');
			return Integer.parseInt(pin.substring(sep + 1, rp));
		} else {
			return -1;
		}
	}
	
	private String removeSubSel(String pin) {
		if (pin.contains("[")) {
			return pin.substring(0, pin.indexOf('['));			
		} else {
			return pin;
		}
	}
	
	private String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
	public String toSignal() {
		if (type == ConnectorType.STANDARD) {
			return "s_" + toString().replace('.', '_');
		} else if (type == ConnectorType.SYSTEM_CONST) {
			if (size > 1) {
				return "\"" + getBinaryString(constValue, size).substring(0, size) + "\"";
			} else {
				return "\'" + getBinaryString(constValue, size).substring(0, size) + "\'";
			}
		} else if (type == ConnectorType.SYSTEM_IN || type == ConnectorType.SYSTEM_OUT) {
			return "p_" + pin.replace('.', '_');			
		} else if (type == ConnectorType.SYSTEM_OPEN) {
			return "OPEN";
		} else if (type == ConnectorType.SYSTEM_CLOCK) {
			return "p_clk";
		}
		return "";
	}
	
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
