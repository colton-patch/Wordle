package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import model.WordleAccount;
import model.WordleGame;

class WordleGameTest {

	String string1 = "Place";
	String string2 = "Space";
	
	@Test
	void testCorrectInThree() {
		WordleGame game = new WordleGame(string1);
		String guess1 = "SLICE";
		String guess2 = "prICe";
		String guess3 = "place";
		game.guessWord(guess1);
		assertEquals(game.isOver(), false);
		game.guessWord(guess2);
		assertEquals(game.isOver(), false);
		game.guessWord(guess3);
		assertEquals(game.isOver(), true);
		assertEquals(game.getNumGuesses(), 3);
	}
	
	@Test
	void testAllIncorrect() {
		WordleGame game = new WordleGame(string2);
		game.guessWord("OCEAN");
		game.guessWord("OCEAN");
		assertEquals(game.getNumGuesses(), 2);
		game.guessWord("OCEAN");
		assertEquals(game.isOver(), false);
		game.guessWord("OCEAN");
		game.guessWord("OCEAN");
		game.guessWord("OCEAN");
		assertEquals(game.isOver(), true);
		assertEquals(game.getNumGuesses(), 6);
	}
	
	@Test
	void testWordList() throws FileNotFoundException {
		WordleGame game = new WordleGame();
		game.guessWord("hello");
		game.guessWord("track");
		game.guessWord("tracktrack");
		assertEquals(game.getNumGuesses(), 2);
		assertEquals(game.isOver(), true);
	}
	
	@Test
	void testRunGame() throws FileNotFoundException {
		WordleGame game = new WordleGame();
		
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("track".getBytes());
		System.setIn(in);

		// game.runGame();
		// game.runGame();
		assertEquals(game.isOver(), true);

		// reset System.in to its original
		System.setIn(sysInBackup);
	}
	
	@Test
	void testRunGameInvalidGuess() throws FileNotFoundException {
		WordleGame game = new WordleGame();
		
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("tracktrack\r\ntrack\r\n".getBytes());
		System.setIn(in);

		// game.runGame();
		
		assertEquals(game.isOver(), true);

		// reset System.in to its original
		System.setIn(sysInBackup);
	}
	
	@Test 
	void testCreateAccount() throws FileNotFoundException {
		WordleGame game = new WordleGame();
		game.createAccount("ryan", "password");
		assertEquals(game.getAccountList().size(), 1);
		game.createAccount("ryan", "a");
		assertEquals(game.getAccountList().size(), 1);
		game.createAccount("d", "password");
		assertEquals(game.getAccountList().size(), 2);
	}
	
	@Test
	void testLoginLogout() throws FileNotFoundException {
		WordleAccount account = new WordleAccount("a", "a");
		WordleGame game = new WordleGame();
		game.createAccount(account);
		game.login("a", "a");
		assertEquals(game.getCurUser(), account);
		game.logout();
		assertEquals(game.getCurUser(), null);
		game.login("c", "password");
		assertEquals(game.getCurUser(), null);
		WordleAccount account2 = new WordleAccount("a", "b");
		game.createAccount(account2);
		assertEquals(game.getAccountList().size(), 1);
		game.login("a", "b");
		assertEquals(game.getCurUser(), null);
		WordleAccount account3 = new WordleAccount("b", "password");
		game.createAccount(account3);
		game.login("b", "password");
		assertEquals(game.getCurUser(), account3);
		game.logout();
		assertEquals(game.getCurUser(), null);
		assertEquals(game.login("b", "b"), false);
	}

}