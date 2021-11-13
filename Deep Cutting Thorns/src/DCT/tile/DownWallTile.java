package DCT.tile;

import java.awt.Color;

import DCT.gfx.Assets;

public class DownWallTile extends Tile {

	public DownWallTile(int id) {
		super(Assets.downWall, id);
		this.setDebuggingColor(new Color(60, 60, 60));
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
