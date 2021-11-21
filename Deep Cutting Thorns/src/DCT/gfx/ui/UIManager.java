package DCT.gfx.ui;

import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import DCT.Facade;

public class UIManager {

	private Facade facade;
	private ArrayList<UIObject> uiObjects;

	public UIManager(Facade facade) {
		this.facade = facade;
		this.uiObjects = new ArrayList<UIObject>();
	}
	
	public void addUIObject(UIObject o) {
		this.uiObjects.add(o);
	}
	
	public void onMouseRelease(MouseEvent e) {
		this.uiObjects.forEach(o -> o.onMouseRelease());
	}
	
	public void onMouseMove(MouseEvent e) {
		this.uiObjects.forEach(o -> o.onMouseMove(e));
	}

	public void removeUIObject(UIObject o) {
		this.uiObjects.remove(o);
	}
	
	public void render(Graphics2D g) {
		this.uiObjects.forEach(o -> o.render(g));
	}
	
	public void update() {
		this.uiObjects.forEach(o -> o.update());
	}

	public Facade getFacade() {
		return facade;
	}
	

}
