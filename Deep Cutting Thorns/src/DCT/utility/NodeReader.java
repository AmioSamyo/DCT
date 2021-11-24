package DCT.utility;

import java.util.ArrayList;

import DCT.Facade;
import DCT.tile.Tile;

public class NodeReader {

	private Node[][] map;
	private int column;
	private int row;
	private int speed;
	private Facade facade;

	public NodeReader(int speed, Facade facade) {
		this.speed = speed;
		this.facade = facade;

		this.column = (76 * 32) / this.speed;
		this.row = (31 * 32) / this.speed;

		this.map = new Node[this.column][this.row];

	}

	public void mapFill() {

		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.column; j++) {
				boolean flagOpen = true;
				boolean flagViable = true;

				if (Tile.tiles[this.facade.getWorld().getTiles()[(j / 32) * this.speed][(i / 32) * this.speed]]
						.isSolid()) {
					flagViable = false;
				}
				for (int k = 0; k < this.facade.getEntityManager().getEntityList().size(); k++) {
					int x = j * this.speed;
					int y = i * this.speed;

					if (this.facade.getEntityManager().getEntityList().get(k).getCollisionHitBox(0, 0).contains(x, y)) {
						flagViable = false;
						break;
					}
				}

				this.map[j][i] = new Node(flagOpen, flagViable);
			}
		}

	}

	public int getColumn() {
		return this.column;
	}

	public int getRow() {
		return this.row;
	}

	public Node[][] getMap() {
		return this.map;
	}

	public Node getNode(int i, int j) {
		return this.map[i][j];
	}
}
