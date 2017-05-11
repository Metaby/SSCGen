package tool;

import antlr.MicrocodeDesignLanguageParser.Gr_fileContext;
import antlr.MicrocodeDesignLanguageParser.Gr_functionContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_bodyContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_callContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_call_codeContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_headContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_nameContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_posContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_setContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_set_codeContext;
import antlr.MicrocodeDesignLanguageParser.Gr_function_tailContext;
import antlr.MicrocodeDesignLanguageParser.Gr_importContext;
import antlr.MicrocodeDesignLanguageParser.Gr_mdfContext;

public class MicrocodeDesignLanguageVisitor extends antlr.MicrocodeDesignLanguageBaseVisitor<String> {
	private Microcode microcode;
	private MicrocodeFunction function;
	
	public MicrocodeDesignLanguageVisitor() {
		microcode = new Microcode();
	}
	
	@Override
	public String visitGr_function(Gr_functionContext ctx) {		
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_head(Gr_function_headContext ctx) {
		function = new MicrocodeFunction();
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
		function.addCall(ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_set(Gr_function_setContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_function_set_code(Gr_function_set_codeContext ctx) {
		function.addSet(ctx.getText());
		return ctx.getText();
	}
	
	@Override
	public String visitGr_function_tail(Gr_function_tailContext ctx) {
		microcode.addFunction(function);
		function = null;
		return null;
	}
	
	@Override
	public String visitGr_mdf(Gr_mdfContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_import(Gr_importContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitGr_file(Gr_fileContext ctx) {
		microcode.addImport(ctx.getText());
		return ctx.getText();
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
}
