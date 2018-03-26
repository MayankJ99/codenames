package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Code.Board;

public class NewGameListener implements ActionListener{

	private Driver _d;
	 public NewGameListener(Driver driver) {
		 _d=driver;
	 }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	    
		_d.run();
	}

}
