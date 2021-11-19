package DCT.utility;

import java.awt.Font;
import java.awt.Color;

public class TextOption {
	
	private String text;
	private Vector position;
	private Font font;
	private Color color;

	public TextOption(String text, Vector position, Font font, Color color) {
		this.text = text;
		this.position = position;
		this.font = font;
		this.color = color;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public Color getColor() {
		return this.color;
	}
}
