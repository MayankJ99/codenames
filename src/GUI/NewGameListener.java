package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Code.Board;

public class NewGameListener implements ActionListener{

	private Board _b;
	 public NewGameListener(Board _board) {
		 _b=_board;
	 }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	    
		_b.gameStart();
	}

}
