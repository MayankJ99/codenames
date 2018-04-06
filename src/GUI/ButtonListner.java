package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Code.Board;


public class ButtonListner implements ActionListener {
	private Board _board;
	private GUI gui;
	private String codename;
	
	public ButtonListner(Board _board, GUI gui, String codename) {
		this._board = _board;
		this.gui = gui;
		this.codename = codename;
	}
	
	public void actionPerformed(ActionEvent e) {
		_board.buttonListnerEvent(this.codename);
	
	} 
	

}
