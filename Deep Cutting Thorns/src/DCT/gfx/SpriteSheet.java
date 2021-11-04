package DCT.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;

	public SpriteSheet(BufferedImage img) {
		this.sheet = img;
	}

	public BufferedImage cropImage(Rectangle rect) {
		BufferedImage croppedImage = this.sheet.getSubimage(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		return croppedImage;
	}
}
