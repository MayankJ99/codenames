package Tests;

import Code.Board;
import Code.Location;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class JUnit {

	
 
	
	// Test to check the method ReadTextFile.
	@Test
	public void Check_ReadTextFile() {
		fail("Not yet implemented");
	}
	
	
	// Test to check the method Select25
	@Test
	public void Check_Select25() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}
	
	
	// Test to check the method WinTeam
	@Test
	public void Check_WinTeam() {
		fail("Not yet implemented");
	}

}
