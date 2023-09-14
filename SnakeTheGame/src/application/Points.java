package application;

public class Points {
	
	int count;
	int points;
	
	public Points () {
		this.count = 0;
		this.points = 0;
	}
	
	public void addPoints() {
		this.points += (100 + 10 * this.count++);
	}
}
