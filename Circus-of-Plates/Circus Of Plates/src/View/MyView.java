package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.GameState;

public class MyView extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;
	public static MyView instance;
	private ViewState current_state;
	private JPanel score;
	private JButton playGame;
	private JButton loadGame;
	private JSlider difficulty;
	private JLabel difficultyLabel;
	private JLabel player1NameLabel;
	private JLabel player2NameLabel;
	private JLabel scoreLabel;
	private JLabel scorep1;
	private JLabel scorep2;
	private JTextField player1Name;
	private JTextField player2Name;
	private int difficultyValue = 15;
	private boolean playPressed = false;
	private JMenuBar menuBar;
	private JMenuItem save;
	private JMenu file;
	private JFileChooser fileChooser;

	private MyView() {
		current_state = GameStarted.getInstance();
		GameStarted gs = (GameStarted) current_state;
		score = new JPanel();
		setTitle("Circus of Plates");
		setSize(600, 700);
		setResizable(false);
		setLocationRelativeTo(null);

		// textfields
		player1Name = new JTextField("Player 1");
		player1Name.setBounds(50, 420, 150, 20);
		player2Name = new JTextField("Player 2");
		player2Name.setBounds(350, 420, 150, 20);
		gs.add(player1Name);
		gs.add(player2Name);

		// labels
		difficultyLabel = new JLabel("Select Your Difficulty Level");
		difficultyLabel.setBackground(Color.red);
		difficultyLabel.setBounds(225, 270, 300, 20);
		gs.add(difficultyLabel);
		player1NameLabel = new JLabel("Player One's Name");
		player1NameLabel.setBackground(Color.red);
		player1NameLabel.setBounds(50, 400, 150, 20);
		player2NameLabel = new JLabel("Player Two's Name");
		player2NameLabel.setBackground(Color.red);
		player2NameLabel.setBounds(350, 400, 150, 20);
		scoreLabel = new JLabel("Scoreboard");
		scoreLabel.setBounds(250, 0, 200, 20);
		scoreLabel.setBackground(Color.white);
		score.add(scoreLabel);
		scorep1 = new JLabel();
		scorep2 = new JLabel();
		scorep1.setBounds(150, 50, 20, 20);
		scorep2.setBounds(450, 50, 20, 20);
		gs.add(player1NameLabel);
		gs.add(player2NameLabel);

		// panel
		gs.setLayout(null);
		gs.setBounds(0, 0, 600, 600);
		gs.setBackground(Color.red);
		score.setLayout(null);
		score.setBounds(0, 600, 600, 100);
		score.setBackground(Color.gray);
		score.setBounds(0, 600, 600, 100);
		add(score);

		// scrollbar
		difficulty = new JSlider(0, 29);
		difficulty.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				difficultyValue = 30 - difficulty.getValue();

			}
		});
		difficulty.setBounds(200, 300, 200, 30);
		difficulty.setBackground(Color.red);
		gs.add(difficulty);

		// buttons
		playGame = new JButton("Play");
		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				playPressed = true;

			}
		});
		loadGame = new JButton("Load");
		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser jf = new JFileChooser();
				jf.showOpenDialog(null);
				try {
					GameState.setInstance((GameState) GameState.getInstance()
							.load(jf.getSelectedFile().getAbsolutePath()));
					playPressed = true;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		playGame.setBounds(100, 100, 100, 50);
		loadGame.setBounds(400, 100, 100, 50);
		gs.add(loadGame);
		gs.add(playGame);
		add(gs);
		// add(score);
	}

	public int getDifficultyValue() {
		return difficultyValue;
	}

	public static MyView getInstance() {
		if (instance == null)
			return new MyView();
		return instance;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (playPressed) {
			player1NameLabel.setText(player1Name.getText());
			player2NameLabel.setText(player2Name.getText());
			MyView.this.remove((ViewState) current_state);
			GameRunning  gr = GameRunning.getInstance();
			// JMenubar
			menuBar = new JMenuBar();
			file = new JMenu("File");
			save = new JMenuItem("Save Game");
			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					fileChooser = new JFileChooser();
					GameRunning.isPaused = true;
					fileChooser.showSaveDialog(null);
					try {
						GameRunning
								.getInstance()
								.getState()
								.save(fileChooser.getSelectedFile()
										.getAbsolutePath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GameRunning.isPaused = false;
				}
			});
			file.add(save);
			menuBar.add(file);
			if(gr.isEndGame())
			{
				playPressed = false;
				if(GameState.getInstance().getPlayer1().isWinner())
				JOptionPane.showMessageDialog(null, "Congrats " + GameState.getInstance().getPlayer1().getName() + " You win");
				else
					JOptionPane.showMessageDialog(null, "Congrats " + GameState.getInstance().getPlayer2().getName() + " You win");
				return;
			}
			MyView.this.add(menuBar);
			MyView.this.setJMenuBar(menuBar);
			gr.getState().getPlayer1().setName(player1Name.getText());
			gr.getState().getPlayer2().setName(player2Name.getText());
			if(!gr.isEndGame())
				current_state = gr;
			current_state.setLayout(null);
			current_state.setBounds(0, 0, 600, 600);
			current_state.setBackground(Color.white);
			current_state.setBounds(0, 0, 600, 600);
			MyView.this.add(current_state);
			scorep1.setText(gr.getState().getPlayer1().getScore() + "");
			scorep2.setText(gr.getState().getPlayer2().getScore() + "");
			player1NameLabel.setBounds(100, 30, 150, 20);
			player2NameLabel.setBounds(400, 30, 150, 20);
			score.add(scorep1);
			score.add(scorep2);
			score.add(player1NameLabel);
			score.add(player2NameLabel);
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
