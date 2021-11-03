package DCT.input;

public class KeyManager {

	private boolean keys[];
	private boolean justPressed[];
	private boolean cantPress[];
	
	public KeyManager() {
		keys=new boolean[256];
		justPressed=new boolean[keys.length];
		cantPress=new boolean [keys.length];
	}
	
}
