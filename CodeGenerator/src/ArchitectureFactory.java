import java.io.File;
import java.io.IOException;
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
			jaxb.Architecture arch = (jaxb.Architecture)u.unmarshal(new File("Processors/mips/architecture.xml"));
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
				if (!c.origin.equals("const")) {
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
	
}
