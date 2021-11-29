package DCT.utility;

public class Node {

	private boolean viable;
	private int x, y;
	private double gScore;
	private double hScore;
	private double fScore;
	private boolean open = true;

	public Node(int i, int j) {
		this.viable = true;
		this.x = i;
		this.y = j;
	}

	public boolean isViable() {
		return this.viable;
	}

	public void setViable(boolean flag) {
		this.viable = flag;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
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
}
