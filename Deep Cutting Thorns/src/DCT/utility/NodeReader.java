package DCT.utility;

import DCT.Facade;
import DCT.entity.creature.Enemy;
import DCT.tile.Tile;

public class NodeReader {

	private Node[][] map;
	private Enemy currentEntity;
	private Vector dimension;
	private int nodeDimension, rangeView;
	private Facade facade;
	private Vector startPosition;
	public final int SCALE;

	public NodeReader(Facade facade, Enemy currentEntity, int scale) {
		SCALE = scale;
		this.facade = facade;
		this.currentEntity = currentEntity;
		this.nodeDimension = Math.max(this.currentEntity.getPositionWidth(), this.currentEntity.getPositionHeight())
				/ SCALE;

		this.rangeView = this.currentEntity.getDiameterAggro();

		this.startPosition = new Vector();
		this.dimension = new Vector();

		this.dimension.setX(this.rangeView / this.nodeDimension);
		if (this.dimension.getX() % 2 == 0) {
			this.dimension.setX(this.dimension.getX() + 1);
			this.rangeView += this.nodeDimension;
		}

		this.dimension.setY(this.rangeView / this.nodeDimension);
		if (this.dimension.getY() % 2 == 0) {
			this.dimension.setY(this.dimension.getY() + 1);
		}

		this.map = new Node[this.dimension.getX()][this.dimension.getY()];

		for (int i = 0; i < this.dimension.getY(); i++) {
			for (int j = 0; j < this.dimension.getX(); j++) {
				this.map[j][i] = new Node(j, i);
			}
		}
	}

	public void fillMap(int x, int y) {
		this.setVectorStart(x, y);
		this.mapCheckSolid();
		this.mapCheckEntity();
	}

	public void mapCheckSolid() {
		for (int k = 0; k < this.dimension.getY(); k++) {
			for (int j = 0; j < this.dimension.getX(); j++) {
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
					this.dimension.getX());
			int yEnd = Math.min(
					((hitbox.getY() - this.startPosition.getY() + hitbox.getHeight()) / this.nodeDimension) + 1,
					this.dimension.getY());
			if (xEnd > this.dimension.getX()) {
				xEnd = this.dimension.getX();
			}
			if (yEnd > this.dimension.getY()) {
				yEnd = this.dimension.getY();
			}

			for (int k = yStart; k < yEnd; k++) {
				for (int j = xStart; j < xEnd; j++) {
					this.map[j][k].setViable(false);
				}
			}
		}

	}

	public void mapRemoveEntity() {
		for (int k = 0; k < this.dimension.getY(); k++) {
			for (int j = 0; j < this.dimension.getX(); j++) {
				this.map[j][k].setViable(true);
				this.map[j][k].setOpen(true);
				this.map[j][k].setGScore(0);
				this.map[j][k].setHScore(0);
				this.map[j][k].setFScore();
			}
		}
	}

	private void setVectorStart(int x, int y) {
		this.startPosition.setX(x);
		this.startPosition.setY(y);
	}

	public Enemy getEnemy() {
		return this.currentEntity;
	}

	public int getColumn() {
		return this.dimension.getX();
	}

	public int getRow() {
		return this.dimension.getY();
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
