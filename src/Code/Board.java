package Code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class simulates the board game Code Names.
 * 
 * @author Mayank, Minseo Kim, Juan Mendoza, Dan Walsh
 */

public class Board {
	/**
	 * Counter for number of red spies
	 */
	private int redCount; 
	
	/**
	 * Counter for number of blue spies
	 */
	private int blueCount;
	
	/**
	 * Assassin Counter
	 */
	private int assassin;
	
	/**
	 * The count given by the Spymaster with a clue.
	 */
	private int count;
	
	/**
	 * Indicates whether or not it is the Red Team's turn
	 */
	private boolean redTurn;
	
	/**
	 * ArrayList holding all the names from sample .txt file
	 */
	private ArrayList<String> allGameWords;
	
	/**
	 * ArrayList holding 25 randomly selected names.
	 */
	private ArrayList<String> newGameWords;
	
	/**
	 * ArrayList holding 25 randomized Persons (Red and Blue Agents, Bystanders, and Assassin);
	 */
	private ArrayList<String> persons;
	
	/**
	 * ArrayList holding 25 Locations
	 */
	private ArrayList<Location> locations;
	
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
		this.codeNamesFileReader("Dictionaries/GameWords2.txt");
	}

	/**
	 * Constructor for Board class 
	 * initializes all arrayLists and variables up until the Final 25 Location instances
	 * 
	 * @param filename - the string of filename that we will read over 
	 * 
	 * @author mayank
	 */
	public Board(String filename) {
		this.codeNamesFileReader(filename);
	}
	
	/**
	 * Reads a file by its location, parameterized as a String input, and creates a
	 * list containing 25 code names from the aforementioned file.
	 * 
	 * @author Juan Mendoza
	 * 
	 * @param filename
	 *            String of the name of the path of the file to be read.
	 */
	public void codeNamesFileReader(String filename) {
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
		allGameWords = codeNames;
	}

	/**
	 * Method for comparison of file content; concatenates all lines into a String, separated by spaces
	 * 
	 * @return String consisting of code names from file
	 */
	public String codeNameFileString(){
		String lines = " ";
		try {
			for(String line : Files.readAllLines(Paths.get(this.filename))) {
				lines = line + " " + lines;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	/**Creates List containing 25 distinct codenames selected at random 
	 * and assigns them to an arrayList
	 * @author mayank
	 * 	
	 */
	public void select25(){
		newGameWords = new ArrayList<String>();
		Collections.shuffle(allGameWords);
		for(int i = 0 ; i < 25 ; i++){
			newGameWords.add(allGameWords.get(i));
		}
	}
	
	/**
	 *  Creates List containing randomly generated assignments for each of the 9 Red Agents, 
	 *  8 Blue Agents, 7 Innocent Bystanders, and 1 Assassin
	 *	  
	 *  @author Dan
	 */
	public void randomAssign() {
		String[] initialData = {"R","R","R","R","R","R","R","R","R","B","B","B","B","B","B","B","B","I","I","I","I","I","I","I","A"};
		this.persons = new ArrayList<>();
		
		for (int a = 0; a < 25; a++) {
			this.persons.add(initialData[a]);
		}
		
		Collections.shuffle(this.persons);
	}
	
	/**
	 * When game started, sets redTurn to true and assigns each of Board's 25 Location instances
	 * a codename, Person, and is Not Revealed
	 * 
	 * @author Dan
	 */	
	public void gameStart() {
		this.select25();
		this.randomAssign();
		this.locations = new ArrayList<Location>();
		
		for (int a = 0; a < 25; a++)
			this.locations.add(new Location(this.newGameWords.get(a), this.persons.get(a)));
		
		this.redTurn = true;
		this.redCount = 9;
		this.blueCount = 8;
		this.assassin = 1;
	}
		
	/**
	 * Method defined which correctly returns if a clue is legal or illegal (clues cannot equal a current
	 * codename unless that codename is in a locations that was already Revealed)
	 * @param String ; The clue that will be checked for validity
	 * @return true if legal and false if not
	 */
	public boolean checkClue(String clue) {
		for (int i = 0; i < locations.size(); i++)
			if (locations.get(i).getCodename().equals(clue) && !locations.get(i).getRevealed())
				return false;
		return true;
	}
	
	/**
	 * Method defined which decrements the count, updates a Location when the Location's
	 * codename was selected, and returns if the Location contained the current team's Agent
	 * 
	 * @param clue the location selected by the current team
	 * @return true if the location contains a spy for the current team
	 * @author Dan
	 */	
	public boolean updateLocation(String guess) {
		this.count -= 1;
		for (Location location : this.locations) {
			if (guess.equals(location.getCodename())) {
				switch (location.getPerson()) {
					case "R" : this.redCount -= 1;
					break;
					case "B" : this.blueCount -= 1;
					break;
					case "A" : this.assassin -= 1;
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
	public boolean updateLocation(Location location) {
		this.count -= 1;
		switch (location.getPerson()) {
			case "R" : this.redCount -= 1;
			break;
			case "B" : this.blueCount -= 1;
			break;
			case "A" : this.assassin -= 1;
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
		if(this.redCount == 0 || this.blueCount == 0 || this.assassin == 0) {
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
	public String winTeam() {
		String WT = "";
		if(this.redTurn) {
			if(assassin==0) {
				WT = "Blue Team Won"; }
		}
	
		if(this.redTurn==false) {
			if(assassin == 0) {
				WT ="Red Team Won";
			}
		}
	
		if(assassin > 0) {
			WT = "No Team Won yet";
		}

		return WT;
	}	
	
	/**Getter Method for All Game Words
	 * 
	 * @return AllGameWords arrayList which contains all gamewords from the txt file
	 * 
	 * @author mayank
	 */
	public ArrayList<String> getAllWords(){ return this.allGameWords; }
	
	/**
	 * Getter method for Persons
	 * 
	 * @author Dan
	 */
	public ArrayList<String> getPersons() {return this.persons;}
	
	/**
	 * Getter method for Locations
	 * 
	 * @author Dan
	 */	
	public ArrayList<Location> getLocations() {return this.locations;}

	/**
	 * Getter method for NewGameWords
	 * 
	 * @author Dan
	 */
	public ArrayList<String> getCodenames() {return this.newGameWords;}

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
	 * Getter method for RedCount
	 * 
	 * @return the number of red spys left
	 * 
	 * @author Juan Menodza
	 */
	public int getRedCount() {
		return this.redCount;
	}
	
	/**
	 * Getter method for Bluecount
	 * 
	 * @return the number of blue spys left
	 * 
	 * @author Juan Menodza
	 */
	public int getBlueCount() {
		return this.blueCount;
	}
	
	/**
	 * Getter method for Assassins
	 * 
	 * @return the number of assassins left
	 * 
	 * @author Juan Menodza
	 */
	public int getAssassinCount() {
		return this.assassin;
	}
	
	/**
	 * Setter method for red count
	 * @param x integer value for new value of red spies
	 * @author mayank
	 */
	public void setRedCount(int x ) {
		this.redCount =x;
	}
	
	/**
	 * setter method for blue count
	 * @param x integer value for new value of blue spies
	 * @author mayank
	 */
	public void setBlueCount( int x ) {
		this.blueCount = x;
	}
	
	/**
	 * setter method for assassin count
	 * @param x integer value for new value of Assassin
	 * @author mayank
	 */
	public void setAssassinCount(int x ) {
		this.assassin=x;
	}
}