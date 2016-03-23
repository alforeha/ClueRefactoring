package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private int row, column;
	private Color color;
	private Set<Card> myCards;
	private Set<Card> seenCards;
	
	public Player(String playerName, int row, int column, Color color) {
		
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new HashSet<Card>();
		seenCards = new HashSet<Card>();
	}

	public Card disproveSuggestion(Solution suggestion){
		return null;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return column;
	}

	public void setCol(int col) {
		this.column = col;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Set<Card> getMyCards() {
		return myCards;
	}

	public void giveCard(Card card) {
		myCards.add(card);
	}
}
