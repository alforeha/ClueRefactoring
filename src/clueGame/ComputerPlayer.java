package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
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
	public void makeSuggestion(Board board, BoardCell location){
		
	}
}
