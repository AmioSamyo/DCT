package DCT.gfx;

import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

public class Assets {

	public static BufferedImage grass, grassFlower, grassRock, wall;

	public static void assetInitialize() {

		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));
		
		grass = grassSheet.cropImage(new Rectangle(96, 0, 32, 32));
		
		SpriteSheet wallSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//walls tile.gif"));
		
		wall = wallSheet.cropImage(new Rectangle(0, 0, 32, 32));
	}
}
