package Tests;

import Code.Board;
import Code.Location;
import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class JUnit {
	// Test to check the method ReadTextFile.
	@Test
	public void CodeNamesListTest() {
		Board real = new Board("Dictionaries/GameWords2.txt");
		assertEquals(50, real.getAllWords().size());
		assertTrue(real.getAllWords().get(0).equals("area"));
		assertTrue(real.getAllWords().get(49).equals("year"));
		
		Board empty = new Board("Dictionaries/emptyFile.txt");
		assertTrue(empty.getAllWords().isEmpty());
		
		Board nullBoard = new Board("Dictionaries/nonExistentFile.txt");
		assertNull(nullBoard.getAllWords());
		
//		String expectedFile = "Dictionaries/GameWords2.txt";
////		String intFile = "Dictionaries/intFile";
////		String emptyFile ="Dictionaries/emptyFile.txt";
////		String nullFile = "";
//		
//		
//		Board real = new Board("Dictionaries/GameWords2.txt");
////		Board ints = new Board(intFile);
//		Board expected = new Board(expectedFile);
////		Board empty = new Board(emptyFile);
//		
//
//		assertNotNull(real.codeNameFileString());
//		assertFalse(real.codeNameFileString().isEmpty());
//		//assertFalse(real.getCodeNames().equals(ints.getCodeNames()));
//	//	assertFalse(real.getCodeNames().equals(empty.getCodeNames()));
////		assertFalse(realCodeNames.getCodeNames().equals(nullCodeNames.getCodeNames()));
//		assertEquals(expected.codeNameFileString(), real.codeNameFileString());
	}

	// Test to check the method Select25.
	@Test
	public void Check_Select25() {
		String file = "Dictionaries/GameWords2.txt";
		
		Board x = new Board(file);
		x.select25();
		
		Assert.assertEquals(25,x.getCodenames().size(),0);
		Assert.assertNotNull(x.getCodenames());
		Assert.assertTrue(x.getCodenames().size()!=0);
		
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
		
		assertFalse(testString.equals("RRRRRRRRRBBBBBBBBIIIIIIIA"));
	}
	
	// Test to check the method GameStart
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
	
	// Test to check the method CheckClue
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
		
//		for(int i = 0; i < x.getLocations().size();i++) {
//			if(x.getLocations().get(i).getCodename().equals(illegal)) {
//				if(x.getLocations().get(i).getRevealed()==true) {        <-- this is never true without an updateLocation() call
//					Assert.assertTrue(x.checkClue(illegal));
//				}
//				if(x.getLocations().get(i).getRevealed()==false) {
//					Assert.assertFalse(x.checkClue(illegal));
//				}
//			}
//		}
	}
	
	// Test to check the method UpdateLocation
	@Test
	public void Check_UpdateLocationString() {
		int randomIndex = (int) Math.floor(Math.random() * 25);
		
		Board board = new Board();
		board.gameStart();
		board.setCount(randomIndex);
		
		Location location = board.getLocations().get(randomIndex);
		String codeName = location.getCodename();
		
		assertTrue(board.updateLocation(codeName) == (location.getPerson().equals("R")));
		assertEquals(randomIndex - 1, board.getCount());
		assertTrue(board.getLocations().get(randomIndex).getRevealed());
		
		board.gameStart();
		
		for (Location item : board.getLocations())
			board.updateLocation(item.getCodename());
		assertEquals(0, board.getRedCount());
		assertEquals(0, board.getBlueCount());
		assertEquals(0, board.getAssassinCount());
	}
	
	// Test to check the method UpdateLocation
	@Test
	public void Check_UpdateLocationLoc() {
		int randomIndex = (int) Math.floor(Math.random() * 25);
		
		Board board = new Board();
		board.gameStart();
		board.setCount(randomIndex);
		
		Location location = board.getLocations().get(randomIndex);
		
		assertEquals(board.updateLocation(location), (location.getPerson().equals("R")));
		assertEquals(randomIndex - 1, board.getCount());
		assertTrue(board.getLocations().get(randomIndex).getRevealed());
		
		board.gameStart();
		
		for (Location item : board.getLocations())
			board.updateLocation(item);
		assertEquals(0, board.getRedCount());
		assertEquals(0, board.getBlueCount());
		assertEquals(0, board.getAssassinCount());
	}
	
	// Test to check the method CheckGameState
	@Test
	public void Check_CheckGameState() {
		Board TrueREDBoard = new Board("Dictionaries/GameWords2.txt");
		Board TrueBLUEBoard = new Board("Dictionaries/GameWords2.txt");
		Board ASSASSINBoard = new Board("Dictionaries/GameWords2.txt");
		
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
	
	// Test to check the method WinTeam
	@Test
	public void Check_WinTeam() {
		Board x = new Board();
		String R = "Red Team Won";
		String B = "Blue Team Won";
		String N = "No Team Won yet";
		
		x.gameStart();
		
		Assert.assertTrue(x.winTeam().equals(N));
		
		x.setAssassinCount(0);
		Assert.assertTrue(x.winTeam().equals(B));
		
		x.setRedTurn(false);
		Assert.assertTrue(x.winTeam().equals(R));
	}
}
