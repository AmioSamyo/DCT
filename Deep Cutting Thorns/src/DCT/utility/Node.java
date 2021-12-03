package DCT.utility;

public class Node {

	private boolean viable;
	private Vector position;
	private double gScore;
	private double hScore;
	private double fScore;
	private boolean open;

	public Node(int i, int j) {
		this.viable = true;
		this.open = true;
		this.position = new Vector(i, j);
	}

	public boolean isViable() {
		return this.viable;
	}

	public void setViable(boolean flag) {
		this.viable = flag;
	}

	public int getX() {
		return this.position.getX();
	}

	public int getY() {
		return this.position.getY();
	}

	public void setGScore(double distance) {
		this.gScore = distance;
	}

	public void setHScore(double distance) {
		this.hScore = distance;
	}

	public void setFScore() {
		this.fScore = this.hScore + this.gScore;
	}

	public double getFScore() {
		return this.fScore;
	}

	public double getGScore() {
		return this.gScore;
	}

	public void isClosed() {
		this.open = false;
	}

	public boolean isOpen() {
		return this.open;
	}

	public void setOpen(boolean b) {
		this.open = b;

	}
}
