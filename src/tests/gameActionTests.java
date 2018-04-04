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


	@Test
	public void selectTarget() {

		targets = new HashSet <BoardCell>();
		comp = new ComputerPlayer("Catty Cassie", 16, 19, Color.CYAN); 

		//check target random selection for a walkway with no access to doors. 	
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

		// Test Selection of Door --------------------------------------------
		comp = new ComputerPlayer("Catty Cassie", 6, 18, Color.CYAN); 
		board.calcTargets(comp.getRow(), comp.getCol(), 2);

		boolean loc_5_18 = false;
		for (int i = 0; i < 100; i++) {
			BoardCell selected = comp.pickLocation(board.getTargets());
			if (selected == board.getCellAt(5,  18))
				loc_5_18 = true;  
			comp.setLastRoom('0'); 
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
}

