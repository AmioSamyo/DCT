package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.EntityManager;
import DCT.entity.creature.Bat;
import DCT.entity.creature.Player;
import DCT.entity.statics.Jar;
import DCT.entity.statics.Rocks;
import DCT.entity.statics.Tree;
import DCT.game.World;
import DCT.utility.Vector;

public class GameState extends State {

	private World world;
	private Facade facade;

	public GameState(String worldPath, Facade facade) {
		this.facade = facade;
		this.world = new World(worldPath, this.facade);

		this.entityManager = new EntityManager(new Player(this.facade, 200, 200));

		for (int j = 0; j < 5; j++) {
			for (int i = 2; i < 12; i++) {
				this.entityManager.addEntity(new Tree(this.facade, i * 200, 300 * j));
			}
		}

		this.entityManager.addEntity(new Jar(this.facade, 140, 280));
		this.entityManager.addEntity(new Rocks(this.facade, 180, 420));

		this.entityManager.addEntity(new Bat(this.facade, new Vector(200, 150)));
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
