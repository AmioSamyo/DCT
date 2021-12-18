package DCT.entity.creature;

import java.awt.Graphics2D;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.AStar;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Enemy extends Creature {

	protected int rangeOfAttack, watchTargetX, watchTargetY;
	protected int diameterAggro = 300, distanceToChange = 20 * this.speed, speedLimit = 8 * this.speed;
	protected boolean getStart = true;
	protected boolean getPositionPath = true;
	protected boolean setWatch = true;

	protected Vector start, target, targetPath;
	protected AStar aStar;

	protected static final int ATTACKCOOLDOWN = 600;

	private long lastAttackTimer, attackTimer = ATTACKCOOLDOWN;

	public Enemy(Facade facade, Rectangle position, int speed, int diameterAggro, int scaleNodeDimension) {
		super(facade, position);
		this.speed = speed;
		this.diameterAggro = diameterAggro;
		this.aStar = new AStar(this.facade, this, scaleNodeDimension);
		this.initialize();
	}

	public int getDiameterAggro() {
		return this.diameterAggro;
	}

	public int getSpeed() {
		return this.speed;
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		if (this.facade.getDebugMode())
			new EnemyDesignerDebug(this.facade, g, this).drawDebug();
		this.aStar.getMap().mapRemoveEntity();
	}

	@Override
	public void update() {
		if (this.health > 0) {
			this.handleMovement();
			this.attack();
		} else {
			this.die();
		}
	}

	@Override
	protected void move() {
		this.moveToPoint();
		super.move();
		this.chooseCurrentAnimation();
		this.resetMovement();
	}

	protected boolean playerInAggro() {
		Vector delta = this.deltaInitAbs(new Vector(this.facade.getPlayerX() + this.facade.getPlayerWidth() / 2,
				this.facade.getPlayerY() + this.facade.getPlayerHeight() / 2));

		if (delta.getModule() < this.diameterAggro / 2) {
			this.getStart = false;
			return true;
		}
		return false;
	}

	private void attack() {
		this.attackTimer();
		if (this.attackTimer >= ATTACKCOOLDOWN) {
			Rectangle playerPosition = new Rectangle(this.facade.getPlayerX(), this.facade.getPlayerY(),
					this.facade.getPlayerWidth(), this.facade.getPlayerHeight());

			if (this.position.intersects(playerPosition)) {
				this.facade.getEntityManager().getPlayer().addHealth(-1);
				this.attackTimer = 0;
			}
		}
	}

	private void attackTimer() {
		this.attackTimer += System.currentTimeMillis() - this.lastAttackTimer;
		this.lastAttackTimer = System.currentTimeMillis();
	}

	private int calculateSpeed(int axis) {
		if (Math.abs(axis) < this.speedLimit)
			return 0;
		return axis < 0 ? -this.speed : this.speed;
	}

	private void checkStart() {
		Vector startToEntity = this.deltaInitAbs(this.start);
		if (startToEntity.getModule() < this.distanceToChange) {
			this.getStart = true;
			this.setWatch = true;
		}
	}

	private void checkEnd() {
		Vector targetToEntity = this.deltaInitAbs(this.target);
		if (targetToEntity.getModule() < this.distanceToChange) {
			this.getStart = false;
		}
	}

	private void chooseTarget() {
		if (this.playerInAggro()) {
			this.targetPlayer();
			return;
		}
		if (this.getStart)
			this.newRouteTarget();
		else
			this.routeToStart();
	}

	private Vector deltaInit(Vector v) {
		Vector delta = new Vector();
		delta.setX(v.getX() - this.getPositionX() - this.getPositionWidth() / 2);
		delta.setY(v.getY() - this.getPositionY() - this.getPositionHeight() / 2);
		return delta;
	}

	private Vector deltaInitAbs(Vector v) {
		Vector delta = new Vector();
		delta.setX(Math.abs(v.getX() - this.getPositionX() - this.getPositionWidth() / 2));
		delta.setY(Math.abs(v.getY() - this.getPositionY() - this.getPositionHeight() / 2));
		return delta;
	}

	private void getPositionPath() {
		this.aStar.update(this.getPositionX() - this.diameterAggro / 2, this.getPositionY() - this.diameterAggro / 2,
				this.target);
		this.targetPath = this.aStar.getPath();
		this.getPositionPath = false;
	}

	private void handleMovement() {
		this.chooseTarget();
		if (this.getPositionPath)
			this.getPositionPath();
		this.move();
	}

	private void initialize() {
		this.start = new Vector(this.getPositionX() + this.getPositionWidth() / 2,
				this.getPositionY() + this.getPositionHeight() / 2);
		this.target = new Vector();
		this.targetPath = new Vector();
	}

	private void moveToPoint() {
		Vector pointToEntity = this.deltaInit(this.targetPath);

		if (Math.abs(pointToEntity.getX()) < this.distanceToChange
				&& Math.abs(pointToEntity.getY()) < this.distanceToChange) {
			this.getPositionPath = true;
		}
		this.xMove = this.calculateSpeed(pointToEntity.getX());
		this.yMove = this.calculateSpeed(pointToEntity.getY());
	}

	private void newRouteTarget() {
		if (this.setWatch) {
			this.setWatchTarget();
		}
		this.checkEnd();
	}

	private void routeToStart() {
		this.target.setX(this.start.getX());
		this.target.setY(this.start.getY());
		this.checkStart();
	}

	private void setWatchLimit() {
		if (this.watchTargetX < 0) {
			this.watchTargetX = 0;
		}
		if (this.watchTargetX > this.facade.getWidth()) {
			this.watchTargetX = this.facade.getWidth();
		}
		if (this.watchTargetY < 0) {
			this.watchTargetY = 0;
		}
		if (this.watchTargetY > this.facade.getHeight()) {
			this.watchTargetY = this.facade.getHeight();
		}
	}

	private void setWatchStandard() {
		final int STANDARDX = 300, STANDARDY = 700;
		int tileX = this.watchTargetX / Tile.TILEWIDTH;
		int tileY = this.watchTargetY / Tile.TILEHEIGHT;
		if (Tile.tiles[this.facade.getWorld().getTiles()[tileX][tileY]].isSolid()) {
			this.watchTargetX = STANDARDX;
			this.watchTargetY = STANDARDY;
		} else if ((Math.abs(this.watchTargetX - this.getPositionX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(this.watchTargetY - this.getPositionY())) < 2 * this.aStar.getMap().getNodeDimension()) {
			this.watchTargetX = STANDARDX;
			this.watchTargetY = STANDARDY;
		} else if (((Math.abs(this.watchTargetX - this.start.getX())) < 2 * this.aStar.getMap().getNodeDimension()
				&& (Math.abs(this.watchTargetY - this.start.getY())) < 2 * this.aStar.getMap().getNodeDimension())) {
			this.watchTargetX = STANDARDX;
			this.watchTargetY = STANDARDY;
		}
	}

	private void setWatchTarget() {
		this.watchTargetX = this.start.getX() - this.diameterAggro
				+ (int) (Math.random() * (this.start.getX() + this.diameterAggro));
		this.watchTargetY = this.start.getY() - this.diameterAggro
				+ (int) (Math.random() * (this.start.getY() + this.diameterAggro));
		this.setWatchLimit();
		this.setWatchStandard();
		this.target.setX(this.watchTargetX);
		this.target.setY(this.watchTargetY);
		this.setWatch = false;
	}

	private void targetPlayer() {
		this.target.setX(this.facade.getPlayerX() + this.facade.getPlayerWidth() / 2);
		this.target.setY(this.facade.getPlayerY() + this.facade.getPlayerHeight() / 2);
	}
}
