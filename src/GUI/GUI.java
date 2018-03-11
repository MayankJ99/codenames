package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import Code.Board;
import GUI.Observer;
import GUI.EventHandlers.*;
import GUI.Driver;

public class GUI implements Observer {

	private JPanel _locationPanel;
	private Board _board;
	private Driver _windowHolder;

	public GUI(Board b, JPanel mp, Driver driver) {
		_windowHolder = driver;
		_board = b;
		
		JPanel _mainPanel = mp;
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		_locationPanel = new JPanel();
		_locationPanel.setLayout(new GridLayout(5, 5));
		_mainPanel.add(_locationPanel);
		
		_board.gameStart();
		_board.addObserver(this);
	}
	
	@Override
	public void update() {
		
		_locationPanel.removeAll();
		for (int a = 0; a < 25; a++) {
			JButton b = new JButton("" + _board.getLocations().get(a).getCodename().toUpperCase());
			setButtonProperties(b);
			_locationPanel.add(b);
			//b.addActionListener(new locationButtonHandler(_board, a));
		}
		
		// This should be last statement of this method:
		updateJFrameIfNotHeadless();
	}
	
	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}

	public void setButtonProperties(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 44));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}

	public void setLabelProperties(JLabel label) {
		label.setFont(new Font("Courier", Font.BOLD, 44));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
}
