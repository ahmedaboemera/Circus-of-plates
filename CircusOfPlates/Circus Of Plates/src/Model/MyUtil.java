package Model;

import java.util.HashMap;

public abstract class MyUtil {
	public static HashMap<Integer, Shape> map = new HashMap<Integer, Shape>();
	public static void addtoMap(Integer k, Shape v)
	{
		map.put(k, v);
	}
	public static void removeFromMap(Integer k)
	{
		map.remove(k);
	}
	public static Shape getFromMap(Integer k)
	{
		return map.get(k);
	}
}
