package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.WordleAccount;

class WordleAccountTest {

	@Test
	void testGetters() {
		WordleAccount account = new WordleAccount("Robert", "asdf1234");
		assertEquals(account.getUsername(), "Robert");
		assertEquals(account.getPassword(), "asdf1234");
		assertEquals(account.getGamesPlayed(), 0);
		assertEquals(account.getMaxStreak(), 0);
		assertEquals(account.getWinPct(), 0);
		assertEquals(account.getWinRecord().size(), 6);
		assertEquals(account.getWinStreak(), 0);
	}
	
	@Test
	void testAddWin() {
		WordleAccount account = new WordleAccount("Alex", "superMan4");
		account.addWin(5);
		assertEquals(account.getGamesPlayed(), 1);
		assertEquals(account.getMaxStreak(), 1);
		assertEquals(account.getWinStreak(), 1);
		assertEquals(account.getWinRecord().get(5), 1);
		
		account.addWin(5);
		assertEquals(account.getGamesPlayed(), 2);
		assertEquals(account.getMaxStreak(), 2);
		assertEquals(account.getWinStreak(), 2);
		assertEquals(account.getWinRecord().get(5), 2);
		
		account.addWin(3);
		assertEquals(account.getGamesPlayed(), 3);
		assertEquals(account.getMaxStreak(), 3);
		assertEquals(account.getWinStreak(), 3);
		assertEquals(account.getWinRecord().get(5), 2);
		assertEquals(account.getWinRecord().get(3), 1);
		assertEquals(account.getWinPct(), 100);
		
		account.addLoss();
		assertEquals(account.getGamesPlayed(), 4);
		assertEquals(account.getMaxStreak(), 3);
		assertEquals(account.getWinStreak(), 0);
		assertEquals(account.getWinRecord().get(5), 2);
		assertEquals(account.getWinRecord().get(3), 1);
		assertEquals(account.getWinRecord().get(1), 0);
		assertEquals(account.getWinPct(), 75);
		
		account.addWin(2);
		account.addWin(2);
		account.addWin(5);
		account.addWin(3);
		account.addWin(6);
		account.addWin(4);
		
		assertEquals(account.getGamesPlayed(), 10);
		assertEquals(account.getMaxStreak(), 6);
		assertEquals(account.getWinRecord().get(2), 2);
		assertEquals(account.getWinRecord().get(5), 3);
		assertEquals(account.getWinRecord().get(6), 1);
		assertEquals(account.getWinPct(), 90);
		
		account.addLoss();
		account.addWin(3);
		account.addWin(4);
		account.addWin(5);
		account.addWin(2);
		
		assertEquals(account.getWinStreak(), 4);
		
		account.addLoss();
		
		assertEquals(account.getWinStreak(), 0);
		assertEquals(account.getMaxStreak(), 6);
		assertEquals(account.getWinPct(), 81);
	}

}
