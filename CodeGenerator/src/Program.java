

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
		sb.ReadSpecification("Processors/mips/specification.xml", "Templates/specification.xsd");
	}
}
