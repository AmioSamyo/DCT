package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;

import DCT.Facade;
import DCT.utility.AStar;
import DCT.utility.Vector;

public class EnemyDesignerDebug {

	public static void drawTarget(Facade facade, Graphics2D g, Color color, Vector position, int width, int height) {
		g.setColor(color);
		g.fillRect(position.getX() - facade.getGameCamera().getXOffset(),
				position.getY() - facade.getGameCamera().getYOffset(), width, height);
	}
	
	public static void drawGrid(Facade facade, AStar aStar, Graphics2D g, Enemy e) {
		for (int i = 0; i < aStar.getMap().getRow(); i++) {
			for (int j = 0; j < aStar.getMap().getColumn(); j++) {

				if (!aStar.getMap().getNode(j, i).isViable()) {
					g.setColor(new Color(0, 0, 0));
					g.drawRect(
							e.getPositionX() - e.diameterAggro / 2 + j * aStar.getMap().getNodeDimension()
									- facade.getGameCamera().getXOffset(),
							e.getPositionY() - e.diameterAggro / 2 + i * aStar.getMap().getNodeDimension()
									- facade.getGameCamera().getYOffset(),
							aStar.getMap().getNodeDimension(), aStar.getMap().getNodeDimension());
				} else {
					g.setColor(new Color(255, 255, 255));
					g.drawRect(
							e.getPositionX() - e.diameterAggro / 2 + j * aStar.getMap().getNodeDimension()
									- facade.getGameCamera().getXOffset(),
							e.getPositionY() - e.diameterAggro / 2 + i * aStar.getMap().getNodeDimension()
									- facade.getGameCamera().getYOffset(),
							aStar.getMap().getNodeDimension(), aStar.getMap().getNodeDimension());
				}
			}
		}
	}
	
	public static void drawPath(Facade facade, Graphics2D g, Color color, Vector v, AStar aStar, Enemy e) {
		g.setColor(color);
		g.drawRect(
				e.getPositionX() - e.diameterAggro / 2 + v.getX() * aStar.getMap().getNodeDimension()
						- facade.getGameCamera().getXOffset(),
				e.getPositionY() - e.diameterAggro / 2 + v.getY() * aStar.getMap().getNodeDimension()
						- facade.getGameCamera().getYOffset(),
				aStar.getMap().getNodeDimension(), aStar.getMap().getNodeDimension());
	}
}
