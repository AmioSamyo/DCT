package DCT.entity.statics;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Rocks extends StaticEntity {

	private static final int ROCKSWIDTH = 33, ROCKSHEIGHT = 32, SCALE = 2;

	public Rocks(Facade facade, int x, int y) {
		super(facade, x, y, ROCKSWIDTH * SCALE, ROCKSHEIGHT * SCALE);

		this.hitBox = new Rectangle((int) (ROCKSWIDTH * SCALE * 0.2), (int) (ROCKSHEIGHT * SCALE * 0.2),
				(int) (ROCKSWIDTH * SCALE * 0.6), (int) (ROCKSHEIGHT * SCALE * 0.6));
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rocks, this.xMoveWithCamera(), this.yMoveWithCamera(), ROCKSWIDTH * SCALE, ROCKSHEIGHT * SCALE,
				null);
		super.render(g);
	}

	@Override
	public void die() {

	}

}
