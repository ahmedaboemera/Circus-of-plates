package Model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Rail extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean increasing_state;
	private int startingX, startingY, endingX, endingY;
	private LinkedList<Shape> shapes_currently_on_rail;
	
	public Rail(int start_X, int end_X) {
		increasing_state = false;
		startingX = start_X;
		endingX = end_X;
		shapes_currently_on_rail = new LinkedList<Shape>();
		startingY = 50; // absolute height of each rail
		endingY = 60;
	}
	
	public int getEndingY() {
		return endingY;
	}

	public void requestShape(ShapePool pool) { // shape is requested from given ShapePool, adds to queue
		if(this.startingX == 600)
			{
			Shape s = pool.requestShape();
			s.moveShape(570, 20);
			shapes_currently_on_rail.add(s);
			}
		else
			shapes_currently_on_rail.add(0, pool.requestShape()); //
	}
	
	public Shape releaseShape() { // shape must be released from rail and returned
		return shapes_currently_on_rail.pollFirst();
	}
	
	public int getStartingX() {
		return startingX;
	}

	public void setStartingX(int startingX) {
		this.startingX = startingX;
	}

	public int getStartingY() {
		return startingY;
	}

	public void setStartingY(int startingY) {
		this.startingY = startingY;
	}

	public int getEndingX() {
		return endingX;
	}

	public void setEndingX(int endingX) {
		this.endingX = endingX;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if(startingX<endingX)
		{
			g.fillRect(0, 0, Math.abs(endingX - startingX), endingY-startingY);
		}
		else
		{
			g.fillRect(0, 0,startingX-endingX, endingY-startingY);
		}
	}

	public LinkedList<Shape> getShapes_currently_on_rail() {
		return shapes_currently_on_rail;
	}

	public void setShapes_currently_on_rail(
			LinkedList<Shape> shapes_currently_on_rail) {
		this.shapes_currently_on_rail = shapes_currently_on_rail;
	}

	public void changeEndingPoint()
	{
		if(increasing_state)
		{
			if(startingX < endingX)
			{
				//rail1
				endingX+=2;
				if(endingX-startingX == 220)
					increasing_state = false;
			}
			else
			{
				//rail2
				endingX-=2;
				if(startingX - endingX == 220)
					increasing_state = false;
			}
		}
		else
		{
			if(startingX < endingX)
			{
				//rail1
				endingX-=2;
				if(endingX-startingX == 50)
					increasing_state = true;
			}
			else
			{
				//rail2
				endingX+=2;
				if(startingX - endingX == 50)
					increasing_state = true;
			}
		}
	}

	public boolean isIncreasing_state() {
		return increasing_state;
	}

	public void removeExcessShapes(int i, ShapePool pool) {
		for (; i >= 0; i--) {
			if (i<shapes_currently_on_rail.size()) {
				pool.releaseShape(shapes_currently_on_rail.get(i));
				shapes_currently_on_rail.remove(i);
			}
		}
		
	}
}
