package DCT.utility;

import DCT.Facade;
import DCT.entity.creature.Enemy;
import DCT.tile.Tile;

public class NodeReader {

	private Node[][] map;
	private Enemy currentEntity;
	private int column;
	private int row;
	private int speed, rangeView;
	private Facade facade;
	private Vector startPosition;

	public NodeReader(Facade facade, Enemy currentEntity) {

		this.facade = facade;
		this.currentEntity = currentEntity;
		this.speed = this.currentEntity.getSpeed();
		this.rangeView = this.currentEntity.getDiameterAggro();

		this.startPosition = new Vector();

		this.column = this.rangeView / this.speed;
		this.row = this.rangeView / this.speed;

		this.map = new Node[this.column][this.row];

		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.column; j++) {
				this.map[j][i] = new Node(false, true);
			}
		}
	}

	public void fillMap(int x, int y) {
		this.setVector(x, y);
		this.mapCheckSolid();
		this.mapCheckEntity();
	}

	public void mapCheckSolid() {
		for (int k = 0; k < this.row; k++) {
			for (int j = 0; j < this.column; j++) {
				int xTile = (this.startPosition.getX() + j * 3) / Tile.TILEWIDTH;
				int yTile = (this.startPosition.getY() + k * 3) / Tile.TILEHEIGHT;
				if (this.startPosition.getX() + j * this.speed < 0 || this.startPosition.getY() + k * this.speed < 0) {
					continue;
				}
				if (Tile.tiles[this.facade.getWorld().getTiles()[xTile][yTile]].isSolid()) {
					this.map[j][k].setViable(false);
				}
			}
		}
	}

	public void mapCheckEntity() {

		for (int i = 0; i < this.facade.getEntityManager().getEntityList().size(); i++) {
			if (this.facade.getEntityManager().getEntityList().get(i) == this.facade.getEntityManager().getPlayer()) {
				continue;
			}
			if (this.facade.getEntityManager().getEntityList().get(i) == this.currentEntity) {
				continue;
			}

			Rectangle hitbox = this.facade.getEntityManager().getEntityList().get(i).getCollisionHitBox(0, 0);

			if (hitbox.getX() + hitbox.getWidth() < this.startPosition.getX()
					|| hitbox.getX() > this.startPosition.getX() + this.rangeView
					|| hitbox.getY() + hitbox.getHeight() < this.startPosition.getY()
					|| hitbox.getY() > this.startPosition.getY() + this.rangeView) {
				continue;
			}

			int xStart = (hitbox.getX() - this.startPosition.getX()) / this.speed;
			int yStart = (hitbox.getY() - this.startPosition.getY()) / this.speed;
			if (hitbox.getX() < this.startPosition.getX()) {
				xStart = 0;
			}
			if (hitbox.getY() < this.startPosition.getY()) {
				yStart = 0;
			}

			int xEnd = (hitbox.getX() - this.startPosition.getX() + hitbox.getWidth()) / this.speed;
			int yEnd = (hitbox.getY() - this.startPosition.getY() + hitbox.getHeight()) / this.speed;
			if (xEnd > this.column) {
				xEnd = this.column;
			}
			if (yEnd > this.row) {
				yEnd = this.row;
			}

			for (int k = yStart; k < yEnd; k++) {
				for (int j = xStart; j < xEnd; j++) {
					this.map[j][k].setViable(false);
				}
			}
		}

	}

	public void mapRemoveEntity() {
		for (int k = 0; k < this.row; k++) {
			for (int j = 0; j < this.column; j++) {
				this.map[j][k].setViable(true);
			}
		}
	}

	private void setVector(int x, int y) {
		this.startPosition.setX(x);
		this.startPosition.setY(y);
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

	public Vector getStartPosition() {
		return this.startPosition;
	}

	public int getDimension() {
		return this.rangeView;

	}

	public int getNodeDimension() {
		return this.speed;
	}
}
