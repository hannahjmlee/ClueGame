package tests;

import static org.junit.Assert.*;
import java.io.*;

import org.junit.*;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class gameSetupTests {
	private static Board board;
	
	public static final int DECK_SIZE = 21; 
	public static final int ROOM_CARD_SIZE = 9; 
	public static final int PERSON_CARD_SIZE = 6; 
	public static final int WEAPON_CARD_SIZE = 6; 
	
	@BeforeClass
	public static void setUp () throws IOException{
		board = Board.getInstance();
		board.setConfigFiles("ClueGameRooms.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
	}
	
	@Test
	public void loadPeople() {
		// check map for starting color 
		assertEquals("Green", board.getPeopleColors().get("Trashy Tracy"));
		assertEquals("Red", board.getPeopleColors().get("Sinful Syndie")); 
		assertEquals("Blue", board.getPeopleColors().get("Deluxe Dolly"));
		
		// checks map for starting location
		BoardCell b = new BoardCell(4, 19);
		assertEquals(b, board.getPeopleStartLoc().get("Batty Betty")); 
		b = new BoardCell(9, 3);
		assertEquals(b, board.getPeopleStartLoc().get("Wacky Wendy")); 
		b = new BoardCell(15, 12); 
		assertEquals(b, board.getPeopleStartLoc().get("Lusty Lucy")); 
	}
	
	@Test
	public void completeDeck() {
		// Check size of the deck
		assertEquals(DECK_SIZE, board.getDeck().size()) ;
		int r = 0; 
		int p = 0; 
		int w = 0; 
		
		for (Card c : board.getDeck()) {
			if (c.getCardType() == CardType.ROOM)
				r++;
			else if (c.getCardType() == CardType.PERSON)
				p++; 
			else if (c.getCardType() == CardType.WEAPON)
				w++; 
		}
		
		// Check number of room cards in deck
		assertEquals(ROOM_CARD_SIZE, r);
		// Check number of person cards in deck
		assertEquals(PERSON_CARD_SIZE, p);
		// Check number of weapon cards in deck
		assertEquals(WEAPON_CARD_SIZE, w);
		
		// Check one room within the deck
		Card c = new Card("Morgue", CardType.ROOM); 
		assertTrue((board.getDeck()).contains(c)); 
		// Check one person within the deck
		c = new Card("Lusty Lucy", CardType.PERSON);
		assertTrue((board.getDeck()).contains(c));
		// check one weapon within the deck
		c = new Card("Frayed Extension Cord", CardType.WEAPON);
		assertTrue((board.getDeck()).contains(c));
	}
}

