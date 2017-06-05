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
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_fix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_fix(MicrocodeDesignLanguageParser.Gr_function_fixContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_function_fix_code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_function_fix_code(MicrocodeDesignLanguageParser.Gr_function_fix_codeContext ctx);
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
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_virtual_head}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_virtual_head(MicrocodeDesignLanguageParser.Gr_virtual_headContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_virtual}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_virtual(MicrocodeDesignLanguageParser.Gr_virtualContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_init_perm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_init_perm(MicrocodeDesignLanguageParser.Gr_init_permContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_init_perm_code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_init_perm_code(MicrocodeDesignLanguageParser.Gr_init_perm_codeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_init_head}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_init_head(MicrocodeDesignLanguageParser.Gr_init_headContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_init_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_init_body(MicrocodeDesignLanguageParser.Gr_init_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_init(MicrocodeDesignLanguageParser.Gr_initContext ctx);
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
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field_declaration(MicrocodeDesignLanguageParser.Gr_field_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field_id(MicrocodeDesignLanguageParser.Gr_field_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field_positions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field_positions(MicrocodeDesignLanguageParser.Gr_field_positionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field_params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field_params(MicrocodeDesignLanguageParser.Gr_field_paramsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicrocodeDesignLanguageParser#gr_field_values}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGr_field_values(MicrocodeDesignLanguageParser.Gr_field_valuesContext ctx);
}