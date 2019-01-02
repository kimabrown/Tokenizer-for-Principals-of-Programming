package FinalTokenizer;

public final class Constants {

	public final static String[] SYMBOLS = new String[] {"(",")","[","]","{","}",",",";","=",".","+","-","*","/","&","|","~","<",">","\""};

	public final static String[] KEY_WORDS = new String[]{
			"method", "int", "class", "boolean", "constructor", "function",
			"else", "do", "if", "static", "field", "let", "void", "var", "char", 
			"null", "return", "this", "false", "while", "true"};

	public final static String REGEX_FIND_QUOTES = "\"([^\"]*)\"";
	public final static String REGEX_FIND_WHITESPACE = "\\s+";
	public final static String REGEX_FIND_SYMBOL_GROUPS = "([.,!?() {};])";

	public final static String KEY_WORD = "keyword";
	public final static String IDENTIFIER = "identifier";
	public final static String SYMBOL = "symbol";
	public final static String STRING_CONSTANT = "stringConstant";
	public final static String INTEGER_CONSTANT = "integerConstant";
}