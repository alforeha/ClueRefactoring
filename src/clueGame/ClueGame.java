package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame{
	
	
	public Board board;
	public DetectiveNotes d;
	public ControlGUI cg;
	
	
	public ClueGame(){
		board = new Board();
		board.initialize();
		setSize(1060, 840);
		add(board, BorderLayout.NORTH);
		cg = new ControlGUI();
		add(cg, BorderLayout.SOUTH);
		add(cg, BorderLayout.NORTH);
		d = new DetectiveNotes();
		d.setSize(400, 400);
	}
	
	public static void main(String [] args){
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.makeMenu();
		
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
