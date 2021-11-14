package DCT.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean up, down, left, right, sprint;
	private boolean debugMode;
	private boolean[] keys, justPressed, justPressedTimed, cantPress;

	// timer
	private long nowTime, lastTime, timer;

	public KeyManager() {

		this.keys = new boolean[256];
		this.justPressed = new boolean[keys.length];
		this.justPressedTimed = new boolean[keys.length];
		this.cantPress = new boolean[keys.length];

		this.lastTime = System.nanoTime();
		this.timer = 0;
	}

	public void update() {

		for (int i = 0; i < this.keys.length; i++) {

			if (this.cantPress[i] && !this.keys[i]) {
				this.cantPress[i] = false;
			} else if (this.justPressed[i]) {
				this.cantPress[i] = true;
				this.justPressed[i] = false;
			}
			if (!this.cantPress[i] && this.keys[i]) {
				this.justPressed[i] = true;
			}
		}

		this.up = this.keys[KeyEvent.VK_W];
		this.down = this.keys[KeyEvent.VK_S];
		this.left = this.keys[KeyEvent.VK_A];
		this.right = this.keys[KeyEvent.VK_D];
		this.sprint = this.keys[KeyEvent.VK_SHIFT];

		this.debugMode = this.keys[KeyEvent.VK_P];

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() < 0 || e.getKeyCode() >= this.keys.length) {
			return;
		}

		this.keys[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() < 0 || e.getKeyCode() >= this.keys.length) {
			return;
		}

		this.keys[e.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public boolean keyJustPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= this.keys.length) {
			return false;
		}

		return this.justPressed[keyCode];
	}

	public boolean keyJustPressedTimed(int keyCode, int milliTime) {
		if (keyCode < 0 || keyCode >= this.keys.length) {
			return false;
		}
		do {
			nowTime = System.nanoTime();
			timer += (nowTime - lastTime);
			lastTime = nowTime;
			return this.justPressedTimed[keyCode];
		} while (timer <= 1000000 * milliTime);
	}

	public boolean getUp() {
		return this.up;
	}

	public boolean getDown() {
		return this.down;
	}

	public boolean getLeft() {
		return this.left;
	}

	public boolean getRight() {
		return this.right;
	}

	public boolean getDebugMode() {
		return this.debugMode;
	}

	public boolean getSprint() {
		return this.sprint;
	}
}
