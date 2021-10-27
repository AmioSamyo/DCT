package DCT;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game {

	private boolean running;
	private int width;
	private int height;

	private String title;
	private Thread thread;

	private Graphics g;
	private BufferStrategy bufferStrategy;
	private Display display;

	public Game(String title, int width, int height) {
		this.running = false;
		this.width = width;
		this.height = height;
		this.title = title;
	}

	public void initialize() {
		this.display = new Display(this.title, this.width, this.height);
	}

	public void update() {
		// TODO
	}

	public void render() {
		this.bufferStrategy = this.display.getCanvas().getBufferStrategy();
		if (this.bufferStrategy == null) {
			this.display.getCanvas().createBufferStrategy(3);
			return;
		}
		this.g = this.bufferStrategy.getDrawGraphics();
		this.bufferStrategy.show();
		this.g.dispose();
	}

	public void run() {

		int fps = 60;
		int update = 0;
		long nowTime;
		long lastTime = System.nanoTime();
		long timer = 0;

		double updatePerSecond = 1000000000 / fps;
		double delta = 0;

		while (this.running) {

			initialize();

			nowTime = System.nanoTime();
			timer += (nowTime - lastTime);
			delta += (nowTime - lastTime) / updatePerSecond;
			lastTime = nowTime;

			if (delta >= 1) {
				update();
				render();
				update++;
				delta--;
			}

			if (timer >= 1000000000) {
				System.out.println("Ticks and Frames: " + update);
				update = 0;
				timer = 0;
			}
		}

		stop();

	}

	public synchronized void start() {
		if (this.running) {
			return;
		}

		this.thread = new Thread();
		this.running = true;

		this.thread.start();
	}

	public synchronized void stop() {
		if (!this.running) {
			return;
		}

		this.running = false;

		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
