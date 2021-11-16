package DCT.gfx;

import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

public class Assets {

	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall, downWall, upperColumns, upperWall;
	public static BufferedImage jar, rocks;

	public static BufferedImage tree;

	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft;
	public static BufferedImage[] playerAnimationIdle;
	public static BufferedImage[] healthBars, playerDeadAnimation;

	public static BufferedImage[] batAnimationUp, batAnimationDown, batAnimationLeft, batAnimationRight;
	public static BufferedImage[] batAnimationDead;

	public static void assetInitialize() {

		SpriteSheet grassSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//32x32_map_Da_Tagliare.png"));

		grass = grassSheet.cropImage(new Rectangle(107, 33, 32, 32));
		flowerGrass = grassSheet.cropImage(new Rectangle(107, 231, 32, 32));
		rockGrass = grassSheet.cropImage(new Rectangle(140, 198, 32, 32));
		tree = grassSheet.cropImage(new Rectangle(0, 102, 63, 87));
		wall = grassSheet.cropImage(new Rectangle(206, 99, 32, 32));
		upperColumns = grassSheet.cropImage(new Rectangle(239, 0, 32, 32));
		upperWall = grassSheet.cropImage(new Rectangle(206, 66, 32, 32));
		downWall = grassSheet.cropImage(new Rectangle(206, 33, 32, 32));

		// SpriteSheet wallSheet = new SpriteSheet(ImageLoader.imageLoad("rsc//walls
		// tile.gif"));
		SpriteSheet desertSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\DesertObjectPack#1 .png"));

		jar = desertSheet.cropImage(new Rectangle(240, 0, 260, 260));
		rocks = desertSheet.cropImage(new Rectangle(1070, 0, 276, 280));

		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\goblin.png"));
		int playerAnimWidth = 704 / 11;
		int playerAnimHeight = 320 / 5;

		playerAnimationDown = new BufferedImage[5];
		playerAnimationRight = new BufferedImage[5];
		playerAnimationUp = new BufferedImage[5];
		playerAnimationLeft = new BufferedImage[5];
		playerAnimationIdle = new BufferedImage[4];
		playerDeadAnimation = new BufferedImage[1];

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

		playerDeadAnimation[0] = playerSheet
				.cropImage(new Rectangle(4 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));

		idleLoading(playerSheet);

		healthBarLoading();

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

	private static void idleLoading(SpriteSheet playerSheet) {
		int playerAnimWidth = 704 / 11;
		int playerAnimHeight = 320 / 5;
		playerAnimationIdle[0] = playerSheet
				.cropImage(new Rectangle(0 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[1] = playerSheet
				.cropImage(new Rectangle(1 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[2] = playerSheet
				.cropImage(new Rectangle(2 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
		playerAnimationIdle[3] = playerSheet
				.cropImage(new Rectangle(1 * playerAnimWidth, playerAnimHeight * 4, playerAnimWidth, playerAnimHeight));
	}

	private static void healthBarLoading() {
		SpriteSheet healthBar = new SpriteSheet(ImageLoader.imageLoad("rsc\\healthBars.png"));
		healthBars = new BufferedImage[29];
		for (int i = 0; i < 29; i++) {
			healthBars[i] = healthBar.cropImage(new Rectangle(0, i * 70, 390, 70));
		}
	}
}
