package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ComponentString;

public class Memory {

	private List<Port> ports;
	private String id;
	private int addressSize;
	private int wordSize;
	
	public Memory(jaxb.Memory mem) {
		id = mem.getId();
		addressSize = mem.getAddressSize();
		addressSize = mem.getWordSize();
		ports = new ArrayList<Port>();
		for (int i = 0; i < mem.getPorts().getPort().size(); i++) {
			ports.add(new Port(mem.getPorts().getPort().get(i), addressSize, wordSize, id));
		}
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		for (int j = 0; j < ports.size(); j++) {
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_isel", (int)Math.ceil(Math.log(ports.get(j).getInputs().size()) / Math.log(2))));
			cv.add(Wrapper.IntToRange(id + "_p" + j + "_asel", (int)Math.ceil(Math.log(ports.get(j).getAddresses().size()) / Math.log(2))));
			if (ports.get(j).getDirection() == PortDirection.IN || ports.get(j).getDirection() == PortDirection.IN_OUT) {
				cv.add(id + "_p" + j + "_write");				
			}
		}
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public void generateComponent(String targetFile) {
		ComponentString component = new ComponentString(id);
		component.AddGeneric("g_wordSize : integer := " + (wordSize - 1));
		component.AddGeneric("g_addressSize : integer := " + (addressSize - 1));
		String muxes = "";
		for (int j = 0; j < ports.size(); j++) {
			Port p = ports.get(j);
			if (p.getDirection() == PortDirection.IN) {
				for (int i = 0; i < p.getInputs().size(); i++) {
					component.AddPort("p_port" + j +  "_input" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
				}
				for (int i = 0; i < p.getAddresses().size(); i++) {
					component.AddPort("p_port" + j +  "_address" + i + " : in std_logic_vector(g_addressSize DOWNTO 0)");					
				}
				int adrBits = (int)Math.ceil(Math.log(p.getInputs().size()) / Math.log(2));
				if (p.getInputs().size() == 2) {
					component.AddPort("p_port" + j + "_inputSelect : in std_logic");
				} else if (p.getInputs().size() > 2) {
					component.AddPort("p_port" + j + "_inputSelect : in std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");					
				}
				adrBits = (int)Math.ceil(Math.log(p.getAddresses().size()) / Math.log(2));
				if (p.getAddresses().size() == 2) {
					component.AddPort("p_port" + j + "_addressSelect : in std_logic");
				} else if (p.getAddresses().size() > 2) {
					component.AddPort("p_port" + j + "_addressSelect : in std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");					
				}
				component.AddSignal("s_port" + j + "_inputSelect : std_logic_vector(g_addressSize DOWNTO 0)");
				muxes += ComponentString.generateMux("p_port" + j + "_inputSelect", "s_port" + j + "_inputSelect", "p_port" + j +  "_input", p.getInputs().size());
				component.AddSignal("s_port" + j + "_addressSelect : std_logic_vector(g_addressSize DOWNTO 0)");
				muxes += ComponentString.generateMux("p_port" + j + "_addressSelect", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddPort("p_port" + j + "_write : in std_logic");
			} else {
				for (int i = 0; i < p.getAddresses().size(); i++) {
					component.AddPort("p_port" + j +  "_address" + i + " : in std_logic_vector(g_addressSize DOWNTO 0)");					
				}
				int adrBits = (int)Math.ceil(Math.log(p.getAddresses().size()) / Math.log(2));
				if (p.getAddresses().size() == 2) {
					component.AddPort("p_port" + j + "_addressSelect : in std_logic");
				} else if (p.getAddresses().size() > 2) {
					component.AddPort("p_port" + j + "_addressSelect : in std_logic_vector(" + (adrBits - 1) + " DOWNTO 0)");					
				}
				component.AddSignal("s_port" + j + "_addressSelect : std_logic_vector(g_addressSize DOWNTO 0)");
				muxes += ComponentString.generateMux("p_port" + j + "_addressSelect", "s_port" + j + "_addressSelect", "p_port" + j +  "_address", p.getAddresses().size());
				component.AddPort("p_port" + j + "_output : out std_logic_vector(g_wordSize DOWNTO 0)");
			}
		}
		component.AddPort("p_clk : in std_logic");
		String behavior = "";
		behavior += "  -- this vhdl-component is only a interface for a custom memory --" + System.lineSeparator();
		behavior += "  -- please insert memory control logic here --" + System.lineSeparator();
		behavior += "  -- for a simple on-chip memory use RegisterFile instead --";
		component.setBehavior(muxes + behavior);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}

	public List<Port> getPorts() {
		return ports;
	}

	public String getId() {
		return id;
	}

	public int getAddressSize() {
		return addressSize;
	}

	public int getWordSize() {
		return wordSize;
	}
	
}
