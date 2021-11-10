package DCT.entity;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.Rectangle;

public abstract class Creature extends Entity {

	protected int xMove, yMove;
	protected int speed = 3;

	public Creature(Facade facade, Rectangle position) {
		super(facade, position);
		this.xMove = 0;
		this.yMove = 0;
	}

	protected void move() {
		if (!this.checkEntityCollisions(0, this.yMove)) {
			this.MoveY();
		}

		if (!this.checkEntityCollisions(this.xMove, 0)) {
			this.MoveX();
		}
	}

	protected void MoveY() {
		if (this.yMove < 0) {
			int futureY = this.position.getY() + this.hitBox.getY() + this.yMove;

			if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY)
					&& !this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(), futureY)) {
				this.position.setY(this.position.getY() + yMove);
			}
		} else if (this.yMove > 0) {
			this.position.setY(this.position.getY() + yMove);
		}
	}

	protected void MoveX() {
		if (this.xMove > 0) {
			this.position.setX(this.position.getX() + xMove);
		} else if (this.xMove < 0) {
			this.position.setX(this.position.getX() + xMove);
		}
	}

	@Override
	public void die() {
		this.speed = 0;
	}

	public void setXMove(int speed) {
		this.xMove = speed;
	}

	public void setYMove(int speed) {
		this.yMove = speed;
	}

	public void addYMove(int speed) {
		this.setYMove(this.yMove + speed);
	}

	public void addXMove(int speed) {
		this.setXMove(this.xMove + speed);
	}

	protected void resetMovement() {
		this.setXMove(0);
		this.setYMove(0);
	}

	protected boolean checkCollisionWithTile(int x, int y) {
		return Tile.tiles[this.facade.getWorld().getTiles()[x / Tile.TILEWIDTH][y / Tile.TILEHEIGHT]].isSolid();
	}

}
