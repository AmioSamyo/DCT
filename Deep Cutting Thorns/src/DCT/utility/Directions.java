package DCT.utility;

public enum Directions {
	
	TopLeft(0), Top(1), TopRight(2), Right(3), BotRight(4), Bot(5), BotLeft(6), Left(7);

	public int code;

	private Directions(int value) {
		this.code = value;
	}

}
