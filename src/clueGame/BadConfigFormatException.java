package clueGame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BadConfigFormatException -- returns an exception if there is an error
 * with the configuration of the board and writes to a file
 * 
 * @author Hannah Lee
 * @author Savannah Paul 
 *
 */
public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {
		super("Error: Configuration File");
	};
	public BadConfigFormatException(String message) throws IOException {
		super(message);
		FileWriter fw = new FileWriter("logfile.txt");
		PrintWriter printw = new PrintWriter(fw);
		printw.printf(message);
		printw.close();
	}

}
