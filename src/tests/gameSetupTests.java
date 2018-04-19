/**
 * gameSetupTests -- tests the setup of all the decks, cards, characters, and the dealing
 * of cards. 
 * 
 * @author Hannah Lee
 * @author Savannah Paul
 */
package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.*;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

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
		board.dealCards();
	}
	
	/**
	 * loadPeople test -- this tests the color and starting location of the people
	 * read in from text file containing all possible characters. The first set of tests
	 * check the colors of three characters. Then the second set of tests check the starting
	 * locations (by comparing row/col values of the board cell) of the remaining three
	 * characters. 
	 * 
	 * We created two separate maps <key: name, value: color/location> to hold all of the
	 * information. 
	 */
	@Test
	public void loadPeople() {
		// check map for character's corresponding color 
		assertEquals(Color.GREEN, board.getPeopleColors().get("Trashy Tracy"));
		assertEquals(Color.RED, board.getPeopleColors().get("Sinful Syndie")); 
		assertEquals(Color.BLUE, board.getPeopleColors().get("Deluxe Dolly"));
		
		// checks map for character's corresponding starting location (row/col values)
		assertEquals(5, board.getPeopleStartLoc().get("Batty Betty").getRow());
		assertEquals(18, board.getPeopleStartLoc().get("Batty Betty").getCol()); 
		assertEquals(9, board.getPeopleStartLoc().get("Wacky Wendy").getRow());
		assertEquals(5, board.getPeopleStartLoc().get("Wacky Wendy").getCol());
		assertEquals(14, board.getPeopleStartLoc().get("Lusty Lucy").getRow());
		assertEquals(12, board.getPeopleStartLoc().get("Lusty Lucy").getCol());
	}
	
	/**
	 * completeDeck test -- tests whether or not the deck has correctly read in all the 
	 * cards. First test checks the size of the deck. Second test checks the number of
	 * room, person, and weapon cards. Third test checks whether or not the deck contains
	 * one of each type of card. 
	 */
	@Test
	public void completeDeck() {
		Set <Card> deck = board.getDeck(); 
		
		// Test 1: Check size of the deck
		assertEquals(DECK_SIZE, deck.size()) ;
		
		// Test 2: Check number of room, person, and weapon cards.
		int r = 0; 
		int p = 0; 
		int w = 0; 
		
		for (Card c : deck) {
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
		
		// Test 3: Check that the deck contains one of each card to test loading of names 
		int count = 0;  // will keep track of how many of the tested cards are valid
		for (Card c : deck) {
			// Check one room within the deck
			if (c.getCardName().equals("Morgue") && c.getCardType() == CardType.ROOM)
				count++; 
			// Check one person within the deck
			else if (c.getCardName().equals("Lusty Lucy") && c.getCardType() == CardType.PERSON)
				count++;
			// Check one weapon within the deck
			else if (c.getCardName().equals("Frayed Extension Cord") && c.getCardType() == CardType.WEAPON)
				count++;
		}
		assertEquals(3, count); 
	}
	
	/**
	 * testPlayerHandSize -- test that each players hand is roughly the same size (within 1 card of eachother)
	 */
	@Test
	public void testPlayerHandSize() {
		//test that players have about the same number of cards
		ArrayList <Player> players = board.getPlayers();
		Set <Card> deck = board.getDeck();
		double handSize = deck.size() / players.size();		//rough distribution of cards
		for(Player p : players) {
			assertEquals(handSize, p.getHand().size(), 1);	//compares size with a tolerance of 1
			for (Card c:p.getHand()) {				
				System.out.println(c.getCardName()); 
			}
		}
	}
	/**
	 * testAllCardsDelt -- this test ensures that all of the cards from the deck were dealt 
	 * as well as checks that each card is present after being dealt
	 */
	@Test
	public void testAllCardsDelt() {
		ArrayList <Player> players = board.getPlayers();
		Set <Card> deck = board.getDeck();
		Set <Card> fullDeck = new HashSet <Card>();
		int numDealt = 0;
		for(Player p : players) {
			numDealt += p.getHand().size();
			for(Card c : p.getHand()) {
				fullDeck.add(c);	//keeps track of all the cards dealt
			}
		}
		//check that the entire deck was dealt
			assertEquals(numDealt, deck.size());
		//check that each card was dealt
		assertEquals(fullDeck.size(), deck.size());
		for(Card c : deck) {
			assertTrue(fullDeck.contains(c));
		}
		
	}
	
}

