package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Coordinates a game of wordle, keeping track of guesses, accounts, and whether or not the game is over.
 */
public class WordleGame {

	private WordleBoard board;
	private String wordToFind;
	private String fileName = "WordList.txt";
	private ArrayList<String> wordList;
	private boolean isOver;
	private int numGuesses;
	private boolean won;
	private WordleAccount curUser;
	private ArrayList<WordleAccount> accountList;

	/**
	 * Gets a random five letter word from WordList.txt and creates board as a new
	 * WordleBoard using that random word.
	 * 
	 * @throws FileNotFoundException
	 */
	public WordleGame() throws FileNotFoundException {

		isOver = false;
		numGuesses = 0;
		wordList = new ArrayList<String>();

		Scanner fileScanner = new Scanner(new File(fileName));
		while (fileScanner.hasNextLine()) {
			wordList.add(fileScanner.nextLine());
		}
		int fileLength = wordList.size();
		int random = (int) (Math.random() * fileLength);
		wordToFind = wordList.get(random);
		// System.out.println(wordToFind);
		fileScanner.close();

		board = new WordleBoard(wordToFind);
		curUser = null;
		loadAccounts();
	}

	/**
	 * Creates a board as a new WordleBoard using the specified word as the string
	 * to find
	 * 
	 * @param word - The word to be used as the specific word to be found
	 */
	public WordleGame(String word) {
		isOver = false;
		this.wordToFind = word;
		board = new WordleBoard(wordToFind);
		curUser = null;
	}
	

	/**
	 * Takes user input and turns it into a string to be guessed
	 * 
	 * @return
	 */
	private String getGuess() {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.println("Make Guess: ");
			String guess = input.nextLine();
			if (guess.length() == 5) {
				return guess;
			} else {
				System.out.println("Make valid 5 letter guess");
			}
		}
	}

	/**
	 * Guesses a specific word
	 * 
	 * @param guess - Word to be guessed
	 */
	public String[] guessWord(String guess) {
		if (guess.length() == 5) {
			if (!guess.contains(" ")) {
				String[] results = board.makeGuess(guess);
				numGuesses += 1;
				isOver = board.gameOver();
				return results;
			}
		} else {
			System.out.println("Invalid Word");
		}
		return null;
	}

	/**
	 * Given a two strings, a username and a password, determines if the user exists
	 * and if the correct password has been entered for the corresponding username.
	 * If both are valid, curUser is set as the given user's WordleAccount
	 * 
	 * @param username - the String entered as the user's username
	 * @param password - the String entered as the user's password
	 * @return - true if the username and password have been successfully entered
	 *         false otherwise
	 */
	public boolean login(String username, String password) {
		for (WordleAccount a : accountList) {
			if (a.getUsername().equals(username)) {
				if (a.getPassword().equals(password)) {
					curUser = a;
					// Username exists and password is correct
					return true;
				}
				// Username exists but password is incorrect
			}
		}
		// Username does not exist
		return false;
	}
	
	public void setCurUser(String username) {
		for (WordleAccount a : accountList) {
			if (a.getUsername().equals(username)) {
				curUser = a;
			}
		}
	}

	/**
	 * Sets the current user to null so that no WordleAccount is logged in (set as
	 * curUser)
	 */
	public void logout() {
		curUser = null;
	}

	/**
	 * Creates an account given a username and password
	 * 
	 * @param username - The intended username for the account to be created
	 * @param password - The intended password for the account to be created
	 * @return - True if the intended username is avaliable, false if it is not
	 */
	public boolean createAccount(String username, String password) {
		for (WordleAccount a : accountList) {
			if (a.getUsername().equals(username)) {
				// Username already exists
				return false;
			}
		}
		// Username does not exist and new WordleAccount is created and added to
		// accountList
		accountList.add(new WordleAccount(username, password));
		saveAccounts();
		return true;
	}

	/**
	 * Adds a given account to the game
	 * 
	 * @param account - the account to be added
	 * @return a boolean, whether or not the account can be added.
	 */
	public boolean createAccount(WordleAccount account) {
		for (WordleAccount a : accountList) {
			if (account.getUsername().equals(a.getUsername())) {
				return false;
			}
		}
		accountList.add(account);
		saveAccounts();
		return true;
	}

	/**
	 * Returns whether the game is over or not
	 * 
	 * @return - true if game is over (Win state has been reached or max guesses
	 *         have been used), false if game is still going
	 */
	public boolean isOver() {
		return isOver;
	}

	/**
	 * Returns whether the game is won or not
	 * 
	 * @return - true if the game has been won (the user has guessed the correct
	 *         word), false if the user has failed to guess the correct word.
	 */
	public boolean won() {
		return board.won();
	}

	/**
	 * Returns the word to be guessed
	 * 
	 * @return - a String, the correct word for this game.
	 */
	public String getCorrectWord() {
		return wordToFind;
	}

	/**
	 * Returns the amount of guesses that have been made in the current game
	 * 
	 * @return an int, the number of guesses
	 */
	public int getNumGuesses() {
		return numGuesses;
	}

	/**
	 * Returns the WordleAccount of the current user
	 * 
	 * @return - the WordleAccount of the current user, void if no user is logged in
	 */
	public WordleAccount getCurUser() {
		return curUser;
	}

	/**
	 * Returns the list of accounts in the game
	 * 
	 * @return an ArrayList of all the accounts
	 */
	public ArrayList<WordleAccount> getAccountList() {
		return this.accountList;
	}

	/**
	 * Writes the list of accounts to a .ser file
	 */
	public void saveAccounts() {
		try {
			FileOutputStream fos = new FileOutputStream("accounts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accountList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * reads the list of accounts from a .ser file and loads them into the game.
	 */
	public void loadAccounts() {
		try {
			FileInputStream fis = new FileInputStream("accounts.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			accountList = (ArrayList<WordleAccount>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			accountList = new ArrayList<WordleAccount>();
		}
	}

}