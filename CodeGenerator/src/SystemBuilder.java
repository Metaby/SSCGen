import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import jaxbClasses.*;

public class SystemBuilder {
	
	public Architecture ReadSpecification(String SpecPath) {
		try {
			JAXBContext jc = JAXBContext.newInstance(Architecture.class);
			Unmarshaller u = jc.createUnmarshaller();
			Architecture arch = (Architecture)u.unmarshal(new File("Processors/mips/architecture.xml"));
			return arch;
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
		Set<String> inputs = new HashSet<String>();
		Set<String> outputs = new HashSet<String>();
		for (int i = 0; i < arch.getRegister().size(); i++) {
			Register reg = arch.getRegister().get(i);
			for (int j = 0; j < reg.getInputs().getInput().size(); j++) {
				inputs.add(reg.getInputs().getInput().get(j));
			}
			outputs.add(reg.getId() + "." + reg.getOutput());
		}
		for (int i = 0; i < arch.getStack().size(); i++) {
			Stack stk = arch.getStack().get(i);
			for (int j = 0; j < stk.getInputs().getInput().size(); j++) {
				inputs.add(stk.getInputs().getInput().get(j));
			}
			outputs.add(stk.getId() + "." + stk.getOutput());
		}
		for (int i = 0; i < arch.getAlu().size(); i++) {
			Alu alu = arch.getAlu().get(i);
			for (int j = 0; j < alu.getInputs().getInput().size(); j++) {
				inputs.add(alu.getInputs().getInput().get(j));
			}
			outputs.add(alu.getId() + "." + alu.getOutput());
		}
		for (int i = 0; i < arch.getMemory().size(); i++) {
			Memory mem = arch.getMemory().get(i);
			for (int j = 0; j < mem.getPorts().getPort().size(); j++) {
				Port p = mem.getPorts().getPort().get(j);
				if (p.getInputs() != null) {
					for (int k = 0; k < p.getInputs().getInput().size(); k++) {
						inputs.add(p.getInputs().getInput().get(k));
					}					
				}
				if (p.getAddresses() != null) {
					for (int k = 0; k < p.getAddresses().getAddress().size(); k++) {
						inputs.add(p.getAddresses().getAddress().get(k));
					}
				}
				if (p.getOutput() != null) {
					outputs.add(mem.getId() + "." + p.getOutput());					
				}
			}
		}
		for (int i = 0; i < arch.getRegisterFile().size(); i++) {
			RegisterFile rf = arch.getRegisterFile().get(i);
			for (int j = 0; j < rf.getPorts().getPort().size(); j++) {
				Port p = rf.getPorts().getPort().get(j);
				if (p.getInputs() != null) {
					for (int k = 0; k < p.getInputs().getInput().size(); k++) {
						inputs.add(p.getInputs().getInput().get(k));
					}					
				}
				if (p.getAddresses() != null) {
					for (int k = 0; k < p.getAddresses().getAddress().size(); k++) {
						inputs.add(p.getAddresses().getAddress().get(k));
					}
				}
				if (p.getOutput() != null) {
					outputs.add(rf.getId() + "." + p.getOutput());					
				}
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			String con = inputs.toArray()[i].toString();
			if (!con.startsWith("const.")) {
				if (con.contains("[")) {
					con = con.substring(0, con.indexOf("["));
				}
				if (!outputs.contains(con)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public List<String> GenerateControlVector(Architecture arch) {
		List<String> cv = new LinkedList<String>();
		for (int i = 0; i < arch.getRegister().size(); i++) {
			Register reg = arch.getRegister().get(i);
			cv.add(reg.getId() + "_" + "write");
			int adrBits = (int)Math.ceil(Math.log(reg.getInputs().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(reg.getId() + "_isel_" + j);
			}
		}
		for (int i = 0; i < arch.getStack().size(); i++) {
			Stack stk = arch.getStack().get(i);
			cv.add(stk.getId() + "_" + "incr");
			cv.add(stk.getId() + "_" + "decr");
			cv.add(stk.getId() + "_" + "write");
			int adrBits = (int)Math.ceil(Math.log(stk.getInputs().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(stk.getId() + "_isel_" + j);
			}
		}

		for (int i = 0; i < arch.getAlu().size(); i++) {
			Alu alu = arch.getAlu().get(i);
			int adrBits = (int)Math.ceil(Math.log(alu.getInputs().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(alu.getId() + "_op1_isel_" + j);
			}
			for (int j = 0; j < adrBits; j++) {
				cv.add(alu.getId() + "_op2_isel_" + j);
			}
			// TODO: alu in xsd fixen, operations und conditions sind keine listen wie vorgesehen
			// TODO: xsd nochmal überarbeiten damit weniger zwischen klassen existieren -> direkt listen
			// TODO: Operand A und B als attribut o.ä.
			// TODO: wordSize als Attribut in der Architektur
			// TODO: restlichen CV erzeugen
			int cmdCnt = alu.getOperations().getOperation().ordinal();
			int cndCnt = alu.getConditions().getCondition().ordinal();
			int cmdBits = (int)Math.ceil(Math.log(cmdCnt + cndCnt) / Math.log(2));
			System.out.println(cmdCnt + " . " + cndCnt + ". " + cmdBits);
		}
		return cv;
	}
}
