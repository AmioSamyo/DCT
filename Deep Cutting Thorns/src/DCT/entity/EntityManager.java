package DCT.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import DCT.Facade;

public class EntityManager {

	private Facade facade;
	private Player player;
	
	private ArrayList<Entity> entityList;

	public EntityManager(Facade facade, Player player) {

		this.facade = facade;
		this.player = player;

		this.entityList = new ArrayList<Entity>();
		this.addEntity(this.player);
	}

	public void update() {

		for (int i = 0; i < this.entityList.size(); i++) {
			this.entityList.get(i).update();
		}
	}

	public void render(Graphics g) {

		for (int i = 0; i < this.entityList.size(); i++) {
			this.entityList.get(i).render(g);
		}
	}

	public void addEntity(Entity e) {

		this.entityList.add(e);
	}

}
