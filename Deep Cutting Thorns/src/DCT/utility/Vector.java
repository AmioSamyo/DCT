package DCT.utility;

import java.io.Serializable;

public class Vector implements Serializable{

	private static final long serialVersionUID = 1L;
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

	public void normalize(int flag) {
		if (this.x != 0) {
			this.x /= Math.abs(this.x);
			this.x *= flag;
		}
		if (this.y != 0) {
			this.y /= Math.abs(this.y);
			this.y *= flag;
		}
	}

	public int distancetoVector(Vector v) {

		return (int) Math.sqrt(Math.pow(v.getX() - this.x, 2) + Math.pow(v.getY() - this.y, 2));
	}
	
	public double doubleDistanceVector(Vector target) {
		return Math.sqrt(Math.pow(target.getX() - this.x, 2) + Math.pow(target.getY() - this.y, 2));
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

	public boolean isEquals(Vector target) {
		if (target.getX() == this.x && target.getY() == this.y) {
			return true;
		}
		return false;

	}
}
