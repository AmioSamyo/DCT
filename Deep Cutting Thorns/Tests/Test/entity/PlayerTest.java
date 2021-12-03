package Test.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.Facade;
import DCT.entity.creature.player.Player;
import DCT.utility.Vector;

class PlayerTest {
	
	@Mock
	Facade mockedFacade;
	
	@DisplayName("Should create correctly the player")
	@Test
	void testPlayer() {
		Player p = new Player(mockedFacade, new Vector());
		
		assertTrue(p.getPositionX() == 0);
		assertTrue(p.getPositionY() == 0);
	}

}
