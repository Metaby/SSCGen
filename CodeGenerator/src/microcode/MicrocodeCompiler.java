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
					replacedBytes += "s:" + microcodeFieldLookup(fields, str.substring(2)) + System.lineSeparator();
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
		int code = 0;
		String[] bitSets = functionLine.split(",");
		for (String bitSet : bitSets) {
			Boolean single = true;
			String key = "";
			if (bitSet.contains("(")) {
				single = false;
				key = bitSet.split("\\(")[1];
				key = key.substring(0, key.indexOf(")"));
				bitSet = bitSet.substring(0, bitSet.indexOf('('));
			}
			for (MicrocodeField field : fields) {
				if (bitSet.equals(field.getId())) {
					if (single) {
						code += (1 << field.getCvStart());
					} else {
						code += (field.getValue(key) << (field.getCvStart()));
					}
				}
			}
		}
		return code;
	}
	
	private void saveMicrocode(String bytes, String outputFile) {
		String hexContent = "v2.0 raw" + System.lineSeparator();
		int currentPos = 0;
		String[] parts = bytes.split(System.lineSeparator());
		for (String part : parts) {
			if (part.startsWith("p:")) {
				int pos = Integer.parseInt(part.substring(2));
				while (pos > currentPos) {
					hexContent += "00 ";
					currentPos++;
					if (currentPos % 8 == 0) {
						hexContent += System.lineSeparator();
					}
				}
			} else if (part.startsWith("s:")) {
				String cb = Integer.toHexString(Integer.parseInt(part.substring(2)));
				if (cb.length() == 1) {
					cb = "0" + cb;
				}
				hexContent += cb + " ";
				currentPos++;
				if (currentPos % 8 == 0) {
					hexContent += System.lineSeparator();
				}
			}
		}
		if (!outputFile.contains(".")) {
			outputFile += ".hex";
		}
		File f = new File(outputFile);
		try {
			Files.write(f.toPath(), hexContent.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: Could not write to target file. (" + outputFile + ")");
		}
	}
}
