// Represents one cell in the grid
public class BoardCell {
	public int x, y = 0;
	public String type;
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
}
