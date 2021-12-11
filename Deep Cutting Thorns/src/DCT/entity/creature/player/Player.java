package DCT.entity.creature.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.entity.creature.Creature;
import DCT.entity.creature.player.items.Weapon;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Player extends Creature {

	private boolean isRolling;
	private boolean attacking;
	private int rollCurrentDistance, attackAnimIndex = 0;
	private long lastAttackTimer, attackTimer;

	private Weapon equippedWeapon;
	private Animation playerSprintDown, playerSprintRight, playerSprintUp, playerSprintLeft;
	private Animation playerRoll, playerAttacking;
	private Vector attackDirection;

	private static final int PLAYERWIDTH = 704 / 11, PLAYERHEIGHT = 320 / 5;
	private static final int SCALE = 2;
	private static final int ANIMATIONSPEED = 100, ANIMATIONSPRINTSPEED = 60;
	private static final int SPRINTSPEED = 8, ROLLBASEDISTANCE = 17;
	private static final double ROLLDELTA = 0.1;

	public Player(Facade facade, Vector position) {

		super(facade, new Rectangle(position.getX(), position.getY(), PLAYERWIDTH * SCALE, PLAYERHEIGHT * SCALE));

		this.speed = 4;
		this.isRolling = false;
		this.rollCurrentDistance = ROLLBASEDISTANCE;
		this.lastAttackTimer = 0;

		this.initialize();
	}

	public void addHealth(int value) {
		this.health += value;
	}

	public void attackDirections() {

		boolean botRightCondition = this.attackDirection.getX() > 0 && this.attackDirection.getY() > 0;
		boolean botLeftCondition = this.attackDirection.getX() < 0 && this.attackDirection.getY() > 0;

		boolean upLeftCondition = this.attackDirection.getX() < 0 && this.attackDirection.getY() < 0;
		boolean upRIghtCondition = this.attackDirection.getX() > 0 && this.attackDirection.getY() < 0;

		boolean rightCondition = this.attackDirection.getX() > 0;
		boolean leftCondition = this.attackDirection.getX() < 0;
		boolean downCondition = this.attackDirection.getY() > 0;
		boolean upCondition = this.attackDirection.getY() < 0;

		if (botRightCondition) {

			this.setDamageDirection(this.hitBox.getWidth(), this.hitBox.getHeight());

		} else if (botLeftCondition) {

			this.setDamageDirection(-this.hitBox.getWidth(), this.hitBox.getHeight());

		} else if (upLeftCondition) {

			this.setDamageDirection(-this.hitBox.getWidth(), -this.hitBox.getHeight());

		} else if (upRIghtCondition) {

			this.setDamageDirection(this.hitBox.getWidth(), -this.hitBox.getHeight());

		} else if (rightCondition) {

			this.setDamageDirection(this.hitBox.getWidth(), 0);

		} else if (leftCondition) {

			this.setDamageDirection(-this.hitBox.getWidth(), 0);

		} else if (downCondition) {

			this.setDamageDirection(0, this.hitBox.getHeight());

		} else if (upCondition) {

			this.setDamageDirection(0, -this.hitBox.getHeight());

		} else {

			this.attacking = false;
			this.equippedWeapon.setDamageBox(0, 0, 0, 0);

		}
	}

	private void setDamageDirection(int xAdjustment, int yAdjustment) {
		this.equippedWeapon.setDamageBox(this.hitBox.getX() + xAdjustment, this.hitBox.getY() + yAdjustment,
				this.hitBox.getWidth(), this.hitBox.getHeight());
	}

	@Override
	public void damage(int amount) {
		if (!this.isRolling) {
			this.health -= amount;
		}
	}

	@Override
	public void die() {
		this.currentAnimation = this.animationDead;
	}

	public int getCurrentHealth() {
		return this.health;
	}

	public boolean isAttacking() {
		return this.attacking;
	}

	@Override
	public void render(Graphics2D g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				this.position.getWidth(), this.position.getHeight(), null);

		this.drawHitBox(g);

		if (this.isAttacking()) {
			this.drawWeaponDamageBox(g);
		}
	}

	public void setHealth(int hp) {
		this.health = hp;
	}

	@Override
	public void showHealthBar(Graphics2D g) {

		float rangeHealthBar = (float) (MAX_HEALTH - 1) / ((float) Assets.healthBars.length - 1);
		int currentHealthBarToShow = (int) ((float) (MAX_HEALTH - this.health) / rangeHealthBar);

		int maxHealthBar = 28;

		if (currentHealthBarToShow < 0)
			currentHealthBarToShow = 0;
		if (currentHealthBarToShow > maxHealthBar)
			currentHealthBarToShow = maxHealthBar;

		int x = 20;
		int y = 20;
		int width = 390;
		int height = 70;
		double scale = 0.75;

		g.drawImage(Assets.healthBars[currentHealthBarToShow], x, y, (int) (width * scale), (int) (height * scale),
				null);

	}

	@Override
	public void update() {
		if (this.health <= 0) {
			this.die();
		}

		this.animationIdle.update();
		this.animationMoveUp.update();
		this.animationMoveRight.update();
		this.animationMoveLeft.update();
		this.animationMoveDown.update();

		this.playerSprintUp.update();
		this.playerSprintRight.update();
		this.playerSprintLeft.update();
		this.playerSprintDown.update();

		this.playerRoll.update();

		this.currentAnimation.update();

		if (this.isAttacking()) {
			this.attack();
			this.checkAttacks();
		} else if (this.health > 0) {
			this.playerMovement();
		}

	}

	@Override
	protected void chooseCurrentAnimation() {
		if (this.isMovingUp()) {
			this.setAnimation(this.animationMoveUp, this.playerSprintUp);
		}
		if (this.isMovingDown()) {
			this.setAnimation(this.animationMoveDown, this.playerSprintDown);
		}
		if (this.isMovingLeft()) {
			this.setAnimation(this.animationMoveLeft, this.playerSprintLeft);
		}
		if (this.isMovingRight()) {
			this.setAnimation(this.animationMoveRight, this.playerSprintRight);
		}
		if (this.isNotMoving()) {
			this.currentAnimation = this.animationIdle;
		}
		if (this.isRolling) {
			this.currentAnimation = this.playerRoll;
		}
		if (this.isAttacking()) {
			this.currentAnimation = this.playerAttacking;
		}
	}

	private void attack() {
		this.attackAnimIndex++;
		this.playerAttacking.update();
		if (this.attackAnimIndex >= this.playerAttacking.getAnimationLength()) {
			this.attacking = false;
			this.facade.getMouseManager().setLeftClicked(false);
			this.attackAnimIndex = 0;
		}
	}

	private void aimAttack() {
		this.attacking = true;
		Rectangle topLeft = new Rectangle(this.getPositionX() - this.facade.getWidth(),
				this.getPositionY() - this.facade.getHeight(), this.facade.getWidth(), this.facade.getHeight());
		Rectangle top = new Rectangle(this.getPositionX(), this.getPositionY() - this.facade.getHeight(),
				this.getPositionWidth(), this.facade.getHeight());
		Rectangle topRight = new Rectangle(this.getPositionX() + this.getPositionWidth(),
				this.getPositionY() - this.facade.getHeight(), this.facade.getWidth(), this.facade.getHeight());
		Rectangle right = new Rectangle(this.getPositionX() + this.getPositionWidth(), this.getPositionY(),
				this.facade.getWidth(), this.getPositionHeight());
		Rectangle botRight = new Rectangle(this.getPositionX() + this.getPositionWidth(),
				this.getPositionY() + this.getPositionHeight(), this.facade.getWidth(), this.facade.getHeight());
		Rectangle bot = new Rectangle(this.getPositionX(), this.getPositionY() + this.getPositionHeight(),
				this.getPositionWidth(), this.facade.getHeight());
		Rectangle botLeft = new Rectangle(this.getPositionX() - this.facade.getWidth(),
				this.getPositionY() + this.getPositionHeight(), this.facade.getWidth(), this.facade.getHeight());
		Rectangle left = new Rectangle(this.getPositionX() - this.facade.getWidth(), this.getPositionY(),
				this.facade.getWidth(), this.getPositionHeight());
		boolean TL = topLeft.contains(
				this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean T = top.contains(this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean TR = topRight.contains(
				this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean R = right.contains(this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean BR = botRight.contains(
				this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean B = bot.contains(this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean BL = botLeft.contains(
				this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());
		boolean L = left.contains(this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());

		if (TL) {
			this.attackDirection = new Vector(-1, -1);
		} else if (T) {
			this.attackDirection = new Vector(0, -1);
		} else if (TR) {
			this.attackDirection = new Vector(1, -1);
		} else if (R) {
			this.attackDirection = new Vector(1, 0);
		} else if (BR) {
			this.attackDirection = new Vector(1, 1);
		} else if (B) {
			this.attackDirection = new Vector(0, 1);
		} else if (BL) {
			this.attackDirection = new Vector(-1, 1);
		} else if (L) {
			this.attackDirection = new Vector(-1, 0);
		} else {
			this.attackDirection = new Vector();
		}
	}

	private void checkAttacks() {
		this.attackTimer += System.currentTimeMillis() - this.lastAttackTimer;
		this.lastAttackTimer = System.currentTimeMillis();
		if (this.attackTimer >= this.equippedWeapon.getCooldown()) {
			this.attackDirections();
			this.attackTimer = 0;

			for (Entity e : this.facade.getEntityManager().getEntityList()) {
				if (e.equals(this)) {
					continue;
				}

				if (e.getCollisionHitBox(-this.facade.getGameCamera().getXOffset(),
						-this.facade.getGameCamera().getYOffset())
						.intersects(this.equippedWeapon.getDamageBoxRelative())) {

					e.damage(this.equippedWeapon.getDamage());
				}
			}
		}
	}

	private void drawWeaponDamageBox(Graphics2D g) {
		if (this.facade.getDebugMode()) {
			this.equippedWeapon.render(g);
		}
	}

	private void getInput() {
		if (this.facade.getKeyManager().getUp()) {
			this.setYSpeed(-this.speed, -SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getDown()) {
			this.setYSpeed(this.speed, SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getLeft()) {
			this.setXSpeed(-this.speed, -SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getRight()) {
			this.setXSpeed(this.speed, SPRINTSPEED);
		}
		if (this.facade.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && !this.isNotMoving()) {
			this.roll();
		}
		if (this.facade.getMouseManager().getLeftClicked()) {
			this.aimAttack();
		}
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.7),
				(int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.16));

		this.equippedWeapon = new Weapon(10, 800, new Rectangle(this.hitBox.getX() - this.hitBox.getWidth(),
				this.hitBox.getY(), this.hitBox.getWidth(), this.hitBox.getHeight()), this);

		this.attackTimer = this.equippedWeapon.getCooldown();

		this.animationMoveDown = new Animation(ANIMATIONSPEED, Assets.playerAnimationDown);
		this.animationMoveRight = new Animation(ANIMATIONSPEED, Assets.playerAnimationRight);
		this.animationMoveUp = new Animation(ANIMATIONSPEED, Assets.playerAnimationUp);
		this.animationMoveLeft = new Animation(ANIMATIONSPEED, Assets.playerAnimationLeft);
		this.animationIdle = new Animation(ANIMATIONSPEED, Assets.playerAnimationIdle);
		this.animationDead = new Animation(1, Assets.playerDeadAnimation);

		this.playerSprintDown = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationDown);
		this.playerSprintRight = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationRight);
		this.playerSprintUp = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationUp);
		this.playerSprintLeft = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationLeft);

		this.playerRoll = new Animation(0, Assets.playerAnimationRoll);
		this.playerAttacking = new Animation(0, Assets.playerAttacking);

		this.currentAnimation = this.animationIdle;

		this.setDebuggingColor(new Color(255, 102, 255, 200));
	}

	private void playerMovement() {
		this.getInput();

		if (this.isRolling) {
			this.rollingMove();
		} else {
			this.move();
		}
		this.chooseCurrentAnimation();
		this.resetMovement();

		this.facade.getGameCamera().centerOnEntity(this);
	}

	private void roll() {
		this.isRolling = true;
	}

	private void rollDown() {
		int futureY = this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight() + this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY) && !this
				.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(), futureY)) {
			this.setY(this.getPositionY() + this.rollCurrentDistance);
		}
	}

	private void rollingMove() {
		if (!this.checkEntityCollisions(this.previousDirection.getX() * this.rollCurrentDistance,
				this.previousDirection.getY() * this.rollCurrentDistance)) {

			if (this.previousDirection.getY() < 0) {
				this.rollUp();
			} else if (this.previousDirection.getY() > 0) {
				this.rollDown();
			}
			if (this.previousDirection.getX() < 0) {
				this.rollLeft();
			} else if (this.previousDirection.getX() > 0) {
				this.rollRight();
			}
		}
		this.rollCurrentDistance -= this.rollCurrentDistance * ROLLDELTA;
		if (this.rollCurrentDistance < 1) {
			this.rollCurrentDistance = ROLLBASEDISTANCE;
			this.isRolling = false;
		}
	}

	private void rollLeft() {
		int futureX = this.position.getX() + this.hitBox.getX() - this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY()) && !this
				.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight())) {
			this.setX(this.getPositionX() - this.rollCurrentDistance);
		}
	}

	private void rollRight() {
		int futureX = this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth() + this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY()) && !this
				.checkCollisionWithTile(futureX, this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight())) {
			this.setX(this.getPositionX() + this.rollCurrentDistance);
		}
	}

	private void rollUp() {
		int futureY = this.position.getY() + this.hitBox.getY() - this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY) && !this
				.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(), futureY)) {
			this.setY(this.getPositionY() - this.rollCurrentDistance);
		}
	}

	private void setAnimation(Animation anim, Animation animSprint) {
		if (this.facade.getKeyManager().getSprint()) {
			this.currentAnimation = animSprint;
		} else {
			this.currentAnimation = anim;
		}
	}

	private void setXSpeed(int speed, int sprintSpeed) {
		if (this.facade.getKeyManager().getSprint()) {
			this.addXMove(sprintSpeed);
		} else {
			this.addXMove(speed);
		}
	}

	private void setYSpeed(int speed, int sprintSpeed) {
		if (this.facade.getKeyManager().getSprint()) {
			this.addYMove(sprintSpeed);
		} else {
			this.addYMove(speed);
		}
	}

}
