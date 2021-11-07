package DCT.entity;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Player extends Creature {

	private int currentHealth;
	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft, playerIdle;

	private Animation currentAnimation;

	public Player(Facade facade, Rectangle position) {
		super(facade, position);
		initialize();
	}

	private void initialize() {
		this.playerMoveDown = new Animation(100, Assets.playerAnimationDown);
		this.playerMoveRight = new Animation(100, Assets.playerAnimationRight);
		this.playerMoveUp = new Animation(100, Assets.playerAnimationUp);
		this.playerMoveLeft = new Animation(100, Assets.playerAnimationLeft);
		this.playerIdle = new Animation(100, Assets.playerAnimationIdle);

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

		this.currentAnimation.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.currentAnimation.getCurrentFrame(), this.position.getX(), this.position.getY(),
					this.position.getWidth(), this.position.getHeight(), null);
	}

	@Override
	public void die() {

	}

	public int getCurrentHealth() {
		return this.currentHealth;
	}

}
