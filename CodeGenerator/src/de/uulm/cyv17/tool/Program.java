package de.uulm.cyv17.tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import de.uulm.cyv17.microcode.MicrocodeCompiler;
import de.uulm.cyv17.wrapper.*;

@SuppressWarnings("ucd")
public class Program {
	
	private static long time;
	
	public static void startTimeMeasuring() {
		time = System.nanoTime();
	}
	
	public static void stopTimeMeasureing() {
		System.out.println("Finished after " + (System.nanoTime() - time) / 1000000.0 + " ms");		
	}
	
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
//		String processor = "example_cpu";
//		generateMicrocodeDesignFiles("processors/" + processor + "/architecture.xml", "processors/" + processor + "/microprogram.mdl");
//		compileMicrocode("processors/" + processor + "/microprogram.mdl", "processors/" + processor + "/microprogram.hex");
//		Assembler asm = new Assembler();
//		asm.initWindow();
//		asm.initReplacement("processors/" + processor + "/mnemonics.csv");
//		compileMicrocode("processors/" + processor + "/microprogram.mdl", "processors/" + processor + "/microprogram.hex");
//		generateArchitecture("processors/" + processor + "/architecture.xml", "processors/" + processor + "/code/");
		System.out.println("sscgen fin (0)");
	}
	
	public static Architecture validateAndLoadArchitecture(String architectureFile) {
		ArchitectureFactory factory = new ArchitectureFactory();
		assertion(factory.validateSpecification(architectureFile, "processors/specification.xsd"));
		Architecture arch = factory.readSpecification(architectureFile);
		assertion(factory.validateIds(arch));
		assertion(factory.validateConnections(arch));
		return arch;
	}
	
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
		mdfContent += " *\tUse \"call idle_cyle()\" for one clock cycle without any operation" + System.lineSeparator();
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
		//fields += "virtual idle_cycle() {" + System.lineSeparator();
		//fields += "    set noop(0);" + System.lineSeparator();
		//fields += "}";
		File mdfPath = new File(outputFile);
		File defPath = new File(defFile);
		try {
			if (!mdfPath.exists()) {
				Files.write(mdfPath.toPath(), mdfContent.getBytes());
			}
			Files.write(defPath.toPath(), fields.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			ErrorHandler.throwError(5);
		}
	}
	
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
			System.out.println("Error: Could not write to target file. (mnemonics.csv)");
		}
		List<String> commandTranslation = compiler.getCommandTranslation();
		int highestAdress = 0;
		for (String trans : commandTranslation) {
			String[] parts = trans.split(";");
			int extern = Integer.parseInt(parts[0], 16);
			if (extern > highestAdress) {
				highestAdress = extern;
			}
		}
		String[] table = new String[highestAdress + 1];
		for (int i = 0; i < table.length; i++) {
			table[i] = "";
		}
		for (String trans : commandTranslation) {
			String[] parts = trans.split(";");
			table[Integer.parseInt(parts[0], 16)] = parts[1];
		}
		String commandTranslationTable = "v2.0 raw" + System.lineSeparator();
		for (int i = 0; i < table.length; i++) {
			if (table[i].equals("")) {
				commandTranslationTable += "0000 ";
			} else {
				commandTranslationTable += addZeros(table[i], 4) + " ";
			}
			if ((i + 1) % 8 == 0) {
				commandTranslationTable += System.lineSeparator();
			}
		}
		f = new File(targetFile.substring(0, targetFile.lastIndexOf("/") + 1) + "translation.hex");
		try {
			Files.write(f.toPath(), commandTranslationTable.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (translation.hex)");
		}
	}
	
	public static void generateArchitecture(String architectureFile, String outputDirectory) {
		deleteFolder(new File(outputDirectory));
		ArchitectureFactory factory = new ArchitectureFactory();
		Architecture arch = validateAndLoadArchitecture(architectureFile);
		factory.generateArchitecture(outputDirectory, arch);		
	}
	
	public static void assertion(Boolean pass) {
		if (!pass) {
			ErrorHandler.throwError(4);		
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
