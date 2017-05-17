grammar MicrocodeDesignLanguage;
gr_mdf: gr_import* gr_function*;
gr_function_head: 'function' gr_function_name '(' gr_function_pos? ')' '{';
gr_function_name: gr_qualifier;
gr_function_pos: gr_hex;
gr_function_tail: '}';
gr_function_set: 'set' gr_function_set_code ';';
gr_function_set_code: (gr_field | ((gr_field ',')+ gr_field));
gr_function_call: 'call' gr_function_call_code ';';
gr_function_call_code: gr_qualifier;
gr_function_body: (gr_function_set | gr_function_call)*;
gr_function: gr_function_head gr_function_body gr_function_tail;
gr_qualifier: (gr_char | gr_digit | '_')+;
gr_field: gr_qualifier '(' gr_parameter ')';
gr_parameter: gr_qualifier '.' gr_qualifier | 'CONST(' gr_number ')' | gr_qualifier;
gr_lc_char: 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' | 'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z';
gr_uc_char: 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'I' | 'J' | 'K' | 'L' | 'M' | 'N' | 'O' | 'P' | 'Q' | 'R' | 'S' | 'T' | 'U' | 'V' | 'W' | 'X' | 'Y' | 'Z';
gr_char: gr_lc_char | gr_uc_char;
gr_digit: '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9';
gr_number: gr_digit+;
gr_hex_digit: gr_digit | 'A' | 'B' | 'C' | 'D' | 'E' | 'F';
gr_hex: '0x' gr_hex_digit gr_hex_digit;
gr_file: (gr_char ':/')? (gr_qualifier | ((gr_qualifier '/')+ gr_qualifier)) '.' gr_char+;
gr_import: 'definition' gr_file;
WHITES: [ \r\n\t]+ -> channel(HIDDEN);
S_COMMENT: '//' ~[\n\r]* -> channel(HIDDEN);
M_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);