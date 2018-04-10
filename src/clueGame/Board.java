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
	private String cardConfigPeople;
	private String cardConfigWeapons;
	private Set<Character> totalSyms = new HashSet<Character>();

	private Map<String, Color> peopleColors; 
	private Map<String, BoardCell> peopleStartLoc; 
	private ArrayList <Player> players;

	private Set <BoardCell> visited; 
	private Set <BoardCell> targets; 
	private Set <BoardCell> returnTargets; 
	private Set <BoardCell> adjList; 

	private Set <Card> deck;
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
		
		selectAnswer();  
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

	/**
	 * createBoardConfig -- creates board from csv file
	 * @throws BadConfigFormatException when csv file is not configured correctly
	 * @throws IOException -- when csv file cannot be found
	 */
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
	 * dealCards -- deals all cards from deck into the "hands" of each player. Using a hash set for deck
	 * ensures that the deck is already shuffled
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

	/**
	 * checkAccusation -- checks player's accusation with the solution. Returns true if they made a correct
	 * accusation. Returns false if they make a false accusation.  
	 * @param accusation -- player's accusation
	 * @return true/false -- true if correct, false if incorrect. 
	 */
	public boolean checkAccusation(Solution accusation) {
		String accPerson = accusation.person;
		String accWeapon = accusation.weapon; 
		String accRoom = accusation.room; 
		if(solution.person.equals(accPerson) && solution.weapon.equals(accWeapon) && solution.room.equals(accRoom))
			return true;
		return false;		
	}
	
	/**
	 * handleSuggestion -- find the first player in the game who can disprove a suggestion and moves that player to the accused room
	 * @param suggestion -- proposed suggestion
	 * @param accusorName -- name of the player who made the suggestion
	 * @param loc -- location of the accusing player
	 * @return -- returns the card that can disprove the suggestion, or null if no one can disprove
	 */
	public Card handleSuggestion(Solution suggestion, String accusorName, BoardCell loc) {
		
		Card returned = null;
		int accusorIndex = 0;
		
		for(Player p : this.players) {
			if(p.getName().equals(accusorName)) {
				accusorIndex = players.indexOf(p);
			}
		}
		for(int i = (accusorIndex + 1); i != accusorIndex; i++) {
			if(i == players.size()) {
				i = -1;			//so i will start at beginning of array
				continue;
			}
			returned = players.get(i).disproveSuggestion(suggestion);
			//if no one can disprove, returned will be set to null
			if(returned != null) {
				break;
			}
		}
		
		//Find the player who is being accused
		Player accusedPlayer = null;
		for(Player p : players) {
			if(p.getName().equals(suggestion.person)) {
				accusedPlayer = p;
			}
		}
		
		//Move that player to the room in suggestion accusation
		if(suggestion.room.equals("Green room")) {
			accusedPlayer.setRow(1);
			accusedPlayer.setCol(4);
		}
		else if(suggestion.room.equals("Morgue")) {
			accusedPlayer.setRow(17);
			accusedPlayer.setCol(18);
		}
		else if(suggestion.room.equals("Craft space")) {
			accusedPlayer.setRow(6);
			accusedPlayer.setCol(3);
		}
		else if(suggestion.room.equals("Exercise room")) {
			accusedPlayer.setRow(0);
			accusedPlayer.setCol(8);
		}
		else if(suggestion.room.equals("Kitchen")) {
			accusedPlayer.setRow(1);
			accusedPlayer.setCol(15);
		}
		else if(suggestion.room.equals("Pool")) {
			accusedPlayer.setRow(8);
			accusedPlayer.setCol(18);
		}
		else if(suggestion.room.equals("Lounge")) {
			accusedPlayer.setRow(16);
			accusedPlayer.setCol(6);
		}
		else if(suggestion.room.equals("Dining room")) {
			accusedPlayer.setRow(15);
			accusedPlayer.setCol(0);
		}
		else if(suggestion.room.equals("Torture room")) {
			accusedPlayer.setRow(14);
			accusedPlayer.setCol(12);
		}
		return returned; 
	}
	
	public void selectAnswer() {
		playerCards = shuffle(playerCards);
		weaponCards = shuffle(weaponCards); 
		roomCards = shuffle(roomCards); 
		
		solution.person = playerCards.get(0).getCardName();
		solution.weapon = weaponCards.get(0).getCardName();
		solution.room = roomCards.get(0).getCardName(); 
	}
	
	/**
	 * shuffle -- function that randomly shuffles the deck
	 * @param deck -- deck of cards used for game
	 * @return -- newly shuffled deck
	 */
	public ArrayList<Card> shuffle(ArrayList<Card> deck) {
		Random random = new Random();
		for (int i = 0; i < 500; ++i) {
			int r1 = random.nextInt(deck.size());
			int r2 = random.nextInt(deck.size());

			Card temp = deck.get(r1);
			deck.set(r1, deck.get(r2));
			deck.set(r2, temp);
		}
		return deck;
	}

	// GETTERS -----------------------------------------------------------------------------------

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
	 * getLegend -- returns a map of the legend that maps the character to the 
	 * name of the room. 
	 * @return legend a map of all characters and their corresponding room names
	 */
	public Map<Character, String> getLegend() {
		return rooms; 
	}

	
	/**
	 * getRooms -- returns all rooms and their corresponding characters
	 * @return rooms -- map that points the character to its room name
	 */
	public Map<Character, String> getRooms() {
		return rooms;
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
	 * getPlayerCards -- returns array list of player cards
	 * @return playerCards -- array list containing all player cards
	 */
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
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
	 * getPlayers -- returns an ArrayList of players
	 * @return players -- returns all player
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}

	/**
	 * getWeaponCards -- returns all weapon cards
	 * @return weaponCards -- array list containing all weapons
	 */
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}

	/**
	 * getRoomCards -- returns all room cards
	 * @return roomCards -- array list containing all rooms
	 */
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	
	/**
	 * getDeck -- return deck
	 * @return deck -- hash set containing all cards
	 */
	public Set<Card> getDeck() {
		return deck;
	}
}
