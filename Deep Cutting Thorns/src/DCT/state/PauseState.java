package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Assets;

public class PauseState extends State {

	private Facade facade;

	public PauseState(Facade facade) {
		this.facade = facade;
	}

	public void update() {
		// TODO

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.flowerGrass, 0, 0, this.facade.getWidth(), this.facade.getHeight(), null);
	}

}
