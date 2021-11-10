package DCT.entity.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import DCT.Facade;
import DCT.gfx.Assets;

public class Tree extends StaticEntity {
	
	private BufferedImage treeTile;	
	private int x, y;
	private static final int TREEWIDTH = 64, TREEHEIGHT = 96;

	public Tree(Facade facade, int x, int y, int width, int height) {
		super(facade, x, y, width, height);
		
		initialize();
	}
	
	private void initialize(){
		this.treeTile = Assets.tree;
		this.x = 100;
		this.y = 100;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(treeTile, this.x, this.y, TREEWIDTH, TREEHEIGHT, null);
	}

	@Override
	public void die() {
		
	}
}
