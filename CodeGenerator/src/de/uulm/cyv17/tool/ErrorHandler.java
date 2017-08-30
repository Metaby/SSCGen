package de.uulm.cyv17.tool;

/**
 * A class for the handling of the program errors
 * and warnings.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class ErrorHandler {

	public static String[] errors = {
/* 0 */		"Expected file after parameter \"-i\" or \"--input\".",
/* 1 */		"Expected file after parameter \"-o\" or \"--output\".",
/* 2 */		"No input file specified.",
/* 3 */		"No operation selected.",
/* 4 */		"Validation failed.",
/* 5 */		"Could not write to target file.",
/* 6 */		"Custom vhdl component has incorrect format.",
/* 7 */		"Source-Connection-Size does not fit size of Input-Connection.",
/* 8 */		"Input-Connection does not exist.",
/* 9 */		"ID already exists.",
/* 10 */	"Qualifier not found.",
/* 11 */	"Parameter for qualifier invalid.",
/* 12 */	"Microcode has cycles in the hierarchy.",
/* 13 */	"Code already assigned to this address.",
/* 14 */	"Qualifier for function or virtual already in use.",
/* 15 */	"Content file does not exist.",
/* 16 */	"Wrong file format.",
/* 17 */	"Reading content file.",
	};
	
	public static String[] warnings = {
/* 0 */		"No output file or directory specified, default value is used.",
/* 1 */		"Input-Connection-Size is lower than expected, Zeros are added as MSBs.",
	};
	
	/**
	 * A static method to throw an error of a given error id.
	 * This results in a program break-off and displays the
	 * appropriate error message and code.
	 *  
	 * @param id the id of the error to throw
	 */
	public static void throwError(int id) {
		if (id >= errors.length) {
			System.err.println("Error (" + id + "): Unknown Error ID");
		}
		System.err.println("Error (" + id + "): " + errors[id]);
		System.exit(id);
	}
	
	/**
	 * A static method to display the warning of a given
	 * warning id.
	 *  
	 * @param id the id of the warning to display
	 */
	public static void throwWarning(int id) {
		if (id >= warnings.length) {
			System.out.println("Warning (" + id + "): Uknown Warning ID");
		}
		System.out.println("Warning (" + id + "): " + warnings[id]);
	}
	
}
