package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField name;

	public ControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(5,0));
		JPanel panel = createButtonPanel();
		add(panel);
		panel = createWhosTurnPanel();
		add(panel);
		panel = createDicePanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);
	}

	private JPanel createWhosTurnPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(1,1));
		//JLabel nameLabel = new JLabel("");
		name = new JTextField(20);
		name.setEditable(false);
		//panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Who's Turn?"));
		return panel;
	}

	private JPanel createDicePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Roll");
		name = new JTextField(20);
		name.setEditable(false);
		panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}

	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("Guess");
		name = new JTextField(20);
		name.setEditable(false);
		panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		//panel.setLayout(new GridLayout(2,3));
		JLabel nameLabel = new JLabel("Response");
		name = new JTextField(20);
		name.setEditable(false);
		//name.add("Hello");
		panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}

	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JButton agree = new JButton("Next Player");
		JButton disagree = new JButton("Make An Accusation");
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(1, 2));
		panel.add(agree);
		//panel.setLayout(new GridLayout(1, 3));
		panel.add(disagree);
		return panel;
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control Panel");
		frame.setSize(400, 300);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}

}