package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.utility.Rectangle;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;

	protected int xStart, yStart;
	protected int rangeOfAttack;
	protected boolean getStart = true;

	public Enemy(Facade facade, Rectangle position) {
		super(facade, position);

		this.xStart = this.getPositionX() - this.getPositionWidth() / 2;
		this.yStart = this.getPositionY() - this.getPositionHeight() / 2;

	}

	@Override
	public void update() {
		if (this.health > 0) {
			this.playerInAggro();
			this.move();
			this.attack();
			this.resetMovement();
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		this.drawRangeAggro(g);
	}

	@Override
	protected void move() {
		this.moveIfNotAggro();
		this.moveToPlayer();
		super.move();
		this.chooseCurrentAnimation();
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
			watcherMove();
		}

	}

	protected void watcherMove() {
		if (this.getStart) {
			int xCurrent = this.getPositionX() + this.getPositionWidth() / 2;
			int yCurrent = this.getPositionY() + this.getPositionHeight() / 2;
			int xDir, yDir;
			xDir = (int) (Math.random() * 2);
			yDir = (int) (Math.random() * 2);
			int deltaX = this.xStart - xCurrent;
			int deltaY = this.yStart - yCurrent;
			if (xCurrent < 0 || xCurrent > this.facade.getWidth() || yCurrent < 0
					|| yCurrent > this.facade.getHeight()) {
				this.getStart = false;
				this.getStart();
				return;
			}
			if (Math.abs(deltaX) > this.speed * 20 || Math.abs(deltaY) > this.speed * 20) {
				this.getStart = false;
				this.getStart();
				return;
			}
			if (xDir == 0) {
				this.xMove = 0;
			}
			if (xDir == 1) {
				this.xMove = this.speed;
			}
			if (xDir == 2) {
				this.xMove = -this.speed;
			}
			if (yDir == 0) {
				this.yMove = 0;
			}
			if (yDir == 1) {
				this.yMove = this.speed;
			}
			if (yDir == 2) {
				this.yMove = -this.speed;
			}

		}
	}

	protected void getStart() {
		int deltaX = this.xStart - this.getPositionX() - this.getPositionWidth() / 2;
		int deltaY = this.yStart - this.getPositionY() - this.getPositionHeight() / 2;

		if (Math.abs(deltaX) < this.speed) {
			this.xMove = 0;
			this.getStart = true;
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
		Rectangle playerPosition=new Rectangle(this.facade.getEntityManager().getPlayer().getPositionX(),
				this.facade.getEntityManager().getPlayer().getPositionY(),
				this.facade.getEntityManager().getPlayer().getPositionWidth(),
				this.facade.getEntityManager().getPlayer().getPositionHeight());
				
		boolean intersect=this.position.intersects(playerPosition);
		
		if(intersect) {
			this.facade.getEntityManager().getPlayer().addHealth(-1);
		}
	}
}
