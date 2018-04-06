package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JDialog;

import Code.Board;
import Code.Location;

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
	
	private Color buttonColor;
	private static Integer idx;
	

	public GUI(Board b, JPanel mp, Driver driver) {
		_windowHolder = driver;
		_board = b;
		
		
		
		_mainPanel = mp;
		_mainPanel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent me) {
				_board.gameStart();
//				_mainPanel.removeMouseListener(this);
				//System.out.println(_windowHolder.);
//				System.out.println(_mainPanel.getSize());
//				System.out.println(_locationPanel.getSize());
//				System.out.println(_infoPanel.getSize());
//				System.out.println(_messagePanel.getSize());
//				System.out.println(_cluePanel.getSize());
//				System.out.println(_countPanel.getSize());
//				System.out.println(_buttonPanel.getSize() + "\n");
			}
			public void mouseExited(MouseEvent me) {}
			public void mouseEntered(MouseEvent me) {}
			public void mousePressed(MouseEvent me) {}
			public void mouseReleased(MouseEvent me) {}
		});
		
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		
		
		
		_locationPanel = new JPanel();
		_locationPanel.setLayout(new GridLayout(5, 5));
		_locationPanel.setPreferredSize(new Dimension((int) (this.screenWidth * .6), (int) (this.screenHeight * .6)));
		_mainPanel.add(_locationPanel);
		
		_infoPanel = new JPanel();
		_infoPanel.setLayout(new BoxLayout(_infoPanel, BoxLayout.Y_AXIS));
		_infoPanel.setPreferredSize(new Dimension((int) (this.screenWidth * .6), (int) (this.screenHeight * .2)));
		_infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  	//When _infoPanel is assigned a BoxLayout, its alignment changes from CENTER to LEFT. Rhetorical WHY?????
		_mainPanel.add(_infoPanel);								//Further, when a container (NOT component) is added, its alignment hanges back to CENTER. 
		
		_messagePanel = new JPanel();
//		_messagePanel.setLayout(new BoxLayout(_messagePanel, BoxLayout.Y_AXIS));
//		_messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//		_messagePanel.setPreferredSize(new Dimension((int) (screenWidth * .6), (int) screenHeight)); //(screenHeight))); // * .05)));
		_messagePanel.setMaximumSize(new Dimension((int) (screenWidth * .6), (int) (screenHeight))); // * .05)));
		_messagePanel.setMinimumSize(new Dimension((int) (screenWidth * .6), (int) 0)); // screenWidth * .6 (screenHeight * .05))); //This is here to prevent *slight* realignment of the fields if the JLabel it contains is not as wide as the JFields below
		_infoPanel.add(_messagePanel);
		
		_cluePanel = new JPanel();
		_cluePanel.setLayout(new BoxLayout(_cluePanel, BoxLayout.X_AXIS));
		_cluePanel.setMaximumSize(new Dimension((int) (screenWidth * .25), (int) (screenHeight))); // * .05)));
		_infoPanel.add(_cluePanel);
		
		_countPanel = new JPanel();
		_countPanel.setLayout(new BoxLayout(_countPanel, BoxLayout.X_AXIS));
		_countPanel.setMaximumSize(new Dimension((int) (screenWidth * .25), (int) (screenHeight))); // * .05)));
		_infoPanel.add(_countPanel);
		
		_buttonPanel = new JPanel();
		_buttonPanel.setLayout(new BoxLayout(_buttonPanel, BoxLayout.Y_AXIS));
		_buttonPanel.setMaximumSize(new Dimension((int) (screenWidth * .6), (int) (screenHeight))); // * .05)));
		//_buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_infoPanel.add(_buttonPanel);
				
//		_messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		_infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		_cluePanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		_countPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//		_buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		_board.addObserver(this);
		updateJFrameIfNotHeadless();
	}
	
	@Override
	public void update() {
		_locationPanel.removeAll();
		
		for (int idx = 0; idx< 25; idx++) {
			this.idx = idx;

			JButton b = new JButton("" + _board.getCodenames().get(idx).toUpperCase());
			//b.setBackground(buttonColor);
			//b.setForeground(Color.WHITE);
			setButtonProperties(b, idx);
			b.addActionListener(new ButtonListner(this._board, this, _board.getCodenames().get(idx)));
			_locationPanel.add(b);
		}
		
		if (_board.getCount() == -1)
			if (_board.getNewTurn())
				newTurn();
			else
				submissionError();
		else
		{
			//_infoPanel.removeAll();
			_messagePanel.removeAll();
			_messagePanel.add(new JLabel("Success Count: " + _board.getCount()));

			_messagePanel.removeAll();
			_messagePanel.add(new JLabel("Success Count: " + _board.getCount()));
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
		
		// This should be last statement of this method:
//		updateJFrameIfNotHeadless();
	}
	
	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}

	public void setButtonProperties(JButton button, int idx) {
		Color R = new Color(255, 0, 0);
		Color B = new Color(0, 0, 225);
		Color I = new Color(192,192,192);
		Color A = new Color(128,128,0);
		
		

		
			if (_board.getLocations().get(idx).getRevealed() == true) {
				switch (_board.getLocations().get(idx).getPerson()) {
				case "R" : button.setBackground(R);
				break;
				case "B" : button.setBackground(B);
				break;
				case "I" : button.setBackground(I);
				break;
				case "A" : button.setBackground(A);
				break;
			}
				
				
				button.setFont(new Font("Courier", Font.BOLD, (int) (screenHeight * 0.03)));
				button.setOpaque(true);
				button.setBorder(
						BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
				

			} else {
				button.setFont(this.font);
				button.setBackground(Color.WHITE);
				button.setForeground(Color.BLACK);
				button.setOpaque(true);
				button.setBorder(
						BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));

			}
	//	}
		
		
		
	}
	
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
		//label.setFont(new Font("Courier", Font.BOLD, 32));
		//label.setBackground(Color.WHITE);
		//label.setForeground(Color.BLACK);
		//label.setOpaque(true);
		//label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
	
	public void makeDialog(String message, String windowTitle) {
		JDialog dialogWindow = _windowHolder.newDialog(windowTitle);
		dialogWindow.setLayout(new BoxLayout(dialogWindow.getContentPane(), BoxLayout.Y_AXIS));
		dialogWindow.addMouseListener(new DialogClickListener(dialogWindow));
		
		dialogWindow.setMinimumSize(new Dimension((int) this.screenWidth/4, (int) (screenHeight/4)));

		JPanel dialogMsg = new JPanel();
		dialogMsg.setLayout(new BoxLayout(dialogMsg, BoxLayout.X_AXIS));
		
		dialogMsg.add(Box.createVerticalGlue());
		
		JLabel dialogText = new JLabel(message);
		dialogText.setFont(this.font);

		dialogMsg.add(Box.createRigidArea(new Dimension((int)(this.screenWidth/100),0)));
		dialogMsg.add(dialogText);
		dialogMsg.add(Box.createRigidArea(new Dimension((int)(this.screenWidth/100),0)));

		dialogWindow.add(dialogMsg);
		
//		dialogWindow.getContentPane().setBackground(Color.BLUE);
//		dialogMsg.setOpaque(false);
		
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
		String dialogMessage;

		if (_board.getRedTurn()) {
			dialogMessage = "RED TEAM'S TURN";
			message = new JLabel(Board.redSpymasterMessage);
		}
		else {
			dialogMessage = "BLUE TEAM'S TURN";
			message = new JLabel(Board.blueSpymasterMessage);
		}

		setLabelProperties(message);
		_messagePanel.add(message);
		
		JLabel clueLabel = new JLabel("CLUE: ");
		setLabelProperties(clueLabel);

		_clueField = new JTextField();
		_clueField.setMaximumSize(new Dimension((int) (screenWidth * .25), (int) (screenHeight * .04)));
		_clueField.setFont(this.font);
		
		_cluePanel.add(clueLabel);
		_cluePanel.add(_clueField);

		JLabel countLabel = new JLabel("COUNT: ");
		setLabelProperties(countLabel);

		_countField = new JTextField();
		_countField.setMaximumSize(new Dimension((int) (screenWidth * .25), (int) (screenHeight * .04)));
		_countField.setFont(this.font);

		//////////////////////////////////// DOCUMENT FILTER /////////////////////////////////////////////
		Document countFieldDoc = _countField.getDocument();
		AbstractDocument absDoc;
		if (countFieldDoc instanceof AbstractDocument) {
			absDoc = (AbstractDocument)countFieldDoc;
			absDoc.setDocumentFilter(new CountFieldDocumentFilter());
		}
		//////////////////////////////////// DOCUMENT FILTER ////////////////////////////////////////////

		_countPanel.add(countLabel);
		_countPanel.add(_countField);

		JButton submitButton = new JButton("SUBMIT");
		setButtonPropertiesSub(submitButton);
		submitButton.addActionListener(new SubmitClueListener(_board, _clueField, _countField));
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		_buttonPanel.add(submitButton);

		updateJFrameIfNotHeadless();
		
		makeDialog(dialogMessage, "New Turn");
	}
	
	public void submissionError() {
		_messagePanel.removeAll();
		if (!_board.checkCount(_countField.getText())) {
			JLabel errorMessage = new JLabel(Board.countError);
			setLabelProperties(errorMessage);
			_messagePanel.add(errorMessage);
		}
		else if (!_board.checkClue(_clueField.getText())) {
			JLabel errorMessage = new JLabel(Board.clueError);
			setLabelProperties(errorMessage);
			_messagePanel.add(errorMessage);
		}
		updateJFrameIfNotHeadless();	
	}
	
	public Color getButtonColor() {
		return this.buttonColor;
	}
	public void setButtonColor(Color rgb) {
		this.buttonColor = rgb;
	}
	public static int getIdx() {
		return idx;
	}
}
