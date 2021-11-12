package DCT.game;

import java.awt.Graphics;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.Utils;

public class World {

	private int[][] tiles;
	private int columns, rows;

	private Facade facade;

	public World(String path, Facade facade) {
		this.loadWorld(path);

		this.facade = facade;
	}

	public void render(Graphics g) {

		int xStart = 0, yStart = 0;
		int xEnd = this.columns, yEnd = this.rows;

		xStart = (int) Math.max(0, this.getXOffsetInTile());
		xEnd = (int) Math.min(this.columns, this.getXOffsetInTile() + 1 + this.facade.getWidth() / Tile.TILEWIDTH);

		yStart = (int) Math.max(0, this.getYOffsetInTile());
		yEnd = (int) Math.min(this.rows, this.getYOffsetInTile() + 1 + this.facade.getHeight() / Tile.TILEHEIGHT);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				Tile.tiles[this.tiles[x][y]].render(g, x * Tile.TILEWIDTH - this.facade.getGameCamera().getXOffset(),
						y * Tile.TILEHEIGHT - this.facade.getGameCamera().getYOffset());
			}
		}
	}

	public void update() {
		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.columns; x++) {
				Tile.tiles[this.tiles[x][y]].update();
			}
		}
	}

	public int getColumns() {
		return this.columns;
	}

	public int getRows() {
		return this.rows;
	}

	public int[][] getTiles() {
		return this.tiles;
	}

	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		int toBeSkipped = 2;

		this.columns = Utils.parseInt(tokens[0]);
		this.rows = Utils.parseInt(tokens[1]);
		this.tiles = new int[this.columns][this.rows];

		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.columns; x++) {
				this.tiles[x][y] = Utils.parseInt(tokens[(x + (y * this.columns)) + toBeSkipped]);
			}
		}
	}

	private int getXOffsetInTile() {
		return this.facade.getGameCamera().getXOffset() / Tile.TILEWIDTH;
	}

	private int getYOffsetInTile() {
		return this.facade.getGameCamera().getYOffset() / Tile.TILEHEIGHT;
	}

}
