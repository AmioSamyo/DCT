package DCT.entity;

import DCT.Facade;
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
		this.MoveY();
		this.MoveX();
	}

	protected void MoveY() {
		if (this.yMove > 0) {
			this.position.setY(this.position.getY() + yMove);
		} else if (this.yMove < 0) {
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

}