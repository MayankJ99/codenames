package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Code.Board;

/**
 * This class is a button listener for the Locations on a Codenames game board.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class ButtonListner implements ActionListener {
	
	/**
	 * The model which contains the handler method.
	 */
	private Board _board;
	
	/**
	 * The String identifier of a Location.
	 */
	private String codename;
	
	/**
	 * Constructor
	 * 
	 * @param _board The Board model for the current game.
	 * @param gui The GUI in which the Locations are clicked.
	 * @param codename The identifier for a Location.
	 */
	public ButtonListner(Board _board, GUI gui, String codename) {
		this._board = _board;
		this.codename = codename;
	}
	
	/**
	 * Calls the _board handler method in response to a Location being clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		_board.buttonListnerEvent(this.codename);
	
	} 
	

}
