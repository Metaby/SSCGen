package de.uulm.cyv17.wrapper;

/**
 * This class provides some methods and function
 * which are used by other classes.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class Wrapper {
	
	/**
	 * Calculates the logarithm to the base 2 of a given value.
	 * 
	 * @param value the integer value to be used
	 * @return the base two logarithm of the integer
	 */
	public static int log2(int value) {
		return (int)Math.ceil(Math.log(value) / Math.log(2));
	}

	/**
	 * Converts a given integer to a binary string under the
	 * restriction of a given number of digits. This is needed
	 * due to the syntax of vhdl.
	 * 
	 * @param value the value to be converted
	 * @param digits the number of digits
	 * @return a string containing the binary representation
	 */
	public static String getBinaryString(int value, int digits) {
		return String.format("%" + digits + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
}