package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.tile.Tile;
import DCT.utility.Rectangle;

public abstract class Creature extends Entity {

	protected int xMove, yMove;
	protected int DiameterAggro;
	protected int speed = 3;
	protected boolean playerInAggro = false;

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
		int xGrid = x / Tile.TILEWIDTH;
		int yGrid = y / Tile.TILEHEIGHT;

		if (xGrid < 0 || xGrid >= this.facade.getWorld().getColumns() || yGrid < 0
				|| yGrid >= this.facade.getWorld().getRows()) {
			return false;
		}
		return Tile.tiles[this.facade.getWorld().getTiles()[xGrid][yGrid]].isSolid();
	}

	protected void drawRangeAggro(Graphics g) {
		if (this.facade.getDebugMode()) {
			Rectangle StartBatEye = new Rectangle(
					(int) (this.position.getX() - this.DiameterAggro / 2 + this.getPositionWidth() / 2),
					(int) (this.position.getY() - this.DiameterAggro / 2 + this.getPositionHeight() / 2), 0, 0);
			if (this.playerInAggro) {
				g.setColor(Color.RED);
			}

			g.drawOval(this.getXMoveHitbox(StartBatEye), this.getYMoveHitbox(StartBatEye), this.DiameterAggro,
					this.DiameterAggro);
		}
	}

	protected void playerInAggro() {
		int x = this.facade.getEntityManager().getPlayer().getPositionX()
				+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2;
		int y = this.facade.getEntityManager().getPlayer().getPositionY()
				+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2;

		int x1 = this.getPositionX() - this.getPositionWidth() / 2;
		int y1 = this.getPositionY() - this.getPositionHeight() / 2;

		int X = Math.abs(x - x1);
		int Y = Math.abs(y - y1);

		int A=(int) Math.sqrt(Y*Y+X*X);

		if (A<this.DiameterAggro/2) {
			this.playerInAggro = true;
		} else {
			this.playerInAggro = false;
		}

	}

	protected void MoveToPlayer() {
		if (this.playerInAggro) {

		}

	}

}
