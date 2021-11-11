package DCT.gfx;

import DCT.Facade;
import DCT.entity.Entity;

public class GameCamera {

	private int xOffset;
	private int yOffset;

	private Facade facade;

	public GameCamera(Facade facade) {

		this.facade = facade;

		this.xOffset = 0;
		this.yOffset = 0;
	}

	public void checkBlankSpace() {

	}

	public void centerOnEntity(Entity e) {
		this.xOffset = e.getPositionX() + (e.getPositionWidth() / 2) - (facade.getWidth() / 2);
		this.yOffset = e.getPositionY() + (e.getPositionHeight() / 2) - (facade.getHeight() / 2);
	}

	public void move(int xAmount, int yAmount) {
		this.xOffset += xAmount;
		this.yOffset += yAmount;
	}

	public int getXOffset() {
		return this.xOffset;
	}

	public int getYOffset() {
		return this.yOffset;
	}
}
