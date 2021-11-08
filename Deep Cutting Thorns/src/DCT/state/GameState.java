package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.Player;
import DCT.game.World;

public class GameState extends State {

	private World world;
	private Facade facade;
	private Player player;

	public GameState(String worldPath, Facade facade) {
		this.facade = facade;
		this.world = new World(worldPath);
		this.player = new Player(this.facade, 200, 200);
	}

	@Override
	public void update() {
		this.world.update();
		this.player.update();
	}

	@Override
	public void render(Graphics g) {
		this.world.render(g);
		this.player.render(g);
	}

	public World getWorld() {
		return this.world;
	}

}
