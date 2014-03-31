package Model;

import java.io.Serializable;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player extends JPanel implements Serializable, Observer {
	private static final long serialVersionUID = 1L;
	private Stack<Shape> rightHand;
	private Stack<Shape> leftHand;
	private int remaining_lives;
	private int right_height;
	private int left_height;
	private int pos;
	private JLabel image;
	private String name;
	private int score;
	private boolean isWinner = false;
	
	public void wins() {
		isWinner = true;
	}
	
	public boolean isWinner() {
		return isWinner;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player(int ps) {
		// TODO Auto-generated constructor stub
		pos = ps;
		right_height = 0;
		left_height = 0;
		rightHand = new Stack<Shape>();
		leftHand = new Stack<Shape>();
		remaining_lives = 5;
	}

	public void rightCatch(Shape s) {
		rightHand.add(s);
	}

	public void leftCatch(Shape s) {
		leftHand.add(s);
	}

	public void move(int x) {
		pos += x;
		
	}

	public int getRemaining_lives() {
		return remaining_lives;
	}

	public void setRemaining_lives(int remaining_lives) {
		this.remaining_lives = remaining_lives;
	}

	public int getRight_height() {
		return right_height;
	}

	public JLabel getImage() {
		return image;
	}

	public void setImage(JLabel image) {
		this.image = image;
	}

	public void setRight_height(int right_height) {
		this.right_height = right_height;
	}

	public int getLeft_height() {
		return left_height;
	}

	public void setLeft_height(int left_height) {
		this.left_height = left_height;
	}

	public Stack<Shape> getRightHand() {
		return rightHand;
	}

	public void setRightHand(Stack<Shape> rightHand) {
		this.rightHand = rightHand;
	}

	public Stack<Shape> getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(Stack<Shape> leftHand) {
		this.leftHand = leftHand;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public void incrementScore() {
		this.score++;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.wins();
	}
	
}
