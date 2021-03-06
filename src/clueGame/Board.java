package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.lang.reflect.Field;



public class Board extends JPanel implements MouseListener{
	private int numRows, numColumns;
	private int numDoors = 0;
	public static final int BOARD_SIZE = 50;
	private BoardCell[][] board;
	public static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets = new HashSet<BoardCell>();;
	private String boardConfigFile;
	private String roomConfigFile;
	private Player[] players;
	private int count = 0;
	private Card[] cards;
	public 	ArrayList<Card> backup = new ArrayList<Card>();
	public Solution solution;
	private boolean turnOver = true;
	private boolean suggestedCard;
	private MakeSuggestionDialog msd;
	public ControlGUI cg;

	

	public ControlGUI getCg() {
		return cg;
	}

	public void setCg(ControlGUI cg) {
		this.cg = cg;
	}

	public boolean isSuggestedCard() {
		return suggestedCard;
	}

	public void setSuggestedCard(boolean suggestedCard) {
		this.suggestedCard = suggestedCard;
	}

	public boolean isTurnOver() {
		return turnOver;
	}

	public void setTurnOver(boolean turnOver) {
		this.turnOver = turnOver;
	}

	public ArrayList<Card> getBackup() {
		return backup;
	}

	//@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numColumns; c++){
				board[r][c].draw(g, board);
			}
		}
		for(int r = 0; r < numRows; r++){
			for(int c = 0; c < numColumns; c++){
				board[r][c].labelRooms(g, board);
			}
		}
		for(int p = 0; p < players.length; p++){
			players[p].draw(g, board);
		}
	
	
	}
	
	public Board(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
		board = new BoardCell[BOARD_SIZE][BOARD_SIZE];
	}
	public Board() {
		boardConfigFile = "Clue Layout.csv";
		roomConfigFile = "Legend.txt";
		board = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		initialize();
	}
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayers();
			loadCards();
			addMouseListener(this);
			msd = new MakeSuggestionDialog(this, cg);
		} catch (FileNotFoundException e) {
			System.out.println("Error loading config file " + e);
		} catch (BadConfigFormatException e) {
			System.out.println("There was a config error.");
		}
		dealCards();
		calcAdjacencies();
	}
	
	public boolean checkAccusation(String name, String weapon, String room){
		if(solution.person.equals(name) && solution.room.equals(room) && solution.weapon.equals(weapon))
			return true;
		else return false;
	}
	
	private void dealCards() {
		Random rand = new Random();
		int solutionPlayer = rand.nextInt(6);
		int solutionWeapon = rand.nextInt(6) + 6;
		int solutionRoom = rand.nextInt(9) + 12;
		
		solution = new Solution(cards[solutionPlayer].getName(), cards[solutionWeapon].getName(), cards[solutionRoom].getName());
		
		cards[solutionPlayer] = null;
		cards[solutionWeapon] = null;
		cards[solutionRoom] = null;
		
		int randPlayer = rand.nextInt(6);
		
		for(int i = 0; i < cards.length; i++){
			randPlayer = rand.nextInt(6);
			if(cards[i] == null){
				continue;
			}
			else if(players[randPlayer].getMyCards().size() < 3){
				players[randPlayer].giveCard(cards[i]);
				players[randPlayer].seeCard(cards[i]);
				cards[i] = null;
			}
			else{
				i--;
			}
		}

	}
	
	public Solution getSolution() {
		return solution;
	}

	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked){
		int startIndex = 0;
		for(int i = 0; i < 6; i++){
			if(players[i].getPlayerName().equals(accusingPlayer)){
				startIndex = i;
				break;
			}
		}
		
		for (Player player: players){
			if (player.getPlayerName().equals(suggestion.person))
				player.setLocation(clicked);
		}
		
		Card answer = null;
		for(int i = (startIndex+1)%(players.length); i != startIndex; i = (i+1)%(players.length)){
			answer = players[i].disproveSuggestion(suggestion);
			if (answer != null) return answer;
		}
		
		return answer;
	}
	

	private void loadCards() throws FileNotFoundException {
		cards = new Card[21];
		FileReader fin = new FileReader("Cards.txt");	// Initializing a bunch of variables.
		Scanner in = new Scanner(fin);
		String temp;
		for(int i = 0; i < 6; i++){
			temp = in.nextLine();
			cards[i] = new Card(CardType.PERSON, temp);
		}
		for(int i = 0; i < 6; i++){
			temp = in.nextLine();
			cards[i+6] = new Card(CardType.WEAPON, temp);
		}
		for(int i = 0; i < 9; i++){
			temp = in.nextLine();
			cards[i+12] = new Card(CardType.ROOM, temp);
		}
		for(int i = 0; i < 21 ; i++){
			backup.add(cards[i]);
		}	
	}
	private void loadPlayers() throws FileNotFoundException{
		players = new Player[6];
		FileReader fin = new FileReader("Players.txt");	// Initializing a bunch of variables.
		Scanner in = new Scanner(fin);
		String temp;
		int i=0;
		while (in.hasNextLine()){
			temp = in.nextLine();
			String name = temp.substring(0, temp.indexOf(','));
			temp = temp.substring(temp.indexOf(',')+1);
			String color = temp.substring(0, temp.indexOf(','));
			temp = temp.substring(temp.indexOf(',')+1);
			String sRow = temp.substring(0, temp.indexOf(','));
			temp = temp.substring(temp.indexOf(',')+1);
			String sCol = temp;
			if(i == 0)
				players[i] = new HumanPlayer(name, Integer.parseInt(sRow), Integer.parseInt(sCol), convertColor(color));
			else
				players[i] = new ComputerPlayer(name, Integer.parseInt(sRow), Integer.parseInt(sCol), convertColor(color));
			i++;
		}
	}
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// In case the file can't be found
		FileReader fin = new FileReader(boardConfigFile);	// Initializing a bunch of variables.
		Scanner in = new Scanner(fin);
		String temp;
		int row=0, col=0;
		while (in.hasNext()) {
			temp = in.nextLine();					// Reads in one line at a time
			Scanner line = new Scanner(temp);
			line.useDelimiter(",");					// Separates it based on commas
			boolean labelRoomHere = false;
			while (line.hasNext()) {
				String spot = line.next();			// Reads in each letter and...
				char initial = spot.charAt(0);
				char direc = 'N';
				if (spot.length()>1 && spot.charAt(1) != 'N') {
					direc = spot.charAt(1);
					numDoors++;
				}
				
				if(Character.isLowerCase(initial)){
					labelRoomHere = true;
					initial = Character.toUpperCase(initial);
				}
				
				if (!rooms.keySet().contains(initial)) {
					line.close();
					throw new BadConfigFormatException();
				}
				board[row][col] = new BoardCell(row, col, initial, direc, labelRoomHere);	// puts it in the appropriate place on the grid
				col++;		
				labelRoomHere = false;
			}
			if (row > 0 && numColumns != col){
				line.close();
				throw new BadConfigFormatException();
			}
			numColumns = col;
			col=0;
			row++;
			line.close();
		}
		numRows = row;
		in.close();
		
	}
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		rooms = new HashMap<Character, String>();			// Initializing a bunch of variables.
		FileReader fin = new FileReader(roomConfigFile);
		Scanner in = new Scanner(fin);
		String temp;
		char c;
		String roomName;
		String type;
		in.useDelimiter(", ");
		while (in.hasNext()){
			temp = in.nextLine();
			Scanner line = new Scanner(temp);
			line.useDelimiter(", ");
			try{
				c = line.next().charAt(0);
				roomName = line.next();
				type = line.next();
			} catch(NoSuchElementException e){
				line.close();
				throw new BadConfigFormatException();
			}
			rooms.put(c, roomName);
			line.close();
		}
		in.close();
	}

	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>(board.length, board[0].length);	// Initializing the HashMap
		for (int x=0; x < numRows; x++) {
			for (int y=0; y < numColumns; y++) {
				LinkedList<BoardCell> ll = new LinkedList<BoardCell>();	// Temporary list to store the adjacent cells in

				if (board[x][y].isDoorway()){
					switch (board[x][y].getDirection()){
					case DOWN:
						ll.add(board[x+1][y]);
						break;
					case LEFT:
						ll.add(board[x][y-1]);
						break;
					case NONE:
						break;
					case RIGHT:
						ll.add(board[x][y+1]);
						break;
					case UP:
						ll.add(board[x-1][y]);
						break;
					default:
						break;
					}
					adjMatrix.put(board[x][y], ll);
					continue;
				}
				

				else if (board[x][y].isRoom()) {
					adjMatrix.put(board[x][y], ll);
					continue;
				}

				else {
					if (x - 1 >= 0 && (board[x-1][y].isWalkway() || (board[x-1][y].isDoorway() && board[x-1][y].getDirection() == DoorDirection.DOWN)))
						ll.add(board[x-1][y]);			// Adds the top cell if it isn't a room
					
					if (y - 1 >= 0 && (board[x][y-1].isWalkway() || (board[x][y-1].isDoorway() && board[x][y-1].getDirection() == DoorDirection.RIGHT)))
						ll.add(board[x][y-1]);			// Adds the left cell if it isn't a room
					
					if (x + 1 < numRows && (board[x+1][y].isWalkway() || (board[x+1][y].isDoorway() && board[x+1][y].getDirection() == DoorDirection.UP)))
						ll.add(board[x+1][y]);		// Adds the bottom cell if it isn't a room
					
					if (y + 1 < numColumns && (board[x][y+1].isWalkway() || (board[x][y+1].isDoorway() && board[x][y+1].getDirection() == DoorDirection.LEFT)))
						ll.add(board[x][y+1]);	// Adds the right cell if it isn't a room
					
					adjMatrix.put(board[x][y], ll);												// Puts the list of adjacent cells into the map
				}
			}			
		}
	}
	public void calcTargets(BoardCell cell, int pathLength, Set<BoardCell> visited){
		Set<BoardCell> myVisited = new HashSet<BoardCell>(visited);		// Initializes a local HashSet of visited cells
		// This is to ensure the lists of past recursions don't get accidentally manipulated
		myVisited.add(cell);											// Adds the current cell to the list of cells that have been visited
		if (pathLength == 0 || cell.isDoorway()) { 
			targets.add(cell); return; 							// Adds the cell to the set of targets if we're at the end
		}
		for (BoardCell o: adjMatrix.get(cell)) {				// Iterates through each adjacent cell
			if (!myVisited.contains(o)) {						// If we haven't visited this adjacent cell...
				calcTargets(o, pathLength-1, myVisited);		// Let's move to it!
			}
		}
	}
	public void calcTargets(int x, int y, int pathLength) {
		targets.clear();
		
		Set<BoardCell> visited = new HashSet<BoardCell>();
		visited.add(board[x][y]);
		for (BoardCell c : adjMatrix.get(board[x][y])){
			calcTargets(c, pathLength - 1, visited);
		}
		if(targets.contains(board[x][y]))
			targets.remove(board[x][y]);
	}
	
	public void drawTargets(Graphics g){
		for (BoardCell cell : targets){
			g.setColor(Color.RED);
			g.fillRect(cell.CELL_WIDTH*cell.getColumn(), cell.CELL_HEIGHT*cell.getRow(), cell.CELL_WIDTH, cell.CELL_HEIGHT);		
		}
	}


	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public Card[] getCards() {
		return cards;
	}
	public Player[] getPlayers() {
		return players;
	}	
	public Set<BoardCell> getTargets() {
		return targets;
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
	public String getBoardConfigFile() {
		return boardConfigFile;
	}
	public String getRoomConfigFile() {
		return roomConfigFile;
	}
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	public Map<BoardCell, LinkedList<BoardCell>> getAdjMatrix() {
		return adjMatrix;
	}
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell) {
		return adjMatrix.get(cell);
	}
	public int getNumDoors() {
		return numDoors;
	}

	public void nextPlayer() {		
		count++;
		count = count % players.length;
		//System.out.println(count);
	}

	public int getCount() {
		return count;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setSuggestedCard(false);
		if(numColumns*BoardCell.CELL_WIDTH > e.getX() && numRows*BoardCell.CELL_HEIGHT > e.getY() && !turnOver){
			BoardCell whichCell = null;
			for (BoardCell cell : targets){

				if (containsClick(e.getX(),e.getY(), cell)){
					whichCell = cell;					
					
					break;
				}
			}
			if (whichCell != null){
				players[count].setLocation(whichCell);
				repaint();
			if (whichCell.isDoorway()){
					setSuggestedCard(true);
					msd = new MakeSuggestionDialog(this, cg);
					
					msd.setVisible(true);
					msd.setModal(true);
				}
				turnOver = true;
				count++;
							
			}
			else
				JOptionPane.showMessageDialog(null, "Select a valid target", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public MakeSuggestionDialog getMsd() {
		return msd;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	public boolean containsClick(int mouseX, int mouseY, BoardCell cell) {
		Rectangle rect = new Rectangle(cell.CELL_WIDTH*cell.getColumn(), cell.CELL_HEIGHT*cell.getRow(), cell.CELL_WIDTH, cell.CELL_HEIGHT);
		getGraphics().setColor(Color.BLACK);
		getGraphics().drawRect(cell.CELL_WIDTH*cell.getColumn(), cell.CELL_HEIGHT*cell.getRow(), cell.CELL_WIDTH, cell.CELL_HEIGHT);
		//Rectangle bounds = board.getBounds();
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}

	public void setCount(int i) {
		count = i;	
	}
		
}
