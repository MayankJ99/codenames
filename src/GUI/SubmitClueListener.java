package GUI;

import Code.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * This class is a button listener for the Submit button, used when a Spymaster enters a clue and count.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class SubmitClueListener implements ActionListener {
	
	/**
	 * The model for the current game which contains the handler method.
	 */
	private Board board;
	
	/**
	 * The JTextField in which the clue is entered.
	 */
	private JTextField clueField;
	
	/**
	 * The JTextField in which the count is entered.
	 */
	private JTextField countField;
	
	/**
	 * Constructor
	 * 
	 * @param board The board
	 * @param clueField The clueField
	 * @param countField The countField
	 */
	public SubmitClueListener(Board board, JTextField clueField, JTextField countField) {
		this.board = board;
		this.clueField = clueField;
		this.countField = countField;
	}
	
	/**
	 * Calls the board handler method in response to the Submit button being clicked
	 */
	public void actionPerformed(ActionEvent ae) {
		this.board.entriesSubmitted(this.clueField.getText(), this.countField.getText());
	}
}
