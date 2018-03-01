package clueGame;

import java.util.*;

public class Board {
	public final static int MAX_BOARD_SIZE = 50; 
	private int numRows; 
	private int numColumns; 
	private static Board theInstance = new Board(); 
	
	
	private Board(){
		// this method returns the only board 
	}
	
	public static Board getInstance(){
		return theInstance; 
	}

	public void setConfigFiles(String string, String string2) {
		return; 
		
	}

	public void initialize() {
		return;
	}
	
	public int getNumRows(){
		return numRows;
	}
	public int getNumColumns(){
		return numColumns;
	}

	public Map<Character, String> getLegend() {
		Map <Character, String> temp = new HashMap<Character,String>(); 
		
		return temp; 
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
}
