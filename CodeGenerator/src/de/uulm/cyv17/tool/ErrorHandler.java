package de.uulm.cyv17.tool;

public class ErrorHandler {

	public static String[] errors = {
/* 0 */		"expected file after parameter \"-in\"",
/* 1 */		"expected file after parameter \"-out\"",
/* 2 */		"no input file specified",
/* 3 */		"no operation selected",
/* 4 */		"validation failed",
/* 5 */		"could not write to target file",
/* 6 */		"custom vhdl component has incorrect format",
/* 7 */		"Source-Connection-Size does not fit size of Input-Connection",
/* 8 */		"input-Connection does not exist",
/* 9 */		"ID already exists",
/* 10 */	"Qualifier not found",
/* 11 */	"",
/* 12 */	"",
/* 13 */	"",
/* 14 */	"",
/* 15 */	"",
/* 16 */	"",
/* 17 */	"",
/* 18 */	"",
/* 19 */	"",
/* 20 */	"",
/* 21 */	"",
/* 22 */	"",
/* 23 */	"",
/* 24 */	"",
	};
	
	public static String[] warnings = {
/* 0 */		"no output file or directory specified, default value is used",
/* 1 */		"Input-Connection-Size is lower than expected, Zeros are added as MSBs",
	};
	
	public static void throwError(int id) {
		if (id >= errors.length) {
			System.err.println("Error (" + id + "): Unknown Error ID");
		}
		System.err.println("Error (" + id + "): " + errors[id]);
		System.exit(id);
	}
	
	public static void throwWarning(int id) {
		if (id >= warnings.length) {
			System.out.println("Warning (" + id + "): Uknown Warning ID");
		}
		System.out.println("Warning (" + id + "): " + warnings[id]);
	}
	
}
