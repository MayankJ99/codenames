package GUI;

import Code.Board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class DialogClickListener implements MouseListener {
	
	/**
	 * JDialog which displays at the start of a new turn, onto which the ClickListener will be added.
	 */
	private JDialog dialog;
	
	/**
	 * The model which contains the event handler.
	 */
	private Board board;
	
	/**
	 * Constructor
	 * 
	 * @param dialog The JDialog to which the listener will be added
	 * @param board The Board which contains the event handler
	 */
	public DialogClickListener(JDialog dialog, Board board) {
		this.dialog = dialog;
		this.board = board;
	}
	
	/**
	 * The event handler for a mouse click on the JDialog. Closes the JDialog and calls a handler in board.
	 */
	public void mouseClicked(MouseEvent me) {
		this.dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.dialog.dispatchEvent(new WindowEvent(this.dialog, WindowEvent.WINDOW_CLOSING));
		this.board.dialogClosed();
	}
	
	/**
	 * Unused event handler in MouseListener interface
	 */
	public void mouseExited(MouseEvent me) {}
	
	/**
	 * Unused event handler in MouseListener interface
	 */
	public void mouseEntered(MouseEvent me) {}
	
	/**
	 * Unused event handler in MouseListener interface
	 */
	public void mousePressed(MouseEvent me) {}
	
	/**
	 * Unused event handler in MouseListener interface
	 */
	public void mouseReleased(MouseEvent me) {}
}
