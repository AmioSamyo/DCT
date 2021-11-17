package Test.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import DCT.Facade;
import DCT.entity.creature.Player;
import DCT.utility.Vector;

class PlayerTest {
	
	@Mock
	Facade mockedFacade;
	
	@Test
	void testPlayer() {
		Player p = new Player(mockedFacade, new Vector());
		
		assertTrue(p.getPositionX() == 0);
		assertTrue(p.getPositionY() == 0);
	}

}
