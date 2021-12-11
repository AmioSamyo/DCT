package DCT.entity.creature.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import DCT.Facade;
import DCT.entity.creature.Creature;
import DCT.entity.creature.player.items.Weapon;
import DCT.gfx.Animation;
import DCT.gfx.Assets;
import DCT.utility.Rectangle;
import DCT.utility.Vector;

public class Player extends Creature {

	private Weapon equippedWeapon;

	private PlayerRoller playerRoller;
	private PlayerAttacker playerAttacker;
	private PlayerAnimator playerAnimator;

	private static final int PLAYERWIDTH = 704 / 11, PLAYERHEIGHT = 320 / 5;
	private static final int SCALE = 2;

	private static final int SPRINTSPEED = 8;

	public Player(Facade facade, Vector position) {

		super(facade, new Rectangle(position.getX(), position.getY(), PLAYERWIDTH * SCALE, PLAYERHEIGHT * SCALE));

		this.speed = 4;

		this.playerRoller = new PlayerRoller(this);
		this.playerAttacker = new PlayerAttacker(this, facade);
		this.playerAnimator = new PlayerAnimator(this, facade);

		this.initialize();
	}

	public void addHealth(int value) {
		this.health += value;
	}

	@Override
	public void damage(int amount) {
		if (!this.playerRoller.isRolling()) {
			this.health -= amount;
		}
	}

	@Override
	public void die() {
		this.currentAnimation = this.animationDead;
	}

	public int getCurrentHealth() {
		return this.health;
	}

	public Rectangle getHitbox() {
		return this.hitBox;
	}

	public Vector getPreviousDirection() {
		return this.previousDirection;
	}

	@Override
	public void render(Graphics2D g) {

		g.drawImage(this.currentAnimation.getCurrentFrame(), this.xMoveWithCamera(), this.yMoveWithCamera(),
				this.position.getWidth(), this.position.getHeight(), null);

		this.drawHitBox(g);

		if (this.playerAttacker.isAttacking()) {
			this.drawWeaponDamageBox(g);
		}
	}

	public void setHealth(int hp) {
		this.health = hp;
	}

	@Override
	public void showHealthBar(Graphics2D g) {

		float rangeHealthBar = (float) (MAX_HEALTH - 1) / ((float) Assets.healthBars.length - 1);
		int currentHealthBarToShow = (int) ((float) (MAX_HEALTH - this.health) / rangeHealthBar);

		int maxHealthBar = 28;

		if (currentHealthBarToShow < 0)
			currentHealthBarToShow = 0;
		if (currentHealthBarToShow > maxHealthBar)
			currentHealthBarToShow = maxHealthBar;

		int x = 20;
		int y = 20;
		int width = 390;
		int height = 70;
		double scale = 0.75;

		g.drawImage(Assets.healthBars[currentHealthBarToShow], x, y, (int) (width * scale), (int) (height * scale),
				null);

	}

	@Override
	public void update() {

		if (this.health <= 0) {
			this.die();
		}

		this.updateAnimation();

		if (this.playerAttacker.isAttacking()) {
			this.playerAnimator.attack();
			this.playerAttacker.checkAttacks();
		} else if (this.health > 0) {
			this.playerMovement();
		}

	}

	@Override
	protected void chooseCurrentAnimation() {

		if (this.isMovingUp()) {
			this.playerAnimator.setAnimation(this.animationMoveUp, this.playerAnimator.getSprintUpAnimation());
		}
		if (this.isMovingDown()) {
			this.playerAnimator.setAnimation(this.animationMoveDown, this.playerAnimator.getSprintDownAnimation());
		}
		if (this.isMovingLeft()) {
			this.playerAnimator.setAnimation(this.animationMoveLeft, this.playerAnimator.getSprintLeftAnimation());
		}
		if (this.isMovingRight()) {
			this.playerAnimator.setAnimation(this.animationMoveRight, this.playerAnimator.getSprintRightAnimation());
		}
		if (this.isNotMoving()) {
			this.currentAnimation = this.animationIdle;
		}
		if (this.playerRoller.isRolling()) {
			this.currentAnimation = this.playerAnimator.getRollAnimation();
		}
		if (this.playerAttacker.isAttacking()) {
			this.currentAnimation = this.playerAnimator.getAttackingAnimation();
		}
	}

	private void drawWeaponDamageBox(Graphics2D g) {

		if (this.facade.getDebugMode()) {
			this.equippedWeapon.render(g);
		}
	}

	private void getInput() {

		if (this.facade.getKeyManager().getUp()) {
			this.setYSpeed(-this.speed, -SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getDown()) {
			this.setYSpeed(this.speed, SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getLeft()) {
			this.setXSpeed(-this.speed, -SPRINTSPEED);
		}
		if (this.facade.getKeyManager().getRight()) {
			this.setXSpeed(this.speed, SPRINTSPEED);
		}
		if (this.facade.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && !this.isNotMoving()) {
			this.playerRoller.roll();
		}
		if (this.facade.getMouseManager().getLeftClicked()) {
			this.playerAttacker.aimAttack();
		}
	}

	private void initialize() {

		this.hitBox = new Rectangle((int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.7),
				(int) (PLAYERWIDTH * SCALE * 0.35), (int) (PLAYERHEIGHT * SCALE * 0.16));

		this.equippedWeapon = new Weapon(10, 800, new Rectangle(this.hitBox.getX() - this.hitBox.getWidth(),
				this.hitBox.getY(), this.hitBox.getWidth(), this.hitBox.getHeight()), this);

		this.playerAttacker.setAttackTimer(this.equippedWeapon.getCooldown());

		this.initializeAnimation();

		this.currentAnimation = this.animationIdle;

		this.setDebuggingColor(new Color(255, 102, 255, 200));
	}

	private void initializeAnimation() {

		this.animationMoveDown = new Animation(PlayerAnimator.ANIMATIONSPEED, Assets.playerAnimationDown);
		this.animationMoveRight = new Animation(PlayerAnimator.ANIMATIONSPEED, Assets.playerAnimationRight);
		this.animationMoveUp = new Animation(PlayerAnimator.ANIMATIONSPEED, Assets.playerAnimationUp);
		this.animationMoveLeft = new Animation(PlayerAnimator.ANIMATIONSPEED, Assets.playerAnimationLeft);
		this.animationIdle = new Animation(PlayerAnimator.ANIMATIONSPEED, Assets.playerAnimationIdle);
		this.animationDead = new Animation(1, Assets.playerDeadAnimation);
		
		this.playerAnimator.initialize();

	}

	private void playerMovement() {

		this.getInput();

		if (this.playerRoller.isRolling()) {
			this.playerRoller.rollingMove();
		} else {
			this.move();
		}

		this.chooseCurrentAnimation();
		this.resetMovement();

		this.facade.getGameCamera().centerOnEntity(this);
	}

	private void setXSpeed(int speed, int sprintSpeed) {

		this.addXMove(this.facade.getKeyManager().getSprint() ? sprintSpeed : speed);

	}

	private void setYSpeed(int speed, int sprintSpeed) {

		this.addYMove(this.facade.getKeyManager().getSprint() ? sprintSpeed : speed);
	}

	private void updateAnimation() {

		this.animationIdle.update();
		this.animationMoveUp.update();
		this.animationMoveRight.update();
		this.animationMoveLeft.update();
		this.animationMoveDown.update();

		this.playerAnimator.update();
		
		this.currentAnimation.update();
	}

	public Weapon getEquippedWeapon() {

		return this.equippedWeapon;
	}

	public PlayerAttacker getPlayerAttacker() {
		return this.playerAttacker;
	}

	public void setCurrentAnimation(Animation current) {
		this.currentAnimation = current;

	}

}
