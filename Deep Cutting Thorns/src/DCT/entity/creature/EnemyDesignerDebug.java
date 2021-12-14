package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.utility.Vector;

public class EnemyDesignerDebug {

	public static void drawTarget(Facade facade, Graphics2D g, Color color, Vector position, int width, int height) {
		g.setColor(color);
		g.fillRect(position.getX() - facade.getGameCamera().getXOffset(),
				position.getY() - facade.getGameCamera().getYOffset(), width, height);
	}
}
