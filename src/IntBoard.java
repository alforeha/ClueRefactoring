import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

// Contains the grid and adjacency lists
public class IntBoard {
	
	private Set<BoardCell> targets = new HashSet<BoardCell>(); // Set of potential targets to move to
	private LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();	// List of adjacent cells
	
	public LinkedList<BoardCell> getAdjList() {
		return adjList;
	}
	private void calcAdjacencies() {
		
	}	// Calculates the adjacency list for each grid cell, stores in a map
	private void calcTargets(BoardCell startCell, int pathLength) {
		
	} // Calculates the targets that are pathLength away from the startCell, stores in local variable
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	
}
