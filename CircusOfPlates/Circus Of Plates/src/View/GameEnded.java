package View;

import javax.swing.JLabel;


public class GameEnded extends ViewState {
	private static final long serialVersionUID = 1L;
	public static GameEnded instance;
	private static JLabel gameFinished;
	private GameEnded() {
		gameFinished = new JLabel("Game Ended");
		this.add(gameFinished);
		this.setLayout(null);
	}
	public static GameEnded getInstance() {
		if (instance == null) return new GameEnded();
		return instance;
	}
}
