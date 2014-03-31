package Model;

import java.io.Serializable;
import java.util.LinkedList;

public class ShapePool implements Serializable {
	/*
	 * for using linked list as a queue:
	 * list.add(0, element); ---> enqueue
	 * list.pollLast(); ---> dequeue
	 * */
	private LinkedList<Shape> usedShapes;
	private LinkedList<Shape> unusedShapes;
	private int maxNumOfShapes;
	public static ShapePool instance;
	private ShapeFactory factory;
	
	private ShapePool() {
		maxNumOfShapes = 100;
		usedShapes = new LinkedList<Shape>();
		unusedShapes = new LinkedList<Shape>();
		factory = ShapeFactory.getInstance();
		fillPool();
	}
	public static ShapePool getInstance() {
		if (instance == null) return new ShapePool();
		else return instance;
	}
	private void fillPool() {
		for (int i = 0; i < maxNumOfShapes; i++) {
			try {
				unusedShapes.add(0, factory.getShape());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Shape requestShape() {
		if (unusedShapes.isEmpty()) refillPoll();
		return unusedShapes.pollLast();
	}
	private void refillPoll() { // refills pool by one object at a time to avoid delay
		try {
			unusedShapes.add(0, factory.getShape());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void releaseShape(Shape s) {
		if (s != null) unusedShapes.add(0, s);
	}
}
