package Test.input;

import java.awt.event.KeyEvent;
import java.awt.Button;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.input.KeyManager;

class KeyManagerTest {

	@Mock
	KeyManager key = new KeyManager();

	@Test
	void testKeyPressed() {
		Button c = new Button();
		@SuppressWarnings("deprecation")
		KeyEvent e = new KeyEvent(c, KeyEvent.KEY_PRESSED, 5, 0, KeyEvent.VK_W);

		key.keyPressed(e);
		key.update();

		assertTrue(key.getUp());
	}

	@Test
	void testKeyReleased() {
		Button c = new Button();
		@SuppressWarnings("deprecation")
		KeyEvent ePressed = new KeyEvent(c, KeyEvent.KEY_PRESSED, 5, 0, KeyEvent.VK_W);
		@SuppressWarnings("deprecation")
		KeyEvent eReleased = new KeyEvent(c, KeyEvent.KEY_RELEASED, 5, 0, KeyEvent.VK_W);

		key.keyPressed(ePressed);
		key.update();
		key.keyReleased(eReleased);
		key.update();

		assertFalse(key.getUp());
	}

	@Test
	void testKeyJustPressed() {
		Button c = new Button();
		@SuppressWarnings("deprecation")
		KeyEvent e = new KeyEvent(c, KeyEvent.KEY_PRESSED, 5, 0, KeyEvent.VK_W);

		key.keyPressed(e);
		key.update();
		
		assertTrue(key.keyJustPressed(e.getKeyCode()));
	}

}
