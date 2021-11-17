package Test.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.game.Game;
import DCT.state.State;

class GameTest {

	@Mock
	Game g = new Game("", 0, 0);

	@Test
	void testInitializeDisplay() {
		g.initializeDisplay();

		assertTrue(g.getDisplay() != null);
	}

	@Test
	void testInitializeGameComponents() {
		g.initializeGameComponents();

		assertTrue(g.getFacade() != null);
		assertTrue(g.getGameCamera() != null);
	}

	@Test
	void testInitializeStates() {
		g.initializeGameComponents();
		g.initializeStates();

		assertTrue(State.getCurrentState() != null);
	}

}
