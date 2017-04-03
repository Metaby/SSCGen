import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import jaxbClasses.Architecture;

public class SystemBuilder {
	
	public Architecture ReadSpecification(String SpecPath, String Schema) {
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
}
