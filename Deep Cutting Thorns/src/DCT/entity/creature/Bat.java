package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Bat extends Creature {

	private Animation batUp, batLeft, batDown, batRight;
	private Animation currentAnimation;

	private final static int BATHEIGHT = 32;
	private final static int BATWIDTH = 24;
	private final static int SCALE = 2;
	private static final int ANIMATIONSPEED = 100;

	public Bat(Facade facade, int x, int y) {
		super(facade, new Rectangle(x, y, BATWIDTH, BATHEIGHT));

		this.initialize();
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (BATWIDTH * SCALE * 0.3), (int) (BATHEIGHT * SCALE * 0.3),
				(int) (BATWIDTH * SCALE * 0.5), (int) (BATHEIGHT * SCALE * 0.5));

		this.setDebuggingColor(new Color(255, 255, 51));
		this.DiameterAggro = 400;

		this.batDown = new Animation(ANIMATIONSPEED, Assets.batAnimationDown);
		this.batLeft = new Animation(ANIMATIONSPEED, Assets.batAnimationLeft);
		this.batUp = new Animation(ANIMATIONSPEED, Assets.batAnimationUp);
		this.batRight = new Animation(ANIMATIONSPEED, Assets.batAnimationRight);

		this.currentAnimation = this.batDown;
	}

	@Override
	public void update() {

		if (this.health <= 0) {
			this.die();
		}

		this.batDown.update();
		this.batUp.update();
		this.batLeft.update();
		this.batRight.update();

		this.playerInAggro();
		this.moveToPlayer();

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				BATWIDTH * SCALE, BATHEIGHT * SCALE, null);

		super.render(g);
		this.drawRangeAggro(g);

	}

}
