package de.uulm.cyv17.tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import de.uulm.cyv17.antlr.MicrocodeDesignLanguageLexer;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser;
import de.uulm.cyv17.microcode.Microcode;
import de.uulm.cyv17.microcode.MicrocodeCompiler;
import de.uulm.cyv17.microcode.MicrocodeDesignLanguageVisitor;
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
		String processor = "stack_machine";
		generateMicrocodeDesignFiles("processors/" + processor + "/architecture.xml", "processors/" + processor + "/" + processor + "_mp.mdl");
		compileMicrocode("processors/" + processor + "/" + processor + "_mp.mdl", "processors/" + processor + "/" + processor + "_mp.hex");
		generateArchitecture("processors/" + processor + "/architecture.xml", "", "processors/" + processor + "/code/");
		System.out.println("fin");
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
		File mdfPath = new File(outputFile);
		File defPath = new File(defFile);
		try {
			if (!mdfPath.exists()) {
				Files.write(mdfPath.toPath(), mdfContent.getBytes());
			}
			Files.write(defPath.toPath(), fields.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + outputFile + ")");
		}
	}
	
	public static void compileMicrocode(String mdfFile, String targetFile) {
		MicrocodeCompiler compiler = new MicrocodeCompiler();
		compiler.compile(mdfFile, targetFile);
	}
	
	public static void generateArchitecture(String architectureFile, String mdf, String outputDirectory) {
		deleteFolder(new File(outputDirectory));
		ArchitectureFactory factory = new ArchitectureFactory();
		Architecture arch = validateAndLoadArchitecture(architectureFile);
		factory.generateArchitecture(outputDirectory, arch);		
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
