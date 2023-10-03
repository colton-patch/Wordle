package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An account that a user can login to to play Wordle. it keeps
 * track of the user's statistics. The constructor is passed
 * the new account's username and password, which are used to
 * login.
 */
public class WordleAccount implements Serializable  {
	
	private String username;
	private String password;
	private Map<Integer, Integer> winRecord;
	private int winStreak;
	private int maxStreak;
	private int gamesPlayed;
	private int gamesWon;
	
	public WordleAccount(String username, String password) {
		this.username = username;
		this.password = password;
		winRecord = new HashMap<Integer, Integer>();
		for (int i = 1; i < 7; i ++) {
			winRecord.put(i, 0);
		}
		winStreak = 0;
		maxStreak = 0;
		gamesPlayed = 0;
	}
	
	/**
	 * Adds a win to the account's statistics.
	 * 
	 * @param numAttempts - the number of guesses it took to get this win
	 */
	public void addWin(int numAttempts) {
		gamesWon += 1;
		winStreak += 1;
		gamesPlayed += 1;
		if (winStreak > maxStreak) {
			maxStreak = winStreak;
		}
		winRecord.put(numAttempts, winRecord.get(numAttempts) + 1);
	}
	
	/**
	 * Adds a loss to the account's statistics.
	 */
	public void addLoss() {
		winStreak = 0;
		gamesPlayed += 1;
	}
	
	/**
	 * Gets this account's password
	 * 
	 * @return - the account's password in the form of a string
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Gets this account's username
	 * 
	 * @return - the account's username in the form of a string
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Returns a record of the account's wins, including how many attempts each win took.
	 * 
	 * @return - A map, with the keys being the number of guesses, and the values being the number of
	 * 			wins achieved with that many guesses.
	 */
	public Map<Integer, Integer> getWinRecord() {
		return this.winRecord;
	}
	
	/**
	 * Returns the account's score.
	 * 
	 * @return the account's score, which is based off of how many wins it has and how many guesses
	 * 			it took to get each win.
	 */
	public int getScore() {
		int score = 0;
		score = winRecord.get(1) * 6 + winRecord.get(2) * 5 + winRecord.get(3) * 4 + winRecord.get(4) * 3 + winRecord.get(5) * 2 + winRecord.get(6) * 1;
		return score;
	}
	
	/**
	 * Returns the account's current win streak.
	 * 
	 * @return - the number of games most recently won in a row without a loss
	 */
	public int getWinStreak() {
		return winStreak;
	}
	
	/**
	 * Returns the account's maximum win streak.
	 * 
	 * @return - the highest number of games ever won in a row.
	 */
	public int getMaxStreak() {
		return maxStreak;
	}
	
	/**
	 * Returns the amount of games played.
	 * 
	 * @return the number of games the account has played.
	 */
	public int getGamesPlayed() {
		if (gamesPlayed == 0) {
			return 0;
		}
		return gamesPlayed;
	}
	
	/**
	 * Returns the percentage of games that the account has won.
	 * 
	 * @return the percentage of games that have been won.
	 */
	public float getWinPct() {
		if (gamesPlayed == 0) {
			return 0;
		}
		int winPct = (gamesWon * 100)/gamesPlayed;
		return winPct;
	}
	
}
