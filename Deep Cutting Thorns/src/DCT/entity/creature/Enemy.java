package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;

	protected Vector start, target;
	private long lastAttackTimer, attackCooldown = 600, attackTimer = this.attackCooldown;
	protected int rangeOfAttack;
	protected boolean getStart = false;

	public Enemy(Facade facade, Rectangle position) {
		super(facade, position);

		this.start = new Vector(this.getPositionX() + this.getPositionWidth() / 2,
				this.getPositionY() + this.getPositionHeight() / 2);
		this.target = new Vector();

	}

	@Override
	public void update() {
		if (this.health > 0) {

			this.move();
			this.attack();
		} else {
			this.die();
		}
	}

	@Override
	public void render(Graphics2D g) {

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
			this.target.setX(this.facade.getEntityManager().getPlayer().getPositionX()
					+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2);
			this.target.setY(this.facade.getEntityManager().getPlayer().getPositionY()
					+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2);
		} else if (!this.getStart) {
			this.target.setX(this.start.getX());
			this.target.setY(this.start.getY());
		} else if (this.getStart) {
			this.target.setX(300);
			this.target.setY(300);
			this.checkEndWatch();
		}
	}

	protected void drawRangeAggro(Graphics2D g) {
		if (this.facade.getDebugMode()) {
			Rectangle StartBatEye = new Rectangle(
					(int) (this.position.getX() - this.DiameterAggro / 2 + this.getPositionWidth() / 2),
					(int) (this.position.getY() - this.DiameterAggro / 2 + this.getPositionHeight() / 2), 0, 0);
			if (this.playerInAggro) {
				g.setColor(new Color(255, 0, 0, 100));
			}

			g.fillOval(this.getXMoveHitbox(StartBatEye), this.getYMoveHitbox(StartBatEye), this.DiameterAggro,
					this.DiameterAggro);
		}
	}

	protected void playerInAggro() {

		Vector delta = new Vector();

		delta.setX(Math.abs(this.facade.getEntityManager().getPlayer().getPositionX()
				+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2 - this.getPositionX()
				- this.getPositionWidth() / 2));
		delta.setY(Math.abs(this.facade.getEntityManager().getPlayer().getPositionY()
				+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2 - this.getPositionY()
				- this.getPositionHeight() / 2));

		int distanceToPlayer = (int) Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));

		if (distanceToPlayer < this.DiameterAggro / 2) {
			this.playerInAggro = true;
			this.getStart = false;
		} else {
			this.playerInAggro = false;
		}

	}

	protected void checkIfStart() {

		Vector delta = new Vector();

		delta.setX(Math.abs(this.start.getX() - this.getPositionX() - this.getPositionWidth() / 2));
		delta.setY(Math.abs(this.start.getY() - this.getPositionY() - this.getPositionHeight() / 2));

		int distanceToPlayer = (int) Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));

		if (distanceToPlayer < this.speed) {
			this.getStart = true;
		}

	}

	private void checkEndWatch() {

		Vector delta = new Vector();

		delta.setX(Math.abs(this.target.getX() - this.getPositionX() - this.getPositionWidth() / 2));
		delta.setY(Math.abs(this.target.getY() - this.getPositionY() - this.getPositionHeight() / 2));
		int distanceToPlayer = (int) Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));
		if (distanceToPlayer < this.speed) {
			this.getStart = false;
		}

	}

	protected void moveToPoint() {

		Vector delta = new Vector();

		delta.setX(this.target.getX() - this.getPositionX() - this.getPositionWidth() / 2);

		delta.setY(this.target.getY() - this.getPositionY() - this.getPositionHeight() / 2);

		if (Math.abs(delta.getX()) < this.speed) {
			this.xMove = 0;
		} else {
			if (delta.getX() < 0) {
				this.xMove = -this.speed;
			}
			if (delta.getX() > 0) {
				this.xMove = this.speed;
			}
		}
		if (Math.abs(delta.getY()) < this.speed) {
			this.yMove = 0;
		} else {
			if (delta.getY() < 0) {
				this.yMove = -this.speed;
			}
			if (delta.getY() > 0) {
				this.yMove = this.speed;
			}
		}
	}

	protected void attack() {
		this.attackTimer += System.currentTimeMillis() - this.lastAttackTimer;
		this.lastAttackTimer = System.currentTimeMillis();
		if (this.attackTimer >= this.attackCooldown) {
			Rectangle playerPosition = new Rectangle(this.facade.getEntityManager().getPlayer().getPositionX(),
					this.facade.getEntityManager().getPlayer().getPositionY(),
					this.facade.getEntityManager().getPlayer().getPositionWidth(),
					this.facade.getEntityManager().getPlayer().getPositionHeight());

			boolean intersect = this.position.intersects(playerPosition);

			if (intersect) {
				this.facade.getEntityManager().getPlayer().addHealth(-1);
				this.attackTimer = 0;
			}
		}
	}
}
