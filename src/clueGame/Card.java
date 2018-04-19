package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.lang.reflect.Field;
import java.awt.Color;
/**
 * Card -- template for all cards
 * @author Hannah Lee
 * @author Savannah Paul
 */
public class Card {
	private String cardName; 
	private CardType cardType;
	private Set<Card> deck;
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public boolean equals() {
		return false; 
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}
	
	
	
	
}
