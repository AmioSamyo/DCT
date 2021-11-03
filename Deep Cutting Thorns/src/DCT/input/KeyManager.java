package DCT.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean keys[];
	private boolean justPressed[];
	private boolean cantPress[];

	public KeyManager() {

		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];

	}

	public void update() {

		for (int i = 0; i < keys.length; i++) {

			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if (!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) 
			return;
		this.keys[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		this.keys[e.getKeyCode()] = false;
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
