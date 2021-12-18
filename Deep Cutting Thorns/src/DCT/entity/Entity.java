package DCT.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public abstract class Entity {

	protected int health = MAX_HEALTH;
	protected boolean alive = true;

	protected Color debuggingColor = Color.RED;
	protected Facade facade;
	protected Rectangle position;
	protected Rectangle hitBox;

	protected static final int MAX_HEALTH = 100;

	public Entity(Facade facade, Rectangle position) {
		this.facade = facade;
		this.position = position;
	}

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
	
	public void damage(int amount) {
		this.health -= amount;
	}
	
	public abstract void die();
	
	public Rectangle getCollisionHitBox(int xOffSet, int yOffSet) {
		return new Rectangle(this.getPositionX() + this.hitBox.getX() + xOffSet,
				this.getPositionY() + this.hitBox.getY() + yOffSet, this.hitBox.getWidth(), this.hitBox.getHeight());
	}
	
	public Color getDebuggingColor() {
		return this.debuggingColor;
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
	
	public int getXMoveHitbox(Rectangle hitBox) {
		return hitBox.getX() - this.facade.getGameCamera().getXOffset();
	}
	
	public int getYMoveHitbox(Rectangle hitBox) {
		return hitBox.getY() - this.facade.getGameCamera().getYOffset();
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void render(Graphics2D g) {
		this.showHealthBar(g);
		drawHitBox(g);
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
	
	public abstract void update();

	public int xMoveWithCamera() {
		return this.position.getX() - this.facade.getGameCamera().getXOffset();
	}

	public int yMoveWithCamera() {
		return this.position.getY() - this.facade.getGameCamera().getYOffset();
	}

	protected void drawHitBox(Graphics2D g) {
		if (this.facade.getDebugMode()) {
			Rectangle hitBox = this.getCollisionHitBox(0, 0);
			g.setColor(Color.WHITE);
			g.drawRect(this.xMoveWithCamera(), this.yMoveWithCamera(), this.getPositionWidth(),
					this.getPositionHeight());
			g.setColor(this.getDebuggingColor());
			g.fillRect(this.getXMoveHitbox(hitBox), this.getYMoveHitbox(hitBox), this.hitBox.getWidth(),
					this.hitBox.getHeight());
		}
	}

	protected void setDebuggingColor(Color debuggingColor) {
		this.debuggingColor = debuggingColor;
	}

	protected void showHealthBar(Graphics2D g) {
		if (this.health < MAX_HEALTH && this.health >= 0) {
			float rangeHealthBar = (float) (MAX_HEALTH - 1) / ((float) Assets.healthBars.length - 1);
			int currentHealthBarToShow = (int) ((float) (MAX_HEALTH - this.health) / rangeHealthBar);
			g.drawImage(Assets.healthBars[currentHealthBarToShow],
					this.xMoveWithCamera() + this.getPositionWidth() / 2 - 290 / 5 / 2,
					this.yMoveWithCamera() + this.hitBox.getY(), 290 / 5, 70 / 5, null);
		}
	}
}
