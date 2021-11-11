package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.EntityManager;
import DCT.entity.Player;
import DCT.entity.statics.Tree;
import DCT.game.World;

public class GameState extends State {

	private World world;
	private Facade facade;

	public GameState(String worldPath, Facade facade) {
		this.facade = facade;
		this.world = new World(worldPath, this.facade);

		this.entityManager = new EntityManager(this.facade, new Player(this.facade, 200, 200));
		entityManager.addEntity(new Tree(this.facade, 400, 200));
	}

	@Override
	public void update() {
		this.world.update();

		this.entityManager.update();
	}

	@Override
	public void render(Graphics g) {
		this.world.render(g);

		this.entityManager.render(g);
	}

	public World getWorld() {
		return this.world;
	}

}
