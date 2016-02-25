import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

// Contains the grid and adjacency lists
// DON'T CHANGE THIS UNTIL TOMORROW
public class IntBoard {

	private Set<BoardCell> targets = new HashSet<BoardCell>(); // Set of potential targets to move to
	private LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();	// List of adjacent cells
	private BoardCell[][] grid = null;
	private Map<BoardCell, BoardCell> adjacencies = null; 

	// Calculates the adjacency list for each grid cell, stores in a map
	public void calcAdjacencies() {
		//adjacencies.put(new BoardCell(), new BoardCell());
	}	

	// Calculates the targets that are pathLength away from the startCell, stores in local variable
	public void calcTargets(BoardCell startCell, int pathLength) {
		// TODO: actually find the target cells IN PART II
	}	

	public IntBoard(int X, int Y) {
		super();
		grid = new BoardCell[X][Y];
		try {
			FileReader fin = new FileReader("Clue Layout.csv");
			Scanner in = new Scanner(fin);
			String temp;
			int x=0, y=0;
			while (in.hasNext()) {
				temp = in.nextLine();
				Scanner line = new Scanner(temp);
				line.useDelimiter(",");
				while (line.hasNext()) {
					String spot = line.next();
					System.out.print(spot);
					//System.out.print("\t");
					grid[y][x] = new BoardCell(x, y, spot);
					x++;					
				}
				//System.out.println();
				x=0;
				y++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Board game file not found.");
		}		
	}

	public Set<BoardCell> getTargets() {
		return null;
		// TODO: make this work IN PART II
	}

	public LinkedList<BoardCell> getAdjList(BoardCell cell) {
		return null;
		// TODO: make this work IN PART II
	}
	
	public BoardCell getCell(int row, int col){
		return null;
		// TODO: make this work IN PART II
	}
	
	public static void main(String[] args) {
		IntBoard x = new IntBoard(26,13);
	}
}
