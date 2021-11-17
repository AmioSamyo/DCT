package DCT.utility;

public class Vector {

	private int x, y;

	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector copy) {
		this.x = copy.getX();
		this.y = copy.getY();
	}

	public void normalize() {
		if (this.x != 0) {
			this.x /= Math.abs(this.x);
		}
		if (this.y != 0) {
			this.y /= Math.abs(this.y);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
