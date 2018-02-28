/**
 * IntBoardTests Junit Test -- checks adjacency lists of various positions on board and tests
 * possible positions depending on path length and position. 
 * 
 * @author Hannah Lee
 * @author Savannah Paul 
 */
package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board; 
	
	/**
	 * beforeAll -- creates a new board of size 4x4 before starting tests
	 */
	@Before
	public void beforeAll(){
		board = new IntBoard(); 
	}
	
	/**
	 * topLeft -- tests adjacency list of the top left hand corner of the board (Position (0,0))
	 */
	@Test
	public void topLeft(){
		BoardCell c = board.getCell(0,0);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(1, 0)));
		assertTrue(test.contains(board.getCell(0, 1)));
		assertEquals(2, test.size());	
	}
	
	/**
	 * bottomRight -- tests adjacency list of the bottom right hand corner of the board (Position(3,3))
	 */
	@Test
	public void bottomRight(){
		BoardCell c = board.getCell(3,3);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(2, 3)));
		assertTrue(test.contains(board.getCell(3, 2)));
		assertEquals(2, test.size());
	}
	
	/**
	 * rightEdge -- tests adjacency list of the far right column (Position (x, 3))
	 */
	@Test
	public void rightEdge(){
		BoardCell c = board.getCell(1,3);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(0, 3)));
		assertTrue(test.contains(board.getCell(1, 2)));
		assertTrue(test.contains(board.getCell(2, 3)));
		assertEquals(3, test.size());
	}
	
	/**
	 * leftEdge -- tests adjacency list of the far left column (Position(x, 0))
	 */
	@Test
	public void leftEdge(){
		BoardCell c = board.getCell(2,0);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(1, 0)));
		assertTrue(test.contains(board.getCell(2, 1)));
		assertTrue(test.contains(board.getCell(3, 0)));
		assertEquals(3, test.size());		
	}
	
	/**
	 * secondColumnMiddle -- tests adjacency list of the second column, in a position that is not an edge
	 */
	@Test
	public void secondColumnMiddle(){
		BoardCell c = board.getCell(1,1);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(0, 1)));
		assertTrue(test.contains(board.getCell(2, 1)));
		assertTrue(test.contains(board.getCell(1, 0)));
		assertTrue(test.contains(board.getCell(1, 2)));
		assertEquals(4, test.size());		
	}
	
	/**
	 *  secondLastColumnMiddle -- tests adjacency list of the second column from the end (third column), 
	 *  in a position that is not an edge
	 */
	@Test
	public void secondLastColumnMiddle(){
		BoardCell c = board.getCell(2,2);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(1, 2)));
		assertTrue(test.contains(board.getCell(3, 2)));
		assertTrue(test.contains(board.getCell(2, 1)));
		assertTrue(test.contains(board.getCell(2, 3)));
		assertEquals(4, test.size());		
	}
	
	/**
	 * testTargets32_3 -- tests possible positions at cell (3,2) with a path length of 3
	 */
	@Test
	public void testTargets32_3(){
		BoardCell cell = board.getCell(3, 2);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	/**
	 * testTargets11_2 -- tests possible positions at cell(1,1) with a path length of 2
	 */
	@Test
	public void testTargets11_2(){
		BoardCell cell = board.getCell(1, 1);	
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
	}
	
	/**
	 * testTargets11_1 -- tests possible positions at cell(1,1) with a path length of 1
	 */
	@Test
	public void testTargets11_1(){
		BoardCell cell = board.getCell(1, 1);	
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
	}
	
	/**
	 * testTargets22_2 -- tests possible positions at cell(2,2) with a path length of 2
	 */
	@Test
	public void testTargets22_2(){
		BoardCell cell = board.getCell(2, 2);	
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}

	/**
	 * testTargets00_3 -- tests possible positions at cell (0,0) with a path length of 3
	 */
	@Test
	public void testTargets00_3(){
		BoardCell cell = board.getCell(0, 0);	
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
	}
	
	/**
	 * testTargets21_4 -- tests possible positions at cell (2,1) with a path length of 4
	 */
	@Test
	public void testTargets21_4(){
		BoardCell cell = board.getCell(2, 1);	
		board.calcTargets(cell, 4);
		Set targets = board.getTargets();
		
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
}
