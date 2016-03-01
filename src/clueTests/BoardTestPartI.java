package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.DoorDirection;

public class BoardTestPartI {

	Board board;
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.initialize();
	}

	@Test
	public void testRooms() {
		assertEquals(11, Board.getRooms().size());
		assertEquals("Conservatory", Board.getRooms().get('C'));
		assertEquals("Kitchen", Board.getRooms().get('K'));
		assertEquals("Ballroom", Board.getRooms().get('B'));
		assertEquals("Billiard room", Board.getRooms().get('R'));
		assertEquals("Library", Board.getRooms().get('L'));
		assertEquals("Study", Board.getRooms().get('S'));
		assertEquals("Dining room", Board.getRooms().get('D'));
		assertEquals("Lounge", Board.getRooms().get('O'));
		assertEquals("Hall", Board.getRooms().get('H'));
		assertEquals("Closet", Board.getRooms().get('X'));
		assertEquals("Walkway", Board.getRooms().get('W'));
	}
	
	@Test
	public void loadConfig() {
		assertEquals(22, board.getNumRows());
		assertEquals(23, board.getNumColumns());
		
		assertEquals(DoorDirection.RIGHT, board.getCellAt(4, 3).getDirection());
		assertEquals(DoorDirection.UP, board.getCellAt(14, 11).getDirection());
		assertEquals(DoorDirection.LEFT, board.getCellAt(2, 13).getDirection());
		assertEquals(DoorDirection.DOWN, board.getCellAt(5, 15).getDirection());
		
		assertTrue(!board.getCellAt(0, 0).isDoorway());
		
		assertEquals(16, board.getNumDoors());

		assertEquals('C', board.getCellAt(0, 0).getInitial());
		assertEquals('R', board.getCellAt(0, 7).getInitial());
		assertEquals('W', board.getCellAt(0, 4).getInitial());
		assertEquals('B', board.getCellAt(10, 0).getInitial());
		assertEquals('K', board.getCellAt(20, 0).getInitial());
		assertEquals('X', board.getCellAt(7, 9).getInitial());
		assertEquals('D', board.getCellAt(17, 9).getInitial());
		assertEquals('L', board.getCellAt(0, 13).getInitial());
		assertEquals('O', board.getCellAt(21, 22).getInitial());
		assertEquals('H', board.getCellAt(10, 22).getInitial());
		assertEquals('S', board.getCellAt(0, 22).getInitial());
	}

	
	@Test (expected = FileNotFoundException.class)
	public void testFileOpening() throws  FileNotFoundException, BadConfigFormatException{
		Board board = new Board("not a file", "not a file");
		board.loadBoardConfig();
		board.loadRoomConfig();
	}
}
