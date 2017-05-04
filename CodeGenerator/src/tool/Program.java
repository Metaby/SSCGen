package tool;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import wrapper.*;

public class Program {
	
	private static long time;
	
	public static void startTimeMeasuring() {
		time = System.nanoTime();
	}
	
	public static void stopTimeMeasureing() {
		System.out.println((System.nanoTime() - time) / 1000000.0 + " ms");		
	}
	
	public static void main(String[] args) {
		startTimeMeasuring();
		String processor = "counter";
//		createMDF("processors/" + processor + "/architecture.xml", "processors/" + processor + "/counter_microprogram.mdf");
		generateProcessor("processors/" + processor + "/architecture.xml", "", "D:/OneDrive/Uni/Masterarbeit/Modelsim/generated_code/");
		generateProcessor("processors/" + processor + "/architecture.xml", "", "processors/" + processor + "/code/");
		stopTimeMeasureing();
//		System.out.println("-- Mikrocode-Design-Filge");
//		System.out.println("--");
//		System.out.println("-- csel = condition-select (JumpLogic)");
//		System.out.println("-- csel = command-select (ALU)");
//		System.out.println("-- isel = input-select");
//		System.out.println("-- asel = address-select");
//		System.out.println("--");
//		System.out.println("-- control-vector-bits:");
//		System.out.println("-- " + cv.toString().replace("[", "").replace("]", ""));
//		System.out.println("--");
//		System.out.println("-- [command, address]\t\tstart microcode-program (µp) named <command> at <address>, address is optional");
//		System.out.println("-- bit, bit, bit, bit\t\tlist all bits to be one at this state of the µp");
//		System.out.println("-- :command\t\t\trun µp named command (nest µp in µp)");
//		System.out.println("-- <cv>(<name> | <integer>)");
//		System.out.println("--");
//		System.out.println("-- Example:");
//		System.out.println("[add, 0]");
//		System.out.println("alu_op1_isel(reg.out1), alu_op2_isel(reg.out2), alu_csel(add), reg_p0_asel(ir.inst), reg_p1_asel(ir.inst), reg_p2_asel(ir.inst), reg_p2_isel(alu.out)");
//		System.out.println("alu_op1_isel(reg.out1), alu_op2_isel(reg.out2), alu_csel(add), reg_p0_asel(ir.inst), reg_p1_asel(ir.inst), reg_p2_asel(ir.inst), reg_p2_isel(alu.out), reg_p2_write");
//		System.out.println("[subtract, 1]");
//		System.out.println("alu_op1_isel(reg.out1), alu_op2_isel(reg.out2), alu_csel(subtract), reg_p0_asel(ir.inst), reg_p1_asel(ir.inst), reg_p2_asel(ir.inst), reg_p2_isel(alu.out)");
//		System.out.println("alu_op1_isel(reg.out1), alu_op2_isel(reg.out2), alu_csel(subtract), reg_p0_asel(ir.inst), reg_p1_asel(ir.inst), reg_p2_asel(ir.inst), reg_p2_isel(alu.out), reg_p2_write");
	}
	
	public static void createMDF(String architectureFile, String outputFile) {
		System.out.println("Creating MDF");
		ArchitectureFactory factory = new ArchitectureFactory();
		assertion(factory.ValidateSpecification(architectureFile, "processors/specification.xsd"));
		Architecture arch = factory.ReadSpecification(architectureFile);
		assertion(factory.ValidateIds(arch));
		assertion(factory.ValidateConnections(arch));
		List<String> cv = factory.GenerateControlVector(arch);
		File file = new File(outputFile);
		try {
			Files.write(file.toPath(), ("-- control-vector:" + System.lineSeparator() + "-- " + cv.toString().replace("[", "").replace("]", "")).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + outputFile + ")");
		}
	}
	
	public static void compileMDF(String architectureFile, String mdf, String outputFile) {
		
	}
	
	public static void generateProcessor(String architectureFile, String mdf, String outputDirectory) {
		System.out.println("Generating Architecture");
		deleteFolder(new File(outputDirectory));
		ArchitectureFactory factory = new ArchitectureFactory();
		assertion(factory.ValidateSpecification(architectureFile, "processors/specification.xsd"));
		Architecture arch = factory.ReadSpecification(architectureFile);
		assertion(factory.ValidateIds(arch));
		assertion(factory.ValidateConnections(arch));
		factory.GenerateArchitecture(outputDirectory, arch);		
	}
	
	public static void assertion(Boolean pass) {
		if (!pass) {
			System.out.println("Process aborted");
			System.exit(-1);			
		}
	}
	
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files != null) {
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
}
