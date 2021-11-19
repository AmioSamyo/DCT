package DCT.entity.creature.player.items;

import java.awt.Graphics;
import java.awt.Color;

import DCT.entity.creature.Creature;
import DCT.utility.Rectangle;

public class Weapon {

	private int damage;
	private long attackCooldown;

	private Color damageHitBoxColor = new Color(255, 255, 255);
	private Creature weaponUser;
	private Rectangle damageBox;

	public Weapon(int damage, long cooldown, Rectangle dmgBox, Creature weaponUser) {
		this.attackCooldown = cooldown;
		this.damage = damage;
		this.damageBox = dmgBox;
		this.weaponUser = weaponUser;
	}

	public void render(Graphics g) {
		g.setColor(this.damageHitBoxColor);
		g.fillRect(this.weaponUser.xMoveWithCamera() + this.damageBox.getX(),
				this.weaponUser.yMoveWithCamera() + this.damageBox.getY(), this.damageBox.getWidth(),
				this.damageBox.getHeight());
	}

	public long getCooldown() {
		return this.attackCooldown;
	}
	
	public int getDamage() {
		return this.damage;
	}

	public Rectangle getDamageBoxRelative() {
		return new Rectangle(this.weaponUser.xMoveWithCamera() + this.damageBox.getX(),
				this.weaponUser.yMoveWithCamera() + this.damageBox.getY(), this.damageBox.getWidth(),
				this.damageBox.getHeight());
	}
	
	public void setDamageBox(int x, int y, int width, int height) {
		this.damageBox.setX(x);
		this.damageBox.setY(y);
		this.damageBox.setWidth(width);
		this.damageBox.setHeight(height);
	}

}
