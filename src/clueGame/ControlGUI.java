package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ControlGUI extends JPanel {
	private JTextField name;
	public JTextField rollField;
	public JTextField nameField;
	public JTextField guessField;
	public JTextField resultField;
	private ClueGame game;
	private String playerName;	
	private int roll;
	private JPanel diePanel;
	private JPanel whoseTurnPanel;
	private JPanel guessPanel;
	private JPanel resultPanel;
	
	public MakeSuggestionDialog msd;

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getRoll() {
		return roll;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public ControlGUI(ClueGame game)
	{
		this.game = game;

		setSize(600,300);
		setLayout(new GridLayout(5,0));
		JPanel panel = createButtonPanel(game.board);

		msd = new MakeSuggestionDialog(game);
		
		whoseTurnPanel = createWhosTurnPanel();

		diePanel = createDicePanel();

		guessPanel = createGuessPanel();

		resultPanel = createGuessResultPanel();
		add(panel);
		add(whoseTurnPanel);
		add(diePanel);
		add(guessPanel);
		add(resultPanel);
	}

	public void updateControl(){

	}

	private JPanel createWhosTurnPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(1,1));
		//JLabel nameLabel = new JLabel("");
		nameField = new JTextField(20);
		nameField.setEditable(false);
		//panel.add(nameLabel);
		panel.add(nameField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Who's Turn?"));
		return panel;
	}

	private JPanel createDicePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Roll");
		rollField = new JTextField(Integer.toString(roll));
		rollField.setEditable(false);
		panel.add(nameLabel);
		panel.add(rollField);

		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("Guess");
		guessField = new JTextField(20);
		guessField.setEditable(false);
		panel.add(nameLabel);
		panel.add(guessField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,3));
		JLabel nameLabel = new JLabel("Response");
		resultField = new JTextField(20);
		resultField.setEditable(false);
		//name.add("Hello");
		panel.add(nameLabel);
		panel.add(resultField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}

	private JPanel createButtonPanel(Board board) {
		// no layout specified, so this is flow
		JButton agree = new JButton("Next Player");
		agree.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {	
				if(!game.board.isTurnOver()){
					JOptionPane.showMessageDialog(null, "Please finish your turn", "You're not done!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					if(game.doTurn(board.getPlayers()[game.board.getCount()])){
						board.nextPlayer(game);						
					}
				}
				
			}				

		});


		JButton disagree = new JButton("Make An Accusation");
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(1, 2));
		panel.add(agree);
		//panel.setLayout(new GridLayout(1, 3));
		panel.add(disagree);
		return panel;
	}

	class MenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}

	}

	public class MakeSuggestionDialog extends JDialog{
		
		private String personString;
		private String weaponString;
		private String roomString;
		private String result;
		
		private Board board;;

		
		public MakeSuggestionDialog(ClueGame clueGame){
			setLayout( new GridLayout(1,0));
			board= clueGame.getBoard();
			JPanel guessPanel = createGuessPanel(board.getPlayers()[board.getCount()]);
			add(guessPanel);
			setSize(300,300);
		}
		
		public MakeSuggestionDialog(Board board){
			setLayout( new GridLayout(1,0));
			this.board = board;
			JPanel guessPanel = createGuessPanel(board.getPlayers()[board.getCount()]);
			add(guessPanel);
			setSize(300,300);
		}
			
		
		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getPersonString() {
			return personString;
		}

		public void setPersonString(String personString) {
			this.personString = personString;
		}

		public String getWeaponString() {
			return weaponString;
		}

		public void setWeaponString(String weaponString) {
			this.weaponString = weaponString;
		}

		public String getRoomString() {
			return roomString;
		}

		public void setRoomString(String roomString) {
			this.roomString = roomString;
		}


		

		
		private JPanel createGuessPanel(Player player){
			JPanel guessPanel = new JPanel();
			guessPanel.setLayout(new GridLayout(4,2));

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
			yourRoom = new JTextField(Board.rooms.get(cell.getInitial()));
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
					setPersonString(person);
					String weapon = (String) weapons.getSelectedItem();
					setWeaponString(weapon);
					String room = yourRoom.getText();
					setRoomString(room);
					Card result = board.handleSuggestion(new Solution(person,weapon,room),player.getPlayerName(),new BoardCell(player.getRow(),player.getCol()));
					if (result != null){
						player.seeCard(result);
						board.setSuggestedCard(true);
						setResult(result.getName());
						dispose();
					}
					else {setResult("No Response");
					dispose();
					}
					
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