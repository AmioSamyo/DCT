package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.AStar;
import DCT.utility.Node;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Enemy extends Creature {

	protected boolean playerInAggro = false;

	protected Vector start, target, targetPath;
	private long lastAttackTimer, attackCooldown = 600, attackTimer = this.attackCooldown;
	protected int rangeOfAttack;
	protected int diameterAggro = 300;
	protected boolean getStart = true;
	protected boolean getPositionPath = true;
	protected AStar aStar;
	protected boolean setWatch = true;

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
		if (this.facade.getDebugMode() ) {
		this.drawNode(g);
		}
		this.aStar.getMap().mapRemoveEntity();

		this.drawRangeAggro(g);
	}

	private void drawNode(Graphics2D g) {

		g.setColor(new Color(175, 0, 120, 100));
		g.fillRect(this.start.getX() - this.facade.getGameCamera().getXOffset(),
				this.start.getY() - this.facade.getGameCamera().getYOffset(), 64, 64);

		g.setColor(new Color(0, 0, 120, 100));
		g.fillRect(this.target.getX() - this.facade.getGameCamera().getXOffset(),
				this.target.getY() - this.facade.getGameCamera().getYOffset(), 64, 64);

		for (int i = 0; i < this.aStar.getMap().getRow(); i++) {
			for (int j = 0; j < this.aStar.getMap().getColumn(); j++) {

				if (!this.aStar.getMap().getNode(j, i).isViable()) {
					g.setColor(new Color(0, 0, 0));
					g.drawRect(
							this.getPositionX() - this.diameterAggro / 2 + j * this.aStar.getMap().getNodeDimension()
									- this.facade.getGameCamera().getXOffset(),
							this.getPositionY() - this.diameterAggro / 2 + i * this.aStar.getMap().getNodeDimension()
									- this.facade.getGameCamera().getYOffset(),
							this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());
				} else {
					g.setColor(new Color(255, 255, 255));
					g.drawRect(
							this.getPositionX() - this.diameterAggro / 2 + j * this.aStar.getMap().getNodeDimension()
									- this.facade.getGameCamera().getXOffset(),
							this.getPositionY() - this.diameterAggro / 2 + i * this.aStar.getMap().getNodeDimension()
									- this.facade.getGameCamera().getYOffset(),
							this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());
				}
			}
		}

		ArrayList<Node> flag = this.aStar.getPathNode();
		for (int k = 0; k < flag.size(); k++) {
			int j = flag.get(k).getX();
			int i = flag.get(k).getY();
			g.setColor(new Color(255, 0, 0));
			g.drawRect(
					this.getPositionX() - this.diameterAggro / 2 + j * this.aStar.getMap().getNodeDimension()
							- this.facade.getGameCamera().getXOffset(),
					this.getPositionY() - this.diameterAggro / 2 + i * this.aStar.getMap().getNodeDimension()
							- this.facade.getGameCamera().getYOffset(),
					this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());
		}
		Vector start = new Vector(this.aStar.getStart());
		Vector target = new Vector(this.aStar.getTarget());
		g.setColor(new Color(0, 255, 0));
		g.drawRect(
				this.getPositionX() - this.diameterAggro / 2 + start.getX() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getXOffset(),
				this.getPositionY() - this.diameterAggro / 2 + start.getY() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getYOffset(),
				this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());

		g.setColor(new Color(0, 0, 255));
		g.drawRect(
				this.getPositionX() - this.diameterAggro / 2 + target.getX() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getXOffset(),
				this.getPositionY() - this.diameterAggro / 2 + target.getY() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getYOffset(),
				this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());

		g.setColor(new Color(0, 0, 255));
		g.drawLine(this.getPositionX() + this.getPositionWidth() / 2 - this.facade.getGameCamera().getXOffset(),
				this.getPositionY() + this.getPositionHeight() / 2 - this.facade.getGameCamera().getYOffset(),
				this.targetPath.getX() - this.facade.getGameCamera().getXOffset(),
				this.targetPath.getY() - this.facade.getGameCamera().getYOffset());
		g.setColor(this.debuggingColor);
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
			this.target.setX(this.facade.getEntityManager().getPlayer().getPositionX()
					+ this.facade.getEntityManager().getPlayer().getPositionWidth() / 2);
			this.target.setY(this.facade.getEntityManager().getPlayer().getPositionY()
					+ this.facade.getEntityManager().getPlayer().getPositionHeight() / 2);

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
			x = 300;
			y = 700;
		} else if ((Math.abs(x - this.getPositionX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(y - this.getPositionY())) < 2 * this.aStar.getMap().getNodeDimension()) {
			x = 300;
			y = 700;
		} else if (((Math.abs(x - this.start.getX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(y - this.start.getY())) < 2 * this.aStar.getMap().getNodeDimension())) {
			x = 300;
			y = 700;
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

		if (distanceToPlayer < 20 * this.speed) {
			this.getStart = true;
			this.setWatch = true;
		}

	}

	private void checkEndWatch() {

		Vector delta = new Vector();

		delta.setX(Math.abs(this.target.getX() - this.getPositionX() - this.getPositionWidth() / 2));
		delta.setY(Math.abs(this.target.getY() - this.getPositionY() - this.getPositionHeight() / 2));
		int distanceToPlayer = (int) Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));
		if (distanceToPlayer < 20 * this.speed) {
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
