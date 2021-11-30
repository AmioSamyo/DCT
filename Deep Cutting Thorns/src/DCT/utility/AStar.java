package DCT.utility;

import java.util.ArrayList;

import DCT.Facade;
import DCT.entity.creature.Enemy;

public class AStar {

	NodeReader map;
	Vector start, target, current;
	Vector path;
	ArrayList<Node> pathNode;

	public AStar(Facade facade, Enemy enemy) {
		this.map = new NodeReader(facade, enemy);
		this.start = new Vector(this.map.getColumn() / 2, this.map.getRow() / 2);

		this.target = new Vector();
		this.pathNode = new ArrayList<Node>();
		this.path = new Vector();
		this.current = new Vector();

	}

	public void update(int x, int y, Vector Target) {
		this.map.fillMap(x, y);
		this.updateTarget(target);
		this.setPath();
	}

	public void setPath() {

		this.pathNode.removeAll(pathNode);

		for (int i = 0; i < this.map.getRow(); i++) {
			for (int j = 0; j < this.map.getColumn(); j++) {
				this.map.getNode(j, i).setHScore(this.distance(new Vector(j, i), this.target));
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
			flag1 = flag1 + this.distance(current, new Vector(neighbors.get(i).getX(), neighbors.get(i).getY()));
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

	private double distance(Vector node, Vector target) {
		return Math.sqrt(Math.pow(node.getX() - target.getX(), 2) + Math.pow(node.getY() - target.getY(), 2));
	}

	private void updateTarget(Vector target) {
		int x = 3;
		int y = 6;

		this.target.setX(x);
		this.target.setY(y);
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
