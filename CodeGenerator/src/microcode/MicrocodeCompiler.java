package microcode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
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
	
	@SuppressWarnings("deprecation")
	private Microcode readMicrocodeDesignFile(String mdfFilePath) {
		try {
			List<String> lines = Files.readAllLines(new File(mdfFilePath).toPath());
			String file = "";
			for (String line : lines) {
				if (line.startsWith("#")) {
					if (line.startsWith("#include ")) {
						String path = line.split(" ")[1];
						List<String> fields = Files.readAllLines(new File(path).toPath());
						for (String field : fields) {
							file += field + System.lineSeparator();
						}
					}
				} else {
					file += line + System.lineSeparator();
				}
			}
			CharStream stream = new ANTLRInputStream(file);			
			MicrocodeDesignLanguageLexer lexer = new MicrocodeDesignLanguageLexer(stream);
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
	
	private Boolean hasCycles(Microcode mc) {
		List<MicrocodeFunction> funcs = mc.getFunctions();
		int[][] adj = new int[funcs.size()][funcs.size()];
		String[] names = new String[funcs.size()];
		for (int i = 0; i < funcs.size(); i++) {
			names[i] = funcs.get(i).getName();
		}
		for (int i = 0; i < funcs.size(); i++) {
			for (String str : funcs.get(i).getFunctionLines()) {
				if (str.startsWith("c:")) {
					String f = str.substring(2, str.length() - 2);
					for (int j = 0; j < names.length; j++) {
						if (names[j].equals(f)) {
							adj[i][j] = 1;
						}
					}
				}
			}
		}
		for (int i = 0; i < funcs.size(); i++) {
			int[][] adjprod = power(adj, adj, funcs.size(), i + 1);
			int diagSum = 0;
			for (int j = 0; j < adj.length; j++) {
				diagSum += adjprod[j][j];
			}
			if (diagSum != 0) {
				System.out.println(i + 1);
				return true;
			}			
		}
		return false;
	}
	
	private int[][] power(int[][] a, int[][] b, int size, int exp) {
		int[][] prod = multiply(a, b, size);
		for (int i = 0; i < exp - 1; i++) {
			prod = multiply(prod, b, size);
		}
		return prod;
	}
	
	private int[][] multiply(int[][] a, int[][] b, int size) {
		int[][] adjprod = new int[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					adjprod[x][y] = adjprod[x][y] + a[x][z] * b[z][y];
				}
			}
		}
		return adjprod;
	}
	
	private Microcode replaceCalls(Microcode mc) {
		return null;
	}
	
	private String compileMicrocode(Microcode mc) {
		if (hasCycles(mc)) {
			System.out.println("Error: Microcode has cycles in the call hirarchy.");
			System.exit(1);
		}
		if (mc != null) {
			String bytes = "";
			// Alle Funktionszeilen einlesen
			for (MicrocodeFunction mf : mc.getFunctions()) {
				if (!mf.isVirtual()) {
					if (mf.getPosition() != -1) {
						bytes += "p:" + mf.getPosition() + System.lineSeparator();
					} else {
						bytes += "p:auto" + System.lineSeparator();
					}
					for (String str : mf.getFunctionLines()) {
						bytes += str + System.lineSeparator();
					}
				}
			}
			// Calls rekursiv durch ihre Funktion ersetzen
			while (bytes.contains("c:")) {
				String[] split = bytes.split(System.lineSeparator());
				String replacedBytes = "";
				for (String str : split) {
					if (str.startsWith("c:")) {
						String functionName = str.substring(2, str.indexOf('('));
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
			perm = 0;
			int fix = 0;
			List<MicrocodeField> fields = mc.getFields();
			for (String str : split) {
				if (str.startsWith("s:")) {
					replacedBytes += "s:" + (microcodeFieldLookup(fields, str.substring(2)) | perm | fix) + System.lineSeparator();
					addressCounter++;
				} else if (str.startsWith("P:")) {
					perm |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("f:")) {
					fix |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("p:auto")) {
					fix = 0;
					replacedBytes += "p:" + addressCounter + System.lineSeparator();
				} else if (str.startsWith("p:")) {
					fix = 0;
					int address = Integer.parseInt(str.substring(2));
					if (address < addressCounter) {
						System.out.println("Error: Code already asigned to address\"" + address + "\".");
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
	
	int perm;
	
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
					String cb = Integer.toHexString(perm);
					if (cb.length() == 1) {
						cb = "0" + cb;
					}
					hexContent += "00" + " ";
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
