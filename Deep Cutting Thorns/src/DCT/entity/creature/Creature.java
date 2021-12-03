package DCT.entity.creature;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.gfx.Animation;
import DCT.tile.Tile;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public abstract class Creature extends Entity {
	protected Vector previousDirection;
	protected int xMove, yMove;
	protected int speed = 3;

	protected int currentHealth;

	protected Animation animationMoveDown, animationMoveRight, animationMoveUp, animationMoveLeft;
	protected Animation animationIdle, animationDead;
	protected Animation currentAnimation;

	public Creature(Facade facade, Rectangle position) {
		super(facade, position);
		this.xMove = 0;
		this.yMove = 0;
	}

	protected void chooseCurrentAnimation() {
		if (this.isMovingLeft()) {
			this.currentAnimation = this.animationMoveLeft;
		} else if (this.isMovingRight()) {
			this.currentAnimation = this.animationMoveRight;
		} else if (this.isMovingUp()) {
			this.currentAnimation = this.animationMoveUp;
		} else if (this.isMovingDown()) {
			this.currentAnimation = this.animationMoveDown;
		} else if (this.isNotMoving()) {
			this.currentAnimation = this.animationIdle;
		}
	}

	protected void move() {
		if (!this.checkEntityCollisions(0, this.yMove)) {
			this.MoveY();
		}

		if (!this.checkEntityCollisions(this.xMove, 0)) {
			this.MoveX();
		}
		this.previousDirection = new Vector(this.xMove, this.yMove);
		this.previousDirection.normalize();
	}

	protected void MoveY() {
		if (this.yMove < 0) {
			int futureY = this.position.getY() + this.hitBox.getY() + this.yMove;

			if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY)
					&& !this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(),
							futureY)) {
				this.position.setY(this.position.getY() + yMove);
			}
		} else if (this.yMove > 0) {
			int futureY = this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight() + this.yMove;

			if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY)
					&& !this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(),
							futureY)) {
				this.position.setY(this.position.getY() + yMove);
			}
		}
	}

	protected void MoveX() {
		if (this.xMove < 0) {
			int futureX = this.position.getX() + this.hitBox.getX() + this.xMove;

			if (!this.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY())
					&& !this.checkCollisionWithTile(futureX,
							this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight())) {
				this.position.setX(this.position.getX() + xMove);
			}
		} else if (this.xMove > 0) {
			int futureX = this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth() + this.xMove;

			if (!this.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY())
					&& !this.checkCollisionWithTile(futureX,
							this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight())) {
				this.position.setX(this.position.getX() + xMove);
			}
		}
	}

	@Override
	public void die() {
		this.alive = false;
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
		int xGrid = x / Tile.TILEWIDTH;
		int yGrid = y / Tile.TILEHEIGHT;

		if (xGrid < 0 || xGrid >= this.facade.getWorld().getColumns() || yGrid < 0
				|| yGrid >= this.facade.getWorld().getRows()) {
			return false;
		}
		return Tile.tiles[this.facade.getWorld().getTiles()[xGrid][yGrid]].isSolid();
	}

	protected boolean isMovingUp() {
		return this.yMove < 0;
	}

	protected boolean isMovingDown() {
		return this.yMove > 0;
	}

	protected boolean isMovingLeft() {
		return this.xMove < 0;
	}

	protected boolean isMovingRight() {
		return this.xMove > 0;
	}

	protected boolean isNotMoving() {
		return this.xMove == 0 && this.yMove == 0;
	}
}
