package de.uulm.cyv17.tool;

/**
 * This class is used to process and handle the
 * parameterized control of the tool.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class ArgumentHandler {

	private String inputFile;
	private String outputFile;
	private ToolOperation op;

	/** 
	 * The constructor of the ArgumentHandler. While
	 * constructing the argument handler, the arguments
	 * of the tool call are given as a parameter to be
	 * processed.
	 * 
	 * @param params an array of strings containing the parameters
	 */
	public ArgumentHandler(String[] params) {
		inputFile = "NA";
		outputFile = "NA";
		op = ToolOperation.NONE;
		// version := major.minor
		// major := number of major releases
		// minor := number of commits since project start
		if (params.length == 0) {
			System.out.println("+------------------------------------+");
			System.out.println("|               SSCGEN               |");
			System.out.println("| [S]imple [S]oft [C]ore [GEN]erator |");
			System.out.println("|  Version 1.62 - Max Brand - 2017   |");
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
			if (op != ToolOperation.RUN_EDITOR) {
				ErrorHandler.throwWarning(0);	
			}
			if (op == ToolOperation.GENERATE_ARCHITECTURE) {
				outputFile = "arch_gen_output/";
			} else if (op == ToolOperation.GENERATE_MICROCODE_TEMPLATE) {
				outputFile = "arch_microcode.mdl";
			} else if (op == ToolOperation.COMPILE_MICROCODE) {
				outputFile = "arch_microcode.hex";
			}
		}
	}
	
	/**
	 * Returns the path of the input file if one
	 * was specified with the arguments.
	 * 
	 * @return the path of the input file
	 */
	public String getInputFile() {
		return inputFile;
	}

	/**
	 * Returns the path of the output file if one
	 * was specified with the arguments.
	 * 
	 * @return the path of the output file
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * Returns the operation specified with the arguments
	 * to control the correct oepration of the tool.
	 * 
	 * @return an enum value containing the tool operation
	 */
	public ToolOperation getOp() {
		return op;
	}
	
}
