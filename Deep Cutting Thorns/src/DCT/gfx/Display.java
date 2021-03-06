package DCT.gfx;

import java.awt.Canvas;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import DCT.gfx.tools.ImageLoader;

public class Display {

	private JFrame frame = null;
	private Canvas canvas = null;

	private String title;
	private int width, height;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.createDisplay();
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public JFrame getJFrame() {
		return this.frame;
	}

	public void createDisplay() {
		this.frame = this.FrameSetter();
		this.canvas = this.CanvasSetter();

		this.frame.add(this.canvas);
		this.frame.pack();
	}

	private JFrame FrameSetter() {
		this.frame = new JFrame(this.title);

		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setIconImage(ImageLoader.imageLoad("rsc\\torce.png"));
		return this.frame;
	}

	private Canvas CanvasSetter() {
		this.canvas = new Canvas();

		this.canvas.setSize(this.width, this.height);
		this.canvas.setVisible(true);
		this.canvas.setFocusable(false);
		return this.canvas;
	}
}
