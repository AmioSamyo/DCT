package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class StaticEntitiesAssets {

	public static BufferedImage jar, rocks, tree;

	public static void initialize() {
		SpriteSheet desertSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\DesertObjectPack#1 .png"));
		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));

		jar = desertSheet.cropImage(new Rectangle(240, 0, 260, 260));
		rocks = desertSheet.cropImage(new Rectangle(1070, 0, 276, 280));
		tree = grassSheet.cropImage(new Rectangle(0, 102, 63, 87));
	}

}
