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
	
	public int getPositionX() {
		return this.position.getX();
	}
	
	public int getPositionY() {
		return this.position.getY();
	}
	
	public int getPositionWidth() {
		return this.position.getWidth();
	}
	
	public int getPositionHeight() {
		return this.position.getHeight();
	}
	
	public void setX(int positionX) {
		this.position.setX(positionX);
	}
	
	public void setY(int positionY) {
		this.position.setY(positionY);
	}
}
