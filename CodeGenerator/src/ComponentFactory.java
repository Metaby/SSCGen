import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wrapper.Architecture;
import wrapper.Rom;

public class ComponentFactory {	
	
	private List<String> baseComponent;
	private String templateTags[] = new String[] {
		"[FileComment]", "[ComponentName]", "[Generic]",
		"[Port]", "[Signals]", "[Behavior]", "[Libraries]"
		};
	
	public ComponentFactory(String TemplateFilePath) {
		File f = new File(TemplateFilePath);
		if (Files.exists(f.toPath(), LinkOption.NOFOLLOW_LINKS)) {
			try {
				baseComponent = Files.readAllLines(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ERROR: Template not found");
		}
	}
	
	public String CreateComponent(String ComponentFilePath) {
		String component = "";
		if (ComponentFilePath != "Blank") {
			Map<String, String> tags = ReadTags(ComponentFilePath);
			List<String> template = new ArrayList<String>(baseComponent);
			for (int i = 0; i < template.size(); i++) {
				for (int j = 0; j < templateTags.length; j++) {					
					String tag = templateTags[j];
					if (template.get(i).contains(tag)) {
						if (!tags.get(tag).isEmpty()) {
							template.set(i, template.get(i).replace(tag, tags.get(tag)));
						}
					}
				}
				component += template.get(i) + System.lineSeparator();	
			}
		} else {
			for (int i = 0; i < baseComponent.size(); i++) {
				component += baseComponent.get(i) + System.lineSeparator();
			}
		}
		return component;
	}
	
	public String CleanComponent(String Component) {
		for (int j = 0; j < templateTags.length; j++) {
			Component = Component.replace(templateTags[j], "");
		}
		return Component;
	}
	
	public void GenerateComponent(String targetFile, String templateFile) {
		String component = CleanComponent(CreateComponent(templateFile));
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateROM(String targetFile, Rom rom) {
		String romName = rom.getId();
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
		String component = CreateComponent("Blank");
		component = component.replace("[FileComment]", "-- Auto generated ROM" + System.lineSeparator());
		String port = 	"PORT" + System.lineSeparator()
						+ "  (" + System.lineSeparator()
						+ "    p_address  :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0);" + System.lineSeparator()
						+ "    p_word     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)" + System.lineSeparator()
						+ "  );" + System.lineSeparator();
		String behavior = "WITH p_address SELECT p_word <=" + System.lineSeparator();
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
		component = component.replace("[ComponentName]", romName);
		component = component.replace("[Port]", port);
		component = component.replace("[Behavior]", behavior);
		component = CleanComponent(component);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}
	
	public void GenerateMux(String targetFile, String muxName, int addressSize, int wordSize) {
		String component = CreateComponent("Blank");
		component = component.replace("[FileComment]", "-- Auto generated Multiplexer" + System.lineSeparator());
		String port = 	"PORT" + System.lineSeparator() + "  (" + System.lineSeparator();
		if (addressSize > 1) {
			port += "    p_address    :  in  std_logic_vector(" + (addressSize - 1) + " DOWNTO 0);" + System.lineSeparator();			
		} else {
			port += "    p_address    :  in  std_logic;" + System.lineSeparator();			
		}
		for (int i = 0; i < Math.pow(2, addressSize); i++) {						
			port += "    p_input" + i + "     :  in  std_logic_vector(" + (wordSize - 1) + " DOWNTO 0);" + System.lineSeparator();
		}
		port += "    p_output     :  out std_logic_vector(" + (wordSize - 1) + " DOWNTO 0)" + System.lineSeparator();
		port += "  );" + System.lineSeparator();
		String behavior = "WITH p_address SELECT p_output <=" + System.lineSeparator();
		for (int i = 0; i < Math.pow(2, addressSize); i++) {
			behavior += "    p_input" + i + " WHEN \"" + String.format("%" + addressSize + "s", Integer.toBinaryString(i)).replace(' ', '0') + "\"," + System.lineSeparator();
		}
		char ca[] = behavior.toCharArray();
		ca[behavior.length() - 3] = ';';
		behavior = new String(ca);
		component = component.replace("[ComponentName]", muxName);
		component = component.replace("[Port]", port);
		component = component.replace("[Behavior]", behavior);		
		component = CleanComponent(component);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}
	}

	private Map<String, String> ReadTags(String TagDefinition) {
		File f = new File(TagDefinition);
		if (Files.exists(f.toPath(), LinkOption.NOFOLLOW_LINKS)) {
			try {
				List<String> definition = Files.readAllLines(f.toPath());
				Map<String, String> tags = new HashMap<String, String>();
				for (int i = 0; i < definition.size(); i++) {
					if (definition.get(i).matches("\\[[a-zA-Z0-9]+\\]")) {
						String tag = definition.get(i);
						String ins = "";
						i++;
						while (i < definition.size() && !definition.get(i).matches("\\[[a-zA-Z0-9]+\\]")) {
							ins += definition.get(i) + System.lineSeparator();
							i++;
						}
						i--;
						tags.put(tag, ins.trim());
					}
				}
				return tags;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ERROR: Tag definition not found");
		}
		return new HashMap<String, String>();
	}
	
	public void GenerateArchitecture(String directory, Architecture arch) {
		new File(directory).mkdirs();
		GenerateROM(directory + "programRom.vhdl", arch.getRoms().get(0));
		String muxName = "3mux32";
		GenerateMux(directory + muxName + ".vhdl", muxName, 3, 32);		
	}
}
