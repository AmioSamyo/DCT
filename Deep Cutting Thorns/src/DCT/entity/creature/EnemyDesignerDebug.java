package DCT.entity.creature;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import DCT.Facade;
import DCT.tile.Tile;
import DCT.utility.AStar;
import DCT.utility.Node;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class EnemyDesignerDebug {

	private Facade facade;
	private Graphics2D g;
	private AStar aStar;
	private Enemy e;

	public EnemyDesignerDebug(Facade facade, Graphics2D g, Enemy e) {
		this.facade = facade;
		this.g = g;
		this.aStar = e.aStar;
		this.e = e;
	}

	public void drawDebug() {
		this.drawPoint(new Color(175, 0, 120, 100), this.e.start, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		this.drawPoint(new Color(0, 0, 120, 100), this.e.target, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		this.drawGrid();
		this.drawPath();
		this.drawCube(Color.GREEN, new Vector(this.aStar.getStart()));
		this.drawCube(Color.BLUE, new Vector(this.aStar.getTarget()));
		this.drawLine(Color.BLUE);
		this.drawRangeAggro();
	}
	
	private void drawCube(Color color, Vector v) {
		this.g.setColor(color);
		this.g.drawRect(
				this.e.getPositionX() - this.e.diameterAggro / 2 + v.getX() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getXOffset(),
				this.e.getPositionY() - this.e.diameterAggro / 2 + v.getY() * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getYOffset(),
				this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());
	}

	private void drawGrid() {
		for (int i = 0; i < this.aStar.getMap().getRow(); i++) {
			for (int j = 0; j < this.aStar.getMap().getColumn(); j++) {
				if (!this.aStar.getMap().getNode(j, i).isViable()) {
					this.drawGridRectangle(i, j, new Color(0, 0, 0));
				} else {
					this.drawGridRectangle(i, j, new Color(255, 255, 255));
				}
			}
		}
	}

	private void drawGridRectangle(int i, int j, Color color) {
		this.g.setColor(color);
		this.g.drawRect(
				this.e.getPositionX() - this.e.diameterAggro / 2 + j * this.aStar.getMap().getNodeDimension()
						- facade.getGameCamera().getXOffset(),
				this.e.getPositionY() - this.e.diameterAggro / 2 + i * this.aStar.getMap().getNodeDimension()
						- this.facade.getGameCamera().getYOffset(),
				this.aStar.getMap().getNodeDimension(), this.aStar.getMap().getNodeDimension());
	}

	private void drawLine(Color color) {
		this.g.setColor(color);
		this.g.drawLine(
				this.e.getPositionX() + this.e.getPositionWidth() / 2 - this.facade.getGameCamera().getXOffset(),
				this.e.getPositionY() + this.e.getPositionHeight() / 2 - this.facade.getGameCamera().getYOffset(),
				this.e.targetPath.getX() - this.facade.getGameCamera().getXOffset(),
				this.e.targetPath.getY() - this.facade.getGameCamera().getYOffset());
	}
	
	private void drawPath() {
		ArrayList<Node> path = this.aStar.getPathNode();
		for (int k = 0; k < path.size(); k++) {
			int j = path.get(k).getX();
			int i = path.get(k).getY();
			this.drawCube(Color.RED, new Vector(j, i));
		}
	}
	
	private void drawPoint(Color color, Vector position, int width, int height) {
		this.g.setColor(color);
		this.g.fillRect(position.getX() - this.facade.getGameCamera().getXOffset(),
				position.getY() - this.facade.getGameCamera().getYOffset(), width, height);
	}

	private void drawRangeAggro() {
		this.g.setColor(this.e.getDebuggingColor());
		Rectangle StartBatEye = new Rectangle(
				(int) (this.e.getPositionX() - this.e.diameterAggro / 2 + this.e.getPositionWidth() / 2),
				(int) (this.e.getPositionY() - this.e.diameterAggro / 2 + this.e.getPositionHeight() / 2), 0, 0);
		if (this.e.playerInAggro()) {
			this.g.setColor(new Color(255, 0, 0, 100));
		}
		this.g.fillOval(this.e.getXMoveHitbox(StartBatEye), this.e.getYMoveHitbox(StartBatEye), this.e.diameterAggro,
				this.e.diameterAggro);
	}
}
