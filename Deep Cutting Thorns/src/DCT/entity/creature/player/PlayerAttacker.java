package DCT.entity.creature.player;

import java.util.ArrayList;

import DCT.Facade;
import DCT.entity.Entity;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class PlayerAttacker {

	private boolean attacking;

	private long lastAttackTimer, attackTimer;

	private Vector attackDirection;
	private Player player;
	private Facade facade;

	public PlayerAttacker(Player player, Facade facade) {
		this.player = player;
		this.facade = facade;
		this.lastAttackTimer = 0;
	}

	public void attackDirections() {

		boolean botRightCondition = this.attackDirection.getX() > 0 && this.attackDirection.getY() > 0;
		boolean botLeftCondition = this.attackDirection.getX() < 0 && this.attackDirection.getY() > 0;

		boolean upLeftCondition = this.attackDirection.getX() < 0 && this.attackDirection.getY() < 0;
		boolean upRIghtCondition = this.attackDirection.getX() > 0 && this.attackDirection.getY() < 0;

		boolean rightCondition = this.attackDirection.getX() > 0;
		boolean leftCondition = this.attackDirection.getX() < 0;
		boolean downCondition = this.attackDirection.getY() > 0;
		boolean upCondition = this.attackDirection.getY() < 0;

		if (botRightCondition) {

			this.setDamageDirection(this.player.getHitbox().getWidth(), this.player.getHitbox().getHeight());

		} else if (botLeftCondition) {

			this.setDamageDirection(-this.player.getHitbox().getWidth(), this.player.getHitbox().getHeight());

		} else if (upLeftCondition) {

			this.setDamageDirection(-this.player.getHitbox().getWidth(), -this.player.getHitbox().getHeight());

		} else if (upRIghtCondition) {

			this.setDamageDirection(this.player.getHitbox().getWidth(), -this.player.getHitbox().getHeight());

		} else if (rightCondition) {

			this.setDamageDirection(this.player.getHitbox().getWidth(), 0);

		} else if (leftCondition) {

			this.setDamageDirection(-this.player.getHitbox().getWidth(), 0);

		} else if (downCondition) {

			this.setDamageDirection(0, this.player.getHitbox().getHeight());

		} else if (upCondition) {

			this.setDamageDirection(0, -this.player.getHitbox().getHeight());

		} else {

			this.attacking = false;
			this.player.getEquippedWeapon().setDamageBox(0, 0, 0, 0);

		}
	}

	public boolean isAttacking() {
		return this.attacking;
	}

	public void aimAttack() {

		this.attacking = true;

		ArrayList<Rectangle> rectangleOfAttacks = new ArrayList<Rectangle>();
		ArrayList<Boolean> targettedPosition = new ArrayList<Boolean>();

		this.createRectangleOfAttack(rectangleOfAttacks);

		this.chooseDirectionOfAttack(rectangleOfAttacks, targettedPosition);

		this.attackDirection = this.chooseVectorOfAttack(targettedPosition);
	}

	public void setAttackTimer(long time) {
		this.attackTimer = time;
	}

	public void setAttack(boolean isAttacking) {
		this.attacking = isAttacking;
	}

	private void chooseDirectionOfAttack(ArrayList<Rectangle> rectangleOfAttacks,
			ArrayList<Boolean> targettedPosition) {

		Vector pointOfAttack = new Vector(
				this.facade.getMouseManager().getMouseX() + this.facade.getGameCamera().getXOffset(),
				this.facade.getMouseManager().getMouseY() + this.facade.getGameCamera().getYOffset());

		for (int i = 0; i < 8; i++) {
			targettedPosition.add(rectangleOfAttacks.get(i).contains(pointOfAttack.getX(), pointOfAttack.getY()));
		}
	}

	private Vector chooseVectorOfAttack(ArrayList<Boolean> targettedPosition) {

		if (targettedPosition.get(0)) {
			return new Vector(-1, -1);
		} else if (targettedPosition.get(1)) {
			return new Vector(0, -1);
		} else if (targettedPosition.get(2)) {
			return new Vector(1, -1);
		} else if (targettedPosition.get(3)) {
			return new Vector(1, 0);
		} else if (targettedPosition.get(4)) {
			return new Vector(1, 1);
		} else if (targettedPosition.get(5)) {
			return new Vector(0, 1);
		} else if (targettedPosition.get(6)) {
			return new Vector(-1, 1);
		} else if (targettedPosition.get(7)) {
			return new Vector(-1, 0);
		} else {
			return new Vector();
		}
	}

	public void checkAttacks() {

		this.updateTimerAttack();

		if (this.attackTimer >= this.player.getEquippedWeapon().getCooldown()) {
			this.checkAttackTarget();
		}
	}

	private void checkAttackTarget() {

		this.attackDirections();
		this.attackTimer = 0;

		for (Entity e : this.facade.getEntityManager().getEntityList()) {
			if (e.equals(this.player)) {
				continue;
			}

			if (e.getCollisionHitBox(-this.facade.getGameCamera().getXOffset(),
					-this.facade.getGameCamera().getYOffset())
					.intersects(this.player.getEquippedWeapon().getDamageBoxRelative())) {

				e.damage(this.player.getEquippedWeapon().getDamage());
			}
		}
	}

	private void createRectangleOfAttack(ArrayList<Rectangle> rectangleOfAttacks) {

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() - this.facade.getWidth(),
				this.player.getPositionY() - this.facade.getHeight(), this.facade.getWidth(), this.facade.getHeight()));

		rectangleOfAttacks
				.add(new Rectangle(this.player.getPositionX(), this.player.getPositionY() - this.facade.getHeight(),
						this.player.getPositionWidth(), this.facade.getHeight()));

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() + this.player.getPositionWidth(),
				this.player.getPositionY() - this.facade.getHeight(), this.facade.getWidth(), this.facade.getHeight()));

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() + this.player.getPositionWidth(),
				this.player.getPositionY(), this.facade.getWidth(), this.player.getPositionHeight()));

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() + this.player.getPositionWidth(),
				this.player.getPositionY() + this.player.getPositionHeight(), this.facade.getWidth(),
				this.facade.getHeight()));

		rectangleOfAttacks.add(
				new Rectangle(this.player.getPositionX(), this.player.getPositionY() + this.player.getPositionHeight(),
						this.player.getPositionWidth(), this.facade.getHeight()));

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() - this.facade.getWidth(),
				this.player.getPositionY() + this.player.getPositionHeight(), this.facade.getWidth(),
				this.facade.getHeight()));

		rectangleOfAttacks.add(new Rectangle(this.player.getPositionX() - this.facade.getWidth(),
				this.player.getPositionY(), this.facade.getWidth(), this.player.getPositionHeight()));
	}

	private void setDamageDirection(int xAdjustment, int yAdjustment) {
		this.player.getEquippedWeapon().setDamageBox(this.player.getHitbox().getX() + xAdjustment,
				this.player.getHitbox().getY() + yAdjustment, this.player.getHitbox().getWidth(),
				this.player.getHitbox().getHeight());
	}

	private void updateTimerAttack() {
		this.attackTimer += System.currentTimeMillis() - this.lastAttackTimer;
		this.lastAttackTimer = System.currentTimeMillis();
	}

}
