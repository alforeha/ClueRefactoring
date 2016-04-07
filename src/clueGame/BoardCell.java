package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row, column = 0;
	public static final int CELL_WIDTH = 80;
	public static final int CELL_HEIGHT = 30;
	public String type;
	private char initial;
	private DoorDirection direction;
	private boolean labelRoomHere;

	public BoardCell(int x, int y, char type, char direction, boolean labelRoomHere) {
		super();
		this.row = x;
		this.column = y;	
		this.labelRoomHere = labelRoomHere;
		initial = type;
		switch (direction){
		case 'U':
			this.direction = DoorDirection.UP;
			break;
		
		case 'D':
			this.direction = DoorDirection.DOWN;
			break;
			
		case 'L':
			this.direction = DoorDirection.LEFT;
			break;
			
		case 'R':
			this.direction = DoorDirection.RIGHT;
			break;
			
		default:
			this.direction = DoorDirection.NONE;
			break;
		}
		
	}

	public BoardCell(int x2, int y2) {
		row = x2;
		column = y2;
		initial = 'X';
		direction = DoorDirection.NONE;
	}

	@Override
	public String toString() {
		return "BoardCell [x=" + row + ", y=" + column + ", type=" + type + "]";
	}
	
	public boolean isWalkway(){
		if (initial == 'W') return true;
		else return false;
	}
	
	public boolean isRoom(){
		if (initial != 'W' && direction == DoorDirection.NONE) return true;
		else return false;
	}
	
	public boolean isDoorway(){
		if (direction != DoorDirection.NONE) return true;
		else return false;
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getType() {
		return type;
	}

	public DoorDirection getDirection() {
		return direction;
	}

	public void draw(Graphics g, BoardCell[][] b) {
		if(this.isWalkway()){
			g.setColor(Color.CYAN);
			g.fillRect(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH, CELL_HEIGHT);
			g.setColor(Color.BLACK);
			g.drawRect(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH, CELL_HEIGHT);
		}
		if(!this.isWalkway()){
			g.setColor(Color.GRAY);
			g.fillRect(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH, CELL_HEIGHT);
			g.setColor(Color.BLACK);
			if(row > 0 && b[row-1][column].getInitial() != b[row][column].getInitial())
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH*(column + 1), CELL_HEIGHT*row);
			if(row == 0)
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH*(column + 1), CELL_HEIGHT*row);
			if(b[row+1][column] != null && b[row+1][column].getInitial() != b[row][column].getInitial())
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*(row+1), CELL_WIDTH*(column + 1), CELL_HEIGHT*(row+1));
			if(b[row+1][column] == null)
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*(row+1), CELL_WIDTH*(column + 1), CELL_HEIGHT*(row+1));
			if(column > 0 && b[row][column-1].getInitial() != b[row][column].getInitial())
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH*column, CELL_HEIGHT*(row+1));
			if(column == 0)
				g.drawLine(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH*column, CELL_HEIGHT*(row+1));
			if(b[row][column+1] != null && b[row][column+1].getInitial() != b[row][column].getInitial())
				g.drawLine(CELL_WIDTH*(column+1), CELL_HEIGHT*row, CELL_WIDTH*(column+1), CELL_HEIGHT*(row+1));
			if(b[row][column+1] == null)
				g.drawLine(CELL_WIDTH*(column+1), CELL_HEIGHT*row, CELL_WIDTH*(column+1), CELL_HEIGHT*(row+1));
		}
		if(this.isDoorway()){
			g.setColor(Color.BLUE);
			if(this.getDirection() == DoorDirection.DOWN)
				g.fillRect(CELL_WIDTH*column, CELL_HEIGHT*row + ((CELL_HEIGHT*5) / 6), CELL_WIDTH, CELL_HEIGHT / 6);
			if(this.getDirection() == DoorDirection.UP)
				g.fillRect(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH, CELL_HEIGHT / 6);
			if(this.getDirection() == DoorDirection.RIGHT)
				g.fillRect(CELL_WIDTH*column + ((CELL_WIDTH*11) / 12), CELL_HEIGHT*row, CELL_WIDTH / 12, CELL_HEIGHT);
			if(this.getDirection() == DoorDirection.LEFT)
				g.fillRect(CELL_WIDTH*column, CELL_HEIGHT*row, CELL_WIDTH / 12, CELL_HEIGHT);
		}
		
	}

	public void labelRooms(Graphics g, BoardCell[][] board) {
		if(labelRoomHere){
			g.setColor(Color.BLACK);
			g.drawString(Board.rooms.get(initial), CELL_WIDTH*column+2, CELL_HEIGHT*(row+1));
		}
	}
	
	
	
}
