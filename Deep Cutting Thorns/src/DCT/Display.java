package DCT;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;

	private String title;
	private int width, height;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		CreateDisplay();
	}

	public void CreateDisplay() {
		frame = FrameSetter();
		canvas = CanvasSetter();
	}

	public JFrame getJFrame() {		
		return frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	private JFrame FrameSetter() {
		frame = new JFrame(title);
		
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setFocusable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	private Canvas CanvasSetter() {
		canvas = new Canvas();
		
		canvas.setSize(width, height);
		canvas.setVisible(true);
		canvas.setFocusable(false);
		return canvas;
	}
}
