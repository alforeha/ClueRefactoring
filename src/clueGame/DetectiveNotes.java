package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog{

	private Board board = new Board();

	public DetectiveNotes(){
		setLayout(new GridLayout(1,1));
		JPanel guessPanel = createGuessPanel();
		JPanel seenPanel = createSeenPanel();
		add(seenPanel);
		add(guessPanel);
	}

	private JPanel createSeenPanel() {
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(3,0));

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
			if (card.getType() == CardType.ROOM){
				roomCards.add(card);
			}
		}
	
		JPanel seenPersons = new JPanel();
		seenPersons.setLayout(new GridLayout(4,0));
		JPanel seenWeapons = new JPanel();
		seenWeapons.setLayout(new GridLayout(4,0));
		JPanel seenRooms = new JPanel();
		seenRooms.setLayout(new GridLayout(4,0));
		
		for ( Card card : personCards){
			JCheckBox persons = new JCheckBox();
			persons.setText(card.getName());
			seenPersons.add(persons);
		}
		seenPersons.setBorder(new TitledBorder (new EtchedBorder(), "Persons"));
		for ( Card card : weaponCards){
			JCheckBox weapons = new JCheckBox();
			weapons.setText(card.getName());
			seenWeapons.add(weapons);
		}
		seenWeapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for ( Card card : roomCards){
			JCheckBox rooms = new JCheckBox();
			rooms.setText(card.getName());
			seenRooms.add(rooms);
		}
		seenRooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		seenPanel.add(seenPersons);
		seenPanel.add(seenWeapons);
		seenPanel.add(seenRooms);
		return seenPanel;
	}

	private JPanel createGuessPanel() {
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(3,0));

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
			if (card.getType() == CardType.ROOM){
				roomCards.add(card);
			}
		}

		JComboBox<String> rooms = new JComboBox<>();
		JComboBox<String> weapons = new JComboBox<>();
		JComboBox<String> persons = new JComboBox<>();

		for ( Card card : personCards){
			persons.addItem(card.getName());
		}
		persons.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		for ( Card card : weaponCards){
			weapons.addItem(card.getName());
		}
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		for ( Card card : roomCards){
			rooms.addItem(card.getName());
		}
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		guessPanel.add(persons);
		guessPanel.add(weapons);
		guessPanel.add(rooms);

		return guessPanel;
	}
}
