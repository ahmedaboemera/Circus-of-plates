package View;


public class GameStarted extends ViewState {

	private static final long serialVersionUID = 1L;
	public static GameStarted instance;
	private GameStarted() {}
	public static GameStarted getInstance() {
		if (instance == null) return new GameStarted();
		return instance;
	}
}
