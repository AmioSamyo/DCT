package DCT;

import java.awt.Graphics;

public abstract class State {

	private static State currentState = null;

	public abstract void update();

	public abstract void render(Graphics g);

	public static State getCurrentState() {
		return State.currentState;
	}

	public static void setCurrentState(State s) {
		State.currentState = s;
	}
}
