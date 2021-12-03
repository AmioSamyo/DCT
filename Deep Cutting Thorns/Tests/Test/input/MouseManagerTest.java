package Test.input;

import java.awt.Button;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.input.MouseManager;

class MouseManagerTest {
	
	@Mock
	MouseManager mouse = new MouseManager();

	@DisplayName("Mouse click should be handled")
	@Test
	void testMouseClicked() {
		Button c = new Button();
		MouseEvent e = new MouseEvent(c, MouseEvent.MOUSE_CLICKED, 5, 0, 0, 0, 1, false, MouseEvent.BUTTON1);
		
		mouse.mouseClicked(e);
		
		assertTrue(mouse.getLeftClicked());
	}

	@DisplayName("Mouse pressed should be handled")
	@Test
	void testMousePressed() {
		Button c = new Button();
		MouseEvent e = new MouseEvent(c, MouseEvent.MOUSE_PRESSED, 5, 0, 0, 0, 1, false, MouseEvent.BUTTON1);
		
		mouse.mousePressed(e);
		assertTrue(mouse.getLeftPressed());
	}

	@DisplayName("Mouse released should be handled")
	@Test
	void testMouseReleased() {
		Button c = new Button();
		MouseEvent ePressed = new MouseEvent(c, MouseEvent.MOUSE_PRESSED, 5, 0, 0, 0, 1, false, MouseEvent.BUTTON1);
		MouseEvent eReleased = new MouseEvent(c, MouseEvent.MOUSE_RELEASED, 5, 0, 0, 0, 1, false, MouseEvent.BUTTON1);

		mouse.mousePressed(ePressed);
		mouse.mouseReleased(eReleased);
		
		assertFalse(mouse.getLeftPressed());

	}

}
