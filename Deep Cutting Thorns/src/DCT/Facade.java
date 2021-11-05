package DCT;

import DCT.game.Game;
import DCT.game.World;
import DCT.gfx.Display;
import DCT.input.KeyManager;
import DCT.state.GameState;
import DCT.state.State;

public class Facade {

	private Game game;

	public Facade(Game game) {
		this.game = game;
	}

	public KeyManager getKeyManager() {
		return this.game.getKeyManager();
	}

	/*
	 * public MouseManager getMouseManager() { return this.game.getMouseManager(); }
	 */

	public Display getDiplay() {
		return this.game.getDisplay();
	}

	public State getCurrentState() {
		return State.getCurrentState();
	}

	public World getWorld() {
		GameState example = new GameState("", this);
		GameState current = (this.getCurrentState().getClass().getName() == example.getClass().getName())
				? (GameState) this.getCurrentState()
				: null;

		if (current == null) {
			return null;
		}
		return current.getWorld();
	}

	public int getWidth() {
		return this.game.getWidth();
	}

	public int getHeight() {
		return this.game.getHeight();
	}

}
