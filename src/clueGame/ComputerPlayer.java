package clueGame;
import java.awt.Color;
import java.util.*; 

/**
 * ComputerPlayer -- class for computer players
 * @author Hannah Lee
 * @author Savannah Paul
 */
public class ComputerPlayer extends Player{
	
	// Computer Player Constructor
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
		// TODO Auto-generated constructor stub
	}

	// starting location of player
	public BoardCell pickLocation(Set <BoardCell> targets) {
		BoardCell temp = new BoardCell(0,0);
		return temp; 
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
		
	}
	public void setLastRoom(char c) {
		// TODO Auto-generated method stub
		
	}
}
