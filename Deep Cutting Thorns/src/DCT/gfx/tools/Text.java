package DCT.gfx.tools;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import DCT.utility.TextOption;

public class Text {

	public static void drawString(TextOption textOption, Graphics2D g) {

		g.setColor(textOption.getColor());
		g.setFont(textOption.getFont());

		if (textOption.getCentered() == true) {
			setPosition(textOption, g);
		}

		g.drawString(textOption.getText(), textOption.getPosition().getX(), textOption.getPosition().getY());
	}

	private static void setPosition(TextOption textOption, Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics(textOption.getFont());
		textOption.setPositionX(textOption.getPosition().getX() - (metrics.stringWidth(textOption.getText()) / 2));
		textOption.setPositionY(textOption.getPosition().getY() + (metrics.getHeight() / 2));
	}
}
