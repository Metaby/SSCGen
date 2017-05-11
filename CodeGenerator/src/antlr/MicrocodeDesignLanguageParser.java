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
		WS=81, RESTOFLINE=82;
	public static final int
		RULE_gr_comment = 0, RULE_gr_mdf = 1, RULE_gr_function_head = 2, RULE_gr_function_name = 3, 
		RULE_gr_function_pos = 4, RULE_gr_function_tail = 5, RULE_gr_function_set = 6, 
		RULE_gr_function_set_code = 7, RULE_gr_function_call = 8, RULE_gr_function_call_code = 9, 
		RULE_gr_function_body = 10, RULE_gr_function = 11, RULE_gr_qualifier = 12, 
		RULE_gr_field = 13, RULE_gr_parameter = 14, RULE_gr_lc_char = 15, RULE_gr_uc_char = 16, 
		RULE_gr_char = 17, RULE_gr_digit = 18, RULE_gr_number = 19, RULE_gr_hex_digit = 20, 
		RULE_gr_hex = 21, RULE_gr_file = 22, RULE_gr_import = 23;
	public static final String[] ruleNames = {
		"gr_comment", "gr_mdf", "gr_function_head", "gr_function_name", "gr_function_pos", 
		"gr_function_tail", "gr_function_set", "gr_function_set_code", "gr_function_call", 
		"gr_function_call_code", "gr_function_body", "gr_function", "gr_qualifier", 
		"gr_field", "gr_parameter", "gr_lc_char", "gr_uc_char", "gr_char", "gr_digit", 
		"gr_number", "gr_hex_digit", "gr_hex", "gr_file", "gr_import"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'--'", "'-- '", "'function'", "'('", "')'", "'{'", "'}'", "'set'", 
		"';'", "','", "'call'", "'_'", "'.'", "'CONST('", "'a'", "'b'", "'c'", 
		"'d'", "'e'", "'f'", "'g'", "'h'", "'i'", "'j'", "'k'", "'l'", "'m'", 
		"'n'", "'o'", "'p'", "'q'", "'r'", "'s'", "'t'", "'u'", "'v'", "'w'", 
		"'x'", "'y'", "'z'", "'A'", "'B'", "'C'", "'D'", "'E'", "'F'", "'G'", 
		"'H'", "'I'", "'J'", "'K'", "'L'", "'M'", "'N'", "'O'", "'P'", "'Q'", 
		"'R'", "'S'", "'T'", "'U'", "'V'", "'W'", "'X'", "'Y'", "'Z'", "'0'", 
		"'1'", "'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", "'9'", "'0x'", 
		"':/'", "'/'", "'definition'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "WS", "RESTOFLINE"
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
	public static class Gr_commentContext extends ParserRuleContext {
		public TerminalNode RESTOFLINE() { return getToken(MicrocodeDesignLanguageParser.RESTOFLINE, 0); }
		public Gr_commentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gr_comment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicrocodeDesignLanguageVisitor ) return ((MicrocodeDesignLanguageVisitor<? extends T>)visitor).visitGr_comment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Gr_commentContext gr_comment() throws RecognitionException {
		Gr_commentContext _localctx = new Gr_commentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_gr_comment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==T__1) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(49);
			match(RESTOFLINE);
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

	public static class Gr_mdfContext extends ParserRuleContext {
		public List<Gr_commentContext> gr_comment() {
			return getRuleContexts(Gr_commentContext.class);
		}
		public Gr_commentContext gr_comment(int i) {
			return getRuleContext(Gr_commentContext.class,i);
		}
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
		enterRule(_localctx, 2, RULE_gr_mdf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2))) != 0) || _la==T__79) {
				{
				setState(54);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
					{
					setState(51);
					gr_comment();
					}
					break;
				case T__79:
					{
					setState(52);
					gr_import();
					}
					break;
				case T__2:
					{
					setState(53);
					gr_function();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(58);
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
		enterRule(_localctx, 4, RULE_gr_function_head);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(T__2);
			setState(60);
			gr_function_name();
			setState(61);
			match(T__3);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__76) {
				{
				setState(62);
				gr_function_pos();
				}
			}

			setState(65);
			match(T__4);
			setState(66);
			match(T__5);
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
		enterRule(_localctx, 6, RULE_gr_function_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
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
		enterRule(_localctx, 8, RULE_gr_function_pos);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
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
		enterRule(_localctx, 10, RULE_gr_function_tail);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
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
		enterRule(_localctx, 12, RULE_gr_function_set);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__7);
			setState(75);
			gr_function_set_code();
			setState(76);
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
		enterRule(_localctx, 14, RULE_gr_function_set_code);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(78);
				gr_field();
				}
				break;
			case 2:
				{
				{
				setState(82); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(79);
						gr_field();
						setState(80);
						match(T__9);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(84); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(86);
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
		enterRule(_localctx, 16, RULE_gr_function_call);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__10);
			setState(91);
			gr_function_call_code();
			setState(92);
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
		enterRule(_localctx, 18, RULE_gr_function_call_code);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
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
		enterRule(_localctx, 20, RULE_gr_function_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7 || _la==T__10) {
				{
				setState(98);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
					{
					setState(96);
					gr_function_set();
					}
					break;
				case T__10:
					{
					setState(97);
					gr_function_call();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(102);
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
		enterRule(_localctx, 22, RULE_gr_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			gr_function_head();
			setState(104);
			gr_function_body();
			setState(105);
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
		enterRule(_localctx, 24, RULE_gr_qualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(110);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__14:
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
					{
					setState(107);
					gr_char();
					}
					break;
				case T__66:
				case T__67:
				case T__68:
				case T__69:
				case T__70:
				case T__71:
				case T__72:
				case T__73:
				case T__74:
				case T__75:
					{
					setState(108);
					gr_digit();
					}
					break;
				case T__11:
					{
					setState(109);
					match(T__11);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(112); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__58) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (T__74 - 64)) | (1L << (T__75 - 64)))) != 0) );
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
		enterRule(_localctx, 26, RULE_gr_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			gr_qualifier();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(115);
				match(T__3);
				setState(116);
				gr_parameter();
				setState(117);
				match(T__4);
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
		enterRule(_localctx, 28, RULE_gr_parameter);
		try {
			setState(130);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				gr_qualifier();
				setState(122);
				match(T__12);
				setState(123);
				gr_qualifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(T__13);
				setState(126);
				gr_number();
				setState(127);
				match(T__4);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
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
		enterRule(_localctx, 30, RULE_gr_lc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39))) != 0)) ) {
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
		enterRule(_localctx, 32, RULE_gr_uc_char);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_la = _input.LA(1);
			if ( !(((((_la - 41)) & ~0x3f) == 0 && ((1L << (_la - 41)) & ((1L << (T__40 - 41)) | (1L << (T__41 - 41)) | (1L << (T__42 - 41)) | (1L << (T__43 - 41)) | (1L << (T__44 - 41)) | (1L << (T__45 - 41)) | (1L << (T__46 - 41)) | (1L << (T__47 - 41)) | (1L << (T__48 - 41)) | (1L << (T__49 - 41)) | (1L << (T__50 - 41)) | (1L << (T__51 - 41)) | (1L << (T__52 - 41)) | (1L << (T__53 - 41)) | (1L << (T__54 - 41)) | (1L << (T__55 - 41)) | (1L << (T__56 - 41)) | (1L << (T__57 - 41)) | (1L << (T__58 - 41)) | (1L << (T__59 - 41)) | (1L << (T__60 - 41)) | (1L << (T__61 - 41)) | (1L << (T__62 - 41)) | (1L << (T__63 - 41)) | (1L << (T__64 - 41)) | (1L << (T__65 - 41)))) != 0)) ) {
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
		enterRule(_localctx, 34, RULE_gr_char);
		try {
			setState(138);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
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
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				gr_lc_char();
				}
				break;
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
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
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
		enterRule(_localctx, 36, RULE_gr_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (T__66 - 67)) | (1L << (T__67 - 67)) | (1L << (T__68 - 67)) | (1L << (T__69 - 67)) | (1L << (T__70 - 67)) | (1L << (T__71 - 67)) | (1L << (T__72 - 67)) | (1L << (T__73 - 67)) | (1L << (T__74 - 67)) | (1L << (T__75 - 67)))) != 0)) ) {
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
		enterRule(_localctx, 38, RULE_gr_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(142);
				gr_digit();
				}
				}
				setState(145); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (T__66 - 67)) | (1L << (T__67 - 67)) | (1L << (T__68 - 67)) | (1L << (T__69 - 67)) | (1L << (T__70 - 67)) | (1L << (T__71 - 67)) | (1L << (T__72 - 67)) | (1L << (T__73 - 67)) | (1L << (T__74 - 67)) | (1L << (T__75 - 67)))) != 0) );
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
		enterRule(_localctx, 40, RULE_gr_hex_digit);
		try {
			setState(154);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__66:
			case T__67:
			case T__68:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
				enterOuterAlt(_localctx, 1);
				{
				setState(147);
				gr_digit();
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(148);
				match(T__40);
				}
				break;
			case T__41:
				enterOuterAlt(_localctx, 3);
				{
				setState(149);
				match(T__41);
				}
				break;
			case T__42:
				enterOuterAlt(_localctx, 4);
				{
				setState(150);
				match(T__42);
				}
				break;
			case T__43:
				enterOuterAlt(_localctx, 5);
				{
				setState(151);
				match(T__43);
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 6);
				{
				setState(152);
				match(T__44);
				}
				break;
			case T__45:
				enterOuterAlt(_localctx, 7);
				{
				setState(153);
				match(T__45);
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
		enterRule(_localctx, 42, RULE_gr_hex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			match(T__76);
			setState(157);
			gr_hex_digit();
			setState(158);
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
		enterRule(_localctx, 44, RULE_gr_file);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(160);
				gr_char();
				setState(161);
				match(T__77);
				}
				break;
			}
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(165);
				gr_qualifier();
				}
				break;
			case 2:
				{
				{
				setState(169); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(166);
						gr_qualifier();
						setState(167);
						match(T__78);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(171); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(173);
				gr_qualifier();
				}
				}
				break;
			}
			setState(177);
			match(T__12);
			setState(179); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(178);
				gr_char();
				}
				}
				setState(181); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 15)) & ~0x3f) == 0 && ((1L << (_la - 15)) & ((1L << (T__14 - 15)) | (1L << (T__15 - 15)) | (1L << (T__16 - 15)) | (1L << (T__17 - 15)) | (1L << (T__18 - 15)) | (1L << (T__19 - 15)) | (1L << (T__20 - 15)) | (1L << (T__21 - 15)) | (1L << (T__22 - 15)) | (1L << (T__23 - 15)) | (1L << (T__24 - 15)) | (1L << (T__25 - 15)) | (1L << (T__26 - 15)) | (1L << (T__27 - 15)) | (1L << (T__28 - 15)) | (1L << (T__29 - 15)) | (1L << (T__30 - 15)) | (1L << (T__31 - 15)) | (1L << (T__32 - 15)) | (1L << (T__33 - 15)) | (1L << (T__34 - 15)) | (1L << (T__35 - 15)) | (1L << (T__36 - 15)) | (1L << (T__37 - 15)) | (1L << (T__38 - 15)) | (1L << (T__39 - 15)) | (1L << (T__40 - 15)) | (1L << (T__41 - 15)) | (1L << (T__42 - 15)) | (1L << (T__43 - 15)) | (1L << (T__44 - 15)) | (1L << (T__45 - 15)) | (1L << (T__46 - 15)) | (1L << (T__47 - 15)) | (1L << (T__48 - 15)) | (1L << (T__49 - 15)) | (1L << (T__50 - 15)) | (1L << (T__51 - 15)) | (1L << (T__52 - 15)) | (1L << (T__53 - 15)) | (1L << (T__54 - 15)) | (1L << (T__55 - 15)) | (1L << (T__56 - 15)) | (1L << (T__57 - 15)) | (1L << (T__58 - 15)) | (1L << (T__59 - 15)) | (1L << (T__60 - 15)) | (1L << (T__61 - 15)) | (1L << (T__62 - 15)) | (1L << (T__63 - 15)) | (1L << (T__64 - 15)) | (1L << (T__65 - 15)))) != 0) );
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
		enterRule(_localctx, 46, RULE_gr_import);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(T__79);
			setState(184);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3T\u00bd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\7\39\n\3\f\3\16\3<\13\3\3\4\3\4\3\4\3\4\5\4B"+
		"\n\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3"+
		"\t\6\tU\n\t\r\t\16\tV\3\t\3\t\5\t[\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\7\fe\n\f\f\f\16\fh\13\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\6\16q\n\16\r"+
		"\16\16\16r\3\17\3\17\3\17\3\17\3\17\5\17z\n\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u0085\n\20\3\21\3\21\3\22\3\22\3\23\3\23\5\23"+
		"\u008d\n\23\3\24\3\24\3\25\6\25\u0092\n\25\r\25\16\25\u0093\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\5\26\u009d\n\26\3\27\3\27\3\27\3\27\3\30\3\30"+
		"\3\30\5\30\u00a6\n\30\3\30\3\30\3\30\3\30\6\30\u00ac\n\30\r\30\16\30\u00ad"+
		"\3\30\3\30\5\30\u00b2\n\30\3\30\3\30\6\30\u00b6\n\30\r\30\16\30\u00b7"+
		"\3\31\3\31\3\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&(*,.\60\2\6\3\2\3\4\3\2\21*\3\2+D\3\2EN\2\u00be\2\62\3\2\2\2\4:\3\2"+
		"\2\2\6=\3\2\2\2\bF\3\2\2\2\nH\3\2\2\2\fJ\3\2\2\2\16L\3\2\2\2\20Z\3\2\2"+
		"\2\22\\\3\2\2\2\24`\3\2\2\2\26f\3\2\2\2\30i\3\2\2\2\32p\3\2\2\2\34t\3"+
		"\2\2\2\36\u0084\3\2\2\2 \u0086\3\2\2\2\"\u0088\3\2\2\2$\u008c\3\2\2\2"+
		"&\u008e\3\2\2\2(\u0091\3\2\2\2*\u009c\3\2\2\2,\u009e\3\2\2\2.\u00a5\3"+
		"\2\2\2\60\u00b9\3\2\2\2\62\63\t\2\2\2\63\64\7T\2\2\64\3\3\2\2\2\659\5"+
		"\2\2\2\669\5\60\31\2\679\5\30\r\28\65\3\2\2\28\66\3\2\2\28\67\3\2\2\2"+
		"9<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;\5\3\2\2\2<:\3\2\2\2=>\7\5\2\2>?\5\b\5"+
		"\2?A\7\6\2\2@B\5\n\6\2A@\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\7\7\2\2DE\7\b\2"+
		"\2E\7\3\2\2\2FG\5\32\16\2G\t\3\2\2\2HI\5,\27\2I\13\3\2\2\2JK\7\t\2\2K"+
		"\r\3\2\2\2LM\7\n\2\2MN\5\20\t\2NO\7\13\2\2O\17\3\2\2\2P[\5\34\17\2QR\5"+
		"\34\17\2RS\7\f\2\2SU\3\2\2\2TQ\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2W"+
		"X\3\2\2\2XY\5\34\17\2Y[\3\2\2\2ZP\3\2\2\2ZT\3\2\2\2[\21\3\2\2\2\\]\7\r"+
		"\2\2]^\5\24\13\2^_\7\13\2\2_\23\3\2\2\2`a\5\32\16\2a\25\3\2\2\2be\5\16"+
		"\b\2ce\5\22\n\2db\3\2\2\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2g\27"+
		"\3\2\2\2hf\3\2\2\2ij\5\6\4\2jk\5\26\f\2kl\5\f\7\2l\31\3\2\2\2mq\5$\23"+
		"\2nq\5&\24\2oq\7\16\2\2pm\3\2\2\2pn\3\2\2\2po\3\2\2\2qr\3\2\2\2rp\3\2"+
		"\2\2rs\3\2\2\2s\33\3\2\2\2ty\5\32\16\2uv\7\6\2\2vw\5\36\20\2wx\7\7\2\2"+
		"xz\3\2\2\2yu\3\2\2\2yz\3\2\2\2z\35\3\2\2\2{|\5\32\16\2|}\7\17\2\2}~\5"+
		"\32\16\2~\u0085\3\2\2\2\177\u0080\7\20\2\2\u0080\u0081\5(\25\2\u0081\u0082"+
		"\7\7\2\2\u0082\u0085\3\2\2\2\u0083\u0085\5\32\16\2\u0084{\3\2\2\2\u0084"+
		"\177\3\2\2\2\u0084\u0083\3\2\2\2\u0085\37\3\2\2\2\u0086\u0087\t\3\2\2"+
		"\u0087!\3\2\2\2\u0088\u0089\t\4\2\2\u0089#\3\2\2\2\u008a\u008d\5 \21\2"+
		"\u008b\u008d\5\"\22\2\u008c\u008a\3\2\2\2\u008c\u008b\3\2\2\2\u008d%\3"+
		"\2\2\2\u008e\u008f\t\5\2\2\u008f\'\3\2\2\2\u0090\u0092\5&\24\2\u0091\u0090"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		")\3\2\2\2\u0095\u009d\5&\24\2\u0096\u009d\7+\2\2\u0097\u009d\7,\2\2\u0098"+
		"\u009d\7-\2\2\u0099\u009d\7.\2\2\u009a\u009d\7/\2\2\u009b\u009d\7\60\2"+
		"\2\u009c\u0095\3\2\2\2\u009c\u0096\3\2\2\2\u009c\u0097\3\2\2\2\u009c\u0098"+
		"\3\2\2\2\u009c\u0099\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009b\3\2\2\2\u009d"+
		"+\3\2\2\2\u009e\u009f\7O\2\2\u009f\u00a0\5*\26\2\u00a0\u00a1\5*\26\2\u00a1"+
		"-\3\2\2\2\u00a2\u00a3\5$\23\2\u00a3\u00a4\7P\2\2\u00a4\u00a6\3\2\2\2\u00a5"+
		"\u00a2\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00b1\3\2\2\2\u00a7\u00b2\5\32"+
		"\16\2\u00a8\u00a9\5\32\16\2\u00a9\u00aa\7Q\2\2\u00aa\u00ac\3\2\2\2\u00ab"+
		"\u00a8\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2"+
		"\2\2\u00ae\u00af\3\2\2\2\u00af\u00b0\5\32\16\2\u00b0\u00b2\3\2\2\2\u00b1"+
		"\u00a7\3\2\2\2\u00b1\u00ab\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\7\17"+
		"\2\2\u00b4\u00b6\5$\23\2\u00b5\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7"+
		"\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8/\3\2\2\2\u00b9\u00ba\7R\2\2\u00ba"+
		"\u00bb\5.\30\2\u00bb\61\3\2\2\2\248:AVZdfpry\u0084\u008c\u0093\u009c\u00a5"+
		"\u00ad\u00b1\u00b7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}