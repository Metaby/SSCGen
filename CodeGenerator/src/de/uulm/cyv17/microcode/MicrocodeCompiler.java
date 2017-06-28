package de.uulm.cyv17.microcode;

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

import de.uulm.cyv17.antlr.MicrocodeDesignLanguageLexer;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser;

/**
 * The compiler for the microcode of a specific
 * computer architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 */
public class MicrocodeCompiler {
	
	/**
	 * Compiles the microcode given as a file and writes the output to a specified hex-file.
	 * 
	 * @param mdlFile the file containing the microcode, usually a .mdl file (Microcode-Design-Language)
	 * @param targetFile the file where to write the hex-code, usually a .hex file
	 */
	public void compile(String mdlFile, String targetFile) {
		Microcode mc = readMicrocodeDesignFile(mdlFile);
		String mcBytes = compileMicrocode(mc);
		saveMicrocode(mcBytes, targetFile);
	}
	
	/**
	 * Reads the microcode of a given file. The microcode will then
	 * be tested for syntax errors and parsed into an object of the
	 * type Microcode. The microcode is returned after testing and
	 * parsing.
	 * 
	 * @param mdlFilePath the file containing the microcode, usually a .mdl file (Microcode-Design-Language)
	 * @return the microcode encapsulated in an object of the type Microcode 
	 */
	@SuppressWarnings("deprecation")
	private Microcode readMicrocodeDesignFile(String mdlFilePath) {
		try {
			List<String> lines = Files.readAllLines(new File(mdlFilePath).toPath());
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
	
	/**
	 * Tests if the given microcode has cycles in the code.
	 * If so, it will return true, otherwise it returns false.
	 * 
	 * @param mc the microcode to be tested
	 * @return true if the microcode has cycles, false otherwise
	 */
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
	
	// TODO: a und b -> nur eine matrix als parameter
	/**
	 * Calculates and returns A^n of a quadratic matrix.
	 * @param a the matrix to be raised
	 * @param b the matrix to be raised
	 * @param size the size of the matrix (m x m)
	 * @param exp the epxonent
	 * @return a^exp
	 */
	private int[][] power(int[][] a, int[][] b, int size, int exp) {
		int[][] prod = multiply(a, b, size);
		for (int i = 0; i < exp - 1; i++) {
			prod = multiply(prod, b, size);
		}
		return prod;
	}
	
	/**
	 * Multiplies two quadratic matrices.
	 * 
	 * @param a the first matrix
	 * @param b the second matrix
	 * @param size the size of the matrices
	 * @return a*b
	 */
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
	
	/**
	 * Replaces all calls in the given microcode by. Each call
	 * is replaced with the code of the called microcode function.
	 * The function iterates until all calls are solved.
	 * 
	 * If the code has cycles, the function will enter an infinite
	 * loop. It is highly recommended to test the microcode for
	 * cycles with the "hasCycles" function before.
	 * 
	 * @param mc the microcode with calls
	 * @return mc without calls
	 */
	private Microcode replaceCalls(Microcode mc) {
		List<MicrocodeFunction> functions = mc.getFunctions();
		Boolean replace;
		do {
			replace = false;
			List<MicrocodeFunction> replacedFunctions = new ArrayList<MicrocodeFunction>();
			for (MicrocodeFunction function : functions) {
				MicrocodeFunction newFunction = new MicrocodeFunction(function.isVirtual());
				newFunction.setName(function.getName());
				newFunction.setPosition(function.getPosition());
				for (String functionLine : function.getFunctionLines()) {
					if (functionLine.startsWith("c:")) {
						replace = true;
						String functionName = functionLine.substring(2, functionLine.length() - 2);
						for (MicrocodeFunction calledFunction : functions) {
							if (calledFunction.getName().equals(functionName)) {
								for (String calledFunctionLine : calledFunction.getFunctionLines()) {
									newFunction.addFunctionLine(calledFunctionLine);
								}
							}
						}
					} else {
						newFunction.addFunctionLine(functionLine);
					}
				}
				replacedFunctions.add(newFunction);
			}
			functions = replacedFunctions;
		} while (replace);
		Microcode replacedMc = new Microcode();
		for (MicrocodeField field : mc.getFields()) {
			replacedMc.addField(field);
		}
		for (MicrocodeFunction function : functions) {
			replacedMc.addFunction(function);
		}
		return replacedMc;
	}

	/**
	 * The core compiler function of the microcode compiler.
	 * It calculates the correct positions of the functions
	 * and generates and intermediate code containing only
	 * control vectors and positions.
	 * 
	 * @param mc the microcode to be compiled
	 * @return the intermediate code of the microcode
	 */
	private String compileMicrocode(Microcode mc) {
		if (mc != null) {
			if (hasCycles(mc)) {
				System.out.println("Error: Microcode has cycles in the call hirarchy.");
				System.exit(1);
			}
			mc = replaceCalls(mc);
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
			// Positionen berechnen und felder ersetzen
			int addressCounter = 0;
			String[] split = bytes.split(System.lineSeparator());
			String intermediateCode = "";
			perm = 0;
			int fix = 0;
			List<MicrocodeField> fields = mc.getFields();
			for (String str : split) {
				if (str.startsWith("s:")) {
					intermediateCode += "s:" + (microcodeFieldLookup(fields, str.substring(2)) | perm | fix) + System.lineSeparator();
					addressCounter++;
				} else if (str.startsWith("P:")) {
					perm |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("f:")) {
					fix |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("p:auto")) {
					fix = 0;
					intermediateCode += "p:" + addressCounter + System.lineSeparator();
				} else if (str.startsWith("p:")) {
					fix = 0;
					int address = Integer.parseInt(str.substring(2));
					if (address < addressCounter) {
						System.out.println("Error: Code already assigned to address \"0x" + Integer.toHexString(address) + "\".");
						System.exit(-1);
					}
					addressCounter = address;
					intermediateCode += "p:" +  address + System.lineSeparator();
				}
			}
			return intermediateCode;
		}
		return "";
	}
	
	int perm;
	
	/**
	 * Converts one line of a function into the correct control vector.
	 * 
	 * The microcode line is being split into parts and each part will be
	 * looked up in the fields of the microcode and replaced with its
	 * value.
	 * 
	 * @param fields the fields of the microcode
	 * @param functionLine the microcode line to be translated
	 * @return the integer value of the control vector
	 */
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
	
	/**
	 * Takes the intermediate code of the "compileMicrocode" function,
	 * converts it into bytes represented as hex-codes and saves it
	 * to the specified url.
	 * 
	 * The generated format is the same as used in "Logisim". It 
	 * starts with "v2.0 raw" in the first line and is then followed
	 * by 10 8 Bit hex-codes per line.
	 * 
	 * @param intermediateCode the intermediate code to convert and save
	 * @param outputFile the url of the file to which the hex code is saved
	 */
	private void saveMicrocode(String intermediateCode, String outputFile) {
		String hexContent = "v2.0 raw" + System.lineSeparator();
		int currentPos = 0;
		String[] parts = intermediateCode.split(System.lineSeparator());
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
