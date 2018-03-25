package GUI;

import Code.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SubmitClueListener implements ActionListener {
	private Board board;
	private JTextField clueField;
	private JTextField countField;
	
	public SubmitClueListener(Board board, JTextField clueField, JTextField countField) {
		this.board = board;
		this.clueField = clueField;
		this.countField = countField;
	}
	
	public void actionPerformed(ActionEvent ae) {
		this.board.entriesSubmitted(this.clueField.getText(), this.countField.getText());
	}
}
