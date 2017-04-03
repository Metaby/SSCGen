package Components;

public class Register extends RegisterComponent {

	private Boolean gpio;
	
	public Register(String Id) {
		super(Id);
		gpio = false;
	}

	public Boolean getGpio() {
		return gpio;
	}

	public void setGpio(Boolean gpio) {
		this.gpio = gpio;
	}

}
