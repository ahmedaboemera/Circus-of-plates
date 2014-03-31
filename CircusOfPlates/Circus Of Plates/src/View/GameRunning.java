package View;

import java.awt.Color;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Controller.GameController;
import Model.GameState;
import Model.MyUtil;
import Model.Player;
import Model.Shape;


public class GameRunning extends ViewState implements MouseMotionListener, KeyEventDispatcher, Runnable {
	private static final long serialVersionUID = 1L;
	public static GameRunning instance;
	private JLabel playerLabel1;
	private JLabel playerLabel2;
	private GameState state;
	private Data data;
	private int shapePositionCounter1, shapePositionCounter2;
	private ArrayList<Shape> shapesOnRail1;
	private ArrayList<Shape> shapesOnRail2;
	private JPanel the_x_block1;
	private JPanel the_x_block2;
	private ArrayList<Shape> shapesInAir;
	private ArrayList<Shape> player1RightStack;
	private ArrayList<Shape> player1LeftStack;
	private ArrayList<Shape> player2RightStack;
	private ArrayList<Shape> player2LeftStack;
	private static int p1X = 150,p2X;
	private static int inc = -95;
	public static boolean switching = false;
	private static File player1IMG = new File("clown.gif");
	private static File player2IMG = new File("clown2.gif");
	private static Image player1;
	private static Image player2;
	private static boolean endGame = false;
	@SuppressWarnings("rawtypes")
	private Iterator itr;
	private ArrayList<Model.Observer> observers = new ArrayList<Model.Observer>();
	public static boolean isPaused = false;
	private JFileChooser fileChooser;
	
	public void attach(Model.Observer o) {
		observers.add(o);
	}
	
	public void notify(Player p) {
		endGame = true;
		p.update();
	}
	
	// method: When any of players win, notify him
	
	public boolean isEndGame() {
		return endGame;
	}

	private GameRunning() {
		try {
			state =GameState.getInstance();
			if(player1 == null)
				player1 = ImageIO.read(player1IMG);
			if(player2 == null)
				player2 = ImageIO.read(player2IMG);
			shapesOnRail1 = new ArrayList<Shape>();
			shapesOnRail2 = new ArrayList<Shape>();
			shapesInAir = new ArrayList<Shape>();
			player1RightStack = new ArrayList<Shape>();
			player1LeftStack = new ArrayList<Shape>();
			player2RightStack = new ArrayList<Shape>();
			player2LeftStack = new ArrayList<Shape>();
			state = GameState.getInstance();
//			p1X = state.getPlayer1().getPos();
			p2X = state.getPlayer2().getPos();
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	        manager.addKeyEventDispatcher(this);
			this.addMouseMotionListener(this);
			the_x_block1 = new JPanel();
			the_x_block1.setBounds(0, 0, 32, 50);
			the_x_block1.setBackground(Color.black);
			the_x_block2 = new JPanel();
			the_x_block2.setBounds(state.getRail2().getStartingX()-30, 0, 30, 50);
			the_x_block2.setBackground(Color.black);
			shapePositionCounter1 = state.getRail1().getStartingX();
			shapePositionCounter2 = state.getRail2().getStartingX();
			playerLabel1 = new JLabel(new ImageIcon(player1));
			playerLabel2 = new JLabel(new ImageIcon(player2));
			playerLabel1.setBounds(state.getPlayer1().getPos() - 61, 385, 123, 180);
			playerLabel2.setBounds(state.getPlayer2().getPos() - 61, 385, 123, 180);
			state.getPlayer1().setImage(playerLabel1);
			state.getPlayer2().setImage(playerLabel2);
			state.getRail1().setBounds(0, 50, state.getRail1().getEndingX()-state.getRail1().getStartingX(), state.getRail1().getEndingY()-state.getRail1().getStartingY());
			state.getRail2().setBounds(state.getRail2().getEndingX(), 50, state.getRail2().getStartingX()-state.getRail2().getEndingX(), state.getRail2().getEndingY()-state.getRail2().getStartingY());
			state.getRail1().repaint();
			state.getRail2().repaint();
			this.setLayout(null);
			this.add(state.getRail1());
			this.add(state.getRail2());
			this.add(playerLabel1);
			this.add(playerLabel2);
			this.add(the_x_block1);
			this.add(the_x_block2);
			addShapesToRail1();
			addShapesToRail2();
			data = new Data(p1X, p2X+(inc)/100);
			if(inc == 105)
				inc = 95;
			if(inc == -105)
				inc = -95;
			if (!isPaused) state = GameController.doUpdates(data);
			GameState.setInstance(state);
			printHashMap();
			printPlayersStacks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printPlayersStacks() {
		// TODO Auto-generated method stub
		itr = state.getPlayer1().getRightHand().iterator();
		while (itr.hasNext()) {
			Shape temp = (Shape)itr.next();
			player1RightStack.add(0, temp);
		}
		itr = state.getPlayer1().getLeftHand().iterator();
		while (itr.hasNext()) {
			Shape temp = (Shape)itr.next();
			player1LeftStack.add(0, temp);
		}
		itr = state.getPlayer2().getRightHand().iterator();
		while (itr.hasNext()) {
			Shape temp = (Shape)itr.next();
			player2RightStack.add(0, temp);
		}
		itr = state.getPlayer2().getLeftHand().iterator();
		while (itr.hasNext()) {
			Shape temp = (Shape)itr.next();
			player2LeftStack.add(0, temp);
		}
		itr = state.getPlayer1().getRightHand().iterator();
		while (itr.hasNext()) {
			Shape temp = (Shape)itr.next();
			temp.setBounds(state.getPlayer1().getPos()+(109-62)-15, temp.getyBound(), 30, 30);
			this.add(temp);
			temp.repaint();
		}
		itr = state.getPlayer1().getLeftHand().iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			s.setBounds(state.getPlayer1().getPos()-(92+17)+43, s.getyBound(), 30, 30);
			this.add(s);
			s.repaint();
		}
		itr = state.getPlayer2().getRightHand().iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			s.setBounds(state.getPlayer2().getPos()+(109-62)-15, s.getyBound(), 30, 30);
			this.add(s);
			s.repaint();
		}
		itr = state.getPlayer2().getLeftHand().iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			s.setBounds(state.getPlayer2().getPos()-(92+17)+43, s.getyBound(), 30, 30);
			this.add(s);
			s.repaint();
		}
		if (!state.getPlayer1().getRightHand().isEmpty())
		{
			state.getPlayer1().getRightHand().peek().setBounds(state.getPlayer1().getPos()+(109-62)-15, 600-(state.getPlayer1().getRight_height()+30+180)-5, 30, 30);
			state.getPlayer1().getRightHand().peek().setyBound(600-(state.getPlayer1().getRight_height()+30+180)-5);
		}
		if (!state.getPlayer1().getLeftHand().isEmpty())
		{
			state.getPlayer1().getLeftHand().peek().setBounds(state.getPlayer1().getPos()-(92+17)+43, 600-(state.getPlayer1().getLeft_height()+30+180)-5, 30, 30);
			state.getPlayer1().getLeftHand().peek().setyBound(600-(state.getPlayer1().getLeft_height()+30+180)-5);
		}
		if (!state.getPlayer2().getRightHand().isEmpty())
		{
			state.getPlayer2().getRightHand().peek().setBounds(state.getPlayer2().getPos()+(109-62)-15, 600-(state.getPlayer2().getRight_height()+30+180)-5, 30, 30);
			state.getPlayer2().getRightHand().peek().setyBound(600-(state.getPlayer2().getRight_height()+30+180)-5);
		}
		if (!state.getPlayer2().getLeftHand().isEmpty())
		{
			state.getPlayer2().getLeftHand().peek().setBounds(state.getPlayer2().getPos()-(92+17)+43, 600-(state.getPlayer2().getLeft_height()+30+180)-5, 30, 30);
			state.getPlayer2().getLeftHand().peek().setyBound(600-(state.getPlayer2().getLeft_height()+30+180)-5);
		}


		
	}

	private void printHashMap() {
		// TODO Auto-generated method stub
		itr =  state.getFallingShapes().values().iterator();
		while (itr.hasNext()) {
			Shape s = (Shape) itr.next();
			if (s != null)
			{
				shapesInAir.add(s);
				
			}
		}
		itr = shapesInAir.iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			this.add(s);
			s.repaint();
		}
		
	}

	private void addShapesToRail2() {
		for (int i = state.getRail2().getShapes_currently_on_rail().size()-1; i >= 0; i--) {
			if (-state.getRail2().getEndingX() + shapePositionCounter2 <15)
			{
				state.getRail2().removeExcessShapes(i, state.getPool());
				break;
			}
			else {
				Shape temp = state.getRail2().getShapes_currently_on_rail().get(i);
				shapesOnRail2.add(temp);
				shapePositionCounter2 -= 30;
			}
		}
		itr = shapesOnRail2.iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			this.add(s);
			s.repaint();
		}
		
	}

	private void addShapesToRail1() {
		for (int i = state.getRail1().getShapes_currently_on_rail().size()-1; i >= 0; i--) {
			if (state.getRail1().getEndingX() - shapePositionCounter1 <15)
			{
				state.getRail1().removeExcessShapes(i, state.getPool());
				break;
			}
			else {
				Shape temp = state.getRail1().getShapes_currently_on_rail().get(i);
				shapesOnRail1.add(temp);
				shapePositionCounter1 += 30;
			}
		}
		itr = shapesOnRail1.iterator();
		while (itr.hasNext()) {
			Shape s = (Shape)itr.next();
			this.add(s);
			s.repaint();
		}
		
	}

	public static GameRunning getInstance() {
		if (instance == null) return new GameRunning();
		return instance;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	// player2
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		p1X = e.getX();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getID() == KeyEvent.KEY_PRESSED) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
            	if(!switching)
            		inc = 95;
            	switching = true;
            	inc ++; 
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {
            	if(switching)
            		inc = -95;
            	switching = false;
            	inc --;
            }
        }
		if(e.getID() == KeyEvent.KEY_RELEASED)
			inc = 95;
		if (e.getID() == KeyEvent.KEY_TYPED) {
			if (Character.toLowerCase(e.getKeyChar()) == 'p')
        	{
				isPaused = true;
//        		GameRunning.instance = GameRunning.getInstance();
        	}
			else if (Character.toLowerCase(e.getKeyChar()) == 'c')
        	{
				isPaused = false;
//        		GameRunning.instance = GameRunning.getInstance();
        	}
			else if (Character.toLowerCase(e.getKeyChar()) == 's') {
				fileChooser = new JFileChooser();
				isPaused = true;
				try {
					fileChooser.showSaveDialog(null);
					state.save(fileChooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isPaused = false;
			}
        }
        return false;
        
	}
	
	public GameState getState() {
		return this.state;
	}
	
	public void setState(GameState gs) {
		this.state = gs;
	}
}
