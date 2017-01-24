package se.ek.tetris.framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import se.ek.tetris.model.Board;
import se.ek.tetris.model.Level;
import se.ek.tetris.model.Spawn;
import se.ek.tetris.window.HUD;
import se.ek.tetris.window.Window;
/**
 * @author Eric Karlsson
 */
public final class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 4512912530380851212L;
	public static final int WIDTH = 350, HEIGHT = 430;
	public static final int SQUARE_SIZE = 20;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private Board board;
	private Spawn spawn;
	private HUD hud;
	private Level level;

	public Game() {
		
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		new Window(WIDTH, HEIGHT, "Tetris", this);
		level = new Level();
		spawn = new Spawn(handler);
	    hud = new HUD(level, spawn);
		spawn.setNextShape();
		spawn.spawnShape();
		spawn.setNextShape();
	}

	private void init() {
		
		board = new Board(handler, level);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; 

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		stop();
	}

	private void tick() {
		
		handler.tick();
		board.tick();
	}

	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);
		board.render(g);
		hud.render(g);

		bs.show();
		g.dispose();
	}

	public static int[][] cloneArray(int[][] array) {
		
		int[][] newArray = new int[4][2];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				newArray[i][j] = array[i][j];
			}
		}
		return newArray;
	}

	public static float clamp(float val, float min, float max) {
		
		if (val <= min) {
			return min;
		}
		if (val >= max) {
			return max;
		}
		return val;
	}

	public static void main(String[] args) {
		new Game();
	}

}
