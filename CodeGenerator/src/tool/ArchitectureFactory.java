package tool;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
	
	public Boolean ValidateIds(Architecture arch) {
		List<String> ids = new ArrayList<String>();
		for (Register reg : arch.getRegisters()) {
			if (ids.contains(reg.getId())) {
				System.out.println("Error: Id already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(reg.getId());
			}
		}
		for (Rom rom : arch.getRoms()) {
			if (ids.contains(rom.getId())) {
				return false;
			} else {
				ids.add(rom.getId());
			}
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			if (ids.contains(rf.getId())) {
				return false;
			} else {
				ids.add(rf.getId());
			}
		}
		for (Alu alu : arch.getAlus()) {
			if (ids.contains(alu.getId())) {
				return false;
			} else {
				ids.add(alu.getId());
			}
		}
		for (JumpLogic jl : arch.getJumpLogics()) {
			if (ids.contains(jl.getId())) {
				return false;
			} else {
				ids.add(jl.getId());
			}
		}
		return true;
	}

	public Boolean ValidateConnections(Architecture arch) {
		List<Connector> inputConnectors = arch.getInputConnectors(false);
		inputConnectors.remove(null);
		List<Connector> outputConnectors = arch.getOutputConnectors(false);
		outputConnectors.remove(null);
		for (Connector inputCon : inputConnectors) {
			if (!outputConnectors.contains(inputCon)) {
				if (!inputCon.origin.startsWith("system")) {
					System.out.println("Error: Input \"" + inputCon.origin + "." + inputCon.pin + "\" does not exist");
					return false;					
				}
			} else {
				Connector sourceCon = outputConnectors.get(outputConnectors.indexOf(inputCon));
				if (sourceCon.size > inputCon.size) {
					if (inputCon.lowerBound != -1 && inputCon.upperBound != -1) {
						int lb = inputCon.lowerBound;
						int ub = inputCon.upperBound;
						if (ub - lb + 1 > inputCon.size) {
							System.out.println("Error: Source-Connection-Size \"" + inputCon.origin + "." + inputCon.pin + "\" does not fit size of Input-Connection");
							return false;
						} else if (ub - lb + 1 < inputCon.size) {
							System.out.println("Warning: Input-Connection-Size is lower than expected, Zeros are added as MSBs");
							System.out.println("\t> " + inputCon.origin + "." + inputCon.pin);				
						}
					} else {
						System.out.println("Error: Source-Connection-Size \"" + inputCon.origin + "." + inputCon.pin + "\" does not fit size of Input-Connection");
						return false;
					}
				} else if (sourceCon.size < inputCon.size) {
					System.out.println("Warning: Input-Connection-Size is lower than expected, Zeros are added as MSBs");
					System.out.println("\t> " + inputCon.origin + "." + inputCon.pin);	
					
				}
			}
		}
		return true;
	}
	
	public String getImport(VhdlComponent component) {
		String imprt = "";
		imprt += "  COMPONENT " + component.getName() + System.lineSeparator();
		if (component.getGenerics().size() > 0) {
			imprt += "    GENERIC (" + System.lineSeparator();
			for (int i = 0; i < component.getGenerics().size(); i++) {
				if (i < component.getGenerics().size() - 1) {
					imprt += "      " + component.getGenerics().get(i) + ";" + System.lineSeparator();					
				} else {
					imprt += "      " + component.getGenerics().get(i) + System.lineSeparator();
				}
			}
			imprt += "    );" + System.lineSeparator();
		}
		if (component.getPorts().size() > 0) {
			imprt += "    PORT (" + System.lineSeparator();
			for (int i = 0; i < component.getPorts().size(); i++) {
				if (i < component.getPorts().size() - 1) {
					imprt += "      " + component.getPorts().get(i) + ";" + System.lineSeparator();
				} else {
					imprt += "      " + component.getPorts().get(i) + System.lineSeparator();					
				}
			}
			imprt += "    );" + System.lineSeparator();
		}
		imprt += "  END COMPONENT;";
		return imprt;
	}
	
	public int getLowerBound(String pin) {
		int lp = pin.indexOf('[');
		int sep = pin.indexOf(',');
		return Integer.parseInt(pin.substring(lp + 1, sep));
	}
	
	public int getUpperBound(String pin) {
		int rp = pin.indexOf(']');
		int sep = pin.indexOf(',');
		return Integer.parseInt(pin.substring(sep + 1, rp));
	}
	
	public List<String> GenerateControlVector(Architecture arch) {
		List<String> cv = new LinkedList<String>();
		for (Register reg : arch.getRegisters()) {
			cv.addAll(reg.getControlVector());
		}
		for (Rom rom : arch.getRoms()) {
			cv.addAll(rom.getControlVector());
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			cv.addAll(rf.getControlVector());
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
		ComponentFactory factory = new ComponentFactory(directory);
		List<VhdlComponent> archComponents = new ArrayList<VhdlComponent>();
		new File(directory + "/components/").mkdirs();
		String behavior = "";
		for (Register reg : arch.getRegisters()) {
			archComponents.add(factory.generateComponent(reg));
			behavior += getInstance(reg) + System.lineSeparator();
		}
		for (Rom rom : arch.getRoms()) {
			VhdlComponent rc = factory.generateComponent(rom);
			if (rc == null) {
				System.out.println("Error");
				System.exit(-1);
			}
			archComponents.add(rc);
		}
		for (JumpLogic jl : arch.getJumpLogics()) {
			archComponents.add(factory.generateComponent(jl));
		}
		for (Alu alu : arch.getAlus()) {
			archComponents.add(factory.generateComponent(alu));
			behavior += getInstance(alu) + System.lineSeparator();
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			archComponents.add(factory.generateComponent(rf));
		}
		VhdlComponent topLevelEntity = new VhdlComponent("processor");
		topLevelEntity.AddGeneric("g_word_size : integer := " + (arch.getWordSize() - 1));
		topLevelEntity.AddPort("p_clk : in std_logic");
		topLevelEntity.AddPort("p_reset : in std_logic");
		for (Connector con : arch.getInputConnectors(true)) {
			if (con != null) {
				if (con.type == ConnectorType.SYSTEM_IN) {
					if (con.size > 1) {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in std_logic_vector(" + (con.size - 1) + " DOWNTO 0)");							
					} else {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in std_logic");							
					}						
				}			
			}
		}
		for (Connector con : arch.getOutputConnectors(true)) {
			if (con != null) {
				if (con.type == ConnectorType.SYSTEM_OUT) {
					if (con.size > 1) {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : out std_logic_vector(" + (con.size - 1) + " DOWNTO 0)");							
					} else {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : out std_logic");							
					}
				} else if (con.type == ConnectorType.SYSTEM_IN) {
					if (con.size > 1) {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in std_logic_vector(" + (con.size - 1) + " DOWNTO 0)");							
					} else {
						topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in std_logic");							
					}						
				} else if (con.size > 0 && con.type == ConnectorType.STANDARD) {
					String signal = "s_" + con.origin + "_" + con.pin + " : ";
					if (con.size == 1) {
						signal += "std_logic";
					} else {
						signal += "std_logic_vector(" + (con.size - 1) + " DOWNTO 0)";
					}
					topLevelEntity.AddSignal(signal);				
				}				
			}
		}
		for (VhdlComponent vc : archComponents) {
			topLevelEntity.AddImport(getImport(vc));
		}
		if (ctrlVectorIndex == 1) {
			topLevelEntity.AddSignal("s_ctrl_vector : std_logic");			
		} else if (ctrlVectorIndex > 1) {
			topLevelEntity.AddSignal("s_ctrl_vector : std_logic_vector(" + (ctrlVectorIndex - 1) + " DOWNTO 0)");			
		}
		topLevelEntity.setBehavior(behavior);
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + directory + "processor.vhdl" + ")");
		}
	}
	
	private int ctrlVectorIndex = 0;
	
	public String getInstance(Register reg) {
		String instance = "";
		int iselSize = log2(reg.getInputs().size());
		instance += "  " + reg.getId() + "_instance : ";
		instance += " GENERIC MAP(";
		instance += "g_word_size => " + (reg.getSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Clock
		instance += "p_clk, ";
		// Reset
		instance += "p_rst, ";
		// Write
		if (reg.getControl().type == ConnectorType.SYSTEM_AUTO) {
			instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";			
		} else if (reg.getControl().type == ConnectorType.SYSTEM_CONST) {
			instance += "\'" + getBits(reg.getControl().constValue, 0, 0) + "\', ";
		} else {
			instance += reg.getControl().toSignal() + "(0), ";
		}
		// Inputs
		for (Connector ic : reg.getInputs()) {
			instance += ic.toSignal() + ", ";
		}
		// Isel
		if (reg.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (iselSize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (iselSize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + iselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
			}
		} else if (reg.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (iselSize == 1) {
				instance += "\'" + getBits(reg.getControl().constValue, 1, 1) + "\', ";
			} else if (iselSize > 1) {
				instance += "\"" + getBits(reg.getControl().constValue, 1, iselSize) + "\", ";
			}
		} else {
			if (iselSize == 1) {
				instance += reg.getControl().toSignal() + "(1), ";
			} else if (iselSize > 1) {
				instance += reg.getControl().toSignal() + "(" + iselSize + " DOWNTO 1), ";
			}			
		}
		// Output
		instance += reg.getOutput().toSignal() + ");";
		return instance;
	}
	
	public String getInstance(Alu alu) {
		String instance = "";
		int iselASize = log2(alu.getInputsA().size());
		int iselBSize = log2(alu.getInputsB().size());
		int cselSize = log2(alu.getOperations().size() + alu.getConditions().size());
		instance += "  " + alu.getId() + "_instance : ";
		instance += " GENERIC MAP(";
		instance += "g_word_size => " + (alu.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Inputs
		for (Connector ic : alu.getInputsA()) {
			instance += ic.toSignal() + ", ";
		}
		for (Connector ic : alu.getInputsB()) {
			instance += ic.toSignal() + ", ";
		}
		int offset = 0;
		// Isel A
		if (alu.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (iselASize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (iselASize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + iselASize - 1) + " DOWNTO " + ctrlVectorIndex + "), ";
				ctrlVectorIndex += iselASize;
			}
		} else if (alu.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (iselASize == 1) {
				instance += "\'" + getBits(alu.getControl().constValue, 0, 0) + "\', ";
			} else if (iselASize > 1) {
				instance += "\"" + getBits(alu.getControl().constValue, 0, iselASize - 1) + "\", ";
				offset += iselASize;
			}
		} else {
			if (iselASize == 1) {
				instance += alu.getControl().toSignal() + "(0), ";
			} else if (iselASize > 1) {
				instance += alu.getControl().toSignal() + "(" + (iselASize - 1) + " DOWNTO 0), ";
				offset += iselASize;
			}
		}
		// Isel B
		if (alu.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (iselBSize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (iselBSize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + iselBSize - 1) + " DOWNTO " + ctrlVectorIndex + "), ";
				ctrlVectorIndex += iselBSize;
			}
		} else if (alu.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (iselBSize == 1) {
				instance += "\'" + getBits(alu.getControl().constValue, offset, offset) + "\', ";
			} else if (iselBSize > 1) {
				instance += "\"" + getBits(alu.getControl().constValue, iselBSize + offset - 1, offset) + "\", ";
				offset += iselBSize;
			}
		} else {
			if (iselBSize == 1) {
				instance += alu.getControl().toSignal() + "(" + offset + "), ";
			} else if (iselBSize > 1) {
				instance += alu.getControl().toSignal() + "(" + (iselBSize + offset - 1) + " DOWNTO " + offset + "), ";
				offset += iselBSize;
			}
		}
		// Csel
		if (alu.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (cselSize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (cselSize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + cselSize - 1) + " DOWNTO " + ctrlVectorIndex + "), ";
				ctrlVectorIndex += cselSize;
			}
		} else if (alu.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (cselSize == 1) {
				instance += "\'" + getBits(alu.getControl().constValue, offset, offset) + "\', ";
			} else if (cselSize > 1) {
				instance += "\"" + getBits(alu.getControl().constValue, cselSize + offset - 1, offset) + "\", ";
			}
		} else {
			if (cselSize == 1) {
				instance += alu.getControl().toSignal() + "(" + offset + "), ";
			} else if (cselSize > 1) {
				instance += alu.getControl().toSignal() + "(" + (cselSize + offset - 1) + " DOWNTO " + offset + "), ";
			}
		}
		// Flag
		if (alu.getStatus() != null) {
			instance += alu.getStatus().toSignal() + ", ";
		}
		// Output 1
		instance += alu.getOutput1().toSignal() + ", ";
		// Output 2
		instance += alu.getOutput2().toSignal() + ");";
		return instance;
	}
	
	private String getBits(int value, int start, int end) {
		String bits = "";
		for (int i = start; i < end + 1; i++) {
			if ((value & (1 << i)) == 0) {
				bits += "0";
			} else {
				bits += "1";
			}
		}
		return bits;
	}
	
	private int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}
}
