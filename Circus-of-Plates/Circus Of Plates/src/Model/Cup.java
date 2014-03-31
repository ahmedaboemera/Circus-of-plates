package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Cup extends Shape implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public Cup(Color c) {
		color = c;
		type = 2;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.white);
		int [] xPoints = {super.x1+5, super.x2-5, super.x2-10,super.x2,super.x1,super.x1+10};
		int [] yPoints = {super.y1, super.y1, super.y2-5, super.y2,super.y2,super.y2-5};
		int nPoints = 6;
		g.setColor(color);
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	public void moveShape(int diffX, int diffY) {
//		super.moveShape(x1-5+differenceX, y1-5+differenceY);
		super.moveShape(diffX, diffY);
	}
	
	public void dropShape() {
		super.dropShape();
	}
}
