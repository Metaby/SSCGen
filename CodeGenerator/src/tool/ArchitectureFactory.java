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
import wrapper.entities.AluEntity;
import wrapper.entities.BaseEntity;
import wrapper.entities.MultiplexerEntity;
import wrapper.entities.RegisterEntity;
import wrapper.entities.RegisterFileEntity;
import wrapper.entities.RomEntity;

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
		for (BaseEntity be : arch.getAllEntites()) {
			System.out.println(be.getControlVector().toString());
			if (ids.contains(be.getId())) {
				System.out.println("Error: Id \"" + be.getId() + "\" already exist, Ids have to be unique");
				return false;
			} else {
				ids.add(be.getId());
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
//		for (RegisterEntity reg : arch.getRegisters()) {
//			cv.addAll(reg.getControlVector());
//		}
//		for (RomEntity rom : arch.getRoms()) {
//			cv.addAll(rom.getControlVector());
//		}
//		for (RegisterFileEntity rf : arch.getRegisterFiles()) {
//			cv.addAll(rf.getControlVector());
//		}
//		for (AluEntity alu : arch.getAlus()) {
//			cv.addAll(alu.getControlVector());
//		}
//		for (MultiplexerEntity jl : arch.getMultiplexers()) {
//			cv.addAll(jl.getControlVector());
//		}
		return cv;
	}
	
	void GenerateArchitecture(String directory, Architecture arch) {
		ComponentFactory factory = new ComponentFactory(directory);
		List<VhdlComponent> archComponents = new ArrayList<VhdlComponent>();
		new File(directory + "/components/").mkdirs();
		InstanceFactory instFact = new InstanceFactory(arch);
		String behavior = "";
		for (RegisterEntity reg : arch.getRegisters()) {
			archComponents.add(factory.generateComponent(reg));
			behavior += instFact.generateInstance(reg) + System.lineSeparator();
		}
		for (RomEntity rom : arch.getRoms()) {
			archComponents.add(factory.generateComponent(rom));
			behavior += instFact.generateInstance(rom) + System.lineSeparator();
		}
		for (MultiplexerEntity mux : arch.getMultiplexers()) {
			archComponents.add(factory.generateComponent(mux));
			behavior += instFact.generateInstance(mux) + System.lineSeparator();
		}
		for (AluEntity alu : arch.getAlus()) {
			archComponents.add(factory.generateComponent(alu));
			behavior += instFact.generateInstance(alu) + System.lineSeparator();
		}
		for (RegisterFileEntity rf : arch.getRegisterFiles()) {
			archComponents.add(factory.generateComponent(rf));
			behavior += instFact.generateInstance(rf);
		}
		VhdlComponent topLevelEntity = new VhdlComponent("processor");
		topLevelEntity.AddGeneric("g_word_size : integer := " + (arch.getWordSize() - 1));
		topLevelEntity.AddPort("p_clk : in", 1);
		topLevelEntity.AddPort("p_reset : in", 1);
		for (Connector con : arch.getInputConnectors(true)) {
			if (con != null) {
				if (con.type == ConnectorType.SYSTEM_IN) {
					topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in", con.size);
				}
			}
		}
		for (Connector con : arch.getOutputConnectors(true)) {
			if (con != null) {
				if (con.type == ConnectorType.SYSTEM_OUT) {
					topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : out", con.size);
				} else if (con.type == ConnectorType.SYSTEM_IN) {
					topLevelEntity.AddPort("p_" + con.pin.substring(con.pin.lastIndexOf('.') + 1) + " : in ", con.size);					
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
		//
		// PROVISORISCH FÜR MODELSIM
		//
		topLevelEntity.setBehavior(replaceInfix(behavior, topLevelEntity));
		//topLevelEntity.setBehavior(behavior);
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + directory + "processor.vhdl" + ")");
		}
	}
	
	//
	// PROVISORISCH FÜR MODELSIM TEST
	//
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

	//
	// PROVISORISCH FÜR MODELSIM
	//
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
}
