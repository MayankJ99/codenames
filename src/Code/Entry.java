package Code;

/**
 * A class for nodes in a circular doubly-linked list. Each Entry represents a team in a game of Codenames.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class Entry {

	/**
	 * One-character String representing an agent's team
	 */
	private String TeamInitial;
	
	/**
	 * String with the full name of an agent's team
	 */
	private String Team;
	
	/**
	 * A reference to the next Entry in the linked list
	 */
	private Entry next;
	
	/**
	 * A reference to the previous Entry in the linked list
	 */
	private Entry prev;
	
	/**
	 * Entry constructor
	 * @param _init One-character String team initial
	 * @param _team Full team name String
	 */
	public Entry(String _init, String _team) {
		this.TeamInitial = _init;
		this.Team = _team;
	}
	
	/**
	 * Getter method for the next Entry in the linked list
	 * @return the next Entry in the linked list
	 */
	public Entry getNext() {
		return this.next;
	}
	
	/**
	 * Setter method for the next Entry in the linked list
	 * @param e Entry to be set as the next Entry
	 */
	public void setNext(Entry e) {
		this.next = e;
	}
	
	/**
	 * Getter method for the previous Entry in the linked list
	 * @return the previous Entry in the linked list
	 */
	public Entry getPrev() {
		return this.prev;
	}
	
	/**
	 * Setter methodfor the previous Entry in the linked list
	 * @param e Entry to be set as the previous Entry
	 */
	public void setPrev(Entry e) {
		this.prev = e;
	}
	
	/**
	 * Setter method for an Entry's initial
	 * @param _init single-character initial for the team
	 */
	public void setInitial(String _init) {
		this.TeamInitial = _init;	
	}
	
	/**
	 * Getter method for an Entry's initial
	 * @return Entry initial
	 */
	public String getInitial() {
		return this.TeamInitial;
	}
	
	/**
	 * Setter method for an Entry's team
	 * @param _team full name of the Entry's team
	 */
	public void setTeam(String _team) {
		this.Team = _team;
	}
	
	/**
	 * Getter method for an Entry's team
	 * @return full name of the Entry's team
	 */
	public String getTeam() {
		return this.Team;
	}
}
