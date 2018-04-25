 package Code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import GUI.Observer;

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
	 * Indicates whether the Easter Egg has been activated
	 */
	private boolean easterEgg;
	
	/**
	 * Variable to indicate the Spymaster's portion of a turn during game play.
	 */
	private boolean newTurn;
	
	/**
	 * Indicates whether the last entered clue and count were valid.
	 */
	private boolean entryError;
	
	/**
	 * Indicates whether a new game has been started.
	 */
	private boolean newGame;
	
	/**
	 * Indicates whether a turn is over during game play.
	 */
	private boolean endTurn;
	
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
	 * Arraylist containing Observers. For a game of CodeNames this is the GUI object.
	 */
	private ArrayList<Observer> _observers;
	
	/**
	 * The valid clue given by the Spymaster during a turn.
	 */
	private String Clue;
	
	/**
	 * A String indicating the reason for an invalid submission by a Spymaster, i.e. a illegal clue or count.
	 */
	private String errorMessage;
	
	/**
	 * A human-readable message that corresponds to an invalid count entry by a Spymaster.
	 */
	public static final String countError = "PLEASE ENTER A VALID COUNT";
	
	/**
	 * A human-readable message that corresponds to an invalid clue entry by a Spymaster.
	 */
	public static final String clueError = "PLEASE ENTER A VALID CLUE";
	
//	/**
//	 * A human-readable message that indicates the Red Spymaster's turn to enter a clue and count.
//	 */
//	public static final String redSpymasterMessage = "RED TEAM SPYMASTER, ENTER A CLUE AND COUNT";
//	
//	/**
//	 * A human-readable message that indicates the Blue Spymaster's turn to enter a clue and count.
//	 */
//	public static final String blueSpymasterMessage = "BLUE TEAM SPYMASTER, ENTER A CLUE AND COUNT";
	
	/**
	 * the current team entry. Similar to head of a linked list
	 */
	private Entry currentTeam;
	
	/**
	 * green count for green team
	 */
	private int greenCount;
	
	/**
	 * Constructor for Board class, default file is GameWords.txt
	 */
	public Board() {
		this.codeNamesFileReader("Dictionaries/GameWords.txt");
		this._observers = new ArrayList<>();
	}

	/**
	 * Constructor for Board class that accepts a filename to be read into allGameWords
	 * 
	 * @param filename - the string of filename that we will read over 
	 */
	public Board(String filename) {
		this.codeNamesFileReader(filename);
		this._observers = new ArrayList<>();
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
	 *  Creates List containing randomly generated assignments for the Red, Blue, and Green agents; innocent bystanders; and assassins.
	 */
	public void randomAssign(int teams) {
		String[] initialData;
		this.persons = new ArrayList<String>();
		
		if (teams == 2) {
			String[] tempData = {"R","R","R","R","R","R","R","R","R","B","B","B","B","B","B","B","B","I","I","I","I","I","I","I","A"};
			initialData = tempData;
		}
		else {
			String[] tempData = {"R","R","R","R","R","R","B","B","B","B","B","G","G","G","G","G","I","I","I","I","I","I","I","A","A"};
			initialData = tempData;
		}
		
		for (int a = 0; a < 25; a++) {
			this.persons.add(initialData[a]);
		}
		
		Collections.shuffle(this.persons);
	}
	
	/**
	 * Starts a new game. When game started, sets redTurn to true and assigns each of Board's 25 Location instances
	 * a codename, Person, and is Not Revealed. Then sets game play flags to appropriate truth values
	 * and updates observers.
	 */	
	public void gameStart_2Team() {
		this.select25();
		
		this.randomAssign(2);
		
		
		this.locations = new ArrayList<Location>();
		
		for (int a = 0; a < 25; a++)
			this.locations.add(new Location(this.newGameWords.get(a), this.persons.get(a)));
		
	
		//not needed anymore. Must be removed when code for implementing turns are replaced
		this.redTurn = true;
		
		
		this.redCount = 9;
		this.blueCount = 8;
		this.assassin = 1;
		this.count = -1;
		this.greenCount = 10;
		
		this.newGame = true;
		this.newTurn = false;
		this.endTurn =  false;
		this.entryError = false;

		this.easterEgg = false;
		
		Entry red = new Entry("R", "Red Team");
		Entry blue = new Entry("B", "Blue Team");
		
		red.setNext(blue);
		red.setPrev(blue);
		blue.setNext(red);
		blue.setPrev(red);
		
		currentTeam = red;
		this.notifyObservers();
	}
		

	public void gameStart_3Team() {
		this.select25();
			
		this.randomAssign(3);
		
		
		this.locations = new ArrayList<Location>();
		
		for (int a = 0; a < 25; a++)
			this.locations.add(new Location(this.newGameWords.get(a), this.persons.get(a)));
		
		
		//not needed anymore. Must be removed when code for implementing turns are replaced
		this.redTurn = true;
		
		this.redCount = 6;
		this.blueCount = 5;
		this.assassin = 2;
		this.count = -1;
		this.greenCount = 5;
		
		this.newGame = true;
		this.newTurn = false;
		this.endTurn =  false;
		this.entryError = false;

		this.easterEgg = false;
		
		Entry red = new Entry("R", "Red Team");
		Entry blue = new Entry("B", "Blue Team");
		Entry green = new Entry("G", "Green Team");
		
		red.setNext(blue);
		red.setPrev(green);
		blue.setNext(green);
		blue.setPrev(red);
		green.setNext(red);
		green.setPrev(blue);
		
		currentTeam = red;
		
		this.notifyObservers();
	}
	
	
	/**
	 * Method defined which correctly returns if a clue is legal or illegal (clues cannot equal a current
	 * codename unless that codename is in a locations that was already Revealed)
	 * 
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
		boolean State;
		if(this.redCount == 0 || this.blueCount == 0 || this.assassin == 0) {
		 State = true;
		}else {
			State = false;
		}
		return State;
	
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
				WT = "Red Team Won"; }
		}
	
		if(this.redTurn==false) {
			if(assassin == 0) {
				WT ="Blue Team Won";
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
	 * @return the number of red spies left
	 */
	public int getRedCount() {
		return this.redCount;
	}
	
	/**
	 * Getter method for blueCount
	 * 
	 * @return the number of blue spies left
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

	/**
	 * Adds Observers to the ArrayList _observer
	 * 
	 * @param obs the Observer to be added
	 */
	public void addObserver(Observer obs) {
		_observers.add(obs);
	}
	
	/**
	 * Calls the update() method for each Observer in _observers. Redraws all elements of the GUI to reflect changes in game state.
	 */
	public void notifyObservers() {
		for (Observer obs : _observers) {
			obs.update();
		}
	}
	
	/**
	 * Event handler for beginning-of-turn dialog windows. Sets game play flags to appropriate values 
	 * and notifies observers.
	 */
	public void dialogClosed() {
		this.newTurn = true;
		this.endTurn = false;
		this.newGame = false;
		this.notifyObservers();
	}
	
	/**
	 * Checks the validity of the count submitted by the Spymaster for the current turn.
	 * 
	 * @param countString String present in _clueField when Submit button was pressed
	 * @return Whether the count is valid
	 */
	public boolean checkCount(String countString) {
		try {
			int count = new Integer(countString);
			if (count <= 0)
				return false;
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/**
	 * Called when Submit button is pressed. Calls checkSubmission to verify the legality of the clue and count submitted
	 * by the Spymaster. Notifies GUI after checks.
	 */
	public void entriesSubmitted(String submittedClue, String submittedCount) {
		checkSubmission(submittedClue, submittedCount);
		this.notifyObservers();
	}
	
	/**
	 * Checks the validity of a submitted clue and count. If both are acceptable, the count and clue are set to the submitted values.
	 * entryError and newTurn are set to false to proceed to the guessing phase of the game. Otherwise entryError is set to 
	 * true and errorMessage is assigned to the applicable error String.
	 * 
	 * @param submittedClue the String in GUI._clueField when the Submit button was pressed
	 * @param submittedCount the String in GUI._countField when the Submit button was pressed
	 */
	public void checkSubmission(String submittedClue, String submittedCount) {
		if (!checkCount(submittedCount)) {
			this.entryError = true;
			this.errorMessage = Board.countError;
		}
		else if (!checkClue(submittedClue)) {
			this.entryError = true;
			this.errorMessage = Board.clueError;
		}
		else if (submittedClue.equals("Dr. Hertz") && !this.easterEgg) {
			this.entryError = true;
			this.easterEgg = true;
			this.errorMessage = "EASTER EGG ACTIVATED";
		}
		else {
			this.count = new Integer(submittedCount);
			this.Clue = submittedClue;
			
			this.entryError = false;
			this.newTurn = false;
		}
	}
	
	/**
	 * Button Listener which reveals an agent's faction when clicked on.
	 * Ends the player's turn if they click on an opposing faction's agent.
	 * 
	 * @param codename The String reference to the codename on the board.
	 */
	public void buttonListnerEvent(String codename) {		
		if(updateLocation(codename) == true) {
			if(this.count == -1) {
				this.endTurn = true;
				redTurn = !redTurn;				
			}
			notifyObservers();
		}
		else{
			redTurn = !redTurn;
			this.endTurn = true;
			notifyObservers();
		}
	}

	/**
	 * Event handler for the Pass button. Ends the current turn during game play.
	 */
	public void passListenerEvent() {
		this.endTurn = true;
		redTurn = !redTurn;
		notifyObservers();
	}
	
	/**
	 * Getter for Clue, the latest valid clue entered by a Spymaster.
	 * @return the value of Clue
	 */
	public String GetSubClue() {
		return Clue;
	}
	
	/**
	 * Sets the frame's icon at the top left corner and on the tool bar to a picture of Matthew Simpson.
	 */
	public static ImageIcon webPageIcon() {
		
		ImageIcon pageIcon = new ImageIcon ("src/MatthewSimpson.png");
		
		return pageIcon;
	}
	
	/**
	 * Indicates whether a new game has been started.
	 * @return the value of newGame
	 */
	public boolean getNewGame() {return this.newGame;}
	
	/**
	 * Indicates whether a turn is over.
	 * @return the value of endTurn
	 */
	public boolean getEndTurn() {return this.endTurn;}
	
	/**
	 * Indicates whether the Easter Egg has been activated.
	 * @return the value of easterEgg
	 */
	public boolean getEasterEgg() {return this.easterEgg;}
	
	/**
	 * Indicates whether it is the Spymaster's portion of the game play.
	 * @return the value of newTurn
	 */
	public boolean getNewTurn() {return this.newTurn;}
	
	/**
	 * Indicates whether there is an error in the latest clue and count submission
	 * by a Spymaster
	 * @return the value of entryError
	 */
	public boolean getEntryError() {return this.entryError;}
	
	/**
	 * Indicates an error in a Spymaster's entry.
	 * @return the value of errorMessage
	 */
	public String getErrorMessage() {return this.errorMessage;}
	
	public void changeTurn() {
		this.currentTeam = currentTeam.getNext();
	}
	
	public Entry getCurrentTurn() {
		return this.currentTeam;
	}
	
	/**
	 * Removes an assassinated team from the rotation/linked list and advances currentTeam to the next team in line
	 */
	public void removeTeamChangeTurn() {
		Entry prior = this.currentTeam.getPrev();
		Entry follower = this.currentTeam.getNext();
		
		prior.setNext(follower);
		follower.setPrev(prior);
		
		this.currentTeam = follower;
	}
	
	/**
	 * Generates a message for the Spymaster, specific to the current team
	 * @return String containing the message to the Spymaster
	 */
	public String getSpymasterMessage() {
		return (this.currentTeam.getTeam() + " SPYMASTER, ENTER A CLUE AND COUNT");
	}
	
	/**
	 * Generates the message displayed in the dialog at the start of a new turn, specific to the current team
	 * @return String containing the message to the upcoming team
	 */
	public String getDialogMessage() {
		return (this.currentTeam.getTeam() + "'S TURN");
	}
}

