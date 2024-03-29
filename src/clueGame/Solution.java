package clueGame;
/**
 * Solution -- contains the solution to the game: who the killer is, which room it was
 * in, and which weapon was used
 * @author Hannah Lee
 * @author Savannah Paul
 */
public class Solution {
	public String person; 
	public String room; 
	public String weapon;
	
	/**
	 * Solution constructor
	 * @param person -- person's 
	 * @param weapon
	 * @param room
	 */
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Solution() {
		this.person = "null"; 
		this.room = "null";
		this.weapon = "null"; 
	}
}
