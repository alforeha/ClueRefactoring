package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


	public ClueGame(){
		board = new Board();
		board.initialize();
		setSize(1300, 860);
		add(board, BorderLayout.CENTER);
		cg = new ControlGUI();
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
	public void addControlGUI(){
		ControlGUI cg = new ControlGUI();
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

}
