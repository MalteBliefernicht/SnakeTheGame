package application;
	
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	String input;
	Image imageWall;
	Image imageHead;
	Image imageBody;
	Image imageApple;
	
	@Override
	public void start(Stage stage) {
		int y = 10;
		int x = 10;
		int multiplikator = 35;
		int width = y * multiplikator;
		int height = x * multiplikator;
		int intervalLength = 400;
		
		imageWall = new Image("file:resources/brickwall.jpg");
		imageHead = new Image("file:resources/head.png");
		imageBody = new Image("file:resources/body.png");
		imageApple = new Image("file:resources/apple.png");
		
		this.input = "w";
		Gameboard gameboard = new Gameboard(y, x);
		Snake snake = new Snake(y / 2, x / 2);
		
		gameboard.setHead(snake.head);
		gameboard.setBody(snake.body);
		
		Apple apple = new Apple(gameboard);
		Points points = new Points();
		
		stage.setTitle("Snake - The Game");		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext context = canvas.getGraphicsContext2D();
		context.setTextAlign(TextAlignment.LEFT);
		context.setFont(new Font(15));
		context.setStroke(Color.WHITE);
		Group group = new Group(canvas);		
		Scene scene = new Scene(group , width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
			
		Timer timer = new Timer();	
		TimerTask task = new TimerTask() {
			public void run() {
				boolean allGood = gameTurn(gameboard, snake, apple, points);
				
				if (allGood) {
					draw(canvas, context, gameboard, points, multiplikator);
				}
				else {
					endscreen(canvas, context);
					timer.cancel();
					timer.purge();
				}
			}
		};		
		timer.scheduleAtFixedRate(task, 0, intervalLength);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getText().equals("w") || e.getText().equals("a") || e.getText().equals("s") || e.getText().equals("d")) {
					setInput(e.getText());
				}
			}
		});
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public boolean gameTurn(Gameboard board, Snake snake, Apple apple, Points points) {
		snake.moveHead(this.input);
		
		if (checkAppleEaten(snake, apple)) {
			apple.initApple(board);
			points.addPoints();
		}
		else {
			snake.removeBody();
		}

		if (checkFailState(board, snake)) {
			return false;
		}
		else {
			board.clearBoard();
			board.setHead(snake.head);
			board.setBody(snake.body);
			board.setApple(apple.pos);
			return true;
		}	
	}
	
	public boolean checkAppleEaten(Snake snake, Apple apple) {
		if (snake.head.equals(apple.pos)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkFailState(Gameboard board, Snake snake) {
		if (board.board[snake.head.y][snake.head.x] == "wall" || board.board[snake.head.y][snake.head.x] == "body") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void endscreen(Canvas canvas, GraphicsContext context) {
		context.setTextAlign(TextAlignment.CENTER);
		context.setFont(new Font(30));
		context.setStroke(Color.BLACK);
		context.strokeText("GAME OVER!", canvas.getWidth()/2, canvas.getHeight()/2);
	}
	
	public void draw(Canvas canvas, GraphicsContext context, Gameboard board, Points points, int multi) {
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for (int y = 0; y < board.board.length; y++) {
			for (int x = 0; x < board.board[0].length; x++) {
				if (board.board[y][x] == "wall") {
					context.drawImage(this.imageWall, x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "head") {
					context.drawImage(this.imageHead, x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "body") {
					context.drawImage(this.imageBody, x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "apple") {
					context.drawImage(this.imageApple, x * multi, y * multi, multi, multi);
				}
			}
		}
		
		context.strokeText("Points: " + Integer.toString(points.points), 10, 20);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
