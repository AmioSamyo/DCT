package DCT.entity;

import java.awt.Graphics;

import DCT.Facade;
import DCT.utility.Rectangle;

public abstract class Entity {
	
	protected Facade facade;
	protected Rectangle position;
	
	public Entity(Facade facade, Rectangle position) {
		this.facade = facade;
		this.position = position;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
}
