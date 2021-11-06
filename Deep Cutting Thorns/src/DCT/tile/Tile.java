package DCT.tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

	public static final int TILEWIDTH = 64;
	public static final int TILEHEIGHT = 64;
	public static Tile[] tiles = new Tile[256];
	public static Tile wallTile = new WallTile(3);

	private int id = 0;
	private boolean solid = false;

	private BufferedImage texture = null;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}

	public void update() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(this.texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}

	public int getId() {
		return this.id;
	}

	public boolean isSolid() {
		return this.solid;
	}

}
