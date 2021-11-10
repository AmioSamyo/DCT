package DCT.utility;

public class Rectangle {

	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(Rectangle r) {
		return !(r.getX() > this.getX() + this.getWidth() || r.getX() + r.getWidth() < this.getX()
				|| r.getY() > this.getY() + this.getHeight() || r.getY() + r.getHeight() < this.getY());
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setX(int positionX) {
		this.x = positionX;
	}

	public void setY(int positionY) {
		this.y = positionY;
	}
}
