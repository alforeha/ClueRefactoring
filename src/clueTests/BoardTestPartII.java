package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;

public class BoardTestPartII {

	Board board;
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.initialize();
	}

	@Test
	public void testWalkwayAdjacent() {
		assertEquals(1, board.getAdjList(0,4).size());
		assertEquals(board.getCellAt(0, 5), board.getAdjList(0,4).get(0));

	}


	@Test
	public void testEdges(){
		//Left Test
		assertEquals(1, board.getAdjList(8,0).size());
		assertEquals(board.getCellAt(7, 0), board.getAdjList(8,0).get(0));

		//Top Test
		assertEquals(1, board.getAdjList(0,12).size());
		assertEquals(board.getCellAt(1, 12), board.getAdjList(0,12).get(0));

		//Bottom Test
		assertEquals(2, board.getAdjList(21, 6).size());
		assertTrue(board.getAdjList(21,6).contains(board.getCellAt(20, 6)));
		assertTrue(board.getAdjList(21,6).contains(board.getCellAt(21, 7)));

		//Right Test
		assertEquals(1, board.getAdjList(6, 22).size());
		assertEquals(board.getCellAt(6, 21), board.getAdjList(6,22).get(0));
		
	}
	
	// There are tests for locations that are beside a room cell that isn't a doorway above, specifically any of the edge tests.
	
	@Test
	public void testDoorways(){
		assertTrue(board.getAdjList(4,4).contains(board.getCellAt(4, 3)));
		assertTrue(board.getAdjList(14,4).contains(board.getCellAt(13, 4)));
		assertTrue(board.getAdjList(2,12).contains(board.getCellAt(2, 13)));
		assertTrue(board.getAdjList(13,11).contains(board.getCellAt(14, 11)));
	}
	
	@Test
	public void testInDoorway(){
		assertEquals(1, board.getAdjList(4,3).size());
		assertEquals(board.getCellAt(4, 4), board.getAdjList(4,3).get(0));
	}
	
	@Test
	public void testWalkwayTargets(){
		board.calcTargets(0, 4, 1);
		assertEquals(1, board.getTargets().size());
		assertTrue(board.getTargets().contains(board.getCellAt(0, 5)));
		
		board.calcTargets(0, 4, 2);
		assertEquals(2, board.getTargets().size());
		
		board.calcTargets(0, 4, 3);
		assertEquals(2, board.getTargets().size());
		
	}
	
	@Test
	public void testEnteringRoom(){
		board.calcTargets(4, 4, 1);
		assertEquals(3, board.getTargets().size());
		assertTrue(board.getTargets().contains(board.getCellAt(4, 3)));
	}
	
	@Test
	public void testLeavingRoom(){
		board.calcTargets(4, 3, 1);
		assertEquals(1, board.getTargets().size());
		assertTrue(board.getTargets().contains(board.getCellAt(4, 4)));
	}
}
