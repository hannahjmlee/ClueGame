/**
 * Board Class -- characterized by the number of rows and columns it has. Read in 
 * from the configuration files
 * @author Hannah Lee
 * @author Savannah Paul
 */
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import clueGame.BoardCell;

public class Board {
	private static Board theInstance = new Board(); 
	public final static int MAX_BOARD_SIZE = 50; 
	private int numRows; 
	private int numColumns; 
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<Character> totalSyms = new HashSet<Character>();

	/**
	 * Board Constructor -- initializes board and its size
	 */
	private Board(){
	}

	/**
	 * getInstance -- getter that returns theInstance
	 * @return theInstance is a board
	 */
	public static Board getInstance(){
		return theInstance; 
	}

	/**
	 * setConfigFiles -- sets up and reads in from the configuration files
	 * @param string name of the first file -- map of board (csv) 
	 * @param string2 name of the second file -- legend (txt)
	 */
	public void setConfigFiles(String string, String string2) {
		boardConfigFile = string;
		roomConfigFile = string2;
		return; 

	}

	/**
	 * initialize -- initializes board from both configuration files. 
	 * @throws IOException 
	 * @throws BadConfigFormatException 
	 * @throws FileNotFoundException 
	 */
	public void initialize() throws IOException {
		try {
			createLegend();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}

		try {
			createBoardConfig();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		return;
	}

	/**
	 * createLegend() -- creates a legend that maps the character to the name of the room
	 * @throws IOException 
	 */
	public void createLegend() throws BadConfigFormatException, IOException{
		FileReader reader = new FileReader("src/data/"+roomConfigFile);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(reader);
		rooms = new HashMap<Character, String>();

		while(in.hasNextLine()){
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			if (legendIn[0].length() > 1) {
				throw new BadConfigFormatException("Room abbrev. is in improper format");
			}
			if(!legendIn[2].equals("Card") && !legendIn[2].equals("Other")){
				throw new BadConfigFormatException("Not a Card or Other type");
			}
			if (legendIn.length != 3){
				throw new BadConfigFormatException("NO ROOM TYPE");
			}
			rooms.put(legendIn[0].charAt(0), legendIn[1]);
		}
		in.close();
		totalSyms=rooms.keySet();
	}

	public void createBoardConfig() throws BadConfigFormatException, IOException{
		FileReader reader = new FileReader("src/data/"+boardConfigFile);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(reader);
		int row = 0;
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];	

		// Setting Board private variable columns. Should be this value forever.
		// It gets reset every time the while loop loops, but still should be constant.


		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] rowarr = line.split("\\s*,\\s*");

			for(int i = 0; i < rowarr.length; i++) {
				board[row][i] = new BoardCell(row, i);
				board[row][i].setInitial(rowarr[i].charAt(0));
				if(!totalSyms.contains(rowarr[i].charAt(0))) {
					throw new BadConfigFormatException("Room not in configuration file");
				}
				if(rowarr[i].length() > 1) {
					board[row][i].setDoorway(true);
					switch(rowarr[i].charAt(1)) {
					case 'U': board[row][i].setDoorDirection(DoorDirection.UP);
					break;
					case 'D': board[row][i].setDoorDirection(DoorDirection.DOWN);
					break;
					case 'L': board[row][i].setDoorDirection(DoorDirection.LEFT);
					break;
					case 'R': board[row][i].setDoorDirection(DoorDirection.RIGHT);
					break;
					case 'N': board[row][i].setDoorDirection(DoorDirection.NONE);
					board[row][i].setDoorway(false);
					break;
					default: {throw new BadConfigFormatException("Invalid DoorDirection");}
					}
				}
			}

			if(row == 0){
				numColumns = rowarr.length;
			}
			else if (rowarr.length != numColumns) {
				throw new BadConfigFormatException("Number of columns not constant");
			}

			row++;
		}
		// Setting Board private variable rows.
		numRows = row;


	}

	/**
	 * getNumRows -- getter that returns the number of rows on the board
	 * @return numRows the number of rows on the board
	 */
	public int getNumRows(){
		return numRows;
	}

	/**
	 * getNumColumns -- getter that returns the number of columns on the board
	 * @return numColumns the number of columns on the board
	 */
	public int getNumColumns(){
		return numColumns;
	}

	/**
	 * getLegend -- returns a map of the legend that maps the character to the 
	 * name of the room. 
	 * @return legend a map of all characters and their corresponding room names
	 */
	public Map<Character, String> getLegend() {
		return rooms; 
	}

	/**
	 * getCellAt -- returns the board cell at the grid location [i, j]
	 * @param i the row we're looking at
	 * @param j the column we're looking at
	 * @return c the board cell at that grid location
	 */
	public BoardCell getCellAt(int i, int j) {
		BoardCell cell = board[i][j];
		return cell;
	}

	public void loadRoomConfig() throws BadConfigFormatException, IOException{
		createLegend();
	}

	public void loadBoardConfig() throws BadConfigFormatException, IOException{
		createBoardConfig();
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		Set<BoardCell> temp= new HashSet<BoardCell>();
		if(board[i][j].getInitial() == 'W') {
			if (i > 0) {
				if(board[i-1][j].getInitial() == 'W' || board[i-1][j].getDoorDirection() == DoorDirection.DOWN)
					temp.add(board[i-1][j]); 	
			}
			if (i < numRows-1) {
				if(board[i+1][j].getInitial() == 'W' || board[i+1][j].getDoorDirection() == DoorDirection.UP)
					temp.add(board[i+1][j]); 
			}
			if (j > 0) {
				if(board[i][j-1].getInitial() == 'W' || board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)
					temp.add(board[i][j-1]);
			}
			if ( j < numColumns-1) {
				if(board[i][j+1].getInitial() == 'W' || board[i][j+1].getDoorDirection() == DoorDirection.LEFT)
					temp.add(board[i][j+1]); 
			}
			return temp;
		}
		else if(board[i][j].getDoorDirection() != null || board[i][j].getDoorDirection() != DoorDirection.NONE) {
			if(board[i][j].getDoorDirection() == DoorDirection.UP) {
				if (i != 0)
				temp.add(board[i-1][j]);
				return temp;
			}
			if(board[i][j].getDoorDirection() == DoorDirection.DOWN) {
				temp.add(board[i+1][j]);
				return temp;
			}
			if(board[i][j].getDoorDirection() == DoorDirection.RIGHT) {
				temp.add(board[i][j+1]);
				return temp;
			}
			if(board[i][j].getDoorDirection() == DoorDirection.LEFT) {
				temp.add(board[i][j-1]);
				return temp;
			}
		}
		return temp;
	}


	public void calcTargets(int i, int j, int k) {

	}

	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp= new HashSet<BoardCell>();
		temp.add(board[0][0]);
		return temp;
	}



}
