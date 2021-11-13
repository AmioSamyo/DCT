package DCT.entity;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Player extends Creature {

	private int currentHealth;

	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft, playerIdle;
	private Animation currentAnimation;

	private static final int PLAYERWIDTH = 704 / 11, PLAYERHEIGHT = 320 / 5;
	private static final int SCALE = 2;
	private static final int ANIMATIONSPEED = 100;

	public Player(Facade facade, int x, int y) {

		super(facade, new Rectangle(x, y, PLAYERWIDTH * SCALE, PLAYERHEIGHT * SCALE));
		this.speed = 4;

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

		this.currentAnimation = this.playerIdle;
		this.setDebuggingColor(Color.PINK);
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
		this.move();
		this.chooseCurrentAnimation();
		this.resetMovement();

		this.facade.getGameCamera().centerOnEntity(this);
	}

	private void getInput() {
		if (this.facade.getKeyManager().getUp()) {
			this.addYMove(-this.speed);
		}
		if (this.facade.getKeyManager().getDown()) {
			this.addYMove(this.speed);
		}
		if (this.facade.getKeyManager().getLeft()) {
			this.addXMove(-this.speed);
		}
		if (this.facade.getKeyManager().getRight()) {
			this.addXMove(this.speed);
		}
	}

	private void chooseCurrentAnimation() {
		if (this.xMove < 0) {
			this.currentAnimation = this.playerMoveLeft;
		} else if (this.xMove > 0) {
			this.currentAnimation = this.playerMoveRight;
		} else if (this.yMove < 0) {
			this.currentAnimation = this.playerMoveUp;
		} else if (this.yMove > 0) {
			this.currentAnimation = this.playerMoveDown;
		} else {
			this.currentAnimation = this.playerIdle;
		}
	}

}
