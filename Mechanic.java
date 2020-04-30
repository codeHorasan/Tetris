import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class Mechanic implements KeyListener {
	private boolean stop = false;
	public ArrayList<Block> blocks = new ArrayList<Block>();
	private boolean threadControl = false;
	private int direction;	
	private Random random = new Random();	
	public ArrayList<String> types = new ArrayList<String>();
	public String nextFirstType, nextType;
	private int score = 0;
	private boolean gameOver = false;
	private Font font = new Font("Arial", Font.ITALIC, 30);
	private Font font2 = new Font("Serif", Font.BOLD, 20);
	private Font font3 = new Font("Arial", Font.BOLD, 20);
	private boolean pause = false;
	
	
	public Mechanic() {
		types.add("I");
		types.add("J");
		types.add("L");
		types.add("S");
		types.add("Z");
		types.add("T");
		types.add("O");
		nextFirstType = types.get(random.nextInt(types.size()));
		blocks.add(new Block(types.get(random.nextInt(types.size() - 1))));
		direction = blocks.get(blocks.size() - 1).getDirection();
	}
	
	public Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true) {
				try {
					Thread.currentThread().sleep(900);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(stop == false && !pause) {
					for (Point point : blocks.get(blocks.size() - 1).points) {
						point.y += 50;
					}
				}
			}
			
		}
	});
	
	public String determineNext() {
		if(blocks.size() <= 1) {
			nextType = nextFirstType;
		}
		return nextType;
	}
	
	public void showNext(Graphics g) {
		String toDraw = determineNext();
		if(toDraw.equals("I")) {
			g.setColor(Color.cyan);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(800, 150, 50, 50);
			g.fillRect(850, 150, 50, 50);		
		}
		else if(toDraw.equals("J")) {
			g.setColor(Color.blue); 
			g.fillRect(700, 100, 50, 50);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(800, 150, 50, 50);
		}
		else if(toDraw.equals("L")) {
			g.setColor(Color.orange);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(800, 150, 50, 50);
			g.fillRect(800, 100, 50, 50);
		}
		else if(toDraw.equals("S")) {
			g.setColor(Color.green);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(750, 100, 50, 50);
			g.fillRect(800, 100, 50, 50);
		}
		else if(toDraw.equals("Z")) {
			g.setColor(Color.red);
			g.fillRect(700, 100, 50, 50);
			g.fillRect(750, 100, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(800, 150, 50, 50);
		}
		else if(toDraw.equals("T")) {
			g.setColor(Color.magenta);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(750, 100, 50, 50);
			g.fillRect(800, 150, 50, 50);
		}
		else if(toDraw.equals("O")) {
			g.setColor(Color.yellow);
			g.fillRect(700, 150, 50, 50);
			g.fillRect(700, 100, 50, 50);
			g.fillRect(750, 150, 50, 50);
			g.fillRect(750, 100, 50, 50);
		}
		g.setColor(Color.black);
		g.drawLine(670, 70, 920, 70);
		g.drawLine(920, 70, 920, 230);
		g.drawLine(670, 230, 920, 230);
		g.drawLine(670, 70, 670, 230);
		
	}
	
	public void stabilCollision() {
		boolean collied = false;
		Block block = blocks.get(blocks.size() - 1);
		ArrayList<Point> list = new ArrayList<Point>();
		ArrayList<Point> otherList = new ArrayList<Point>();
		
		for(int i=0; i<4; i++) {
			list.add(new Point(block.getPoints().get(i).x, block.getPoints().get(i).y + 50));
		}
		
		for(int i=0; i<blocks.size() - 1; i++) {
			for(int j=0; j<4; j++) {
				otherList.add(blocks.get(i).getPoints().get(j));
			}
		}

		for (Point p1 : list) {
			for (Point p2 : otherList) {
				if(p1.equals(p2)) {
					collied = true;
				}
			}
		}
		
		if(collied) {
			ArrayList<Integer> yPoints = new ArrayList<Integer>();
			for(int i=0; i<4; i++) {
				yPoints.add(block.getPoints().get(i).y);
			}
			for (Integer y : yPoints) {
				if(y <= 0) {
					stop = true;
					gameOver = true;
				}
			}
			if(!stop) {
				direction = 0;
				blocks.add(new Block(nextType));
				nextType = types.get(random.nextInt(types.size()));
				score += 16;
			}
		}
		
		checkFilled();

	}
	
	public void bottomCollision() {
		Block block = blocks.get(blocks.size() - 1);
		boolean collied = false;
		
		for (Point p : block.getPoints()) {
			if(p.y >= 550) {
				collied = true;
			}
		}
		if(collied) {
			direction = 0;
			blocks.add(new Block(nextType));
			nextType = types.get(random.nextInt(types.size()));
			score += 16;
		}		
		
	}
	
	public void checkFilled() {
		int determinedY = 0;
		boolean checkRow = false;
		ArrayList<Point> blockPoints = new ArrayList<Point>();
				
		for(int i=0; i<blocks.size() - 1; i++) {
			for(int j=0; j<4; j++) {
				blockPoints.add(blocks.get(i).getPoints().get(j));
			}
		}
		
		for(int y = 550; y >= 50; y -= 50) {
			ArrayList<Point> screenPoints = new ArrayList<Point>();
			for(int x = 0; x <= 550; x += 50) {
				screenPoints.add(new Point(x,y));
			}
			if(blockPoints.containsAll(screenPoints)) {
				determinedY = y;
				checkRow = true;
				score += 64;
			}
		}
		
		if(checkRow) {
			for (Block block : blocks) {
				for(int i=0; i<block.getPoints().size(); i++) {
					if(block.points.get(i).y == determinedY) {
						block.points.set(i, new Point(1000,1000));
					}
					if(block.points.get(i).y < determinedY) {
						block.points.get(i).y += 50;
					}
				}
			}
		}
	}
	
	public void showStrings(Graphics g) {
		g.setFont(font);
		g.drawString("Score : " + score, 680, 285);
		g.setFont(font3);
		g.drawString("Press SPACE to Pause", 660, 375);
		
		if(gameOver) {
			g.setFont(font2);
			g.drawString("GAME OVER!", 220, 250);
			g.drawString("Your Score is : " + score, 220, 280);
		}
		
	}
	
	public void render(Graphics g) {
		
		for (Block block : blocks) {
			g.setColor(block.getColor());
			for (Point p : block.getPoints()) {
				g.fillRect(p.x, p.y, 50, 50);
			}
		}
		showNext(g);
		showStrings(g);
	}
	
	public void update() {
		if(stop) 
			threadControl = true;
		
		if(!threadControl) {
			thread.start();
			threadControl = true;
		}

		bottomCollision();
		stabilCollision();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(stop) {
			return;
		}
		
		int c = e.getKeyCode();
	
		if(c == KeyEvent.VK_RIGHT) {
			ArrayList<Point> list = new ArrayList<Point>();
			if(blocks.get(blocks.size() - 1).getType().equals("I")) {
				Point p = new Point(blocks.get(blocks.size() - 1).getPoints().get(3).x+50, 
						blocks.get(blocks.size()-1).getPoints().get(3).y);
				
				for(int i=0; i<blocks.size() - 1; i++) {
					for(int j=0; j<4; j++) {
						list.add(blocks.get(i).getPoints().get(j));
					}
				}
				
				for (Point point : list) {
					if(point.equals(p)) {
						return;
					}
				}
			}
			
			else {
				Block block = blocks.get(blocks.size() - 1);
				Point p1 = new Point(block.getPoints().get(0).x + 50, block.getPoints().get(0).y);
				Point p2 = new Point(block.getPoints().get(1).x + 50, block.getPoints().get(1).y);
				Point p3 = new Point(block.getPoints().get(2).x + 50, block.getPoints().get(2).y);
				Point p4 = new Point(block.getPoints().get(3).x + 50, block.getPoints().get(3).y);
				
				for(int i=0; i<blocks.size() - 1; i++) {
					for(int j=0; j<4; j++) {
						list.add(blocks.get(i).getPoints().get(j));
					}
				}
				
				for (Point point : list) {
					if(point.equals(p1) || point.equals(p2) || point.equals(p3) || point.equals(p4)) {
						return;
					}
				}	
			}		
	
			
			boolean boundary = false;
			for(int i=0; i<blocks.get(blocks.size() - 1).points.size(); i++) {
				if(blocks.get(blocks.size() - 1).points.get(i).x >= 550) {
					boundary = true;
				}
			}
			if(!boundary) {
				for (Point point : blocks.get(blocks.size() - 1).points) {
					point.x += 50;
				}
			}
		}
		
		else if(c == KeyEvent.VK_LEFT) {
			ArrayList<Point> list = new ArrayList<Point>();
			
			if(blocks.get(blocks.size() - 1).getType().equals("I")) {
				Point p = new Point(blocks.get(blocks.size() - 1).getPoints().get(0).x-50, 
						blocks.get(blocks.size()-1).getPoints().get(0).y);
				Point p2 = new Point(blocks.get(blocks.size() - 1).getPoints().get(3).x-50, 
						blocks.get(blocks.size()-1).getPoints().get(3).y);
				
				for(int i=0; i<blocks.size() - 1; i++) {
					for(int j=0; j<4; j++) {
						list.add(blocks.get(i).getPoints().get(j));
					}
				}
				
				for (Point point : list) {
					if(point.equals(p) || point.equals(p2)) {
						return;
					}
				}
			}
			
			else {
				Block block = blocks.get(blocks.size() - 1);
				Point p1 = new Point(block.getPoints().get(0).x - 50, block.getPoints().get(0).y);
				Point p2 = new Point(block.getPoints().get(1).x - 50, block.getPoints().get(1).y);
				Point p3 = new Point(block.getPoints().get(2).x - 50, block.getPoints().get(2).y);
				Point p4 = new Point(block.getPoints().get(3).x - 50, block.getPoints().get(3).y);
				
				for(int i=0; i<blocks.size() - 1; i++) {
					for(int j=0; j<4; j++) {
						list.add(blocks.get(i).getPoints().get(j));
					}
				}
				
				for (Point point : list) {
					if(point.equals(p1) || point.equals(p2) || point.equals(p3) || point.equals(p4)) {
						return;
					}
				}			
			}
			
			
			boolean boundary = false;
			for(int i=0; i<blocks.get(blocks.size() - 1).points.size(); i++) {
				if(blocks.get(blocks.size() - 1).points.get(i).x <= 0) {
					boundary = true;
				}
			}
			if(!boundary) {
				for (Point point : blocks.get(blocks.size() - 1).points) {
					point.x -= 50;
				}
			}
		}
		
		else if(c == KeyEvent.VK_UP) {
			Block block = blocks.get(blocks.size() - 1);
			
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			Point p3 = new Point(0,0);

			if(block.getType().equals("I")) {
				p1 = new Point(block.getPoints().get(0).x - 50, block.getPoints().get(2).y + 100);
				p2 = new Point(block.getPoints().get(0).x + 50, block.getPoints().get(2).y + 100);
				p3 = new Point(block.getPoints().get(0).x + 100, block.getPoints().get(2).y + 100);
				
				if(block.getDirection() == 1 || block.getDirection() == 3) {
					if(block.getPoints().get(0).x == 0 || block.getPoints().get(0).x >= 500) 
						return;			
				}
				
				if(blocks.size() >= 2) {
					if(block.getDirection() == 1 || block.getDirection() == 3) {
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p1)) 
									return;
								else if(blocks.get(i).getPoints().get(j).equals(p2)) 
									return;
								else if(blocks.get(i).getPoints().get(j).equals(p3)) 
									return;
							}
						}
					}
				}
			}
			if(block.getType().equals("J")) {
				if((block.points.get(1).x == 0 && block.points.get(2).x == 0 && block.points.get(3).x == 0) || 
						block.points.get(1).x == 550 && block.points.get(2).x == 550 && block.points.get(3).x == 550) {
					return;
				}
				
			}
			
			if(block.getType().equals("J")) {
				if(blocks.size() >= 2) {
					if(block.getDirection() == 1) {
						Point p0 = new Point(block.getPoints().get(0).x, block.getPoints().get(0).y + 100);
						Point p22 = new Point(block.getPoints().get(3).x - 50, block.getPoints().get(3).y - 50);
						
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p0) || 
										blocks.get(i).getPoints().get(j).equals(p22)) 
									return;
							}
						}						
					}
					else if(block.getDirection() == 3) {
						Point p0 = new Point(block.getPoints().get(1).x + 50, block.getPoints().get(1).y + 50);
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p0)) 
									return;
							}
						}	
					}
					
				}		
			}
			
			if(block.getType().equals("L")) {
				if((block.points.get(0).x == 0 && block.points.get(1).x == 0 && block.points.get(2).x == 0) || 
						block.points.get(0).x == 550 && block.points.get(1).x == 550 && block.points.get(2).x == 550) {
					return;
				}
				if(blocks.size() >= 2) {
					if(block.getDirection() == 1) {
						Point p11 = new Point(block.getPoints().get(1).x - 50, block.getPoints().get(1).y);
						Point p22 = new Point(block.getPoints().get(2).x - 50, block.getPoints().get(2).y);
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p11) ||
										blocks.get(i).getPoints().get(j).equals(p22)) 
									return;
							}
						}
					}
					else if(block.getDirection() == 3) {
						Point p0 = new Point(block.getPoints().get(2).x + 50, block.getPoints().get(2).y + 50);
						Point p00 = new Point(block.getPoints().get(2).x + 50, block.getPoints().get(2).y);
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p0) ||
										blocks.get(i).getPoints().get(j).equals(p00)) 
									return;
							}
						}	
					}				
				}				
			}
			
			if(block.getType().equals("S")) {
				if(block.getDirection() == 1 || block.getDirection() == 3) {
					if(block.getPoints().get(0).x == 0) {
						return;
					}
					Point p00 = new Point(block.getPoints().get(0).x - 50, block.getPoints().get(0).y + 100);
					Point p11 = new Point(block.getPoints().get(0).x + 50, block.getPoints().get(0).y);
					if(blocks.size() >= 2) {
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p00) ||
										blocks.get(i).getPoints().get(j).equals(p11)) 
									return;
							}
						}
					}
				}
				else if(block.getDirection() == 0 || block.getDirection() == 2) {
					Point p00 =  new Point(block.getPoints().get(3).x, block.getPoints().get(3).y + 50);
					Point p11 =  new Point(block.getPoints().get(2).x, block.getPoints().get(2).y - 50);
					if(blocks.size() >= 2) {
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p00) ||
										blocks.get(i).getPoints().get(j).equals(p11)) 
									return;
							}
						}
					}
				}	
			}
			
			if(block.getType().equals("Z")) {
				if(block.getPoints().get(2).x == 0) {
					return;
				}
				if(block.getDirection() == 0 || block.getDirection() == 2) {
					Point p = new Point(block.getPoints().get(1).x + 50, block.getPoints().get(1).y);
					Point pp = new Point(block.getPoints().get(1).x + 50, block.getPoints().get(1).y - 50);
					if(blocks.size() >= 2) {
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p) ||
										blocks.get(i).getPoints().get(j).equals(pp)) 
									return;
							}
						}
					}
				}
				else if(block.getDirection() == 1 || block.getDirection() == 3) {
					Point p = new Point(block.getPoints().get(2).x, block.getPoints().get(2).y - 50);
					Point pp = new Point(block.getPoints().get(2).x - 50, block.getPoints().get(2).y - 50);
					if(blocks.size() >= 2) {
						for(int i=0; i<=blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p) ||
										blocks.get(i).getPoints().get(j).equals(pp)) 
									return;
							}
						}
					}
				}
			}
			
			if(block.getType().equals("T")) {
				if((block.getPoints().get(0).x == 0 && block.getPoints().get(1).x == 0 && block.getPoints().get(2).x == 0) || 
				block.getPoints().get(0).x == 550 && block.getPoints().get(1).x == 550 && block.getPoints().get(2).x == 550){
					return;
				}
				if(block.getDirection() == 0) {
					Point p11 = new Point(block.getPoints().get(1).x, block.getPoints().get(1).y + 50);
					if(blocks.size() >= 2) {
						for(int i=0; i<blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p11))
									return;
							}
						}
					}
				}
				else if(block.getDirection() == 1) {
					Point p11 = new Point(block.getPoints().get(1).x - 50, block.getPoints().get(1).y);
					if(blocks.size() >= 2) {
						for(int i=0; i<blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p11)) 
									return;
							}
						}
					}
				}
				else if(block.getDirection() == 2) {
					Point p11 = new Point(block.getPoints().get(1).x, block.getPoints().get(1).y - 50);
					if(blocks.size() >= 2) {
						for(int i=0; i<blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p11))
									return;
							}
						}
					}
				}
				else if(block.getDirection() == 3) {
					Point p11 = new Point(block.getPoints().get(1).x + 50, block.getPoints().get(1).y);
					if(blocks.size() >= 2) {
						for(int i=0; i<blocks.size() - 1; i++) {
							for(int j=0; j<4; j++) {
								if(blocks.get(i).getPoints().get(j).equals(p11))
									return;
							}
						}
					}
				}
			}		
			
			if(direction == 3)
				direction = 0;
			else {
				direction++;
			}
			block.setDirection(direction);
	
			if(block.getType().equals("I")) {
				if(block.getDirection() == 1 || block.getDirection() == 3) {
					block.points.get(0).x += 50;
					block.points.get(2).x -= 50;
					block.points.get(3).x -= 100;
					block.points.get(3).y += 50;
					block.points.get(2).y -= 50;
					block.points.get(0).y -= 100;
				}
				else if(block.getDirection() == 0 || block.getDirection() == 2) {
					block.points.get(0).x -= 50;
					block.points.get(2).x += 50;
					block.points.get(3).x += 100;
					block.points.get(3).y -= 50;
					block.points.get(2).y += 50;
					block.points.get(0).y += 100;
				}
			}
			else if(block.getType().equals("J")) {	
				boolean zeroAgain = false;
				if(block.getDirection() == 1) {
					block.points.get(0).x += 100;
					block.points.get(1).x += 50;
					block.points.get(3).x -= 50;
					block.points.get(1).y -= 50;
					block.points.get(3).y += 50;
				}
				else if(block.getDirection() == 2) {
					block.points.get(0).y += 100;
					block.points.get(1).x += 50;
					block.points.get(3).x -= 50;
					block.points.get(1).y += 50;
					block.points.get(3).y -= 50;
				}
				else if(block.getDirection() == 3) {
					zeroAgain = true;
					block.points.get(0).x -= 100;
					block.points.get(1).x -= 50;
					block.points.get(1).y -= 50;
					block.points.get(3).y += 50;
					block.points.get(3).x += 50;
				}
				else if(zeroAgain || block.getDirection() == 0) {
					block.points.get(0).y -= 100;
					block.points.get(1).x -= 50;
					block.points.get(3).x += 50;
					block.points.get(1).y += 50;
					block.points.get(3).y -= 50;
				}
				
			}
			else if(block.getType().equals("L")) {	
				boolean zeroAgain = false;
				if(block.getDirection() == 1) {
					block.points.get(0).x += 50;
					block.points.get(0).y -= 50;
					block.points.get(2).x -= 50;
					block.points.get(2).y += 50;
					block.points.get(3).y += 100;
				}
				else if(block.getDirection() == 2) {
					block.points.get(0).x += 50;
					block.points.get(0).y += 50;
					block.points.get(2).x -= 50;
					block.points.get(2).y -= 50;
					block.points.get(3).x -= 100;
				}
				else if(block.getDirection() == 3) {
					zeroAgain = true;
					block.points.get(0).x -= 50;
					block.points.get(0).y += 50;
					block.points.get(2).x += 50;
					block.points.get(2).y -= 50;
					block.points.get(3).y -= 100;
				}
				else if(zeroAgain || block.getDirection() == 0) {
					block.points.get(0).x -= 50;
					block.points.get(0).y -= 50;
					block.points.get(2).x += 50;
					block.points.get(2).y += 50;
					block.points.get(3).x += 100;
				}			
			}
			else if(block.getType().equals("S")) {
				if(block.getDirection() == 1 || block.getDirection() == 3) {
					block.points.get(0).x += 50;
					block.points.get(0).y -= 100;
					block.points.get(1).y -= 50;
					block.points.get(2).x += 50;
					block.points.get(3).y += 50;
				}
				else if(block.getDirection() == 2 || (block.getDirection() == 0)) {
					block.points.get(0).x -= 50;
					block.points.get(0).y += 100;
					block.points.get(1).y += 50;
					block.points.get(2).x -= 50;
					block.points.get(3).y -= 50;
				}			
			}
			else if(block.getType().equals("Z")) {
				if(block.getDirection() == 1 || block.getDirection() == 3) {
					block.points.get(0).x += 100;
					block.points.get(1).x += 50;
					block.points.get(1).y += 50;
					block.points.get(3).x -= 50;	
					block.points.get(3).y += 50;	
				}
				else if(block.getDirection() == 2 || (block.getDirection() == 0)) {
					block.points.get(0).x -= 100;
					block.points.get(1).x -= 50;
					block.points.get(1).y -= 50;
					block.points.get(3).x += 50;	
					block.points.get(3).y -= 50;
				}
			}
			else if(block.getType().equals("T")) {
				boolean zeroAgain = false;
				if(block.getDirection() == 1) {
					block.points.get(3).x += 50;
					block.points.get(3).y += 50;
					block.points.get(2).x -= 50;
					block.points.get(2).y += 50;
					block.points.get(0).x += 50;
					block.points.get(0).y -= 50;
				}
				else if(block.getDirection() == 2) {
					block.points.get(3).x -= 50;
					block.points.get(3).y += 50;
					block.points.get(2).x -= 50;
					block.points.get(2).y -= 50;
					block.points.get(0).x += 50;
					block.points.get(0).y += 50;
				}
				else if(block.getDirection() == 3) {
					block.points.get(3).x -= 50;
					block.points.get(3).y -= 50;
					block.points.get(2).x += 50;
					block.points.get(2).y -= 50;
					block.points.get(0).x -= 50;
					block.points.get(0).y += 50;
					zeroAgain = true;
				}
				else if(block.getDirection() == 0 || zeroAgain) {
					block.points.get(3).x += 50;
					block.points.get(3).y -= 50;
					block.points.get(2).x += 50;
					block.points.get(2).y += 50;
					block.points.get(0).x -= 50;
					block.points.get(0).y -= 50;
				}
			}					
		}
		
		else if(c == KeyEvent.VK_DOWN) {
			boolean boundary = false;
			for(int i=0; i<blocks.get(blocks.size() - 1).points.size(); i++) {
				if(blocks.get(blocks.size() - 1).points.get(i).y >= 550) {
					boundary = true;
				}
			}
			if(!boundary) {
				for (Point point : blocks.get(blocks.size() - 1).points) {
					point.y += 50;
				}
			}
		}
		
		else if(c == KeyEvent.VK_SPACE) {
			pause = !pause;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {	
	}

}
