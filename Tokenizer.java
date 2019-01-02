package FinalTokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

	public static void processString(String line) {
		List<String> stringConstants = getAllMatches(line,Constants.REGEX_FIND_QUOTES);
		line = line.replaceAll(Constants.REGEX_FIND_QUOTES, " \" ");

		String[] words = line.split(Constants.REGEX_FIND_WHITESPACE);


		// Crate a List from SYMBOLS so that we can search easier
		List<String> symbols = Arrays.asList(Constants.SYMBOLS);


		for(String word: words) {
			if (word.contains("//")) break;

			else if ( Arrays.asList(Constants.KEY_WORDS).contains(word) ) { // if the word parsed is a key word
				printTokenizedItem(Constants.KEY_WORD, word);
			}
			else if (word.contains(".")) { // if there is a . in the parsed word. This part can contain anything. There's nothing specific

				// Parse every possible symbol and replace it with itself with whitespace before and after it.
				word = word.replaceAll(Constants.REGEX_FIND_SYMBOL_GROUPS, " $1 "); 
				String[] parsedWords = word.split(Constants.REGEX_FIND_WHITESPACE);


				for(String pw : parsedWords) {
					pw = pw.trim();
					if ( !pw.equals(".") && !pw.equals("") && !symbols.contains(pw)) {
						printTokenizedItem(Constants.IDENTIFIER, pw);
					}
					else if (!pw.equals("") && symbols.contains(pw)) {
						printTokenizedItem(Constants.SYMBOL, pw);
					}
				}
			}
			else if (word.contains("\"")) {
				if(!stringConstants.isEmpty()) {
					printTokenizedItem(Constants.STRING_CONSTANT, stringConstants.remove(0));
				}
			}

			else {
				List<String> toBePrinted = new ArrayList<>();
				boolean keepReadingDigit = false;
				boolean keepReadingWord= false;

				for(Character c : word.toCharArray()) {

					if (Character.isDigit(c)) {
						keepReadingWord = false;
						if (keepReadingDigit) {
							final int last = toBePrinted.size()-1;
							toBePrinted.set(last, toBePrinted.get(last)+c);
						}else {
							toBePrinted.add(Constants.INTEGER_CONSTANT+">");
							toBePrinted.add(String.valueOf(c));
							keepReadingDigit = true;
						}
					}
					else if (symbols.contains(String.valueOf(c))) {
						keepReadingDigit = keepReadingWord = false;
						toBePrinted.add(Constants.SYMBOL+">");
						toBePrinted.add(String.valueOf(c));
					}
					else if (!symbols.contains(String.valueOf(c))) {
						keepReadingDigit = false;
						if (keepReadingWord) {
							final int last = toBePrinted.size()-1;
							toBePrinted.set(last, toBePrinted.get(last)+c);
						}else {
							toBePrinted.add(Constants.IDENTIFIER+">");
							toBePrinted.add(String.valueOf(c));
							keepReadingWord = true;
						}
					}
				}

				for (int i = 0 ; i < toBePrinted.size() ; i+=2) {
					System.out.println("<"+toBePrinted.get(i)+toBePrinted.get(i+1)+"</"+toBePrinted.get(i));
				}
			}	
		}
	}

	public static void main(String[] args) {

		final String fileName = "sample/Square/SquareGame.jack";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			System.out.println("<tokens>");
			while ((line = br.readLine()) != null) {
				processString(line);
			}
			System.out.println("</tokens>");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// I found this function from stackoverflow
	public static List<String> getAllMatches(String text, String regex) {
		List<String> matches = new ArrayList<String>();
		Matcher m = Pattern.compile(regex).matcher(text);
		while(m.find()) {
			matches.add(m.group(1));
		}
		return matches;
	}

	public static void printTokenizedItem(String tokenName, String value) {
		System.out.printf("<%s>%s</%s>\n",tokenName,value,tokenName);
	}
}