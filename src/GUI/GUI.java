package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JDialog;

import Code.Board;

/**
 * This class creates a user interface for the Board class.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class GUI implements Observer {

	/**
	 * The width of the screen in pixels.
	 */
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	/**
	 * The height of the screen in pixels.
	 */
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 * The font to be used for text in the GUI.
	 */
	private Font font = new Font("Courier", Font.BOLD, (int) (screenHeight * 0.03));
	
	/**
	 * The JPanel added to the JFrame in Driver.
	 */
	private JPanel _mainPanel;
	
	/**
	 * The JPanel containing Locations.
	 */
	private JPanel _locationPanel;
	
	/**
	 * The JPanel containing game play I/O.
	 */
	private JPanel _infoPanel;
	
	/**
	 * The JPanel for messages to users.
	 */
	private JPanel _messagePanel;
	
	/**
	 * The JPanel for the clue submission JTextField.
	 */
	private JPanel _cluePanel;
	
	/**
	 * The JPanel for the count submission JTextField.
	 */
	private JPanel _countPanel;
	
	/**
	 * The JPanel for buttons.
	 */
	private JPanel _buttonPanel;
	
	/**
	 * The JTextField in which clues are entered.
	 */
	private JTextField _clueField;
	
	/**
	 * The JTextField in which counts are entered.
	 */
	private JTextField _countField;
	
	/**
	 * The Board which serves as the model for a game of CodeNames.
	 */
	private Board _board;
	
	/**
	 * The Driver which started the GUI.
	 */
	private Driver _windowHolder;
	
	/**
	 * Constructor for GUI. Creates JPanels in which content will be drawn. Sets attributes for a particular appearance.
	 * 
	 * @param b The Board which is the model for the GUI
	 * @param mp The JPanel in the JFrame on which the GUI will be drawn
	 * @param driver The Driver which created the GUI
	 */
	public GUI(Board b, JPanel mp, Driver driver) {
		_windowHolder = driver;
		_board = b;		
		_mainPanel = mp;
		
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		_locationPanel = new JPanel();
		_locationPanel.setLayout(new GridLayout(5, 5));
		_locationPanel.setPreferredSize(new Dimension((int) (this.screenWidth * .8), (int) (this.screenHeight * .6)));
		_mainPanel.add(_locationPanel);
		
		_infoPanel = new JPanel();
		_infoPanel.setLayout(new BoxLayout(_infoPanel, BoxLayout.Y_AXIS));
		_infoPanel.setPreferredSize(new Dimension((int) (this.screenWidth * .8), (int) (this.screenHeight * .2)));
		_infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
		_mainPanel.add(_infoPanel);	
		
		_messagePanel = new JPanel();
		_messagePanel.setMaximumSize(new Dimension((int) (screenWidth * .8), (int) (screenHeight)));
		_messagePanel.setMinimumSize(new Dimension((int) (screenWidth * .8), (int) 0));
		_infoPanel.add(_messagePanel);
		
		_cluePanel = new JPanel();
		_cluePanel.setLayout(new BoxLayout(_cluePanel, BoxLayout.X_AXIS));
		_cluePanel.setMaximumSize(new Dimension((int) (screenWidth * .4), (int) (screenHeight)));
		_infoPanel.add(_cluePanel);
		
		_countPanel = new JPanel();
		_countPanel.setLayout(new BoxLayout(_countPanel, BoxLayout.X_AXIS));
		_countPanel.setMaximumSize(new Dimension((int) (screenWidth * .4), (int) (screenHeight)));
		_infoPanel.add(_countPanel);
		
		_buttonPanel = new JPanel();
		_buttonPanel.setMaximumSize(new Dimension((int) (screenWidth * .8), (int) (screenHeight)));
		_infoPanel.add(_buttonPanel);
		
		_board.addObserver(this);
		updateJFrameIfNotHeadless();
	}
	
	/**
	 * update method for Observer interface. Builds the GUI and determines how GUI will display based on information from _board.
	 */
	@Override
	public void update() {
		
		if (_board.getNewTurn() || _board.checkGameState()) {
			this.SpymasterView();
		}
		else { 
			this.PlayerView();
		}
	
		if(_board.checkGameState()==true) {
			this.GameEndScenario();
		}
		else if (_board.getNewGame()) {
			this.newGame();
		}
		else if (_board.getNewTurn()) {
			if (_board.getEntryError())
				this.submissionError();
			else
				this.newTurn();
		}
		else
		{
			this.CurrentTurn();
			
			if (_board.getEndTurn()) {
				this.displayDialog();
			}
		}
	}
	
	/**
	 * Repaints the GUI
	 */
	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}
	
	/**
	 * Sets the properties for Locations on the board. Color of the button is determined by a Location's revealed status.
	 * 
	 * @param button JButton on the board.
	 * @param idx its reference on the board from the GUI.
	 */
	public void setButtonProperties(JButton button, int idx) {
		Color R = new Color(255, 0, 0);
		Color B = new Color(0, 0, 225);
		Color I = new Color(192,192,192);
		Color A = new Color(128,128,0);
		
		if (_board.getLocations().get(idx).getRevealed() == true) {
			switch (_board.getLocations().get(idx).getPerson()) {
			case "R" : 
				button.setBackground(R);
				button.setText("");
				break;
			case "B" : 
				button.setBackground(B);
				button.setText("");
				break;
			case "I" : 
				button.setBackground(I);
				button.setText("");
				break;
			case "A" : 
				button.setBackground(A);
				button.setText("");
				break;
			}
			
			if (_board.getEasterEgg()) {
				switch (_board.getLocations().get(idx).getPerson()) {
				case "R" :
					button.setIcon(new ImageIcon("src/RedHat.png"));
					break;
				case "B" :
					button.setIcon(new ImageIcon("src/BlueHat.png"));
					break;
				case "I" :
					button.setIcon(new ImageIcon("src/GrayHat.png"));
					break;
				case "A" :
					button.setIcon(new ImageIcon("src/GreenHat.png"));
					break;
				}
			}

			button.setFont(this.font);
			button.setOpaque(true);
			button.setBorder(
					BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
		}
		else {
			button.setFont(this.font);
			button.setBackground(Color.WHITE);
			button.setForeground(Color.BLACK);
			button.setOpaque(true);
			button.setBorder(
					BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
		}
	}
	
	/**
	 * Sets the button properties on the user interface.
	 * 
	 * @param button JButton which will act as the submit button.
	 */
	public void setButtonPropertiesSub(JButton button) {
		button.setFont(this.font);
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}

	/**
	 * Sets the font for JLabels.
	 * @param label The JLabel to have its font set
	 */
	public void setLabelProperties(JLabel label) {
		label.setFont(this.font);
	}
	
	/**
	 * Creates a JDialog to indicate turn changes during game play and act as an event listener. 
	 * 
	 * @param message The text to display on the JDialog.
	 * @param windowTitle The title of the JDialog.
	 */
	public void makeDialog(String message, String windowTitle) {
		JDialog dialogWindow = _windowHolder.newDialog(windowTitle);
		dialogWindow.setLayout(new BoxLayout(dialogWindow.getContentPane(), BoxLayout.Y_AXIS));
		dialogWindow.addMouseListener(new DialogClickListener(dialogWindow, _board));
		dialogWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		dialogWindow.setMinimumSize(new Dimension((int) this.screenWidth/4, (int) (screenHeight/4)));

		JPanel dialogMsg = new JPanel();
		dialogMsg.setLayout(new BoxLayout(dialogMsg, BoxLayout.X_AXIS));
		
		dialogMsg.add(Box.createVerticalGlue());
		
		JLabel dialogText = new JLabel(message);
		dialogText.setFont(this.font);

		dialogMsg.add(dialogText);

		dialogWindow.add(dialogMsg);
		
		dialogWindow.pack();
		_windowHolder.locateDialog(dialogWindow);
		
		dialogWindow.setVisible(true);
	}
	
	/**
	 * Builds the GUI for the Spymaster portion of a turn. Includes an instructional message and fields for text entry.
	 */
	public void newTurn() {			
		_messagePanel.removeAll();
		_cluePanel.removeAll();
		_countPanel.removeAll();
		_buttonPanel.removeAll();

		JLabel message;

		if (_board.getRedTurn()) {
			message = new JLabel(Board.redSpymasterMessage);
		}
		else {
			message = new JLabel(Board.blueSpymasterMessage);
		}

		setLabelProperties(message);
		_messagePanel.add(message);
		
		JLabel clueLabel = new JLabel("CLUE: ");
		setLabelProperties(clueLabel);

		_clueField = new JTextField();
		_clueField.setMaximumSize(new Dimension((int) (screenWidth * .4), (int) (screenHeight * .04)));
		_clueField.setFont(this.font);
		
		_cluePanel.add(clueLabel);
		_cluePanel.add(_clueField);

		JLabel countLabel = new JLabel("COUNT: ");
		setLabelProperties(countLabel);

		_countField = new JTextField();
		_countField.setMaximumSize(new Dimension((int) (screenWidth * .4), (int) (screenHeight * .04)));
		_countField.setFont(this.font);

		Document countFieldDoc = _countField.getDocument();
		AbstractDocument absDoc;
		if (countFieldDoc instanceof AbstractDocument) {
			absDoc = (AbstractDocument)countFieldDoc;
			absDoc.setDocumentFilter(new CountFieldDocumentFilter());
		}

		_countPanel.add(countLabel);
		_countPanel.add(_countField);

		JButton submitButton = new JButton("SUBMIT");
		setButtonPropertiesSub(submitButton);
		submitButton.addActionListener(new SubmitClueListener(_board, _clueField, _countField));
		//submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_buttonPanel.add(submitButton);

		updateJFrameIfNotHeadless();
	}
	
	/**
	 * Determines information to display in JDialogs during gameplay and calls makeDialog to create them. 
	 */
	void displayDialog() {
		String dialogMessage;

		if (_board.getRedTurn()) {
			dialogMessage = "RED TEAM'S TURN";
		}
		else {
			dialogMessage = "BLUE TEAM'S TURN";
		}
		
		makeDialog(dialogMessage, "New Turn");
	}
	
	/**
	 * Displays an error message for invalid inputs during the Spymaster's turn.
	 */
	public void submissionError() {
		_messagePanel.removeAll();

		JLabel errorMessage = new JLabel(_board.getErrorMessage());
		setLabelProperties(errorMessage);
		_messagePanel.add(errorMessage);
		
		updateJFrameIfNotHeadless();	
	}
	
	/**
	 * Builds the GUI when a game is over. Displays winning team information and allows users to start a new game or exit.
	 */
	public void GameEndScenario() {
		_messagePanel.removeAll();
		_cluePanel.removeAll();
		_countPanel.removeAll();
		_buttonPanel.removeAll();
		
		if(_board.getBlueCount()==0) {
		JLabel x = new JLabel("Game over. Blue Team Wins. Would you like to play again? ");
			_messagePanel.add(x);
			this.setLabelProperties(x);
		}
		
		if(_board.getRedCount()==0) {
			JLabel x = new JLabel("Game over. Red Team Wins. Would you like to play again?");
			_messagePanel.add(x);
			this.setLabelProperties(x);
		}			
		
		if(_board.getAssassinCount()==0) {
			JLabel x = new JLabel("Oops! Assassin Revealed. Game over. ");
			JLabel y = new JLabel(_board.winTeam() + ". Would you like to play again?");
			_messagePanel.add(x);
			_messagePanel.add(y);
			this.setLabelProperties(x);
			this.setLabelProperties(y);
		}
		
		JButton yes = new JButton("Yes.");
		yes.addActionListener(new NewGameListener(_board));
		setButtonPropertiesSub(yes);
		
		JButton no = new JButton("No, Close Game");
		no.addActionListener(new CloseListener());
		setButtonPropertiesSub(no);
		
		_buttonPanel.add(yes);
		_buttonPanel.add(no);
		updateJFrameIfNotHeadless();
	}
	
	/**
	 * Builds the GUI when a new game is started.
	 */
	public void newGame() {
		_messagePanel.removeAll();
		_cluePanel.removeAll();
		_countPanel.removeAll();
		_buttonPanel.removeAll();
		
		this.updateJFrameIfNotHeadless();
		
		this.displayDialog();
	}
	
	/**
	 * Displays Locations during the Spymaster's turn. Shows characters for unrevealed Locations and has no action listeners.
	 */
	public void SpymasterView() {
		_locationPanel.removeAll();
		
		for (int idx = 0; idx< 25; idx++) {
			JButton b = new JButton("" + _board.getCodenames().get(idx).toUpperCase() + "("+_board.getLocations().get(idx).getPerson()+")");
			setButtonProperties(b, idx);
			_locationPanel.add(b);
		}
	}
	
	/**
	 * Displays Locations during a team's turn. Does not show unrevealed characters and adds action listeners for Locations to be revealed.
	 */
	public void PlayerView() {
		_locationPanel.removeAll();
		
		for (int idx = 0; idx< 25; idx++) {		
			JButton b = new JButton("" + _board.getCodenames().get(idx).toUpperCase());
			setButtonProperties(b, idx);
			if(_board.getLocations().get(idx).getRevealed()==false)
				b.addActionListener(new ButtonListner(this._board, this, _board.getCodenames().get(idx)));
			_locationPanel.add(b);
		}
	}
	
	/**
	 * Builds GUI for a team's turn. Displays the current count, clue, numbers of unrevealed spies, and allows players to end the turn.
	 */
	public void CurrentTurn() {
		_messagePanel.removeAll();
		_cluePanel.removeAll();
		_countPanel.removeAll();
		_buttonPanel.removeAll();
		
		JLabel clueLabel = new JLabel("Success! Clue is: " + "'"+_board.GetSubClue()+"'");
		JLabel countLabel = new JLabel(" and Count is: " + _board.getCount());
		this.setLabelProperties(clueLabel);
		this.setLabelProperties(countLabel);
		_messagePanel.add(clueLabel);
		_messagePanel.add(countLabel);
		
		JLabel RA = new JLabel("RED AGENTS: ");
				setLabelProperties(RA);
				
		JLabel RCount = new JLabel(Integer.toString(_board.getRedCount()));
				setLabelProperties(RCount);
				
		_cluePanel.add(RA);
		_cluePanel.add(RCount);
		
		JLabel BA = new JLabel("BLUE AGENTS: ");
				setLabelProperties(BA);
		
		JLabel BCount = new JLabel(Integer.toString(_board.getBlueCount()));
				setLabelProperties(BCount);
				
		_countPanel.add(BA);
		_countPanel.add(BCount);
		
		JButton passButton = new JButton("PASS");
		setButtonPropertiesSub(passButton);
		passButton.addActionListener(new PassListener(this,_board));
		passButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_buttonPanel.add(passButton);

		updateJFrameIfNotHeadless();
	}	
}
