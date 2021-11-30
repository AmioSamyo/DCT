package DCT.utility;

import DCT.Facade;
import DCT.entity.creature.Enemy;
import DCT.tile.Tile;

public class NodeReader {

	private Node[][] map;
	private Enemy currentEntity;
	private int column;
	private int row;
	private int nodeDimension, rangeView;
	private Facade facade;
	private Vector startPosition;

	public NodeReader(Facade facade, Enemy currentEntity) {

		this.facade = facade;
		this.currentEntity = currentEntity;
		this.nodeDimension = Math.max(this.currentEntity.getPositionWidth(), this.currentEntity.getPositionHeight());
		this.rangeView = this.currentEntity.getDiameterAggro();

		this.startPosition = new Vector();

		this.column = this.rangeView / this.nodeDimension;
		if (this.column % 2 == 0) {
			this.column += 1;
			this.rangeView += this.nodeDimension;
		}
		if (this.row % 2 == 0) {
			this.row += 1;
		}
		this.row = this.rangeView / this.nodeDimension;

		this.map = new Node[this.column][this.row];

		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.column; j++) {
				this.map[j][i] = new Node(j, i);
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
				int xTile = (this.startPosition.getX() + j * this.nodeDimension) / Tile.TILEWIDTH;
				int yTile = (this.startPosition.getY() + k * this.nodeDimension) / Tile.TILEHEIGHT;
				if (this.startPosition.getX() + j * this.nodeDimension < 0
						|| this.startPosition.getY() + k * this.nodeDimension < 0) {
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

			int xStart = Math.max(((hitbox.getX() - this.startPosition.getX()) / this.nodeDimension), 0);
			int yStart = Math.max(((hitbox.getY() - this.startPosition.getY()) / this.nodeDimension), 0);
			if (hitbox.getX() < this.startPosition.getX()) {
				xStart = 0;
			}
			if (hitbox.getY() < this.startPosition.getY()) {
				yStart = 0;
			}

			int xEnd = Math.min(
					((hitbox.getX() - this.startPosition.getX() + hitbox.getWidth()) / this.nodeDimension) + 1,
					this.column);
			int yEnd = Math.min(
					((hitbox.getY() - this.startPosition.getY() + hitbox.getHeight()) / this.nodeDimension) + 1,
					this.row);
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
				this.map[j][k].setOpen(true);
				this.map[j][k].setGScore(0);
				this.map[j][k].setHScore(0);
				this.map[j][k].setFScore();
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
		return this.nodeDimension;
	}
}
