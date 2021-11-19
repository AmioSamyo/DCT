package DCT.gfx.tools;

import java.awt.Graphics;

import DCT.utility.TextOption;

public class Text {
	
	private static int x, y;
	
	public Text() {
		
	}
	
	public static void drawString(TextOption textOption, Graphics g) {
		
		g.setColor(textOption.getColor());
		g.setFont(textOption.getFont());
		
		setPosition(textOption);
		
		g.drawString(textOption.getText(), x, y);
	}

	private static void setPosition(TextOption textOption) {
		if (textOption.getCentered() == false) {
			x = textOption.getPosition().getX();
			y = textOption.getPosition().getY();
		}
		else {
			//TODO
		}
	}
}
