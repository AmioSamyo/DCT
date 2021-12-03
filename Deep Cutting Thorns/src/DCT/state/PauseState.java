package DCT.state;

import java.awt.Graphics2D;
import java.awt.Color;

import DCT.Facade;
import DCT.entity.creature.player.Player;
import DCT.gfx.Assets;
import DCT.gfx.ui.IClickListener;
import DCT.gfx.ui.UIImageButton;
import DCT.gfx.ui.UIManager;
import DCT.state.save_load.SaverLoader;
import DCT.state.save_load.StateSavingData;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class PauseState extends State {

	private Player p;

	private Facade facade;
	private UIManager uiManager;

	public PauseState(Facade facade) {
		this.facade = facade;
		this.p = this.facade.getEntityManager().getPlayer();
		this.uiManager = new UIManager(this.facade);
		this.facade.getMouseManager().setUIManager(this.uiManager);

		// RESUME BUTTON
		this.uiManager.addUIObject(new UIImageButton(
				new Rectangle(this.facade.getWidth() / 2 - 75, this.facade.getHeight() / 2 - 75, 150, 150),
				Assets.resumeButton, new IClickListener() {
					@Override
					public void onClick() {
						facade.getMouseManager().setUIManager(null);
						facade.setGamePause(false);
					}
				}));

		// DEBUG MODE
		this.uiManager.addUIObject(new UIImageButton(
				new Rectangle(this.facade.getWidth() / 2 - 75, this.facade.getHeight() / 2 - 75 + 180, 150, 150),
				Assets.debugButton, new IClickListener() {
					@Override
					public void onClick() {
						facade.getMouseManager().setUIManager(null);
						facade.lightDebugMode();
						facade.setGamePause(false);
					}
				}));

		// SAVE BUTTON
		this.uiManager.addUIObject(new UIImageButton(
				new Rectangle((int) (this.facade.getWidth() * 0.1), (int) (this.facade.getHeight() * 0.1), 150, 150),
				Assets.saveButton, new IClickListener() {
					@Override
					public void onClick() {
						facade.getMouseManager().setUIManager(null);
						saveGame();
						facade.setGamePause(false);
					}
				}));
		
		// LOAD BUTTON
		this.uiManager.addUIObject(new UIImageButton(
				new Rectangle((int) (this.facade.getWidth() * 0.8), (int) (this.facade.getHeight() * 0.1), 150, 150),
				Assets.loadButton, new IClickListener() {
					@Override
					public void onClick() {
						facade.getMouseManager().setUIManager(null);
						loadGame();
						facade.setGamePause(false);
					}
				}));
	}

	@Override
	public void update() {
		this.uiManager.update();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.facade.getWidth(), this.facade.getHeight());
		this.uiManager.render(g);
	}

	private void saveGame() {
		StateSavingData data = new StateSavingData();
		data.setHealth(p.getCurrentHealth());
		data.setPosition(new Vector(this.p.getPositionX(), this.p.getPositionY()));

		try {
			SaverLoader.save(data, "1.save");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void loadGame() {
		StateSavingData data;
		try {
			data = (StateSavingData) SaverLoader.load("1.save");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		this.p.setHealth(data.getHealth());
		this.p.setX(data.getPosition().getX());
		this.p.setY(data.getPosition().getY());
	}
}
