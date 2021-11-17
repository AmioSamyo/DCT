package Test.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import DCT.Facade;
import DCT.entity.EntityManager;
import DCT.entity.creature.Player;
import DCT.game.Game;

class EntityManagerTest {
	
	Game mockedGame = new Game("", 100, 100);
	Facade mockedFacade = new Facade(mockedGame);
	Player player2 = new Player(mockedFacade, 100, 100);

	@Test
	void testUpdate() {
		Player player1 = Mockito.mock(Player.class);
		Player player2 = Mockito.mock(Player.class);
		EntityManager em = new EntityManager(player2);
		em.addEntity(player1);
		em.update();
		assertEquals(em.getEntityList().get(0), player1);
		assertEquals(em.getEntityList().get(1), player2);
	}

}
