// Generated from ../processors/MicrocodeDesignLanguage.g4 by ANTLR 4.7
package de.uulm.cyv17.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MicrocodeDesignLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, WHITES=85, S_COMMENT=86, M_COMMENT=87;
	public static final int
		RULE_gr_mdf = 0, RULE_gr_function_head = 1, RULE_gr_function_name = 2, 
		RULE_gr_function_pos = 3, RULE_gr_function_tail = 4, RULE_gr_function_set = 5, 
		RULE_gr_function_set_code = 6, RULE_gr_function_call = 7, RULE_gr_function_call_code = 8, 
		RULE_gr_function_fix = 9, RULE_gr_function_fix_code = 10, RULE_gr_function_body = 11, 
		RULE_gr_function = 12, RULE_gr_virtual_head = 13, RULE_gr_virtual = 14, 
		RULE_gr_init_perm = 15, RULE_gr_init_perm_code = 16, RULE_gr_init_head = 17, 
		RULE_gr_init_body = 18, RULE_gr_init = 19, RULE_gr_qualifier = 20, RULE_gr_field = 21, 
		RULE_gr_parameter = 22, RULE_gr_lc_char = 23, RULE_gr_uc_char = 24, RULE_gr_char = 25, 
		RULE_gr_digit = 26, RULE_gr_number = 27, RULE_gr_hex_digit = 28, RULE_gr_hex = 29, 
		RULE_gr_field_declaration = 30, RULE_gr_field_id = 31, RULE_gr_field_positions = 32, 
		RULE_gr_field_params = 33, RULE_gr_field_values = 34;
	public static final String[] ruleNames = {
		"gr_mdf", "gr_function_head", "gr_function_name", "gr_function_pos", "gr_function_tail", 
		"gr_function_set", "gr_function_set_code", "gr_function_call", "gr_function_call_code", 
		"gr_function_fix", "gr_function_fix_code", "gr_function_body", "gr_function", 
		"gr_virtual_head", "gr_virtual", "gr_init_perm", "gr_init_perm_code", 
		"gr_init_head", "gr_init_body", "gr_init", "gr_qualifier", "gr_field", 
		"gr_parameter", "gr_lc_char", "gr_uc_char", "gr_char", "gr_digit", "gr_number", 
		"gr_hex_digit", "gr_hex", "gr_field_declaration", "gr_field_id", "gr_field_positions", 
		"gr_field_params", "gr_field_values"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'function'", "'('", "')'", "'{'", "'auto'", "','", "'}'", "'set'", 
		"';'", "'call'", "'()'", "'fix'", "'virtual'", "'perm'", "'init'", "'0x00'", 
		"'_'", "'.'", "'CONST('", "'a'", "'b'", "'c'", "'d'", "'e'", "'f'", "'g'", 
		"'h'", "'i'", "'j'", "'k'", "'l'", "'m'", "'n'", "'o'", "'p'", "'q'", 
		"'r'", "'s'", "'t'", "'u'", "'v'", "'w'", "'x'", "'y'", "'z'", "'A'", 
		"'B'", "'C'", "'D'", "'E'", "'F'", "'G'", "'H'", "'I'", "'J'", "'K'", 
		"'L'", "'M'", "'N'", "'O'", "'P'", "'Q'", "'R'", "'S'", "'T'", "'U'", 
		"'V'", "'W'", "'X'", "'Y'", "'Z'", "'0'", "'1'", "'2'", "'3'", "'4'", 
		"'5'", "'6'", "'7'", "'8'", "'9'", "'0x'", "'field'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "WHITES", "S_COMMENT", "M_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MicrocodeDesignLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MicrocodeDesignLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Gr_mdfContext extends ParserRuleContext {
		public Gr_initContext gr_init() {
			return getRuleContext(Gr_initContext.class,0);
		}
		public List<Gr_field_declarationContext> gr_field_declaration() {
			return getRuleContexts(Gr_field_declarationContext.class);
		}
		public Gr_field_declarationContext gr_field_declaration(int i) {
			return getRuleContext(Gr_field_declarationContext.class,i);
		}
		public List<Gr_functionContext> gr_function() {
			return getRuleContexts(Gr_functionContext.class);
		}
		public Gr_functionContext gr_function(int i) {
			return getRuleContext(Gr_functionContext.class,i);
		}
		public List<Gr_virtualContext> gr_virtual() {
			return getRuleContexts(Gr_virtualContext.class);
		}
		public Gr_virtualContext gr_virtual(int i) {
			return getRuleContext(Gr_virtualContext.class,i);
		}
		public Gr_mdfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_mdf; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_mdf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_mdfContext gr_mdf() throws RecognitionException {
		Gr_mdfContext _localctx = new Gr_mdfContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_gr_mdf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__82) {
				{
				{
				setState(70);
				gr_field_declaration();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
			gr_init();
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0 || _la==T__12) {
				{
				setState(79);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(77);
					gr_function();
					}
					break;
				case T__12:
					{
					setState(78);
					gr_virtual();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_headContext extends ParserRuleContext {
		public Gr_function_nameContext gr_function_name() {
			return getRuleContext(Gr_function_nameContext.class,0);
		}
		public Gr_function_posContext gr_function_pos() {
			return getRuleContext(Gr_function_posContext.class,0);
		}
		public Gr_function_headContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_head; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_head(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_headContext gr_function_head() throws RecognitionException {
		Gr_function_headContext _localctx = new Gr_function_headContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_gr_function_head);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__0);
			setState(85);
			gr_function_name();
			setState(86);
			match(T__1);
			setState(87);
			gr_function_pos();
			setState(88);
			match(T__2);
			setState(89);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_nameContext extends ParserRuleContext {
		public Gr_qualifierContext gr_qualifier() {
			return getRuleContext(Gr_qualifierContext.class,0);
		}
		public Gr_function_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_nameContext gr_function_name() throws RecognitionException {
		Gr_function_nameContext _localctx = new Gr_function_nameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_gr_function_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			gr_qualifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_posContext extends ParserRuleContext {
		public List<Gr_hexContext> gr_hex() {
			return getRuleContexts(Gr_hexContext.class);
		}
		public Gr_hexContext gr_hex(int i) {
			return getRuleContext(Gr_hexContext.class,i);
		}
		public Gr_numberContext gr_number() {
			return getRuleContext(Gr_numberContext.class,0);
		}
		public Gr_function_posContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_pos; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_pos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_posContext gr_function_pos() throws RecognitionException {
		Gr_function_posContext _localctx = new Gr_function_posContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_gr_function_pos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				{
				setState(93);
				match(T__4);
				}
				break;
			case T__81:
				{
				setState(94);
				gr_hex();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(97);
				match(T__5);
				setState(98);
				gr_hex();
				setState(99);
				match(T__5);
				setState(100);
				gr_number();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_tailContext extends ParserRuleContext {
		public Gr_function_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_tailContext gr_function_tail() throws RecognitionException {
		Gr_function_tailContext _localctx = new Gr_function_tailContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_gr_function_tail);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_setContext extends ParserRuleContext {
		public Gr_function_set_codeContext gr_function_set_code() {
			return getRuleContext(Gr_function_set_codeContext.class,0);
		}
		public Gr_function_setContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_set; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_set(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_setContext gr_function_set() throws RecognitionException {
		Gr_function_setContext _localctx = new Gr_function_setContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_gr_function_set);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(T__7);
			setState(107);
			gr_function_set_code();
			setState(108);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_set_codeContext extends ParserRuleContext {
		public List<Gr_fieldContext> gr_field() {
			return getRuleContexts(Gr_fieldContext.class);
		}
		public Gr_fieldContext gr_field(int i) {
			return getRuleContext(Gr_fieldContext.class,i);
		}
		public Gr_function_set_codeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_set_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_set_code(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_set_codeContext gr_function_set_code() throws RecognitionException {
		Gr_function_set_codeContext _localctx = new Gr_function_set_codeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_gr_function_set_code);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(110);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(114); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(111);
						gr_field();
						setState(112);
						match(T__5);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(116); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(118);
				gr_field();
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_callContext extends ParserRuleContext {
		public Gr_function_call_codeContext gr_function_call_code() {
			return getRuleContext(Gr_function_call_codeContext.class,0);
		}
		public Gr_function_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_call; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_callContext gr_function_call() throws RecognitionException {
		Gr_function_callContext _localctx = new Gr_function_callContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_gr_function_call);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__9);
			setState(123);
			gr_function_call_code();
			setState(124);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_call_codeContext extends ParserRuleContext {
		public Gr_qualifierContext gr_qualifier() {
			return getRuleContext(Gr_qualifierContext.class,0);
		}
		public Gr_function_call_codeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_call_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_call_code(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_call_codeContext gr_function_call_code() throws RecognitionException {
		Gr_function_call_codeContext _localctx = new Gr_function_call_codeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_gr_function_call_code);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			gr_qualifier();
			setState(127);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_fixContext extends ParserRuleContext {
		public Gr_function_fix_codeContext gr_function_fix_code() {
			return getRuleContext(Gr_function_fix_codeContext.class,0);
		}
		public Gr_function_fixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_fix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_fix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_fixContext gr_function_fix() throws RecognitionException {
		Gr_function_fixContext _localctx = new Gr_function_fixContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_gr_function_fix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(T__11);
			setState(130);
			gr_function_fix_code();
			setState(131);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_fix_codeContext extends ParserRuleContext {
		public List<Gr_fieldContext> gr_field() {
			return getRuleContexts(Gr_fieldContext.class);
		}
		public Gr_fieldContext gr_field(int i) {
			return getRuleContext(Gr_fieldContext.class,i);
		}
		public Gr_function_fix_codeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_fix_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_fix_code(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_fix_codeContext gr_function_fix_code() throws RecognitionException {
		Gr_function_fix_codeContext _localctx = new Gr_function_fix_codeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_gr_function_fix_code);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(133);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(137); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(134);
						gr_field();
						setState(135);
						match(T__5);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(139); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(141);
				gr_field();
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_function_bodyContext extends ParserRuleContext {
		public List<Gr_function_setContext> gr_function_set() {
			return getRuleContexts(Gr_function_setContext.class);
		}
		public Gr_function_setContext gr_function_set(int i) {
			return getRuleContext(Gr_function_setContext.class,i);
		}
		public List<Gr_function_callContext> gr_function_call() {
			return getRuleContexts(Gr_function_callContext.class);
		}
		public Gr_function_callContext gr_function_call(int i) {
			return getRuleContext(Gr_function_callContext.class,i);
		}
		public List<Gr_function_fixContext> gr_function_fix() {
			return getRuleContexts(Gr_function_fixContext.class);
		}
		public Gr_function_fixContext gr_function_fix(int i) {
			return getRuleContext(Gr_function_fixContext.class,i);
		}
		public Gr_function_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function_body; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_function_bodyContext gr_function_body() throws RecognitionException {
		Gr_function_bodyContext _localctx = new Gr_function_bodyContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_gr_function_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__9) | (1L << T__11))) != 0)) {
				{
				setState(148);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
					{
					setState(145);
					gr_function_set();
					}
					break;
				case T__9:
					{
					setState(146);
					gr_function_call();
					}
					break;
				case T__11:
					{
					setState(147);
					gr_function_fix();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_functionContext extends ParserRuleContext {
		public Gr_function_headContext gr_function_head() {
			return getRuleContext(Gr_function_headContext.class,0);
		}
		public Gr_function_bodyContext gr_function_body() {
			return getRuleContext(Gr_function_bodyContext.class,0);
		}
		public Gr_function_tailContext gr_function_tail() {
			return getRuleContext(Gr_function_tailContext.class,0);
		}
		public Gr_functionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_function(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_functionContext gr_function() throws RecognitionException {
		Gr_functionContext _localctx = new Gr_functionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_gr_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			gr_function_head();
			setState(154);
			gr_function_body();
			setState(155);
			gr_function_tail();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_virtual_headContext extends ParserRuleContext {
		public Gr_function_nameContext gr_function_name() {
			return getRuleContext(Gr_function_nameContext.class,0);
		}
		public Gr_virtual_headContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_virtual_head; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_virtual_head(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_virtual_headContext gr_virtual_head() throws RecognitionException {
		Gr_virtual_headContext _localctx = new Gr_virtual_headContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_gr_virtual_head);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(T__12);
			setState(158);
			gr_function_name();
			setState(159);
			match(T__10);
			setState(160);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_virtualContext extends ParserRuleContext {
		public Gr_virtual_headContext gr_virtual_head() {
			return getRuleContext(Gr_virtual_headContext.class,0);
		}
		public Gr_function_bodyContext gr_function_body() {
			return getRuleContext(Gr_function_bodyContext.class,0);
		}
		public Gr_function_tailContext gr_function_tail() {
			return getRuleContext(Gr_function_tailContext.class,0);
		}
		public Gr_virtualContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_virtual; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_virtual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_virtualContext gr_virtual() throws RecognitionException {
		Gr_virtualContext _localctx = new Gr_virtualContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_gr_virtual);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			gr_virtual_head();
			setState(163);
			gr_function_body();
			setState(164);
			gr_function_tail();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_init_permContext extends ParserRuleContext {
		public Gr_init_perm_codeContext gr_init_perm_code() {
			return getRuleContext(Gr_init_perm_codeContext.class,0);
		}
		public Gr_init_permContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_init_perm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_init_perm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_init_permContext gr_init_perm() throws RecognitionException {
		Gr_init_permContext _localctx = new Gr_init_permContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_gr_init_perm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(T__13);
			setState(167);
			gr_init_perm_code();
			setState(168);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_init_perm_codeContext extends ParserRuleContext {
		public List<Gr_fieldContext> gr_field() {
			return getRuleContexts(Gr_fieldContext.class);
		}
		public Gr_fieldContext gr_field(int i) {
			return getRuleContext(Gr_fieldContext.class,i);
		}
		public Gr_init_perm_codeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_init_perm_code; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_init_perm_code(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_init_perm_codeContext gr_init_perm_code() throws RecognitionException {
		Gr_init_perm_codeContext _localctx = new Gr_init_perm_codeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_gr_init_perm_code);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(170);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(174); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(171);
						gr_field();
						setState(172);
						match(T__5);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(176); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(178);
				gr_field();
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_init_headContext extends ParserRuleContext {
		public Gr_init_headContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_init_head; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_init_head(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_init_headContext gr_init_head() throws RecognitionException {
		Gr_init_headContext _localctx = new Gr_init_headContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_gr_init_head);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(T__14);
			setState(183);
			match(T__1);
			setState(184);
			match(T__15);
			setState(185);
			match(T__2);
			setState(186);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_init_bodyContext extends ParserRuleContext {
		public List<Gr_function_setContext> gr_function_set() {
			return getRuleContexts(Gr_function_setContext.class);
		}
		public Gr_function_setContext gr_function_set(int i) {
			return getRuleContext(Gr_function_setContext.class,i);
		}
		public List<Gr_function_callContext> gr_function_call() {
			return getRuleContexts(Gr_function_callContext.class);
		}
		public Gr_function_callContext gr_function_call(int i) {
			return getRuleContext(Gr_function_callContext.class,i);
		}
		public List<Gr_function_fixContext> gr_function_fix() {
			return getRuleContexts(Gr_function_fixContext.class);
		}
		public Gr_function_fixContext gr_function_fix(int i) {
			return getRuleContext(Gr_function_fixContext.class,i);
		}
		public List<Gr_init_permContext> gr_init_perm() {
			return getRuleContexts(Gr_init_permContext.class);
		}
		public Gr_init_permContext gr_init_perm(int i) {
			return getRuleContext(Gr_init_permContext.class,i);
		}
		public Gr_init_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_init_body; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_init_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_init_bodyContext gr_init_body() throws RecognitionException {
		Gr_init_bodyContext _localctx = new Gr_init_bodyContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_gr_init_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__9) | (1L << T__11) | (1L << T__13))) != 0)) {
				{
				setState(192);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
					{
					setState(188);
					gr_function_set();
					}
					break;
				case T__9:
					{
					setState(189);
					gr_function_call();
					}
					break;
				case T__11:
					{
					setState(190);
					gr_function_fix();
					}
					break;
				case T__13:
					{
					setState(191);
					gr_init_perm();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_initContext extends ParserRuleContext {
		public Gr_init_headContext gr_init_head() {
			return getRuleContext(Gr_init_headContext.class,0);
		}
		public Gr_init_bodyContext gr_init_body() {
			return getRuleContext(Gr_init_bodyContext.class,0);
		}
		public Gr_function_tailContext gr_function_tail() {
			return getRuleContext(Gr_function_tailContext.class,0);
		}
		public Gr_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_initContext gr_init() throws RecognitionException {
		Gr_initContext _localctx = new Gr_initContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_gr_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			gr_init_head();
			setState(198);
			gr_init_body();
			setState(199);
			gr_function_tail();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_qualifierContext extends ParserRuleContext {
		public List<Gr_charContext> gr_char() {
			return getRuleContexts(Gr_charContext.class);
		}
		public Gr_charContext gr_char(int i) {
			return getRuleContext(Gr_charContext.class,i);
		}
		public List<Gr_digitContext> gr_digit() {
			return getRuleContexts(Gr_digitContext.class);
		}
		public Gr_digitContext gr_digit(int i) {
			return getRuleContext(Gr_digitContext.class,i);
		}
		public Gr_qualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_qualifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_qualifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_qualifierContext gr_qualifier() throws RecognitionException {
		Gr_qualifierContext _localctx = new Gr_qualifierContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_gr_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(204);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__19:
				case T__20:
				case T__21:
				case T__22:
				case T__23:
				case T__24:
				case T__25:
				case T__26:
				case T__27:
				case T__28:
				case T__29:
				case T__30:
				case T__31:
				case T__32:
				case T__33:
				case T__34:
				case T__35:
				case T__36:
				case T__37:
				case T__38:
				case T__39:
				case T__40:
				case T__41:
				case T__42:
				case T__43:
				case T__44:
				case T__45:
				case T__46:
				case T__47:
				case T__48:
				case T__49:
				case T__50:
				case T__51:
				case T__52:
				case T__53:
				case T__54:
				case T__55:
				case T__56:
				case T__57:
				case T__58:
				case T__59:
				case T__60:
				case T__61:
				case T__62:
				case T__63:
				case T__64:
				case T__65:
				case T__66:
				case T__67:
				case T__68:
				case T__69:
				case T__70:
					{
					setState(201);
					gr_char();
					}
					break;
				case T__71:
				case T__72:
				case T__73:
				case T__74:
				case T__75:
				case T__76:
				case T__77:
				case T__78:
				case T__79:
				case T__80:
					{
					setState(202);
					gr_digit();
					}
					break;
				case T__16:
					{
					setState(203);
					match(T__16);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(206); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (T__74 - 64)) | (1L << (T__75 - 64)) | (1L << (T__76 - 64)) | (1L << (T__77 - 64)) | (1L << (T__78 - 64)) | (1L << (T__79 - 64)) | (1L << (T__80 - 64)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_fieldContext extends ParserRuleContext {
		public Gr_qualifierContext gr_qualifier() {
			return getRuleContext(Gr_qualifierContext.class,0);
		}
		public Gr_parameterContext gr_parameter() {
			return getRuleContext(Gr_parameterContext.class,0);
		}
		public Gr_fieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_fieldContext gr_field() throws RecognitionException {
		Gr_fieldContext _localctx = new Gr_fieldContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_gr_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			gr_qualifier();
			setState(209);
			match(T__1);
			setState(210);
			gr_parameter();
			setState(211);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_parameterContext extends ParserRuleContext {
		public List<Gr_qualifierContext> gr_qualifier() {
			return getRuleContexts(Gr_qualifierContext.class);
		}
		public Gr_qualifierContext gr_qualifier(int i) {
			return getRuleContext(Gr_qualifierContext.class,i);
		}
		public Gr_numberContext gr_number() {
			return getRuleContext(Gr_numberContext.class,0);
		}
		public Gr_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_parameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_parameterContext gr_parameter() throws RecognitionException {
		Gr_parameterContext _localctx = new Gr_parameterContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_gr_parameter);
		try {
			setState(222);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				gr_qualifier();
				setState(214);
				match(T__17);
				setState(215);
				gr_qualifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				match(T__18);
				setState(218);
				gr_number();
				setState(219);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(221);
				gr_qualifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_lc_charContext extends ParserRuleContext {
		public Gr_lc_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_lc_char; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_lc_char(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_lc_charContext gr_lc_char() throws RecognitionException {
		Gr_lc_charContext _localctx = new Gr_lc_charContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_gr_lc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_uc_charContext extends ParserRuleContext {
		public Gr_uc_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_uc_char; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_uc_char(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_uc_charContext gr_uc_char() throws RecognitionException {
		Gr_uc_charContext _localctx = new Gr_uc_charContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_gr_uc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			_la = _input.LA(1);
			if ( !(((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (T__45 - 46)) | (1L << (T__46 - 46)) | (1L << (T__47 - 46)) | (1L << (T__48 - 46)) | (1L << (T__49 - 46)) | (1L << (T__50 - 46)) | (1L << (T__51 - 46)) | (1L << (T__52 - 46)) | (1L << (T__53 - 46)) | (1L << (T__54 - 46)) | (1L << (T__55 - 46)) | (1L << (T__56 - 46)) | (1L << (T__57 - 46)) | (1L << (T__58 - 46)) | (1L << (T__59 - 46)) | (1L << (T__60 - 46)) | (1L << (T__61 - 46)) | (1L << (T__62 - 46)) | (1L << (T__63 - 46)) | (1L << (T__64 - 46)) | (1L << (T__65 - 46)) | (1L << (T__66 - 46)) | (1L << (T__67 - 46)) | (1L << (T__68 - 46)) | (1L << (T__69 - 46)) | (1L << (T__70 - 46)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_charContext extends ParserRuleContext {
		public Gr_lc_charContext gr_lc_char() {
			return getRuleContext(Gr_lc_charContext.class,0);
		}
		public Gr_uc_charContext gr_uc_char() {
			return getRuleContext(Gr_uc_charContext.class,0);
		}
		public Gr_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_char; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_char(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_charContext gr_char() throws RecognitionException {
		Gr_charContext _localctx = new Gr_charContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_gr_char);
		try {
			setState(230);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				gr_lc_char();
				}
				break;
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
			case T__50:
			case T__51:
			case T__52:
			case T__53:
			case T__54:
			case T__55:
			case T__56:
			case T__57:
			case T__58:
			case T__59:
			case T__60:
			case T__61:
			case T__62:
			case T__63:
			case T__64:
			case T__65:
			case T__66:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				gr_uc_char();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_digitContext extends ParserRuleContext {
		public Gr_digitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_digit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_digit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_digitContext gr_digit() throws RecognitionException {
		Gr_digitContext _localctx = new Gr_digitContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_gr_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_la = _input.LA(1);
			if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (T__71 - 72)) | (1L << (T__72 - 72)) | (1L << (T__73 - 72)) | (1L << (T__74 - 72)) | (1L << (T__75 - 72)) | (1L << (T__76 - 72)) | (1L << (T__77 - 72)) | (1L << (T__78 - 72)) | (1L << (T__79 - 72)) | (1L << (T__80 - 72)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_numberContext extends ParserRuleContext {
		public List<Gr_digitContext> gr_digit() {
			return getRuleContexts(Gr_digitContext.class);
		}
		public Gr_digitContext gr_digit(int i) {
			return getRuleContext(Gr_digitContext.class,i);
		}
		public Gr_numberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_number; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_number(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_numberContext gr_number() throws RecognitionException {
		Gr_numberContext _localctx = new Gr_numberContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_gr_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(234);
				gr_digit();
				}
				}
				setState(237); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (T__71 - 72)) | (1L << (T__72 - 72)) | (1L << (T__73 - 72)) | (1L << (T__74 - 72)) | (1L << (T__75 - 72)) | (1L << (T__76 - 72)) | (1L << (T__77 - 72)) | (1L << (T__78 - 72)) | (1L << (T__79 - 72)) | (1L << (T__80 - 72)))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_hex_digitContext extends ParserRuleContext {
		public Gr_digitContext gr_digit() {
			return getRuleContext(Gr_digitContext.class,0);
		}
		public Gr_hex_digitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_hex_digit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_hex_digit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_hex_digitContext gr_hex_digit() throws RecognitionException {
		Gr_hex_digitContext _localctx = new Gr_hex_digitContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_gr_hex_digit);
		try {
			setState(246);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
				enterOuterAlt(_localctx, 1);
				{
				setState(239);
				gr_digit();
				}
				break;
			case T__45:
				enterOuterAlt(_localctx, 2);
				{
				setState(240);
				match(T__45);
				}
				break;
			case T__46:
				enterOuterAlt(_localctx, 3);
				{
				setState(241);
				match(T__46);
				}
				break;
			case T__47:
				enterOuterAlt(_localctx, 4);
				{
				setState(242);
				match(T__47);
				}
				break;
			case T__48:
				enterOuterAlt(_localctx, 5);
				{
				setState(243);
				match(T__48);
				}
				break;
			case T__49:
				enterOuterAlt(_localctx, 6);
				{
				setState(244);
				match(T__49);
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 7);
				{
				setState(245);
				match(T__50);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_hexContext extends ParserRuleContext {
		public List<Gr_hex_digitContext> gr_hex_digit() {
			return getRuleContexts(Gr_hex_digitContext.class);
		}
		public Gr_hex_digitContext gr_hex_digit(int i) {
			return getRuleContext(Gr_hex_digitContext.class,i);
		}
		public Gr_hexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_hex; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_hex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_hexContext gr_hex() throws RecognitionException {
		Gr_hexContext _localctx = new Gr_hexContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_gr_hex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(T__81);
			setState(249);
			gr_hex_digit();
			setState(250);
			gr_hex_digit();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_field_declarationContext extends ParserRuleContext {
		public Gr_field_idContext gr_field_id() {
			return getRuleContext(Gr_field_idContext.class,0);
		}
		public Gr_field_positionsContext gr_field_positions() {
			return getRuleContext(Gr_field_positionsContext.class,0);
		}
		public Gr_field_paramsContext gr_field_params() {
			return getRuleContext(Gr_field_paramsContext.class,0);
		}
		public Gr_field_valuesContext gr_field_values() {
			return getRuleContext(Gr_field_valuesContext.class,0);
		}
		public Gr_field_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_field_declarationContext gr_field_declaration() throws RecognitionException {
		Gr_field_declarationContext _localctx = new Gr_field_declarationContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_gr_field_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(T__82);
			setState(253);
			gr_field_id();
			setState(254);
			match(T__83);
			setState(255);
			gr_field_positions();
			setState(256);
			gr_field_params();
			setState(257);
			gr_field_values();
			setState(258);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_field_idContext extends ParserRuleContext {
		public Gr_qualifierContext gr_qualifier() {
			return getRuleContext(Gr_qualifierContext.class,0);
		}
		public Gr_field_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_field_idContext gr_field_id() throws RecognitionException {
		Gr_field_idContext _localctx = new Gr_field_idContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_gr_field_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			gr_qualifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_field_positionsContext extends ParserRuleContext {
		public List<Gr_numberContext> gr_number() {
			return getRuleContexts(Gr_numberContext.class);
		}
		public Gr_numberContext gr_number(int i) {
			return getRuleContext(Gr_numberContext.class,i);
		}
		public Gr_field_positionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field_positions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field_positions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_field_positionsContext gr_field_positions() throws RecognitionException {
		Gr_field_positionsContext _localctx = new Gr_field_positionsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_gr_field_positions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(T__3);
			{
			setState(263);
			gr_number();
			setState(264);
			match(T__5);
			setState(265);
			gr_number();
			}
			setState(267);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_field_paramsContext extends ParserRuleContext {
		public List<Gr_parameterContext> gr_parameter() {
			return getRuleContexts(Gr_parameterContext.class);
		}
		public Gr_parameterContext gr_parameter(int i) {
			return getRuleContext(Gr_parameterContext.class,i);
		}
		public Gr_field_paramsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field_params(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_field_paramsContext gr_field_params() throws RecognitionException {
		Gr_field_paramsContext _localctx = new Gr_field_paramsContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_gr_field_params);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(T__3);
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(270);
				gr_parameter();
				}
				break;
			case 2:
				{
				{
				setState(274); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(271);
						gr_parameter();
						setState(272);
						match(T__5);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(276); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(278);
				gr_parameter();
				}
				}
				break;
			}
			setState(282);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Gr_field_valuesContext extends ParserRuleContext {
		public List<Gr_numberContext> gr_number() {
			return getRuleContexts(Gr_numberContext.class);
		}
		public Gr_numberContext gr_number(int i) {
			return getRuleContext(Gr_numberContext.class,i);
		}
		public Gr_field_valuesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_field_values; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_field_values(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_field_valuesContext gr_field_values() throws RecognitionException {
		Gr_field_valuesContext _localctx = new Gr_field_valuesContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_gr_field_values);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(T__3);
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(285);
				gr_number();
				}
				break;
			case 2:
				{
				{
				setState(289); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(286);
						gr_number();
						setState(287);
						match(T__5);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(291); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(293);
				gr_number();
				}
				}
				break;
			}
			setState(297);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3Y\u012e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\7\2J\n\2\f\2\16\2M\13\2\3\2\3\2\3\2\7\2R\n"+
		"\2\f\2\16\2U\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\5\5b\n\5"+
		"\3\5\3\5\3\5\3\5\3\5\5\5i\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\6\bu\n\b\r\b\16\bv\3\b\3\b\5\b{\n\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\6\f\u008c\n\f\r\f\16\f\u008d\3\f\3\f\5"+
		"\f\u0092\n\f\3\r\3\r\3\r\7\r\u0097\n\r\f\r\16\r\u009a\13\r\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\6\22\u00b1\n\22\r\22\16\22\u00b2\3\22\3\22\5\22"+
		"\u00b7\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u00c3"+
		"\n\24\f\24\16\24\u00c6\13\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\6\26\u00cf"+
		"\n\26\r\26\16\26\u00d0\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\5\30\u00e1\n\30\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\5\33\u00e9\n\33\3\34\3\34\3\35\6\35\u00ee\n\35\r\35\16\35\u00ef\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u00f9\n\36\3\37\3\37\3\37\3\37\3 "+
		"\3 \3 \3 \3 \3 \3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3"+
		"#\6#\u0115\n#\r#\16#\u0116\3#\3#\5#\u011b\n#\3#\3#\3$\3$\3$\3$\3$\6$\u0124"+
		"\n$\r$\16$\u0125\3$\3$\5$\u012a\n$\3$\3$\3$\2\2%\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDF\2\5\3\2\26/\3\2\60I\3"+
		"\2JS\2\u012d\2K\3\2\2\2\4V\3\2\2\2\6]\3\2\2\2\ba\3\2\2\2\nj\3\2\2\2\f"+
		"l\3\2\2\2\16z\3\2\2\2\20|\3\2\2\2\22\u0080\3\2\2\2\24\u0083\3\2\2\2\26"+
		"\u0091\3\2\2\2\30\u0098\3\2\2\2\32\u009b\3\2\2\2\34\u009f\3\2\2\2\36\u00a4"+
		"\3\2\2\2 \u00a8\3\2\2\2\"\u00b6\3\2\2\2$\u00b8\3\2\2\2&\u00c4\3\2\2\2"+
		"(\u00c7\3\2\2\2*\u00ce\3\2\2\2,\u00d2\3\2\2\2.\u00e0\3\2\2\2\60\u00e2"+
		"\3\2\2\2\62\u00e4\3\2\2\2\64\u00e8\3\2\2\2\66\u00ea\3\2\2\28\u00ed\3\2"+
		"\2\2:\u00f8\3\2\2\2<\u00fa\3\2\2\2>\u00fe\3\2\2\2@\u0106\3\2\2\2B\u0108"+
		"\3\2\2\2D\u010f\3\2\2\2F\u011e\3\2\2\2HJ\5> \2IH\3\2\2\2JM\3\2\2\2KI\3"+
		"\2\2\2KL\3\2\2\2LN\3\2\2\2MK\3\2\2\2NS\5(\25\2OR\5\32\16\2PR\5\36\20\2"+
		"QO\3\2\2\2QP\3\2\2\2RU\3\2\2\2SQ\3\2\2\2ST\3\2\2\2T\3\3\2\2\2US\3\2\2"+
		"\2VW\7\3\2\2WX\5\6\4\2XY\7\4\2\2YZ\5\b\5\2Z[\7\5\2\2[\\\7\6\2\2\\\5\3"+
		"\2\2\2]^\5*\26\2^\7\3\2\2\2_b\7\7\2\2`b\5<\37\2a_\3\2\2\2a`\3\2\2\2bh"+
		"\3\2\2\2cd\7\b\2\2de\5<\37\2ef\7\b\2\2fg\58\35\2gi\3\2\2\2hc\3\2\2\2h"+
		"i\3\2\2\2i\t\3\2\2\2jk\7\t\2\2k\13\3\2\2\2lm\7\n\2\2mn\5\16\b\2no\7\13"+
		"\2\2o\r\3\2\2\2p{\5,\27\2qr\5,\27\2rs\7\b\2\2su\3\2\2\2tq\3\2\2\2uv\3"+
		"\2\2\2vt\3\2\2\2vw\3\2\2\2wx\3\2\2\2xy\5,\27\2y{\3\2\2\2zp\3\2\2\2zt\3"+
		"\2\2\2{\17\3\2\2\2|}\7\f\2\2}~\5\22\n\2~\177\7\13\2\2\177\21\3\2\2\2\u0080"+
		"\u0081\5*\26\2\u0081\u0082\7\r\2\2\u0082\23\3\2\2\2\u0083\u0084\7\16\2"+
		"\2\u0084\u0085\5\26\f\2\u0085\u0086\7\13\2\2\u0086\25\3\2\2\2\u0087\u0092"+
		"\5,\27\2\u0088\u0089\5,\27\2\u0089\u008a\7\b\2\2\u008a\u008c\3\2\2\2\u008b"+
		"\u0088\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2"+
		"\2\2\u008e\u008f\3\2\2\2\u008f\u0090\5,\27\2\u0090\u0092\3\2\2\2\u0091"+
		"\u0087\3\2\2\2\u0091\u008b\3\2\2\2\u0092\27\3\2\2\2\u0093\u0097\5\f\7"+
		"\2\u0094\u0097\5\20\t\2\u0095\u0097\5\24\13\2\u0096\u0093\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2"+
		"\2\2\u0098\u0099\3\2\2\2\u0099\31\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c"+
		"\5\4\3\2\u009c\u009d\5\30\r\2\u009d\u009e\5\n\6\2\u009e\33\3\2\2\2\u009f"+
		"\u00a0\7\17\2\2\u00a0\u00a1\5\6\4\2\u00a1\u00a2\7\r\2\2\u00a2\u00a3\7"+
		"\6\2\2\u00a3\35\3\2\2\2\u00a4\u00a5\5\34\17\2\u00a5\u00a6\5\30\r\2\u00a6"+
		"\u00a7\5\n\6\2\u00a7\37\3\2\2\2\u00a8\u00a9\7\20\2\2\u00a9\u00aa\5\"\22"+
		"\2\u00aa\u00ab\7\13\2\2\u00ab!\3\2\2\2\u00ac\u00b7\5,\27\2\u00ad\u00ae"+
		"\5,\27\2\u00ae\u00af\7\b\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ad\3\2\2\2\u00b1"+
		"\u00b2\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b4\3\2"+
		"\2\2\u00b4\u00b5\5,\27\2\u00b5\u00b7\3\2\2\2\u00b6\u00ac\3\2\2\2\u00b6"+
		"\u00b0\3\2\2\2\u00b7#\3\2\2\2\u00b8\u00b9\7\21\2\2\u00b9\u00ba\7\4\2\2"+
		"\u00ba\u00bb\7\22\2\2\u00bb\u00bc\7\5\2\2\u00bc\u00bd\7\6\2\2\u00bd%\3"+
		"\2\2\2\u00be\u00c3\5\f\7\2\u00bf\u00c3\5\20\t\2\u00c0\u00c3\5\24\13\2"+
		"\u00c1\u00c3\5 \21\2\u00c2\u00be\3\2\2\2\u00c2\u00bf\3\2\2\2\u00c2\u00c0"+
		"\3\2\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4"+
		"\u00c5\3\2\2\2\u00c5\'\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c8\5$\23\2"+
		"\u00c8\u00c9\5&\24\2\u00c9\u00ca\5\n\6\2\u00ca)\3\2\2\2\u00cb\u00cf\5"+
		"\64\33\2\u00cc\u00cf\5\66\34\2\u00cd\u00cf\7\23\2\2\u00ce\u00cb\3\2\2"+
		"\2\u00ce\u00cc\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00ce"+
		"\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1+\3\2\2\2\u00d2\u00d3\5*\26\2\u00d3"+
		"\u00d4\7\4\2\2\u00d4\u00d5\5.\30\2\u00d5\u00d6\7\5\2\2\u00d6-\3\2\2\2"+
		"\u00d7\u00d8\5*\26\2\u00d8\u00d9\7\24\2\2\u00d9\u00da\5*\26\2\u00da\u00e1"+
		"\3\2\2\2\u00db\u00dc\7\25\2\2\u00dc\u00dd\58\35\2\u00dd\u00de\7\5\2\2"+
		"\u00de\u00e1\3\2\2\2\u00df\u00e1\5*\26\2\u00e0\u00d7\3\2\2\2\u00e0\u00db"+
		"\3\2\2\2\u00e0\u00df\3\2\2\2\u00e1/\3\2\2\2\u00e2\u00e3\t\2\2\2\u00e3"+
		"\61\3\2\2\2\u00e4\u00e5\t\3\2\2\u00e5\63\3\2\2\2\u00e6\u00e9\5\60\31\2"+
		"\u00e7\u00e9\5\62\32\2\u00e8\u00e6\3\2\2\2\u00e8\u00e7\3\2\2\2\u00e9\65"+
		"\3\2\2\2\u00ea\u00eb\t\4\2\2\u00eb\67\3\2\2\2\u00ec\u00ee\5\66\34\2\u00ed"+
		"\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f09\3\2\2\2\u00f1\u00f9\5\66\34\2\u00f2\u00f9\7\60\2\2\u00f3\u00f9"+
		"\7\61\2\2\u00f4\u00f9\7\62\2\2\u00f5\u00f9\7\63\2\2\u00f6\u00f9\7\64\2"+
		"\2\u00f7\u00f9\7\65\2\2\u00f8\u00f1\3\2\2\2\u00f8\u00f2\3\2\2\2\u00f8"+
		"\u00f3\3\2\2\2\u00f8\u00f4\3\2\2\2\u00f8\u00f5\3\2\2\2\u00f8\u00f6\3\2"+
		"\2\2\u00f8\u00f7\3\2\2\2\u00f9;\3\2\2\2\u00fa\u00fb\7T\2\2\u00fb\u00fc"+
		"\5:\36\2\u00fc\u00fd\5:\36\2\u00fd=\3\2\2\2\u00fe\u00ff\7U\2\2\u00ff\u0100"+
		"\5@!\2\u0100\u0101\7V\2\2\u0101\u0102\5B\"\2\u0102\u0103\5D#\2\u0103\u0104"+
		"\5F$\2\u0104\u0105\7\13\2\2\u0105?\3\2\2\2\u0106\u0107\5*\26\2\u0107A"+
		"\3\2\2\2\u0108\u0109\7\6\2\2\u0109\u010a\58\35\2\u010a\u010b\7\b\2\2\u010b"+
		"\u010c\58\35\2\u010c\u010d\3\2\2\2\u010d\u010e\7\t\2\2\u010eC\3\2\2\2"+
		"\u010f\u011a\7\6\2\2\u0110\u011b\5.\30\2\u0111\u0112\5.\30\2\u0112\u0113"+
		"\7\b\2\2\u0113\u0115\3\2\2\2\u0114\u0111\3\2\2\2\u0115\u0116\3\2\2\2\u0116"+
		"\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\5."+
		"\30\2\u0119\u011b\3\2\2\2\u011a\u0110\3\2\2\2\u011a\u0114\3\2\2\2\u011b"+
		"\u011c\3\2\2\2\u011c\u011d\7\t\2\2\u011dE\3\2\2\2\u011e\u0129\7\6\2\2"+
		"\u011f\u012a\58\35\2\u0120\u0121\58\35\2\u0121\u0122\7\b\2\2\u0122\u0124"+
		"\3\2\2\2\u0123\u0120\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0123\3\2\2\2\u0125"+
		"\u0126\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0128\58\35\2\u0128\u012a\3\2"+
		"\2\2\u0129\u011f\3\2\2\2\u0129\u0123\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\7\t\2\2\u012cG\3\2\2\2\33KQSahvz\u008d\u0091\u0096\u0098\u00b2"+
		"\u00b6\u00c2\u00c4\u00ce\u00d0\u00e0\u00e8\u00ef\u00f8\u0116\u011a\u0125"+
		"\u0129";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}