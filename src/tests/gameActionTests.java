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
	
	@Test
	public void createSuggestion() {
		Player cx = board.getPlayers().get(1);
		ComputerPlayer cplayer = (ComputerPlayer) cx;
		
		
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
		assertTrue(cplayer.getLastSuggestion().person.equals(board.solution.person));
		assertTrue(cplayer.getLastSuggestion().weapon.equals(board.solution.weapon));
		assertTrue(cplayer.getLastSuggestion().room.equals(board.getRooms().get('G')));
		
		Card extra = null;
		
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
}

