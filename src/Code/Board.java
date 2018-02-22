package Code;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

//	Feel free to change any method(return type, parameters, definition etc.) 
//  add or remove parameters and instance variables as you like

	
	
	
	private int RedCount; //Count for Red Agents
	private int BlueCount; //Count for blue agents
	private int IBCount; //Count for Innocent Bystanders
	private int Assassin; //The Assassin *Dun Dun Dun*
	
	//ArrayList holding all the names from sample .txt file
	private ArrayList<String> AllGameWords = new ArrayList<String>(); 
	
	//ArrayList holding 25 randomly selected names.
	private ArrayList<String> NewGameWords;
	
	
	
//	Correctly reads codenames from a file named GameWords.txt and stores them in a List
	public void ReadTextFile(String filename) {
		
	}
	
	
//	Creates List containing 25 distinct codenames selected at random 
	public void select25(){

		NewGameWords = new ArrayList<String>();
		Collections.shuffle(AllGameWords);
		for(int i = 0 ; i < 25 ; i++){
			NewGameWords.add(AllGameWords.get(i));
			}
	}
	
//  Creates List containing randomly generated assignments for each of the 9 Red Agents, 
//	8 Blue Agents, 7 Innocent Bystanders, and 1 Assassin	
	public void RandomAssign() {
		
	}
	
	
//	When game started, it is Red team's move and each of Board's 25 Location instances
//	is assigned a codename, Person, and is Not Revealed	
	public void GameStart() {
		
	}
	
	
//	Method defined which correctly returns if a clue is legal or illegal (clues cannot equal a current 
//	codename unless that codename is in a locations that was already Revealed)
	public boolean CheckClue(String clue) {
		return false;
		
	}
	
	
//	Method defined which decrements the count, updates a Location when the Location's
//	codename was selected, and returns if the Location contained the current team's Agent	
	public void UpdateLocation() {
		
	}
	
	
//	Method defined which correctly returns whether or not the Board is in one of the winning states	
	public boolean checkGameState() {
		return false;
	}
	
	
//	Method defined which correctly returns which team did not lose (i.e., win) when the Assassin was revealed	
	public String WinTeam() {
		return null;
		
	}
	
	
	
}
