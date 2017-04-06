import java.util.List;

import jaxb.Architecture;
import wrapper.Connector;

public class Program {
	
	private static long time;
	
	public static void StartTimeMeasuring() {
		time = System.nanoTime();
	}
	
	public static void StopTimeMeasureing() {
		System.out.println((System.nanoTime() - time) / 1000000.0 + " ms");		
	}
	
	public static void main(String[] args) {
		ArchitectureFactory sb = new ArchitectureFactory();
		System.out.println("Validating specification");
		boolean passed = sb.ValidateSpecification("processors/mips/architecture.xml", "templates/specification.xsd");
		if (!passed) {
			System.out.println("Error: validation of specification failed");
			return;
		}
		System.out.println("Reading specification");
		Architecture arch = sb.ReadSpecification("processors/mips/architecture.xml");
		System.out.println("Validating connections");
		passed = sb.ValidateConnections(arch);
		if (!passed) {
			System.out.println("Error: validation of connections failed");
			return;
		}
		List<String> cv = sb.GenerateControlVector(arch);
		System.out.println("CV-Length: " + cv.size());
		System.out.println(cv);
		wrapper.Architecture a = new wrapper.Architecture(arch);
		List<Connector> ics = a.getInputConnectors();
		List<Connector> ocs = a.getOutputConnectors();
		System.out.println(a.toString());
	}
}
