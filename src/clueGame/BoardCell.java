package clueGame;
// Represents one cell in the grid
public class BoardCell {
	private int x, y = 0;
	public String type;
	private char initial;
	private DoorDirection direction;
	// TODO: I don't think anything more needs to be done to this for now

	public BoardCell(int x, int y, String type) {
		super();
		this.x = x;
		this.y = y;		
		this.type = type;
	}

	@Override
	public String toString() {
		return "BoardCell [x=" + x + ", y=" + y + ", type=" + type + "]";
	}
	
	public boolean isWalkway(){
		return true;
	}
	
	public boolean isRoom(){
		return true;
	}
	
	public boolean isDoorway(){
		return true;
	}

	public Object getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}
	
	
	
}
