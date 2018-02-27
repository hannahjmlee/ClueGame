package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell [][] grid;
	private Map <BoardCell, Set<BoardCell>> adjMtx; 
	private Set <BoardCell> visited; 
	private Set <BoardCell> targets; 
	
	public IntBoard(){
		grid = new BoardCell[4][4];
		calcAdjencies(); 
	}
	
	
	public void calcAdjencies(){
		return;
	}
	
	public Set<BoardCell> getAdjList(){
		return null;  
	}
	
	public List calcTargets(int startCell, int pathLength){
		return null;
		
	}
	
	public Set <BoardCell> getTargets(){
		return null; 
	}
}
