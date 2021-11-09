package DCT.entity;

import DCT.Facade;

public class EntityManager {

	private Facade facade;
	private Player player;

	public EntityManager(Facade facade, Player player) {
		
		this.facade=facade;
		this.player=player;
	}

}
