package DCT.tile;

import DCT.gfx.Assets;

public class UpperWallTile extends Tile {

	public UpperWallTile(int id) {
		super(Assets.upperWall, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
