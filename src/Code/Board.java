package Code;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

//	Feel free to change any method(return type, parameters, definition etc.) 
//  add or remove parameters and instance variables as you like.
	
	private int RedCount; //Count for Red Agents
	private int BlueCount; //Count for blue agents
	private int IBCount; //Count for Innocent Bystanders
	private int Assassin; //The Assassin *Dun Dun Dun*
	
	private boolean redTurn; //Indicates whether or not it is the Red Team's turn
	
	//ArrayList holding all the names from sample .txt file
	private ArrayList<String> AllGameWords = new ArrayList<String>(); 
	
	//ArrayList holding 25 randomly selected names.
	private ArrayList<String> NewGameWords;
	
	//ArrayList holding 25 randomized Persons (Red and Blue Agents, Bystanders, and Assassin);
	private ArrayList<String> Persons;
	
	//ArrayList holding 25 Locations
	private ArrayList<Location> Locations;
	
	
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
	
/**
 *  Creates List containing randomly generated assignments for each of the 9 Red Agents, 
 *  8 Blue Agents, 7 Innocent Bystanders, and 1 Assassin
 *  
 *  @author Dan
 */
	public void RandomAssign() {
		String[] initialData = {"R","R","R","R","R","R","R","R","R","B","B","B","B","B","B","B","B","I","I","I","I","I","I","I","A"};
		this.Persons = new ArrayList<>();
		
		for (int a = 0; a < 25; a++) {
			this.Persons.add(initialData[a]);
		}
		
		Collections.shuffle(this.Persons);
	}
	
/**
 * When game started, sets redTurn to true and assigns each of Board's 25 Location instances
 * a codename, Person, and is Not Revealed
 * 
 * @author Dan
 */	
	public void GameStart() {
		this.select25();
		this.RandomAssign();
		
		for (int a = 0; a < 25; a++) {
			Location temp = new Location();
			temp.setCodename(this.NewGameWords.get(a));
			temp.setPerson(this.Persons.get(a));
			this.Locations.add(temp);
			//this.Locations.add(new Location(this.NewGameWords.get(a), this.Persons.get(a), false));
		}
		
		this.redTurn = true;
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
		String WT = "";

		if(this.redTurn) {
			for(int i = 0; i<25 ; i++) {
				if(Locations.get(i).getCodename() == "A") {
					WT = "Blue team won!";
					break;
				}
			}
		}

		else
		{
			for(int i = 0; i<25 ; i++) {
				if(Locations.get(i).getCodename() == "A") {
					WT = "Red team won!";
					break;
				}
			}
		}




		return WT;
	}	
	
/**
 * Getter method for Persons
 * 
 * @author Dan
 */
	public ArrayList<String> getPersons() {return this.Persons;}
	
/**
 * Getter method for Locations
 * 
 * @author Dan
 */	
	public ArrayList<Location> getLocations() {return this.Locations;}

/**
 * Getter method for NewGameWords
 * 
 * @author Dan
 */
	public ArrayList<String> getCodenames() {return this.NewGameWords;}

/**
 * Getter method for redTurn
 * 
 * @author Dan
 */
	public boolean getRedTurn() {return this.redTurn;}
	
	
}
