package GUI;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CountFieldDocumentFilter extends DocumentFilter {
	public CountFieldDocumentFilter() {}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
		String newString = new String();
		System.out.println("In insertString");
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) >= 48 && str.charAt(i) <= 57)
				newString += String.valueOf(str.charAt(i));
		if (newString.length() > 0)
			super.insertString(fb, offset, newString, attr);
		else
			Toolkit.getDefaultToolkit().beep();
	}
	
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
