package clueGame;
// Represents one cell in the grid
public class BoardCell {
	private int x, y = 0;
	public String type;
	private char initial;
	private DoorDirection direction;
	// TODO: I don't think anything more needs to be done to this for now

	public BoardCell(int x, int y, char type, char direction) {
		super();
		this.x = x;
		this.y = y;		
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
		x = x2;
		y = y2;
		initial = 'X';
		direction = DoorDirection.NONE;
	}

	@Override
	public String toString() {
		return "BoardCell [x=" + x + ", y=" + y + ", type=" + type + "]";
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

	public Object getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getType() {
		return type;
	}

	public DoorDirection getDirection() {
		return direction;
	}
	
	
	
}
