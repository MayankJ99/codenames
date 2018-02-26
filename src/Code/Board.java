package Code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {

//	Feel free to change any method(return type, parameters, definition etc.) 
//  add or remove parameters and instance variables as you like.
	
	private int RedCount; //Count for Red Agents
	private int BlueCount; //Count for blue agents
	private int IBCount; //Count for Innocent Bystanders
	private int Assassin; //The Assassin *Dun Dun Dun*
	private int count; //The count given by the Spymaster with a clue.
	
	private boolean redTurn; //Indicates whether or not it is the Red Team's turn
	
	//ArrayList holding all the names from sample .txt file
	private ArrayList<String> AllGameWords = new ArrayList<String>(); 
	
	//ArrayList holding 25 randomly selected names.
	private ArrayList<String> NewGameWords;
	
	//ArrayList holding 25 randomized Persons (Red and Blue Agents, Bystanders, and Assassin);
	private ArrayList<String> Persons;
	
	//ArrayList holding 25 Locations
	private ArrayList<Location> Locations = new ArrayList<Location>();
	
	

	
	/**
	 * Default Constructor for Board class
	 * Temporary use until code changes are made for proper construtor
	 * 
	 * @author mayank
	 * 
	 */
	public Board() {
	
}


//Constructor to call basic methods.
	
	/**
	 * Constructor for Board class 
	 * initializes all arrayLists and variables up until the Final 25 Location instances
	 * 
	 * @param filename - the string of filename that we will read over 
	 * 
	 * @author mayank
	 */
public Board(String filename) {
	this.CodeNamesFileReader(filename);
	this.select25();
	this.RandomAssign();
	this.GameStart();
	}
	
	

//	Correctly reads codenames from a file named GameWords.txt and stores them in a List
	public void CodeNamesFileReader(String filename) {

		ArrayList<String> codeNames = new ArrayList<>();
		Scanner reader = null;

		try {
			reader = new Scanner(new File(filename));

			while (reader.hasNextLine()) {
				codeNames.add(reader.nextLine());
			}

			//System.out.println(codeNames);
			this.AllGameWords = codeNames;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			reader.close();
		}
		
	}
	
	
//	Creates List containing 25 distinct codenames selected at random 
	
/**Creates List containing 25 distinct codenames selected at random 
 * and assigns them to an arrayList
 * @author mayank
 * 	
 */
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
		this.CodeNamesFileReader("Dictionaries/GameWords2.txt");
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
	
	

	
/**
 * Method defined which correctly returns if a clue is legal or illegal (clues cannot equal a current
 * codename unless that codename is in a locations that was already Revealed)
 * @param String ; The clue that will be checked for validity
 * @return true if legal and false if not
 */
	public boolean CheckClue(String clue) {
		for(int i = 0; i < Locations.size();i++) {
			if(Locations.get(i).getCodename().equals(clue)==false) {
				return true;
			}
			if(Locations.get(i).getCodename().equals(clue)) {
				if(Locations.get(i).getRevealed()==true) {
					return true;
				}
			}
			
		}
		return false;
		
	}
	
/**
 * Method defined which decrements the count, updates a Location when the Location's
 * codename was selected, and returns if the Location contained the current team's Agent
 * 
 * @param clue the location selected by the current team
 * @return true if the location contains a spy for the current team
 * @author Dan
 */	
	public boolean UpdateLocation(String guess) {
		this.count -= 1;
		for (Location location : this.Locations) {
			if (guess.equals(location.getCodename())) {
				location.setRevealed(true);
				if ((this.redTurn && (location.getPerson().equals("R"))) || (!this.redTurn && (location.getPerson().equals("B"))))
					return true;
			}
		}
		return false;
	}

/**
 * Method defined which decrements the count, updates a Location when the Location's
 * codename was selected, and returns if the Location contained the current team's Agent
 * 
 * @param location a reference to the location selected by the current team
 * @return true if the location contains a spy for the current team
 * @author Dan
 */	
	public boolean UpdateLocation(Location location) {
		this.count -= 1;
		location.setRevealed(true);
		if ((this.redTurn && (location.getPerson().equals("R"))) || (!this.redTurn && (location.getPerson().equals("B"))))
			return true;
		return false;
	}
	
	
//	Method defined which correctly returns whether or not the Board is in one of the winning states	
	public boolean checkGameState() {
		return false;
	}
	
	
/**
 * Method defined which correctly returns which team did not lose (i.e., win) when the Assassin was revealed	
 * @return "Blue team won!" if the assassin gets revealed in Red Team's turn. 
 * @author Minseo Kim 
 */
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
	
/**Setter Method for All Game Words
 * 
 * @return AllGameWords arrayList which contains all gamewords from the txt file
 * 
 * @author mayank
 */
	
	
	public ArrayList<String> getAllWords(){ return this.AllGameWords; }
	
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
	
/**
 * Setter method for count
 * 
 * @author Dan
 */
	public void setCount(int c) {this.count = c;}

/**
 * Getter method for count
 *
 * @author Dan
 */
	public int getCount() {return this.count;}
}
