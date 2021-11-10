package DCT.entity.statics;

import java.awt.Graphics;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.utility.Rectangle;

public class StaticEntity extends Entity {

	public StaticEntity(Facade facade, int x, int y, int width, int height) {
		super(facade, new Rectangle(x, y, width, height));
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public void die() {
		
	}

}
