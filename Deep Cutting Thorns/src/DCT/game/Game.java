package DCT.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.gfx.Display;
import DCT.gfx.GameCamera;
import DCT.input.KeyManager;
import DCT.input.MouseManager;
import DCT.state.GameState;
import DCT.state.PauseState;
import DCT.state.State;

public class Game implements Runnable {

	private boolean running, pausing;
	private int width;
	private int height;

	private String worldPath = "rsc\\worldTest";
	private String title;
	private Thread thread;
	private Graphics g;
	private BufferStrategy bufferStrategy;

	private Display display = null;
	private State gameState, pauseState;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private Facade facade = null;
	private GameCamera gameCamera = null;

	public Game(String title, int width, int height) {
		this.running = false;
		this.pausing = false;
		this.width = width;
		this.height = height;
		this.title = title;

		this.mouseManager = new MouseManager();
		this.keyManager = new KeyManager();
	}

	public void initialize() {
		initializeDisplay();

		initializeGameComponents();

		Assets.assetInitialize();

		initializeStates();

	}

	public void initializeGameComponents() {
		this.facade = new Facade(this);
		this.gameCamera = new GameCamera(this.facade);
	}

	public void initializeStates() {
		this.gameState = new GameState(this.worldPath, this.facade);
		this.pauseState = null;
		State.setCurrentState(gameState);
	}

	public void initializeDisplay() {
		this.display = new Display(this.title, this.width, this.height);
		this.display.getJFrame().addKeyListener(this.keyManager);

		this.display.getJFrame().addMouseListener(this.mouseManager);
		this.display.getJFrame().addMouseMotionListener(this.mouseManager);

		this.display.getCanvas().addMouseListener(this.mouseManager);
		this.display.getCanvas().addMouseMotionListener(this.mouseManager);
	}

	public void update() {

		this.keyManager.update();

		if (this.facade.pauseGame()) {
			if (this.pauseState == null) {
				this.pauseState = new PauseState(this.facade);
			}
			State.setCurrentState(this.pauseState);
		} else {
			State.setCurrentState(this.gameState);
			this.pauseState = null;
		}

		if (State.getCurrentState() != null) {
			State.getCurrentState().update();
		}
	}

	public void render() {

		this.bufferStrategy = this.display.getCanvas().getBufferStrategy();

		if (this.bufferStrategy == null) {
			this.display.getCanvas().createBufferStrategy(3);
			return;
		}

		this.g = this.bufferStrategy.getDrawGraphics();
		this.g.clearRect(0, 0, this.width, this.height);

		if (State.getCurrentState() != null) {
			State.getCurrentState().render(g);
		}

		this.bufferStrategy.show();
		this.g.dispose();
	}

	public void run() {

		initialize();

		int fps = 60;
		int update = 0;
		long nowTime;
		long lastTime = System.nanoTime();
		long timer = 0;

		double updatePerSecond = 1000000000 / fps;
		double delta = 0;

		while (this.running) {

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
				//System.out.println("Ticks and Frames: " + update);
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

		this.thread = new Thread(this);
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

	public KeyManager getKeyManager() {
		return this.keyManager;
	}

	public MouseManager getMouseManager() {
		return this.mouseManager;
	}

	public Display getDisplay() {
		return this.display;
	}
	
	public Facade getFacade() {
		return this.facade;
	}

	public GameCamera getGameCamera() {
		return this.gameCamera;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean getPausing() {
		return this.pausing;
	}

	public void setPausing(boolean b) {
		this.pausing = b;
	}

	public State getGameState() {
		return this.gameState;
	}
}
