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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class Main extends Application {
	
	String input;
	
	@Override
	public void start(Stage stage) {
		int y = 20;
		int x = 20;
		int multiplikator = 25;
		int width = y * multiplikator;
		int height = x * multiplikator;
		int intervalLength = 500;
		
		this.input = "w";
		Gameboard gameboard = new Gameboard(y, x);
		Snake snake = new Snake(y / 2, x / 2);
		
		gameboard.setHead(snake.head);
		gameboard.setBody(snake.body);
		
		stage.setTitle("Snake - The Game");		
		Canvas canvas = new Canvas(width, height);
		GraphicsContext context = canvas.getGraphicsContext2D();		
		Group group = new Group(canvas);		
		Scene scene = new Scene(group , width, height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
			
		Timer timer = new Timer();	
		TimerTask task = new TimerTask() {
			public void run() {
				boolean allGood = gameTurn(gameboard, snake);
				
				if (allGood) {
					draw(canvas, context, gameboard, multiplikator);
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
	
	public boolean gameTurn(Gameboard board, Snake snake) {
		snake.moveHead(this.input);
		
		if (checkFailState(board, snake)) {
			return false;
		}
		else {
			board.clearBoard();
			board.setHead(snake.head);
			board.setBody(snake.body);
			return true;
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
		context.strokeText("GAME OVER!", canvas.getWidth()/2, canvas.getHeight()/2);
	}
	
	public void draw(Canvas canvas, GraphicsContext context, Gameboard board, int multi) {
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for (int y = 0; y < board.board.length; y++) {
			for (int x = 0; x < board.board[0].length; x++) {
				if (board.board[y][x] == "wall") {
					context.setFill(Color.BLACK);
					context.fillRect(x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "head") {
					context.setFill(Color.BLUE);
					context.fillRect(x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "body") {
					context.setFill(Color.BLUE);
					context.fillRect(x * multi, y * multi, multi, multi);
				}
				else if (board.board[y][x] == "apple") {
					context.setFill(Color.RED);
					context.fillRect(x * multi, y * multi, multi, multi);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
