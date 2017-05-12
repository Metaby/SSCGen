package tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.MicrocodeDesignLanguageLexer;
import antlr.MicrocodeDesignLanguageParser;
import microcode.Microcode;
import microcode.MicrocodeCompiler;
import microcode.MicrocodeDesignLanguageVisitor;
import wrapper.*;

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
		String processor = "counter";
//		generateMicrocodeDesignFiles("processors/" + processor + "/architecture.xml", "processors/" + processor + "/counter_microprogram.mdf");
//		generateArchitecture("processors/" + processor + "/architecture.xml", "", "D:/OneDrive/Uni/Masterarbeit/Modelsim/" + processor + "/");
//		generateArchitecture("processors/" + processor + "/architecture.xml", "", "processors/" + processor + "/code/");
		compileMicrocode("processors/" + processor + "/counter_microprogram.mdf", "processors/" + processor + "/counter_microprogram.hex");
	}
	
	public static Architecture validateAndLoadArchitecture(String architectureFile) {
		ArchitectureFactory factory = new ArchitectureFactory();
		assertion(factory.ValidateSpecification(architectureFile, "processors/specification.xsd"));
		Architecture arch = factory.ReadSpecification(architectureFile);
		assertion(factory.ValidateIds(arch));
		assertion(factory.ValidateConnections(arch));
		return arch;
	}
	
	public static void generateMicrocodeDesignFiles(String architectureFile, String outputFile) {
		Architecture arch = validateAndLoadArchitecture(architectureFile);
		ControlVector cv = arch.getControlVector();
		String mdfContent = "";
		mdfContent += "-- Konventionen:" + System.lineSeparator();
		mdfContent += "-- isel = input-select" + System.lineSeparator();
		mdfContent += "-- asel = address-select" + System.lineSeparator();
		mdfContent += "-- csel = command-select" + System.lineSeparator();
		mdfContent += "--" + System.lineSeparator();
		mdfContent += "-- Steuervektor:" + System.lineSeparator();
		mdfContent += "-- ";
		String mdfFields = "--" + System.lineSeparator();
		mdfFields += "-- Parameter:" + System.lineSeparator();
		for (ControlField cf : cv.getFields()) {
			if (cf.getSize() > 1) {
				mdfContent += cf.getName() + "[0," + (cf.getEnd() - cf.getStart()) + "], ";
				mdfFields += "-- " + cf.getName() + System.lineSeparator();
				for (String param : cf.getParameters().keySet()) {
					mdfFields += "--   " + param + System.lineSeparator();
				}
			} else {
				mdfContent += cf.getName() + ", ";
			}
		}
		mdfContent = mdfContent.substring(0, mdfContent.length() - 2) + System.lineSeparator() + mdfFields;
		String defFile = outputFile;
		if (defFile.contains(".")) {
			defFile = defFile.substring(0, defFile.indexOf('.')) + ".def";
		} else {
			defFile += ".def";
		}
		mdfContent += System.lineSeparator() + "import " + defFile + System.lineSeparator() + System.lineSeparator();
		String fields = "";
		String singles = "";
		for (ControlField cf : cv.getFields()) {
			if (cf.getSize() > 1) {
				fields += "field " + cf.getName() + " = {" + cf.getStart() + "," + cf.getEnd() + "}{";
				String values = "{";
				for (String param : cf.getParameters().keySet()) {
					fields += param + ", ";
					values += cf.getParameters().get(param) + ", ";
				}
				values = values.substring(0, values.length() - 2);
				values += "};";
				fields = fields.substring(0, fields.length() - 2) + "}" + values + System.lineSeparator();
			} else {
				singles += "single " + cf.getName() + " = {" + cf.getStart() + "};" + System.lineSeparator();
			}
		}
		File mdfPath = new File(outputFile);
		File defPath = new File(defFile);
		try {
			Files.write(mdfPath.toPath(), mdfContent.getBytes());
			Files.write(defPath.toPath(), (singles + fields).getBytes());
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
