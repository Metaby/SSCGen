package de.uulm.cyv17.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to generate hex files in Intel-Hex-Format
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class HexGenerator {
	
	/***
	 * This function generated a single Intel-Hex-Format line of the given hex codes at the specified start address.
	 * 
	 * @param hexCodes array of 2-character long Strings, each representing one byte
	 * @param start the start address
	 * @return String representing a single line of the Intel-Hex-Format
	 */
	public static String GenerateIntelHexLine(String[] hexCodes, int start) {
		String startAdr = Integer.toHexString(start);
		while (startAdr.length() < 4) {
			startAdr = "0" + startAdr;
		}
		start = Integer.parseInt(startAdr.substring(0, 2), 16) + Integer.parseInt(startAdr.substring(2, 4), 16);
		String byteCount = Integer.toHexString(hexCodes.length);
		while (byteCount.length() < 2) {
			byteCount = "0" + byteCount;
		}
		String ihxLine = ":" + byteCount + startAdr + "00";
		int chksum = hexCodes.length + start;
		for (int i = 0; i < hexCodes.length; i++) {
			ihxLine += hexCodes[i];
			chksum += Integer.valueOf(hexCodes[i], 16);
		}
		chksum *= -1;
		String hexChksum = Integer.toHexString(chksum);
		if (hexChksum.length() > 2) {
			hexChksum = hexChksum.substring(hexChksum.length() - 2);
		}
		ihxLine += hexChksum + "\n";
		return ihxLine;
	}
	
	/***
	 * This method is used to write hexadecimal codes to a file in the Intel Hex Format.
	 * 
	 * @param fileName The filename to be stored into
	 * @param hexCodes The hexCodes to be written, given as a String array with two characters for each Byte
	 * @param wordSize The word size in Bytes for the hexadecimal code to be written
	 */
	public static void WriteIntelHexFile(String fileName, String[] hexCodes, int wordSize) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (int i = 0; i < hexCodes.length; i += wordSize) {
				int s = i;
				int e = Math.min(i + wordSize, hexCodes.length);
				int d = e - s;
				String[] tmp = new String[d];
				System.arraycopy(hexCodes, s, tmp, 0, d);
				writer.write(HexGenerator.GenerateIntelHexLine(tmp, s / wordSize));
			}
			writer.write(":00000001FF");
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	public static int[] ReadIntelHexFile(String fileName, int wordSize) {
		File inputFile = new File(fileName);
		List<Character> bytes = new ArrayList<Character>();
		try {
			int currentAddress = 0;
			List<String> strContent = Files.readAllLines(inputFile.toPath());
			for (String line : strContent) {
				if (line.startsWith(":")) {
					if (line.substring(7, 9).equals("00")) {
						int byteCount = Integer.parseInt(line.substring(1, 3), 16);
						int address = Integer.parseInt(line.substring(3, 7), 16);
						while (currentAddress < address - 1) {
							bytes.add((char)0);
							currentAddress++;
						}
						String strBytes = line.substring(9, line.length() - 2);
						for (int i = 0; i < strBytes.length() - 1; i += 2) {
							bytes.add((char)Integer.parseInt(strBytes.substring(i, i + 2), 16));
							currentAddress++;
						}
					} else if (line.substring(7, 9).equals("01")) {
						break;
					}
				}
			}
			int[] bytesArray = new int[bytes.size() / wordSize];
			for (int i = 0; i < bytesArray.length; i++) {
				int value = 0;
				for (int j = 0; j < wordSize; j++) {
					value += (bytes.get(i * wordSize + j) << (8 * (wordSize - j - 1)));
				}
				bytesArray[i] = value;
			}
			return bytesArray;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new int[0];
	}
}
