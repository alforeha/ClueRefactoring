import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

// Contains the grid and adjacency lists
// DON'T CHANGE THIS UNTIL TOMORROW
public class IntBoard {


	private LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();	// List of adjacent cells
	private BoardCell[][] grid = null;
	private Map<BoardCell, ArrayList<BoardCell>> adjacencies = null;	

	// Calculates the adjacency list for each grid cell, stores in a map
	public void calcAdjacencies() {
		adjacencies = new HashMap<BoardCell, ArrayList<BoardCell>>(grid.length, grid[0].length);
		for (int x=0; x<grid.length; x++) {
			for (int y=0; y < grid[x].length; y++) {
				ArrayList<BoardCell> al = new ArrayList<BoardCell>();
				if (x - 1 >= 0) al.add(grid[x-1][y]);
				if (y - 1 >= 0) al.add(grid[x][y-1]);
				if (x + 1 < grid.length) al.add(grid[x+1][y]);
				if (y + 1 < grid[x].length) al.add(grid[x][y+1]);
				adjacencies.put(grid[x][y], al);				
			}			
		}		
	}	

	// Calculates the targets that are pathLength away from the startCell, stores in local variable
	private void calcTargets(BoardCell startCell, int pathLength, Set<BoardCell> visited, Set<BoardCell> targetSet) {
		Set<BoardCell> myVisited = new HashSet<BoardCell>(visited);
		myVisited.add(startCell);
		if (pathLength == 0) { 
			targetSet.add(startCell); return; 
		}
		for (BoardCell o: adjacencies.get(startCell)) {
			if (!myVisited.contains(o)) {				
				calcTargets(o, pathLength-1, myVisited, targetSet);
			}
		}
	}

	public IntBoard(int X, int Y, String filename) {
		super();
		grid = new BoardCell[X][Y];
		try {
			FileReader fin = new FileReader(filename);
			Scanner in = new Scanner(fin);
			String temp;
			int x=0, y=0;
			while (in.hasNext()) {
				temp = in.nextLine();
				Scanner line = new Scanner(temp);
				line.useDelimiter(",");
				while (line.hasNext()) {
					String spot = line.next();
					//System.out.print(spot);
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

	public Set<BoardCell> getTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> targetSet = new HashSet<BoardCell>(); // Set of potential targets to move to
		Set<BoardCell> visited = new HashSet<BoardCell>(); 
		calcTargets(startCell, pathLength, visited, targetSet);
		return targetSet;
	}

	public LinkedList<BoardCell> getAdjList(BoardCell cell) {
		return adjList;
	}

	public BoardCell getCell(int row, int col){
		return grid[row][col];
	}

	public static void main(String[] args) {
		IntBoard x = new IntBoard(4,4, "Clue Layout2.csv");
		x.calcAdjacencies();
		//System.out.println(x.getTargets(x.getCell(0,0), 3));
		BoardCell cell = x.getCell(3, 3);
		Set<BoardCell> targets = x.getTargets(cell, 3);
		System.out.println(targets);
	}
}
