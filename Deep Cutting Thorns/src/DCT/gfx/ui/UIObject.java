package DCT.gfx.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import DCT.utility.Rectangle;

public abstract class UIObject {

	protected Rectangle position;
	protected boolean hovering;

	public UIObject(Rectangle dimensions) {
		this.position = dimensions;
		this.hovering = false;
	}

	public abstract void update();

	public abstract void render(Graphics g);

	public abstract void onClick();

	public void onMouseMove(MouseEvent e) {
		this.setHovering(this.position.contains(e.getX(), e.getY()));
	}

	public void onMouseRelease() {
		if (this.isHovering()) {
			this.onClick();
		}
	}

	public int getPositionX() {
		return this.position.getX();
	}

	public int getPositionY() {
		return this.position.getY();
	}

	public int getPositionWidth() {
		return this.position.getWidth();
	}

	public int getPositionHeight() {
		return this.position.getHeight();
	}

	public void setPositionX(int x) {
		this.position.setX(x);
	}

	public void setPositionY(int y) {
		this.position.setX(y);
	}

	public boolean isHovering() {
		return this.hovering;
	}

	public void setHovering(boolean b) {
		this.hovering = b;
	}

}
