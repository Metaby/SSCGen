// Generated from ../processors/MicrocodeDesignLanguage.g4 by ANTLR 4.7
package antlr;
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
		T__80=81, WHITES=82, S_COMMENT=83, M_COMMENT=84;
	public static final int
		RULE_gr_mdf = 0, RULE_gr_function_head = 1, RULE_gr_function_name = 2, 
		RULE_gr_function_pos = 3, RULE_gr_function_tail = 4, RULE_gr_function_set = 5, 
		RULE_gr_function_set_code = 6, RULE_gr_function_call = 7, RULE_gr_function_call_code = 8, 
		RULE_gr_function_fix = 9, RULE_gr_function_fix_code = 10, RULE_gr_function_body = 11, 
		RULE_gr_function = 12, RULE_gr_virtual_head = 13, RULE_gr_virtual = 14, 
		RULE_gr_qualifier = 15, RULE_gr_field = 16, RULE_gr_parameter = 17, RULE_gr_lc_char = 18, 
		RULE_gr_uc_char = 19, RULE_gr_char = 20, RULE_gr_digit = 21, RULE_gr_number = 22, 
		RULE_gr_hex_digit = 23, RULE_gr_hex = 24, RULE_gr_file = 25, RULE_gr_import = 26;
	public static final String[] ruleNames = {
		"gr_mdf", "gr_function_head", "gr_function_name", "gr_function_pos", "gr_function_tail", 
		"gr_function_set", "gr_function_set_code", "gr_function_call", "gr_function_call_code", 
		"gr_function_fix", "gr_function_fix_code", "gr_function_body", "gr_function", 
		"gr_virtual_head", "gr_virtual", "gr_qualifier", "gr_field", "gr_parameter", 
		"gr_lc_char", "gr_uc_char", "gr_char", "gr_digit", "gr_number", "gr_hex_digit", 
		"gr_hex", "gr_file", "gr_import"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'function'", "'('", "')'", "'{'", "'}'", "'set'", "';'", "','", 
		"'call'", "'()'", "'fix'", "'virtual'", "'_'", "'.'", "'CONST('", "'a'", 
		"'b'", "'c'", "'d'", "'e'", "'f'", "'g'", "'h'", "'i'", "'j'", "'k'", 
		"'l'", "'m'", "'n'", "'o'", "'p'", "'q'", "'r'", "'s'", "'t'", "'u'", 
		"'v'", "'w'", "'x'", "'y'", "'z'", "'A'", "'B'", "'C'", "'D'", "'E'", 
		"'F'", "'G'", "'H'", "'I'", "'J'", "'K'", "'L'", "'M'", "'N'", "'O'", 
		"'P'", "'Q'", "'R'", "'S'", "'T'", "'U'", "'V'", "'W'", "'X'", "'Y'", 
		"'Z'", "'0'", "'1'", "'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", 
		"'9'", "'0x'", "':/'", "'/'", "'definition'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "WHITES", 
		"S_COMMENT", "M_COMMENT"
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
		public List<Gr_importContext> gr_import() {
			return getRuleContexts(Gr_importContext.class);
		}
		public Gr_importContext gr_import(int i) {
			return getRuleContext(Gr_importContext.class,i);
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
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__80) {
				{
				{
				setState(54);
				gr_import();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0 || _la==T__11) {
				{
				setState(62);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(60);
					gr_function();
					}
					break;
				case T__11:
					{
					setState(61);
					gr_virtual();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(66);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__0);
			setState(68);
			gr_function_name();
			setState(69);
			match(T__1);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__77) {
				{
				setState(70);
				gr_function_pos();
				}
			}

			setState(73);
			match(T__2);
			setState(74);
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
			setState(76);
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
		public Gr_hexContext gr_hex() {
			return getRuleContext(Gr_hexContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			gr_hex();
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
			setState(80);
			match(T__4);
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
			setState(82);
			match(T__5);
			setState(83);
			gr_function_set_code();
			setState(84);
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
			setState(96);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(86);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(90); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(87);
						gr_field();
						setState(88);
						match(T__7);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(92); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(94);
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
			setState(98);
			match(T__8);
			setState(99);
			gr_function_call_code();
			setState(100);
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
			setState(102);
			gr_qualifier();
			setState(103);
			match(T__9);
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
			setState(105);
			match(T__10);
			setState(106);
			gr_function_fix_code();
			setState(107);
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
			setState(119);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(109);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(113); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(110);
						gr_field();
						setState(111);
						match(T__7);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(115); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(117);
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
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__8) | (1L << T__10))) != 0)) {
				{
				setState(124);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__5:
					{
					setState(121);
					gr_function_set();
					}
					break;
				case T__8:
					{
					setState(122);
					gr_function_call();
					}
					break;
				case T__10:
					{
					setState(123);
					gr_function_fix();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(128);
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
			setState(129);
			gr_function_head();
			setState(130);
			gr_function_body();
			setState(131);
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
			setState(133);
			match(T__11);
			setState(134);
			gr_function_name();
			setState(135);
			match(T__9);
			setState(136);
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
			setState(138);
			gr_virtual_head();
			setState(139);
			gr_function_body();
			setState(140);
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
		enterRule(_localctx, 30, RULE_gr_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(145);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__15:
				case T__16:
				case T__17:
				case T__18:
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
					{
					setState(142);
					gr_char();
					}
					break;
				case T__67:
				case T__68:
				case T__69:
				case T__70:
				case T__71:
				case T__72:
				case T__73:
				case T__74:
				case T__75:
				case T__76:
					{
					setState(143);
					gr_digit();
					}
					break;
				case T__12:
					{
					setState(144);
					match(T__12);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(147); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (T__74 - 64)) | (1L << (T__75 - 64)) | (1L << (T__76 - 64)))) != 0) );
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
		enterRule(_localctx, 32, RULE_gr_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			gr_qualifier();
			setState(150);
			match(T__1);
			setState(151);
			gr_parameter();
			setState(152);
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
		enterRule(_localctx, 34, RULE_gr_parameter);
		try {
			setState(163);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				gr_qualifier();
				setState(155);
				match(T__13);
				setState(156);
				gr_qualifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(158);
				match(T__14);
				setState(159);
				gr_number();
				setState(160);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(162);
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
		enterRule(_localctx, 36, RULE_gr_lc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40))) != 0)) ) {
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
		enterRule(_localctx, 38, RULE_gr_uc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_la = _input.LA(1);
			if ( !(((((_la - 42)) & ~0x3f) == 0 && ((1L << (_la - 42)) & ((1L << (T__41 - 42)) | (1L << (T__42 - 42)) | (1L << (T__43 - 42)) | (1L << (T__44 - 42)) | (1L << (T__45 - 42)) | (1L << (T__46 - 42)) | (1L << (T__47 - 42)) | (1L << (T__48 - 42)) | (1L << (T__49 - 42)) | (1L << (T__50 - 42)) | (1L << (T__51 - 42)) | (1L << (T__52 - 42)) | (1L << (T__53 - 42)) | (1L << (T__54 - 42)) | (1L << (T__55 - 42)) | (1L << (T__56 - 42)) | (1L << (T__57 - 42)) | (1L << (T__58 - 42)) | (1L << (T__59 - 42)) | (1L << (T__60 - 42)) | (1L << (T__61 - 42)) | (1L << (T__62 - 42)) | (1L << (T__63 - 42)) | (1L << (T__64 - 42)) | (1L << (T__65 - 42)) | (1L << (T__66 - 42)))) != 0)) ) {
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
		enterRule(_localctx, 40, RULE_gr_char);
		try {
			setState(171);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
			case T__16:
			case T__17:
			case T__18:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(169);
				gr_lc_char();
				}
				break;
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
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
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
		enterRule(_localctx, 42, RULE_gr_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_la = _input.LA(1);
			if ( !(((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (T__67 - 68)) | (1L << (T__68 - 68)) | (1L << (T__69 - 68)) | (1L << (T__70 - 68)) | (1L << (T__71 - 68)) | (1L << (T__72 - 68)) | (1L << (T__73 - 68)) | (1L << (T__74 - 68)) | (1L << (T__75 - 68)) | (1L << (T__76 - 68)))) != 0)) ) {
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
		enterRule(_localctx, 44, RULE_gr_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(175);
				gr_digit();
				}
				}
				setState(178); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (T__67 - 68)) | (1L << (T__68 - 68)) | (1L << (T__69 - 68)) | (1L << (T__70 - 68)) | (1L << (T__71 - 68)) | (1L << (T__72 - 68)) | (1L << (T__73 - 68)) | (1L << (T__74 - 68)) | (1L << (T__75 - 68)) | (1L << (T__76 - 68)))) != 0) );
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
		enterRule(_localctx, 46, RULE_gr_hex_digit);
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				gr_digit();
				}
				break;
			case T__41:
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				match(T__41);
				}
				break;
			case T__42:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				match(T__42);
				}
				break;
			case T__43:
				enterOuterAlt(_localctx, 4);
				{
				setState(183);
				match(T__43);
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 5);
				{
				setState(184);
				match(T__44);
				}
				break;
			case T__45:
				enterOuterAlt(_localctx, 6);
				{
				setState(185);
				match(T__45);
				}
				break;
			case T__46:
				enterOuterAlt(_localctx, 7);
				{
				setState(186);
				match(T__46);
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
		enterRule(_localctx, 48, RULE_gr_hex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(T__77);
			setState(190);
			gr_hex_digit();
			setState(191);
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

	public static class Gr_fileContext extends ParserRuleContext {
		public List<Gr_qualifierContext> gr_qualifier() {
			return getRuleContexts(Gr_qualifierContext.class);
		}
		public Gr_qualifierContext gr_qualifier(int i) {
			return getRuleContext(Gr_qualifierContext.class,i);
		}
		public List<Gr_charContext> gr_char() {
			return getRuleContexts(Gr_charContext.class);
		}
		public Gr_charContext gr_char(int i) {
			return getRuleContext(Gr_charContext.class,i);
		}
		public Gr_fileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_file; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_file(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_fileContext gr_file() throws RecognitionException {
		Gr_fileContext _localctx = new Gr_fileContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_gr_file);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(193);
				gr_char();
				setState(194);
				match(T__78);
				}
				break;
			}
			setState(208);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(198);
				gr_qualifier();
				}
				break;
			case 2:
				{
				{
				setState(202); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(199);
						gr_qualifier();
						setState(200);
						match(T__79);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(204); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(206);
				gr_qualifier();
				}
				}
				break;
			}
			setState(210);
			match(T__13);
			setState(212); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(211);
				gr_char();
				}
				}
				setState(214); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (T__15 - 16)) | (1L << (T__16 - 16)) | (1L << (T__17 - 16)) | (1L << (T__18 - 16)) | (1L << (T__19 - 16)) | (1L << (T__20 - 16)) | (1L << (T__21 - 16)) | (1L << (T__22 - 16)) | (1L << (T__23 - 16)) | (1L << (T__24 - 16)) | (1L << (T__25 - 16)) | (1L << (T__26 - 16)) | (1L << (T__27 - 16)) | (1L << (T__28 - 16)) | (1L << (T__29 - 16)) | (1L << (T__30 - 16)) | (1L << (T__31 - 16)) | (1L << (T__32 - 16)) | (1L << (T__33 - 16)) | (1L << (T__34 - 16)) | (1L << (T__35 - 16)) | (1L << (T__36 - 16)) | (1L << (T__37 - 16)) | (1L << (T__38 - 16)) | (1L << (T__39 - 16)) | (1L << (T__40 - 16)) | (1L << (T__41 - 16)) | (1L << (T__42 - 16)) | (1L << (T__43 - 16)) | (1L << (T__44 - 16)) | (1L << (T__45 - 16)) | (1L << (T__46 - 16)) | (1L << (T__47 - 16)) | (1L << (T__48 - 16)) | (1L << (T__49 - 16)) | (1L << (T__50 - 16)) | (1L << (T__51 - 16)) | (1L << (T__52 - 16)) | (1L << (T__53 - 16)) | (1L << (T__54 - 16)) | (1L << (T__55 - 16)) | (1L << (T__56 - 16)) | (1L << (T__57 - 16)) | (1L << (T__58 - 16)) | (1L << (T__59 - 16)) | (1L << (T__60 - 16)) | (1L << (T__61 - 16)) | (1L << (T__62 - 16)) | (1L << (T__63 - 16)) | (1L << (T__64 - 16)) | (1L << (T__65 - 16)) | (1L << (T__66 - 16)))) != 0) );
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

	public static class Gr_importContext extends ParserRuleContext {
		public Gr_fileContext gr_file() {
			return getRuleContext(Gr_fileContext.class,0);
		}
		public Gr_importContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_import; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_import(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_importContext gr_import() throws RecognitionException {
		Gr_importContext _localctx = new Gr_importContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_gr_import);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(T__80);
			setState(217);
			gr_file();
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3V\u00de\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\7\2:\n\2\f\2\16\2=\13\2\3\2\3\2\7\2"+
		"A\n\2\f\2\16\2D\13\2\3\3\3\3\3\3\3\3\5\3J\n\3\3\3\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\6\b]\n\b\r\b\16\b^\3\b\3"+
		"\b\5\bc\n\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\6\ft\n\f\r\f\16\fu\3\f\3\f\5\fz\n\f\3\r\3\r\3\r\7\r\177\n\r\f\r"+
		"\16\r\u0082\13\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\6\21\u0094\n\21\r\21\16\21\u0095\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00a6"+
		"\n\23\3\24\3\24\3\25\3\25\3\26\3\26\5\26\u00ae\n\26\3\27\3\27\3\30\6\30"+
		"\u00b3\n\30\r\30\16\30\u00b4\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u00be"+
		"\n\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\5\33\u00c7\n\33\3\33\3\33\3\33"+
		"\3\33\6\33\u00cd\n\33\r\33\16\33\u00ce\3\33\3\33\5\33\u00d3\n\33\3\33"+
		"\3\33\6\33\u00d7\n\33\r\33\16\33\u00d8\3\34\3\34\3\34\3\34\2\2\35\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\5\3\2\22+\3"+
		"\2,E\3\2FO\2\u00de\2;\3\2\2\2\4E\3\2\2\2\6N\3\2\2\2\bP\3\2\2\2\nR\3\2"+
		"\2\2\fT\3\2\2\2\16b\3\2\2\2\20d\3\2\2\2\22h\3\2\2\2\24k\3\2\2\2\26y\3"+
		"\2\2\2\30\u0080\3\2\2\2\32\u0083\3\2\2\2\34\u0087\3\2\2\2\36\u008c\3\2"+
		"\2\2 \u0093\3\2\2\2\"\u0097\3\2\2\2$\u00a5\3\2\2\2&\u00a7\3\2\2\2(\u00a9"+
		"\3\2\2\2*\u00ad\3\2\2\2,\u00af\3\2\2\2.\u00b2\3\2\2\2\60\u00bd\3\2\2\2"+
		"\62\u00bf\3\2\2\2\64\u00c6\3\2\2\2\66\u00da\3\2\2\28:\5\66\34\298\3\2"+
		"\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<B\3\2\2\2=;\3\2\2\2>A\5\32\16\2?A\5"+
		"\36\20\2@>\3\2\2\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\3\3\2\2\2"+
		"DB\3\2\2\2EF\7\3\2\2FG\5\6\4\2GI\7\4\2\2HJ\5\b\5\2IH\3\2\2\2IJ\3\2\2\2"+
		"JK\3\2\2\2KL\7\5\2\2LM\7\6\2\2M\5\3\2\2\2NO\5 \21\2O\7\3\2\2\2PQ\5\62"+
		"\32\2Q\t\3\2\2\2RS\7\7\2\2S\13\3\2\2\2TU\7\b\2\2UV\5\16\b\2VW\7\t\2\2"+
		"W\r\3\2\2\2Xc\5\"\22\2YZ\5\"\22\2Z[\7\n\2\2[]\3\2\2\2\\Y\3\2\2\2]^\3\2"+
		"\2\2^\\\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\5\"\22\2ac\3\2\2\2bX\3\2\2\2b\\"+
		"\3\2\2\2c\17\3\2\2\2de\7\13\2\2ef\5\22\n\2fg\7\t\2\2g\21\3\2\2\2hi\5 "+
		"\21\2ij\7\f\2\2j\23\3\2\2\2kl\7\r\2\2lm\5\26\f\2mn\7\t\2\2n\25\3\2\2\2"+
		"oz\5\"\22\2pq\5\"\22\2qr\7\n\2\2rt\3\2\2\2sp\3\2\2\2tu\3\2\2\2us\3\2\2"+
		"\2uv\3\2\2\2vw\3\2\2\2wx\5\"\22\2xz\3\2\2\2yo\3\2\2\2ys\3\2\2\2z\27\3"+
		"\2\2\2{\177\5\f\7\2|\177\5\20\t\2}\177\5\24\13\2~{\3\2\2\2~|\3\2\2\2~"+
		"}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\31"+
		"\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084\5\4\3\2\u0084\u0085\5\30\r\2"+
		"\u0085\u0086\5\n\6\2\u0086\33\3\2\2\2\u0087\u0088\7\16\2\2\u0088\u0089"+
		"\5\6\4\2\u0089\u008a\7\f\2\2\u008a\u008b\7\6\2\2\u008b\35\3\2\2\2\u008c"+
		"\u008d\5\34\17\2\u008d\u008e\5\30\r\2\u008e\u008f\5\n\6\2\u008f\37\3\2"+
		"\2\2\u0090\u0094\5*\26\2\u0091\u0094\5,\27\2\u0092\u0094\7\17\2\2\u0093"+
		"\u0090\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2"+
		"\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096!\3\2\2\2\u0097\u0098"+
		"\5 \21\2\u0098\u0099\7\4\2\2\u0099\u009a\5$\23\2\u009a\u009b\7\5\2\2\u009b"+
		"#\3\2\2\2\u009c\u009d\5 \21\2\u009d\u009e\7\20\2\2\u009e\u009f\5 \21\2"+
		"\u009f\u00a6\3\2\2\2\u00a0\u00a1\7\21\2\2\u00a1\u00a2\5.\30\2\u00a2\u00a3"+
		"\7\5\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a6\5 \21\2\u00a5\u009c\3\2\2\2\u00a5"+
		"\u00a0\3\2\2\2\u00a5\u00a4\3\2\2\2\u00a6%\3\2\2\2\u00a7\u00a8\t\2\2\2"+
		"\u00a8\'\3\2\2\2\u00a9\u00aa\t\3\2\2\u00aa)\3\2\2\2\u00ab\u00ae\5&\24"+
		"\2\u00ac\u00ae\5(\25\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae+"+
		"\3\2\2\2\u00af\u00b0\t\4\2\2\u00b0-\3\2\2\2\u00b1\u00b3\5,\27\2\u00b2"+
		"\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2"+
		"\2\2\u00b5/\3\2\2\2\u00b6\u00be\5,\27\2\u00b7\u00be\7,\2\2\u00b8\u00be"+
		"\7-\2\2\u00b9\u00be\7.\2\2\u00ba\u00be\7/\2\2\u00bb\u00be\7\60\2\2\u00bc"+
		"\u00be\7\61\2\2\u00bd\u00b6\3\2\2\2\u00bd\u00b7\3\2\2\2\u00bd\u00b8\3"+
		"\2\2\2\u00bd\u00b9\3\2\2\2\u00bd\u00ba\3\2\2\2\u00bd\u00bb\3\2\2\2\u00bd"+
		"\u00bc\3\2\2\2\u00be\61\3\2\2\2\u00bf\u00c0\7P\2\2\u00c0\u00c1\5\60\31"+
		"\2\u00c1\u00c2\5\60\31\2\u00c2\63\3\2\2\2\u00c3\u00c4\5*\26\2\u00c4\u00c5"+
		"\7Q\2\2\u00c5\u00c7\3\2\2\2\u00c6\u00c3\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7"+
		"\u00d2\3\2\2\2\u00c8\u00d3\5 \21\2\u00c9\u00ca\5 \21\2\u00ca\u00cb\7R"+
		"\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00c9\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\5 "+
		"\21\2\u00d1\u00d3\3\2\2\2\u00d2\u00c8\3\2\2\2\u00d2\u00cc\3\2\2\2\u00d3"+
		"\u00d4\3\2\2\2\u00d4\u00d6\7\20\2\2\u00d5\u00d7\5*\26\2\u00d6\u00d5\3"+
		"\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		"\65\3\2\2\2\u00da\u00db\7S\2\2\u00db\u00dc\5\64\33\2\u00dc\67\3\2\2\2"+
		"\26;@BI^buy~\u0080\u0093\u0095\u00a5\u00ad\u00b4\u00bd\u00c6\u00ce\u00d2"+
		"\u00d8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}