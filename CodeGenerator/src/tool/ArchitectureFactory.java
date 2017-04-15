package tool;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import wrapper.*;

public class ArchitectureFactory {
	
	public Architecture ReadSpecification(String SpecPath) {
		try {
			JAXBContext jc = JAXBContext.newInstance(jaxb.Architecture.class);
			Unmarshaller u = jc.createUnmarshaller();
			jaxb.Architecture arch = (jaxb.Architecture)u.unmarshal(new File(SpecPath));
			return new Architecture(arch);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean ValidateSpecification(String SpecPath, String Schema) {
		SchemaFactory f = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema s = f.newSchema(new File(Schema));
			s.newValidator().validate(new StreamSource(new File(SpecPath)));
		} catch (SAXException | IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public Boolean ValidateConnections(Architecture arch) {
		List<Connector> inputConnectors = arch.getInputConnectors();
		List<Connector> outputConnectors = arch.getOutputConnectors();
		for (Connector c : inputConnectors) {
			if (!outputConnectors.contains(c)) {
				if (!c.origin.startsWith(":")) {
					System.out.println("Error: Output-Connection \"" + c.origin + "." + c.pin + "\" used as input does not exist");
					return false;					
				}
			} else {
				int oSize = outputConnectors.get(outputConnectors.indexOf(c)).size;
				int iSize = c.size;
				if (iSize > oSize) {
					System.out.println("Error: Output-Connection-Size \"" + c.origin + "." + c.pin + "\" does not fit size of Input-Connection");
					return false;					
				}
			}
		}
		return true;
	}
	
	public List<String> GenerateControlVector(Architecture arch) {
		List<String> cv = new LinkedList<String>();
		for (Register reg : arch.getRegisters()) {
			cv.addAll(reg.getControlVector());
		}
		for (Stack stk : arch.getStacks()) {
			cv.addAll(stk.getControlVector());
		}
		for (Rom rom : arch.getRoms()) {
			cv.addAll(rom.getControlVector());
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			cv.addAll(rf.getControlVector());
		}
		for (Memory mem : arch.getMemories()) {
			cv.addAll(mem.getControlVector());
		}
		for (Alu alu : arch.getAlus()) {
			cv.addAll(alu.getControlVector());
		}
		for (JumpLogic jl : arch.getJumpLogics()) {
			cv.addAll(jl.getControlVector());
		}
		return cv;
	}
	
	public void GenerateArchitecture(String directory, Architecture arch) {
		new File(directory + "/components/").mkdirs();
		for (Register reg : arch.getRegisters()) {
			reg.generateComponent(directory + "/components/" + reg.getId() + ".vhdl");
		}
		for (Stack stk : arch.getStacks()) {
			stk.generateComponent(directory + "/components/" + stk.getId() + ".vhdl");
		}
		for (Rom rom : arch.getRoms()) {
			rom.generateComponent(directory + "/components/" + rom.getId() + ".vhdl");	
		}
		for (JumpLogic jl : arch.getJumpLogics()) {
			jl.generateComponent(directory + "/components/" + jl.getId() + ".vhdl");
		}
		for (Alu alu : arch.getAlus()) {
			alu.generateComponent(directory + "/components/" + alu.getId() + ".vhdl");
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			rf.generateComponent(directory + "/components/" + rf.getId() + ".vhdl");
		}		
		for (Memory mem : arch.getMemories()) {
			mem.generateComponent(directory + "/components/" + mem.getId() + ".vhdl");
		}
		ComponentBuilder topLevelEntity = new ComponentBuilder("processor");
		for (Connector con : arch.getOutputConnectors()) {
			if (con != null && con.size > 0 && !con.pin.startsWith(":")) {
				String signal = "s_" + con.origin + "_" + con.pin + " : ";
				if (con.size == 1) {
					signal += "std_logic";
				} else {
					signal += "std_logic_vector(" + (con.size - 1) + " DOWNTO 0)";
				}
				topLevelEntity.AddSignal(signal);				
			}
		}
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + directory + "processor.vhdl" + ")");
		}	
	}	
	
}
