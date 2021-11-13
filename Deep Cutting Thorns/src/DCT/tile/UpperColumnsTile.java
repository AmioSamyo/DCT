package DCT.tile;

import java.awt.Color;

import DCT.gfx.Assets;

public class UpperColumnsTile extends Tile {

	public UpperColumnsTile(int id) {
		super(Assets.upperColumns, id);
		this.setDebuggingColor(new Color(30, 30, 30));
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
