package Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import Model.GameState;
import Model.Player;
import Model.Shape;
import View.Data;
import View.GameRunning;

public class GameController {
	public static GameState gameState = GameState.getInstance();

	public static GameState doUpdates(Data data_in) {
		gameState = moveShapesAndRails(data_in);
		GameState.setInstance(gameState);
		gameState = moveFallingShapes();
		GameState.setInstance(gameState);
		gameState = movePlayers(data_in);
		GameState.setInstance(gameState);
		return gameState;
	}

	private static GameState movePlayers(Data data_in) {
		gameState.getPlayer1().setPos(data_in.getIncrement_X1());
		gameState.getPlayer2().setPos(data_in.getIncrement_X2());
		GameState.setInstance(gameState);
		gameState = checkPlayers();
		return gameState = calcScore();
	}

	private static GameState calcScore() {
		Player p1 = gameState.getPlayer1();
		Player p2 = gameState.getPlayer2();
		int i = p1.getLeftHand().size()-1;
		if (i-1>-1 && i-2>-1) {
			Shape s1 = p1.getLeftHand().get(i);
			Shape s2 = p1.getLeftHand().get(i-1);
			Shape s3 = p1.getLeftHand().get(i-2);
			if (s1.getType() == s2.getType() && s2.getType() == s3.getType() &&
				s1.getColor() == s2.getColor() && s2.getColor() == s3.getColor()) {
				p1.setScore(p1.getScore()+1);
				p1.getLeftHand().pop();
				p1.getLeftHand().pop();
				p1.getLeftHand().pop();
				p1.setLeft_height(p1.getLeft_height()-90);
			}
		}
		i = p1.getRightHand().size()-1;
		if (i-1>-1 && i-2>-1) {
			Shape s1 = p1.getRightHand().get(i);
			Shape s2 = p1.getRightHand().get(i-1);
			Shape s3 = p1.getRightHand().get(i-2);
			if (s1.getType() == s2.getType() && s2.getType() == s3.getType() &&
				s1.getColor() == s2.getColor() && s2.getColor() == s3.getColor()) {
				p1.setScore(p1.getScore()+1);
				p1.getRightHand().pop();
				p1.getRightHand().pop();
				p1.getRightHand().pop();
				p1.setRight_height(p1.getRight_height()-90);
			}
		}
		i = p2.getLeftHand().size()-1;
		if (i-1>-1 && i-2>-1) {
			Shape s1 = p2.getLeftHand().get(i);
			Shape s2 = p2.getLeftHand().get(i-1);
			Shape s3 = p2.getLeftHand().get(i-2);
			if (s1.getType() == s2.getType() && s2.getType() == s3.getType() &&
				s1.getColor() == s2.getColor() && s2.getColor() == s3.getColor()) {
				p2.setScore(p2.getScore()+1);
				p2.getLeftHand().pop();
				p2.getLeftHand().pop();
				p2.getLeftHand().pop();
				p2.setLeft_height(p2.getLeft_height()-90);
			}
		}
		i = p2.getRightHand().size()-1;
		if (i-1>-1 && i-2>-1) {
			Shape s1 = p2.getRightHand().get(i);
			Shape s2 = p2.getRightHand().get(i-1);
			Shape s3 = p2.getRightHand().get(i-2);
			if (s1.getType() == s2.getType() && s2.getType() == s3.getType() &&
				s1.getColor() == s2.getColor() && s2.getColor() == s3.getColor()) {
				p2.setScore(p2.getScore()+1);
				p2.getRightHand().pop();
				p2.getRightHand().pop();
				p2.getRightHand().pop();
				p2.setRight_height(p2.getRight_height()-90);
			}
		}
		gameState.setPlayer1(p1);
		gameState.setPlayer2(p2);
		return checkWinners();
	}
	private static GameState checkWinners()
	{
		if(gameState.getPlayer1().getRightHand().size()==12 || gameState.getPlayer1().getLeftHand().size()==12)
		{
			GameRunning.getInstance().notify(gameState.getPlayer2());
		}
		if(gameState.getPlayer2().getRightHand().size()==12 || gameState.getPlayer2().getLeftHand().size()==12)
		{
			GameRunning.getInstance().notify(gameState.getPlayer1());
		}
		return gameState;
	}
	private static GameState moveFallingShapes() {
		// Synchronized hashmap
		HashMap<Integer, Shape> myMap = gameState.getFallingShapes();
		Iterator ss =  myMap.values().iterator();
		while (ss.hasNext()) {
			Shape a = (Shape)ss.next();
			a.dropShape();
		}
		myMap = checkMap(myMap);
		gameState.setFallingShapes(myMap);
		return gameState;
	}

	private static HashMap<Integer, Shape> checkMap(HashMap<Integer, Shape> myMap) {
		// TODO Auto-generated method stub
	    Iterator itr = myMap.values().iterator();
	    while (itr.hasNext())
	    {
	        Shape o = (Shape)itr.next();
	        if (o.getyBound() == 570) 
	            itr.remove(); //remove the pair if key length is less then 3
	    }
		return myMap;
	}

	private static GameState moveShapesAndRails(Data data_in) {
		LinkedList<Shape> rail_1_Shapes = gameState.getRail1()
				.getShapes_currently_on_rail();
		LinkedList<Shape> rail_2_Shapes = gameState.getRail2()
				.getShapes_currently_on_rail();
		int startShapes1 = gameState.getRail1().getStartingX();
		int startShapes2 = 600;
		int numShapes1 = 0;
		int numShapes2 = 0;
		gameState.getRail1().changeEndingPoint();
		gameState.getRail2().changeEndingPoint();
		int rail1State = gameState.getRail1().getEndingX()
				- gameState.getRail1().getStartingX() - 30;
		int rail2State = gameState.getRail2().getStartingX()
				- gameState.getRail2().getEndingX() - 30;
		if (rail_1_Shapes.size() < (int) rail1State / 100) {
			gameState.getRail1().requestShape(gameState.getPool());
		}
		if (rail_2_Shapes.size() < (int) rail2State / 100) {
			gameState.getRail2().requestShape(gameState.getPool());
		}
		for (int i = gameState.getRail2().getShapes_currently_on_rail().size() - 1; i >= 0; i--) {
			Shape shape = gameState.getRail2().getShapes_currently_on_rail()
					.get(i);
			Random r = new Random();
			int a  = r.nextInt(10);
			shape.moveShape(shape.getxBound()-(a), 20);
			startShapes2 -= 30;
			numShapes2++;
			if (shape.getxBound() + 15 < gameState.getRail2().getEndingX()) {
				gameState.transferShapeFromRailToMap(gameState.getRail2(),
						shape);
			}
		}
		for (int i = gameState.getRail1().getShapes_currently_on_rail().size() - 1; i >= 0; i--) {
			Shape shape = gameState.getRail1().getShapes_currently_on_rail()
					.get(i);
			Random r = new Random();
			int a  = r.nextInt(10);
			shape.moveShape((shape.getxBound()+(10-a)), 20);
			startShapes1 += 30;
			numShapes1++;
			if (shape.getxBound() + 15 > gameState.getRail1().getEndingX())
				gameState.transferShapeFromRailToMap(gameState.getRail1(),
						shape);
		}
		GameState.setInstance(gameState);
		return gameState;
	}

	private static GameState checkPlayers() {
		// detect case and select function
		if(Math.abs(gameState.getPlayer1().getPos() - gameState.getPlayer2().getPos())<=9)
			return adjacentPlayers();
		else if(gameState.getPlayer1().getPos() - gameState.getPlayer2().getPos()<=(124+9) && gameState.getPlayer1().getPos() - gameState.getPlayer2().getPos()>=(124-9))
			return partiallyAdjacentPlayers(2);
		else if(gameState.getPlayer2().getPos() - gameState.getPlayer1().getPos()<=(124+9) && gameState.getPlayer2().getPos() - gameState.getPlayer1().getPos()>=(124-9))
			return partiallyAdjacentPlayers(1);
		else
			return awayPlayers();
	}

	private static GameState adjacentPlayers() {
		int p1LH = gameState.getPlayer1().getLeft_height();
		int p1RH = gameState.getPlayer1().getRight_height();
		int p2LH = gameState.getPlayer2().getLeft_height();
		int p2RH = gameState.getPlayer2().getRight_height();
		int lFlag = (p1LH > p2LH) ? 1 : 2;
		int rFlag = (p1RH > p2RH) ? 1 : 2;
		int bedayet_elleft_stack = (lFlag == 1) ? gameState.getPlayer1()
				.getPos() - (62) : gameState.getPlayer2().getPos() - (62);
		int bedayet_elright_stack = (rFlag == 1) ? gameState.getPlayer1()
				.getPos() + (97 - 62) : gameState.getPlayer1().getPos()
				+ (97 - 62);
		int maxL = Math.max(p1LH, p2LH);
		int maxR = Math.max(p1RH, p2RH);
		for (int i = 0; i <= ((lFlag == 1) ? 15 : 35); i++) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elleft_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elleft_stack + i);
				if (shape.getyBound() + 30 == 600 - (180 + maxL)) {
					// el player masak elshape
					if (lFlag == 1)
					{
						gameState.getPlayer1().getLeftHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
						gameState.getPlayer1().setLeft_height(gameState.getPlayer1().getLeft_height()+30);
					}
					else
					{
						gameState.getPlayer2().getLeftHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
						gameState.getPlayer2().setLeft_height(gameState.getPlayer1().getLeft_height()+30);
					}
					break;
				}
			}
		}
		for (int i = 0; i >= ((rFlag == 1) ? -20 : -40); i--) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elright_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elright_stack + i);
				if (shape.getyBound() + 30 == 600 - (180+maxR)) {
					// el player masak elshape
					if (lFlag == 1)
					{
						gameState.getPlayer1().getRightHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elright_stack + i);
						gameState.getPlayer1().setRight_height(gameState.getPlayer1().getRight_height()+30);
					}
					else
					{
						gameState.getPlayer2().getRightHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elright_stack + i);
						gameState.getPlayer2().setRight_height(gameState.getPlayer2().getRight_height()+30);
					}
					break;
				}
			}
		}
		return gameState;
	}

	private static GameState partiallyAdjacentPlayers(int ourFlag) {
		if (ourFlag == 1) {
			int bedayet_elleft_stack = gameState.getPlayer1().getPos() - (62);
			int bedayet_elright_stack = gameState.getPlayer2().getPos()+ (97 - 62);
			for (int i = 0; i <= 15; i++) {
				if (gameState.getFallingShapes().containsKey(
						bedayet_elleft_stack + i)) {
					Shape shape = gameState.getFallingShapes().get(
							bedayet_elleft_stack + i);
					if (shape.getyBound() + 30 == 600-(180 + gameState.getPlayer1().getLeft_height())) {
						// el player masak elshape
						{
							gameState.getPlayer1().getLeftHand().add(shape);
							gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
							gameState.getPlayer1().setLeft_height(gameState.getPlayer1().getLeft_height()+30);
							break;
						}
					}
				}
			}
			for (int i = 0; i >= -40; i--) {
				if (gameState.getFallingShapes().containsKey(
						bedayet_elright_stack + i)) {
					Shape shape = gameState.getFallingShapes().get(
							bedayet_elright_stack + i);
					if (shape.getyBound() + 30 == 600 - (180+gameState.getPlayer2().getRight_height())) {
						// el player masak elshape
						gameState.getPlayer2().getRightHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elright_stack + i);
						gameState.getPlayer2().setRight_height(gameState.getPlayer2().getRight_height()+30);
						break;
					}
				}
			}
			int temp1 =  gameState.getPlayer2().getPos() - (62);
			int temp2 = gameState.getPlayer1().getPos()+ (97 - 62);
			int min = Math.min(temp1, temp2);
			int priority =(gameState.getPlayer1().getRight_height()>gameState.getPlayer2().getLeft_height())?1:2;
			int maxH = (priority == 1)?gameState.getPlayer1().getRight_height():gameState.getPlayer2().getLeft_height();
			for (int i = 0; i <= 60; i++) {
				if (gameState.getFallingShapes().containsKey(min + i)) {
					Shape shape = gameState.getFallingShapes().get(min + i);
					if (shape.getyBound() + 30 == 600-(180+maxH)) {
						// el player masak elshape
						if(priority == 1)
						{
							gameState.getPlayer1().getRightHand().add(shape);
							gameState.getFallingShapes().remove(min + i);
							gameState.getPlayer1().setRight_height(gameState.getPlayer1().getRight_height()+30);
						}
						else
						{
							gameState.getPlayer2().getLeftHand().add(shape);
							gameState.getFallingShapes().remove(min + i);
							gameState.getPlayer2().setLeft_height(gameState.getPlayer2().getLeft_height()+30);
						}
						break;
					}
				}
			}
		}
		else
		{
			int bedayet_elleft_stack = gameState.getPlayer2().getPos() - (62);
			int bedayet_elright_stack = gameState.getPlayer1().getPos()+ (97 - 62);
			for (int i = 0; i <= 35; i++) {
				if (gameState.getFallingShapes().containsKey(
						bedayet_elleft_stack + i)) {
					Shape shape = gameState.getFallingShapes().get(
							bedayet_elleft_stack + i);
					if (shape.getyBound() + 30 == 600 - (180 + gameState.getPlayer2().getLeft_height())) {
						// el player masak elshape
						{
							gameState.getPlayer2().getLeftHand().add(shape);
							gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
							gameState.getPlayer2().setLeft_height(gameState.getPlayer2().getLeft_height()+30);
							break;
						}
						
					}
				}
			}
			for (int i = 0; i >= -20; i--) {
				if (gameState.getFallingShapes().containsKey(
						bedayet_elright_stack + i)) {
					Shape shape = gameState.getFallingShapes().get(
							bedayet_elright_stack + i);
					if (shape.getyBound() + 30 == 600-(180 + gameState.getPlayer1().getRight_height())) {
						// el player masak elshape
						{
							gameState.getPlayer1().getRightHand().add(shape);
							gameState.getFallingShapes().remove(bedayet_elright_stack + i);
							gameState.getPlayer1().setRight_height(gameState.getPlayer1().getRight_height()+30);
							break;
						}
						
					}
				}
			}
			int temp1 =  gameState.getPlayer1().getPos() - (62);
			int temp2 = gameState.getPlayer2().getPos()+ (97 - 62);
			int min = Math.min(temp1, temp2);
			int priority =(gameState.getPlayer2().getRight_height()>gameState.getPlayer1().getLeft_height())?2:1;
			int maxH = (priority == 1)?gameState.getPlayer1().getLeft_height():gameState.getPlayer2().getRight_height();
			for (int i = 0; i <= 60; i++) {
				if (gameState.getFallingShapes().containsKey(min + i)) {
					Shape shape = gameState.getFallingShapes().get(min + i);
					if (shape.getyBound() + 30 == 600-(180+maxH)) {
						// el player masak elshape
						if(priority == 1)
						{
							gameState.getPlayer1().getLeftHand().add(shape);
							gameState.getFallingShapes().remove(min + i);
							gameState.getPlayer1().setLeft_height(gameState.getPlayer1().getLeft_height()+30);
						}
						else
						{
							gameState.getPlayer2().getRightHand().add(shape);
							gameState.getFallingShapes().remove(min + i);
							gameState.getPlayer2().setRight_height(gameState.getPlayer2().getRight_height()+30);
						}
						break;
					}
				}
			}
		}
		return gameState;
	}

	private static GameState awayPlayers() {
		// player 1 code
		int bedayet_elleft_stack = gameState.getPlayer1().getPos() - (62);
		int bedayet_elright_stack = gameState.getPlayer1().getPos() + (97 - 62);
		for (int i = 0; i <= 15; i++) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elleft_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elleft_stack + i);
				if (shape.getyBound() + 30 == 600 - (180 + gameState.getPlayer1().getLeft_height())) {
					// el player masak elshape
					{
						gameState.getPlayer1().getLeftHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
						gameState.getPlayer1().setLeft_height(gameState.getPlayer1().getLeft_height()+30);
						break;
					}
				}
			}
		}
		for (int i = 0; i >= -20; i--) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elright_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elright_stack + i);
				if (shape.getyBound() + 30 == 600 - (180 + gameState.getPlayer1().getRight_height())) {
					// el player masak elshape
					{
						gameState.getPlayer1().getRightHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elright_stack + i);
						gameState.getPlayer1().setRight_height(gameState.getPlayer1().getRight_height()+30);
						break;
					}
				}
			}
		}
		// player 2 code
		bedayet_elleft_stack = gameState.getPlayer2().getPos() - (62);
		bedayet_elright_stack = gameState.getPlayer2().getPos() + (97 - 62);
		for (int i = 0; i <= 35; i++) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elleft_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elleft_stack + i);
				if (shape.getyBound() + 30 == 600 - (180 + gameState.getPlayer2().getLeft_height())) {
					// el player masak elshape
					{
						gameState.getPlayer2().getLeftHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elleft_stack + i);
						gameState.getPlayer2().setLeft_height(gameState.getPlayer2().getLeft_height()+30);
						break;
					}
				}
			}
		}
		for (int i = 0; i >= -40; i--) {
			if (gameState.getFallingShapes().containsKey(
					bedayet_elright_stack + i)) {
				Shape shape = gameState.getFallingShapes().get(
						bedayet_elright_stack + i);
				if (shape.getyBound() + 30 == 600 - (180 + gameState.getPlayer2().getRight_height())) {
					// el player masak elshape
					{
						gameState.getPlayer2().getRightHand().add(shape);
						gameState.getFallingShapes().remove(bedayet_elright_stack + i);
						gameState.getPlayer2().setRight_height(gameState.getPlayer2().getRight_height()+30);
						break;
					}
					
				}
			}
		}
		return gameState;
	}
}
