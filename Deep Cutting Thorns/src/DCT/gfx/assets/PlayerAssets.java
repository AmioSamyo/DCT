package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class PlayerAssets {

	public static int playerAnimWidth = 704 / 11;
	public static int playerAnimHeight = 320 / 5;

	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft,
			playerDeadAnimation;
	public static BufferedImage[] playerAnimationIdle, playerAnimationRoll, playerAttacking;

	public static void initialize() {
		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\goblin.png"));

		attackLoading(playerSheet);
		deadLoading(playerSheet);
		idleLoading(playerSheet);
		movementLoading(playerSheet);
		rollLoading();
	}

	private static void attackLoading(SpriteSheet playerSheet) {
		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));
		playerAttacking = new BufferedImage[12];

		for (int i = 0; i < 12; i++) {
			playerAttacking[i] = grassSheet.cropImage(new Rectangle(0, 102, 63, 87));
		}
	}

	private static void deadLoading(SpriteSheet playerSheet) {
		playerDeadAnimation = new BufferedImage[1];

		playerDeadAnimation[0] = playerSheet
				.cropImage(new Rectangle(4 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));

	}

	private static void idleLoading(SpriteSheet playerSheet) {
		playerAnimationIdle = new BufferedImage[4];

		playerAnimationIdle[0] = playerSheet
				.cropImage(new Rectangle(0 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[1] = playerSheet
				.cropImage(new Rectangle(1 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[2] = playerSheet
				.cropImage(new Rectangle(2 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[3] = playerSheet
				.cropImage(new Rectangle(1 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
	}

	private static void movementLoading(SpriteSheet playerSheet) {
		playerAnimationDown = new BufferedImage[5];
		playerAnimationRight = new BufferedImage[5];
		playerAnimationUp = new BufferedImage[5];
		playerAnimationLeft = new BufferedImage[5];

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
	}

	private static void rollLoading() {
		SpriteSheet desertSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\DesertObjectPack#1 .png"));
		playerAnimationRoll = new BufferedImage[1];

		playerAnimationRoll[0] = desertSheet.cropImage(new Rectangle(1070, 0, 276, 280));
	}

}
