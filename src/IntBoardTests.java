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
		// TODO: test right edge
	}
	
	@Test
	public void testLEdge(){
		// TODO: test left edge
	}
	
	@Test
	public void testC2M(){
		// TODO: test second column middle (C2M = Column 2 Middle)
	}
	
	@Test
	public void test2FLM(){
		// TODO: test second from last column, middle of grid (2FLM = 2 From Last Middle)
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
