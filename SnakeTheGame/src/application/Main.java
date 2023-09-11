package application;
	
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Gameboard gameboard;
	Snake snake;
	String input;
	
	@Override
	public void start(Stage stage) {
		int height = 20;
		int width = 20;
		int multiplikator = 20;
		this.input = "w";
		
		this.gameboard = new Gameboard(height, width);
		
		this.snake = new Snake(height / 2, width / 2);
		
		gameboard.setHead(snake.head);
		gameboard.setBody(snake.body);
		
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			public void run() {
				gameTurn();
			}
		};
		
		timer.scheduleAtFixedRate(task, 0, 1000);
		
		try {
			stage.setTitle("Snake - The Game");
			
			Canvas canvas = new Canvas(width * multiplikator, height * multiplikator);
			GraphicsContext context = canvas.getGraphicsContext2D();
			
			Group group = new Group(canvas);
			
			
			Scene scene = new Scene(group , width * multiplikator, height * multiplikator);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gameTurn() {
		gameboard.initBoard();
		
		snake.moveHead(this.input);
		
		gameboard.setHead(snake.head);
		gameboard.setBody(snake.body);
		//gameboard.drawBoard();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
