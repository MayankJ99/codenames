package Tests;

import Code.Board;
import Code.Location;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BoardTest {
	// Test to check the method codeNamesFileReader.
	@Test
	public void CodeNamesListTest() {
		Board realBoard = new Board();
		assertEquals(50, realBoard.getAllWords().size());
		assertEquals("area", realBoard.getAllWords().get(0));
		assertEquals("year", realBoard.getAllWords().get(49));
		
		Board emptyBoard = new Board("Dictionaries/emptyFile.txt");
		assertTrue(emptyBoard.getAllWords().isEmpty());
		
		Board nullBoard = new Board("invalidFilename");
		assertNull(nullBoard.getAllWords());
	}

	// Test to check the method select25.
	@Test
	public void Check_Select25() {
		String file = "Dictionaries/GameWords.txt";
		
		Board x = new Board(file);
		x.select25();
		
		Assert.assertEquals(25,x.getCodenames().size(),0);
		Assert.assertNotNull(x.getCodenames());
		
		Assert.assertTrue(x.getAllWords().containsAll(x.getCodenames()));
		
		for(int i = 0; i < x.getCodenames().size();i++) {
			for(int j = 0; j< x.getCodenames().size();j++) {
				if(i!=j) {
					Assert.assertNotEquals(x.getCodenames().get(i), x.getCodenames().get(j));
				}
			}
		}
	}
	
	// Test to check the method randomAssign();
	@Test
	public void Check_RandomAssign() {
		Board board = new Board();
		board.randomAssign();
		ArrayList<String> persons = board.getPersons();
		String testString = new String();
		
		for (int a = 0; a < persons.size(); a++) {
			testString += persons.get(a);
		}
		
		assertTrue(testString.matches("(.*R.*){9}"));
		assertTrue(testString.matches("(.*B.*){8}"));
		assertTrue(testString.matches("(.*I.*){7}"));
		assertTrue(testString.matches("(.*A.*){1}"));
		
		assertEquals(25, testString.length());
		
		assertNotEquals(testString, "RRRRRRRRRBBBBBBBBIIIIIIIA");
	}
	
	// Test to check the method gameStart
	@Test
	public void Check_GameStart() {
		Board board = new Board();
		board.gameStart();

		boolean sameNames = true;
		boolean samePersons = true;
		boolean notRevealed = true;
		
		ArrayList<Location> locations = board.getLocations();
		
		assertEquals(25, locations.size());
		
		for (int a = 0; a < 25; a++) {
			if (!locations.get(a).getCodename().equals(board.getCodenames().get(a)))
				sameNames = false;
			if (!locations.get(a).getPerson().equals(board.getPersons().get(a)))
				samePersons = false;
			if (locations.get(a).getRevealed())
				notRevealed = false;
		}
		
		assertTrue(sameNames);
		assertTrue(samePersons);
		assertTrue(notRevealed);
		
		assertTrue(board.getRedTurn());
		assertEquals(9, board.getRedCount());
		assertEquals(8, board.getBlueCount());
		assertEquals(1, board.getAssassinCount()); 
	}
	
	// Test to check the method checkClue
	@Test
	public void Check_CheckClue() {
		Board x = new Board();
		x.gameStart();
		
		String legal = "Knox 104";
		String illegal = x.getLocations().get(5).getCodename();
		String nothing = null;
		String empty = "";
		
		Assert.assertTrue(x.checkClue(legal));
		Assert.assertTrue(x.checkClue(nothing));	
		Assert.assertTrue(x.checkClue(empty));	
		//Trick Test
	
		Assert.assertFalse(x.checkClue(illegal));
		x.updateLocation(illegal);
		Assert.assertTrue(x.checkClue(illegal));
	}
	
	// Test to check the method updateLocation(String)
	@Test
	public void Check_UpdateLocationString() {
		Board board = new Board();
		board.gameStart();
		board.setCount(2);
		
		Location location = board.getLocations().get(0);
		String codeName = location.getCodename();
		location.setPerson("R");
		
		assertTrue(board.updateLocation(codeName));
		assertEquals(1, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(8, board.getRedCount());
		
		board.setRedTurn(false);
		location = board.getLocations().get(1);
		codeName = location.getCodename();
		location.setPerson("R");
		assertFalse(board.updateLocation(codeName));
		assertEquals(0, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(7, board.getRedCount());
		
		location = board.getLocations().get(2);
		codeName = location.getCodename();
		location.setPerson("A");
		assertFalse(board.updateLocation(codeName));
		assertEquals(-1, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(0, board.getAssassinCount());
		
		board.gameStart();
		
		for (Location item : board.getLocations())
			board.updateLocation(item.getCodename());
		assertEquals(0, board.getRedCount());
		assertEquals(0, board.getBlueCount());
		assertEquals(0, board.getAssassinCount());
	}
	
	// Test to check the method checkGameState
	@Test
	public void Check_CheckGameState() {
		Board TrueREDBoard = new Board();
		Board TrueBLUEBoard = new Board();
		Board ASSASSINBoard = new Board();
		
		TrueREDBoard.gameStart();
		TrueBLUEBoard.gameStart();
		ASSASSINBoard.gameStart();
		
		assertFalse(TrueREDBoard.checkGameState());
		assertFalse(TrueBLUEBoard.checkGameState());
		assertFalse(ASSASSINBoard.checkGameState());
		
		TrueREDBoard.setRedCount(0);
		TrueBLUEBoard.setBlueCount(0);
		ASSASSINBoard.setAssassinCount(0);

		assertTrue(TrueREDBoard.checkGameState());
		assertTrue(TrueBLUEBoard.checkGameState());
		assertTrue(ASSASSINBoard.checkGameState());
	}
	
	// Test to check the method winTeam
	@Test
	public void Check_WinTeam() {
		Board x = new Board();
		String R = "Red Team Won";
		String B = "Blue Team Won";
		String N = "No Team Won yet";
		
		x.gameStart();
		
		Assert.assertEquals(N, x.winTeam());
		
		x.setAssassinCount(0);
		Assert.assertEquals(B, x.winTeam());
		
		x.setRedTurn(false);
		Assert.assertEquals(R, x.winTeam());
	}
}
