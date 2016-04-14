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



public class ClueGame extends JFrame implements MouseListener{

	public Board board;
	public DetectiveNotes d;
	public ControlGUI cg;
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
		addMouseListener(this);
		setSize(1300, 900);
		add(board, BorderLayout.CENTER);
		cg = new ControlGUI(this);
		cg.setSize(300, 840);
		add(cg, BorderLayout.SOUTH);
		d = new DetectiveNotes();
		d.setSize(700, 400);
		add(addMyCards(), BorderLayout.EAST);
		System.out.println(board.getSolution().getPerson() + " " + board.getSolution().getWeapon() + "  " + board.getSolution().getRoom());

		for (Player player : board.getPlayers()){
			System.out.println(player.getPlayerName());
			for (Card card : player.getMyCards()){
				System.out.println(card.getName());
			}
			System.out.println();
		}

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
				

				if(player.getSeenCards().size() == board.getBackup().size()-3){
					((ComputerPlayer) player).makeAccusation();
				}


				board.setTurnOver(true);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "You are Miss Scarlett, press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		}

		if(board.isSuggestedCard()){
			MakeSuggestionDialog msd = new MakeSuggestionDialog(board.getPlayers()[board.getCount()]);
			msd.setVisible(true);
		}
		
		return board.isTurnOver();
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

	}

	public Board getBoard() {
		return board;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public class MakeSuggestionDialog extends JDialog{
		
		public MakeSuggestionDialog(Player player){
			setLayout( new GridLayout(1,0));
			JPanel guessPanel = createGuessPanel(player);
			add(guessPanel);
			setSize(300,300);
		}
		
		private JPanel createGuessPanel(Player player){
			JPanel guessPanel = new JPanel();
			guessPanel.setLayout(new GridLayout(4,2));
	

			ArrayList<Card> roomCards = new ArrayList<Card>();
			ArrayList<Card> weaponCards = new ArrayList<Card>();
			ArrayList<Card> personCards = new ArrayList<Card>();

			for ( Card card : board.getBackup()){
				if (card.getType() == CardType.PERSON){
					personCards.add(card);
				}
				if (card.getType() == CardType.WEAPON){
					weaponCards.add(card);
				}
			}

			JComboBox<String> weapons = new JComboBox<>();
			JComboBox<String> persons = new JComboBox<>();

			JTextField roomGuess;
			roomGuess = new JTextField("Your Room ");
			roomGuess.setEditable(false);
			JTextField personGuess;
			personGuess = new JTextField("Person ");
			personGuess.setEditable(false);
			JTextField weaponGuess;
			weaponGuess = new JTextField("Weapon ");
			weaponGuess.setEditable(false);
			
			
			JTextField yourRoom;
			BoardCell cell = new BoardCell(player.getRow(), player.getCol());
			yourRoom = new JTextField(board.rooms.get(cell.getInitial()));
			yourRoom.setEditable(false);
			
			for ( Card card : personCards){
				persons.addItem(card.getName());
			}

			for ( Card card : weaponCards){
				weapons.addItem(card.getName());
			}
			guessPanel.add(roomGuess);
			guessPanel.add(yourRoom);
			
			guessPanel.add(personGuess);
			guessPanel.add(persons);
			
			guessPanel.add(weaponGuess);
			guessPanel.add(weapons);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {	
					String person = (String) persons.getSelectedItem();
					String weapon = (String) weapons.getSelectedItem();
					String room = yourRoom.getText();
					board.handleSuggestion(new Solution(person,weapon,room),player.getPlayerName(),new BoardCell(player.getRow(),player.getCol())).getName();
					
				}				

			});


			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {	
					dispose();
				}				

			});
			

			guessPanel.add(submit);

			guessPanel.add(cancel);
			return guessPanel;
		}
		
	
		
	}
	
	

}
