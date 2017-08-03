package de.uulm.cyv17.tool;

public class ArgumentHandler {

	private String inputFile;
	private String outputFile;
	private ToolOperation op;

	public ArgumentHandler(String[] params) {
		inputFile = "NA";
		outputFile = "NA";
		op = ToolOperation.NONE;
		if (params.length == 0) {
			System.out.println("====================================");
			System.out.println("= S-imple A-rchitecture G-enerator =");
			System.out.println("= Version 0.54 - Max Brand - 2017  =");
			System.out.println("====================================");
			System.out.println("");
			System.out.println("-= Usage =-");
			System.out.println("Generate Microcode-Design-Language template:");
			System.out.println("\t./sag.jar -template -in arch.xml -out arch.mdl");
			System.out.println("");
			System.out.println("Compile Microcode-Design-Language:");
			System.out.println("./sag.jar -compiler -in arch.mdl -out arch.hex");
			System.out.println("");
			System.out.println("Generate Architecture:");
			System.out.println("./sag.jar -generate -in arch.xml -out arch/");
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i].equals("-template")) {
				op = ToolOperation.GENERATE_MICROCODE_TEMPLATE;
			} else if (params[i].equals("-compile")) {
				op = ToolOperation.COMPILE_MICROCODE;
			} else if (params[i].equals("-generate")) {
				op = ToolOperation.GENERATE_ARCHITECTURE;
			} else if (params[i].equals("-translation")) {
				op = ToolOperation.GENERATE_TRANSLATION;
			} else if (params[i].equals("-in")) {
				if (params.length - 1 == i) {
					ErrorHandler.throwError(0);
				}
				inputFile = params[i + 1];
				i++;
			} else if (params[i].equals("-out")) {
				if (params.length - 1 == i) {
					ErrorHandler.throwError(1);
				}	
				outputFile = params[i + 1];
				i++;			
			}
		}
		if (op == ToolOperation.NONE) {
			ErrorHandler.throwError(3);
		}
		if (inputFile.equals("NA")) {
			ErrorHandler.throwError(2);			
		}
		if (outputFile.equals("NA")) {
			ErrorHandler.throwWarning(0);
			if (op == ToolOperation.GENERATE_ARCHITECTURE) {
				outputFile = "arch_gen_output/";
			} else if (op == ToolOperation.GENERATE_MICROCODE_TEMPLATE) {
				outputFile = "arch_microcode.mdl";
			} else if (op == ToolOperation.COMPILE_MICROCODE) {
				outputFile = "arch_microcode.hex";
			}
		}
	}
	
	public String getInputFile() {
		return inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public ToolOperation getOp() {
		return op;
	}
	
}
