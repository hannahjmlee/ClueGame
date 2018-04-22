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

	public BoardCell(int r, int c) {
		row = r; 
		column = c;
		targetHighlight = false; 
		doorway = false; 
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
	
	public boolean isRoom() {
		if(this.initial != 'W' && this.initial != 'C') {
			return true;
		}
		return false;
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

	public boolean getTargetHighlight() {
		return targetHighlight;
	}

	public void setTargetHighlight(boolean targetHighlight) {
		this.targetHighlight = targetHighlight;
	}
	
	// GUI ------------------------------------
	/**
	 * drawRoom -- draws out rooms and walkways for the boardcell.
	 * @param g
	 */
	public void drawRoom(Graphics g) {
		g.setColor(Color.GRAY);

		// draw walkway:
		if (initial == 'W') {
			g.drawRect(column*20, row * 20, 20, 20);
			if (targetHighlight) {
				g.setColor(new Color(150,211,241));
			}
			else {
				g.setColor(Color.YELLOW);	
			}	
			g.fillRect(column*20 + 1, row*20 + 1, 19, 19); 
		}
		else {
		// draw room
			if (targetHighlight) {
				g.setColor(new Color(150,211,241));
			}
			else {
				g.setColor(Color.GRAY.brighter());
			}
			g.fillRect(column * 20 + 1, row * 20 + 1, 20, 20);
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
		g.setColor(Color.BLUE); 
		if (getDoorDirection().equals(DoorDirection.UP)) {
			g.drawRect(column * 20, row * 20, 20, 5);
			g.fillRect(column * 20 + 1, row * 20 + 1, 19, 4);
		}
		else if (getDoorDirection().equals(DoorDirection.DOWN)) {
			g.drawRect(column * 20, row * 20 + 14, 20, 5);
			g.fillRect(column * 20 + 1, row * 20 + 14 + 1, 19, 4);
		}
		else if (getDoorDirection().equals(DoorDirection.LEFT)){
			g.drawRect(column * 20, row * 20, 5, 20);
			g.fillRect(column * 20 + 1, row * 20 + 1, 4, 19);
		}
		else if (getDoorDirection().equals(DoorDirection.RIGHT)) {
			g.drawRect(column * 20 + 14, row * 20, 5, 20);
			g.fillRect(column * 20 + 14 + 1, row * 20 + 1, 4, 19);
		}		
	}

	/**
	 * drawRoomLabel -- display's room name at corresponding room
	 * @param g
	 * @param roomName -- string that holds room name
	 */
	public void drawRoomLabel(Graphics g, String roomName) {
		g.drawString(roomName, column*20 , row * 20+15); 
	}
	
	/**
	 * drawPlayerLoc -- display's player's circle/color within that
	 * board cell
	 * @param g
	 */
	public void drawPlayerLoc(Graphics g) {
		g.fillOval(column*20, row*20, 20, 20); 
		
	}
}
