package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Bat extends Enemy {

	private final static int BATHEIGHT = 32;
	private final static int BATWIDTH = 24;
	private final static int SCALE = 2;
	private static final int ANIMATIONSPEED = 150;

	public Bat(Facade facade, int x, int y) {
		super(facade, new Rectangle(x, y, BATWIDTH, BATHEIGHT));

		this.initialize();
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (BATWIDTH * SCALE * 0.3), (int) (BATHEIGHT * SCALE * 0.3),
				(int) (BATWIDTH * SCALE * 0.5), (int) (BATHEIGHT * SCALE * 0.5));

		this.setDebuggingColor(new Color(255, 255, 51));
		this.DiameterAggro = 400;

		this.animationMoveDown = new Animation(ANIMATIONSPEED, Assets.batAnimationDown);
		this.animationMoveLeft = new Animation(ANIMATIONSPEED, Assets.batAnimationLeft);
		this.animationMoveUp = new Animation(ANIMATIONSPEED, Assets.batAnimationUp);
		this.animationMoveRight = new Animation(ANIMATIONSPEED, Assets.batAnimationRight);
		this.animationIdle = this.animationMoveDown;

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
	public void render(Graphics g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				BATWIDTH * SCALE, BATHEIGHT * SCALE, null);

		super.render(g);

	}

}
