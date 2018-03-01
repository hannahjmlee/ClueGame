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
		for (int i = 0; i < 4; i ++) {
			for (int j = 0; j < 4; j++) {
				grid[i][j] = new BoardCell(i, j); 
			}
		}

		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>(); 
		targets= new HashSet<BoardCell>();
		calcAdjencies();
		
	}
	
	/**
	 * calcAdjencies -- populates adjacency map for each position on the board
	 */
	public void calcAdjencies(){
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Set <BoardCell> temp = new HashSet<BoardCell>(); 
				if (i != 0) 
					temp.add(grid[i-1][j]); 
				if (i != 3)
					temp.add(grid[i+1][j]);
				if (j != 0)
					temp.add(grid[i][j-1]);
				if (j != 3)
					temp.add(grid[i][j+1]);
				
				BoardCell current = grid[i][j]; 
				adjMtx.put(current, temp);  
			}
		}
		
		
	}
	
	/**
	 * getAdjList -- getter that returns adjacency list at a certain position by 
	 * accessing the values in adjMtx.
	 * @param c is the specific position we are pulling the adjacency list from
	 * @return adjMtx.get(c) adjacency list for board cell c
	 */
	public Set<BoardCell> getAdjList(BoardCell c){
		Set <BoardCell> temp = adjMtx.get(c); 
		return temp; 
	}
	
	/**
	 * calcTargets -- calculates a list of all possible target positions
	 * @param startCell the starting position of the player
	 * @param pathLength the number they rolled on the dice / the number of spaces they are allowed to move
	 * @return a list that contains all possible target positions
	 */
	public void calcTargets(BoardCell startCell, int pathLength){
		
		if(visited.isEmpty()){
			visited.add(grid[startCell.getRow()][startCell.getCol()]);
		}
		Set<BoardCell> adjCells = adjMtx.get(grid[startCell.getRow()][startCell.getCol()]); 
		for (BoardCell c : adjCells) {
			if (visited.contains(c) == false) {
				visited.add(c); 
				if (pathLength == 1) {
					targets.add(c); 
				}
				else {
					calcTargets(c, pathLength - 1);
				}
				visited.remove(c);
			}		
		}
	}
	
	/**
	 * getTargets -- getter that returns the list of possible target positions
	 * @return targets the set that contains all possible target cells
	 */
	public Set <BoardCell> getTargets(){
		return targets;  
	}
	
	/**
	 * getCell -- getter that returns type BoardCell at the location provided
	 * @param row that the cell is at
	 * @param column that the cell is at
	 * @return the BoardCell at position (row, column)
	 */
	public BoardCell getCell(int row, int column){
		return grid[row][column];
	}
	
	public static void main (String [] args) {
		IntBoard x = new IntBoard();
		BoardCell temp = x.getCell(1, 1); 
		
		Set <BoardCell> u = x.getAdjList(temp);
		
		x.calcTargets(temp,  2); 
		System.out.println(x.getTargets());
		
	} 
}
