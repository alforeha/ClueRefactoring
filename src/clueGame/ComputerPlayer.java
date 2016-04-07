package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		for (BoardCell b : targets){
			if (b.isDoorway() && (b.getX() != prevRow || b.getY() != prevCol)){
				return b;
			}
		}
		
		int i = 0;
		Random rand = new Random();
		int j = rand.nextInt(targets.size());
		
		for (BoardCell b : targets){
			if (i == j)
				return b;
			i++;
		}
		
		return null;
	}
	
	public void makeAccusation(){
		
	}
	
	public Solution makeSuggestion(Board board, BoardCell location){
		Solution guess = new Solution("","",board.getRooms().get(location.getInitial()));
		
		ArrayList<Card> guessablePeople = new ArrayList<Card>();
		ArrayList<Card> guessableWeapons = new ArrayList<Card>();
		
		for(Card c : board.getCards()){
			
			boolean hasBeenSeen = false;
			for(Card seen : seenCards){
				if(seen.getName().equals(c.getName()))
					hasBeenSeen = true;
			}
			
			if(!hasBeenSeen){
				
				if(c.getType().equals(CardType.PERSON))
					guessablePeople.add(c);
				else if(c.getType().equals(CardType.WEAPON))
					guessableWeapons.add(c);
			}
		}
		
		int i = 0;
		Random rand = new Random();
		int r = rand.nextInt(guessablePeople.size());
		for(Card c : guessablePeople){
			if (i == r)
				guess.person = c.getName();
			i++;
		}
		
		i = 0;
		r = rand.nextInt(guessableWeapons.size());
		for(Card c : guessableWeapons){
			if (i == r)
				guess.weapon = c.getName();
			i++;
		}
		
		return guess;
	}
}
