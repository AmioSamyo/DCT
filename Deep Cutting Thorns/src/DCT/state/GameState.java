package DCT.state;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.entity.EntityManager;
import DCT.entity.creature.Bat;
import DCT.entity.creature.player.Player;
import DCT.entity.statics.Jar;
import DCT.entity.statics.Rocks;
import DCT.entity.statics.Tree;
import DCT.game.World;
import DCT.gfx.tools.FontLoader;
import DCT.gfx.tools.Text;
import DCT.utility.TextOption;
import DCT.utility.Vector;

public class GameState extends State {

	private World world;
	private Facade facade;

	public GameState(String worldPath, Facade facade) {
		this.facade = facade;
		this.world = new World(worldPath, this.facade);

		this.entityManager = new EntityManager(new Player(this.facade, new Vector(200, 200)));

		for (int j = 0; j < 5; j++) {
			for (int i = 2; i < 12; i++) {
				this.entityManager.addEntity(new Tree(this.facade, i * 200, 300 * j));
			}
		}

		this.entityManager.addEntity(new Jar(this.facade, 140, 280));
		this.entityManager.addEntity(new Rocks(this.facade, 180, 420));

		this.entityManager.addEntity(new Bat(this.facade, new Vector(64 * 4, 64 * 3)));
		/*
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(400, 150)));
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(500, 150)));
		 * 
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(200, 230)));
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(400, 260)));
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(500, 280)));
		 * 
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(600, 150)));
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(700, 150)));
		 * this.entityManager.addEntity(new Bat(this.facade, new Vector(800, 150)));
		 */

	}

	@Override
	public void update() {

		this.world.update();

		this.entityManager.update();
	}

	@Override
	public void render(Graphics2D g) {
		this.world.render(g);

		this.entityManager.render(g);

	}

	public World getWorld() {
		return this.world;
	}

}
