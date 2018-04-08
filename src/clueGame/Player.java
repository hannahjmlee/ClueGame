package clueGame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
	public Set<Card> hand;
	public static ArrayList<Card> possibleCards;
	
	public Player(String playerName, int row, int col, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = col;
		this.color = color;
		hand = new HashSet <Card>();
	}

	public Card disproveSuggestion(Solution suggestion) {
		Random rand = new Random();
		ArrayList <Card> matches = new ArrayList<Card>();
		for(Card c : this.hand) {
			if(c.getCardName().equals(suggestion.person)) {
				matches.add(c);
			}
			else if(c.getCardName().equals(suggestion.weapon)) {
				matches.add(c);
			}
			else if(c.getCardName().equals(suggestion.room)) {
				matches.add(c);
			}
		}
		if(matches.size() == 0) {
			return null;
		}
		int index = rand.nextInt(matches.size());
		return matches.get(index);
	}
	
	public void dealCard(Card c) {
		hand.add(c);
	}
	
	public Set <Card> getHand(){
		return hand;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setCol(int c) {
		column = c;
	}
	
	public void setPossibleCards(ArrayList<Card> arr) {
		possibleCards = arr;
	}
	
}
