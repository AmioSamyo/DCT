package DCT.state;

import java.awt.Graphics;

import DCT.Facade;
import DCT.gfx.Assets;
import DCT.gfx.ui.IClickListener;
import DCT.gfx.ui.UIImageButton;
import DCT.gfx.ui.UIManager;
import DCT.utility.Rectangle;

public class PauseState extends State {

	private Facade facade;
	private UIManager uiManager;

	public PauseState(Facade facade) {
		this.facade = facade;
		this.uiManager = new UIManager(this.facade);
		this.facade.getMouseManager().setUIManager(this.uiManager);
		
		this.uiManager.addUIObject(new UIImageButton(new Rectangle(300, 300, 300, 100), Assets.healthBars, new IClickListener() {
			@Override
			public void onClick() {
				facade.getMouseManager().setUIManager(null);
				facade.setGamePause(false);
			}
		}));
	}

	@Override
	public void update() {
		this.uiManager.update();
	}

	@Override
	public void render(Graphics g) {
		this.uiManager.render(g);
	}

}
