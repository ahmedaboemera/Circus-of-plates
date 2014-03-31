package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class GameState implements Serializable {
	
	public ShapePool getPool() {
		return pool;
	}
	private static final long serialVersionUID = 1L;
	public static GameState instance;
	private ShapePool pool;
	private Player player1;
	private Player player2;
	private Rail rail1;
	private Rail rail2;
	private HashMap<Integer,Shape> fallingShapes;
	private Player winner; // null
	
	private GameState() {
		MyClassLoader.loadClasses();
		pool = ShapePool.getInstance();
		player1 = new Player(150); // to changed when a player image is used
		player2 = new Player(450); // to changed when a player image is used
		rail1 = new Rail(0,60);
		rail2 = new Rail(600,540);
		fallingShapes = new HashMap<Integer,Shape>();
	}
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Rail getRail1() {
		return rail1;
	}

	public void setRail1(Rail rail1) {
		this.rail1 = rail1;
	}

	public Rail getRail2() {
		return rail2;
	}

	public void setRail2(Rail rail2) {
		this.rail2 = rail2;
	}

	public HashMap<Integer, Shape> getFallingShapes() {
		return fallingShapes;
	}

	public void setFallingShapes(HashMap<Integer, Shape> fallingShapes) {
		this.fallingShapes = fallingShapes;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public void requestShape(Rail r) { // prompts rail to request shape from the shape pool
		r.requestShape(pool);
	}
	
	public void transferShapeFromRailToMap(Rail r, Shape shape) {
		fallingShapes.put(shape.getxBound(), r.releaseShape());
	}
	
	public static GameState getInstance() {
		if (instance == null){
			return new GameState();
		}
		return instance;
	}
	
	public void save(String file_path) throws IOException {
		FileOutputStream fos;
		ObjectOutputStream oos;
		fos = new FileOutputStream(new File(file_path));
		oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
	}
	
	public Object load(String file_path) throws IOException, ClassNotFoundException {
		ObjectInputStream ois;
		FileInputStream fis;
		fis = new FileInputStream(file_path);
		ois = new ObjectInputStream(fis);
		Object a  = ois.readObject();
		fis.close();
		ois.close();
		return a;
	}
	public static void setInstance(GameState instance) {
		GameState.instance = instance;
	}
}
