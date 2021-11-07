package DCT.entity;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.utility.Rectangle;

public class Player extends Creature {
	
	private int currentHealt;
	private Animation playerMoveDown, playerMoveRight, playerMoveUp, playerMoveLeft;
	
	public Player(Facade facade, Rectangle position) {
		super(facade, position);
		initialize();
	}

	private void initialize() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

}
