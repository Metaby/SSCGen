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
		Microcode mc = loadMicrocode("processors/" + processor + "/counter_microprogram.mdf");
		String mcBytes = compileMicrocode(mc);
		saveMicrocode(mcBytes, "");
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
	
	@SuppressWarnings("deprecation")
	public static Microcode loadMicrocode(String mdfFilePath) {
		try {
			ANTLRInputStream mdlFile = new ANTLRInputStream(new FileReader(mdfFilePath));
			MicrocodeDesignLanguageLexer lexer = new MicrocodeDesignLanguageLexer(mdlFile);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MicrocodeDesignLanguageParser parser = new MicrocodeDesignLanguageParser(tokens);
			ParseTree tree = parser.gr_mdf();
			MicrocodeDesignLanguageVisitor visitor = new MicrocodeDesignLanguageVisitor();
			visitor.visit(tree);
			return visitor.getMicrocode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static String compileMicrocode(Microcode mc) {
		if (mc != null) {
			String bytes = "";
			for (String str : mc.getImports()) {
				bytes += str + System.lineSeparator();
			}
			return bytes;
		}
		return "";
	}
	
	public static void saveMicrocode(String bytes, String outputFile) {
		System.out.println(bytes);
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
