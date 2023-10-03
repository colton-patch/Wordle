package model;

import java.util.HashMap;

/**
 * 
 * An instance of WordleWord contains a five letter word which can be compared to another
 * five letter word. When comparing using compare(WordleWord), it returns an array of strings
 * corresponding to each letter, in which each string reveals how the letter compares to the
 * letters in the word being compared to.
 * 
 * For example, if the word was "space" and it was being compared to "sleep", it would return
 * {"correct",
 *  "wrong space",
 *  "incorrect",
 *  "incorrect",
 *  "wrong space",}
 *  
 *  This is because the s is in the right space, the p and e are in the other word but not the right space,
 *  and the a and c are not in the other word.
 * 
 * @author Colton Patch
 */
public class WordleWord {
	private char[] letters;
	private String word;
	private HashMap<Character, Integer> occurences;

	/**
	 * It must be passed a five letter word when constructing.
	 * 
	 * @param newWord - a five letter word.
	 */
	public WordleWord(String newWord) {
		assert(newWord.length() == 5);
		
		String newWordUpper = newWord.toUpperCase();
		letters = newWordUpper.toCharArray();
		word = newWordUpper;
	}
	
	/**
	 * Compares this WordleWord to the other WordleWord passed as a parameter.
	 * It returns an array of Strings representing the correctness of each 
	 * 
	 * @param otherWord - another WordleWord to compare this one to.
	 * @return An array of 5 Strings, one for each letter. 
	 * 		   	The string will be "correct" if the right letter is in the right spot
	 * 			The string will be "wrong space" if the letter is in the other word, but not in the right spot.
	 *			The string will be "incorrect" if the letter is not in the other word.
	 */
	public String[] compare(WordleWord otherWord) {
		initializeOccurences(otherWord);
		String return_array[] = new String[5];
		char otherLetters[] = otherWord.getLetters();
		
		for (int i = 0; i < 5; i++) {
			if (letters[i] == otherLetters[i]) {
				return_array[i] = "correct";
			} else {
				return_array[i] = checkIfInWord(otherLetters, i, return_array);
			} 
		}
		
		return return_array;
	}
	
	/**
	 * @return The word as a String.
	 */
	public String toString() {
		return word;
	}
	
	/**
	 * @return the word as an array of chars.
	 */
	public char[] getLetters() {
		return letters;
	}
	
	/**
	 * Checks if the letter at the given index is in the word being compared to.
	 * If it is, returns "wrong space." if it is not, returns "incorrect"
	 * @param otherLetters
	 * @param i
	 * @param return_array
	 * @return "wrong space" or "correct"
	 */
	private String checkIfInWord(char[] otherLetters, int i, String[] return_array) {
		for (int j = 0; j < 5; j++) {
			char letter = letters[i];
			if (letter == otherLetters[j]) {
				if (return_array[j] != "correct") {
					int curCount = occurences.get(letter);
					if (curCount > 0) {
						occurences.replace(letter, curCount - 1);
						return "wrong space";
					}
				}
			}
		}
		return "incorrect";
	}
	
	private void initializeOccurences(WordleWord otherWord) {
		occurences = new HashMap<Character, Integer>();
		for (Character letter : otherWord.getLetters()) {
			occurences.putIfAbsent(letter, 0);
			int curCount = occurences.get(letter);
			occurences.replace(letter, curCount + 1);
		}
	}
}
