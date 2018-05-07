package GUI;

/**
 * Interface for use in Java GUI applications.
 * 
 * @author Mayank Jha
 * @author Minseo Kim
 * @author Juan Mendoza
 * @author Daniel Walsh
 */
public interface Observer {
	
	/**
	 * Prompts a GUI to repaint itself to reflect changes in its content
	 */
	public void update();
}
