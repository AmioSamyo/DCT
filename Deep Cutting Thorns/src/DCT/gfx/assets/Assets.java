package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class Assets {

	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall, downWall, upperColumns, upperWall;
	public static BufferedImage jar, rocks;

	public static BufferedImage tree;

	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft, playerDeadAnimation;
	public static BufferedImage[] healthBars;
	public static BufferedImage[] resumeButton, debugButton, saveButton, loadButton;
	public static BufferedImage[] playerAnimationIdle, playerAnimationRoll, playerAttacking;

	public static BufferedImage[] batAnimationUp, batAnimationDown, batAnimationLeft, batAnimationRight;
	public static BufferedImage[] batAnimationDead;

	public static void initialize() {
		playerLoading();
		tilesLoading();

		SpriteSheet desertSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\DesertObjectPack#1 .png"));

		jar = desertSheet.cropImage(new Rectangle(240, 0, 260, 260));
		rocks = desertSheet.cropImage(new Rectangle(1070, 0, 276, 280));

		

		healthBarLoading();

		pauseStateAssets();

		SpriteSheet batSheet = new SpriteSheet(ImageLoader.imageLoad("rsc\\BatSprite.png"));

		batAnimationDown = new BufferedImage[3];
		batAnimationUp = new BufferedImage[3];
		batAnimationLeft = new BufferedImage[3];
		batAnimationRight = new BufferedImage[3];
		batAnimationDead = new BufferedImage[1];

		setBatTile(batSheet);
	}

	private static void tilesLoading() {
		TilesAssets.initialize();

		grass = TilesAssets.grass;
		flowerGrass = TilesAssets.flowerGrass;
		rockGrass = TilesAssets.rockGrass;
		wall = TilesAssets.wall;
		upperColumns = TilesAssets.upperColumns;
		upperWall = TilesAssets.upperWall;
		downWall = TilesAssets.downWall;
	}

	private static void playerLoading() {
		PlayerAssets.initialize();
		
		playerAnimationDown = PlayerAssets.playerAnimationDown;
		playerAnimationRight = PlayerAssets.playerAnimationRight;
		playerAnimationUp = PlayerAssets.playerAnimationUp;
		playerAnimationLeft = PlayerAssets.playerAnimationLeft;
		playerAnimationIdle = PlayerAssets.playerAnimationIdle;
		playerDeadAnimation = PlayerAssets.playerDeadAnimation;
		playerAnimationRoll = PlayerAssets.playerAnimationRoll;
		playerAttacking = PlayerAssets.playerAttacking;
	}

	private static void pauseStateAssets() {

		resumeButton = new BufferedImage[2];

		resumeButton[1] = ImageLoader.imageLoad("rsc\\resumeButton0.png");
		resumeButton[0] = ImageLoader.imageLoad("rsc\\resumeButton1.png");

		debugButton = new BufferedImage[2];

		debugButton[1] = ImageLoader.imageLoad("rsc\\debugButton0.png");
		debugButton[0] = ImageLoader.imageLoad("rsc\\debugButton1.png");

		saveButton = new BufferedImage[2];

		saveButton[0] = ImageLoader.imageLoad("rsc\\save0.png");
		saveButton[1] = ImageLoader.imageLoad("rsc\\save1.png");

		loadButton = new BufferedImage[2];

		loadButton[0] = ImageLoader.imageLoad("rsc\\loading0.png");
		loadButton[1] = ImageLoader.imageLoad("rsc\\loading1.png");
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

	private static void healthBarLoading() {
		SpriteSheet healthBar = new SpriteSheet(ImageLoader.imageLoad("rsc\\healthBars.png"));
		healthBars = new BufferedImage[29];
		for (int i = 0; i < 29; i++) {
			healthBars[i] = healthBar.cropImage(new Rectangle(0, i * 70, 390, 70));
		}
	}
}
