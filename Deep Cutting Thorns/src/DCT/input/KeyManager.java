package DCT.input;

public class KeyManager {

	private boolean keys[];
	private boolean justPressed[];
	private boolean cantPress[];

	public KeyManager() {

		keys = new boolean[256];
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];

	}

	public void update() {
		
		for (int i = 0; i < keys.length; i++) {
			
			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if (!cantPress[i] && keys[i]) {
				justPressed[i]=true;
			}
		}
	}

}
