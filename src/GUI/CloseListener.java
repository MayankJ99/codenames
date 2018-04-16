package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is an action listener for the Close JMenu item and Close button, which close the JFrame.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class CloseListener implements ActionListener {

	/**
	 * Calls System.exit(0) when a close menu item or button are clicked, exiting the JFrame.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
