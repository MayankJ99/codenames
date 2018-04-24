package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Code.Board;

/**
 * This class is an action listener for the new game menu item and JButton.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class NewGameListener implements ActionListener{

	/**
	 * The model which contains the event handler.
	 */
	private Board _b;
	
	/**
	 * Constructor
	 * @param _board A reference to the model for the current game which contains the event handler.
	 */
	 public NewGameListener(Board _board) {
		 _b=_board;
	 }
	
	 /**
	  * Calls the event handler in _b when a new game is selected during game play.
	  */
	@Override
	public void actionPerformed(ActionEvent e) {
		_b.gameStart_2Team();
	}

}
