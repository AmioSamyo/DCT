package DCT;

import java.awt.Graphics;

public class World {
	
	private int[][] tiles;
	private int width, height;
	
	public World(String path) {
		this.loadWorld(path);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		
		this.width = Utils.parseInt(tokens[0]);
		this.height = Utils.parseInt(tokens[1]);
		this.tiles = new int[this.width][this.height];
		
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * this.width) + 4]);
			}
		}
	}

}
