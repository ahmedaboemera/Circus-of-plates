package Model;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class ShapeFactory implements Serializable{

	public static ShapeFactory instance;
	private ArrayList<Color> listOfColors = new ArrayList<Color>();
	
	private ShapeFactory() {
		listOfColors.add(Color.black);
		listOfColors.add(Color.blue);
		listOfColors.add(Color.cyan);
		listOfColors.add(Color.gray);
		listOfColors.add(Color.green);
		listOfColors.add(Color.magenta);
		listOfColors.add(Color.orange);
		listOfColors.add(Color.pink);
		listOfColors.add(Color.red);
	}
	
	public static ShapeFactory getInstance() {
		if (instance == null) return new ShapeFactory();
		return instance;
	}
	private Color randomizeColor() {
		Random r = new Random();
		return listOfColors.get(r.nextInt(9));
	}
	
	private int randomizeType() {
		Random r = new Random();
		return r.nextInt(3) + 1;
	}
	
	public Shape getShape() throws IllegalAccessException {
		Color c = randomizeColor();
		int randomValue = randomizeType();
		try{
		switch (randomValue) {
		case 1:
			{
				Shape toreturn = (Shape) MyClassLoader.PlateConstructor.newInstance(c);
				return toreturn;
			}
			case 2:
			{
				Shape toreturn = (Shape) MyClassLoader.CupConstructor.newInstance(c);
				return toreturn;
			}
			case 3:
			{
				Shape toreturn = (Shape) MyClassLoader.BallConstructor.newInstance(c);
				return toreturn;
			}
		}
		}
		catch(InstantiationException e)
		{
			return null;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
