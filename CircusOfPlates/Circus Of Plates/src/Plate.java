

import java.awt.Color;
import java.awt.Graphics;

import Model.Shape;

public class Plate extends Shape {	
private static final long serialVersionUID = 1L;
	
	public Plate(Color c) {
		color = c;
		type = 1;
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.white);
		int [] xPoints = {super.x1, super.x2, super.x2-5, super.x1+5};
		int [] yPoints = {super.y1, super.y1, super.y2, super.y2};
		int nPoints = 4;
		g.setColor(color);
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	public void moveShape(int diffX, int diffY) {
//		super.moveShape(x1+differenceX, y1+differenceY);
		super.moveShape(diffX, diffY);
	}
	
	public void dropShape() {
		super.dropShape();
	}
}
