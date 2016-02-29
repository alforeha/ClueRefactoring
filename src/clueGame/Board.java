package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class Board {
	private int numRows, numColumns;
	public static final int BOARD_SIZE = 50;
	private BoardCell[][] board;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board(String layout, String legend) {
		// TODO Auto-generated constructor stub
	}
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public void initialize() {
		
	}
	public void loadRoomConfig() {
		
	}
	public void loadBoardConfig() {
		
	}
	public void calcAdjacencies() {
		
	}
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public static int getBoardSize() {
		return BOARD_SIZE;
	}
	public BoardCell[][] getBoard() {
		return board;
	}
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	public Map<BoardCell, LinkedList<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public String getBoardConfigFile() {
		return boardConfigFile;
	}
	public String getRoomConfigFile() {
		return roomConfigFile;
	}
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public void calcTargets(int x, int y, int steps) {
		
	}
}
