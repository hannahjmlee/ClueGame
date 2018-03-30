package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Card -- template for all cards
 * @author Hannah Lee
 * @author Savannah Paul
 */
public class Card {
	private String cardName; 
	private CardType cardType;
	private Set<Card> deck;
	private String cardConfigPeople;
	private String cardConfigWeapons;
	
	
	private Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public void setConfigFiles(String string, String string2) {
		cardConfigPeople = string;
		cardConfigWeapons = string2;
		return; 
	}
	
	private void generateDeck() throws FileNotFoundException {
		deck = new HashSet<Card>();
		FileReader reader = new FileReader("src/data/"+cardConfigPeople);
		Scanner in = new Scanner(reader);
		//we get to design the config file so this will depend on how we do that (name, type)
		while(in.hasNextLine()){
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			Card person = null;
			if(legendIn[1] == "Person") {
				person = new Card(legendIn[0], CardType.PERSON);
			}
			else if(legendIn[1] == "Weapon") {
				person = new Card(legendIn[0], CardType.WEAPON);
			}
			else if(legendIn[1] == "Room") {
				person = new Card(legendIn[0], CardType.ROOM);
			}
			else {
				//throw error?
			}
			deck.add(person);
		}
		in.close();
	}
	
	public boolean equals() {
		return false; 
	}
}
