package DCT.state.save_load;

import java.io.Serializable;

import DCT.utility.Vector;

public class StateSavingData implements Serializable {
	
	private int health;
	private Vector position;
	
	private static final long serialVersionUID = 1L;
	
	public void setHealth(int hp) {
		this.health = hp;
	}
	
	public void setPosition(Vector pos) {
		this.position = pos;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public Vector getPosition() {
		return this.position;
	}

}
