package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		/*
		for (BoardCell b : targets){
			if (b.isDoorway())
				return b;
		}
		*/
		
		return new BoardCell(0,0);
	}
	public void makeAccusation(){
		
	}
	public void makeSuggestion(Board board, BoardCell location){
		
	}
}
