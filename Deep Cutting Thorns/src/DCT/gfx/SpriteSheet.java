package DCT.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;

	public SpriteSheet(BufferedImage img) {
		this.sheet = img;
	}

	public BufferedImage cropImage(Rectangle rect) {
		this.sheet.getSubimage(rect.x, rect.y, rect.width, rect.height);
		return this.sheet;
	}
}
