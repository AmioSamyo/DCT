package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class EnemiesAssets {

	public static BufferedImage[] batAnimationUp, batAnimationDown, batAnimationLeft, batAnimationRight;
	public static BufferedImage[] batAnimationDead;

	public static void initialize() {
		SpriteSheet batSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\BatSprite.png"));

		batAnimationDown = new BufferedImage[3];
		batAnimationUp = new BufferedImage[3];
		batAnimationLeft = new BufferedImage[3];
		batAnimationRight = new BufferedImage[3];
		batAnimationDead = new BufferedImage[1];

		setBatTile(batSheet);
	}

	private static void setBatTile(SpriteSheet batSheet) {
		int batAnimWidth = 32;
		int batAnimHeight = 24;

		batAnimationDown[0] = batSheet.cropImage(new Rectangle(31, 4, batAnimWidth, batAnimHeight));
		batAnimationDown[1] = batSheet.cropImage(new Rectangle(63, 4, batAnimWidth, batAnimHeight));
		batAnimationDown[2] = batSheet.cropImage(new Rectangle(95, 0, batAnimWidth, batAnimHeight));

		batAnimationRight[0] = batSheet.cropImage(new Rectangle(32, 31, batAnimWidth, batAnimHeight));
		batAnimationRight[1] = batSheet.cropImage(new Rectangle(64, 31, batAnimWidth, batAnimHeight));
		batAnimationRight[2] = batSheet.cropImage(new Rectangle(95, 37, batAnimWidth, batAnimHeight));

		batAnimationUp[0] = batSheet.cropImage(new Rectangle(32, 68, batAnimWidth, batAnimHeight));
		batAnimationUp[1] = batSheet.cropImage(new Rectangle(64, 68, batAnimWidth, batAnimHeight));
		batAnimationUp[2] = batSheet.cropImage(new Rectangle(95, 64, batAnimWidth, batAnimHeight));

		batAnimationLeft[0] = batSheet.cropImage(new Rectangle(32, 95, batAnimWidth, batAnimHeight));
		batAnimationLeft[1] = batSheet.cropImage(new Rectangle(64, 95, batAnimWidth, batAnimHeight));
		batAnimationLeft[2] = batSheet.cropImage(new Rectangle(95, 100, batAnimWidth, batAnimHeight));

		batAnimationDead[0] = batSheet.cropImage(new Rectangle(0, 104, batAnimWidth, batAnimHeight));
	}

}
