package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.utility.Rectangle;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;

	protected int xStart, yStart, xTarget, yTarget;
	protected int rangeOfAttack;
	protected boolean getStart = false;

	public Enemy(Facade facade, Rectangle position) {
		super(facade, position);

		this.xStart = this.getPositionX() - this.getPositionWidth() / 2;
		this.yStart = this.getPositionY() - this.getPositionHeight() / 2;

	}

	@Override
	public void update() {
		if (this.health > 0) {
			this.move();
			this.attack();
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		this.drawRangeAggro(g);
	}

	@Override
	protected void move() {
		this.chooseTarget();
		this.moveToPoint();
		super.move();
		this.chooseCurrentAnimation();

		this.resetMovement();
	}

	protected void chooseTarget() {
		this.playerInAggro();
		this.checkIfStart();
		if (this.playerInAggro) {
			this.xTarget = this.facade.getEntityManager().getPlayer().getPositionX()
					+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2;
			this.yTarget = this.facade.getEntityManager().getPlayer().getPositionY()
					+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2;
		} else if (!this.getStart) {
			this.xTarget = this.xStart;
			this.yTarget = this.yStart;
		} else if (this.getStart) {
			this.xTarget = 300;
			this.yTarget = 300;
			this.checkEndWatch(this.xTarget, this.yTarget);
		}
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
			this.getStart = false;
		} else {
			this.playerInAggro = false;
		}

	}

	protected void checkIfStart() {
		int deltaX = Math.abs(this.xStart - this.getPositionX() - this.getPositionWidth() / 2);
		int deltaY = Math.abs(this.yStart - this.getPositionY() - this.getPositionHeight() / 2);

		int distanceToPlayer = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		if (distanceToPlayer < this.speed) {
			this.getStart = true;
		}

	}

	private void checkEndWatch(int xTarget, int yTarget) {
		int deltaX = Math.abs(xTarget - this.getPositionX() - this.getPositionWidth() / 2);
		int deltaY = Math.abs(yTarget - this.getPositionY() - this.getPositionHeight() / 2);
		int distanceToPlayer = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		if (distanceToPlayer < this.speed) {
			this.getStart = false;
		}

	}

	protected void moveToPoint() {

		int deltaX = xTarget - this.getPositionX() - this.getPositionWidth() / 2;

		int deltaY = yTarget - this.getPositionY() - this.getPositionHeight() / 2;

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

	protected void attack() {
		Rectangle playerPosition = new Rectangle(this.facade.getEntityManager().getPlayer().getPositionX(),
				this.facade.getEntityManager().getPlayer().getPositionY(),
				this.facade.getEntityManager().getPlayer().getPositionWidth(),
				this.facade.getEntityManager().getPlayer().getPositionHeight());

		boolean intersect = this.position.intersects(playerPosition);

		if (intersect) {
			this.facade.getEntityManager().getPlayer().addHealth(0);
		}
	}
}
