package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public final static int NUM_CARDS_IN_SOLUTION = 3;
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets){

		for (BoardCell b : targets){
			if (b.isDoorway() && (b.getRow() != prevRow || b.getColumn() != prevCol)){
				setLocation(b);
				return b;
			}
		}
		
		int i = 0;
		Random rand = new Random();
		int j = rand.nextInt(targets.size());
		
		for (BoardCell b : targets){
			if (i == j){
				setLocation(b);
				return b;}
			i++;
		}
		
		return null;
	}
	
	public Solution makeAccusation(Board board){
		Solution accusation = new Solution("","","");
		ArrayList<Card> guessablePeople = new ArrayList<Card>();
		ArrayList<Card> guessableWeapons = new ArrayList<Card>();
		ArrayList<Card> guessableRooms = new ArrayList<Card>();
	
		
		for(Card c : board.getBackup()){			
			boolean hasBeenSeen = false;
						
			for(Card seen : seenCards){
				
				if(seen.getName().equals(c.getName()))
					hasBeenSeen = true;
			}
			
			if(!hasBeenSeen){				
				if(c.getType().equals(CardType.PERSON))
					guessablePeople.add(c);
				if(c.getType().equals(CardType.WEAPON))
					guessableWeapons.add(c);
				if(c.getType().equals(CardType.ROOM))
					guessableRooms.add(c);
			}
		}		
		
		int i = 0;
		Random rand = new Random();
		int r = rand.nextInt(guessablePeople.size());
		for(Card c : guessablePeople){
			if (i == r)
				accusation.setPerson(c.getName());
			i++;
		}
		
		i = 0;
		r = rand.nextInt(guessableWeapons.size());
		for(Card c : guessableWeapons){
			if (i == r)
				accusation.setWeapon(c.getName());
			i++;
		}
		
		i = 0;
		r = rand.nextInt(guessableRooms.size());
		for(Card c : guessableRooms){
			if (i == r)
				accusation.setRoom(c.getName());
			i++;
		}
		
		return accusation;
	}
	
	public Solution makeSuggestion(Board board, BoardCell location){
		Solution guess = new Solution("","",board.getRooms().get(location.getInitial()));
		
		ArrayList<Card> guessablePeople = new ArrayList<Card>();
		ArrayList<Card> guessableWeapons = new ArrayList<Card>();
	
		
		for(Card c : board.getBackup()){			
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
				guess.setPerson(c.getName());
			i++;
		}
		
		i = 0;
		r = rand.nextInt(guessableWeapons.size());
		for(Card c : guessableWeapons){
			if (i == r)
				guess.setWeapon(c.getName());
			i++;
		}
		
		return guess;
	}
}
