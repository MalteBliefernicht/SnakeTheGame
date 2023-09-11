package application;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
	
	Point head;
	ArrayList<Point> body;
	
	public Snake(int startY, int startX) {
		this.head = new Point(startX, startY);
		this.body = new ArrayList<Point>();
		initBody(startY, startX);
	}
	
	public void initBody(int startY, int startX) {
		this.body.add(new Point(startX, startY + 1));
		this.body.add(new Point(startX, startY + 2));
		this.body.add(new Point(startX, startY + 3));
	}
	
	public void moveHead(String input) {
		moveBody();
		
		if (input == "w") {
			this.head.y--;
		}
		else if (input == "s") {
			this.head.y++;
		}
		else if (input == "a") {
			this.head.x--;
		}
		else if (input == "d") {
			this.head.x++;
		}
	}
	
	public void moveBody() {
		Point headPos = new Point(this.head.x, this.head.y);
		this.body.add(0, headPos);
		this.body.remove(this.body.size() - 1);
	}
}
