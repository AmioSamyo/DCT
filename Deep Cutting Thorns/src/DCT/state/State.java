package DCT.state;

import java.awt.Graphics2D;

import DCT.entity.EntityManager;
import DCT.game.World;

public abstract class State {

	private static State currentState = null;
	
	protected World world;
	protected EntityManager entityManager;

	public abstract void update();

	public abstract void render(Graphics2D g);

	public static State getCurrentState() {
		return State.currentState;
	}

	public static void setCurrentState(State s) {
		State.currentState = s;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
