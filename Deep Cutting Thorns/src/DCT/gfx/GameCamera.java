package DCT.gfx;

import DCT.Facade;

public class GameCamera {

	private int xOffset;
	private int yOffset;

	private Facade facade;

	public GameCamera(Facade facade) {

		this.facade = facade;

		this.xOffset = 0;
		this.yOffset = 0;
	}

	public int getXOffset() {
		return this.xOffset;
	}

	public int getYOffset() {
		return this.yOffset;
	}
}
