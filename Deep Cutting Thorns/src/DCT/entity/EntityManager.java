package DCT.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import DCT.Facade;

public class EntityManager {

	private Facade facade;
	private Player player;

	private ArrayList<Entity> entityList;

	private Comparator<Entity> ruleSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if (a.getPositionY() + a.getPositionHeight() < b.getPositionY() + b.getPositionHeight()) {
				return -1;
			}
			return 1;
		}
	};

	public EntityManager(Facade facade, Player player) {

		this.facade = facade;
		this.player = player;

		this.entityList = new ArrayList<Entity>();
		this.addEntity(this.player);
	}

	public void update() {

		this.entityList.forEach(Entity -> Entity.update());

		this.entityList.sort(ruleSorter);
	}

	public void render(Graphics g) {

		this.entityList.forEach(Entity -> Entity.render(g));
	}

	public void addEntity(Entity e) {

		this.entityList.add(e);
	}

	public Player getPlayer() {
		return this.player;
	}

	public ArrayList<Entity> getEntityList() {
		return this.entityList;
	}

}
