package DCT.tile;

import java.awt.Color;

import DCT.gfx.assets.Assets;

public class UpperColumnsTile extends Tile {

	public UpperColumnsTile(int id) {
		super(Assets.upperColumns, id);
		this.setDebuggingColor(new Color(30, 30, 30, 220));
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
