package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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
	private boolean targetHighlight; 
	private boolean doorway; 

	/**
	 * BoardCell -- constructor that sets row and column values
	 * @param r = row
	 * @param c = column
	 */
	public BoardCell(int r, int c) {
		row = r; 
		column = c;
		targetHighlight = false; 
		doorway = false; 
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// GUI RELATED FUNCTIONS ---------------------------------------------------
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * drawRoom -- draws out rooms and walkways for the boardcell.
	 * @param g
	 */
	public void drawRoom(Graphics g) {
		g.setColor(new Color(1,103,90));

		// draw walkway:
		if (initial == 'W') {
			g.drawRect(column*30, row * 30, 30, 30);
			g.fillRect(column*30,row * 30, 30, 30); 
			if (targetHighlight) {
				g.setColor(new Color(95,224,208)); 
			}
			else {
				g.setColor(new Color(1,133,116));	
			}	
			g.fillRect(column*30 + 1, row*30 + 1, 28, 28); 
		}
		else {
		// draw room
			if (targetHighlight) {
				g.setColor(new Color(95,224,208)); 
			}
			else {
				g.setColor(new Color(68,66,64));
			}
			g.fillRect(column * 30 + 1, row * 30 + 1, 30, 30);
		}

	}
	
	/**
	 * drawDoor -- if the board cell has a door then a door indicator is placed
	 * in the direction of the door
	 * @param g
	 */
	public void drawDoor(Graphics g) {
		if (!isDoorway()) {
			return;
		}
		
		if (getDoorDirection().equals(DoorDirection.UP)) {
			g.setColor(new Color(0,153,188));
			g.drawRect(column * 30, row * 30, 30, 5);
			g.setColor(new Color(0,183,195));
			g.fillRect(column * 30+1, row * 30, 29, 5);
		}
		else if (getDoorDirection().equals(DoorDirection.DOWN)) {
			g.setColor(new Color(0,153,188));
			g.drawRect(column * 30, row * 30 + 25, 30, 5);
			g.setColor(new Color(0,183,195));
			g.fillRect(column * 30+1, row * 30 + 25, 29, 5);
		}
		else if (getDoorDirection().equals(DoorDirection.LEFT)){
			g.setColor(new Color(0,153,188));
			g.drawRect(column * 30, row * 30, 5, 30);
			g.setColor(new Color(0,183,195));
			g.fillRect(column * 30, row * 30+1, 5, 29);
		}
		else if (getDoorDirection().equals(DoorDirection.RIGHT)) {
			g.setColor(new Color(0,153,188));
			g.drawRect(column * 30 + 25, row * 30, 5, 30);
			g.setColor(new Color(0,183,195));
			g.fillRect(column * 30 + 25, row * 30 + 1, 5, 29);
		}		
	}

	/**
	 * drawRoomLabel -- display's room name at corresponding room
	 * @param g
	 * @param roomName -- string that holds room name
	 */
	public void drawRoomLabel(Graphics g, String roomName) {
		g.drawString(roomName, column*30 + 15, row * 30 +15 ); 
	}
	
	/**
	 * drawPlayerLoc -- display's player's circle/color within that
	 * board cell
	 * @param g
	 */
	public void drawPlayerLoc(Graphics g) {
		//g.fillOval(column*30, row*30, 30, 30);
		g.fillOval(column*30 +2 , row*30 +2, 25, 25); 
		
	}
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// GETTER/SETTERS ----------------------------------------------------------
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public int getRow(){
		return row;
	}
	public int getCol(){
		return column;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setDoorway(boolean doorway) {
		this.doorway = doorway;
	}

	public char getInitial() {
		return initial;
	} 
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public boolean getTargetHighlight() {
		return targetHighlight;
	}

	public void setTargetHighlight(boolean targetHighlight) {
		this.targetHighlight = targetHighlight;
	}

	public boolean isDoorway() {
		return doorway;
	}
	
	public boolean isRoom() {
		if(this.initial != 'W' && this.initial != 'C') {
			return true;
		}
		return false;
	}
}
