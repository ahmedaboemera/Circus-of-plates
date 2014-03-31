package Model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader {
protected static Class Ball;
protected static Class Cup;
protected static Class Plate;
protected static Constructor BallConstructor;
protected static Constructor CupConstructor;
protected static Constructor PlateConstructor;
public static void loadClasses()
{
	try{
		Ball = Class.forName("Ball");
		Plate = Class.forName("Plate");
		Cup  = Class.forName("Cup");
		BallConstructor = Ball.getConstructors()[0];
		PlateConstructor = Plate.getConstructors()[0];
		CupConstructor = Cup.getConstructors()[0];
	}
	catch(ClassNotFoundException e)
	{
		System.out.println("Some Classes are not Found");
	} 
}
}
