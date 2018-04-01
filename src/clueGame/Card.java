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
	private Set<Player> people;
	private String cardConfigPeople;
	private String cardConfigWeapons;
	
	
	public Card(String cardName, CardType cardType) {
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
		//we get to design the config file so this will depend on how we do that (name, type, color, start_row, start_col) <- ?
		while(in.hasNextLine()){
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			Card temp = null;
			Player p = null;
			if(legendIn[1] == "Person") {
				temp = new Card(legendIn[0], CardType.PERSON);
				Color c = convertColor(legendIn[2]);
				p = new Player(legendIn[0],Integer.parseInt(legendIn[3]), Integer.parseInt(legendIn[4]), c);
				people.add(p);
			}
			else if(legendIn[1] == "Weapon") {
				temp = new Card(legendIn[0], CardType.WEAPON);
			}
			else if(legendIn[1] == "Room") {
				temp = new Card(legendIn[0], CardType.ROOM);
			}
			else {
				//throw error?
			}
			deck.add(temp);
		}
		in.close();
	}
	
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
	}

	
	public boolean equals() {
		return false; 
	}
}
