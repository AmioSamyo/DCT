package DCT;

import DCT.entity.EntityManager;
import DCT.game.Game;
import DCT.game.World;
import DCT.gfx.Display;
import DCT.gfx.GameCamera;
import DCT.input.KeyManager;
import DCT.input.MouseManager;
import DCT.state.State;

public class Facade {

	private Game game;

	public Facade(Game game) {
		this.game = game;
	}

	public KeyManager getKeyManager() {
		return this.game.getKeyManager();
	}

	public MouseManager getMouseManager() {
		return this.game.getMouseManager();
	}

	public Display getDiplay() {
		return this.game.getDisplay();
	}

	public State getCurrentState() {
		return State.getCurrentState();
	}

	public EntityManager getEntityManager() {
		return this.getCurrentState().getEntityManager();
	}

	public World getWorld() {
		return State.getCurrentState().getWorld();
	}

	public GameCamera getGameCamera() {
		return this.game.getGameCamera();
	}

	public int getWidth() {
		return this.game.getWidth();
	}

	public int getHeight() {
		return this.game.getHeight();
	}

}
