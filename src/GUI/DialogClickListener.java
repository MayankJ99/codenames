package GUI;

import Code.Board;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class DialogClickListener implements MouseListener {
	private JDialog dialog;
	private Board board;
	
	public DialogClickListener(JDialog dialog, Board board) {
		this.dialog = dialog;
		this.board = board;
	}
	
	public void mouseClicked(MouseEvent me) {
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.dialog.dispatchEvent(new WindowEvent(this.dialog, WindowEvent.WINDOW_CLOSING));
		this.board.endNewTurn();
	}
	
	public void mouseExited(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
}
