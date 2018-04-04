package clueGame;
import java.awt.Color;
import java.util.*; 

/**
 * ComputerPlayer -- class for computer players
 * @author Hannah Lee
 * @author Savannah Paul
 */
public class ComputerPlayer extends Player{
	private char lastRoom; 
	public static Solution lastSuggestion;
	
	
	// Computer Player Constructor
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
		lastRoom = '0'; 
		lastSuggestion = new Solution(); 
	}

	
	// starting location of player
	public BoardCell pickLocation(Set <BoardCell> targets) {
		for (BoardCell c : targets) {
			if (c.isDoorway() && !(c.getInitial() == lastRoom)) { // not in last room
				setLastRoom(c); 
				return c; 
			}
		}
		Random random = new Random(); 
		int r = random.nextInt(targets.size());
		
		int i = 0; 
		for (BoardCell c : targets) {
			if (r == i) {
				if (c.isDoorway())
					setLastRoom(c);
				return c; 
			}
			i++; 
		}
		
		return null; 
	}
	
	// allows player to make an accusation
	public void makeAccusation() {
		// to be implemented later
	}
	
	// allows player to make a suggestion
	public void createSuggestion() {
		// to be implemented later
	}

	public void setLastRoom(BoardCell c) {
		lastRoom = c.getInitial(); 
	}
	public void setLastRoom(char c) {
		lastRoom = c;
	}
}
