package DCT.gfx.assets;

import java.awt.image.BufferedImage;

public class Assets {

	// ENEMIES
	public static BufferedImage[] batAnimationUp, batAnimationDown, batAnimationLeft, batAnimationRight,
			batAnimationDead;

	// PLAYER
	public static BufferedImage[] playerAnimationDown, playerAnimationRight, playerAnimationUp, playerAnimationLeft,
			playerDeadAnimation;
	public static BufferedImage[] playerAnimationIdle, playerAnimationRoll, playerAttacking;

	// STATIC ENTITIES
	public static BufferedImage jar, rocks, tree;

	// TILES
	public static BufferedImage grass, flowerGrass, rockGrass;
	public static BufferedImage wall, downWall, upperColumns, upperWall;

	// UI
	public static BufferedImage[] healthBars;
	public static BufferedImage[] resumeButton, debugButton, saveButton, loadButton;

	public static void initialize() {
		enemiesLoading();
		playerLoading();
		staticEntitiesLoading();
		tilesLoading();
		uiLoading();
	}

	private static void enemiesLoading() {
		EnemiesAssets.initialize();

		batAnimationDown = EnemiesAssets.batAnimationDown;
		batAnimationUp = EnemiesAssets.batAnimationUp;
		batAnimationLeft = EnemiesAssets.batAnimationLeft;
		batAnimationRight = EnemiesAssets.batAnimationRight;
		batAnimationDead = EnemiesAssets.batAnimationDead;
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

	private static void uiLoading() {
		UiAssets.initialize();

		healthBars = UiAssets.healthBars;

		resumeButton = UiAssets.resumeButton;
		debugButton = UiAssets.debugButton;
		saveButton = UiAssets.saveButton;
		loadButton = UiAssets.loadButton;
	}
}
