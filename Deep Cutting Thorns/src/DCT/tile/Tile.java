package DCT.tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

	public static final int TILEWIDTH = 0;
	public static final int TILEHEIGTH = 0;
	public static Tile[] tiles = new Tile[256];

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
		g.drawImage(this.texture, x, y, null);
	}

	public int getId() {
		return this.id;
	}

	public boolean isSolid() {
		return this.solid;
	}

}