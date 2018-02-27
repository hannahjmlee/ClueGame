package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	private IntBoard board; 
	
	@Before
	public void beforeAll(){
		board = new IntBoard(); 
	}
	
	@Test
	public void topLeft(){
		BoardCell c = board.getCell(0,0);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(1, 0)));
		assertTrue(test.contains(board.getCell(0, 1)));
		assertEquals(2, test.size());
		
	}
	
	@Test
	public void bottomRight(){
		BoardCell c = board.getCell(3,3);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(2, 3)));
		assertTrue(test.contains(board.getCell(3, 2)));
		assertEquals(2, test.size());
		
	}
	
	@Test
	public void rightEdge(){
		BoardCell c = board.getCell(1,3);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(0, 3)));
		assertTrue(test.contains(board.getCell(1, 2)));
		assertTrue(test.contains(board.getCell(2, 3)));
		assertEquals(3, test.size());
		
	}
	
	@Test
	public void leftEdge(){
		BoardCell c = board.getCell(2,0);
		Set<BoardCell> test = board.getAdjList(c);
		
		assertTrue(test.contains(board.getCell(1, 0)));
		assertTrue(test.contains(board.getCell(2, 1)));
		assertTrue(test.contains(board.getCell(3, 0)));
		assertEquals(3, test.size());
		
	}
	
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
	
	@Test
	public void testTargets0_3(){
		BoardCell cell = board.getCell(0, 0);
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
	
	@Test
	public void testTargets1_2(){
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
	
}
