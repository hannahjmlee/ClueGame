package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;

import org.junit.*; 
import java.util.*; 

import clueGame.*;

public class gameActionTests {
	private static Board board;
	Set <BoardCell> targets; 
	private ComputerPlayer comp; 
	private HumanPlayer hum;

	@BeforeClass
	public static void setUp () throws IOException{
		board = Board.getInstance();
		board.setConfigFiles("ClueGameRooms.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
		board.dealCards();
	}

	/**
	 * selectTarget -- this tests the computer player's ability to choose a target randomly. There are
	 * three tests: 1) choosing a target when no door is present, 2) choosing a target when there is a door
	 * 3) choosing a target when we just left a room
	 */
	@Test
	public void selectTarget() {

		targets = new HashSet <BoardCell>();
		comp = new ComputerPlayer("Catty Cassie", 16, 9, Color.CYAN); 

		// Test selection when there is no door present, just walkways ------------	
		board.calcTargets(comp.getRow(), comp.getCol(), 2);

		boolean loc_14_9 = false;
		boolean loc_18_9 = false; 
		boolean loc_17_10 = false; 
		boolean loc_15_10 = false; 
		boolean loc_16_11 = false; 
		boolean loc_15_8 = false; 

		targets = board.getTargets();

		for (int i = 0; i < 100; i++) {
			BoardCell selected = comp.pickLocation(targets); 
			if (selected == board.getCellAt(14, 9))
				loc_14_9 = true; 
			else if (selected == board.getCellAt(18, 9))
				loc_18_9 = true;
			else if (selected == board.getCellAt(17, 10))
				loc_17_10 = true;
			else if (selected == board.getCellAt(15, 10))
				loc_15_10 = true;
			else if (selected == board.getCellAt(16, 11))
				loc_16_11 = true;
			else if (selected == board.getCellAt(15, 8))
				loc_15_8 = true;
			else
				fail("Invalid target selected"); 
		}

		assertTrue(loc_14_9); 
		assertTrue(loc_18_9);
		assertTrue(loc_17_10);
		assertTrue(loc_15_10);
		assertTrue(loc_16_11);
		assertTrue(loc_15_8);

		// Test Selection when there is a door present -------------------------
		comp = new ComputerPlayer("Catty Cassie", 6, 18, Color.CYAN); 
		board.calcTargets(comp.getRow(), comp.getCol(), 2);
		targets = board.getTargets(); 
		boolean loc_5_18 = false;
		for (int i = 0; i < 100; i++) {
			BoardCell selected = comp.pickLocation(targets);
			if (selected == board.getCellAt(5,  18)) {
				loc_5_18 = true;
				comp.setLastRoom('0');
			}			 
		}
		assertTrue(loc_5_18); 


		//Test random selection when you just left a room -----------------------
		comp = new ComputerPlayer("Catty Cassie", 16, 4, Color.CYAN);
		board.calcTargets(comp.getRow(), comp.getCol(), 1); 
		boolean loc_16_3 = false;
		boolean loc_16_5 = false;
		boolean loc_15_4 = false;
		boolean loc_17_4 = false;
		comp.setLastRoom(board.getCellAt(16, 4));

		for (int i = 0; i < 100; i++) {
			BoardCell selected = comp.pickLocation(board.getTargets()); 
			if (selected == board.getCellAt(16, 3))
				loc_16_3 = true; 
			else if (selected == board.getCellAt(16, 5))
				loc_16_5 = true;
			else if (selected == board.getCellAt(15, 4))
				loc_15_4 = true;
			else if (selected == board.getCellAt(17, 4))
				loc_17_4 = true;
			else
				fail("Invalid target selected"); 
		}

		assertTrue(loc_16_3);
		assertTrue(loc_16_5);
		assertTrue(loc_15_4);
		assertTrue(loc_17_4);


	}

	/**
	 * checkAccusation -- tests accusation by checking correct and incorrect accusations
	 */
	@Test
	public void checkAccusation() {
		// Set up solution
		board.solution.person = "Trashy Tracy";
		board.solution.weapon = "Frayed Extension Cord";
		board.solution.room = "Morgue";

		// Correct accusation
		Solution testSolution = new Solution("Trashy Tracy", "Frayed Extension Cord", "Morgue"); 
		assertTrue(board.checkAccusation(testSolution));

		// Wrong person
		testSolution = new Solution("Lusty Lucy", "Frayed Extension Cord", "Morgue"); 
		assertFalse(board.checkAccusation(testSolution));

		// Wrong Weapon
		testSolution = new Solution("Trashy Tracy", "Rusted Spoon", "Morgue"); 
		assertFalse(board.checkAccusation(testSolution));

		// Wrong Room
		testSolution = new Solution("Trashy Tracy", "Frayed Extension Cord", "Pool"); 
		assertFalse(board.checkAccusation(testSolution));

		// Wrong everything
		testSolution = new Solution("Lusty Lucy", "Rusted Spoon", "Pool");
		assertFalse(board.checkAccusation(testSolution));
	}
	
	/**
	 * createSuggestion -- NEED EXPLANATION
	 */
	@Test
	public void createSuggestion() {
		// Setting up tests by creating possible cards by removing solution cards, creates forced suggestion
		ComputerPlayer cplayer = (ComputerPlayer) board.getPlayers().get(1); 
		
		cplayer.setRow(1);
		cplayer.setCol(2);
		
		ArrayList <Card> possibleCards = new ArrayList <Card>();
		for(Card c : board.getPlayerCards()) {
			possibleCards.add(c);
		}
		for(Card c : board.getWeaponCards()) {
			possibleCards.add(c);
		}
		
		ArrayList <Card> removeCards = new ArrayList <Card>();
		for(Card c : possibleCards) {
			if(c.getCardName().equals(board.solution.person)) {
				removeCards.add(c);
			}
			if(c.getCardName().equals(board.solution.weapon)) {
				removeCards.add(c);
			}
		}
		for(Card c : removeCards) {
			possibleCards.remove(c);
		}
		
		cplayer.setPossibleCards(possibleCards);
		
		cplayer.makeSuggestion(board, board.getCellAt(cplayer.getRow(), cplayer.getCol()));
		assertTrue(cplayer.getLastSuggestion().room.equals(board.getRooms().get('G')));
		assertTrue(cplayer.getLastSuggestion().person.equals(board.solution.person));
		assertTrue(cplayer.getLastSuggestion().weapon.equals(board.solution.weapon));

		
		
		possibleCards = new ArrayList <Card>();
		for(Card c : board.getPlayerCards()) {
			possibleCards.add(c);
		}
		for(Card c : board.getWeaponCards()) {
			possibleCards.add(c);
		}
		
		removeCards = new ArrayList <Card>();
		for(Card c : possibleCards) {
			if(c.getCardName().equals(board.solution.person)) {
				removeCards.add(c);
			}
			if(c.getCardName().equals(board.solution.weapon)) {
				removeCards.add(c);
			}
		}
		
		for(Card c : removeCards) {
			possibleCards.remove(c);
		}
		
		
		Card extra = new Card("null", null); 
		
		for(Card c : board.getPlayerCards()) {
			if(c.getCardType().equals(CardType.PERSON) && possibleCards.contains(c)) {
				extra = c;
				possibleCards.remove(c);
				break;
			}
		}
		
		cplayer.setPossibleCards(possibleCards);
		
		boolean solPerson = false;
		boolean extraPerson = false;
		
		for(int i = 0; i < 100; i++) {
			
			cplayer.makeSuggestion(board, board.getCellAt(cplayer.getRow(), cplayer.getCol()));
			
			if(cplayer.getLastSuggestion().person.equals(board.solution.person)) {
				solPerson = true;
			}
			else if(cplayer.getLastSuggestion().person.equals(extra.getCardName())) {
				extraPerson = true;
			}
			
			assertTrue(cplayer.getLastSuggestion().weapon.equals(board.solution.weapon));
			assertTrue(cplayer.getLastSuggestion().room.equals(board.getRooms().get('G')));
		}
		
		assertTrue(solPerson);
		assertTrue(extraPerson);
		

	}
	
	/**
	 * disproveSuggestion - 
	 */
	@Test
	public void disproveSuggestion() {
		
		ComputerPlayer cp = new ComputerPlayer("cpu", 8, 5, Color.blue);
		
		Card personOne = new Card ("p1", CardType.PERSON);
		Card personTwo = new Card ("p2", CardType.PERSON);
		Card weaponOne = new Card("w1", CardType.WEAPON);
		Card weaponTwo = new Card("w2", CardType.WEAPON);
		Card roomOne = new Card("r1", CardType.ROOM);
		Card roomTwo = new Card("r2", CardType.ROOM);
		
		cp.dealCard(personOne);
		cp.dealCard(personTwo);
		cp.dealCard(weaponOne);
		cp.dealCard(weaponTwo);
		cp.dealCard(roomOne);
		cp.dealCard(roomTwo);
		
		//Test correct person
		Solution testingSol = new Solution("p1","w", "r");
		assertEquals(personOne, cp.disproveSuggestion(testingSol));
		//Test correct weapon
		testingSol = new Solution("p", "w1", "r");
		assertEquals(weaponOne, cp.disproveSuggestion(testingSol));
		//Test correct room
		testingSol = new Solution("p", "w", "r2");
		assertEquals(roomTwo, cp.disproveSuggestion(testingSol));
		
		//Test nothing correct -- return null
		testingSol = new Solution("p", "w", "r");
		assertEquals(null, cp.disproveSuggestion(testingSol));
		//Test all correct 
		testingSol = new Solution("p1", "w1", "r1");
		boolean person = false;
		boolean weapon = false;
		boolean room = false;
		for(int i = 0; i < 100; i++) {
			Card disproveCard = cp.disproveSuggestion(testingSol);
			
			if(disproveCard == personOne) {
				person = true;
			}
			else if(disproveCard == weaponOne) {
				weapon = true;
			}
			else if(disproveCard == roomOne) {
				room = true;
			}
			else {
				fail("No card found");
			}
		}
		assertTrue(person);
		assertTrue(weapon);
		assertTrue(room);
	}
	
	@Test
	public void handleSuggestion() {
		//Create two cards of each type
		Card personOne = new Card ("p1", CardType.PERSON);
		Card personTwo = new Card ("p2", CardType.PERSON);
		Card weaponOne = new Card("w1", CardType.WEAPON);
		Card weaponTwo = new Card("w2", CardType.WEAPON);
		Card roomOne = new Card("r1", CardType.ROOM);
		Card roomTwo = new Card("r2", CardType.ROOM);
		
		//Clear each players hand for test sake
		for(Player p : board.getPlayers()) {
			p.clear();
		}
		
		//Create all players and deal each player only one card
		hum = new HumanPlayer(board.getPlayers().get(0).getName(),board.getPlayers().get(0).getRow(),board.getPlayers().get(0).getCol(),board.getPlayers().get(0).getColor());
		hum.dealCard(personOne);
		ComputerPlayer cp1 = (ComputerPlayer) board.getPlayers().get(1);
		cp1.dealCard(personTwo);
		ComputerPlayer cp2 = (ComputerPlayer) board.getPlayers().get(2);
		cp2.dealCard(weaponOne);
		ComputerPlayer cp3 = (ComputerPlayer) board.getPlayers().get(3);
		cp3.dealCard(weaponTwo);
		ComputerPlayer cp4 = (ComputerPlayer) board.getPlayers().get(4);
		cp4.dealCard(roomOne);
		ComputerPlayer cp5 = (ComputerPlayer) board.getPlayers().get(5);
		cp5.dealCard(roomTwo);
		
		//No player can disprove
		Solution testingSol = new Solution("p3", "w3", "r3");
		assertEquals(null, board.handleSuggestion(testingSol, cp1.getName(), null));
		//Human can disprove
		testingSol = new Solution("p1", "w3", "r3");
		assertEquals(personOne, board.handleSuggestion(testingSol, cp1.getName(), null));
		//No players can disprove but suggesting player
		testingSol = new Solution("p2", "w3", "r3");
		assertEquals(null, board.handleSuggestion(testingSol, cp1.getName(), null));
		//Two people can disprove (only next up does)
		testingSol = new Solution("p1", "w2", "r3");
		assertEquals(weaponTwo, board.handleSuggestion(testingSol, cp1.getName(), null));
		//Ensure that other player returns answer too
		testingSol = new Solution("p1", "w3", "r3");
		assertEquals(personOne, board.handleSuggestion(testingSol, cp1.getName(), null));
	}
}

