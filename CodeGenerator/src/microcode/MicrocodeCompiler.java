package microcode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.MicrocodeDesignLanguageLexer;
import antlr.MicrocodeDesignLanguageParser;

public class MicrocodeCompiler {
	
	public void compile(String mdfFile, String targetFile) {
		Microcode mc = readMicrocodeDesignFile(mdfFile);
		String mcBytes = compileMicrocode(mc);
		saveMicrocode(mcBytes, targetFile);
	}
	
	private List<MicrocodeField> readDefinitionFile(String inputFile) {
		List<MicrocodeField> fields = new ArrayList<MicrocodeField>();
		try {
			List<String> strContent = Files.readAllLines(new File(inputFile).toPath());
			for (String f : strContent) {
				if (f.startsWith("single")) {
					fields.add(readSingle(f.substring(7)));
				} else if (f.startsWith("field")) {
					fields.add(readField(f.substring(6)));				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fields;
	}
	
	private MicrocodeField readSingle(String def) {
		String[] parts = def.split(" = ");
		int pos = Integer.parseInt(parts[1].substring(1, parts[1].length() - 2).trim());
		return new MicrocodeField(parts[0], pos, pos, true);
	}
	
	private MicrocodeField readField(String def) {
		String[] commandParts = def.split(" = ");
		String[] fieldParts = commandParts[1].split("}");
		int start = Integer.parseInt(fieldParts[0].substring(1).split(",")[0].trim());
		int end = Integer.parseInt(fieldParts[0].substring(1).split(",")[1].trim());
		MicrocodeField field = new MicrocodeField(commandParts[0], start, end, false);
		String[] keys = fieldParts[1].substring(1).split(",");
		String[] values = fieldParts[2].substring(1).split(",");
		if (keys.length == values.length) {
			for (int i = 0; i < keys.length; i++) {
				field.addKeyVal(keys[i], Integer.parseInt(values[i].trim()));
			}
		} else {
			System.out.println("Error: bad definition file.");
			System.exit(-1);
		}
		return field;
	}
	
	@SuppressWarnings("deprecation")
	private Microcode readMicrocodeDesignFile(String mdfFilePath) {
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
	
	private int getCommandInteger(List<MicrocodeField> fields, String qualifier, String key) {
		for (MicrocodeField field : fields) {
			if (field.getId().equals(qualifier)) {
				return field.getValue(key);
			}
		}
		System.out.println("Error compiling mdf: Qualifier \"" + qualifier + "\" or key \"" + key + "\" not found.");
		System.exit(-1);
		return -1;
	}
	
	private String compileMicrocode(Microcode mc) {
		if (mc != null) {
			List<MicrocodeField> fields = new ArrayList<MicrocodeField>();
			String bytes = "";
			// Definition lesen und parsen
			for (String str : mc.getImports()) {
				fields.addAll(readDefinitionFile(str));
			}
			// Alle Funktionszeilen einlesen
			for (MicrocodeFunction mf : mc.getFunctions()) {
				if (mf.getPosition() != -1) {
					bytes += "p:" + mf.getPosition() + System.lineSeparator();
				} else {
					bytes += "p:auto" + System.lineSeparator();
				}
				for (String str : mf.getFunctionLines()) {
					bytes += str + System.lineSeparator();
				}
			}
			// Calls rekursiv durch ihre Funktion ersetzen
			while (bytes.contains("c:")) {
				String[] split = bytes.split(System.lineSeparator());
				String replacedBytes = "";
				for (String str : split) {
					if (str.startsWith("c:")) {
						String functionName = str.substring(2);
						for (MicrocodeFunction mf : mc.getFunctions()) {
							if (mf.getName().equals(functionName)) {
								for (String str2 : mf.getFunctionLines()) {
									replacedBytes += str2 + System.lineSeparator();
								}
							}
						}
					} else {
						replacedBytes += str + System.lineSeparator();
					}
				}
				bytes = replacedBytes;
			}
			// Positionen berechnen und felder ersetzen
			int addressCounter = 0;
			String[] split = bytes.split(System.lineSeparator());
			String replacedBytes = "";
			for (String str : split) {
				if (str.startsWith("s:")) {
					System.out.println(microcodeFieldLookup(fields, str.substring(2)));
//					String[] split2 = str.split(",");
//					for (String str2 : split2) {
//						System.out.println(str2);
//						String qualifier =  str2.substring(2, str2.indexOf('('));
//						String key = str2.substring(str.indexOf('(') + 1, str.indexOf(')'));
//						System.out.println(qualifier + " " + key + " " + getCommandInteger(fields, qualifier, key));						
//					}
					addressCounter++;
				} else if (str.startsWith("p:auto")) {
					replacedBytes += "p:" + addressCounter + System.lineSeparator();
				} else if (str.startsWith("p:")) {
					int address = Integer.parseInt(str.substring(2));
					if (address < addressCounter) {
						System.out.println("Error: Selected address imposible, because code already asigned to this address.");
						System.exit(-1);
					}
					addressCounter = address;
					replacedBytes += "p:" +  address + System.lineSeparator();
				}
			}
			return replacedBytes;
		}
		return "";
	}
	
	private int microcodeFieldLookup(List<MicrocodeField> fields, String functionLine) {
		return 0;
	}
	
	private void saveMicrocode(String bytes, String outputFile) {
		System.out.println(bytes);
	}
}
