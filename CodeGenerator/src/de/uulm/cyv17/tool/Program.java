package de.uulm.cyv17.tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.microcode.MicrocodeCompiler;
import de.uulm.cyv17.wrapper.*;

/**
 * The class containing the main function for the program entry. 
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
@SuppressWarnings("ucd")
public class Program {
	
	/**
	 * The program entry of the tool.
	 * 
	 * @param args the program arguments given while called
	 */
	public static void main(String[] args) {
		ArgumentHandler pm = new ArgumentHandler(args);
		if (pm.getOp() == ToolOperation.GENERATE_MICROCODE_TEMPLATE) {
			generateMicrocodeDesignFiles(pm.getInputFile(), pm.getOutputFile());
		} else if (pm.getOp() == ToolOperation.COMPILE_MICROCODE) {
			compileMicrocode(pm.getInputFile(), pm.getOutputFile());
		} else if (pm.getOp() == ToolOperation.GENERATE_ARCHITECTURE) {
			generateArchitecture(pm.getInputFile(), pm.getOutputFile());
		} else if (pm.getOp() == ToolOperation.RUN_EDITOR) {
			Assembler asm = new Assembler();
			asm.initWindow();
			asm.initReplacement(pm.getInputFile());
		}
		System.out.println("sscgen fin (0)");
	}
	
	/**
	 * Validates and loads an architecture out of a given specification file.
	 * 
	 * @param architectureFile the path of the specification file
	 * @return the validated, parsed and prepared architecture
	 */
	public static Architecture validateAndLoadArchitecture(String architectureFile) {
		ArchitectureFactory factory = new ArchitectureFactory();
		assertion(factory.validateSpecification(architectureFile, "processors/specification.xsd"));
		Architecture arch = factory.readSpecification(architectureFile);
		assertion(factory.validateIds(arch));
		assertion(factory.validateConnections(arch));
		return arch;
	}
	
	/**
	 * Generates the microcode templates of a given architecture specification.
	 * 
	 * @param architectureFile the path of the architectural specification file
	 * @param outputFile the output file name for the templates
	 */
	public static void generateMicrocodeDesignFiles(String architectureFile, String outputFile) {
		Architecture arch = validateAndLoadArchitecture(architectureFile);
		ControlVector cv = arch.getControlVector();
		String mdfContent = "/*" + System.lineSeparator();
		mdfContent += " *\tConventions:" + System.lineSeparator();
		mdfContent += " *\tisel = input-select" + System.lineSeparator();
		mdfContent += " *\tasel = address-select" + System.lineSeparator();
		mdfContent += " *\tcsel = command-select" + System.lineSeparator() + " *" + System.lineSeparator();
		mdfContent += " *\tFields and Parameters:" + System.lineSeparator();
		for (ControlField cf : cv.getFields()) {
			mdfContent += " *\t" + cf.getName() + "[" + cf.getStart() + ":" + cf.getEnd() + "] = {";
			for (String param : cf.getParameters().keySet()) {
				mdfContent += param + ", ";
			}
			mdfContent = mdfContent.substring(0, mdfContent.length() -2) + "}" + System.lineSeparator();
		}
		mdfContent += " *" + System.lineSeparator();
		mdfContent += " */" + System.lineSeparator() + System.lineSeparator();
		String defFile = outputFile;
		if (defFile.contains(".")) {
			defFile = defFile.substring(0, defFile.indexOf('.')) + "_def.mdl";
		} else {
			defFile += "_def.mdl";
		}
		mdfContent += System.lineSeparator() + "#include " + defFile + System.lineSeparator() + System.lineSeparator();
		mdfContent += "function init(0x00) {" + System.lineSeparator() + "\t" + System.lineSeparator() + "}";
		String fields = "";
		for (ControlField cf : cv.getFields()) {
			fields += "field " + cf.getName() + " = {" + cf.getStart() + "," + cf.getEnd() + "}{";
			String values = "{";
			if (cf.getParameters().size() == 0) {
				fields += "H, L";
				values += "1, 0";
			} else {
				for (String param : cf.getParameters().keySet()) {
					fields += param + ", ";
					values += cf.getParameters().get(param) + ", ";
				}
				values = values.substring(0, values.length() - 2);
				fields = fields.substring(0, fields.length() - 2);
			}
			values += "};";
			fields = fields + "}" + values + System.lineSeparator();
		}
		fields += "field noop = {0," + (cv.getSize() - 1) + "}{0}{0};" + System.lineSeparator() + System.lineSeparator();
		File mdfPath = new File(outputFile);
		File defPath = new File(defFile);
		try {
			if (!mdfPath.exists()) {
				Files.write(mdfPath.toPath(), mdfContent.getBytes());
			}
			Files.write(defPath.toPath(), fields.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(outputFile + ", " + defFile);
			ErrorHandler.throwError(5);
		}
	}
	
	/**
	 * Compiles the given microcode and generates the appropriate hex file.
	 * 
	 * @param mdfFile the path of the microcode file
	 * @param targetFile the target path of the hex file
	 */
	public static void compileMicrocode(String mdfFile, String targetFile) {
		MicrocodeCompiler compiler = new MicrocodeCompiler();
		compiler.compile(mdfFile, targetFile);
		File f = new File(targetFile.substring(0, targetFile.lastIndexOf("/") + 1) + "mnemonics.csv");
		try {
			String content = "";
			for (String line : compiler.getFunctionMnemonicInformations()) {
				content += line + System.lineSeparator();
			}
			Files.write(f.toPath(), content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("mnemonics.csv");
			ErrorHandler.throwError(5);
		}		
		List<String> commandTranslation = compiler.getCommandTranslation();
		int highestAdress = 0;
		int tmp = 0;
		for (String trans : commandTranslation) {
			String[] parts = trans.split(";");
			int extern = Integer.parseInt(parts[1], 16);
			int tmp2 = Integer.parseInt(parts[0], 16);
			if (extern > highestAdress) {
				highestAdress = extern;
			}
			if (tmp2 > tmp) {
				tmp = tmp2;
			}
		}
		tmp += 1;
		int wordSize = (int)Math.ceil((Math.log(highestAdress) / Math.log(2)) / 8);
		int[] table = new int[highestAdress + 1];
		for (int i = 0; i < table.length; i++) {
			table[i] = 0;
		}
		for (String trans : commandTranslation) {
			String[] parts = trans.split(";");
			table[Integer.parseInt(parts[0], 16)] = Integer.parseInt(parts[1]);
		}
		String[] hexCodes = new String[tmp * wordSize];
		for (int i = 0; i < tmp; i++) {
			for (int j = 0; j < wordSize; j++) {
				 String hex = Integer.toHexString((char)(255 & (table[i] >> 8 * j)));
				 while (hex.length() < 2) {
					 hex = "0" + hex;
				 }
				 hexCodes[i * wordSize + (wordSize - j - 1)] = hex;
			}
		}
		HexGenerator.WriteIntelHexFile(targetFile.substring(0, targetFile.lastIndexOf("/") + 1) + "translation.hex", hexCodes, wordSize);
	}
	
	/**
	 * Generates the complete vhdl code for a given architecture.
	 * 
	 * @param architectureFile the path of the specification file
	 * @param outputDirectory the output directory where the vhdl code is saved to
	 */
	public static void generateArchitecture(String architectureFile, String outputDirectory) {
		deleteFolder(new File(outputDirectory));
		ArchitectureFactory factory = new ArchitectureFactory();
		Architecture arch = validateAndLoadArchitecture(architectureFile);
		factory.generateArchitecture(outputDirectory, arch);		
	}
	
	/**
	 * Assertion function for the validation. It throws an error
	 * with the error handler if the given parameter is false.
	 * 
	 * @param pass the boolean if the assertion should fire or not
	 */
	public static void assertion(Boolean pass) {
		if (!pass) {
			ErrorHandler.throwError(4);		
		}
	}
	
	/**
	 * A method for deleting a complete directory with all its content.
	 * 
	 * @param folder the directory to be deleted
	 */
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
	
	/**
	 * Expands the given hex-code with leading zeros until it has
	 * the desired length.
	 * 
	 * @param hex the hex-code to expand
	 * @param length the desired length of the hex-code
	 * @return the hex-code with leading zeros
	 */
	private static String addZeros(String hex, int length) {
		while (hex.length() < length) {
			hex = "0" + hex;
		}
		return hex;
	}
}
