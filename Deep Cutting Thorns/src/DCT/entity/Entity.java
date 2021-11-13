package DCT.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public abstract class Entity {

	protected static final int MAX_HEALTH = 100;

	protected int health = MAX_HEALTH;

	protected Facade facade;
	protected Rectangle position;
	protected Rectangle hitBox;

	public Entity(Facade facade, Rectangle position) {
		this.facade = facade;
		this.position = position;
	}

	public abstract void update();

	public void render(Graphics g) {

		this.showHealthBar(g);

		if (this.facade.isDebugging()) {
			Rectangle hitBox = this.getCollisionHitBox(0, 0);
			g.setColor(Color.RED);
			g.fillRect(this.getXMoveHitbox(hitBox), this.getYMoveHitbox(hitBox), this.hitBox.getWidth(),
					this.hitBox.getHeight());
		}
	}

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

	protected void showHealthBar(Graphics g) {
		if (this.health < MAX_HEALTH && this.health >= 0) {
			float rangeHealthBar = (float) (MAX_HEALTH - 1) / ((float) Assets.healthBars.length - 1);
			int currentHealthBarToShow = (int) ((float) (MAX_HEALTH - this.health) / rangeHealthBar);
			g.drawImage(Assets.healthBars[currentHealthBarToShow],
					this.xMoveWithCamera() + this.getPositionWidth() / 2 - 290 / 5 / 2,
					this.yMoveWithCamera() + this.hitBox.getY(), 290 / 5, 70 / 5, null);
		}
		this.health = 50;
	}

	protected int xMoveWithCamera() {
		return this.position.getX() - this.facade.getGameCamera().getXOffset();
	}

	protected int yMoveWithCamera() {
		return this.position.getY() - this.facade.getGameCamera().getYOffset();
	}

	private int getXMoveHitbox(Rectangle hitBox) {
		return hitBox.getX() - this.facade.getGameCamera().getXOffset();
	}

	private int getYMoveHitbox(Rectangle hitBox) {
		return hitBox.getY() - this.facade.getGameCamera().getYOffset();
	}
}
