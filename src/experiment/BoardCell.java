package experiment;

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
}
