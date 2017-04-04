import java.util.List;

import jaxbClasses.Architecture;

public class Program {
	
	private static long time;
	
	public static void StartTimeMeasuring() {
		time = System.nanoTime();
	}
	
	public static void StopTimeMeasureing() {
		System.out.println((System.nanoTime() - time) / 1000000.0 + " ms");		
	}
	
	public static void main(String[] args) {
		SystemBuilder sb = new SystemBuilder();
		System.out.println("Validating specification");
		boolean passed = sb.ValidateSpecification("Processors/mips/architecture.xml", "Templates/specification.xsd");
		if (!passed) {
			System.out.println("Error: validation of specification failed");
			return;
		}
		System.out.println("Reading specification");
		Architecture arch = sb.ReadSpecification("Processors/mips/architecture.xml");
		System.out.println("Validating connections");
		passed = sb.ValidateConnections(arch);
		if (!passed) {
			System.out.println("Error: validation of connections failed");
			return;
		}
		List<String> cv = sb.GenerateControlVector(arch);
		for (int i = 0; i < cv.size(); i++) {
			System.out.println(cv.get(i));
		}
	}
}
