package clueGame;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

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
	private Set <Card> hand;
	
	public Player(String playerName, int row, int col, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = col;
		this.color = color;
		hand = new HashSet <Card>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		Card c = new Card(playerName, null);
		return c; 
	}
	
	public void dealCard(Card c) {
		//hand.add(c);
	}
	
	public Set <Card> getHand(){
		//return hand;
		return null;
	}
}
