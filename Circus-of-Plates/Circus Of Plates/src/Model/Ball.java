package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Ball extends Shape implements Serializable{
	private static final long serialVersionUID = 1L;
	public Ball(Color c) {
		color = c;
		type = 3;
	}
	
	public void paint(Graphics g) {
		this.setBackground(Color.white);
		super.paint(g);
		g.setColor(color);
		g.fillOval(x1, y1, x2, y2);

	}
	
	public void moveShape(int diffX, int diffY) {
//		super.moveShape(x1+differenceX, y1+differenceY);
		super.moveShape(diffX, diffY);
	}
	
	public void dropShape() {
		super.dropShape();
	}
}
