package DCT.utility;

public class Vector {

	private int x, y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
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
}
