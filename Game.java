import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	public static final int WIDTH = 950, HEIGHT = 600;
	private boolean running = false;
	private Thread thread;
	private JFrame frame;
	
	private Mechanic mechanic = new Mechanic();
	
	
	public Game() {
		Dimension size = new Dimension(Game.WIDTH, Game.HEIGHT);
		setPreferredSize(size);
		frame = new JFrame();
	}
	

	@Override
	public void run() {
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / 60;
		double delta = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();	
				fps++;
				delta--;
			}
			render();

			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle("Tetris    |    " + fps + " FPS");
				fps = 0;
			}
		}
		stop();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
		addKeyListener(mechanic);
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();	
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		mechanic.render(g);
		
		g.setColor(Color.BLACK);
		for(int i=0; i<=600; i++) {
			if(i % 50 == 0) 
				g.drawLine(i, 0, i, 600);
		}
		for(int i=0; i<=650; i++) {
			if(i % 50 == 0)
				g.drawLine(0, i, 600, i);
		}
	
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
		mechanic.update();	
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Tetris");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.setFocusable(true);
		game.frame.setVisible(true);	
		game.start();
	}
}
