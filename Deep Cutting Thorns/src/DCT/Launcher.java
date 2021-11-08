package DCT;

import DCT.game.Game;

public class Launcher {

	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	private static final String TITLE = "Deep Cutting Thorns : PENE";

	public static void main(String[] args) {

		Game game = new Game(TITLE, WIDTH, HEIGHT);
		game.start();

	}

}
