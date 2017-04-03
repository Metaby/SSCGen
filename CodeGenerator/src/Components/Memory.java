package Components;

public class Memory extends RegisterFile {

	private String type;
	
	public Memory(String Id) {
		super(Id);
		type = "OnChip";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
