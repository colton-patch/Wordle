package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.WordleWord;

class WordleWordTest {

	WordleWord space = new WordleWord("space");
	WordleWord sleep = new WordleWord("sleeP");
	WordleWord space2 = new WordleWord("SPACE");
	WordleWord scosc = new WordleWord("scosc");
	
	char spaceLetters[] = {'S', 'P', 'A', 'C', 'E'};
	char sleepLetters[] = {'S', 'L', 'E', 'E', 'P'};
	
	String sleepCompare[] = {"correct", "wrong space", "incorrect", "incorrect", "wrong space"};
	String spaceCompare[] = {"correct", "correct", "correct", "correct", "correct"};
	String scoscCompare[] = {"correct", "wrong space", "incorrect", "incorrect", "incorrect"};
	
	@Test
	void testWordGetters() {
		String spaceString = space.toString();
		char spaceArr[] = space.getLetters();
		String sleepString = sleep.toString();
		char sleepArr[] = sleep.getLetters();
		String space2String = space2.toString();
		char space2Arr[] = space2.getLetters();
		
		assertTrue(spaceString.equals("SPACE"));
		assertTrue(sleepString.equals("SLEEP"));
		for (int i = 0; i < 5; i++ ) {
			assertEquals(spaceArr[i], spaceLetters[i]);
			assertEquals(sleepArr[i], sleepLetters[i]);
			assertEquals(space2Arr[i], spaceLetters[i]);
		}
	}
	
	@Test
	void testWordCompare() {
		String spaceCompareArr[] = space.compare(space2);
		String sleepCompareArr[] = space.compare(sleep);
		String scoscCompareArr[] = scosc.compare(space);
		for (int i = 0; i < 5; i++ ) {
			System.out.println(scoscCompareArr[i]);
			assertTrue(spaceCompareArr[i].equals(spaceCompare[i]));
			assertTrue(sleepCompareArr[i].equals(sleepCompare[i]));
			assertTrue(scoscCompareArr[i].equals(scoscCompare[i]));
			
		}
	}

	@Test
	void testNotFiveLetters() {
		assertThrows(AssertionError.class, () -> {
			System.out.println("test");
	        WordleWord fail = new WordleWord("fail");
	        fail.toString();
	    });
	}
}
