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
		if(e.getButton() == MouseEvent.BUTTON1 && e.getButton() == MouseEvent.BUTTON2) {
			this.leftClicked = true;
			this.rightClicked = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			this.leftClicked = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			this.rightClicked = true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && e.getButton() == MouseEvent.BUTTON2) {
			this.leftPressed = true;
			this.rightPressed = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			this.leftPressed = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			this.rightPressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.NOBUTTON) {
			this.leftPressed = false;
			this.rightPressed = false;
		}
		else if(e.getButton() != MouseEvent.NOBUTTON && e.getButton() != MouseEvent.BUTTON1) {
			this.leftPressed = false;
		}
		else if(e.getButton() != MouseEvent.NOBUTTON && e.getButton() != MouseEvent.BUTTON2) {
			this.rightPressed = false;
		}
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
