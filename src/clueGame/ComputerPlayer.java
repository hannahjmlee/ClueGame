package clueGame;
import java.awt.Color;
import java.util.*; 

/**
 * ComputerPlayer -- class for computer players
 * @author Hannah Lee
 * @author Savannah Paul
 */

public class ComputerPlayer extends Player{
	private char lastRoom; 
	public static Solution lastSuggestion;


	/**
	 * ComputerPlayer -- constructor for computer player
	 * @param playerName -- name of player
	 * @param row -- row position
	 * @param col -- column position
	 * @param color -- color
	 */
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
		lastRoom = '0'; 
		lastSuggestion = new Solution(); 
	}


	/**
	 * pickLocation -- chooses which location the computer player will go to based on its
	 * current location
	 * @param targets -- list of target locations it can visit
	 * @return c -- BoardCell that is the computer player's next location
	 */
	public BoardCell pickLocation(Set <BoardCell> targets) {
		for (BoardCell c : targets) {
			if (c.isDoorway() && !(c.getInitial() == lastRoom)) { // not in last room
				setLastRoom(c); 
				return c; 
			}
		}

		Random random = new Random(); 
		int rand = random.nextInt(targets.size());
		int i = 0; 

		for (BoardCell c : targets) {
			if (rand == i) {
				if (c.isDoorway())
					setLastRoom(c);
				return c; 
			}
			i++; 
		}

		return null; 
	}

	// allows player to make a suggestion
	public void makeSuggestion(Board board, BoardCell location) {
		ArrayList<Card> personGuesses = new ArrayList<Card>();
		ArrayList<Card> weaponGuesses = new ArrayList<Card>();


		for (Card c : board.getDeck()) {
			if (!(possibleCards.contains(c))) { 
				if (c.getCardType().equals(CardType.PERSON)) {
					personGuesses.add(c);
				}
				else if (c.getCardType().equals(CardType.WEAPON)) {
					weaponGuesses.add(c);
				}
			}
		}

		Random random = new Random();
		int randomPerson = random.nextInt(personGuesses.size());
		int randomWeapon = random.nextInt(weaponGuesses.size());

		lastSuggestion.person = personGuesses.get(randomPerson).getCardName();
		lastSuggestion.weapon = weaponGuesses.get(randomWeapon).getCardName();
		lastSuggestion.room = board.getRooms().get(location.getInitial());
	}

	public void setLastRoom(BoardCell c) {
		lastRoom = c.getInitial(); 
	}
	public void setLastRoom(char c) {
		lastRoom = c;
	}

	public char getLastRoom() {
		return lastRoom;
	}

	public Solution getLastSuggestion() {
		return lastSuggestion;
	}
}
