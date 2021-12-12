package DCT.utility;

import java.util.ArrayList;

import DCT.Facade;
import DCT.entity.creature.Enemy;

public class AStar {

	private NodeReader nodeMap;
	private Vector start, target, current;
	private Vector path;
	private ArrayList<Node> pathNodeMap;

	public AStar(Facade facade, Enemy enemy, int scale) {
		this.nodeMap = new NodeReader(facade, enemy, scale);
		this.initialize();
	}

	public void setPath() {
		this.pathNodeMap.clear();
		this.initializeScores();
		this.setCurrentNode(this.start.getX(), this.start.getY());
		this.initializePathNodeMap();
	}

	public void update(int x, int y, Vector targetPosition) {
		this.nodeMap.fillMap(x, y);
		this.updateTarget(targetPosition);
		this.setPath();
	}

	private Node chooseOpenNode(ArrayList<Node> neighbors) {
		Node openNode = new Node(0, 0);
		double min = 0;

		for (int i = 0; i < neighbors.size(); i++) {
			double currentScore = this.updateCurrentScore(neighbors, i);
			this.updateNeighborsScores(neighbors, i, currentScore);

			if (i == 0 || currentScore < min) {
				openNode = this.extractNode(neighbors, i);
				min = (i == 0) ? neighbors.get(i).getFScore() : currentScore;
			}
		}

		return openNode;
	}

	private Node extractNode(ArrayList<Node> neighbors, int index) {
		return neighbors.get(index);
	}

	private ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> flag = new ArrayList<Node>();

		int xStart = node.getX() - 1;
		int xEnd = xStart + 3;
		if (xStart < 0) {
			xStart = 0;
		}
		if (xEnd > this.nodeMap.getColumn()) {
			xEnd = this.nodeMap.getColumn();
		}
		int yStart = node.getY() - 1;
		int yEnd = yStart + 3;
		if (yStart < 0) {
			yStart = 0;
		}
		if (yEnd > this.nodeMap.getRow()) {
			yEnd = this.nodeMap.getRow();
		}

		for (int i = yStart; i < yEnd; i++) {
			for (int j = xStart; j < xEnd; j++) {
				if (i == node.getY() && j == node.getX()) {
					continue;
				}
				if (!this.nodeMap.getNode(j, i).isOpen() || !this.nodeMap.getNode(j, i).isViable()) {
					continue;
				}
				flag.add(this.nodeMap.getNode(j, i));
			}
		}
		return flag;
	}

	private void initialize() {
		int colums = (this.nodeMap.getEnemy().getPositionWidth() / 2) / this.nodeMap.getNodeDimension();
		int rows = (this.nodeMap.getEnemy().getPositionHeight() / 2) / this.nodeMap.getNodeDimension();

		this.start = new Vector(colums + this.nodeMap.getColumn() / 2, rows + this.nodeMap.getRow() / 2);
		this.pathNodeMap = new ArrayList<Node>();
		this.target = new Vector();
		this.path = new Vector();
		this.current = new Vector();
	}

	private void initializePathNodeMap() {
		ArrayList<Node> neighbors = this.getNeighbors(this.nodeMap.getNode(this.start.getX(), this.start.getY()));
		Node currentNode = null;
		while (neighbors.size() > 0) {
			if (this.current.isEquals(this.target))
				break;

			currentNode = this.chooseOpenNode(neighbors);
			this.setCurrentNode(currentNode.getX(), currentNode.getY());
			this.nodeMap.getNode(this.current.getX(), this.current.getY()).isClosed();
			this.pathNodeMap.add(this.nodeMap.getNode(this.current.getX(), this.current.getY()));

			neighbors = this.getNeighbors(this.nodeMap.getNode(this.current.getX(), this.current.getY()));
		}
	}

	private void initializeScores() {
		for (int i = 0; i < this.nodeMap.getRow(); i++) {
			for (int j = 0; j < this.nodeMap.getColumn(); j++) {
				this.nodeMap.getNode(j, i).setHScore(this.target.doubleDistanceVector(new Vector(j, i)));
			}
		}

		this.nodeMap.getNode(this.start.getX(), this.start.getY()).setGScore(0);
		this.nodeMap.getNode(this.start.getX(), this.start.getY()).setFScore();
	}

	private void setCurrentNode(int x, int y) {
		this.current.setX(x);
		this.current.setY(y);
	}

	private double updateCurrentScore(ArrayList<Node> neighbors, int index) {
		double currentScore = this.nodeMap.getNode(this.current.getX(), this.current.getY()).getGScore();

		currentScore = currentScore + this.current
				.doubleDistanceVector(new Vector(neighbors.get(index).getX(), neighbors.get(index).getY()));
		this.nodeMap.getNode(neighbors.get(index).getX(), neighbors.get(index).getY()).setGScore(currentScore);
		this.nodeMap.getNode(neighbors.get(index).getX(), neighbors.get(index).getY()).setFScore();
		currentScore = this.nodeMap.getNode(neighbors.get(index).getX(), neighbors.get(index).getY()).getFScore();

		return currentScore;
	}

	private void updateNeighborsScores(ArrayList<Node> neighbors, int i, double currentScore) {
		neighbors.get(i).setGScore(currentScore);
		neighbors.get(i).setFScore();
	}

	private void updateTarget(Vector target) {
		int x = (target.getX() - this.nodeMap.getStartPosition().getX()) / this.nodeMap.getNodeDimension();
		int y = (target.getY() - this.nodeMap.getStartPosition().getY()) / this.nodeMap.getNodeDimension();

		if (x >= this.nodeMap.getColumn()) {
			x = this.nodeMap.getColumn() - 1;
		}
		if (y >= this.nodeMap.getRow()) {
			y = this.nodeMap.getRow() - 1;
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
		if (this.pathNodeMap.size() > 0) {
			int xTile = this.pathNodeMap.get(0).getX();
			int yTile = this.pathNodeMap.get(0).getY();

			if (xTile == this.start.getX() - 1 && yTile == this.start.getY() - 1) {// 1
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() && yTile == this.start.getY() - 1) {// 2 V
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY() - 1) {// 3 V
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY()) {// 4 V
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() + 1 && yTile == this.start.getY() + 1) {// 5
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() && yTile == this.start.getY() + 1) {// 6 V
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() - 1 && yTile == this.start.getY() + 1) {// 7
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY() + 1) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
			if (xTile == this.start.getX() - 1 && yTile == this.start.getY()) {// 8
				x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
						- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
				y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
						+ this.nodeMap.getNodeDimension() / 2
						+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2;
			}
		} else {
			x = this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
					+ this.nodeMap.getNodeDimension() / 2;
			y = this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
					+ this.nodeMap.getNodeDimension() / 2;
		}

		this.path.setX(x);
		this.path.setY(y);

		return this.path;
	}

	public ArrayList<Node> getPathNode() {
		return this.pathNodeMap;
	}

	public NodeReader getMap() {
		return this.nodeMap;
	}

	public Vector getStart() {
		return this.start;
	}

	public Vector getTarget() {
		return this.target;
	}

}
