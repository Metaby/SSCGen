import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import wrapper.Architecture;
import wrapper.Rom;

import static java.nio.file.StandardCopyOption.*;

public class ComponentFactory {	

	public void GenerateROM(String targetFile, Rom rom) {
		int addressSize = rom.getAddressSize();
		int wordSize = rom.getWordSize();
		int content[] = new int[] { 1 };
		File inputFile = new File(rom.getContentFile());
		if (!inputFile.exists()) {
			System.out.println("Error: Content file does not exist. (" + rom.getContentFile() + ")");
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
				System.out.println("Error: Wrong file format. (" + rom.getContentFile() + ")");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Reading content file. (" + rom.getContentFile() + ")");
			return;
		}
		ComponentBuilder component = new ComponentBuilder(rom.getId());
		component.AddPort("p_address  :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0)");
		component.AddPort("p_word     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		String behavior = "  WITH p_address SELECT p_word <=" + System.lineSeparator();
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
		component.setBehavior(behavior.substring(0, behavior.length() - 2));
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateMux(String targetFile, String muxName, int addressSize, int wordSize) {
		ComponentBuilder c = new ComponentBuilder(muxName);
		if (addressSize > 1) {
			c.AddPort("p_address    :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0)");
		} else {
			c.AddPort("p_address    :  in  std_logic;");
		}
		for (int i = 0; i < Math.pow(2, addressSize); i++) {						
			c.AddPort("p_input" + i + "     :  in  std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		}
		c.AddPort("p_output     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)");
		String behavior = "  WITH p_address SELECT p_output <=" + System.lineSeparator();
		for (int i = 0; i < Math.pow(2, addressSize); i++) {
			behavior += "    p_input" + i + " WHEN \"" + String.format("%" + addressSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"," + System.lineSeparator();
		}
		char ca[] = behavior.toCharArray();
		ca[behavior.length() - 3] = ';';
		behavior = new String(ca);
		c.setBehavior(behavior.substring(0, behavior.length() - 2));
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), c.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateArchitecture(String directory, Architecture arch) {
		new File(directory).mkdirs();
		if (arch.getRegisters().size() > 0) {
			try {
				Files.copy(new File("processors/components/Register.vhdl").toPath(), new File(directory + "Register.vhdl").toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (arch.getStacks().size() > 0) {
			try {
				Files.copy(new File("processors/components/Stack.vhdl").toPath(), new File(directory + "Stack.vhdl").toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		GenerateMux(directory + "4t1mux8.vhdl", "4t1mux8", 2, 8);
		for (Rom rom : arch.getRoms()) {
			GenerateROM(directory + rom.getId() + ".vhdl", rom);			
		}
	}
}
