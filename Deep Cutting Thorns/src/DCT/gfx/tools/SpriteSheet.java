package DCT.gfx.tools;

import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

public class SpriteSheet {

	private BufferedImage sheet;

	public SpriteSheet(BufferedImage img) {
		this.sheet = img;
	}

	public BufferedImage cropImage(Rectangle rect) {
		BufferedImage croppedImage = this.sheet.getSubimage(rect.getX(), rect.getY(), rect.getWidth(),
				rect.getHeight());
		return croppedImage;
	}
}
