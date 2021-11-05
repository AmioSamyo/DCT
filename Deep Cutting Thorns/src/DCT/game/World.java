package DCT.game;

import java.awt.Graphics;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.Utils;

public class World {

	private int[][] tiles;
	private int columns, rows;

	public World(String path) {
		this.loadWorld(path);
	}

	public void render(Graphics g) {
		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.columns; x++) {
				Tile.tiles[this.tiles[x][y]].render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGTH);
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

}
