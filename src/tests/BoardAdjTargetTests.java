package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	private static Board board;

	@BeforeClass
	public static void setUp() throws IOException {
		board = Board.getInstance();
		board.setConfigFiles("ClueGameRooms.csv", "ClueRooms.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are YELLOW on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms(){
		//Corner
		Set<BoardCell> testList = board.getAdjList(0, 20);
		assertEquals(0, testList.size());
		//Walkway underneath
		testList = board.getAdjList(3, 0);
		assertEquals(0, testList.size());
		//Walkway above
		testList = board.getAdjList(5, 2);
		assertEquals(0, testList.size());
		//Middle of room
		testList = board.getAdjList(2, 18);
		assertEquals(0, testList.size());
		//Beside a door
		testList = board.getAdjList(18, 16);
		assertEquals(0, testList.size());
		//Corner of room
		testList = board.getAdjList(15, 3);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are RED/PINK (SALMON) on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(8, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(4, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 15)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(12, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 3)));
		//TEST DOORWAY UP
		testList = board.getAdjList(14, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(3, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 3)));

	}
	// Test adjacency at entrance to rooms
	// These tests are TURQUOISE in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(8, 6);
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertTrue(testList.contains(board.getCellAt(8, 7)));
		assertTrue(testList.contains(board.getCellAt(7, 6)));
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(10, 18);
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertTrue(testList.contains(board.getCellAt(11, 18)));
		assertEquals(2, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(0, 7);
		assertTrue(testList.contains(board.getCellAt(0, 6)));
		assertTrue(testList.contains(board.getCellAt(0, 8)));
		assertTrue(testList.contains(board.getCellAt(1, 7)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(6, 20);
		assertTrue(testList.contains(board.getCellAt(6, 19)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertEquals(2, testList.size());
	}

	// Tests locations with only walkways as adjacent locations
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		//Test on left edge of the board, 1 walkway piece
		Set<BoardCell> testList = board.getAdjList(14, 0);
		assertTrue(testList.contains(board.getCellAt(14, 1)));
		assertTrue(testList.contains(board.getCellAt(15, 0)));
		assertEquals(2, testList.size());

		//Test along wall in board, three walkway pieces
		testList = board.getAdjList(14, 5);
		assertTrue(testList.contains(board.getCellAt(13, 5)));
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(14, 4)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(4, 1);
		assertTrue(testList.contains(board.getCellAt(4, 0)));
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(4,5);
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertEquals(4, testList.size());

		//Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(12, 20);
		assertTrue(testList.contains(board.getCellAt(12, 19)));
		assertTrue(testList.contains(board.getCellAt(11, 20)));
		assertEquals(2, testList.size());

		//Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(18, 9);
		assertTrue(testList.contains(board.getCellAt(17, 9)));
		assertTrue(testList.contains(board.getCellAt(18, 10)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 16);
		assertTrue(testList.contains(board.getCellAt(5, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertEquals(2, testList.size());
	}

	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testWalkwayTargetsOneStep() {
		board.calcTargets(18, 13, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		assertTrue(targets.contains(board.getCellAt(18, 12)));

		board.calcTargets(0, 13, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 12)));
		assertTrue(targets.contains(board.getCellAt(0, 14)));
		assertTrue(targets.contains(board.getCellAt(1, 13)));
	}

	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testWalkwayTargetsTwoStep() {
		board.calcTargets(4, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 2)));

		board.calcTargets(16, 9, 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 9)));	 
		assertTrue(targets.contains(board.getCellAt(17, 10)));	
		assertTrue(targets.contains(board.getCellAt(15, 10)));
		assertTrue(targets.contains(board.getCellAt(16, 11)));
		assertTrue(targets.contains(board.getCellAt(15, 8)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testWalkwayTargetsFourSteps() {
		board.calcTargets(4, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 3)));	
		assertTrue(targets.contains(board.getCellAt(4, 4)));

		
		// Includes a path that doesn't have enough length
		board.calcTargets(6, 18, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(6, 14)));	
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(8, 16)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testWalkwayTargetsSixSteps() {
		board.calcTargets(6, 18, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 12)));
		assertTrue(targets.contains(board.getCellAt(5, 13)));
		assertTrue(targets.contains(board.getCellAt(4, 14)));
		assertTrue(targets.contains(board.getCellAt(6, 14)));
		assertTrue(targets.contains(board.getCellAt(8, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(9, 15)));
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(8, 16)));
		assertTrue(targets.contains(board.getCellAt(10, 16)));
		
			
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(6, 18, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		// directly down (can't go up 2 steps)
		assertTrue(targets.contains(board.getCellAt(8, 18)));
		// directly right and left
		assertTrue(targets.contains(board.getCellAt(6, 20)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(18, 5, 5);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(10, targets.size());
		//walkway targets
		assertTrue(targets.contains(board.getCellAt(13,5)));
		assertTrue(targets.contains(board.getCellAt(15,5)));
		assertTrue(targets.contains(board.getCellAt(17,5)));
		assertTrue(targets.contains(board.getCellAt(14,4)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(18,4)));
		
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(16, 3)));
		assertTrue(targets.contains(board.getCellAt(18, 3)));		
		// 
		assertTrue(targets.contains(board.getCellAt(16, 6)));		
		assertTrue(targets.contains(board.getCellAt(17, 6)));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(18, 3, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 14)));
		
		// Take two steps
		board.calcTargets(18, 3, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 4)));
	}
	
}
