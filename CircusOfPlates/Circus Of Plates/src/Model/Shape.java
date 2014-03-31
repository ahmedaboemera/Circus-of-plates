package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

public abstract class Shape extends JPanel implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int x1 = 0, y1 = 0, x2 = 30, y2 = 30, type = 0;
	protected int xBound, yBound; // for falling
	protected Color color;
	public Color getColor() {
		return color;
	}

	public void setxBound(int xBound) {
		this.xBound = xBound;
	}

	public void setyBound(int yBound) {
		this.yBound = yBound;
	}

	public int getxBound() {
		return xBound;
	}

	public int getyBound() {
		return yBound;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public void moveShape(int diffX, int diffY) {
		this.setBounds(diffX, this.yBound, 30, 30);
		this.xBound = diffX;
		this.yBound = diffY;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void dropShape() {
		yBound+=2;
		this.setBounds(xBound, yBound, 30, 30);
	}

	public int getType() {
		return type;
	}
}