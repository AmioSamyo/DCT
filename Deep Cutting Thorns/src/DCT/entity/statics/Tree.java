package DCT.entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import DCT.Facade;
import DCT.gfx.Assets;

public class Tree extends StaticEntity {

	private BufferedImage treeTile;
	private int x, y;
	private static final int TREEWIDTH = 64, TREEHEIGHT = 96, SCALE = 2;

	public Tree(Facade facade, int x, int y) {
		super(facade, x, y, TREEWIDTH, TREEHEIGHT);

		this.treeTile = Assets.tree;
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(treeTile, this.x, this.y, TREEWIDTH * SCALE, TREEHEIGHT * SCALE, null);
	}

	@Override
	public void die() {

	}
}
