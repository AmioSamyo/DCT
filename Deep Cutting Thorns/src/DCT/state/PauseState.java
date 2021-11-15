package DCT.state;

import java.awt.Graphics;
import java.awt.Color;

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
		
		//RESUME BUTTON
		this.uiManager.addUIObject(new UIImageButton(new Rectangle(this.facade.getWidth()/2 - 150, this.facade.getHeight()/2 - 50, 300, 100), Assets.healthBars, new IClickListener() {
			@Override
			public void onClick() {
				facade.getMouseManager().setUIManager(null);
				facade.setGamePause(false);
			}
		}));
		
		//DEBUG MODE
		this.uiManager.addUIObject(new UIImageButton(new Rectangle(this.facade.getWidth()/2 - 150, this.facade.getHeight()/2 + 150, 300, 100), Assets.healthBars, new IClickListener() {
			@Override
			public void onClick() {
				facade.getMouseManager().setUIManager(null);
				facade.lightDebugMode();
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.facade.getWidth(), this.facade.getHeight());
		this.uiManager.render(g);
	}

}
