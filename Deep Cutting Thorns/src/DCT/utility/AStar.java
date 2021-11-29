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
			if(this.current.isEquals(target)) {
				break;
			}
			Node currentNode=this.chooseOpenNode(neighbors);
			this.current.setX(currentNode.getX());
			this.current.setY(currentNode.getY());
			this.map.getNode(this.current.getX(), this.current.getY()).isClosed();
			this.pathNode.add(this.map.getNode(this.current.getX(), this.current.getY()));

		}

	}

	private Node chooseOpenNode(ArrayList<Node> neighbors) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Node> getNeighbors(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	private double distance(Vector node, Vector target) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void updateTarget(Vector target) {
		int x = (target.getX() - this.map.getDimension()) / this.map.getNodeDimension();
		int y = (target.getY() - this.map.getDimension()) / this.map.getNodeDimension();

		if (target.getX() > this.map.getStartPosition().getX() + this.map.getDimension()) {
			x = this.map.getColumn();
		}
		if (target.getY() > this.map.getStartPosition().getY() + this.map.getDimension()) {
			y = this.map.getRow();
		}
		if (target.getX() < this.map.getStartPosition().getX()) {
			x = 0;
		}
		if (target.getY() < this.map.getStartPosition().getY()) {
			y = 0;
		}

		this.target.setX(x);
		this.target.setY(y);
	}

	public NodeReader getMap() {
		return this.map;
	}

}
