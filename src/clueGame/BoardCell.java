package clueGame;

/**
 * BoardCell class -- characterized by a row and a column
 * 
 * @author Hannah Lee
 * @author Savannah Paul
 *
 */
public class BoardCell {
	private int row; 
	private int column; 
	private DoorDirection doorDirection;
	private char initial;
	private boolean doorway = false;
	
	public BoardCell(int r, int c) {
		row = r; 
		column = c; 
	}
	
	public int getRow(){
		return row;
	}
	public int getCol(){
		return column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	
	public boolean isDoorway() {
		return doorway;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public void setDoorway(boolean doorway) {
		this.doorway = doorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	} 
}
