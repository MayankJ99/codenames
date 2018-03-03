package Code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {

//	Feel free to change any method(return type, parameters, definition etc.) 
//  add or remove parameters and instance variables as you like.
	
	/**
	 * Counter for number of red spies
	 */
	private int RedCount; 
	
	/**
	 * Counter for number of blue spies
	 */
	private int BlueCount; //Count for blue agents
	
	/**
	 * Counter for number of Innocent Bystanders
	 */
	private int IBCount; //Count for Innocent Bystanders
	
	/**
	 * Assassin Counter
	 */
	private int Assassin; //The Assassin *Dun Dun Dun*
	
	/**
	 * The count given by the Spymaster with a clue.
	 */
	private int count; //The count given by the Spymaster with a clue.
	
	/**
	 * Indicates whether or not it is the Red Team's turn
	 */
	private boolean redTurn; //Indicates whether or not it is the Red Team's turn
	
	/**
	 * ArrayList holding all the names from sample .txt file
	 */
	private ArrayList<String> AllGameWords= new ArrayList<String>();
	
	/**
	 * ArrayList holding 25 randomly selected names.
	 */
	private ArrayList<String> NewGameWords;
	
	/**
	 * ArrayList holding 25 randomized Persons (Red and Blue Agents, Bystanders, and Assassin);
	 */
	private ArrayList<String> Persons;
	
	/**
	 * ArrayList holding 25 Locations
	 */
	private ArrayList<Location> Locations = new ArrayList<Location>();
	
	/**
	 * String holding the path of the file.
	 */
	private String filename;
	
	

	
	/**
	 * Default Constructor for Board class
	 * Temporary use until code changes are made for proper construtor
	 * 
	 * @author mayank
	 * 
	 */
	public Board() {
		this.RedCount = 9;
		this.BlueCount = 8; 
		this.IBCount=7;
		this.Assassin = 1;
	
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
	
	

	// Correctly reads codenames from a file named GameWords.txt and stores them in
	// a List
	/**
	 * Reads a file by its location, parameterized as a String input, and creates a
	 * list containing 25 code names from the aforementioned file.
	 * 
	 * @author Juan Mendoza
	 * 
	 * @param filename
	 *            String of the name of the path of the file to be read.
	 */
	public void CodeNamesFileReader(String filename) {

			
			this.filename = filename;

			ArrayList<String> codeNames = new ArrayList<>();
			Scanner reader = null;

			try {
				File file = new File(filename);
				reader = new Scanner(file);

				while (reader.hasNextLine()) {
					codeNames.add(reader.nextLine());
				}

				System.out.println(codeNames);

			} catch (IOException ex) {
				ex.printStackTrace();
			} 
			finally {
				reader.close();
			}

			
			AllGameWords = codeNames;
		}
		
		public String getCodeNames(){
			String lines = null;
			try {
				for(String line : Files.readAllLines(Paths.get(this.filename))) {
					lines = line + " " + lines;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return lines ;
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
				switch (location.getPerson()) {
					case "R" : this.RedCount -= 1;
					break;
					case "B" : this.BlueCount -= 1;
					break;
					case "I" : this.IBCount -= 1;
					break;
					case "A" : this.Assassin -= 1;
					break;
				}
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
		switch (location.getPerson()) {
			case "R" : this.RedCount -= 1;
			break;
			case "B" : this.BlueCount -= 1;
			break;
			case "I" : this.IBCount -= 1;
			break;
			case "A" : this.Assassin -= 1;
			break;
		}
		location.setRevealed(true);
		if ((this.redTurn && (location.getPerson().equals("R"))) || (!this.redTurn && (location.getPerson().equals("B"))))
			return true;
		return false;
	}
	
/**
 * "Method defined which correctly returns whether or not the Board is in one of the winning states."
 *  
 * @return true if blueCount or recount or assassinCount equals 0 else it returns false
 * @author Juan Mendoza
 */
	public boolean checkGameState() {
//		if(WinTeam().equals("Blue team won!")) {
//			return true;
//		}else if(WinTeam().equals( "Red team won!")) {
//			return true;
//		}
		if(this.RedCount == 0 || this.BlueCount == 0 || this.Assassin == 0) {
			return true;
		}else {
			return false;
		}
	}


	
	
/**
 * Method defined which correctly returns which team did not lose (i.e., win) when the Assassin was revealed	
 * @return "Blue team won!" if the assassin gets revealed in Red Team's turn. 
 * @author Minseo Kim
 */
	public String WinTeam() {
String WT = "";
	if(this.redTurn) {
		if(Assassin==0) {
		WT = "Blue Team Won"; }
	}
	
	if(this.redTurn==false) {
		if(Assassin == 0) {
			WT ="Red Team Won";
			
		}
	}
	
	if(Assassin > 0) {
		WT = "No Team Won yet";
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
 * setterMethod for red turn	
 * @param x boolean value
 * @author mayank
 */
	public void setRedTurn(boolean x) {this.redTurn = x;}

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

/**
 * @return the number of red spys left
 * 
 * @author Juan Menodza
 */
	public int getRedCount() {
		return this.RedCount;
	}
	
	/**
	 * @return the number of blue spys left
	 * 
	 * @author Juan Menodza
	 */
	
	public int getBlueCount() {
		return this.BlueCount;
	}
	
	/**
	 * @return the number of assassins left
	 * 
	 * @author Juan Menodza
	 */
	public int getAssassinCount() {
		return this.Assassin;
	}
	
	
	/**
	 * Setter method for red count
	 * @param x integer value for new value of red spies
	 * @author mayank
	 */
	public void setRedCount(int x ) {
		this.RedCount =x;
	}
	
	/**
	 * setter method for blue count
	 * @param x integer value for new value of blue spies
	 * @author mayank
	 */
	public void setBlueCount( int x ) {
		this.BlueCount = x;
	}
	
	/**
	 * setter method for assassin count
	 * @param x integer value for new value of Assassin
	 * @author mayank
	 */
	public void setAssassinCount(int x ) {
		this.Assassin=x;
	}
	
	
	
	
	
}