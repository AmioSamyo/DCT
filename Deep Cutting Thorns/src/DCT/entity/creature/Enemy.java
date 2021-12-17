package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.AStar;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Enemy extends Creature {

	private long lastAttackTimer, attackCooldown = 600, attackTimer = this.attackCooldown;

	protected int rangeOfAttack;
	protected int diameterAggro = 300, aggro = 20 * this.speed;
	protected boolean playerInAggro = false;
	protected boolean getStart = true;
	protected boolean getPositionPath = true;
	protected boolean setWatch = true;

	protected static final int STANDARDX = 300, STANDARDY = 700;
	protected Vector start, target, targetPath;
	protected AStar aStar;
	

	public Enemy(Facade facade, Rectangle position, int speed, int diameterAggro, int scaleNodeDimension) {
		super(facade, position);

		this.start = new Vector(this.getPositionX() + this.getPositionWidth() / 2,
				this.getPositionY() + this.getPositionHeight() / 2);
		this.target = new Vector();
		this.targetPath = new Vector();
		this.speed = speed;
		this.diameterAggro = diameterAggro;
		this.aStar = new AStar(this.facade, this, scaleNodeDimension);
	}

	@Override
	public void update() {
		if (this.health > 0) {
			this.chooseTarget();
			if (this.getPositionPath) {
				this.aStar.update(this.getPositionX() - this.diameterAggro / 2,
						this.getPositionY() - this.diameterAggro / 2, this.target);
				this.targetPath = this.aStar.getPath();
				this.getPositionPath = false;
			}
			this.move();
			this.attack();
		} else {
			this.die();
		}
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		if (this.facade.getDebugMode()) {
			new EnemyDesignerDebug(this.facade, g, this).drawDebug();
		}
		this.aStar.getMap().mapRemoveEntity();

		this.drawRangeAggro(g);
	}

	@Override
	protected void move() {
		this.moveToPoint();
		super.move();
		this.chooseCurrentAnimation();
		this.resetMovement();
	}

	protected void chooseTarget() {
		this.playerInAggro();
		if (this.playerInAggro) {
			targetPlayer();
		} else {
			if (this.getStart) {
				if (this.setWatch) {
					this.setWatchTarget();
					this.setWatch = false;
				}
				this.checkEndWatch();
			}
			if (!this.getStart) {
				this.target.setX(this.start.getX());
				this.target.setY(this.start.getY());
				this.checkIfStart();
			}
		}
	}

	private void targetPlayer() {
		this.target.setX(this.facade.getEntityManager().getPlayer().getPositionX()
				+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2);
		this.target.setY(this.facade.getEntityManager().getPlayer().getPositionY()
				+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2);
	}

	private void setWatchTarget() {
		int x = this.start.getX() - this.diameterAggro
				+ (int) (Math.random() * (this.start.getX() + this.diameterAggro));
		int y = this.start.getY() - this.diameterAggro
				+ (int) (Math.random() * (this.start.getY() + this.diameterAggro));
		if (x < 0) {
			x = 0;
		}
		if (x > this.facade.getWidth()) {
			x = this.facade.getWidth();
		}
		if (y < 0) {
			y = 0;
		}
		if (y > this.facade.getHeight()) {
			y = this.facade.getHeight();
		}
		int flagX = x / Tile.TILEWIDTH;
		int flagY = y / Tile.TILEHEIGHT;
		if (Tile.tiles[this.facade.getWorld().getTiles()[flagX][flagY]].isSolid()) {
			x = STANDARDX;
			y = STANDARDY;
		} else if ((Math.abs(x - this.getPositionX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(y - this.getPositionY())) < 2 * this.aStar.getMap().getNodeDimension()) {
			x = STANDARDX;
			y = STANDARDY;
		} else if (((Math.abs(x - this.start.getX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(y - this.start.getY())) < 2 * this.aStar.getMap().getNodeDimension())) {
			x = STANDARDX;
			y = STANDARDY;
		}

		this.target.setX(x);
		this.target.setY(y);
		this.setWatch = false;
	}

	protected void drawRangeAggro(Graphics2D g) {
		if (this.facade.getDebugMode()) {
			Rectangle StartBatEye = new Rectangle(
					(int) (this.position.getX() - this.diameterAggro / 2 + this.getPositionWidth() / 2),
					(int) (this.position.getY() - this.diameterAggro / 2 + this.getPositionHeight() / 2), 0, 0);
			if (this.playerInAggro) {
				g.setColor(new Color(255, 0, 0, 100));
			}
			g.fillOval(this.getXMoveHitbox(StartBatEye), this.getYMoveHitbox(StartBatEye), this.diameterAggro,
					this.diameterAggro);
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

		if (distanceToPlayer < this.diameterAggro / 2) {
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

		if (distanceToPlayer < this.aggro) {
			this.getStart = true;
			this.setWatch = true;
		}
	}

	private void checkEndWatch() {
		Vector delta = new Vector();

		delta.setX(Math.abs(this.target.getX() - this.getPositionX() - this.getPositionWidth() / 2));
		delta.setY(Math.abs(this.target.getY() - this.getPositionY() - this.getPositionHeight() / 2));
		int distanceToPlayer = (int) Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));
		if (distanceToPlayer < this.aggro) {
			this.getStart = false;
		}
	}

	protected void moveToPoint() {
		Vector delta = new Vector();

		delta.setX(this.targetPath.getX() - this.getPositionX() - this.getPositionWidth() / 2);
		delta.setY(this.targetPath.getY() - this.getPositionY() - this.getPositionHeight() / 2);

		if (Math.abs(delta.getX()) < 20 * this.speed && Math.abs(delta.getY()) < 20 * this.speed) {
			this.getPositionPath = true;
		}
		if (Math.abs(delta.getX()) < 8 * this.speed) {
			this.xMove = 0;
		} else {
			if (delta.getX() < 0) {
				this.xMove = -this.speed;
			}
			if (delta.getX() > 0) {
				this.xMove = this.speed;
			}
		}
		if (Math.abs(delta.getY()) < 8 * this.speed) {
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

	public int getDiameterAggro() {
		return this.diameterAggro;
	}

	public int getSpeed() {
		return this.speed;
	}
}
