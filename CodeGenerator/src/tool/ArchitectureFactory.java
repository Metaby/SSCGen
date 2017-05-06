package tool;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import wrapper.*;

class ArchitectureFactory {
	
	Architecture ReadSpecification(String SpecPath) {
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
	
	Boolean ValidateSpecification(String SpecPath, String Schema) {
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
	
	Boolean ValidateIds(Architecture arch) {
		List<String> ids = new ArrayList<String>();
		for (Register reg : arch.getRegisters()) {
			if (ids.contains(reg.getId())) {
				System.out.println("Error: Id \"" + reg.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(reg.getId());
			}
		}
		for (Rom rom : arch.getRoms()) {
			if (ids.contains(rom.getId())) {
				System.out.println("Error: Id \"" + rom.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(rom.getId());
			}
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			if (ids.contains(rf.getId())) {
				System.out.println("Error: Id \"" + rf.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(rf.getId());
			}
		}
		for (Alu alu : arch.getAlus()) {
			if (ids.contains(alu.getId())) {
				System.out.println("Error: Id \"" + alu.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(alu.getId());
			}
		}
		for (Multiplexer mux : arch.getMultiplexers()) {
			if (ids.contains(mux.getId())) {
				System.out.println("Error: Id \"" + mux.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(mux.getId());
			}
		}
		return true;
	}

	Boolean ValidateConnections(Architecture arch) {
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
	
	private String getImport(VhdlComponent component) {
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
	
	List<String> GenerateControlVector(Architecture arch) {
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
		for (Multiplexer jl : arch.getMultiplexers()) {
			cv.addAll(jl.getControlVector());
		}
		return cv;
	}
	
	private Map<String, Integer> outputSize;
	
	void GenerateArchitecture(String directory, Architecture arch) {
		outputSize = new HashMap<String, Integer>();
		ComponentFactory factory = new ComponentFactory(directory);
		List<VhdlComponent> archComponents = new ArrayList<VhdlComponent>();
		new File(directory + "/components/").mkdirs();
		InstanceFactory instFact = new InstanceFactory(arch);
		String behavior = "";
		for (Register reg : arch.getRegisters()) {
			archComponents.add(factory.generateComponent(reg));
			//behavior += getInstance(reg) + System.lineSeparator();
			behavior += instFact.generateInstance(reg) + System.lineSeparator();
			outputSize.put(reg.getId(), reg.getWordSize());
		}
		for (Rom rom : arch.getRoms()) {
			archComponents.add(factory.generateComponent(rom));
			//behavior += getInstance(rom) + System.lineSeparator();
			behavior += instFact.generateInstance(rom) + System.lineSeparator();
			outputSize.put(rom.getId(), rom.getWordSize());
		}
		for (Multiplexer mux : arch.getMultiplexers()) {
			archComponents.add(factory.generateComponent(mux));
			//behavior += getInstance(mux) + System.lineSeparator();
			behavior += instFact.generateInstance(mux) + System.lineSeparator();
			outputSize.put(mux.getId(), mux.getWordSize());
		}
		for (Alu alu : arch.getAlus()) {
			archComponents.add(factory.generateComponent(alu));
			//behavior += getInstance(alu) + System.lineSeparator();
			behavior += instFact.generateInstance(alu) + System.lineSeparator();
			outputSize.put(alu.getId(), alu.getWordSize());
		}
		for (RegisterFile rf : arch.getRegisterFiles()) {
			archComponents.add(factory.generateComponent(rf));
			//behavior += getInstance(rf) + System.lineSeparator();
			System.out.println(instFact.generateInstance(rf));
			outputSize.put(rf.getId(), rf.getWordSize());
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
		topLevelEntity.setBehavior(replaceInfix(behavior, topLevelEntity));
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + directory + "processor.vhdl" + ")");
		}
	}
	
	private String replaceInfix(String behavior, VhdlComponent vc) {
		Pattern p = Pattern.compile("[\\\'|\\\"][01]+[\\\'|\\\"]\\ &\\ [a-zA-Z0-9\\_]+");
		Matcher m = p.matcher(behavior);
		int cnt = 0;
		while (m.find()) {
			behavior = behavior.replaceFirst("[\\\'|\\\"][01]+[\\\'|\\\"]\\ &\\ [a-zA-Z0-9\\_]+", "s_tmp_" + cnt);
			vc.AddSignal("s_tmp_" + cnt + " : std_logic_vector(" + getSize(m.group(), vc) + " DOWNTO 0)");
			behavior += "  s_tmp_" + cnt + " <= " + m.group() + ";" + System.lineSeparator();
			cnt++;
		}
		return behavior;
	}
	
	private int getSize(String infix, VhdlComponent vc) {
		Pattern p = Pattern.compile("[\\\'|\\\"][01]+[\\\'|\\\"]");
		Matcher m = p.matcher(infix);
		if (m.find()) {
			int zeros = m.group().length() - 2;
			Pattern p2 = Pattern.compile("\\ [a-zA-Z0-9\\_]+");
			Matcher m2 = p2.matcher(infix);
			if (m2.find()) {
				for (int i = 0; i < vc.getSignals().size(); i++) {
					if (vc.getSignals().get(i).startsWith(m2.group().trim())) {
						String str = vc.getSignals().get(i);
						str = str.substring(str.indexOf('(') + 1);
						str = str.substring(0, str.indexOf(' '));
						return zeros + Integer.parseInt(str);
					}
				}
				return zeros;
			}
			return 0;
		}
		return 0;
	}
	
	private int ctrlVectorIndex = 0;
	
	private String generateSignal(Connector con, int cSize, int wSize) {
		String signal = "";
		if (cSize < wSize) {
			int diff = wSize - cSize;
			if (diff > 1) {
				signal += "\"";
				for (int i = 0; i < diff; i++) {
					signal += "0";
				}
				signal += "\" & ";
			} else {
				signal += "\'0\' & ";
			}
		}
		if (con.lowerBound == -1 && con.upperBound == -1) {
			signal += con.toSignal() + ", ";				
		} else {
			signal += con.toSignal() + "(" + con.upperBound + " DOWNTO " + con.lowerBound + "), ";
		}
		return signal;
	}
	
	private String getInstance(Register reg) {
		String instance = "";
		int iselSize = log2(reg.getInputs().size());
		instance += "  " + reg.getId() + "_instance : " + reg.getId();
		instance += " GENERIC MAP(";
		instance += "g_word_size => " + (reg.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Clock
		instance += "p_clk, ";
		// Reset
		instance += "p_reset, ";
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
			instance += generateSignal(ic, getSize(ic), reg.getWordSize());
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

	private String getInstance(Rom rom) {
		String instance = "";
		int aselSize = log2(rom.getAddresses().size());
		instance += "  " + rom.getId() + "_instance : " + rom.getId();
		instance += " GENERIC MAP(";
		instance += "g_addressSize => " + (rom.getAddressSize() - 1) + ", ";
		instance += "g_wordSize => " + (rom.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Inputs
		for (Connector ic : rom.getAddresses()) {
			instance += generateSignal(ic, getSize(ic), rom.getAddressSize());
		}
		// Asel
		if (rom.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (aselSize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (aselSize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + aselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
			}
		} else if (rom.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (aselSize == 1) {
				instance += "\'" + getBits(rom.getControl().constValue, 1, 1) + "\', ";
			} else if (aselSize > 1) {
				instance += "\"" + getBits(rom.getControl().constValue, 1, aselSize) + "\", ";
			}
		} else {
			if (aselSize == 1) {
				instance += rom.getControl().toSignal() + "(1), ";
			} else if (aselSize > 1) {
				instance += rom.getControl().toSignal() + "(" + aselSize + " DOWNTO 1), ";
			}
		}
		// Output
		instance += rom.getOutput().toSignal() + ");";
		return instance;
	}
	
	private String getInstance(Alu alu) {
		String instance = "";
		int iselASize = log2(alu.getInputsA().size());
		int iselBSize = log2(alu.getInputsB().size());
		int cselSize = log2(alu.getOperations().size() + alu.getConditions().size());
		instance += "  " + alu.getId() + "_instance : " + alu.getId();
		instance += " GENERIC MAP(";
		instance += "g_word_size => " + (alu.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Inputs
		for (Connector ic : alu.getInputsA()) {
			instance += generateSignal(ic, getSize(ic), alu.getWordSize());
		}
		for (Connector ic : alu.getInputsB()) {
			instance += generateSignal(ic, getSize(ic), alu.getWordSize());
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
	
	private String getInstance(Multiplexer mux) {
		String instance = "";
		int iselSize = log2(mux.getInputs().size());
		instance += "  " + mux.getId() + "_instance : " + mux.getId();
		instance += " GENERIC MAP(";
		instance += "g_word_size => " + (mux.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		// Inputs
		for (Connector ic : mux.getInputs()) {
			instance += generateSignal(ic, getSize(ic), mux.getWordSize());
		}
		// Isel
		if (mux.getControl().type == ConnectorType.SYSTEM_AUTO) {
			if (iselSize == 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
			} else if (iselSize > 1) {
				instance += "s_ctrl_vector(" + (ctrlVectorIndex + iselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
			}
		} else if (mux.getControl().type == ConnectorType.SYSTEM_CONST) {
			if (iselSize == 1) {
				instance += "\'" + getBits(mux.getControl().constValue, 0, 0) + "\', ";
			} else if (iselSize > 1) {
				instance += "\"" + getBits(mux.getControl().constValue, 0, iselSize - 1) + "\", ";
			}
		} else {
			if (iselSize == 1) {
				instance += mux.getControl().toSignal() + ", ";
			} else if (iselSize > 1) {
				instance += mux.getControl().toSignal() + "(" + iselSize + " DOWNTO 0), ";
			}			
		}
		// Output
		instance += mux.getOutput().toSignal() + ");";
		return instance;
	}
	
	private String getInstance(RegisterFile rf) {
		String instance = "";
		instance += "  " + rf.getId() + "_instance : " + rf.getId();
		instance += " GENERIC MAP(";
		instance += "g_address_size => " + (rf.getAddressSize() - 1) + ", ";
		instance += "g_word_size => " + (rf.getWordSize() - 1);
		instance += ") ";
		instance += " PORT MAP(";
		instance += "p_clk, ";
		int offset = 0;
		for (int i = 0; i < rf.getPorts().size(); i++) {
			Port p = rf.getPorts().get(i);
			int aselSize = log2(p.getAddresses().size());
			if (p.getDirection() == PortDirection.IN) {
				int iselSize = log2(p.getInputs().size());
				for (Connector ic : p.getInputs()) {
					instance += generateSignal(ic, getSize(ic), rf.getWordSize());
				}
				for (Connector ac : p.getAddresses()) {
					instance += generateSignal(ac, getSize(ac), rf.getWordSize());
				}
				// Isel
				if (rf.getControl().type == ConnectorType.SYSTEM_AUTO) {
					if (iselSize == 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
					} else if (iselSize > 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex + iselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
					}
				} else if (rf.getControl().type == ConnectorType.SYSTEM_CONST) {
					if (iselSize == 1) {
						instance += "\'" + getBits(rf.getControl().constValue, offset, offset) + "\', ";
					} else if (iselSize > 1) {
						instance += "\"" + getBits(rf.getControl().constValue, iselSize + offset - 1, offset) + "\", ";
						offset += iselSize;
					}
				} else {
					if (iselSize == 1) {
						instance += rf.getControl().toSignal() + "(" + offset + "), ";
					} else if (iselSize > 1) {
						instance += rf.getControl().toSignal() + "(" + (iselSize + offset - 1) + " DOWNTO " + offset + "), ";
						offset += iselSize;
					}			
				}
				// Asel
				if (rf.getControl().type == ConnectorType.SYSTEM_AUTO) {
					if (aselSize == 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
					} else if (aselSize > 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex + aselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
					}
					instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
				} else if (rf.getControl().type == ConnectorType.SYSTEM_CONST) {
					if (aselSize == 1) {
						instance += "\'" + getBits(rf.getControl().constValue, offset, offset) + "\', ";
					} else if (aselSize > 1) {
						instance += "\"" + getBits(rf.getControl().constValue, aselSize + offset - 1, offset) + "\", ";
						offset += aselSize;
					}
					instance += "\'" + getBits(rf.getControl().constValue, offset, offset) + "\'";
					offset++;
				} else {
					if (aselSize == 1) {
						instance += rf.getControl().toSignal() + "(" + offset + "), ";
					} else if (aselSize > 1) {
						instance += rf.getControl().toSignal() + "(" + (aselSize + offset - 1) + " DOWNTO " + offset + "), ";
						offset += aselSize;
					}
					if (rf.getControl().size > 1) {
						instance += rf.getControl().toSignal() + "(" + offset + "), ";						
					} else {
						instance += rf.getControl().toSignal() + ", ";						
					}
					offset++;
				}
			} else if (p.getDirection() == PortDirection.OUT) {
				for (Connector ac : p.getAddresses()) {
					instance += generateSignal(ac, getSize(ac), rf.getWordSize());
				}
				// Asel
				if (rf.getControl().type == ConnectorType.SYSTEM_AUTO) {
					if (aselSize == 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex++) + "), ";
					} else if (aselSize > 1) {
						instance += "s_ctrl_vector(" + (ctrlVectorIndex + aselSize) + " DOWNTO " + ctrlVectorIndex + "), ";
					}
				} else if (rf.getControl().type == ConnectorType.SYSTEM_CONST) {
					if (aselSize == 1) {
						instance += "\'" + getBits(rf.getControl().constValue, offset, offset) + "\', ";
					} else if (aselSize > 1) {
						instance += "\"" + getBits(rf.getControl().constValue, aselSize + offset - 1, offset) + "\", ";
						offset += aselSize;
					}
				} else {
					if (aselSize == 1) {
						instance += rf.getControl().toSignal() + "(" + offset + "), ";
					} else if (aselSize > 1) {
						instance += rf.getControl().toSignal() + "(" + (aselSize + offset - 1) + " DOWNTO " + offset + "), ";
						offset += aselSize;
					}			
				}
				instance += p.getOutput().toSignal() + ", ";
			}
		}
		instance = instance.substring(0, instance.length() - 2);
		return instance + ");";
	}
	
	private int getSize(Connector con) {
		if (outputSize.containsKey(con.origin)) {
			return outputSize.get(con.origin);			
		} else {
			return con.size;
		}
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
