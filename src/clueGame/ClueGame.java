package clueGame;

import javax.swing.JFrame;

import org.junit.Before;

public class ClueGame extends JFrame{
	
	
	public Board board;
	
	public ClueGame(){
		board = new Board();
		board.initialize();
		this.setSize(810, 840);
		this.add(board);
	}
	
	public static void main(String [] args){
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
