package DCT;

public class Game {

	private boolean running;
	private Display display;
	private Thread thread;

	public Game(String title, int width, int height) {
		this.display = new Display(title, width, height);
		this.running = false;
	}

	public void initialize() {
		// TODO
	}

	public void update() {
		// TODO
	}

	public void render() {
		// TODO
	}

	public void run() {
		
		int fps;
		int nowTime;
		int lastTime;
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
