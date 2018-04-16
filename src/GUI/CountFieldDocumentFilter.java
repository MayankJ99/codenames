package GUI;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * This class is a DocumentFilter for the count entry JTextField in codenames. It prevents invalid entries from being entered.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public class CountFieldDocumentFilter extends DocumentFilter {
	
	/**
	 * Constructor
	 */
	public CountFieldDocumentFilter() {}
	
	/**
	 * Examines the String of characters entered into the count JTextField via the insertString method. Any characters that are not '0' 
	 * through '9' are removed, and any remaining characters are passed to the JTextField Document. They will then appear on the screen.
	 * If no characters are to be passed to the Document, no valid characters were entered, and the system will beep to indicate invalid inputs.
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
		String newString = new String();
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) >= 48 && str.charAt(i) <= 57)
				newString += String.valueOf(str.charAt(i));
		if (newString.length() > 0)
			super.insertString(fb, offset, newString, attr);
		else
			Toolkit.getDefaultToolkit().beep();
	}
	
	/**
	 * Examines the String of characters entered into the count JTextField via the replace method. Any characters that are not '0' 
	 * through '9' are removed, and any remaining characters are passed to the JTextField Document. They will then appear on the screen.
	 * If no characters are to be passed to the Document, no valid characters were entered, and the system will beep to indicate invalid inputs.
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attr) throws BadLocationException {
		String newString = new String();
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) >= 48 && str.charAt(i) <= 57)
				newString += String.valueOf(str.charAt(i));
		if (newString.length() > 0)
			super.replace(fb, offset, length, newString, attr);
		else
			Toolkit.getDefaultToolkit().beep();
	}
}
