/**
 * IntBoard -- creates a board comprised of BoardCells and contains all methods
 * necessary for the game to run correctly. 
 * 
 * @author Hannah Lee
 * @author Savannah Paul
 */
package experiment;
import java.util.*;

public class IntBoard {
	private BoardCell [][] grid;
	private Map <BoardCell, Set<BoardCell>> adjMtx; 
	private Set <BoardCell> visited; 
	private Set <BoardCell> targets; 
	
	/**
	 * IntBoard constructor -- creates a 4x4 board and creates a adjacency map. 
	 */
	public IntBoard(){
		grid = new BoardCell[4][4];
		calcAdjencies(); 
	}
	
	/**
	 * calcAdjencies -- populates adjacency map for each position on the board
	 */
	public void calcAdjencies(){
		return;
	}
	
	/**
	 * getAdjList -- returns adjacency list at a certain position by accessing the values
	 * in adjMtx.
	 * @param c is the specific position we are pulling the adjacency list from
	 * @return adjacency list for board cell c
	 */
	public Set<BoardCell> getAdjList(BoardCell c){
		return null;  
	}
	
	/**
	 * calcTargets -- calculates a list of all possible target positions
	 * @param startCell the starting position of the player
	 * @param pathLength the number they rolled on the dice / the number of spaces they are allowed to move
	 * @return a list that contains all possible target positions
	 */
	public List<BoardCell> calcTargets(BoardCell startCell, int pathLength){
		return null;	
	}
	
	/**
	 * getTargets -- getter that returns the list of possible target positions
	 * @return targets the set that contains all possible target cells
	 */
	public Set <BoardCell> getTargets(){
		return null; 
	}
	
	/**
	 * getCell -- getter that returns type BoardCell at the location provided
	 * @param row that the cell is at
	 * @param column that the cell is at
	 * @return the BoardCell at position (row, column)
	 */
	public BoardCell getCell(int row, int column){
		return null;
	}
}
