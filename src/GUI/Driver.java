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

/**
 * This class is the Driver from which the GUI and model for a game of Codenames are created.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class Driver implements Runnable {
	
	/**
	 * The model for a game of Codenames
	 */
	private Board _board;
	
	/**
	 * The JFrame in which the GUI is displayed.
	 */
	private JFrame _window;
	
	/**
	 * The JPanel in which the GUI elements are added.
	 */
	private JPanel _mainPanel;
	
	/**
	 * A JMenuBar that contains _window's JMenu and its commands.
	 */
	private JMenuBar _Main;
	
	/**
	 * The JMenu for _window.
	 */
	private JMenu _menu;
	
	/**
	 * Menu items to appear in _menu
	 */
	private JMenuItem _m1,_m2,_m3;
	
	/**
	 * Constructor
	 * 
	 * @param b The model for a game of Codenames
	 */
	public Driver(Board b) {
		_board = b;
	}
	
	/**
	 * Main; creates a new board and a new thread from which the GUI will be created.
	 * @param args
	 */
	public static void main(String[] args) {
		Board b = new Board("Dictionaries/GameWords.txt");
		SwingUtilities.invokeLater(new Driver(b));
	}

	/**
	 * run method for the Runnable interface. Creates the JFrame in which the GUI will be displayed.
	 */
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
		_m3 = new JMenuItem("New Game: 3 Teams");

		
		
		_Main.add(_menu);
		_menu.add(_m1);
		_menu.add(_m2);
		_menu.add(_m3);
		_window.setJMenuBar(_Main);
	
		_m1.addActionListener(new NewGameListener(_board));
		_m2.addActionListener(new CloseListener());
		_m3.addActionListener(new NewGame3PlayerListener(_board));
		
		new GUI(_board, _mainPanel, this);
		
		_window.setVisible(true);
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * Creates JDialogs associated with _window for use during gameplay. 
	 * @param message The tile of the JDialog window 
	 * @return a JDialog associated with _window
	 */
	public JDialog newDialog(String message) {
		JDialog dialog = new JDialog(_window, true);
		dialog.setTitle(message);
        return dialog;
	}
	
	/**
	 * Sets the location of a new JDialog to be the center of the JFrame.
	 * @param dialog The JDialog to be centered
	 */
	public void locateDialog(JDialog dialog) {
		dialog.setLocationRelativeTo(_window);
	}
	
	/**
	 * Repaints the GUI and resizes the JFrame based on its contents.
	 */
	public void updateJFrame() {
		_window.pack();
		_window.repaint();
	}
}
