package DCT.gfx.tools;

import java.awt.Graphics;

import javax.swing.JTextPane;

import DCT.utility.TextOption;

public class Text {

	private JTextPane textPane;
	private TextOption textOption;
	private boolean centered;
	
	public Text(TextOption textOption, Graphics g) {
		
		this.textOption = textOption;
		this.centered = false;
		
		this.textPane.setText(this.textOption.getText());
		this.textPane.setFont(this.textOption.getFont());
		this.textPane.setForeground(this.textOption.getColor());
		
	}
	
	private void setPosition() {
		if (this.centered == false) {
			this.textPane.setAlignmentX(this.textOption.getPosition().getX());
			this.textPane.setAlignmentY(this.textOption.getPosition().getY());
		}
		else {
			//TODO
		}
	}
}
