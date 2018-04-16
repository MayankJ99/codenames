package GUI;

import Code.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a button listener for the Pass button, used when a team wants to end their turn.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class PassListener implements ActionListener {
	
	/**
	 * The model for the current game which contains the handler method.
	 */
	private Board board;

	/**
	 * Constructor
	 * @param g The GUI for the current game
	 * @param b The Board for the current game
	 */
	public PassListener(GUI g, Board b) {
		this.board = b;
	}
	
	/**
	 * Calls the board handler method in response to the Pass button being clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.board.passListenerEvent();
	}

}
