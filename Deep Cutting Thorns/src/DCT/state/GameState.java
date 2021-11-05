package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.game.World;

public class GameState extends State {
	
	private World world;
	private Facade facade;

	public GameState(String worldPath, Facade facade) {
		this.world = new World(worldPath);
		this.facade = facade;
	}

	@Override
	public void update() {
		this.world.update();
	}

	@Override
	public void render(Graphics g) {
		this.world.render(g);
	}
	
	public World getWorld() {
		return this.world;
	}

}
