package Components;

public class Stack extends RegisterComponent {
	
	private int size;

	public Stack(String Id) {
		super(Id);
		size = 0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
