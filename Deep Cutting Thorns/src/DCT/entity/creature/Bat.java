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

	private int rangeAggro;
	private boolean playerInAggro;

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

		this.batDown = new Animation(ANIMATIONSPEED, Assets.batAnimationDown);
		this.batLeft = new Animation(ANIMATIONSPEED, Assets.batAnimationLeft);
		this.batUp = new Animation(ANIMATIONSPEED, Assets.batAnimationUp);
		this.batRight = new Animation(ANIMATIONSPEED, Assets.batAnimationRight);

		this.rangeAggro = 400;

		this.currentAnimation = this.batDown;
	}

	@Override
	public void update() {

		this.batDown.update();
		this.batUp.update();
		this.batLeft.update();
		this.batRight.update();

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				BATWIDTH * SCALE, BATHEIGHT * SCALE, null);

		super.render(g);
		if(this.facade.getDebugMode()) {
		this.drawRangeAggro(g);
		}

	}

	private void drawRangeAggro(Graphics g) {

		Rectangle StartBatEye = new Rectangle((int) (this.position.getX() - this.rangeAggro / 2 + BATWIDTH / 2),
				(int) (this.position.getY() - this.rangeAggro / 2 + BATHEIGHT / 2), 0, 0);

		g.drawOval(this.getXMoveHitbox(StartBatEye), this.getYMoveHitbox(StartBatEye), this.rangeAggro,
				this.rangeAggro);
	}

}
