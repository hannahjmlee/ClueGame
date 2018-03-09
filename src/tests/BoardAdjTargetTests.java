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
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt");		
		board.initialize();
	}
	
	// Tests locations with only walkways as adjacent locations
	// These tests are ________ on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		//Test on left edge of the board, 1 walkway piece
		Set<BoardCell> testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertEquals(1, testList.size());
		
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
		assertTrue(testList.contains(board.getCellAt(6, 5)));
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
		testList = board.getAdjList(5, 3);
		assertTrue(testList.contains(board.getCellAt(5, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertEquals(3, testList.size());
	}
	
	// Ensure that player does not move around within room
	// These cells are ______ on the planning spreadsheet
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
	
	@Test
	public void testWalkwayTargetsOneStep() {
		board.calcTargets(18, 9, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(17, 9)));
		
		board.calcTargets(0, 13, 1);
		Set<BoardCell> targets1= board.getTargets();
		assertEquals(3, targets1.size());
		assertTrue(targets1.contains(board.getCellAt(0, 12)));
		assertTrue(targets1.contains(board.getCellAt(0, 14)));
		assertTrue(targets1.contains(board.getCellAt(1, 13)));
	}
	
	@Test
	public void testWalkwayTargetsTwoStep() {
		board.calcTargets(4, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 2)));
		
		board.calcTargets(18, 9, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 11)));	
		assertTrue(targets.contains(board.getCellAt(19, 10)));	
	}
	
	

}
