package DCT.entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;

public class Tree extends StaticEntity {

	private int x, y;
	private static final int TREEWIDTH = 64, TREEHEIGHT = 96, SCALE = 3;

	public Tree(Facade facade, int x, int y) {
		super(facade, x, y, TREEWIDTH * SCALE, TREEHEIGHT * SCALE);

		this.hitBox = new Rectangle((int) (TREEWIDTH * SCALE * 0.4), (int) (TREEHEIGHT * SCALE * 0.5),
				(int) (TREEWIDTH * SCALE * 0.2), (int) (TREEHEIGHT * SCALE * 0.5));

		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, this.x, this.y, TREEWIDTH * SCALE, TREEHEIGHT * SCALE, null);
		super.render(g);
	}

	@Override
	public void die() {

	}
}
