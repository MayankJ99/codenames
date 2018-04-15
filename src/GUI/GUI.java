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

public class GUI implements Observer {

	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	private Font font = new Font("Courier", Font.BOLD, (int) (screenHeight * 0.03));
	
	private JPanel _mainPanel;
	private JPanel _locationPanel;
	private JPanel _infoPanel;
	
	private JPanel _messagePanel;
	private JPanel _cluePanel;
	private JPanel _countPanel;
	private JPanel _buttonPanel;
	
	private JTextField _clueField;
	private JTextField _countField;
	
	private Board _board;
	private Driver _windowHolder;
	

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
		_infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  	//When _infoPanel is assigned a BoxLayout, its alignment changes from CENTER to LEFT. Rhetorical WHY?????
		_mainPanel.add(_infoPanel);								//Further, when a container (NOT component) is added, its alignment hanges back to CENTER. 
		
		_messagePanel = new JPanel();
		_messagePanel.setMaximumSize(new Dimension((int) (screenWidth * .8), (int) (screenHeight))); // * .05)));
		_messagePanel.setMinimumSize(new Dimension((int) (screenWidth * .8), (int) 0)); // screenWidth * .6 (screenHeight * .05))); //This is here to prevent *slight* realignment of the fields if the JLabel it contains is not as wide as the JFields below
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
	
	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}
	
	/**
	 * Sets the button properties for all the buttons on the board. 
	 * Checks if a button is revealed and updates the color of the button.
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
	 * Sets the button properties for the submit button.
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

	public void setLabelProperties(JLabel label) {
		label.setFont(this.font);
	}
	
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
	
	public void submissionError() {
		_messagePanel.removeAll();

		JLabel errorMessage = new JLabel(_board.getErrorMessage());
		setLabelProperties(errorMessage);
		_messagePanel.add(errorMessage);
		
		updateJFrameIfNotHeadless();	
	}
	
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
	
	public void newGame() {
		_messagePanel.removeAll();
		_cluePanel.removeAll();
		_countPanel.removeAll();
		_buttonPanel.removeAll();
		
		this.updateJFrameIfNotHeadless();
		
		this.displayDialog();
	}
	
	public void SpymasterView() {
		_locationPanel.removeAll();
		
		for (int idx = 0; idx< 25; idx++) {
			JButton b = new JButton("" + _board.getCodenames().get(idx).toUpperCase() + "("+_board.getLocations().get(idx).getPerson()+")");
			setButtonProperties(b, idx);
			_locationPanel.add(b);
		}
	}
	
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
