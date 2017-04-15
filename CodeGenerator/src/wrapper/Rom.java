package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ComponentBuilder;

public class Rom {

	private List<Connector> addresses;
	private Connector output;
	private String contentFile;
	private String id;
	private int addressSize;
	private int wordSize;
	
	public Rom(jaxb.Rom rom) {
		id = rom.getId();
		contentFile = rom.getContentFile();
		addressSize = rom.getAddressSize();
		wordSize = rom.getWordSize();
		Connector outCon = new Connector();
		outCon.origin = rom.getId();
		outCon.pin = rom.getOutput();
		outCon.size = wordSize;
		outCon.id = Connector.getNewId();
		output = outCon;
		addresses = new ArrayList<Connector>();
		for (int i = 0; i < rom.getAddresses().getAddress().size(); i++) {
			Connector inCon = new Connector();
			String input = rom.getAddresses().getAddress().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = addressSize;
			inCon.id = Connector.getNewId();
			addresses.add(inCon);
		}		
	}
	
	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_asel", (int)Math.ceil(Math.log(addresses.size()) / Math.log(2))));
		cv.add(id + "_write");
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}	

	public void generateComponent(String targetFile) {		
		int content[] = new int[] { 1 };
		File inputFile = new File(contentFile);
		if (!inputFile.exists()) {
			System.out.println("Error: Content file does not exist. (" + contentFile + ")");
			return;
		}
		try {
			List<String> strContent = Files.readAllLines(inputFile.toPath());
			if (strContent.size() > 0 && strContent.get(0).equals("v2.0 raw")) {
				String strBytes = "";
				for (int i = 1; i < strContent.size(); i++) {
					strBytes += strContent.get(i) + " ";
				}
				String strBytesArray[] = strBytes.split("\\s+");
				content = new int[strBytesArray.length];
				for (int i = 0; i < strBytesArray.length; i++) {
					content[i] = Integer.parseInt(strBytesArray[i], 16);
				}
			} else {
				System.out.println("Error: Wrong file format. (" + contentFile + ")");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Reading content file. (" + contentFile + ")");
			return;
		}
		ComponentBuilder component = new ComponentBuilder(id);
		component.AddGeneric("g_addressSize : integer := " + (addressSize - 1));
		component.AddGeneric("g_wordSize : integer := " + (wordSize - 1));
		for (int i = 0; i < addresses.size(); i++) {
			component.AddPort("p_address" + i + " : in std_logic_vector(g_addressSize DOWNTO 0)");
		}
		int adrSize = (int)Math.ceil(Math.log(addresses.size()) / Math.log(2));
		if (addresses.size() > 1) {
			if (adrSize > 1) {
				component.AddPort("p_addressSelect : in std_logic_vector(" + (adrSize - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_addressSelect : in std_logic");			
			}			
		}
		component.AddPort("p_word : out std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddSignal("s_address : std_logic_vector(g_addressSize DOWNTO 0");		
		String behavior = "";
		behavior += ComponentBuilder.generateMux("p_addrSelect", "s_address", "p_address", addresses.size());
		behavior += "  -- Behavior" + System.lineSeparator();
		behavior += "  WITH s_address SELECT p_word <=" + System.lineSeparator();
		for (int i = 0; i < content.length; i++) {
			behavior += "    \"" + String.format("%" + wordSize + "s", Integer.toBinaryString(content[i])).replace(' ', '0') + "\" WHEN \""
						+ String.format("%" + addressSize + "s",Integer.toBinaryString(i)).replace(' ', '0');
			behavior += "\",";
			behavior += System.lineSeparator();
		}
		if (content.length < Math.pow(2, addressSize)) {
			behavior += "    \"" + String.format("%" + wordSize + "s", Integer.toBinaryString(0)).replace(' ', '0') + "\" WHEN others;" + System.lineSeparator();			
		} else {
			char ca[] = behavior.toCharArray();
			ca[behavior.length() - 3] = ';';
			behavior = new String(ca);
		}
		component.setBehavior(behavior);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public List<Connector> getAddresses() {
		return addresses;
	}

	public Connector getOutput() {
		return output;
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

	public String getContentFile() {
		return contentFile;
	}
	
}
