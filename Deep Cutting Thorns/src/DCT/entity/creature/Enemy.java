package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.utility.Rectangle;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;
	
	public Enemy(Facade facade, Rectangle position) {
		super(facade, position);
		
	}

	@Override
	public void update() {
		
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

		int A = (int) Math.sqrt(Y * Y + X * X);

		if (A < this.DiameterAggro / 2) {
			this.playerInAggro = true;
		} else {
			this.playerInAggro = false;
		}

	}

	protected void moveToPlayer() {
		if (this.playerInAggro) {

			int x = this.facade.getEntityManager().getPlayer().getPositionX()
					+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2;
			int y = this.facade.getEntityManager().getPlayer().getPositionY()
					+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2;

			int x1 = this.getPositionX() - this.getPositionWidth() / 2;
			int y1 = this.getPositionY() - this.getPositionHeight() / 2;

			int A = x - x1;
			int B = y - y1;

			if (A < 0) {
				this.xMove = -this.speed;
			} else {
				if (A > 0) {

					this.xMove = this.speed;
				}
			}
			if (B < 0) {
				this.yMove = -this.speed;
			} else {
				if (B > 0) {
					this.yMove = this.speed;
				}
			}
			
			
			this.move();
			
			this.resetMovement();
		}
	}
	

}
