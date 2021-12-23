package DCT.gfx.assets;

import java.awt.image.BufferedImage;

import DCT.gfx.tools.ImageLoader;
import DCT.gfx.tools.SpriteSheet;
import DCT.utility.Rectangle;

public class Assets {

	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall, downWall, upperColumns, upperWall;
	public static BufferedImage jar, rocks, tree;

	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft,
			playerDeadAnimation;
	public static BufferedImage[] healthBars;
	public static BufferedImage[] resumeButton, debugButton, saveButton, loadButton;
	public static BufferedImage[] playerAnimationIdle, playerAnimationRoll, playerAttacking;

	public static BufferedImage[] batAnimationUp, batAnimationDown, batAnimationLeft, batAnimationRight,
			batAnimationDead;

	public static void initialize() {
		playerLoading();
		tilesLoading();
		staticEntitiesLoading();
		enemiesLoading();

		healthBarLoading();

		pauseStateAssets();

		
	}

	private static void enemiesLoading() {
		EnemiesAssets.initialize();

		batAnimationDown = EnemiesAssets.batAnimationDown;
		batAnimationUp = EnemiesAssets.batAnimationUp;
		batAnimationLeft = EnemiesAssets.batAnimationLeft;
		batAnimationRight = EnemiesAssets.batAnimationRight;
		batAnimationDead = EnemiesAssets.batAnimationDead;
	}

	private static void staticEntitiesLoading() {
		StaticEntitiesAssets.initialize();

		jar = StaticEntitiesAssets.jar;
		rocks = StaticEntitiesAssets.rocks;
		tree = StaticEntitiesAssets.tree;
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

	private static void healthBarLoading() {
		SpriteSheet healthBar = new SpriteSheet(ImageLoader.imageLoad("rsc\\healthBars.png"));
		healthBars = new BufferedImage[29];
		for (int i = 0; i < 29; i++) {
			healthBars[i] = healthBar.cropImage(new Rectangle(0, i * 70, 390, 70));
		}
	}
}
