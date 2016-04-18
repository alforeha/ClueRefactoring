package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MakeAccusationDialog extends JDialog{
	
	private String personString;
	private String weaponString;
	private String roomString;
	private String result;
	private boolean accusation = false;
	
	private Board board;
	private ClueGame clueGame;
	private ControlGUI cg;

	
	public MakeAccusationDialog(Board board){
		setLayout( new GridLayout(1,0));
		this.board=board;
		personString = "";
		weaponString = "";
		roomString = "";
		JPanel guessPanel = createGuessPanel(board.getPlayers()[board.getCount()]);
		add(guessPanel);
		setSize(300,300);
		accusation = true;
		
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
		ArrayList<Card> roomCards = new ArrayList<Card>();

		for ( Card card : board.getBackup()){
			if (card.getType() == CardType.PERSON){
				personCards.add(card);
			}
			if (card.getType() == CardType.WEAPON){
				weaponCards.add(card);
			}
			if (card.getType() == CardType.ROOM){
				roomCards.add(card);
			}
		}

		JComboBox<String> weapons = new JComboBox<>();
		JComboBox<String> persons = new JComboBox<>();
		JComboBox<String> rooms = new JComboBox<>();

		JTextField roomGuess;
		roomGuess = new JTextField("Your Room ");
		roomGuess.setEditable(false);
		JTextField personGuess;
		personGuess = new JTextField("Person ");
		personGuess.setEditable(false);
		JTextField weaponGuess;
		weaponGuess = new JTextField("Weapon ");
		weaponGuess.setEditable(false);
		
		
		JTextField room = new JTextField(20);
		
		BoardCell cell = board.getCellAt(player.getRow(), player.getCol());
		
		
		for ( Card card : personCards){
			persons.addItem(card.getName());
		}

		for ( Card card : weaponCards){
			weapons.addItem(card.getName());
		}
		
		guessPanel.add(roomGuess);
		
	
		for ( Card card : roomCards){
			rooms.addItem(card.getName());
			guessPanel.add(rooms);}
		
		
			
		
		
		
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
				
				if (!accusation){
					setRoomString(board.rooms.get(cell.getInitial()));
					//rooms.setSelectedItem(board.rooms.get(cell.getInitial()));
				Card result = board.handleSuggestion(new Solution(person,weapon,getRoomString()),player.getPlayerName(),new BoardCell(player.getRow(),player.getCol()));
				if (result != null){
					player.seeCard(result);
					System.out.println((String) person + ", " + (String) weapon + ", " + getRoomString());
					System.out.println();
					System.out.println(result.getName());
					
					setPersonString(person);
					setWeaponString(weapon);
					
					setResult(result.getName());
					
					
					if (cg!=null){
					cg.guessField.setText((String) person + ", " + (String) weapon + ", " + getRoomString());
					cg.resultField.setText(result.getName());
					}
					board.setSuggestedCard(true);
					setResult(result.getName());
					
					dispose();
				}
				else {setResult("No Response");
				dispose();
				}
				}
				else{
					String room = (String) rooms.getSelectedItem();
					setRoomString(room);
					//System.out.println(person + ", " + weapon + ", " + getRoomString());
					if(board.checkAccusation(getPersonString(), getWeaponString(), getRoomString())){
						JOptionPane.showMessageDialog(null, "It was " + getPersonString()
						+ " with the " + getWeaponString() + " in the " + getRoomString() + "!", "You Win!", JOptionPane.INFORMATION_MESSAGE);
					}
					if(!board.checkAccusation(getPersonString(), getWeaponString(), getRoomString())){	
						JOptionPane.showMessageDialog(null, "Nope. Your turn is over. Please click \"Next Player\"", "Not right!", JOptionPane.INFORMATION_MESSAGE);
						board.nextPlayer();
						board.setTurnOver(true);
						dispose();
					}
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