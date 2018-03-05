package clueGame;
/**
 * BadConfigFormatException -- returns an exception if there is an error
 * with the configuration of the board
 * 
 * @author Hannah Lee
 * @author Savannah Paul 
 *
 */
public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {};
	public BadConfigFormatException(String message) {
		super(message);
	}

}
