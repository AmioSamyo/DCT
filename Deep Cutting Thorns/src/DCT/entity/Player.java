package DCT.entity;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Player extends Creature {

	private int currentHealth;
	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft, playerIdle;

	public Player(Facade facade, Rectangle position) {
		super(facade, position);
		initialize();
	}

	private void initialize() {
		this.playerMoveDown = new Animation(speed, Assets.playerAnimationDown);
		this.playerMoveRight = new Animation(speed, Assets.playerAnimationRight);
		this.playerMoveUp = new Animation(speed, Assets.playerAnimationUp);
		this.playerMoveLeft = new Animation(speed, Assets.playerAnimationLeft);
		this.playerIdle = new Animation(speed, Assets.playerAnimationIdle);
	}

	@Override
	public void update() {
		if (currentHealth <= 0) {
			die();
		}

		if (xMove == 0 && yMove == 0) {
			this.playerIdle.update();
		}

		if (yMove > 0) {
			this.playerMoveUp.update();
		}

		if (xMove > 0) {
			this.playerMoveRight.update();
		}

		if (xMove < 0) {
			this.playerMoveLeft.update();
		}

		if (yMove < 0) {
			this.playerMoveDown.update();
		}
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	@Override
	public void die() {
		
	}
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}

}
