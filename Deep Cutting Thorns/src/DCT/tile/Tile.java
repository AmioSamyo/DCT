package DCT.tile;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Tile {

	private int id = 0;

	private BufferedImage texture = null;

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
	}

	public void update() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(this.texture, x, y, null);
	}

}
