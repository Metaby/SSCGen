package de.uulm.cyv17.tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

import de.uulm.cyv17.wrapper.*;
import de.uulm.cyv17.wrapper.entities.AluEntity;
import de.uulm.cyv17.wrapper.entities.BaseEntity;
import de.uulm.cyv17.wrapper.entities.CustomEntity;
import de.uulm.cyv17.wrapper.entities.MultiplexerEntity;
import de.uulm.cyv17.wrapper.entities.RegisterEntity;
import de.uulm.cyv17.wrapper.entities.RegisterFileEntity;
import de.uulm.cyv17.wrapper.entities.RomEntity;

/**
 * Factory class for generating a computer architecture
 * out of a given architecture.xml file which describes
 * the components and wiring of the architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
class ArchitectureFactory {
	
	/**
	 * Reads the specification of the computer architecture 
	 * from the given URL. The architecture is then parsed with
	 * jaxb and converted into an object of the type "Architecture".
	 * 
	 * @param SpecPath the url of the specification
	 * @return the architecture
	 */
	Architecture readSpecification(String SpecPath) {
		try {
			JAXBContext jc = JAXBContext.newInstance(de.uulm.cyv17.jaxb.Architecture.class);
			Unmarshaller u = jc.createUnmarshaller();
			de.uulm.cyv17.jaxb.Architecture arch = (de.uulm.cyv17.jaxb.Architecture)u.unmarshal(new File(SpecPath));
			return new Architecture(arch);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Takes the xml schema definition (xsd) and validates the given
	 * specificcation of the architecture.
	 * 
	 * @param SpecPath the URL of the specification
	 * @param Schema the URL of the XSD
	 * @return true if the validation was correct, false otherwise
	 */
	Boolean validateSpecification(String SpecPath, String Schema) {
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
	
	/**
	 * Validates the components IDs of the given architecture.
	 * If one ore more IDs are not unique, the validation
	 * fails and will return false.
	 * 
	 * @param arch the architecture to be validated
	 * @return true if the validation is correct, false otherwise
	 */
	Boolean validateIds(Architecture arch) {
		List<String> ids = new ArrayList<String>();
		for (BaseEntity be : arch.getAllEntites()) {
			if (ids.contains(be.getId())) {
				ErrorHandler.throwError(9);
				return false;
			} else {
				ids.add(be.getId());
			}
		}
		return true;
	}

	/**
	 * Validates the components connections of the given
	 * architecture. If all connections are legit, the
	 * validation is correct and it will return true.
	 * 
	 * @param arch the architecture to be validated
	 * @return true if the validation is correct, false otherwise
	 */
	Boolean validateConnections(Architecture arch) {
		List<Connector> inputConnectors = arch.getInputConnectors(false);
		inputConnectors.remove(null);
		List<Connector> outputConnectors = arch.getOutputConnectors(false);
		outputConnectors.remove(null);
		for (Connector inputCon : inputConnectors) {
			if (!outputConnectors.contains(inputCon)) {
				if (!inputCon.origin.startsWith("system")) {
					System.out.println(inputCon.origin + "." + inputCon.pin);
					ErrorHandler.throwError(8);	
				}
			} else {
				Connector sourceCon = outputConnectors.get(outputConnectors.indexOf(inputCon));
				if (sourceCon.size > inputCon.size) {
					if (inputCon.lowerBound != -1 && inputCon.upperBound != -1) {
						int lb = inputCon.lowerBound;
						int ub = inputCon.upperBound;
						if (ub - lb + 1 > inputCon.size) {
							System.out.println(inputCon.origin + "." + inputCon.pin);
							ErrorHandler.throwError(7);
						} else if (ub - lb + 1 < inputCon.size) {
							ErrorHandler.throwWarning(1);
							System.out.println("\t> " + sourceCon.origin + "." + sourceCon.pin + " -> " + inputCon.origin + "." + inputCon.pin);				
						}
					} else {
						System.out.println(inputCon.origin + "." + inputCon.pin);
						ErrorHandler.throwError(7);
					}
				} else if (sourceCon.size < inputCon.size) {
					ErrorHandler.throwWarning(1);
					System.out.println("\t> " + sourceCon.origin + "." + sourceCon.pin + " -> " + inputCon.origin + "." + inputCon.pin);
					
				}
			}
		}
		return true;
	}
	
	/**
	 * Generates the VHDL-files for the given architecture. The
	 * files are saved in the specified directory.
	 * 
	 * @param directory the directory where to save all files
	 * @param arch the architecture
	 */
	void generateArchitecture(String directory, Architecture arch) {
		ComponentFactory cFactory = new ComponentFactory(directory);
		InstanceFactory iFactory = new InstanceFactory(arch);
		List<VhdlComponent> archComponents = new ArrayList<VhdlComponent>();
		new File(directory + "/components/").mkdirs();
		String behavior = "";
		for (RegisterEntity reg : arch.getRegisters()) {
			archComponents.add(cFactory.generateComponent(reg));
			behavior += iFactory.generateInstance(reg) + System.lineSeparator();
		}
		for (RomEntity rom : arch.getRoms()) {
			archComponents.add(cFactory.generateComponent(rom));
			behavior += iFactory.generateInstance(rom) + System.lineSeparator();
		}
		for (RegisterFileEntity rf : arch.getRegisterFiles()) {
			archComponents.add(cFactory.generateComponent(rf));
			behavior += iFactory.generateInstance(rf);
		}
		for (AluEntity alu : arch.getAlus()) {
			archComponents.add(cFactory.generateComponent(alu));
			behavior += iFactory.generateInstance(alu) + System.lineSeparator();
		}
		for (MultiplexerEntity mux : arch.getMultiplexers()) {
			archComponents.add(cFactory.generateComponent(mux));
			behavior += iFactory.generateInstance(mux) + System.lineSeparator();
		}
		for (CustomEntity cus : arch.getCustoms()) {
			behavior += iFactory.generateInstance(cus) + System.lineSeparator();
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
					topLevelEntity.AddSignal("s_" + con.origin + "_" + con.pin, con.size);				
				}				
			}
		}
		if (arch.getControlVector().getSize() > 0) {
			topLevelEntity.AddSignal("s_ctrl_vector", arch.getControlVector().getSize());
		}
		for (VhdlComponent vc : archComponents) {
			topLevelEntity.AddImport(vc.getImport());
		}
		handleCustomEntities(topLevelEntity, arch.getCustoms(), directory);
		// Provisional code replacement to match the code conditions of ModelSim.
		topLevelEntity.setBehavior(replaceInfix(behavior, topLevelEntity)); // comment this line to remove the code replacement
		//topLevelEntity.setBehavior(behavior); // uncomment this line, if the one above is commented
		File outputFile = new File(directory + "processor.vhdl");
		try {
			Files.write(outputFile.toPath(), topLevelEntity.getComponent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			ErrorHandler.throwError(5);
		}
	}
	
	/**
	 * The function is used to handle the import and usage
	 * of custom entities in the specification. Also the
	 * method is used to copy the custom entities to the
	 * architecture directory.
	 * 
	 * @param tle the top-level-entity of the architecture
	 * @param customs a list containing the custom entities
	 * @param targetLocation the target location of the architecture
	 */
	private void handleCustomEntities(VhdlComponent tle, List<CustomEntity> customs, String targetLocation) {
		for (CustomEntity cus : customs) {
			try {
				String fp = cus.getFilePath();
				File f = new File(fp);
				if (f.exists()) {
					if (fp.contains("\\")) {
						fp = fp.substring(fp.lastIndexOf("\\"));
					}
					if (fp.contains("/")) {
						fp = fp.substring(fp.lastIndexOf("/"));						
					}
					copyFile(f, new File(targetLocation + "/components/" + fp));
					String component = "";
					for (String line :  Files.readAllLines(f.toPath())) {
						component += line + System.lineSeparator();
					}
					Pattern p = Pattern.compile("(?i)(generic|port)(.|\\s)*?end");
					Matcher m = p.matcher(component);
					if (m.find()) {
						String imprt = "COMPONENT " + cus.getName() + System.lineSeparator() + m.group() + " COMPONENT;";
						String[] parts = imprt.split("\n");
						imprt = "";
						for (String str : parts) {
							str = str.trim();
							if (str.toLowerCase().startsWith("component") ||
								str.toLowerCase().endsWith("component;")) {
								imprt += "  " + str + System.lineSeparator();
							} else if (str.toLowerCase().startsWith("port") ||
								str.toLowerCase().startsWith("generic") ||
								str.toLowerCase().equals(");")) {
								imprt += "    " + str + System.lineSeparator();
							} else {
								imprt += "      " + str + System.lineSeparator();
							}
						}
						tle.AddImport(imprt);
					} else {
						ErrorHandler.throwError(6);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method is used to copy files from the source to the target.
	 * 
	 * @param source the source file
	 * @param target the target file
	 * @throws IOException
	 */
	private void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}

	// Provisional code replacement to match the code conditions of ModelSim.
	/**
	 * This function replaces concatenated signals in the given behavior
	 * of a given VHDL-Component. It replaces only concatenated signals
	 * in component instantiation. The concatenation will be then done
	 * as a new signal and the new signal is used in the instantiation.
	 * 
	 * This is only needed when simulating the computer architecture
	 * with ModelSim, because the ModelSim VHDL-Compiler isn't able
	 * to solve these concatenations.
	 * 
	 * @param behavior the behavior to vhdl-component
	 * @param vc the vhdl-component
	 * @return the fixed behavior
	 */
	private String replaceInfix(String behavior, VhdlComponent vc) {
		Pattern p = Pattern.compile("[\\\'|\\\"][01]+[\\\'|\\\"]\\ &\\ [a-zA-Z0-9\\_]+");
		Matcher m = p.matcher(behavior);
		int cnt = 0;
		while (m.find()) {
			behavior = behavior.replaceFirst("[\\\'|\\\"][01]+[\\\'|\\\"]\\ &\\ [a-zA-Z0-9\\_]+", "s_tmp_" + cnt);
			vc.AddSignal("s_tmp_" + cnt, getSize(m.group(), vc));
			behavior += "  s_tmp_" + cnt + " <= " + m.group() + ";" + System.lineSeparator();
			cnt++;
		}
		return behavior;
	}

	// Provisional code replacement to match the code conditions of ModelSim.
	/**
	 * This function retrieves the signal size of the given
	 * infix (signal concatenation) in its VHDL-Component.
	 * 
	 * @param infix the signal concatenation
	 * @param vc the VHDL-Component
	 * @return the size of the concatenated signals
	 */
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
