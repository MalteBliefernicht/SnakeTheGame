package application;

import java.awt.Point;
import java.util.ArrayList;

public class Gameboard {
	
	String[][] board;
	
	public Gameboard(int height, int width) {
		this.board = new String[height][width];
		initBoard();
	}
	
	public void initBoard() {
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board[0].length; x++) {
				if (y == 0 || y == this.board.length - 1 || x == 0 || x == this.board[0].length - 1) {
					this.board[y][x] = "#";
				}
				else {
					this.board[y][x] = " ";
				}
			}
		}
	}
	
	public void setHead(Point point) {
		this.board[point.y][point.x] = "O";
	}
	
	public void setBody(ArrayList<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			this.board[points.get(i).y][points.get(i).x] = "X";
		}
	}
	
	public void setApple(Point point) {
		this.board[point.y][point.x] = "@";
	}
	
	public void drawBoard() {	
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board[0].length; x++) {
				System.out.print(this.board[y][x]);
			}
			System.out.println();
		}
	}
}
