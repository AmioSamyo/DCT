package DCT.gfx.ui;

import java.awt.Graphics2D;
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
	public void render(Graphics2D g) {
		if (this.isHovering()) {
			g.drawImage(this.images[1], this.getPositionX(), this.getPositionY(), this.getPositionWidth(),
					this.getPositionHeight(), null);
		} else {
			g.drawImage(this.images[0], this.getPositionX(), this.getPositionY(), this.getPositionWidth(),
					this.getPositionHeight(), null);
		}
	}

	@Override
	public void update() {
	}

}