package model;

/**
 * WordleBoard allows for one game of Wordle to be played. It must be passed
 * a five letter string, which will be the word that needs to be guessed. It checks if
 * the game is over and has been won, and allows for guesses to be made in the form of
 * five letter strings, which will return an array of strings, representing the results
 * for each letter.
 * 
 * @author Colton Patch
 * 
 */
public class WordleBoard {
	private int guessCount;
	private WordleWord wordToGuess;
	private boolean gameOver;
	private boolean won;
	
	/**
	 * WordleBoard needs to be passed a five letter String when a new game
	 * starts, which will be the word to guess.
	 * @param wordToGuess - the word that will be guessed this game.
	 */
	public WordleBoard(String wordToGuess) {
		this.wordToGuess = new WordleWord(wordToGuess);
		guessCount = 0;
		gameOver = false;
		won = false;
	}
	
	/**
	 * Checks if the game is over
	 * @return true if the game is over, false if it is still going.
	 */
	public boolean gameOver() {
		if (guessCount >= 6 || won) {
			gameOver = true;
		}
		return gameOver;
	}
	
	/**
	 * Checks if the game has been won by making a correct guess.
	 * @return true if the game has been won, false if not.
	 */
	public boolean won() {
		return won;
	}
	
	/**
	 * Makes one guess if the game is still running and returns the results.
	 * The results will be in the form of an array of five Strings. Each string
	 * corresponds to the five letters in the guess and the results for each.
	 * If the word is correctly guessed, won will be set to true and the game will
	 * be over.
	 * 
	 * "correct": the letter is in the correct spot.
	 * "wrong space": the letter is in the word, but it was not guessed in the right spot.
	 * "incorrect": the letter is not in the word.
	 * 
	 * @param guess -  the string of the five letter word that is guessed
	 * @return the results array.
	 */
	public String[] makeGuess(String guess) {
		String results[] = new String[5];
		
		if (!gameOver) {
			guessCount += 1;
			WordleWord guessWord = new WordleWord(guess);
			results = guessWord.compare(wordToGuess);
			won = checkForWin(results);
		}

		return results;
	}
	
	/**
	 * Returns the correct word that is being guessed.
	 * @return the String of the correct word.
	 */
	public String getCorrectWord() {
		return wordToGuess.toString();
	}
	
	/**
	 * Checks if the win state has been reached.
	 * 
	 * @param results - the results to be checked
	 * @return true if a winning guess is made, false if not.
	 */
	private boolean checkForWin(String[] results) {
		for (String result : results) {
			if (!result.equals("correct")) {
				return false;
			}
		}
		return true;
	}
	
}
