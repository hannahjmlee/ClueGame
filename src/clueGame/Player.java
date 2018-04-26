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
	
	/**
	 * Player -- constructor for player class
	 * @param playerName -- name of player
	 * @param row -- row position
	 * @param col -- column position
	 * @param color -- color of player
	 */
	public Player(String playerName, int row, int col, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = col;
		this.color = color;
		hand = new HashSet <Card>();
		possibleCards = new ArrayList<Card>(); 
	}

	/**
	 * disproveSuggestion - Allows a player to disprove a suggestion
	 * by revealing the cards they have
	 * @param suggestion -- provided suggestion
	 * @return -- card that disproves suggestion
	 */
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
	
	/**
	 * dealCard -- adds provided card to player's hand
	 * @param c -- card to add
	 */
	public void dealCard(Card c) {
		hand.add(c);
	}
	
	/**
	 * clear -- clears player's hand
	 */
	public void clear() {
		this.hand.clear();
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++
	// GETTERS/SETTERS ----------------------------------
	// ++++++++++++++++++++++++++++++++++++++++++++++++++
	public Set <Card> getHand(){
		return hand;
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return color;
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

	public void addToPossibleCards(Card c) {
		possibleCards.add(c); 
	}
	
	public char getLastRoom() {
		return 0;
	}	
}
