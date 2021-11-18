package Test.entity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.entity.EntityManager;
import DCT.entity.creature.Player;
import DCT.utility.Vector;

@RunWith(MockitoJUnitRunner.class)
class EntityManagerTest {

	@Mock
	Facade mockedFacade;

	@Mock
	Entity playerHigher = new Player(mockedFacade, new Vector());
	@Mock
	Player playerLower = new Player(mockedFacade, new Vector());

	@Mock
	EntityManager em = new EntityManager(playerLower);

	@DisplayName("Should sort correctly the enitites")
	@Test
	void testUpdate() {
		playerLower.setY(200);
		
		em.addEntity(playerHigher);
		em.sort();
		assertEquals(em.getEntityList().get(0), playerHigher);
		assertEquals(em.getEntityList().get(1), playerLower);
	}

}
