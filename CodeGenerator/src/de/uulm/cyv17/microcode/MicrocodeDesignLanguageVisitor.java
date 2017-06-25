package de.uulm.cyv17.microcode;

import java.util.List;
import java.util.Map;

import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_field_declarationContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_field_idContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_field_paramsContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_field_positionsContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_field_valuesContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_functionContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_bodyContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_callContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_call_codeContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_fixContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_fix_codeContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_headContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_nameContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_posContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_setContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_set_codeContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_function_tailContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_initContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_init_headContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_init_permContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_init_perm_codeContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_mdfContext;
import de.uulm.cyv17.antlr.MicrocodeDesignLanguageParser.Gr_virtual_headContext;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: URL von [1]
/**
 *  The following class will not be documented in fact
 *  it is only an overwritten auto generated class.
 *  For further details see [1].
 *  
 *  It is used to visit and parse the microcode design language
 *  into an object of the type "Microcode". The microcode
 *  itself is documented in the specific classes.
 *  
 *  [1] antlr.org (4.7)
 *  
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class MicrocodeDesignLanguageVisitor extends de.uulm.cyv17.antlr.MicrocodeDesignLanguageBaseVisitor<String> {
	private List<Integer> values;
	private List<String> functionNames;
	private List<String> params;
	private MicrocodeFunction function;
	private MicrocodeField field;
	private Microcode microcode;
	
	public MicrocodeDesignLanguageVisitor() {
		microcode = new Microcode();
		functionNames = new ArrayList<String>();
		params = new ArrayList<String>();
		values = new ArrayList<Integer>();
	}
	
	@Override
	public String visitGr_function(Gr_functionContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_init(Gr_initContext ctx) {
		return visitChildren(ctx);
	}

	@Override
	public String visitGr_function_head(Gr_function_headContext ctx) {
		function = new MicrocodeFunction(false);
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_virtual_head(Gr_virtual_headContext ctx) {
		function = new MicrocodeFunction(true);
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_init_head(Gr_init_headContext ctx) {
		function = new MicrocodeFunction(false);
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_name(Gr_function_nameContext ctx) {
		function.setName(ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_pos(Gr_function_posContext ctx) {
		function.setPosition(ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_body(Gr_function_bodyContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_call(Gr_function_callContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_call_code(Gr_function_call_codeContext ctx) {
		function.addFunctionLine("c:" + ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_set(Gr_function_setContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_set_code(Gr_function_set_codeContext ctx) {
		function.addFunctionLine("s:" + ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_fix(Gr_function_fixContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_fix_code(Gr_function_fix_codeContext ctx) {
		function.addFunctionLine("f:" + ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_init_perm(Gr_init_permContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_init_perm_code(Gr_init_perm_codeContext ctx) {
		function.addFunctionLine("P:" + ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_field_declaration(Gr_field_declarationContext ctx) {
		field = new MicrocodeField();
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_field_id(Gr_field_idContext ctx) {
		field.setId(ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_field_positions(Gr_field_positionsContext ctx) {
		int start = Integer.parseInt(removeBrackets(ctx.getText()).split(",")[0]);
		int end = Integer.parseInt(removeBrackets(ctx.getText()).split(",")[1]);
		field.setCvStart(start);
		field.setCvEnd(end);
		return ctx.getText();
	}
	
	@Override
	public String visitGr_field_params(Gr_field_paramsContext ctx) {
		String[] split = removeBrackets(ctx.getText()).split(",");
		for (String param : split) {
			params.add(param);
		}
		return ctx.getText();
	}
	
	@Override
	public String visitGr_field_values(Gr_field_valuesContext ctx) {
		String[] split = removeBrackets(ctx.getText()).split(",");
		for (String value : split) {
			values.add(Integer.parseInt(value));
		}
		Map<String, Integer> fieldParams = new HashMap<String, Integer>();
		for (int i = 0; i < params.size(); i++) {
			fieldParams.put(params.get(i), values.get(i));
		}
		field.setFields(fieldParams);
		microcode.addField(field);
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_tail(Gr_function_tailContext ctx) {
		microcode.addFunction(function);
		if (functionNames.contains(function.getName())) {
			System.out.println("Error: Qualifier \"" + function.getName() + "\" for function or virtual already in use.");
			System.exit(-1);
		} else {
			functionNames.add(function.getName());
		}
		function = null;
		return null;
	}
	
	@Override
	public String visitGr_mdf(Gr_mdfContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	protected String aggregateResult(String aggregate, String nextResult) {
		if (aggregate == null) {
			return nextResult;
		}
		if (nextResult == null) {
			return aggregate;
		}
		return aggregate + " " + nextResult;
	}

	public Microcode getMicrocode() {
		return microcode;
	}
	
	public String removeBrackets(String str) {
		int start = 0;
		int end = str.length();
		if (str.contains("{")) {
			start = str.indexOf('{') + 1;
		}
		if (str.contains("}")) {
			end = str.indexOf('}');
		}
		return str.substring(start, end);
	}
}
