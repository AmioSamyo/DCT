package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.utility.Rectangle;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;

	protected int xStart, yStart;
	protected boolean getStart = true;

	public Enemy(Facade facade, Rectangle position) {
		super(facade, position);

		this.xStart = this.getPositionX() - this.getPositionWidth() / 2;
		this.yStart = this.getPositionY() - this.getPositionHeight() / 2;

	}

	@Override
	public void update() {

		this.playerInAggro();
		this.moveIfNotAggro();
		this.moveToPlayer();
		this.move();

		this.resetMovement();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		this.drawRangeAggro(g);
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

		int deltaX = Math.abs(this.facade.getEntityManager().getPlayer().getPositionX()
				+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2 - this.getPositionX()
				- this.getPositionWidth() / 2);
		int deltaY = Math.abs(this.facade.getEntityManager().getPlayer().getPositionY()
				+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2 - this.getPositionY()
				- this.getPositionHeight() / 2);

		int distanceToPlayer = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

		if (distanceToPlayer < this.DiameterAggro / 2) {
			this.playerInAggro = true;
		} else {
			this.playerInAggro = false;
		}

	}

	protected void moveToPlayer() {
		if (this.playerInAggro) {

			int deltaX = this.facade.getEntityManager().getPlayer().getPositionX()
					+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2 - this.getPositionX()
					- this.getPositionWidth() / 2;
			;
			int deltaY = this.facade.getEntityManager().getPlayer().getPositionY()
					+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2 - this.getPositionY()
					- this.getPositionHeight() / 2;

			if (Math.abs(deltaX) < this.speed) {
				this.xMove = 0;
			} else {
				if (deltaX < 0) {
					this.xMove = -this.speed;
				}
				if (deltaX > 0) {
					this.xMove = this.speed;
				}
			}
			if (Math.abs(deltaY) < this.speed) {
				this.yMove = 0;
			} else {
				if (deltaY < 0) {
					this.yMove = -this.speed;
				}
				if (deltaY > 0) {
					this.yMove = this.speed;
				}
			}
		}
	}

	protected void moveIfNotAggro() {
		if (!this.playerInAggro) {
			getStart();
		}
	}

	protected void getStart() {
		int deltaX = this.xStart - this.getPositionX() - this.getPositionWidth() / 2;
		int deltaY = this.yStart - this.getPositionY() - this.getPositionHeight() / 2;

		if (Math.abs(deltaX) < this.speed) {
			this.xMove = 0;
			getStart = true;
		} else {
			if (deltaX < 0) {
				this.xMove = -this.speed;
			}
			if (deltaX > 0) {
				this.xMove = this.speed;
			}
		}
		if (Math.abs(deltaY) < this.speed) {
			this.yMove = 0;
		} else {
			if (deltaY < 0) {
				this.yMove = -this.speed;
			}
			if (deltaY > 0) {
				this.yMove = this.speed;
			}
		}
	}
}
