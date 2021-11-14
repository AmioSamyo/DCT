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

		this.hitBox = new Rectangle((int) (this.position.getX()), (int) ( this.position.getY()),
				(int) (BATWIDTH * SCALE ), (int) (BATHEIGHT * SCALE ));
		
		this.setDebuggingColor(new Color(51, 255, 255));

		this.batUp = new Animation(ANIMATIONSPEED, Assets.batAnimationUp);
		this.batLeft = new Animation(ANIMATIONSPEED, Assets.batAnimationLeft);
		this.batDown = new Animation(ANIMATIONSPEED, Assets.batAnimationDown);
		this.batRight = new Animation(ANIMATIONSPEED, Assets.batAnimationRight);

		this.currentAnimation = this.batUp;
	}

	@Override
	public void update() {
		this.batUp.update();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.batUp.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(), BATWIDTH * SCALE,
				BATHEIGHT * SCALE, null);
	
	}

}
