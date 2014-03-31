package Model;

public class Coordinate {
private int xCoordinate;
private int yCoordinate;
// center of shape coordinates
public Coordinate(int x, int y) {
	// TODO Auto-generated constructor stub
	xCoordinate = x;
	yCoordinate = y;
}
public void setxCoordinate(int xCoordinate) {
	this.xCoordinate = xCoordinate;
}
public void setyCoordinate(int yCoordinate) {
	this.yCoordinate = yCoordinate;
}
public int getxCoordinate() {
	return xCoordinate;
}
public int getyCoordinate() {
	return yCoordinate;
}
}
