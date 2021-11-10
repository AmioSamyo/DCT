package DCT.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import DCT.Facade;
import DCT.utility.Rectangle;

public abstract class Entity {

	protected Facade facade;
	protected Rectangle position;
	protected Rectangle hitBox;

	public Entity(Facade facade, Rectangle position) {
		this.facade = facade;
		this.position = position;
	}

	public abstract void update();

	public abstract void render(Graphics g);

	public abstract void die();

	public boolean checkEntityCollisions(int xOffSet, int yOffSet) {
		ArrayList<Entity> entities = this.facade.getEntityManager().getEntityList();

		for (Entity e : entities) {
			boolean intersects = e.getCollisionHitBox(0, 0).intersects(this.getCollisionHitBox(xOffSet, yOffSet));
			if (e != this && intersects) {
				return true;
			}
		}

		return false;
	}

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

	public void setHitBox(int x, int y, int width, int height) {
		this.hitBox = new Rectangle(x, y, width, height);
	}

	public Rectangle getCollisionHitBox(int xOffSet, int yOffSet) {
		return new Rectangle(this.getPositionX() + this.hitBox.getX() + xOffSet,
				this.getPositionY() + this.hitBox.getY() + yOffSet, this.hitBox.getWidth(), this.hitBox.getHeight());
	}
}
