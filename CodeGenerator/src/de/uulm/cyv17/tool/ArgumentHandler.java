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
			System.out.println("+------------------------------------+");
			System.out.println("|               SSCGEN               |");
			System.out.println("| [S]imple [S]oft [C]ore [GEN]erator |");
			System.out.println("|  Version 0.54 - Max Brand - 2017   |");
			System.out.println("+------------------------------------+");
			System.out.println("");
			System.out.println("usage: sscgen\t[--generate-architecture, -a]");
			System.out.println("\t\t[--generate-template, -t]");
			System.out.println("\t\t[--compile-microcode, -c]");
			System.out.println("\t\t[--editor, -e]");
			System.out.println("\t\t[--input, -i]");
			System.out.println("\t\t[--output, -o]");
			System.out.println("");
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i].equals("--generate-template") || params[i].equals("-t")) {
				op = ToolOperation.GENERATE_MICROCODE_TEMPLATE;
			} else if (params[i].equals("--compile-microcode") || params[i].equals("-c")) {
				op = ToolOperation.COMPILE_MICROCODE;
			} else if (params[i].equals("--generate-architecture") || params[i].equals("-a")) {
				op = ToolOperation.GENERATE_ARCHITECTURE;
			} else if (params[i].equals("--editor") || params[i].equals("-e")) {
				op = ToolOperation.RUN_EDITOR;
			} else if (params[i].equals("-i") || params[i].equals("--input")) {
				if (params.length - 1 == i) {
					ErrorHandler.throwError(0);
				}
				inputFile = params[i + 1];
				i++;
			} else if (params[i].equals("-o") || params[i].equals("--output")) {
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
