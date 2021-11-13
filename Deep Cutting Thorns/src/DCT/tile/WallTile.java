package DCT.tile;

import java.awt.Color;

import DCT.gfx.Assets;

public class WallTile extends Tile {

	public WallTile(int id) {
		super(Assets.wall, id);
		this.setDebuggingColor(new Color(0, 0, 0));
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
