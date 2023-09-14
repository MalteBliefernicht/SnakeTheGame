package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
	
	Point pos;
	Random rand;
	
	public Apple(Gameboard gameboard) {
		this.pos = new Point();
		this.rand = new Random();
		initApple(gameboard);
	}
	
	public void initApple(Gameboard gameboard) {
		ArrayList<Point> validPoints = new ArrayList<Point>();
		
		for (int y = 0; y < gameboard.board.length; y++) {
			for (int x = 0; x < gameboard.board[0].length; x++) {
				if (gameboard.board[y][x].equals("")) {
					validPoints.add(new Point(x, y));
				}
			}
		}
		
		this.pos = validPoints.get(rand.nextInt(validPoints.size()));
	}
}
