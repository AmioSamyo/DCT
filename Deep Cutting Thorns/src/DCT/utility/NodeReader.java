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

		this.column = (76 * Tile.TILEWIDTH) / this.speed;
		this.row = (31 * Tile.TILEHEIGHT) / this.speed;

		this.map = new Node[this.column][this.row];
		
		this.mapFillSolid();

	}

	public void mapFillSolid() {

		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.column; j++) {
				this.map[j][i] = new Node(true, true);
			}
		}
	}

	public void mapCheckEntity() {
		for (int i = 0; i < this.facade.getEntityManager().getEntityList().size(); i++) {

			Rectangle flag = new Rectangle(this.facade.getEntityManager().getEntityList().get(i).getPositionX(),
					this.facade.getEntityManager().getEntityList().get(i).getPositionY(),
					this.facade.getEntityManager().getEntityList().get(i).getPositionWidth(),
					this.facade.getEntityManager().getEntityList().get(i).getPositionHeight());

			int columnRect = flag.getX() / 3;
			int rowRect = flag.getY() / 3;
			int widthRect = flag.getWidth() / 3;
			int heightRect = flag.getHeight() / 3;

			for (int j = rowRect; j < heightRect; j++) {
				for (int k = columnRect; k < widthRect; k++) {
					this.map[k][j].setViable(false);
				}

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
