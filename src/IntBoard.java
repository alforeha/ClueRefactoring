import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

// Contains the grid and adjacency lists
public class IntBoard {

	private Set<BoardCell> targets = new HashSet<BoardCell>(); // Set of potential targets to move to
	private LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();	// List of adjacent cells

	// Calculates the adjacency list for each grid cell, stores in a map
	private void calcAdjacencies() {
		// TODO: actually find the list of adjacent cells IN PART II
	}	

	// Calculates the targets that are pathLength away from the startCell, stores in local variable
	private void calcTargets(BoardCell startCell, int pathLength) {
		// TODO: actually find the target cells IN PART II
	}	

	public IntBoard(int rows, int cols) {
		super();
		// TODO: make this construct the board IN PART II
	}

	public Set<BoardCell> getTargets() {
		return null;
		// TODO: make this work IN PART II
	}

	public LinkedList<BoardCell> getAdjList() {
		return null;
		// TODO: make this work IN PART II
	}
}
