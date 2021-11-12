package DCT.tile;

import DCT.gfx.Assets;

public class UpperColumnsTile extends Tile {

	public UpperColumnsTile(int id) {
		super(Assets.upperColumns, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
