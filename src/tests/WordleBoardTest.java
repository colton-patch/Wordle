package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.WordleBoard;

public class WordleBoardTest {
	@Test
	void testGetCorrectWord() {
		WordleBoard game = new WordleBoard("track");
		assertEquals(game.getCorrectWord(), "TRACK");
	}
}
