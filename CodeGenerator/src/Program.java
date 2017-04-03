import java.io.IOException;
import java.util.List;
import Components.BaseComponent;

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
		System.out.println("Reading specification");
		List<BaseComponent> system = sb.ReadSpecification("Processors/mips/specification.xml");
//		System.out.println("Verifying system connections");
//		if (!sb.VerifyConnections(system)) {
//			System.out.println("Error: System connections wrong");			
//		}
//		System.out.println("Generating system graph");
//		sb.GenerateSystemGraph("system.dot", system);
//		try {
//			Runtime.getRuntime().exec("graphviz/bin/dot.exe -Tpng -o system.png system.dot");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Generating code");
	}
}
