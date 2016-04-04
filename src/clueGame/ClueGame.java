package clueGame;

import javax.swing.JFrame;

import org.junit.Before;

public class ClueGame extends JFrame{
	
	
	public Board board;
	
	public ClueGame(){
		board = new Board();
		board.initialize();
		this.setSize(1060, 840);
		this.add(board);
		DetectiveNotes d = new DetectiveNotes();
		d.setSize(500,500);
		d.setVisible(true);
	}
	
	public static void main(String [] args){
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
