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

	public NodeReader getMap() {
		return this.nodeMap;
	}

	public Vector getPath() {
		Vector newPos = new Vector();

		if (this.pathNodeMap.size() > 0) {
			this.choosePathDirection(newPos);
		} else {
			this.setXCenter(newPos);
			this.setYCenter(newPos);
		}

		this.updatePath(newPos.getX(), newPos.getY());
		return this.path;
	}

	public ArrayList<Node> getPathNode() {
		return this.pathNodeMap;
	}

	public Vector getStart() {
		return this.start;
	}

	public Vector getTarget() {
		return this.target;
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

	private int checkHorizontalEdges(int x) {
		if (x >= this.nodeMap.getColumn()) {
			x = this.nodeMap.getColumn() - 1;
		}
		if (x < 0) {
			x = 0;
		}
		return x;
	}

	private int checkVerticalEdges(int y) {
		if (y >= this.nodeMap.getRow()) {
			y = this.nodeMap.getRow() - 1;
		}
		if (y < 0) {
			y = 0;
		}
		return y;
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
		ArrayList<Node> neighbors = new ArrayList<Node>();
		Vector xStartEnd = this.horizontalNeighborsIndexes(node);
		Vector yStartEnd = verticalNeighborsIndexes(node);

		for (int i = yStartEnd.getX(); i < yStartEnd.getY(); i++) {
			for (int j = xStartEnd.getX(); j < xStartEnd.getY(); j++) {
				if (!this.isNodeValid(node, j, i)) {
					continue;
				}
				neighbors.add(this.nodeMap.getNode(j, i));
			}
		}
		return neighbors;
	}

	private Vector horizontalNeighborsIndexes(Node node) {
		Vector xStartEnd = new Vector(node.getX() - 1, node.getX() + 2);
		if (xStartEnd.getX() < 0) {
			xStartEnd.setX(0);
		}
		if (xStartEnd.getY() > this.nodeMap.getColumn()) {
			xStartEnd.setY(this.nodeMap.getColumn());
		}
		return xStartEnd;
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

	private boolean isCurrentNode(Node node, int x, int y) {
		return y == node.getY() && x == node.getX();
	}

	private boolean isNodeAvailable(int x, int y) {
		return this.nodeMap.getNode(x, y).isOpen() && this.nodeMap.getNode(x, y).isViable();
	}

	private boolean isNodeValid(Node node, int x, int y) {
		return !this.isCurrentNode(node, x, y) && this.isNodeAvailable(x, y);
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

	private void updateNeighborsScores(ArrayList<Node> neighbors, int index, double currentScore) {
		neighbors.get(index).setGScore(currentScore);
		neighbors.get(index).setFScore();
	}

	private void updateTarget(Vector target) {
		int x = (target.getX() - this.nodeMap.getStartPosition().getX()) / this.nodeMap.getNodeDimension();
		int y = (target.getY() - this.nodeMap.getStartPosition().getY()) / this.nodeMap.getNodeDimension();

		x = this.checkHorizontalEdges(x);
		y = this.checkVerticalEdges(y);

		this.updateTargetVector(x, y);
	}

	private void updateTargetVector(int x, int y) {
		this.target.setX(x);
		this.target.setY(y);
	}

	private void updatePath(int x, int y) {
		this.path.setX(x);
		this.path.setY(y);
	}

	private void choosePathDirection(Vector nextPosVector) {
		Vector tilePos = new Vector(this.pathNodeMap.get(0).getX(), this.pathNodeMap.get(0).getY());

		if (tilePos.getX() == this.start.getX() - 1) {
			this.setXLeft(nextPosVector);
		} else if (tilePos.getX() == this.start.getX()) {
			this.setXCenter(nextPosVector);
		} else if (tilePos.getX() == this.start.getX() + 1) {
			this.setXRight(nextPosVector);
		}

		if (tilePos.getY() == this.start.getY() - 1) {
			this.setYTop(nextPosVector);
		} else if (tilePos.getY() == this.start.getY()) {
			this.setYCenter(nextPosVector);
		} else if (tilePos.getY() == this.start.getY() + 1) {
			this.setYBot(nextPosVector);
		}
	}

	private void setYBot(Vector pos) {
		pos.setY(this.nodeMap.getStartPosition().getY() + (this.start.getY() + 1) * this.nodeMap.getNodeDimension()
				+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2);
	}

	private void setXCenter(Vector pos) {
		pos.setX(this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
				+ this.nodeMap.getNodeDimension() / 2);
	}

	private void setYCenter(Vector pos) {
		pos.setY(this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
				+ this.nodeMap.getNodeDimension() / 2);
	}

	private void setXLeft(Vector pos) {
		pos.setX(this.nodeMap.getStartPosition().getX() + (this.start.getX()) * this.nodeMap.getNodeDimension()
				- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2);
	}

	private void setXRight(Vector pos) {
		pos.setX(this.nodeMap.getStartPosition().getX() + (this.start.getX() + 1) * this.nodeMap.getNodeDimension()
				+ this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2);
	}

	private void setYTop(Vector pos) {
		pos.setY(this.nodeMap.getStartPosition().getY() + (this.start.getY()) * this.nodeMap.getNodeDimension()
				- this.nodeMap.SCALE * this.nodeMap.getNodeDimension() / 2);
	}

	private Vector verticalNeighborsIndexes(Node node) {
		Vector yStartEnd = new Vector(node.getY() - 1, node.getY() + 2);
		if (yStartEnd.getX() < 0) {
			yStartEnd.setX(0);
		}
		if (yStartEnd.getY() > this.nodeMap.getRow()) {
			yStartEnd.setY(this.nodeMap.getRow());
		}
		return yStartEnd;
	}
}
