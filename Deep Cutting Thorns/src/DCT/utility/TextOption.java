package DCT.utility;

import java.awt.Font;
import java.awt.Color;

public class TextOption {
	
	private String text;
	private Vector position;
	private Font font;
	private Color color;
	private boolean centered;

	public TextOption(String text, Vector position, Font font, Color color, boolean centered) {
		this.text = text;
		this.position = position;
		this.font = font;
		this.color = color;
		this.centered = centered;
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
	
	public boolean getCentered() {
		return this.centered;
	}
}
