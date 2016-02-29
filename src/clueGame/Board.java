package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	private int numRows, numColumns;
	public static final int BOARD_SIZE = 50;
	private BoardCell[][] board;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets = new HashSet<BoardCell>();;
	private String boardConfigFile;
	private String roomConfigFile;

	public Board(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
		board = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		for (int x = 0; x < board.length; x++){
			for (int y = 0; y < board[x].length; y++){
				board[x][y] = new BoardCell(x, y);
			}
		}
		initialize();
	}
	public Board() {
		boardConfigFile = "ClueLayout.csv";
		roomConfigFile = "ClueLegend.txt";
	}
	
	public void initialize() {
		loadRoomConfig();
		loadBoardConfig();
		calcAdjacencies();
	}
	public void loadRoomConfig() {
		try {	// In case the file can't be found
			FileReader fin = new FileReader(roomConfigFile);	// Initializing a bunch of variables.
			Scanner in = new Scanner(fin);
			String temp;
			int row=0, col=0;
			while (in.hasNext()) {
				temp = in.nextLine();					// Reads in one line at a time
				Scanner line = new Scanner(temp);
				line.useDelimiter(",");					// Separates it based on commas
				while (line.hasNext()) {
					String spot = line.next();			// Reads in each letter and...
					char initial = spot.charAt(0);
					char direc = 'N';
					if (spot.length()>1 && spot.charAt(1) != 'N') direc = spot.charAt(1); 
					board[row][col] = new BoardCell(row, col, initial, direc);	// puts it in the appropriate place on the grid
					col++;					
				}
				col=0;
				row++;
				line.close();
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Board game file not found.");	// In case the file can't be found
		}
	}
	public void loadBoardConfig() {
		//TODO: write code to load from the board config
	}
	
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>(board.length, board[0].length);	// Initializing the HashMap
		for (int x=0; x<board.length; x++) {
			for (int y=0; y < board[x].length; y++) {
				LinkedList<BoardCell> ll = new LinkedList<BoardCell>();	// Temporary list to store the adjacent cells in
				if (x - 1 >= 0) ll.add(board[x-1][y]);					// Adds the left cell if it exists
				if (y - 1 >= 0) ll.add(board[x][y-1]);					// Adds the top cell if it exists
				if (x + 1 < board.length) ll.add(board[x+1][y]);			// Adds the right cell if it exists
				if (y + 1 < board[x].length) ll.add(board[x][y+1]);		// Adds the top cell if it exists
				adjMatrix.put(board[x][y], ll);						// Puts the list of adjacent cells into the map
			}			
		}
	}
	public void calcTargets(BoardCell cell, int pathLength, Set<BoardCell> visited){
		Set<BoardCell> myVisited = new HashSet<BoardCell>(visited);		// Initializes a local HashSet of visited cells
		// This is to ensure the lists of past recursions don't get accidentally manipulated
		myVisited.add(cell);											// Adds the current cell to the list of cells that have been visited
		if (pathLength == 0) { 
			targets.add(cell); return; 							// Adds the cell to the set of targets if we're at the end
		}
		for (BoardCell o: adjMatrix.get(cell)) {				// Iterates through each adjacent cell
			if (!myVisited.contains(o)) {						// If we haven't visited this adjacent cell...
				calcTargets(o, pathLength-1, myVisited);		// Let's move to it!
			}
		}
	}
	public void calcTargets(int x, int y, int pathLength) {
		calcTargets(board[x][x], pathLength, new HashSet<BoardCell>());
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

}
