package DCT.gfx.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import DCT.utility.Rectangle;

public class UIImageButton extends UIObject {

	private BufferedImage[] images;
	private IClickListener clicker;

	public UIImageButton(Rectangle dimensions, BufferedImage[] images, IClickListener clicker) {
		super(dimensions);
		this.images = images;
		this.clicker = clicker;
	}
	
	@Override
	public void onClick() {
		this.clicker.onClick();
	}

	@Override
	public void render(Graphics g) {
		if (hovering) {
			g.drawImage(this.images[1], this.getPositionX(), this.getPositionY(), this.getPositionWidth(),
					this.getPositionHeight(), null);
		} else {
			g.drawImage(this.images[0], this.getPositionX(), this.getPositionY(), this.getPositionWidth(),
					this.getPositionHeight(), null);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}