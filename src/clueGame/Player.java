package clueGame;
import java.awt.Color;

/**
 * Player -- basic class that creates player
 * @author Hannah Lee
 * @author Savannah Paul
 *
 */
public class Player {
	private String playerName; 
	private int row; 
	private int column; 
	private Color color;
	
	public Player(String playerName, int row, int col, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = col;
		this.color = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Card c = new Card(playerName, null);
		return c; 
	}
}
