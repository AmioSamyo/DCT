package DCT;

import java.awt.Graphics;

public abstract class State {

	private static State currentState = null;

	public abstract void update();

	public abstract void render(Graphics g);

	public State getCurrentState() {
		return State.currentState;
	}

	public void setCurrentState(State s) {
		State.currentState = s;
	}
}
