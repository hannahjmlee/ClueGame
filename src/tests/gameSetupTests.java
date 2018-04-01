package tests;

import static org.junit.Assert.*;
import java.io.*;

import org.junit.*;

import clueGame.Board;

public class gameSetupTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp () throws IOException{
		board = Board.getInstance();
		board.setConfigFiles("ClueGameRooms.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
