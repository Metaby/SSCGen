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
import de.uulm.cyv17.tool.ErrorHandler;

/**
 * The compiler for the microcode of a specific
 * computer architecture.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 */
public class MicrocodeCompiler {
	
	private int maxCodeSize = 0;
	private List<String> functionMnemonicInformations;
	private List<String> commandTranslation;
	
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
	 * Replaces all calls in the given microcode by. Each call
	 * is replaced with the code of the called microcode function.
	 * The function iterates until all calls are solved.
	 * 
	 * If the code has cycles, the function will abort with an
	 * appropriate error message.
	 * 
	 * @param mc microcode with calls
	 * @return the microcode object mc without calls
	 */
	private Microcode replaceCalls(Microcode mc) {
		List<MicrocodeFunction> functions = mc.getFunctions();
		List<MicrocodeFunction> newFunctions = new ArrayList<MicrocodeFunction>();
		for (MicrocodeFunction function : functions) {
			MicrocodeFunction newFunction = new MicrocodeFunction(function.isVirtual());
			newFunction.setName(function.getName());
			newFunction.setPosition(function.getPosition());
			List<String> lines = function.getFunctionLines(true);
				for (int i = 0; i < lines.size(); i++) {
					if (lines.get(i).startsWith("c:")) {
						String functionName = lines.get(i).substring(2, lines.get(i).length() - 2);
						if (functionName.equals(function.getName())) {
							ErrorHandler.throwError(12);
						}
						for (MicrocodeFunction calledFunction : functions) {
							if (calledFunction.getName().equals(functionName)) {
								lines.remove(i);
								lines.addAll(i, calledFunction.getFunctionLines(true));
								i--;
								break;
							}
						}
					}
				}
			for (String line : lines) {					
				newFunction.addFunctionLine(line);
			}
			newFunction.setOpcode(function.getOpcode());
			newFunction.setOperandCount(function.getOperandCount());
			newFunctions.add(newFunction);
		}
		functions = newFunctions;
		Microcode replacedMc = new Microcode();
		for (MicrocodeField field : mc.getFields()) {
			replacedMc.addField(field);
		}
		for (MicrocodeFunction function : newFunctions) {
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
		/*
		 * b: = begin [function name]
		 * p: = position [auto, integer]
		 * s: = set
		 * P: = perm
		 * f: = fix
		 * e: = end [function name]
		 */
		if (mc != null) {
			mc = replaceCalls(mc);
			String bytes = "";
			// read all function lines
			for (MicrocodeFunction mf : mc.getFunctions()) {
				if (!mf.isVirtual()) {
					if (mf.getPosition() != -1) {
						bytes += "p:" + mf.getPosition() + System.lineSeparator();
					} else {
						bytes += "p:auto" + System.lineSeparator();
					}
					for (String str : mf.getFunctionLines(false)) {
						bytes += str + System.lineSeparator();
					}
				}
			}
			// calculate positions and replace fields
			int addressCounter = 0;
			String[] split = bytes.split(System.lineSeparator());
			String intermediateCode = "";
			perm = 0;
			int[] fix = new int[1000];
			int fixPtr = -1;
			List<MicrocodeField> fields = mc.getFields();
			Boolean newFunction = true;
			String functionName = "";
			functionMnemonicInformations = new ArrayList<String>();
			commandTranslation = new ArrayList<String>();
			for (String str : split) {
				if (str.startsWith("s:")) {
					intermediateCode += "s:" + (microcodeFieldLookup(fields, str.substring(2)) | perm | fix[fixPtr]) + System.lineSeparator();
					addressCounter++;
				} else if (str.startsWith("P:")) {
					perm |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("f:")) {
					fix[fixPtr] |= microcodeFieldLookup(fields, str.substring(2));
				} else if (str.startsWith("p:auto")) {
					intermediateCode += "p:" + addressCounter + System.lineSeparator();
				} else if (str.startsWith("p:")) {
					int address = Integer.parseInt(str.substring(2));
					if (address < addressCounter) {
						System.err.println("\"0x" + Integer.toHexString(address) + "\"");
						ErrorHandler.throwError(13);
					}
					addressCounter = address;
					intermediateCode += "p:" +  address + System.lineSeparator();
				} else if (str.startsWith("b:")) {
					if (newFunction && !str.substring(2).equals("")) {
						functionName = str.substring(2);
						MicrocodeFunction mf = GetFunction(mc, functionName);
						if (mf == null) {
							functionMnemonicInformations.add(functionName + ";" + Integer.toHexString(addressCounter) + ";0");
						} else {
							if (mf.getOpcode() != -1) {
								functionMnemonicInformations.add(mf.getName() + ";" + Integer.toHexString(mf.getOpcode()) + ";" + mf.getOperandCount());
								commandTranslation.add(Integer.toHexString(mf.getOpcode()) + ";" + Integer.toHexString(addressCounter));
							} else {
								functionMnemonicInformations.add(mf.getName() + ";" + Integer.toHexString(addressCounter) + ";0");
							}
						}
						newFunction = false;
					}
					fixPtr++;
					fix[fixPtr] = 0;
				} else if (str.startsWith("e:")) {
					if (!newFunction && str.substring(2).equals(functionName)) {
						newFunction = true;
					}
					fixPtr--;
				}
			}
			return intermediateCode;
		}
		return "";
	}
	
	/**
	 * Returns the command translation table.
	 * 
	 * @return a List<String> containing the command translation table
	 */
	public List<String> getCommandTranslation() {
		return commandTranslation;
	}

	/**
	 * Searches for the microcode function with the given id. The function
	 * will be searched within a complete set of microcode represented by
	 * the Microcode class.
	 * 
	 * @param mc The microcode with its functions in which is searched
	 * @param id The id of the wanted function
	 * @return the microcode function if it is found, null otherwise
	 */
	private MicrocodeFunction GetFunction(Microcode mc, String id) {
		for (MicrocodeFunction mf : mc.getFunctions()) {
			if (mf.getName().equals(id)) {
				return mf;
			}
		}
		return null;
	}
	
	/**
	 * Returns the mnemonic informations of a microcode function.
	 * 
	 * @return a List<String> containing the mnemonic informations
	 */
	public List<String> getFunctionMnemonicInformations() {
		return functionMnemonicInformations;
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
			Boolean found = false;
			for (MicrocodeField field : fields) {
				if (bitSet.equals(field.getId())) {
					if (single) {
						code += (1 << field.getCvStart());
					} else {
						int val = field.getValue(key);
						if (val != -1) {
							code += (val << (field.getCvStart()));
						} else {
							System.out.println(bitSet + "(" + key + ")");
							ErrorHandler.throwError(11);
						}
					}
					found = true;
				}
			}
			if (!found) {
				System.out.println(bitSet);
				ErrorHandler.throwError(10);
			}
		}
		int codeSize = Integer.toBinaryString(code).length();
		if (codeSize > maxCodeSize) {
			maxCodeSize = codeSize;
		}
		return code;
	}
	
	/**
	 * Takes the intermediate code of the "compileMicrocode" function,
	 * converts it into bytes represented as hex-codes and saves it
	 * to the specified url.
	 * 
	 * The generated format is the same as used in the "Logisim"
	 * Software. It starts with "v2.0 raw" in the first line and is
	 * then followed by 8 hex-codes per line.
	 * 
	 * @param intermediateCode the intermediate code to convert and save
	 * @param outputFile the url of the file to which the hex code is saved
	 */
	private void saveMicrocode(String intermediateCode, String outputFile) {
		String hexContent = "v2.0 raw" + System.lineSeparator();
		int currentPos = 0;
		int bvSize = maxCodeSize;
		int length =(int)Math.ceil(bvSize / 4.0);
		String[] parts = intermediateCode.split(System.lineSeparator());
		for (String part : parts) {
			if (part.startsWith("p:")) {
				int pos = Integer.parseInt(part.substring(2));
				while (pos > currentPos) {
					String cb = addZeros(Integer.toHexString(perm), 4);
					hexContent += addZeros("0", length) + " ";
					currentPos++;
					if (currentPos % 8 == 0) {
						hexContent += System.lineSeparator();
					}
				}
			} else if (part.startsWith("s:")) {
				String cb = addZeros(Integer.toHexString(Integer.parseInt(part.substring(2))), length);
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
			System.err.println(outputFile);
			ErrorHandler.throwError(5);
		}
	}
	
	/**
	 * Expands the given hex-code with leading zeros until it has
	 * the desired length.
	 * 
	 * @param hex the hex-code to expand
	 * @param length the desired length of the hex-code
	 * @return the hex-code with leading zeros
	 */
	private String addZeros(String hex, int length) {
		while (hex.length() < length) {
			hex = "0" + hex;
		}
		return hex;
	}
}
