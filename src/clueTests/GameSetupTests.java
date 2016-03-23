package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@Test
	public void testDeal() {
		for(Player p : board.getPlayers()){
			assertEquals(3, p.getMyCards().size());
		}
		
		Set<Card> cardList = new HashSet<Card>();
		for(Player p : board.getPlayers()){
			for(Card c : p.getMyCards()){
				if(!cardList.contains(c))
					cardList.add(c);
			}
		}
		assertEquals(18, cardList.size()); // 18 b/c 3 cards are in the solution
		
		for(Card c : cardList){
			switch(c.getType()){
			case PERSON:
				if(board.solution.person.equals(c.getName()))
					fail("Player has person solution card.");
				break;
			case WEAPON:
				if(board.solution.weapon.equals(c.getName()))
					fail("Player has weapon solution card.");
				break;
			case ROOM:
				if(board.solution.room.equals(c.getName()))
					fail("Player has room solution card.");
				break;
			}
		}
	}
	
	@Test
	public void testAcccusation(){
		board.solution.person = "Bob";
		board.solution.weapon = "Fork";
		board.solution.room = "Mines";
		
		assertTrue(board.checkAccusation("Bob", "Fork", "Mines"));
		assertFalse(board.checkAccusation("Jeff", "Fork", "Mines"));
		assertFalse(board.checkAccusation("Bob", "Spoon", "Mines"));
		assertFalse(board.checkAccusation("Bob", "Fork", "CU"));
	}
}
