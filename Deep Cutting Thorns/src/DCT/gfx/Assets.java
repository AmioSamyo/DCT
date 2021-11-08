package DCT.gfx;

import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

public class Assets {

	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall;
	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft;
	public static BufferedImage[] playerAnimationIdle;

	public static void assetInitialize() {

		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));

		grass = grassSheet.cropImage(new Rectangle(107, 33, 32, 32));
		flowerGrass = grassSheet.cropImage(new Rectangle(107, 231, 32, 32));
		rockGrass = grassSheet.cropImage(new Rectangle(140, 198, 32, 32));

		SpriteSheet wallSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//walls tile.gif"));

		wall = wallSheet.cropImage(new Rectangle(32, 0, 32, 32));

		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\goblin.png"));
		int playerAnimWidth = 704 / 11;
		int playerAnimHeight = 320 / 5;

		playerAnimationDown = new BufferedImage[5];
		playerAnimationRight = new BufferedImage[5];
		playerAnimationUp = new BufferedImage[5];
		playerAnimationLeft = new BufferedImage[5];
		playerAnimationIdle = new BufferedImage[4];

		for (int i = 0; i < 5; i++) {
			playerAnimationDown[i] = playerSheet.cropImage(
					new Rectangle(i * playerAnimWidth, playerAnimHeight * 0, playerAnimWidth, playerAnimHeight));
			playerAnimationRight[i] = playerSheet.cropImage(
					new Rectangle(i * playerAnimWidth, playerAnimHeight * 1, playerAnimWidth, playerAnimHeight));
			playerAnimationUp[i] = playerSheet.cropImage(
					new Rectangle(i * playerAnimWidth, playerAnimHeight * 2, playerAnimWidth, playerAnimHeight));
			playerAnimationLeft[i] = playerSheet.cropImage(
					new Rectangle(i * playerAnimWidth, playerAnimHeight * 3, playerAnimWidth, playerAnimHeight));
		}
		idleLoading(playerSheet);
	}

	private static void idleLoading(SpriteSheet playerSheet) {
		int playerAnimWidth = 704 / 11;
		int playerAnimHeight = 320 / 5;
		playerAnimationIdle[0] = playerSheet
				.cropImage(new Rectangle(2 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[1] = playerSheet
				.cropImage(new Rectangle(3 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[2] = playerSheet
				.cropImage(new Rectangle(4 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[3] = playerSheet
				.cropImage(new Rectangle(3 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
	}
}
