package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;

public class DialogClickListener implements MouseListener {
	private JDialog dialog;
	
	public DialogClickListener(JDialog dialog) {
		this.dialog = dialog;
	}
	
	public void mouseClicked(MouseEvent me) {
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.dialog.dispatchEvent(new WindowEvent(this.dialog, WindowEvent.WINDOW_CLOSING));
	}
	
	public void mouseExited(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
}
