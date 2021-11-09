package DCT.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import DCT.Facade;

public class EntityManager {

	private Facade facade;
	private Player player;
	private ArrayList<Entity> entityList;

	public EntityManager(Facade facade, Player player) {
		
		this.facade=facade;
		this.player=player;
		
		this.entityList=new ArrayList<Entity>();
	}
	
	public void update() {
		this.player.update();
	}
	
	public void render(Graphics g) {
		this.player.render(g);
	}
	
	
	public void addEntity(Entity e) {
		
		this.entityList.add(e);
	}

}
