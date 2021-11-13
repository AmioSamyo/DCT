package DCT.tile;

import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {

	public static final int TILEWIDTH = 64;
	public static final int TILEHEIGHT = 64;
	public static Tile[] tiles = new Tile[256];

	public static Tile grassTile = new GrassTile(0);
	public static Tile flowerGrassTile = new FlowerGrassTile(1);
	public static Tile rockGrassTile = new RockGrassTile(2);
	public static Tile wallTile = new WallTile(3);
	public static Tile upperColumns = new UpperColumnsTile(4);
	public static Tile upperWall = new UpperWallTile(5);
	public static Tile downWall = new DownWallTile(6);

	private int id = 0;
	private boolean solid = false;
	private Color debuggingColor = Color.WHITE;

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
		//this.drawDebugging(g, x, y);
	}

	public int getId() {
		return this.id;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public void drawDebugging(Graphics g, int x, int y) {
		if (this.isSolid()) {
			g.setColor(this.debuggingColor);
			g.fillRect(x, y, Tile.TILEWIDTH-10, Tile.TILEHEIGHT-10);
		}
	}

	protected void setDebuggingColor(Color debuggingColor) {
		this.debuggingColor = debuggingColor;
	}

}
