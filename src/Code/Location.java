package Code;

/**
 * This class is a model for each of the 25 locations in the game Code Words
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */

public class Location {
	/**
	 * The code name of the Location's agent
	 */
	private String codename;
	
	/**
	 * Which type of Person is at a location: Red Agent, Blue Agent, Innocent Bystander, or Assassin
	 */
	private String Person;
	
	/**
	 * Whether or not the Person has been revealed 
	 */
	private	boolean Revealed;
	
	/**
	 * Constructor
	 */
	public Location() {
		codename = "";
		Person = "";
		Revealed = false;
	}
	
	/**
	 * Constructor which accepts inputs for codename and Person	
	 * 
	 * @param C the intended codename
	 * @param P the intended Person
	 */
	public Location(String C, String P) {
		codename = C;
		Person = P;
	}
	
	/**
	 * Setter method for codename
	 * 
	 * @param c the intended codename
	 */
	public void setCodename(String c) {
		codename = c;
	}
		
	/**
	 * Getter method for codename
	 * 
	 * @return the codename for a location
	 */
	public String getCodename() {
		return codename;
	}
	
	/**
	 * Setter method for Person
	 * 
	 * @param p the intended Person
	 */
	public void setPerson(String p) {
		Person = p;
	}
	
	/**
	 * Getter method for Person
	 * 
	 * @return the Person for a Location
	 */
	public String getPerson() {
		return Person;
	}
	
	/**
	 * Setter method for Revealed
	 * 
	 * @param r the intended Revealed state
	 */
	public void setRevealed(boolean r) {
		Revealed = r;
	}
	
	/**
	 * Getter method for Revealed
	 * 
	 * @return the Revealed state for a Location
	 */
	public boolean getRevealed() {
		return Revealed;
	}
}
