package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class ClueGame extends JFrame implements MouseListener{

	public Board board;
	public DetectiveNotes d;
	public ControlGUI cg;
	private static final int DIE = 6;
	private boolean ifDone = false;


	public ClueGame(){
		board = new Board();
		board.initialize();
		addMouseListener(this);
		setSize(1300, 860);
		add(board, BorderLayout.CENTER);
		cg = new ControlGUI(this);
		cg.setSize(300, 840);
		add(cg, BorderLayout.SOUTH);
		d = new DetectiveNotes();
		d.setSize(700, 400);
		add(addMyCards(), BorderLayout.EAST);
	}

	public static void main(String [] args){
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(null, "You are Miss Scarlett, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
		game.makeMenu();

	}

	private int rollDie(){
		Random rand = new Random();
		int j = rand.nextInt(DIE)+1;
		return j;
	}


	public boolean doTurn(Player player) {
		ifDone = false;
		if(!ifDone){
			String playerName = player.getPlayerName();
			 int row = player.getRow();
			 int col = player.getCol();
			 Color color = player.getColor();
			 cg.setRoll(rollDie());
			 cg.setPlayerName(playerName);
			 cg.rollField.setText(Integer.toString(cg.getRoll()));
			 cg.nameField.setText(playerName);

			 if (player.getClass() == HumanPlayer.class){			
				 board.calcTargets(row, col, cg.getRoll());
				 Set<BoardCell> targets = board.getTargets();
				 board.drawTargets(board.getGraphics());		
				
			 }

			 if (player.getClass() == ComputerPlayer.class){

				 ComputerPlayer playerTurn = new ComputerPlayer(playerName,row,col,color);

				 board.calcTargets(row, col, cg.getRoll());
				 Set targets = board.getTargets();

				 if(playerTurn.getSeenCards().size() == board.getBackup().size()-3){
					 playerTurn.makeAccusation();
				 }

				 BoardCell picked = playerTurn.pickLocation(targets);
				 if (picked.isDoorway()){
					 Solution guess = playerTurn.makeSuggestion(board, picked);
					 playerTurn.seeCard(board.handleSuggestion(guess,playerTurn.getPlayerName(),picked));
				 }

			 }
		}
		else{
			JOptionPane.showMessageDialog(null, "You are Miss Scarlett, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return ifDone;
	}


	public JPanel addMyCards(){
		JPanel myCards = new JPanel();
		myCards.setLayout(new GridLayout(0,1));
		myCards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		for(Card c : board.getPlayers()[0].getMyCards()){
			String name = c.getName();
			if ( c.getType()==CardType.PERSON){
				JTextField personCard = new JTextField(20);
				personCard.setText(name);
				personCard.setEditable(false);
				personCard.setBorder(new TitledBorder (new EtchedBorder(), "Person"));
				myCards.add(personCard);
			}
			if ( c.getType()==CardType.WEAPON){
				JTextField weaponCard = new JTextField(20);
				weaponCard.setText(name);
				weaponCard.setEditable(false);
				weaponCard.setBorder(new TitledBorder (new EtchedBorder(), "Weapon"));
				myCards.add(weaponCard);
			}
			if ( c.getType()==CardType.ROOM){
				JTextField roomCard = new JTextField(20);
				roomCard.setText(name);
				roomCard.setEditable(false);
				roomCard.setBorder(new TitledBorder (new EtchedBorder(), "Room"));
				myCards.add(roomCard);
			}

		}
		return myCards;

	}
	public void addControlGUI(Board board){
		ControlGUI cg = new ControlGUI(this);
		add(cg, BorderLayout.SOUTH);
	}

	public void makeMenu(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		//System.out.println(menuBar.getMaximumSize());

	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.add(createFileDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}

		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	private JMenuItem createFileDetectiveNotesItem()
	{
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				d.setVisible(true);
			}

		}
		item.addActionListener(new MenuItemListener());

		return item;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(board.getNumColumns()*BoardCell.CELL_WIDTH > arg0.getX() && board.getNumRows()*BoardCell.CELL_HEIGHT > arg0.getY()){
			BoardCell whichCell = null;
			for (BoardCell cell : board.getTargets()){

				if (containsClick(arg0.getX()-6,arg0.getY()-BoardCell.CELL_HEIGHT-20, cell)){
					whichCell = cell;
					break;
				}
			}
			if (whichCell != null){
				board.getPlayers()[board.getCount()].setLocation(whichCell);
				repaint();
				ifDone = true;
			}
			else
				JOptionPane.showMessageDialog(null, "Select a valid target", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	public Board getBoard() {
		return board;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean containsClick(int mouseX, int mouseY, BoardCell cell) {
		Rectangle rect = new Rectangle(cell.CELL_WIDTH*cell.getColumn(), cell.CELL_HEIGHT*cell.getRow(), cell.CELL_WIDTH, cell.CELL_HEIGHT);
		board.getGraphics().setColor(Color.BLACK);
		board.getGraphics().drawRect(cell.CELL_WIDTH*cell.getColumn(), cell.CELL_HEIGHT*cell.getRow(), cell.CELL_WIDTH, cell.CELL_HEIGHT);
		Rectangle bounds = board.getBounds();
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}
}
