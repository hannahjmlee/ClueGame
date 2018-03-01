package clueGame;

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
	
	
}
