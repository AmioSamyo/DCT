package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class TilesAssets {
	
	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall, downWall, upperColumns, upperWall;

	public static void initialize() {
		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));

		grass = grassSheet.cropImage(new Rectangle(107, 33, 32, 32));
		flowerGrass = grassSheet.cropImage(new Rectangle(107, 231, 32, 32));
		rockGrass = grassSheet.cropImage(new Rectangle(140, 198, 32, 32));
		wall = grassSheet.cropImage(new Rectangle(206, 99, 32, 32));
		upperColumns = grassSheet.cropImage(new Rectangle(239, 0, 32, 32));
		upperWall = grassSheet.cropImage(new Rectangle(206, 66, 32, 32));
		downWall = grassSheet.cropImage(new Rectangle(206, 33, 32, 32));
	}

}
