package DCT.state;

import java.awt.Graphics;

import DCT.game.World;

public class GameState extends State {
	
	private World world;

	public GameState(String worldPath) {
		this.world = new World(worldPath);
	}

	@Override
	public void update() {
		this.world.update();
	}

	@Override
	public void render(Graphics g) {
		this.world.render(g);
	}

}
