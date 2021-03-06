package DCT.entity.statics;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.gfx.assets.Assets;
import DCT.utility.Rectangle;

public class Jar extends StaticEntity {

	private static final int JARWIDTH = 32, JARHEIGHT = 32, SCALE = 2;

	public Jar(Facade facade, int x, int y) {
		super(facade, x, y, JARWIDTH * SCALE, JARHEIGHT * SCALE);

		this.hitBox = new Rectangle((int) (JARWIDTH * SCALE * 0.4), (int) (JARHEIGHT * SCALE * 0.62),
				(int) (JARWIDTH * SCALE * 0.2), (int) (JARHEIGHT * SCALE * 0.08));

		this.setDebuggingColor(new Color(102, 255, 178, 200));

	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Assets.jar, this.xMoveWithCamera(), this.yMoveWithCamera(), JARWIDTH * SCALE, JARHEIGHT * SCALE,
				null);
		super.render(g);
	}

}
