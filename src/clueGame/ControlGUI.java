package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
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
	
	public MakeAccusationDialog mad;
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

		mad = new MakeAccusationDialog(game.board);
		msd = new MakeSuggestionDialog(game.board, this);
		
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
						board.nextPlayer();						
					}
				}
				
			}				

		});


		JButton disagree = new JButton("Make An Accusation");
		disagree.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {	
				if(game.board.getCount() != 0){
					JOptionPane.showMessageDialog(null, "It's not your turn!", "You must wait your turn!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					mad.setVisible(true);
					//mad.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					//mad.setModal(true);
					//while(msd.getPersonString().equals("")){
					//}
					
				}
				
			}				

		});
		
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

	

}