// Generated from ../processors/MicrocodeDesignLanguage.g4 by ANTLR 4.7
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MicrocodeDesignLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MicrocodeDesignLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_comment(MicrocodeDesignLanguageParser.Gr_commentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_mdf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_mdf(MicrocodeDesignLanguageParser.Gr_mdfContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_head}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_head(MicrocodeDesignLanguageParser.Gr_function_headContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_name(MicrocodeDesignLanguageParser.Gr_function_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_pos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_pos(MicrocodeDesignLanguageParser.Gr_function_posContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_tail(MicrocodeDesignLanguageParser.Gr_function_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_set(MicrocodeDesignLanguageParser.Gr_function_setContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_set_code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_set_code(MicrocodeDesignLanguageParser.Gr_function_set_codeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_call(MicrocodeDesignLanguageParser.Gr_function_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_call_code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_call_code(MicrocodeDesignLanguageParser.Gr_function_call_codeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_body(MicrocodeDesignLanguageParser.Gr_function_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function(MicrocodeDesignLanguageParser.Gr_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_qualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_qualifier(MicrocodeDesignLanguageParser.Gr_qualifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field(MicrocodeDesignLanguageParser.Gr_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_parameter(MicrocodeDesignLanguageParser.Gr_parameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_lc_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_lc_char(MicrocodeDesignLanguageParser.Gr_lc_charContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_uc_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_uc_char(MicrocodeDesignLanguageParser.Gr_uc_charContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_char(MicrocodeDesignLanguageParser.Gr_charContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_digit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_digit(MicrocodeDesignLanguageParser.Gr_digitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_number(MicrocodeDesignLanguageParser.Gr_numberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_hex_digit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_hex_digit(MicrocodeDesignLanguageParser.Gr_hex_digitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_hex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_hex(MicrocodeDesignLanguageParser.Gr_hexContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_file(MicrocodeDesignLanguageParser.Gr_fileContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_import}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_import(MicrocodeDesignLanguageParser.Gr_importContext ctx);
}