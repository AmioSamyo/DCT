package DCT.input;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class MouseManager implements MouseInputListener {
	
	private int mouseX, mouseY;
	private boolean leftClicked, leftPressed, rightClicked, rightPressed;

	public MouseManager() {
		this.leftClicked = false;
		this.leftPressed = false;
		this.rightClicked = false;
		this.rightPressed = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			this.leftClicked = true;
			return;
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			this.rightClicked = true;
			return;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
