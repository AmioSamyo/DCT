package DCT.utility;

import java.util.ArrayList;

import DCT.Facade;
import DCT.entity.creature.Enemy;

public class AStar {

	private NodeReader map;
	private Vector start, target, current;
	private Vector path;
	private ArrayList<Node> pathNode;

	public AStar(Facade facade, Enemy enemy, int scale) {
		this.map = new NodeReader(facade, enemy, scale);
		this.start = new Vector(this.map.getColumn() / 2, this.map.getRow() / 2);

		this.target = new Vector();
		this.pathNode = new ArrayList<Node>();
		this.path = new Vector();
		this.current = new Vector();

	}

	public void update(int x, int y, Vector targetPosition) {
		this.map.fillMap(x, y);
		this.updateTarget(targetPosition);
		this.setPath();
	}

	public void setPath() {

		this.pathNode.removeAll(pathNode);

		for (int i = 0; i < this.map.getRow(); i++) {
			for (int j = 0; j < this.map.getColumn(); j++) {
				this.map.getNode(j, i).setHScore(this.target.doubleDistanceVector(new Vector(j, i)));
			}
		}

		this.map.getNode(this.start.getX(), this.start.getY()).setGScore(0);
		this.map.getNode(this.start.getX(), this.start.getY()).setFScore();
		ArrayList<Node> neighbors = new ArrayList<Node>();

		neighbors = this.getNeighbors(this.map.getNode(this.start.getX(), this.start.getY()));
		this.current.setX(this.start.getX());
		this.current.setY(this.start.getY());

		while (neighbors.size() > 0) {
			if (this.current.isEquals(this.target)) {
				break;
			}
			Node currentNode = this.chooseOpenNode(neighbors);
			this.current.setX(currentNode.getX());
			this.current.setY(currentNode.getY());
			this.map.getNode(this.current.getX(), this.current.getY()).isClosed();
			this.pathNode.add(this.map.getNode(this.current.getX(), this.current.getY()));
			neighbors = this.getNeighbors(this.map.getNode(this.current.getX(), this.current.getY()));

		}

	}

	private Node chooseOpenNode(ArrayList<Node> neighbors) {
		Node flag = new Node(0, 0);
		double min = 0;
		for (int i = 0; i < neighbors.size(); i++) {
			double flag1 = this.map.getNode(this.current.getX(), this.current.getY()).getGScore();
			flag1 = flag1
					+ this.current.doubleDistanceVector(new Vector(neighbors.get(i).getX(), neighbors.get(i).getY()));
			this.map.getNode(neighbors.get(i).getX(), neighbors.get(i).getY()).setGScore(flag1);
			this.map.getNode(neighbors.get(i).getX(), neighbors.get(i).getY()).setFScore();
			flag1 = this.map.getNode(neighbors.get(i).getX(), neighbors.get(i).getY()).getFScore();

			neighbors.get(i).setGScore(flag1);
			neighbors.get(i).setFScore();
			if (i == 0) {
				min = neighbors.get(i).getFScore();
				flag = neighbors.get(i);
			} else {
				if (flag1 < min) {
					min = flag1;
					flag = neighbors.get(i);

				}
			}
		}

		return flag;
	}

	private ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> flag = new ArrayList<Node>();

		int xStart = node.getX() - 1;
		int xEnd = xStart + 3;
		if (xStart < 0) {
			xStart = 0;
		}
		if (xEnd > this.map.getColumn()) {
			xEnd = this.map.getColumn();
		}
		int yStart = node.getY() - 1;
		int yEnd = yStart + 3;
		if (yStart < 0) {
			yStart = 0;
		}
		if (yEnd > this.map.getRow()) {
			yEnd = this.map.getRow();
		}

		for (int i = yStart; i < yEnd; i++) {
			for (int j = xStart; j < xEnd; j++) {
				if (i == node.getY() && j == node.getX()) {
					continue;
				}
				if (!this.map.getNode(j, i).isOpen() || !this.map.getNode(j, i).isViable()) {
					continue;
				}
				flag.add(this.map.getNode(j, i));
			}
		}
		return flag;
	}

	private void updateTarget(Vector target) {
		int x = (target.getX() - this.map.getStartPosition().getX()) / this.map.getNodeDimension();
		int y = (target.getY() - this.map.getStartPosition().getY()) / this.map.getNodeDimension();

		if (x > this.map.getColumn()) {
			x = this.map.getColumn();
		}
		if (y > this.map.getRow()) {
			y = this.map.getRow();
		}
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		this.target.setX(x);
		this.target.setY(y);
	}

	public Vector getPath() {
		int x = 0;
		int y = 0;
		if (this.pathNode.size() > 0) {
			int xTile = this.pathNode.get(0).getX();
			int yTile = this.pathNode.get(0).getY();

			if (xTile == this.start.getX() - 1 && yTile == this.start.getY() - 1) {// 1
				x = this.map.getStartPosition().getX() + (this.start.getX()) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX()) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() && yTile == this.start.getY() - 1) {// 2 V
				x = this.map.getStartPosition().getX() + (this.start.getX()) * this.map.getNodeDimension()
						+ this.map.getNodeDimension() / 2;
				y = this.map.getStartPosition().getY() + (this.start.getX()) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY() - 1) {// 3 V
				x = this.map.getStartPosition().getX() + (this.start.getX() + 1) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX()) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY()) {// 4 V
				x = this.map.getStartPosition().getX() + (this.start.getX() + 1) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX()) * this.map.getNodeDimension()
						+ this.map.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY() + 1) {// 5
				x = this.map.getStartPosition().getX() + (this.start.getX() + 1) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX() + 1) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() && yTile == this.start.getY() + 1) {// 6 V
				x = this.map.getStartPosition().getX() + (this.start.getX()) * this.map.getNodeDimension()
						+ this.map.getNodeDimension() / 2;
				y = this.map.getStartPosition().getY() + (this.start.getX() + 1) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() - 1 && yTile == this.start.getY() + 1) {// 7
				x = this.map.getStartPosition().getX() + (this.start.getX()) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX() + 1) * this.map.getNodeDimension();
			}
			if (xTile == this.start.getX() - 1 && yTile == this.start.getY()) {// 8
				x = this.map.getStartPosition().getX() + (this.start.getX()) * this.map.getNodeDimension();
				y = this.map.getStartPosition().getY() + (this.start.getX()) * this.map.getNodeDimension()
						+ this.map.getNodeDimension() / 2;
			}
		} else {
			x = this.map.getStartPosition().getX() + (this.start.getX() + 1) * this.map.getNodeDimension()
					+ this.map.getNodeDimension() / 2;
			y = this.map.getStartPosition().getY() + (this.start.getX() + 1) * this.map.getNodeDimension()
					+ this.map.getNodeDimension() / 2;
		}

		this.path.setX(x);
		this.path.setY(y);

		return this.path;
	}

	public ArrayList<Node> getPathNode() {
		return this.pathNode;
	}

	public NodeReader getMap() {
		return this.map;
	}

	public Vector getStart() {
		return this.start;
	}

	public Vector getTarget() {
		return this.target;
	}

}
