package DCT.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Player extends Creature {

	private int currentHealth;
	private boolean isRolling;
	private int rollCurrentDistance;

	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft, playerIdle;
	private Animation playerSprintDown, playerSprintRight, playerSprintUp, playerSprintLeft;
	private Animation playerRoll;
	private Animation currentAnimation;

	private static final int PLAYERWIDTH = 704 / 11, PLAYERHEIGHT = 320 / 5;
	private static final int SCALE = 2;
	private static final int ANIMATIONSPEED = 100, ANIMATIONSPRINTSPEED = 60;
	private static final int SPRINTSPEED = 8, ROLLBASEDISTANCE = 30;
	private static final double ROLLDELTA = 0.1;

	public Player(Facade facade, int x, int y) {

		super(facade, new Rectangle(x, y, PLAYERWIDTH * SCALE, PLAYERHEIGHT * SCALE));
		this.speed = 4;
		this.isRolling = false;
		this.rollCurrentDistance = this.ROLLBASEDISTANCE;

		initialize();
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.7),
				(int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.16));

		this.playerMoveDown = new Animation(ANIMATIONSPEED, Assets.playerAnimationDown);
		this.playerMoveRight = new Animation(ANIMATIONSPEED, Assets.playerAnimationRight);
		this.playerMoveUp = new Animation(ANIMATIONSPEED, Assets.playerAnimationUp);
		this.playerMoveLeft = new Animation(ANIMATIONSPEED, Assets.playerAnimationLeft);
		this.playerIdle = new Animation(ANIMATIONSPEED, Assets.playerAnimationIdle);

		this.playerSprintDown = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationDown);
		this.playerSprintRight = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationRight);
		this.playerSprintUp = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationUp);
		this.playerSprintLeft = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationLeft);

		this.playerRoll = new Animation(0, Assets.playerAnimationRoll);

		this.currentAnimation = this.playerIdle;
	}

	@Override
	public void update() {
		if (currentHealth <= 0) {
			die();
		}

		this.playerIdle.update();
		this.playerMoveUp.update();
		this.playerMoveRight.update();
		this.playerMoveLeft.update();
		this.playerMoveDown.update();

		this.playerSprintUp.update();
		this.playerSprintRight.update();
		this.playerSprintLeft.update();
		this.playerSprintDown.update();

		this.playerRoll.update();

		this.currentAnimation.update();

		this.playerMovement();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				this.position.getWidth(), this.position.getHeight(), null);

		super.render(g);
	}

	@Override
	public void die() {

	}

	public int getCurrentHealth() {
		return this.currentHealth;
	}

	private void playerMovement() {
		this.getInput();

		if (!this.isRolling) {
			this.move();
		} else {
			this.rollingMove();
		}
		this.chooseCurrentAnimation();
		this.resetMovement();

		this.facade.getGameCamera().centerOnEntity(this);
	}

	private void rollingMove() {
		if (this.previousDirection.getY() < 0) {
			this.setY(this.getPositionY() - this.rollCurrentDistance);
		} else if (this.previousDirection.getY() > 0) {
			this.setY(this.getPositionY() + this.rollCurrentDistance);
		}
		if (this.previousDirection.getX() < 0) {
			this.setX(this.getPositionX() - this.rollCurrentDistance);
		} else if (this.previousDirection.getX() > 0) {
			this.setX(this.getPositionX() + this.rollCurrentDistance);
		}
		this.rollCurrentDistance -= this.rollCurrentDistance * this.ROLLDELTA;
		if (this.rollCurrentDistance < 1) {
			this.rollCurrentDistance = this.ROLLBASEDISTANCE;
			this.isRolling = false;

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
		if (this.facade.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
			this.roll();
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

	private void chooseCurrentAnimation() {
		if (this.isMovingUp()) {
			this.setAnimation(this.playerMoveUp, this.playerSprintUp);
		}
		if (this.isMovingDown()) {
			this.setAnimation(this.playerMoveDown, this.playerSprintDown);
		}
		if (this.isMovingLeft()) {
			this.setAnimation(this.playerMoveLeft, this.playerSprintLeft);
		}
		if (this.isMovingRight()) {
			this.setAnimation(this.playerMoveRight, this.playerSprintRight);
		}
		if (this.isNotMoving()) {
			this.currentAnimation = this.playerIdle;
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
