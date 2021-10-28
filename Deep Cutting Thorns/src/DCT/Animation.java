package DCT;

import java.awt.image.BufferedImage;

public class Animation {

	private int animationSpeed = 1;
	private int currentFrameIndex = 0;

	private long lastUpdateTime, timeCounter;

	private BufferedImage[] frames = new BufferedImage[0];

	public Animation(int animationSpeed, BufferedImage[] frames) {
		this.animationSpeed = animationSpeed;
		this.frames = frames;

		this.lastUpdateTime = System.currentTimeMillis();
		this.timeCounter = 0;
	}

	public void update() {
		this.timeCounter += System.currentTimeMillis() - this.lastUpdateTime;
		this.lastUpdateTime = System.currentTimeMillis();

		if (this.timeCounter > this.animationSpeed) {
			this.currentFrameIndex++;
			this.timeCounter = 0;
			
			if (this.currentFrameIndex >= this.frames.length) {
				this.currentFrameIndex = 0;
			}
		}

	}

	public BufferedImage getCurrentFrame() {
		return this.frames[this.currentFrameIndex];
	}

}
