package DCT.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import DCT.entity.creature.player.Player;

public class EntityManager {

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

	public EntityManager(Player player) {
		this.player = player;

		this.entityList = new ArrayList<Entity>();
		this.addEntity(this.player);
	}

	public void update() {

		Iterator<Entity> it = this.entityList.iterator();
		while (it.hasNext()) {
			Entity e = it.next();
			e.update();
			if (!e.isAlive())
				it.remove();
		}

		this.sort();
	}

	public void render(Graphics2D g) {

		this.entityList.forEach(e -> e.render(g));
		this.player.showHealthBar(g);
	}

	public void sort() {
		this.entityList.sort(ruleSorter);
	}

	public void addEntity(Entity e) {

		this.entityList.add(e);
	}

	public void removeEntity(Entity e) {
		this.entityList.remove(e);
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public void setEntityList(ArrayList<Entity> list) {
		this.entityList = list;
	}

	public ArrayList<Entity> getEntityList() {
		return this.entityList;
	}

}
