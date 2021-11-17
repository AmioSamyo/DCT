package Test.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import DCT.Facade;
import DCT.entity.EntityManager;
import DCT.entity.creature.Player;
import DCT.game.Game;

class EntityManagerTest {
	
	Game mockedGame = new Game("", 100, 100);
	Facade mockedFacade = new Facade(mockedGame);
	Player player1 = new Player(mockedFacade, 0, 0);
	Player player2 = new Player(mockedFacade, 10, 10);

	@Test
	void testUpdate() {
		EntityManager em = new EntityManager(player2);
		em.addEntity(player1);
		em.update();
	}

}
