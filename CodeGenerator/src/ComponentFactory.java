import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public void GenerateROM(String TargetFile, String ROMName, int AdrSize, int WrdSize, int Content[]) {
		String component = CreateComponent("Blank");
		component = component.replace("[FileComment]", "-- Auto generated ROM" + System.lineSeparator() + "-- Date: ");
		String port = 	"PORT" + System.lineSeparator()
						+ "  (" + System.lineSeparator()
						+ "    p_address  :  in  std_logic_vector(" + AdrSize + " DOWNTO 0);" + System.lineSeparator()
						+ "    p_word     :  out std_logic_vector(" + WrdSize + " DOWNTO 0)" + System.lineSeparator()
						+ "  );" + System.lineSeparator();
		String behavior = "WITH p_address SELECT p_word <=" + System.lineSeparator();
		for (int i = 0; i < Content.length; i++) {
			behavior += "    \"" + String.format("%" + WrdSize + "s", Integer.toBinaryString(Content[i])).replace(' ', '0') + "\" WHEN \""
						+ String.format("%" + AdrSize + "s",Integer.toBinaryString(i)).replace(' ', '0');
			if (i < Content.length - 1) {
				behavior += "\",";
			} else {
				behavior += "\";";
			}
			behavior += System.lineSeparator();
		}
		component = component.replace("[ComponentName]", ROMName);
		component = component.replace("[Port]", port);
		component = component.replace("[Behavior]", behavior);
		component = CleanComponent(component);
		System.out.println(component);
	}
	
	public void GenerateMux(String TargetFile, String MuxName, int AdrSize, int WrdSize) {
		
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
}
