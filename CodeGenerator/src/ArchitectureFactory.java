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

// TODO: Wrapper-Classes selber schreiben
public class ArchitectureFactory {
	
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
			for (int j = 0; j < alu.getInputsOperandA().getInput().size(); j++) {
				inputs.add(alu.getInputsOperandA().getInput().get(j));
			}
			outputs.add(alu.getId() + "." + alu.getOutput());
			outputs.add(alu.getId() + "." + alu.getStatusFlags());
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
		for (int i = 0; i < arch.getRom().size(); i++) {
			Rom rom = arch.getRom().get(i);
			for (int j = 0; j < rom.getAddresses().getAddress().size(); j++) {
				inputs.add(rom.getAddresses().getAddress().get(j));
			}
			outputs.add(rom.getId() + "." + rom.getOutput());
		}
		for (int i = 0; i < arch.getJumpLogic().size(); i++) {
			JumpLogic jl = arch.getJumpLogic().get(i);
			for (int j = 0; j < jl.getProgramTargetA().getInput().size(); j++) {
				inputs.add(jl.getProgramTargetA().getInput().get(j));
			}
			for (int j = 0; j < jl.getProgramTargetB().getInput().size(); j++) {
				inputs.add(jl.getProgramTargetB().getInput().get(j));
			}
			inputs.add(jl.getInputFlags());
			outputs.add(jl.getId() + "." + jl.getOutput());
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
		for (int i = 0; i < arch.getRegisterFile().size(); i++) {
			RegisterFile rf = arch.getRegisterFile().get(i);
			for (int j = 0; j < rf.getPorts().getPort().size(); j++) {
				Port p = rf.getPorts().getPort().get(j);
				if (p.getInputs() != null) {
					int adrBits = (int)Math.ceil(Math.log(p.getInputs().getInput().size()) / Math.log(2));
					for (int k = 0; k < adrBits; k++) {
						cv.add(rf.getId() + "_p" + j + "_isel_" + k);
					}					
				}
				if (p.getAddresses() != null) {
					int adrBits = (int)Math.ceil(Math.log(p.getAddresses().getAddress().size()) / Math.log(2));
					for (int k = 0; k < adrBits; k++) {
						cv.add(rf.getId() + "_p" + j + "_asel_" + k);
					}					
				}
				if (p.getType() == PortDirection.IN || p.getType() == PortDirection.IN_OUT) {
					cv.add(rf.getId() + "_p" + j + "_write");
				}
			}
		}
		for (int i = 0; i < arch.getAlu().size(); i++) {
			Alu alu = arch.getAlu().get(i);
			int adrBits = (int)Math.ceil(Math.log(alu.getInputsOperandA().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(alu.getId() + "_op1_isel_" + j);
			}
			adrBits = (int)Math.ceil(Math.log(alu.getInputsOperandB().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(alu.getId() + "_op2_isel_" + j);
			}
			int opsCnt = alu.getOperations().getOperation().size();
			int cndCnt = alu.getConditions().getCondition().size();
			int cmdBits = (int)Math.ceil(Math.log(opsCnt + cndCnt) / Math.log(2));
			for (int j = 0; j < cmdBits; j++) {
				cv.add(alu.getId() + "_csel_" + j);
			}
		}
		for (int i = 0; i < arch.getRegisterFile().size(); i++) {
			Memory mem = arch.getMemory().get(i);
			for (int j = 0; j < mem.getPorts().getPort().size(); j++) {
				Port p = mem.getPorts().getPort().get(j);
				if (p.getInputs() != null) {
					int adrBits = (int)Math.ceil(Math.log(p.getInputs().getInput().size()) / Math.log(2));
					for (int k = 0; k < adrBits; k++) {
						cv.add(mem.getId() + "_p" + j + "_isel_" + k);
					}					
				}
				if (p.getAddresses() != null) {
					int adrBits = (int)Math.ceil(Math.log(p.getAddresses().getAddress().size()) / Math.log(2));
					for (int k = 0; k < adrBits; k++) {
						cv.add(mem.getId() + "_p" + j + "_asel_" + k);
					}					
				}
				if (p.getType() == PortDirection.IN || p.getType() == PortDirection.IN_OUT) {
					cv.add(mem.getId() + "_p" + j + "_write");
				}
			}
		}
		for (int i = 0; i < arch.getJumpLogic().size(); i++) {
			JumpLogic jl = arch.getJumpLogic().get(i);
			int adrBits = (int)Math.ceil(Math.log(jl.getProgramTargetA().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(jl.getId() + "_pt1_isel_" + j);
			}
			adrBits = (int)Math.ceil(Math.log(jl.getProgramTargetB().getInput().size()) / Math.log(2));
			for (int j = 0; j < adrBits; j++) {
				cv.add(jl.getId() + "_pt2_isel_" + j);
			}
			int jcBits = (int)Math.ceil(Math.log(jl.getInputFlagsCnt().intValue()) / Math.log(2));
			for (int j = 0; j < jcBits; j++) {
				cv.add(jl.getId() + "_jcsel_" + j);
			}
		}
		return cv;
	}
}
