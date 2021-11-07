package DCT.entity;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Player extends Creature {

	private int currentHealth;
	private int speed;
	
	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft, playerIdle;
	private Animation currentAnimation;

	private static final int PLAYERWIDTH = 704 / 11, PLAYERHEIGHT = 320 / 5;
	private static final int SCALE = 2;
	private static final int ANIMATIONSPEED=100;

	public Player(Facade facade, int x, int y) {
		
		super(facade, new Rectangle(x, y, PLAYERWIDTH * SCALE, PLAYERHEIGHT * SCALE));
		this.speed=4;
		
		initialize();
	}

	private void initialize() {
		
		this.playerMoveDown = new Animation(ANIMATIONSPEED, Assets.playerAnimationDown);
		this.playerMoveRight = new Animation(ANIMATIONSPEED, Assets.playerAnimationRight);
		this.playerMoveUp = new Animation(ANIMATIONSPEED, Assets.playerAnimationUp);
		this.playerMoveLeft = new Animation(ANIMATIONSPEED, Assets.playerAnimationLeft);
		this.playerIdle = new Animation(ANIMATIONSPEED, Assets.playerAnimationIdle);

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
