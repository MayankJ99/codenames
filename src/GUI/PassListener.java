package GUI;

import Code.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class PassListener implements ActionListener {
	private GUI gui;
	private Board board;

	public PassListener(GUI g, Board b) {
		this.gui = g;
		this.board = b;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.board.passListenerEvent();
//		this.board.setRedTurn(!this.board.getRedTurn());
		
		// TODO Auto-generated method stub
		
	}

}
