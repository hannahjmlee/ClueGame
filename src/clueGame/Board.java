/**
 * Board Class -- characterized by the number of rows and columns it has. Read in 
 * from the configuration files
 * @author Hannah Lee
 * @author Savannah Paul
 */
package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
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
	private Set <BoardCell> visited; 
	private Set <BoardCell> targets; 
	private Set <BoardCell> returnTargets; 
	private Set <BoardCell> adjList; 
	
	private String cardConfigPeople;
	private String cardConfigWeapons; 
	private Set <Card> deck;
	private Map<String, Color> peopleColors; 
	private Map<String, BoardCell> peopleStartLoc; 
	private ArrayList <Player> players;
	public ArrayList <Card> playerCards;
	public ArrayList <Card> weaponCards;
	public ArrayList <Card> roomCards;
	
	public Solution solution; 

	/**
	 * Board Constructor -- initializes board and its size
	 */
	private Board(){
		visited = new HashSet<BoardCell>(); 
		targets= new HashSet<BoardCell>();
		deck = new HashSet<Card>(); 
		returnTargets= new HashSet<BoardCell>();
		peopleColors = new HashMap <String, Color>(); 
		peopleStartLoc = new HashMap <String, BoardCell> (); 
		players = new ArrayList <Player>();
		solution = new Solution();
		playerCards = new ArrayList <Card>();
		weaponCards = new ArrayList <Card>();
		roomCards = new ArrayList <Card>();
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
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		boardConfigFile = string;
		roomConfigFile = string2;
		cardConfigPeople = string3; 
		cardConfigWeapons = string4; 
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
		
		try {
			createPlayerCards();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}

		try {
			createWeaponCards();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
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
			
			if (legendIn[2].equals("Card")) {
				deck.add(new Card(legendIn[1], CardType.ROOM));
				roomCards.add(new Card(legendIn[1], CardType.ROOM));
			}
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
					default: board[row][i].setDoorDirection(DoorDirection.NONE); // set all non-door rooms to have a door direction of NONE
					board[row][i].setDoorway(false); // this will be helpful later on when we have secret paths.  
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

	/**
	 * loadRoomConfig -- creates the legend
	 * @throws BadConfigFormatException throws an exception if the board is badly configured
	 * @throws IOException throws a file i/o exception if the board file cannot be found
	 */
	public void loadRoomConfig() throws BadConfigFormatException, IOException{
		createLegend();
	}

	/**
	 * loadBoardConfig -- creates the board
	 * @throws BadConfigFormatException throws an exception if the board is badly configured
	 * @throws IOException throws a file i/o exception if the board file cannot be found
	 */
	public void loadBoardConfig() throws BadConfigFormatException, IOException{
		createBoardConfig();
	}

	/**
	 * getAdjList -- calls the method that calculates the adjacency list and returns that list.  
	 * @param row is the row of the board cell
	 * @param col is the column of the board cell
	 * @return adjList a set of all adjacent board cells
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		calcAdjacency(row, col); 
		return adjList;
	}
	
	/**
	 * calcAdjacency -- calculates the adjacency list when given a row and a column based on surrounding board cells and their symbol/door direction
	 * @param row is the row of the board cell
	 * @param col is the column of the board cell
	 */
	public void calcAdjacency(int row, int col) {
		adjList= new HashSet<BoardCell>();
		if(board[row][col].getInitial() == 'W') {
			if (row > 0 && (board[row-1][col].getInitial() == 'W' || board[row-1][col].getDoorDirection() == DoorDirection.DOWN)) {
					adjList.add(board[row-1][col]); 	
			}
			if (row < numRows-1 && (board[row+1][col].getInitial() == 'W' || board[row+1][col].getDoorDirection() == DoorDirection.UP)) {
					adjList.add(board[row+1][col]); 
			}
			if (col > 0 && (board[row][col-1].getInitial() == 'W' || board[row][col-1].getDoorDirection() == DoorDirection.RIGHT)) {
					adjList.add(board[row][col-1]);
			}
			if ( col < numColumns-1 && (board[row][col+1].getInitial() == 'W' || board[row][col+1].getDoorDirection() == DoorDirection.LEFT)) {
					adjList.add(board[row][col+1]); 
			}
		}
		else if(board[row][col].getDoorDirection() != null || board[row][col].getDoorDirection() != DoorDirection.NONE) {
			if(board[row][col].getDoorDirection() == DoorDirection.UP && row != 0) {
					adjList.add(board[row-1][col]);
			}
			if(board[row][col].getDoorDirection() == DoorDirection.DOWN && row != numRows - 1) {
				adjList.add(board[row+1][col]);
			}
			if(board[row][col].getDoorDirection() == DoorDirection.RIGHT && col != numColumns - 1) {
				adjList.add(board[row][col+1]);
			}
			if(board[row][col].getDoorDirection() == DoorDirection.LEFT && col != 0) {
				adjList.add(board[row][col-1]);
			}
		}
	}

	/**
	 * calcTargets -- calculates target positions based on the number of steps provided. 
	 * @param row the row of the starting board cell
	 * @param col the column of the starting board cell
	 * @param step the number of steps to take
	 */
	public void calcTargets(int row, int col, int step) {
		if(step != 0) {
			if(visited.isEmpty()){
				visited.add(board[row][col]);
			}

			Set<BoardCell> adjCells = getAdjList(row, col); 

			for (BoardCell c : adjCells) {
				if (visited.contains(c) == false) {
					visited.add(c);

					if((step != 1) && c.getDoorDirection() != null && c.getDoorDirection() != DoorDirection.NONE) {
						targets.add(c);
					}

					if (step == 1) { 
						if (c.getInitial() == 'W' || c.getDoorDirection() != null && c.getDoorDirection() != DoorDirection.NONE)
							targets.add(c);
					}
					else {
						calcTargets(c.getRow(), c.getCol(), step - 1);
					}

					visited.remove(c);
				}		
			}
			returnTargets.clear(); // clears return targets since a new list of targets was created.
		}
	}

	/**
	 * getTargets -- returns the list of targets for the calculated board cells.
	 * @return returnTargets a list of targets to return to the user
	 */
	public Set<BoardCell> getTargets() {
		for (BoardCell c : targets) {
			returnTargets.add(c);	// stores targets into returnTargets so that it can be used multiple times  
		}
		targets.clear(); // clear the targets for when we calculate the targets next time
		visited.clear(); // clear the visited cells for when we calculate the targets next time
		return returnTargets;
	}
	
	/**
	 * createPlayerCards -- reads in players from cluePeople and adds them accordingly to 
	 * the deck and adds their information to maps. 
	 * @throws BadConfigFormatException when card type is not valid or starting location is not valid
	 * @throws IOException when file cannot be found
	 */
	private void createPlayerCards() throws BadConfigFormatException, IOException{
		FileReader reader = new FileReader("src/data/" + cardConfigPeople);
		Scanner in = new Scanner(reader); 
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			
			if (!(legendIn[1].equals("Card"))){
				throw new BadConfigFormatException("Person not in configuration file");
			}
			Card temp = new Card (legendIn[0], CardType.PERSON); 
			deck.add(temp);
			playerCards.add(temp);
			
			ComputerPlayer p = new ComputerPlayer(legendIn[0], Integer.parseInt(legendIn[3]), Integer.parseInt(legendIn[3]), convertColor(legendIn[2]));
			players.add(p);
			
			Color c = convertColor(legendIn[2]); 
			peopleColors.put(legendIn[0], c);
			
			int tempRow = Integer.parseInt(legendIn[3]);
			int tempCol = Integer.parseInt(legendIn[4]); 
			if (tempRow < 0 || tempRow >= numRows || tempCol < 0 || tempCol >= numColumns) {
				throw new BadConfigFormatException("Starting location not in configuration file");
			}
			BoardCell start = new BoardCell(tempRow, tempCol);  
			peopleStartLoc.put(legendIn[0], start); 
		}
		in.close(); 
	}
	
	/**
	 * convertColor -- converts string into Java.Color object
	 * @param strColor -- string we want to convert
	 * @return color -- color object of the inputed string
	 */
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
	}
	
	
	/**
	 * getDeck -- return deck
	 * @return deck -- hash set containing all cards
	 */
	public Set<Card> getDeck() {
		return deck;
	}

	/**
	 * getPeopleColors -- returns map of characters and their colors
	 * @return peopleColors -- hash map containing all characters and their corresponding colors
	 */
	public Map<String, Color> getPeopleColors() {
		return peopleColors;
	}

	/**
	 * getPeopleStartLoc-- returns map of characters and their starting location board cell
	 * @return peopleStartLoc -- hash map containing all characters and their corresponding board cell
	 */
	public Map<String, BoardCell> getPeopleStartLoc() {
		return peopleStartLoc;
	}

	/**
	 * createWeaponCards -- reads in players from clueWeapons and adds them accordingly to the deck
	 * @throws BadConfigFormatException when card type is not valid
	 * @throws IOException when file cannot be found
	 */
	private void createWeaponCards() throws BadConfigFormatException, IOException{
		FileReader reader = new FileReader("src/data/" + cardConfigWeapons);
		Scanner in = new Scanner(reader); 
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] legendIn = line.split(", ");
			
			if (!(legendIn[1].equals("Card"))){
				throw new BadConfigFormatException("Weapon not in configuration file");
			}
			Card temp = new Card (legendIn[0], CardType.WEAPON); 
			deck.add(temp); 
			weaponCards.add(temp);
		}
		in.close();
		
	}
	/**
	 * getPlayers -- returns an ArrayList of players
	 * @return
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}
	/**
	 * dealCards -- deals all cards from deck into the "hands" of each player 
	 */
	public void dealCards() {
		Random rand = new Random();
        int count = 0;
        ArrayList<Card> arrDeck = new ArrayList<Card>();
        for(int i = 0; i < deck.size(); i++) {
        	arrDeck.add((Card) deck.toArray()[i]);
        }
        while(arrDeck.size() != 0) {
            Player nextPlayer = players.get(count++ % players.size());
            int randIndex = rand.nextInt(arrDeck.size());
            Card c = arrDeck.get(randIndex);
            nextPlayer.dealCard(c);
            arrDeck.remove(c); 	//remove card after being dealt
        }
	}
	

	// TO BE IMPLEMENTED AT A LATER TIME: 
	public void selectAnswer() {
	}
	
	public Card handleSuggestion() {
		Card c = new Card("X", null);
		return c; 
	}
	
	public boolean checkAccusation(Solution accusation) {
		String accPerson = accusation.person;
		String accWeapon = accusation.weapon; 
		String accRoom = accusation.room; 
		if(solution.person.equals(accPerson) && solution.weapon.equals(accWeapon) && solution.room.equals(accRoom))
			return true;
		return false;		
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}

	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	

}
