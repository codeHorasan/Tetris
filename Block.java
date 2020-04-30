import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Block {
	private String type;
	private int direction;
	private Color color;
	public ArrayList<Point> points = new ArrayList<Point>();
	
	
	public Block(String type) {
		this.type = type;
		this.direction = 0;
		
		if(this.type.equals("I")) 
			color = Color.cyan;
		else if(this.type.equals("J"))
			color = Color.blue;
		else if(this.type.equals("L"))
			color = Color.orange;
		else if(this.type.equals("S"))
			color = Color.green;
		else if(this.type.equals("Z"))
			color = Color.red;	
		else if(this.type.equals("T")) {
			color = Color.magenta;
		}
		else if(this.type.equals("O")) {
			color = Color.yellow;
		}
		
		points.add(new Point(0,0));
		points.add(new Point(0,0));
		points.add(new Point(0,0));
		points.add(new Point(0,0));
		setInitialPoints();
	}
	
	public void setInitialPoints() {
		
		if(this.getType().equals("I")) {
			points.set(0, new Point(200,0));
			points.set(1, new Point(250,0));
			points.set(2, new Point(300,0));
			points.set(3, new Point(350,0));
		}
		
		else if(this.getType().equals("J")) {
			points.set(0, new Point(200,-50));
			points.set(1, new Point(200,0));
			points.set(2, new Point(250,0));
			points.set(3, new Point(300,0));
		}
		
		else if(this.getType().equals("L")) {
			points.set(0, new Point(200,0));
			points.set(1, new Point(250,0));
			points.set(2, new Point(300,0));
			points.set(3, new Point(300,-50));
		}
		
		else if(this.getType().equals("S")) {
			points.set(0, new Point(200,0));
			points.set(1, new Point(250,0));
			points.set(2, new Point(250,-50));
			points.set(3, new Point(300,-50));
		}
		
		else if(this.getType().equals("Z")) {
			points.set(0, new Point(200,-50));
			points.set(1, new Point(250,-50));
			points.set(2, new Point(250,0));
			points.set(3, new Point(300,0));
		}
		
		else if(this.getType().equals("T")) {
			points.set(0, new Point(200,0));
			points.set(1, new Point(250,0));
			points.set(2, new Point(300,0));
			points.set(3, new Point(250,-50	));
		}
		
		else if(this.getType().equals("O")) {
			points.set(0, new Point(200,0));
			points.set(1, new Point(200,-50));
			points.set(2, new Point(250,0));
			points.set(3, new Point(250,-50));
		}
		
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getDirection() {
		return direction;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public Color getColor() {
		return this.color;
	}
	

	public ArrayList<Point> getPoints() {
		return points;
	}


	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
}