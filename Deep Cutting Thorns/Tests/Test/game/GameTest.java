package Test.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.game.Game;
import DCT.state.State;

class GameTest {

	@Mock
	Game g = new Game("", 0, 0);

	@DisplayName("Should initialize correctly the display")
	@Test
	void testInitializeDisplay() {
		g.initializeDisplay();

		assertTrue(g.getDisplay() != null);
	}

	@DisplayName("Should initialize correctly the facade and the gamecamera")
	@Test
	void testInitializeGameComponents() {
		g.initializeGameComponents();

		assertTrue(g.getFacade() != null);
		assertTrue(g.getGameCamera() != null);
	}

	@DisplayName("Should initialize correctly the state")
	@Test
	void testInitializeStates() {
		g.initializeGameComponents();
		g.initializeStates();

		assertTrue(State.getCurrentState() != null);
	}

}
