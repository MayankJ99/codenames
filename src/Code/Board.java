package Code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class simulates the board game Code Names.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
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
	 * Constructor for Board class, default file is GameWords.txt
	 */
	public Board() {
		this.codeNamesFileReader("Dictionaries/GameWords.txt");
	}

	/**
	 * Constructor for Board class that accepts a filename to be read into allGameWords
	 * 
	 * @param filename - the string of filename that we will read over 
	 */
	public Board(String filename) {
		this.codeNamesFileReader(filename);
	}
	
	/**
	 * Reads a file by its location, parameterized as a String input, and creates a
	 * list containing 25 code names from the aforementioned file.
	 * 
	 * @param filename
	 *            String of the name of the path of the file to be read.
	 */
	public void codeNamesFileReader(String filename) {
		Scanner reader = null;

		try {
			File file = new File(filename);
			reader = new Scanner(file);
			this.allGameWords = new ArrayList<>();
			
			while (reader.hasNextLine()) {
				allGameWords.add(reader.nextLine());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * Creates List containing 25 distinct codenames selected at random 
	 * and assigns them to an arrayList	
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
	 */
	public void randomAssign() {
		String[] initialData = {"R","R","R","R","R","R","R","R","R","B","B","B","B","B","B","B","B","I","I","I","I","I","I","I","A"};
		this.persons = new ArrayList<String>();
		
		for (int a = 0; a < 25; a++) {
			this.persons.add(initialData[a]);
		}
		
		Collections.shuffle(this.persons);
	}
	
	/**
	 * When game started, sets redTurn to true and assigns each of Board's 25 Location instances
	 * a codename, Person, and is Not Revealed
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
			if (locations.get(i).getCodename().equalsIgnoreCase(clue) && !locations.get(i).getRevealed())
				return false;
		return true;
	}
	
	/**
	 * Method defined which decrements the count, updates a Location when the Location's
	 * codename was selected, and returns if the Location contained the current team's Agent
	 * 
	 * @param guess the code name for the location selected by the current team
	 * @return true if the code name's location contains a spy for the current team
	 */	
	public boolean updateLocation(String guess) {
		this.count -= 1;
		for (Location location : this.locations) {
			if (guess.equalsIgnoreCase(location.getCodename())) {
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
	 * "Method defined which correctly returns whether or not the Board is in one of the winning states."
	 *  
	 * @return true if blueCount or redCount or assassinCount equals 0 else it returns false
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
	 *
	 * @return a String identifying the winner if the assassin has been revealed. 
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
	
	/**
	 * Getter Method for allGameWords
	 * 
	 * @return allGameWords arrayList which contains allGameWords from the text file
	 */
	public ArrayList<String> getAllWords(){ return this.allGameWords; }
	
	/**
	 * Getter method for persons
	 */
	public ArrayList<String> getPersons() {return this.persons;}
	
	/**
	 * Getter method for locations
	 */	
	public ArrayList<Location> getLocations() {return this.locations;}

	/**
	 * Getter method for newGameWords
	 */
	public ArrayList<String> getCodenames() {return this.newGameWords;}

	/**
	 * Getter method for redTurn
	 */
	public boolean getRedTurn() {return this.redTurn;}
	
	/**
	 * setterMethod for redTurn	
	 * @param x boolean value
	 */
	public void setRedTurn(boolean x) {this.redTurn = x;}

	/**
	 * Setter method for count
	 */
	public void setCount(int c) {this.count = c;}

	/**
	 * Getter method for count
	 */
	public int getCount() {return this.count;}

	/**
	 * Getter method for redCount
	 * 
	 * @return the number of red spys left
	 */
	public int getRedCount() {
		return this.redCount;
	}
	
	/**
	 * Getter method for blueCount
	 * 
	 * @return the number of blue spys left
	 */
	public int getBlueCount() {
		return this.blueCount;
	}
	
	/**
	 * Getter method for assassin
	 * 
	 * @return the number of assassins left
	 */
	public int getAssassinCount() {
		return this.assassin;
	}
	
	/**
	 * Setter method for redCount
	 * @param x integer value for new value of red spies
	 */
	public void setRedCount(int x ) {
		this.redCount =x;
	}
	
	/**
	 * setter method for blueCount
	 * @param x integer value for new value of blue spies
	 */
	public void setBlueCount( int x ) {
		this.blueCount = x;
	}
	
	/**
	 * setter method for assassin
	 * @param x integer value for new value of Assassin
	 */
	public void setAssassinCount(int x ) {
		this.assassin=x;
	}
}