package wrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tool.ComponentBuilder;

public class Stack {
	
	private List<Connector> inputs;
	private Connector output;
	private String id;
	private int size;
	
	public Stack(jaxb.Stack stk) {
		id = stk.getId();
		size = stk.getSize();
		Connector outCon = new Connector();
		outCon.origin = stk.getId();
		outCon.pin = stk.getOutput();
		outCon.size = size;
		outCon.id = Connector.getNewId();
		output = outCon;
		inputs = new ArrayList<Connector>();
		for (int i = 0; i < stk.getInputs().getInput().size(); i++) {
			Connector inCon = new Connector();
			String input = stk.getInputs().getInput().get(i);
			inCon.origin = input.substring(0, input.indexOf("."));
			inCon.pin = input.substring(input.indexOf(".") + 1);
			inCon.size = size;
			inCon.id = Connector.getNewId();
			inputs.add(inCon);
		}
	}

	public List<String> getControlVector() {
		List<String> cv = new ArrayList<String>();
		cv.add(Wrapper.IntToRange(id + "_isel", (int)Math.ceil(Math.log(inputs.size()) / Math.log(2))));
		cv.add(id + "_incr");
		cv.add(id + "_decr");
		cv.add(id + "_write");
		cv.removeAll(Arrays.asList("", null));
		return cv;
	}
	
	public void generateComponent(String targetFile) {
		ComponentBuilder component = new ComponentBuilder(id);
		component.AddGeneric("g_addressSize : integer := " + (size - 1));
		component.AddGeneric("g_wordSize : integer := " + (size - 1));
		component.AddPort("p_clk : in std_logic");
		component.AddPort("p_write : in std_logic");
		component.AddPort("p_incr : in std_logic");
		component.AddPort("p_decr : in std_logic");
		for (int i = 0; i < inputs.size(); i++) {
			component.AddPort("p_input" + i + " : in std_logic_vector(g_wordSize DOWNTO 0)");
		}
		int adrSize = (int)Math.ceil(Math.log(inputs.size()) / Math.log(2));
		if (inputs.size() > 1) {
			if (adrSize > 1) {
				component.AddPort("p_inputSelect : in std_logic_vector(" + (adrSize - 1) + " DOWNTO 0)");				
			} else {
				component.AddPort("p_inputSelect : in std_logic");			
			}			
		}
		component.AddPort("p_word : out std_logic_vector(g_wordSize DOWNTO 0)");
		// TODO: fix stack in xsd, size->wordSize und addressSize hinzufügen
		component.AddType("TYPE registerArray IS ARRAY(" + (int)Math.pow(2, size) + " DOWNTO 0) OF std_logic_vector(g_wordSize DOWNTO 0)");
		component.AddSignal("s_registers : registerArray");
		component.AddSignal("s_input : std_logic_vector(g_wordSie DOWNTO 0");	
		component.AddSignal("s_pointer : std_logic_vector(g_addressSize DOWNTO 0)");
		String behavior = "";
		behavior += ComponentBuilder.generateMux("p_inputSelect", "s_input", "p_input", inputs.size());
		behavior += "  -- Behavior" + System.lineSeparator();
		behavior += "  PROCESS (p_clk) BEGIN" + System.lineSeparator();
		behavior += "    IF rising_edge(p_clk)" + System.lineSeparator();
		behavior += "      IF p_incr = '1' THEN" + System.lineSeparator();
		behavior += "        s_pointer <= s_pointer + 1;" + System.lineSeparator();
		behavior += "      ELSIF p_decr = '1' THEN" + System.lineSeparator();
		behavior += "        s_pointer <= s_pointer - 1;" + System.lineSeparator();
		behavior += "      ELSIF p_write = '1' THEN" + System.lineSeparator();
		behavior += "        s_registers(s_pointer) <= s_input;" + System.lineSeparator();		
		behavior += "      END IF;" + System.lineSeparator();
		behavior += "      p_word <= s_registers(s_pointer);" + System.lineSeparator();
		behavior += "    END IF;" + System.lineSeparator();
		behavior += "  END PROCESS;";
		component.setBehavior(behavior);
		File outputFile = new File(targetFile);
		try {
			Files.write(outputFile.toPath(), component.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + targetFile + ")");
		}	
	}
	
	public List<Connector> getInputs() {
		return inputs;
	}

	public Connector getOutput() {
		return output;
	}

	public String getId() {
		return id;
	}

	public int getSize() {
		return size;
	}
	
}
