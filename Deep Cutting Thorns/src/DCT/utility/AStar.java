package DCT.utility;

import java.util.ArrayList;

public class AStar {

	NodeReader map;
	Vector start, target;
	ArrayList<Vector> path;

	public AStar(NodeReader map) {
		this.map = map;
		this.start = new Vector(this.map.getColumn() / 2, this.map.getRow() / 2);

		this.target = new Vector();
		this.path = new ArrayList<Vector>();

	}

	public ArrayList<Vector> givePath(Vector target) {
		int x = (target.getX()-this.map.getDimension())/this.map.getNodeDimension();
		int y = (target.getY()-this.map.getDimension())/this.map.getNodeDimension();

		if (target.getX() > this.map.getStartPosition().getX() + this.map.getDimension()) {
			x = this.map.getColumn();
		}
		if (target.getY() > this.map.getStartPosition().getY() + this.map.getDimension()) {
			y = this.map.getRow();
		}
		if (target.getX() < this.map.getStartPosition().getX() ) {
			x = 0;
		}
		if (target.getY() < this.map.getStartPosition().getY() ) {
			y = 0;
		}

		this.target.setX(x);
		this.target.setY(y);

		return path;
	}

}
