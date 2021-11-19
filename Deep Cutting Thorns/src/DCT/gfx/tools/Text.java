package DCT.gfx.tools;

import java.awt.Graphics;

import DCT.utility.TextOption;

public class Text {

	private TextOption textOption;
	private Graphics g;
	private int x, y;
	
	public Text(TextOption textOption, Graphics g) {
		
		this.textOption = textOption;
		this.g = g;
		
		this.g.setColor(this.textOption.getColor());
		this.g.setFont(this.textOption.getFont());
		
		this.setPosition();
		this.drawString();
	}
	
	private void drawString() {
		this.g.drawString(this.textOption.getText(), this.x, this.y);
	}

	private void setPosition() {
		if (this.textOption.getCentered() == false) {
			this.x = textOption.getPosition().getX();
			this.y = textOption.getPosition().getY();
		}
		else {
			//TODO
		}
	}
}
