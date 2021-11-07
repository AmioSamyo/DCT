package DCT.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import DCT.Facade;

public class KeyManager implements KeyListener {

	private boolean up, down, left, right;
	private boolean[] keys, justPressed, cantPress;

	private Facade facade;

	public KeyManager(Facade facade) {

		this.keys = new boolean[256];
		this.justPressed = new boolean[keys.length];
		this.cantPress = new boolean[keys.length];

		this.facade = facade;

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

		up = this.keys[KeyEvent.VK_W];

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
	
	public boolean getUp() {
		return this.up;
	}
}
