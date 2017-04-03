package Components;

public class BaseComponent {
	
	private String id;
	
	public BaseComponent(String Id) {
		id = Id;
	}	
	
	public BaseComponent() {
		id = "";
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
