package Test.gfx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DCT.gfx.Display;

class DisplayTest {

	@Test
	void testCreateDisplay() {
		Display d = new Display("", 0, 0);
		
		assertTrue(d.getJFrame() != null);
		assertTrue(d.getCanvas() != null);
	}

}
