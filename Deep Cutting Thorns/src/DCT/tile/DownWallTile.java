package DCT.tile;

import DCT.gfx.Assets;

public class DownWallTile extends Tile {

	public DownWallTile(int id) {
		super(Assets.downWall, id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
