package GUI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.JDialog;

import Code.Board;
import GUI.GUI;

public class Driver implements Runnable {
	
	private Board _board;
	private JFrame _window;
	private JPanel _mainPanel;
	private JMenuBar _Main;
	private JMenu _menu;
	private JMenuItem _m1,_m2;
	
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
		_window.setIconImage(Board.webPageIcon().getImage());

		_mainPanel = new JPanel();
		_window.getContentPane().add(_mainPanel);
		
		_Main = new JMenuBar();
		_menu = new JMenu("File");
		_m1 = new JMenuItem("New Game");
		_m2  = new JMenuItem("Close");
		
		
		_Main.add(_menu);
		_menu.add(_m1);
		_menu.add(_m2);
		_window.setJMenuBar(_Main);
//		
		_m1.addActionListener(new NewGameListener(_board));
		_m2.addActionListener(new CloseListener());

		
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
	
	public void close() {
		
	}
}
