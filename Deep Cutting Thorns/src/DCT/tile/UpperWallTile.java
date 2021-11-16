package DCT.tile;

import java.awt.Color;

import DCT.gfx.Assets;

public class UpperWallTile extends Tile {

	public UpperWallTile(int id) {
		super(Assets.upperWall, id);
		this.setDebuggingColor(new Color(50, 50, 50));
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
