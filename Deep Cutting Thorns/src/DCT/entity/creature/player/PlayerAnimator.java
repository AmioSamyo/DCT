package DCT.entity.creature.player;

import DCT.Facade;
import DCT.gfx.Animation;
import DCT.gfx.assets.Assets;

public class PlayerAnimator {

	public static final int ANIMATIONSPEED = 100;

	private int attackAnimIndex = 0;

	private Animation playerSprintDown, playerSprintRight, playerSprintUp, playerSprintLeft;
	private Animation playerRoll, playerAttacking;
	private Player player;
	private Facade facade;

	private static final int ANIMATIONSPRINTSPEED = 60;

	public PlayerAnimator(Player player, Facade facade) {
		this.player = player;
		this.facade = facade;
	}

	public void attack() {
		this.attackAnimIndex++;
		this.playerAttacking.update();

		if (this.attackAnimIndex >= this.playerAttacking.getAnimationLength()) {
			this.player.getPlayerAttacker().setAttack(false);
			this.facade.getMouseManager().setLeftClicked(false);
			this.attackAnimIndex = 0;
		}
	}

	public Animation getAttackingAnimation() {
		return this.playerAttacking;
	}

	public Animation getRollAnimation() {
		return this.playerRoll;
	}

	public Animation getSprintUpAnimation() {
		return this.playerSprintUp;
	}

	public Animation getSprintDownAnimation() {
		return this.playerSprintDown;
	}

	public Animation getSprintLeftAnimation() {
		return this.playerSprintLeft;
	}

	public Animation getSprintRightAnimation() {
		return this.playerSprintRight;
	}

	public void initialize() {
		this.playerSprintDown = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationDown);
		this.playerSprintRight = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationRight);
		this.playerSprintUp = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationUp);
		this.playerSprintLeft = new Animation(ANIMATIONSPRINTSPEED, Assets.playerAnimationLeft);

		this.playerRoll = new Animation(0, Assets.playerAnimationRoll);
		this.playerAttacking = new Animation(0, Assets.playerAttacking);
	}

	public void setAnimation(Animation moveAnimation, Animation sprintAnimation) {
		this.player.setCurrentAnimation(this.facade.getKeyManager().getSprint() ? sprintAnimation : moveAnimation);
	}

	public void update() {
		this.playerSprintUp.update();
		this.playerSprintRight.update();
		this.playerSprintLeft.update();
		this.playerSprintDown.update();

		this.playerRoll.update();
	}
}
