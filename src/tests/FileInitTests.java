package tests;
/**
 * FileInitTests -- tests our board and all of its features. 
 * @author Hannah Lee
 * @author Savannah Paul
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 19;
	public static final int NUM_COLUMNS = 21;

	private static Board board;
	
	/**
	 * setUp -- initializes board and config files. 
	 * @throws IOException 
	 */
	@BeforeClass
	public static void setUp() throws IOException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameRooms.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		}
	
	
	/**
	 * testBoardDimensions -- Checks for correct number of rows/cols
	 */
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS,board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	/**
	 * testRooms -- tests that the map that contains our legend matches the 
	 * inputed legend text. This makes sure that the character key of the map
	 * is correctly pointing to its corresponding room name. 
	 */
	@Test
	public void testRooms(){
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		//Test specific rooms
		assertEquals("Morgue", legend.get('M'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Pool", legend.get('P'));
		assertEquals("Dining room", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	/**
	 * testDoorway -- tests the door direction at certain board cells. Also checks to
	 * make sure that room pieces and walkways that aren't doors, know that they aren't doors. 
	 */
	@Test 
	public void testDoorway(){
		BoardCell room = board.getCellAt(8, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(10, 20);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(16, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(6, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(12, 6);
		assertFalse(cell.isDoorway());		
	}
	
	/**
	 * testNumberOfDoorways -- tests that the number of doorways is correctly read in from
	 * the csv file by incrementing through all board cells on the board and counting the 
	 * number of doorways it encounters. 
	 */
	@Test
	public void testNumberOfDoorways() {
		int doors = 0;
		for(int row = 0; row<board.getNumRows(); row++)
			for(int col = 0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					doors++;
			}
		Assert.assertEquals(33, doors);
	}
	
	/**
	 * testRoomInitials -- tests that the character denoting the type of room cell at various
	 * cells is correctly read in. 
	 */
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('G', board.getCellAt(0, 0).getInitial());
		assertEquals('C', board.getCellAt(5, 0).getInitial());
		assertEquals('L', board.getCellAt(14, 6).getInitial());
		// Test last cell in room
		assertEquals('M', board.getCellAt(18, 20).getInitial());
		assertEquals('K', board.getCellAt(5, 20).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(18, 15).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(7, 8).getInitial());
	}
	
	

}
