package Tests;

import Code.Board;
import Code.Entry;
import Code.Location;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BoardTest {
	// Test to check the method codeNamesFileReader.
	@Test
	public void Check_CodeNamesFileReader() {
		Board realBoard = new Board();
		assertEquals(400, realBoard.getAllWords().size());
		assertEquals("AFRICA", realBoard.getAllWords().get(0));
		assertEquals("YARD", realBoard.getAllWords().get(399));
		
		Board emptyBoard = new Board("Dictionaries/emptyFile.txt");
		assertTrue(emptyBoard.getAllWords().isEmpty());
		
		Board nullBoard = new Board("invalidFilename");
		assertNull(nullBoard.getAllWords());
	}

	// Test to check the method select25.
	@Test
	public void Check_Select25() {
		Board x = new Board();
		x.select25();
		
		Assert.assertEquals(25,x.getCodenames().size(),0);
		
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
		board.randomAssign(2);
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
		board.gameStart_2Team();

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
		x.gameStart_2Team();
		
		String legal = "Knox 104";
		String illegal = x.getLocations().get(5).getCodename();
		String upperCase = x.getLocations().get(5).getCodename().toUpperCase();
		String nothing = null;
		String empty = "";
		
		Assert.assertTrue(x.checkClue(legal));
		Assert.assertTrue(x.checkClue(nothing));	
		Assert.assertTrue(x.checkClue(empty));
		//Trick Test
		Assert.assertFalse(x.checkClue(upperCase));
		Assert.assertFalse(x.checkClue(illegal));
		x.updateLocation(illegal);
		Assert.assertTrue(x.checkClue(illegal));
	}
	
	// Test to check the method updateLocation(String)
	@Test
	public void Check_UpdateLocation() {
		Board board = new Board();
		board.gameStart_2Team();
		board.setCount(4);
		
		Location location = board.getLocations().get(0);
		String codeName = location.getCodename();
		location.setPerson("R");
		
		assertTrue(board.updateLocation(codeName)); //team correct check and method call
		assertEquals(3, board.getCount()); //clue decrement
		assertTrue(location.getRevealed()); //checks for reveal status update ; field check
		assertEquals(8, board.getRedCount()); //field check for team count
		
		board.setTurn("B");
		
		location = board.getLocations().get(1);
		codeName = location.getCodename();
		location.setPerson("R");
		
		assertFalse(board.updateLocation(codeName));
		assertEquals(2, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(7, board.getRedCount());
		
		location = board.getLocations().get(2);
		codeName = location.getCodename();
		location.setPerson("B");
		
		assertTrue(board.updateLocation(codeName));
		assertEquals(1, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(7, board.getBlueCount());
		
		board.setTurn("R");
		location = board.getLocations().get(3);
		codeName = location.getCodename();
		location.setPerson("B");
		
		assertFalse(board.updateLocation(codeName));
		assertEquals(0, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(6, board.getBlueCount());
		
		location = board.getLocations().get(4);
		codeName = location.getCodename();
		location.setPerson("A");
		
		assertFalse(board.updateLocation(codeName));
		assertEquals(-1, board.getCount());
		assertTrue(location.getRevealed());
		assertEquals(0, board.getAssassinCount());
		
		board.gameStart_2Team();
		
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
		
		TrueREDBoard.gameStart_2Team();
		TrueBLUEBoard.gameStart_2Team();
		ASSASSINBoard.gameStart_2Team();
		
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
	public void Check_WinTeam2Team() {
		Board x = new Board();
		String R = "Red Team Won";
		String B = "Blue Team Won";
		String N = "No Team Won yet";
		
		x.gameStart_2Team();
		
		Location location = x.getLocations().get(0);
		String codename = location.getCodename();
		location.setPerson("A");
		
		Assert.assertEquals(N, x.winTeam());
		
		x.buttonListnerEvent(codename);
		assertEquals(B, x.winTeam());
		
		x.gameStart_2Team();
		
		location = x.getLocations().get(0);
		codename = location.getCodename();
		location.setPerson("A");
		x.setTurn("B");
		
		Assert.assertEquals(N, x.winTeam());
		
		x.buttonListnerEvent(codename);
		assertEquals(R, x.winTeam());
	}

	@Test
	public void Check_WinTeam3Team() {
		Board x = new Board();
		String R = "Red Team Won";
		String B = "Blue Team Won";
		String G = "Green Team Won";
		String N = "No Team Won yet";
		
		x.gameStart_3Team();
		
		assertEquals(N, x.winTeam());
		
		Location location = x.getLocations().get(0);
		String codename = location.getCodename();
		location.setPerson("A");
		
		x.buttonListnerEvent(codename);
		assertEquals(N, x.winTeam());
		
		location.setRevealed(false);
		x.buttonListnerEvent(codename);
		assertEquals(G, x.winTeam());
		
		x.gameStart_3Team();
		assertEquals(N, x.winTeam());
		
		location = x.getLocations().get(0);
		codename = location.getCodename();
		location.setPerson("A");
		x.setTurn("B");
		
		x.buttonListnerEvent(codename);
		assertEquals(N, x.winTeam());
		
		location.setRevealed(false);
		x.buttonListnerEvent(codename);
		assertEquals(R, x.winTeam());
		
		x.gameStart_3Team();
		assertEquals(N, x.winTeam());
		
		location = x.getLocations().get(0);
		codename = location.getCodename();
		location.setPerson("A");
		x.setTurn("G");
		
		x.buttonListnerEvent(codename);
		assertEquals(N, x.winTeam());
		
		location.setRevealed(false);
		x.buttonListnerEvent(codename);
		assertEquals(B, x.winTeam());
	}

	@Test
	public void Check_RandomAssign3Team() {
		Board board = new Board();
		board.randomAssign(3);
		ArrayList<String> persons = board.getPersons();
		String testString = new String();
		
		for (int a = 0; a < persons.size(); a++) {
			testString += persons.get(a);
		}
		
		assertTrue(testString.matches("(.*R.*){6}"));
		assertTrue(testString.matches("(.*B.*){5}"));
		assertTrue(testString.matches("(.*G.*){5}"));

		assertTrue(testString.matches("(.*I.*){7}"));
		assertTrue(testString.matches("(.*A.*){2}"));
		
		assertEquals(25, testString.length());
		
		assertNotEquals(testString, "RRRRRRBBBBBGGGGGIIIIIIIAA");
	}
	
	@Test
	public void Check_CheckGameState3Team() {
		Board TrueREDBoard = new Board();
		Board TrueBLUEBoard = new Board();
		Board TrueGREENBoard = new Board();
		Board ASSASSINBoard = new Board();
		
		TrueREDBoard.gameStart_3Team();
		TrueBLUEBoard.gameStart_3Team();
		TrueGREENBoard.gameStart_3Team();
		ASSASSINBoard.gameStart_3Team();
		
		assertFalse(TrueREDBoard.checkGameState());
		assertFalse(TrueBLUEBoard.checkGameState());
		assertFalse(TrueGREENBoard.checkGameState());
		assertFalse(ASSASSINBoard.checkGameState());
		
		TrueREDBoard.setRedCount(0);
		TrueBLUEBoard.setBlueCount(0);
		TrueGREENBoard.setGreenCount(0);
		ASSASSINBoard.setAssassinCount(0);

		assertTrue(TrueREDBoard.checkGameState());
		assertTrue(TrueBLUEBoard.checkGameState());
		assertTrue(TrueGREENBoard.checkGameState());
		assertTrue(ASSASSINBoard.checkGameState());
		
		TrueREDBoard.setAssassinCount(1);
		TrueREDBoard.setCurrentTeam(TrueREDBoard.getNextTeam());
		TrueREDBoard.removePriorTeam();
		assertFalse(TrueREDBoard.checkGameState());
		
		TrueREDBoard.setBlueCount(0);
		assertTrue(TrueREDBoard.checkGameState());
		
		TrueREDBoard.setBlueCount(5);
		TrueREDBoard.setAssassinCount(0);
		TrueREDBoard.setCurrentTeam(TrueREDBoard.getNextTeam());
		TrueREDBoard.removePriorTeam();
		assertTrue(TrueREDBoard.checkGameState());
	}
	
	@Test
	public void Check_GetNextTeam() {
		Board board = new Board();
		
		board.gameStart_3Team();
		Entry red = board.getCurrentTeam();
		Entry blue = red.getNext();
		Entry green = blue.getNext();
		Location location = board.getLocations().get(0);
		
		assertEquals(blue, board.getNextTeam());
		
		board.passListenerEvent();
		assertEquals(green, board.getNextTeam());
		
		board.passListenerEvent();
		assertEquals(red, board.getNextTeam());
		
		board.setCount(0);
		location.setPerson("G");
		board.updateLocation(location.getCodename());
		assertEquals(red, board.getNextTeam());
		board.setCurrentTeam(board.getNextTeam());
		
		board.setCount(1);
		location.setRevealed(false);
		board.updateLocation(location.getCodename());
		assertEquals(blue, board.getNextTeam());
		board.setCurrentTeam(board.getNextTeam());
		
		board.setCount(1);
		location.setPerson("A");
		location.setRevealed(false);
		board.updateLocation(location.getCodename());
		assertEquals(green, board.getNextTeam());
		board.setCurrentTeam(board.getNextTeam());
		board.removePriorTeam();
		
		assertEquals(red, board.getNextTeam());
		board.passListenerEvent();
		assertEquals(green, board.getNextTeam());
		
		board.setCount(1);
		location.setRevealed(false);
		board.updateLocation(location.getCodename());
		assertEquals(green, board.getNextTeam());
		
		board.gameStart_2Team();
		red = board.getCurrentTeam();
		blue = red.getNext();
		location = board.getLocations().get(0);
		
		assertEquals(blue, board.getNextTeam());
		
		board.passListenerEvent();
		assertEquals(red, board.getNextTeam());
		
		board.setCount(0);
		location.setPerson("B");
		board.updateLocation(location.getCodename());
		assertEquals(red, board.getNextTeam());
		board.setCurrentTeam(board.getNextTeam());
		
		board.setCount(1);
		location.setRevealed(false);
		board.updateLocation(location.getCodename());
		assertEquals(blue, board.getNextTeam());
		board.setCurrentTeam(board.getNextTeam());
		
		board.setCount(1);
		location.setPerson("A");
		location.setRevealed(false);
		board.updateLocation(location.getCodename());
		assertEquals(red, board.getNextTeam());
	}
}
