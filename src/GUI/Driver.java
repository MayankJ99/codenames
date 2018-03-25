package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;

import javax.swing.JDialog;

import Code.Board;
import GUI.GUI;

public class Driver implements Runnable {
	
	private Board _board;
	private JFrame _window;
	private JPanel _mainPanel;
	
	public Driver(Board b) {
		_board = b;
	}
	
	public static void main(String[] args) {
		Board b = new Board("Dictionaries/GameWords.txt");
		SwingUtilities.invokeLater(new Driver(b));
	}

	@Override
	public void run() {
		_window = new JFrame("CodeNames");
		_mainPanel = new JPanel();
		_window.getContentPane().add(_mainPanel);
		
		new GUI(_board, _mainPanel, this);
		
		_window.setVisible(true);
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public JDialog newDialog(String message) {
		JDialog dialog = new JDialog(_window, true);
		dialog.setTitle(message);
        return dialog;
	}
	
	public void locateDialog(JDialog dialog) {
		dialog.setLocationRelativeTo(_window);
	}
	
	public void updateJFrame() {
		_window.pack();
		_window.repaint();
	}
}
