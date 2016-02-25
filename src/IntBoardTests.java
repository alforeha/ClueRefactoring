import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {

	// THIS CLASS MUST WORK IN PART II WITH A 4X4 GAME BOARD, NOT THE MATRIX WE MADE
	
	// TODO: Determine where the @before is supposed to go
	@Before
	IntBoard board = new IntBoard(4,4);
	
	@Test
	public void testTLCorner(){
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
		// tests if the cell to the right and the one directly down from the TL corner are
		// in the list, and that they are the only two in the list.
	}
	
	@Test
	public void testBRCorner(){
		BoardCell cell = board.getCell(3,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
		// tests if the cell to the left and the one directly up from the BR corner are
		// in the list, and that they are the only two in the list.
	}
	
	@Test
	public void testREdge(){
		BoardCell cell = board.getCell(1,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(3, testList.size());
		// tests if the cells to the left, up, and down from a right edge are
		// in the list, and that they are the only ones in the list.
	}
	
	@Test
	public void testLEdge(){
		BoardCell cell = board.getCell(2,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertEquals(3, testList.size());
		// tests if the cells to the right, up, and down from a left edge are
		// in the list, and that they are the only ones in the list.
	}
	
	@Test
	public void testC2M(){ // C2M = Column 2, Middle
		BoardCell cell = board.getCell(1,1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
		// tests if the cells in all four directions from [1,1] are
		// in the list, and that they are the only ones in the list.
	}
	
	@Test
	public void test2FLM(){	// 2FLM = 2 From Last Middle
		BoardCell cell = board.getCell(2,2);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
		// tests if the cells in all four directions from [2,2] are
		// in the list, and that they are the only ones in the list.
	}

	@Test
	public void pathCreation1(){
		// TODO: test path creation
	}

	@Test
	public void pathCreation2(){
		// TODO: test path creation
	}

	@Test
	public void pathCreation3(){
		// TODO: test path creation
	}

	@Test
	public void pathCreation4(){
		// TODO: test path creation
	}

	@Test
	public void pathCreation5(){
		// TODO: test path creation
	}

	@Test
	public void pathCreation6(){
		// TODO: test path creation
	}
	
}
