package Tests;

import Code.Board;
import Code.Location;
import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class JUnit {

	
 //comment
	
	// Test to check the method ReadTextFile.
	@Test
	public void CodeNamesListTest() {
		Board real = new Board();
		Board ints = new Board();
		Board expected = new Board();
		Board empty = new Board();
		
//		CodeNamesFileReader expectedCodeNames = new CodenamesFileReader();
//		CodenamesFileReader realCodeNames = new CodenamesFileReader();
//		CodenamesFileReader intCodeNames = new CodenamesFileReader();
//		CodenamesFileReader emptyCodeNames = new CodenamesFileReader();
//		CodenamesFileReader nullCodeNames = new CodenamesFileReader();

		
		String expectedFile = "Dictionaries/GameWords2.txt";
		String intFile = "Dictionaries/intFile";
		String emptyFile ="Dictionaries/EmptyFile";
//		String nullFile = "";
		
		real.CodeNamesFileReader("Dictionaries/GameWords2.txt");
		ints.CodeNamesFileReader(intFile);
		expected.CodeNamesFileReader(expectedFile);
		empty.CodeNamesFileReader(emptyFile);

//		realCodeNames.printCodeNames("src/GameWords.txt");
//		intCodeNames.printCodeNames(intFile);
//		expectedCodeNames.printCodeNames(expectedFile);
//		emptyCodeNames.printCodeNames(emptyFile);
//		nullCodeNames.printCodeNames(nullFile);
		
		

		assertNotNull(real.getCodeNames());
		assertFalse(real.getCodeNames().isEmpty());
		assertFalse(real.getCodeNames().equals(ints.getCodeNames()));
		assertFalse(real.getCodeNames().equals(empty.getCodeNames()));
//		assertFalse(realCodeNames.getCodeNames().equals(nullCodeNames.getCodeNames()));
		assertEquals(expected.getCodeNames(), real.getCodeNames());
	}

	
	
	// Test to check the method Select25.
	@Test
	public void Check_Select25() {
		//Board x = new Board();
		String file = "Dictionaries/GameWords2.txt";
		//x.CodeNamesFileReader(file);
		//x.select25();
		
		Board x = new Board(file);
		
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
	
	
	/**
	 * Tests the content of the ArrayList returned by RandomAssign() to verify correct CodeNames characters:
	 *  - 9 Red Team spies
	 *  - 8 Blue Team spies
	 *  - 7 Innocent bystanders
	 *  - 1 Assassin
	 * 
	 * Tests that the above characters are the only contents of roles.
	 * 
	 * Tests for random ordering by comparing to the initial ordering in RandomAssign. There is a 0.0000000005% chance that the randomized 
	 * list of Persons will be the same as the initial value (1 in 200 billion).
	 * 
	 * @author Dan
	 */
	@Test
	public void Check_RandomAssign() {
		Board board = new Board();
		board.RandomAssign();
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
		board.GameStart();

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
	}
	
	
	// Test to check the method CheckClue
	@Test
	public void Check_CheckClue() {
		Board x = new Board();
		x.GameStart();
		
		String legal = "Knox 104";
		String illegal = x.getLocations().get(5).getCodename();
		String nothing = null;
		String empty = "";
		
		
	Assert.assertTrue(x.CheckClue(legal));
	Assert.assertTrue(x.CheckClue(nothing));	
	Assert.assertTrue(x.CheckClue(empty));	
	//Trick Test
	
	for(int i = 0; i < x.getLocations().size();i++) {
		if(x.getLocations().get(i).getCodename().equals(illegal))
		if(x.getLocations().get(i).getRevealed()==true) {
			Assert.assertTrue(x.CheckClue(illegal));
			}
		}
	}
	

	// Test to check the method UpdateLocation
	@Test
	public void Check_UpdateLocationString() {
		int randomIndex = (int) Math.floor(Math.random() * 25);
		
		Board board = new Board();
		board.GameStart();
		board.setCount(randomIndex);
		
		Location location = board.getLocations().get(randomIndex);
		String codeName = location.getCodename();
		
		assertTrue(board.UpdateLocation(codeName) == (location.getPerson().equals("R")));
		assertEquals(randomIndex - 1, board.getCount());
		assertTrue(board.getLocations().get(randomIndex).getRevealed());
	}
	
	// Test to check the method UpdateLocation
	@Test
	public void Check_UpdateLocationLoc() {
		int randomIndex = (int) Math.floor(Math.random() * 25);
		
		Board board = new Board();
		board.GameStart();
		board.setCount(randomIndex);
		
		Location location = board.getLocations().get(randomIndex);
		
		assertTrue(board.UpdateLocation(location) == (location.getPerson().equals("R")));
		assertEquals(randomIndex - 1, board.getCount());
		assertTrue(board.getLocations().get(randomIndex).getRevealed());
	}
	
	
	// Test to check the method CheckGameState
	@Test
	public void Check_CheckGameState() {
		Board TrueREDBoard = new Board();
		Board TrueBLUEBoard = new Board();
		Board ASSASSINBoard = new Board();
		
		int TrueRedBoardCOUNT = TrueREDBoard.getRedCount();
		while(TrueRedBoardCOUNT > 0) {
			TrueRedBoardCOUNT -= 1;
		}
		
		int TrueBLUEBoardCOUNT = TrueBLUEBoard.getBlueCount();
		while(TrueBLUEBoardCOUNT > 0) {
			TrueBLUEBoardCOUNT -= 1;
		}
		
		int ASSASSINBoardCOUNT = TrueBLUEBoard.getAssassinCount();
		while(ASSASSINBoardCOUNT > 0) {
			ASSASSINBoardCOUNT -= 1;
		}
		
		assertTrue(TrueREDBoard.checkGameState());
		assertTrue(TrueBLUEBoard.checkGameState());
		assertTrue(ASSASSINBoard.checkGameState());
		
		assertEquals(TrueREDBoard.checkGameState(),TrueRedBoardCOUNT == 0 );
		assertEquals(TrueBLUEBoard.checkGameState(),TrueBLUEBoardCOUNT == 0 );
		assertEquals(ASSASSINBoard.checkGameState(),ASSASSINBoardCOUNT == 0 );

	}
	
	
	
	// Test to check the method WinTeam
	@Test
	public void Check_WinTeam() {
		Board b = new Board("Dictionaries/GameWords2.txt");
		
		assertEquals("Blue team won!", b.WinTeam());
	}

}
