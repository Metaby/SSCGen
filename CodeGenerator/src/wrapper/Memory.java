package wrapper;

import java.util.ArrayList;
import java.util.List;

public class Memory {

	private List<Port> ports;
	private String id;
	private MemoryType type;
	private int addressSize;
	private int wordSize;
	
	public Memory(jaxb.Memory mem) {
		if (mem.getType() == jaxb.MemoryType.INTERN) {
			type = MemoryType.INTERN;
		} else {
			type = MemoryType.EXTERN;			
		}
		id = mem.getId();
		addressSize = mem.getAddressSize();
		addressSize = mem.getWordSize();
		ports = new ArrayList<Port>();
		for (int i = 0; i < mem.getPorts().getPort().size(); i++) {
			ports.add(new Port(mem.getPorts().getPort().get(i), addressSize, wordSize, id));
		}
	}

	public List<Port> getPorts() {
		return ports;
	}

	public String getId() {
		return id;
	}

	public MemoryType getType() {
		return type;
	}

	public int getAddressSize() {
		return addressSize;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
