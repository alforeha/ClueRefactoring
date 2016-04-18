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
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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



public class ClueGame extends JFrame{

	public Board board;
	public DetectiveNotes d;
	public ControlGUI cg;
	public MakeSuggestionDialog msd;
	private static final int DIE = 6;
	private boolean ifDone = true;


	public boolean isIfDone() {
		return ifDone;
	}

	public void setIfDone(boolean ifDone) {
		this.ifDone = ifDone;
	}

	public ClueGame(){
		board = new Board();
		board.initialize();
		setSize(1300, 900);
		add(board, BorderLayout.CENTER);
		cg = new ControlGUI(this);
		cg.setSize(300, 840);
		add(cg, BorderLayout.SOUTH);
		d = new DetectiveNotes();
		d.setSize(700, 400);
		//msd = board.getMsd();
		
		add(addMyCards(), BorderLayout.EAST);
		System.out.println(board.getSolution().getPerson() + " " + board.getSolution().getWeapon() + " " + board.getSolution().getRoom());

	/*	for (Player player : board.getPlayers()){
			System.out.println(player.getPlayerName());
			for (Card card : player.getMyCards()){
				System.out.println(card.getName());
			}
			System.out.println();
		}*/

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
		board.setTurnOver(false);
		board.setSuggestedCard(false);
		
		cg.guessField.setText("");
		cg.resultField.setText("");

		if(!board.isTurnOver()){
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
				board.drawTargets(board.getGraphics());				
				
				board.getMsd().setModal(true);
				
				board.setCg(cg);
				if(msd != null){
				cg.resultField.setText(msd.getResult());
				cg.guessField.setText(board.getMsd().getPersonString() + ", " + board.getMsd().getWeaponString() + ", " + board.getMsd().getRoomString());
				cg.repaint();}
								
			}

			if (player.getClass() == ComputerPlayer.class){
				board.calcTargets(row, col, cg.getRoll());				

				BoardCell picked = ((ComputerPlayer) player).pickLocation(board.getTargets());
				board.repaint();

				if (picked.isDoorway()){
					Solution guess = ((ComputerPlayer) player).makeSuggestion(board, picked);

					cg.guessField.setText(guess.person + ", " + guess.weapon + ", " + guess.room);
					Card result = board.handleSuggestion(guess,player.getPlayerName(),picked);					
					
					if (result != null){
						player.seeCard(result);
						cg.resultField.setText(result.getName());}
					else {cg.resultField.setText("No Response");}
				}
				

				if(player.getSeenCards().size() == board.getBackup().size()-ComputerPlayer.NUM_CARDS_IN_SOLUTION){
					Solution accusation = ((ComputerPlayer) player).makeAccusation(board);
					JOptionPane.showMessageDialog(null, "It was " + accusation.person
					+ " with the " + accusation.weapon + " in the " + accusation.room + "!", player.getPlayerName() + " wins...", JOptionPane.INFORMATION_MESSAGE);
				}


				board.setTurnOver(true);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "You are Miss Scarlett, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		}

		
		return board.isTurnOver();
	}


	public ControlGUI getCg() {
		return cg;
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

	

	public Board getBoard() {
		return board;
	}

	public void checkSuggestion() {
		if (board.isSuggestedCard()){
			msd.setVisible(true);
		}
		
	}



}
