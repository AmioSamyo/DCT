package DCT.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage grass, grassFlower, grassRock;

	public static void assetInitialize() {

		SpriteSheet grass = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));
	}
}
