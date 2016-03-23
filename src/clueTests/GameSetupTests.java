package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {

	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.initialize();
	}
	
	@Test
	public void testLoadPeople() {
		assertEquals(6, board.getPlayers().length);
		
		assertEquals(Color.pink, board.getPlayers()[0].getColor());
		assertEquals(board.getPlayers()[0].getClass(), HumanPlayer.class);
		assertEquals(board.getPlayers()[0].getRow(), 4);
		assertEquals(board.getPlayers()[0].getCol(), 6);
		
		assertEquals(Color.white, board.getPlayers()[5].getColor());
		assertEquals(board.getPlayers()[5].getClass(), ComputerPlayer.class);
		assertEquals(board.getPlayers()[5].getRow(), 11);
		assertEquals(board.getPlayers()[5].getCol(), 10);
	}
	
	@Test
	public void testLoadCards() {
		assertEquals(21, board.getCards().length);
		
		int p=0, r=0, w=0;
		for(Card c : board.getCards()){
			switch(c.getType()){
			case PERSON:
				p++;
				break;
			case WEAPON:
				w++;
				break;
			case ROOM:
				r++;
				break;
			}
		}
		assertEquals(6, p);
		assertEquals(6, w);
		assertEquals(9, r);
		
		boolean roo = false, wep = false, per = false;
		for(Card c : board.getCards()){
			if(c.getName().equals("Miss Scarlett")){
				per = true;
			}
			if(c.getName().equals("Wrench")){
				wep = true;
			}
			if(c.getName().equals("Lounge")){
				roo = true;
			}
		}
		assertTrue(per && wep && roo);
	}

}
