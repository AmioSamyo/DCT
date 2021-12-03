package DCT.utility;

import java.awt.Font;
import java.awt.Color;

public class TextOption {
	
	private boolean centered;
	private String text;
	
	private Font font;
	private Color color;
	
	private Vector position;
	

	public TextOption(String text, Vector position, Font font, Color color, boolean centered) {
		
		this.centered = centered;
		this.text = text;
		
		this.font = font;
		this.color = color;

		this.position = position;
		
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
	
	public void setPositionX(int x) {
		this.position.setX(x);
	}
	
	public void setPositionY(int y) {
		this.position.setY(y);
	}
	
	public boolean getCentered() {
		return this.centered;
	}
}
