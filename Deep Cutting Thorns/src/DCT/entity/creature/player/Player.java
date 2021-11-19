package DCT.entity.creature.player;

import java.awt.Color;
import java.awt.Graphics;
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
	private int rollCurrentDistance;
	private long lastAttackTimer, attackTimer;

	private Weapon equippedWeapon = new Weapon(10, 800, new Rectangle(50, 50, 50, 50), this);

	private Animation playerSprintDown, playerSprintRight, playerSprintUp, playerSprintLeft;
	private Animation playerRoll;

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
		this.attackTimer = this.equippedWeapon.getCooldown();
		this.lastAttackTimer = 0;

		initialize();
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.7),
				(int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.16));

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

		this.currentAnimation = this.animationIdle;

		this.setDebuggingColor(new Color(255, 102, 255));
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
			this.checkAttacks();
		} else if (this.health > 0) {
			this.playerMovement();
		}

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				this.position.getWidth(), this.position.getHeight(), null);

		this.drawHitBox(g);
		if (this.isAttacking()) {
			this.drawWeaponDamageBox(g);
		}
	}
	
	@Override
	public void damage(int amount) {
		if(!this.isRolling) {
			this.health -= amount;
		}
	}
	private void checkAttacks() {
		this.attackTimer += System.currentTimeMillis() - this.lastAttackTimer;
		this.lastAttackTimer = System.currentTimeMillis();
		if (this.attackTimer >= this.equippedWeapon.getCooldown()) {
			this.attackDirections();
			this.attackTimer = 0;
			this.attacking = false;

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

	public void attackDirections() {
		if (this.previousDirection.getX() > 0 && this.previousDirection.getY() > 0) {// BOTRIGHT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() + this.hitBox.getWidth(),
					this.hitBox.getY() + this.hitBox.getHeight(), this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getX() < 0 && this.previousDirection.getY() > 0) {// BOTLEFT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() - this.hitBox.getWidth(),
					this.hitBox.getY() + this.hitBox.getHeight(), this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getX() < 0 && this.previousDirection.getY() < 0) {// UPLEFT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() - this.hitBox.getWidth(),
					this.hitBox.getY() - this.hitBox.getHeight(), this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getX() < 0 && this.previousDirection.getY() < 0) {// UPRIGHT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() + this.hitBox.getWidth(),
					this.hitBox.getY() - this.hitBox.getHeight(), this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getX() > 0) {//RIGHT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() + this.hitBox.getWidth(), this.hitBox.getY(),
					this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getX() < 0) {//LEFT
			this.equippedWeapon.setDamageBox(this.hitBox.getX() - this.hitBox.getWidth(), this.hitBox.getY(),
					this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getY() > 0) {//DOWN
			this.equippedWeapon.setDamageBox(this.hitBox.getX(), this.hitBox.getY() + this.hitBox.getHeight(),
					this.hitBox.getWidth(), this.hitBox.getHeight());
		} else if (this.previousDirection.getY() < 0) {//UP
			this.equippedWeapon.setDamageBox(this.hitBox.getX(), this.hitBox.getY() - this.hitBox.getHeight(),
					this.hitBox.getWidth(), this.hitBox.getHeight());
		}
	}

	@Override
	public void showHealthBar(Graphics g) {
		float rangeHealthBar = (float) (MAX_HEALTH - 1) / ((float) Assets.healthBars.length - 1);
		int currentHealthBarToShow = (int) ((float) (MAX_HEALTH - this.health) / rangeHealthBar);
		if (currentHealthBarToShow < 0)
			currentHealthBarToShow = 0;
		if (currentHealthBarToShow > 28)
			currentHealthBarToShow = 28;
		g.drawImage(Assets.healthBars[currentHealthBarToShow], 20, 20, (int) (390 * 0.75), (int) (70 * 0.75), null);

	}

	private void drawWeaponDamageBox(Graphics g) {
		if (this.facade.getDebugMode()) {
			this.equippedWeapon.render(g);
		}
	}

	@Override
	public void die() {
		this.currentAnimation = this.animationDead;
	}

	public boolean isAttacking() {
		return this.attacking;
	}

	public int getCurrentHealth() {
		return this.currentHealth;
	}

	public void addHealth(int value) {
		this.health += value;
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

	private void rollUp() {
		int futureY = this.position.getY() + this.hitBox.getY() - this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY) && !this
				.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(), futureY)) {
			this.setY(this.getPositionY() - this.rollCurrentDistance);
		}
	}

	private void rollDown() {
		int futureY = this.position.getY() + this.hitBox.getY() + this.hitBox.getHeight() + this.rollCurrentDistance;

		if (!this.checkCollisionWithTile(this.position.getX() + this.hitBox.getX(), futureY) && !this
				.checkCollisionWithTile(this.position.getX() + this.hitBox.getX() + this.hitBox.getWidth(), futureY)) {
			this.setY(this.getPositionY() + this.rollCurrentDistance);
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
		if (this.facade.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			this.attacking = true;
		}
	}

	private void setYSpeed(int speed, int sprintSpeed) {
		if (this.facade.getKeyManager().getSprint()) {
			this.addYMove(sprintSpeed);
		} else {
			this.addYMove(speed);
		}
	}

	private void setXSpeed(int speed, int sprintSpeed) {
		if (this.facade.getKeyManager().getSprint()) {
			this.addXMove(sprintSpeed);
		} else {
			this.addXMove(speed);
		}
	}

	private void roll() {
		this.isRolling = true;
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
	}

	private void setAnimation(Animation anim, Animation animSprint) {
		if (this.facade.getKeyManager().getSprint()) {
			this.currentAnimation = animSprint;
		} else {
			this.currentAnimation = anim;
		}
	}
}
