package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Bat extends Enemy {

	private final static int BATHEIGHT = 32;
	private final static int BATWIDTH = 24;
	private final static int SCALE = 2;
	private static final int ANIMATIONSPEED = 150;

	public Bat(Facade facade, Vector position) {
		super(facade, new Rectangle(position.getX(), position.getY(), SCALE * BATWIDTH, SCALE * BATHEIGHT), 3, 400);

		this.initialize();
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (BATWIDTH * SCALE * 0.3), (int) (BATHEIGHT * SCALE * 0.3),
				(int) (BATWIDTH * SCALE * 0.5), (int) (BATHEIGHT * SCALE * 0.5));

		this.setDebuggingColor(new Color(255, 255, 51, 155));

		this.animationMoveDown = new Animation(ANIMATIONSPEED, Assets.batAnimationDown);
		this.animationMoveLeft = new Animation(ANIMATIONSPEED, Assets.batAnimationLeft);
		this.animationMoveUp = new Animation(ANIMATIONSPEED, Assets.batAnimationUp);
		this.animationMoveRight = new Animation(ANIMATIONSPEED, Assets.batAnimationRight);
		this.animationIdle = this.animationMoveDown;
		this.animationDead = new Animation(ANIMATIONSPEED, Assets.batAnimationDead);

		this.currentAnimation = this.animationMoveDown;

		this.rangeOfAttack = this.speed;

	}

	@Override
	public void update() {

		if (this.health <= 0) {
			this.die();
		}

		this.animationMoveDown.update();
		this.animationMoveUp.update();
		this.animationMoveLeft.update();
		this.animationMoveRight.update();

		this.currentAnimation.update();

		super.update();

	}

	@Override
	public void render(Graphics2D g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				BATWIDTH * SCALE, BATHEIGHT * SCALE, null);

		super.render(g);

	}

	@Override
	public void die() {
		this.currentAnimation = this.animationDead;
		this.alive = false;
	}

}
