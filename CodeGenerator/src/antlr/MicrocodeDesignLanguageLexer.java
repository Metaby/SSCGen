// Generated from ../processors/MicrocodeDesignLanguage.g4 by ANTLR 4.7
package antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MicrocodeDesignLanguageLexer extends Lexer {
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
		WS=81;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40", 
		"T__41", "T__42", "T__43", "T__44", "T__45", "T__46", "T__47", "T__48", 
		"T__49", "T__50", "T__51", "T__52", "T__53", "T__54", "T__55", "T__56", 
		"T__57", "T__58", "T__59", "T__60", "T__61", "T__62", "T__63", "T__64", 
		"T__65", "T__66", "T__67", "T__68", "T__69", "T__70", "T__71", "T__72", 
		"T__73", "T__74", "T__75", "T__76", "T__77", "T__78", "T__79", "WS", "RESTOFLINE"
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
		null, null, null, null, null, null, null, null, null, "WS"
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


	public MicrocodeDesignLanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MicrocodeDesignLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2S\u0173\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\3\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6"+
		"\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)"+
		"\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62"+
		"\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3"+
		";\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3A\3B\3B\3C\3C\3D\3D\3E\3E\3F\3"+
		"F\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\3L\3L\3M\3M\3N\3N\3N\3O\3O\3O\3P\3P\3"+
		"Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3Q\3R\6R\u0168\nR\rR\16R\u0169\3R\3R\3S\7"+
		"S\u016f\nS\fS\16S\u0172\13S\2\2T\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60"+
		"_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\u0081B\u0083C\u0085"+
		"D\u0087E\u0089F\u008bG\u008dH\u008fI\u0091J\u0093K\u0095L\u0097M\u0099"+
		"N\u009bO\u009dP\u009fQ\u00a1R\u00a3S\u00a5\2\3\2\4\5\2\13\f\17\17\"\""+
		"\4\2\f\f\17\17\2\u0173\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3"+
		"\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2"+
		"w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2"+
		"\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b"+
		"\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2"+
		"\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2\2\2\u009d"+
		"\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\3\u00a7\3\2\2"+
		"\2\5\u00aa\3\2\2\2\7\u00ae\3\2\2\2\t\u00b7\3\2\2\2\13\u00b9\3\2\2\2\r"+
		"\u00bb\3\2\2\2\17\u00bd\3\2\2\2\21\u00bf\3\2\2\2\23\u00c3\3\2\2\2\25\u00c5"+
		"\3\2\2\2\27\u00c7\3\2\2\2\31\u00cc\3\2\2\2\33\u00ce\3\2\2\2\35\u00d0\3"+
		"\2\2\2\37\u00d7\3\2\2\2!\u00d9\3\2\2\2#\u00db\3\2\2\2%\u00dd\3\2\2\2\'"+
		"\u00df\3\2\2\2)\u00e1\3\2\2\2+\u00e3\3\2\2\2-\u00e5\3\2\2\2/\u00e7\3\2"+
		"\2\2\61\u00e9\3\2\2\2\63\u00eb\3\2\2\2\65\u00ed\3\2\2\2\67\u00ef\3\2\2"+
		"\29\u00f1\3\2\2\2;\u00f3\3\2\2\2=\u00f5\3\2\2\2?\u00f7\3\2\2\2A\u00f9"+
		"\3\2\2\2C\u00fb\3\2\2\2E\u00fd\3\2\2\2G\u00ff\3\2\2\2I\u0101\3\2\2\2K"+
		"\u0103\3\2\2\2M\u0105\3\2\2\2O\u0107\3\2\2\2Q\u0109\3\2\2\2S\u010b\3\2"+
		"\2\2U\u010d\3\2\2\2W\u010f\3\2\2\2Y\u0111\3\2\2\2[\u0113\3\2\2\2]\u0115"+
		"\3\2\2\2_\u0117\3\2\2\2a\u0119\3\2\2\2c\u011b\3\2\2\2e\u011d\3\2\2\2g"+
		"\u011f\3\2\2\2i\u0121\3\2\2\2k\u0123\3\2\2\2m\u0125\3\2\2\2o\u0127\3\2"+
		"\2\2q\u0129\3\2\2\2s\u012b\3\2\2\2u\u012d\3\2\2\2w\u012f\3\2\2\2y\u0131"+
		"\3\2\2\2{\u0133\3\2\2\2}\u0135\3\2\2\2\177\u0137\3\2\2\2\u0081\u0139\3"+
		"\2\2\2\u0083\u013b\3\2\2\2\u0085\u013d\3\2\2\2\u0087\u013f\3\2\2\2\u0089"+
		"\u0141\3\2\2\2\u008b\u0143\3\2\2\2\u008d\u0145\3\2\2\2\u008f\u0147\3\2"+
		"\2\2\u0091\u0149\3\2\2\2\u0093\u014b\3\2\2\2\u0095\u014d\3\2\2\2\u0097"+
		"\u014f\3\2\2\2\u0099\u0151\3\2\2\2\u009b\u0153\3\2\2\2\u009d\u0156\3\2"+
		"\2\2\u009f\u0159\3\2\2\2\u00a1\u015b\3\2\2\2\u00a3\u0167\3\2\2\2\u00a5"+
		"\u0170\3\2\2\2\u00a7\u00a8\7/\2\2\u00a8\u00a9\7/\2\2\u00a9\4\3\2\2\2\u00aa"+
		"\u00ab\7/\2\2\u00ab\u00ac\7/\2\2\u00ac\u00ad\7\"\2\2\u00ad\6\3\2\2\2\u00ae"+
		"\u00af\7h\2\2\u00af\u00b0\7w\2\2\u00b0\u00b1\7p\2\2\u00b1\u00b2\7e\2\2"+
		"\u00b2\u00b3\7v\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6"+
		"\7p\2\2\u00b6\b\3\2\2\2\u00b7\u00b8\7*\2\2\u00b8\n\3\2\2\2\u00b9\u00ba"+
		"\7+\2\2\u00ba\f\3\2\2\2\u00bb\u00bc\7}\2\2\u00bc\16\3\2\2\2\u00bd\u00be"+
		"\7\177\2\2\u00be\20\3\2\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7g\2\2\u00c1"+
		"\u00c2\7v\2\2\u00c2\22\3\2\2\2\u00c3\u00c4\7=\2\2\u00c4\24\3\2\2\2\u00c5"+
		"\u00c6\7.\2\2\u00c6\26\3\2\2\2\u00c7\u00c8\7e\2\2\u00c8\u00c9\7c\2\2\u00c9"+
		"\u00ca\7n\2\2\u00ca\u00cb\7n\2\2\u00cb\30\3\2\2\2\u00cc\u00cd\7a\2\2\u00cd"+
		"\32\3\2\2\2\u00ce\u00cf\7\60\2\2\u00cf\34\3\2\2\2\u00d0\u00d1\7E\2\2\u00d1"+
		"\u00d2\7Q\2\2\u00d2\u00d3\7P\2\2\u00d3\u00d4\7U\2\2\u00d4\u00d5\7V\2\2"+
		"\u00d5\u00d6\7*\2\2\u00d6\36\3\2\2\2\u00d7\u00d8\7c\2\2\u00d8 \3\2\2\2"+
		"\u00d9\u00da\7d\2\2\u00da\"\3\2\2\2\u00db\u00dc\7e\2\2\u00dc$\3\2\2\2"+
		"\u00dd\u00de\7f\2\2\u00de&\3\2\2\2\u00df\u00e0\7g\2\2\u00e0(\3\2\2\2\u00e1"+
		"\u00e2\7h\2\2\u00e2*\3\2\2\2\u00e3\u00e4\7i\2\2\u00e4,\3\2\2\2\u00e5\u00e6"+
		"\7j\2\2\u00e6.\3\2\2\2\u00e7\u00e8\7k\2\2\u00e8\60\3\2\2\2\u00e9\u00ea"+
		"\7l\2\2\u00ea\62\3\2\2\2\u00eb\u00ec\7m\2\2\u00ec\64\3\2\2\2\u00ed\u00ee"+
		"\7n\2\2\u00ee\66\3\2\2\2\u00ef\u00f0\7o\2\2\u00f08\3\2\2\2\u00f1\u00f2"+
		"\7p\2\2\u00f2:\3\2\2\2\u00f3\u00f4\7q\2\2\u00f4<\3\2\2\2\u00f5\u00f6\7"+
		"r\2\2\u00f6>\3\2\2\2\u00f7\u00f8\7s\2\2\u00f8@\3\2\2\2\u00f9\u00fa\7t"+
		"\2\2\u00faB\3\2\2\2\u00fb\u00fc\7u\2\2\u00fcD\3\2\2\2\u00fd\u00fe\7v\2"+
		"\2\u00feF\3\2\2\2\u00ff\u0100\7w\2\2\u0100H\3\2\2\2\u0101\u0102\7x\2\2"+
		"\u0102J\3\2\2\2\u0103\u0104\7y\2\2\u0104L\3\2\2\2\u0105\u0106\7z\2\2\u0106"+
		"N\3\2\2\2\u0107\u0108\7{\2\2\u0108P\3\2\2\2\u0109\u010a\7|\2\2\u010aR"+
		"\3\2\2\2\u010b\u010c\7C\2\2\u010cT\3\2\2\2\u010d\u010e\7D\2\2\u010eV\3"+
		"\2\2\2\u010f\u0110\7E\2\2\u0110X\3\2\2\2\u0111\u0112\7F\2\2\u0112Z\3\2"+
		"\2\2\u0113\u0114\7G\2\2\u0114\\\3\2\2\2\u0115\u0116\7H\2\2\u0116^\3\2"+
		"\2\2\u0117\u0118\7I\2\2\u0118`\3\2\2\2\u0119\u011a\7J\2\2\u011ab\3\2\2"+
		"\2\u011b\u011c\7K\2\2\u011cd\3\2\2\2\u011d\u011e\7L\2\2\u011ef\3\2\2\2"+
		"\u011f\u0120\7M\2\2\u0120h\3\2\2\2\u0121\u0122\7N\2\2\u0122j\3\2\2\2\u0123"+
		"\u0124\7O\2\2\u0124l\3\2\2\2\u0125\u0126\7P\2\2\u0126n\3\2\2\2\u0127\u0128"+
		"\7Q\2\2\u0128p\3\2\2\2\u0129\u012a\7R\2\2\u012ar\3\2\2\2\u012b\u012c\7"+
		"S\2\2\u012ct\3\2\2\2\u012d\u012e\7T\2\2\u012ev\3\2\2\2\u012f\u0130\7U"+
		"\2\2\u0130x\3\2\2\2\u0131\u0132\7V\2\2\u0132z\3\2\2\2\u0133\u0134\7W\2"+
		"\2\u0134|\3\2\2\2\u0135\u0136\7X\2\2\u0136~\3\2\2\2\u0137\u0138\7Y\2\2"+
		"\u0138\u0080\3\2\2\2\u0139\u013a\7Z\2\2\u013a\u0082\3\2\2\2\u013b\u013c"+
		"\7[\2\2\u013c\u0084\3\2\2\2\u013d\u013e\7\\\2\2\u013e\u0086\3\2\2\2\u013f"+
		"\u0140\7\62\2\2\u0140\u0088\3\2\2\2\u0141\u0142\7\63\2\2\u0142\u008a\3"+
		"\2\2\2\u0143\u0144\7\64\2\2\u0144\u008c\3\2\2\2\u0145\u0146\7\65\2\2\u0146"+
		"\u008e\3\2\2\2\u0147\u0148\7\66\2\2\u0148\u0090\3\2\2\2\u0149\u014a\7"+
		"\67\2\2\u014a\u0092\3\2\2\2\u014b\u014c\78\2\2\u014c\u0094\3\2\2\2\u014d"+
		"\u014e\79\2\2\u014e\u0096\3\2\2\2\u014f\u0150\7:\2\2\u0150\u0098\3\2\2"+
		"\2\u0151\u0152\7;\2\2\u0152\u009a\3\2\2\2\u0153\u0154\7\62\2\2\u0154\u0155"+
		"\7z\2\2\u0155\u009c\3\2\2\2\u0156\u0157\7<\2\2\u0157\u0158\7\61\2\2\u0158"+
		"\u009e\3\2\2\2\u0159\u015a\7\61\2\2\u015a\u00a0\3\2\2\2\u015b\u015c\7"+
		"f\2\2\u015c\u015d\7g\2\2\u015d\u015e\7h\2\2\u015e\u015f\7k\2\2\u015f\u0160"+
		"\7p\2\2\u0160\u0161\7k\2\2\u0161\u0162\7v\2\2\u0162\u0163\7k\2\2\u0163"+
		"\u0164\7q\2\2\u0164\u0165\7p\2\2\u0165\u00a2\3\2\2\2\u0166\u0168\t\2\2"+
		"\2\u0167\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a"+
		"\3\2\2\2\u016a\u016b\3\2\2\2\u016b\u016c\bR\2\2\u016c\u00a4\3\2\2\2\u016d"+
		"\u016f\n\3\2\2\u016e\u016d\3\2\2\2\u016f\u0172\3\2\2\2\u0170\u016e\3\2"+
		"\2\2\u0170\u0171\3\2\2\2\u0171\u00a6\3\2\2\2\u0172\u0170\3\2\2\2\5\2\u0169"+
		"\u0170\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}