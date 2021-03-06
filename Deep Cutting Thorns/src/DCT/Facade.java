package DCT;

import java.awt.event.KeyEvent;

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
	private boolean debugMode;

	public Facade(Game game) {
		this.game = game;

		this.debugMode = false;
	}
	
	public Game getGame() {
		return this.game;
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

	public boolean getDebugMode() {
		return this.debugMode;
	}
	
	public void lightDebugMode() {
		this.debugMode = !this.debugMode;
	}
	
	public void setGamePause(boolean b) {
		this.game.setPausing(b);
	}

	public boolean pauseGame() {
		if (this.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			this.game.setPausing(!this.game.getPausing());
		}

		return this.game.getPausing();
	}
	
	public int getPlayerX() {
		return this.getEntityManager().getPlayer().getPositionX();
	}
	
	public int getPlayerY() {
		return this.getEntityManager().getPlayer().getPositionY();
	}
	
	public int getPlayerWidth() {
		return this.getEntityManager().getPlayer().getPositionWidth();
	}
	
	public int getPlayerHeight() {
		return this.getEntityManager().getPlayer().getPositionHeight();
	}
}
