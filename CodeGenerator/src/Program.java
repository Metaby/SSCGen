import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import wrapper.*;

public class Program {
	
	private static long time;
	
	public static void StartTimeMeasuring() {
		time = System.nanoTime();
	}
	
	public static void StopTimeMeasureing() {
		System.out.println((System.nanoTime() - time) / 1000000.0 + " ms");		
	}
	
	public static void main(String[] args) {
		StartTimeMeasuring();
		CreateMDF("processors/mips/architecture.xml", "processors/mips/mips_microprogram.mdf");
		StopTimeMeasureing();
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
	
	public static void CreateMDF(String architectureFile, String outputFile) {
		ArchitectureFactory sb = new ArchitectureFactory();
		System.out.println("Validating specification");
		boolean passed = sb.ValidateSpecification(architectureFile, "processors/specification.xsd");
		if (!passed) {
			System.out.println("Error: validation of specification failed");
			return;
		}
		System.out.println("Reading specification");
		Architecture arch = sb.ReadSpecification(architectureFile);
		System.out.println("Validating connections");
		passed = sb.ValidateConnections(arch);
		if (!passed) {
			System.out.println("Error: validation of connections failed");
			return;
		}
		List<String> cv = sb.GenerateControlVector(arch);
		System.out.println("Writing Microcode-Design-File");
		File of = new File(outputFile);
		try {
			Files.write(of.toPath(), ("-- control-vector:" + System.lineSeparator() + "-- " + cv.toString().replace("[", "").replace("]", "")).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + outputFile + ")");
		}
	}
	
	public static void CompileMDF(String architectureFile, String mdf, String outputFile) {
		
	}
	
	public static void GenerateProcessor(String architectureFile, String mdf, String outputDirectory) {
		
	}
}
