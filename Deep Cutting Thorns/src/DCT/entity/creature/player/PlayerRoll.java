package DCT.entity.creature.player;

public class PlayerRoll {

	private boolean isRolling;
	private int rollCurrentDistance;

	private Player player;

	private static final double ROLLDELTA = 0.1;
	private static final int ROLLBASEDISTANCE = 17;

	public PlayerRoll(Player pureya) {

		this.player = pureya;

		this.isRolling = false;
		this.rollCurrentDistance = ROLLBASEDISTANCE;
	}

	public void roll() {
		this.isRolling = true;
	}

	public boolean isRolling() {
		return this.isRolling;
	}

	public void rollingMove() {

		if (!this.player.checkEntityCollisions(this.player.getPreviousDirection().getX() * this.rollCurrentDistance,
				this.player.getPreviousDirection().getY() * this.rollCurrentDistance)) {

			this.chooseRollDirection();
		}

		this.rollCurrentDistance -= this.rollCurrentDistance * ROLLDELTA;
		if (this.rollCurrentDistance < 1) {
			this.rollCurrentDistance = ROLLBASEDISTANCE;
			this.isRolling = false;
		}
	}

	private void chooseRollDirection() {
		if (this.player.getPreviousDirection().getY() < 0) {
			this.rollUp();
		} else if (this.player.getPreviousDirection().getY() > 0) {
			this.rollDown();
		}
		if (this.player.getPreviousDirection().getX() < 0) {
			this.rollLeft();
		} else if (this.player.getPreviousDirection().getX() > 0) {
			this.rollRight();
		}
	}

	private void rollDown() {
		int futureY = this.player.getPositionY() + this.player.getHitbox().getY() + this.player.getHitbox().getHeight()
				+ this.rollCurrentDistance;

		boolean rollingCondition1 = !this.player
				.checkCollisionWithTile(this.player.getPositionX() + this.player.getHitbox().getX(), futureY);
		boolean rollingCondition2 = !this.player.checkCollisionWithTile(
				this.player.getPositionX() + this.player.getHitbox().getX() + this.player.getHitbox().getWidth(),
				futureY);

		if (rollingCondition1 && rollingCondition2) {
			this.player.setY(this.player.getPositionY() + this.rollCurrentDistance);
		}
	}

	private void rollLeft() {
		int futureX = this.player.getPositionX() + this.player.getHitbox().getX() - this.rollCurrentDistance;

		boolean rollingCondition1 = !this.player.checkCollisionWithTile(futureX,
				this.player.getPositionY() + this.player.getHitbox().getY());
		boolean rollingCondition2 = !this.player.checkCollisionWithTile(futureX,
				this.player.getPositionY() + this.player.getHitbox().getY() + this.player.getHitbox().getHeight());

		if (rollingCondition1 && rollingCondition2) {
			this.player.setX(this.player.getPositionX() - this.rollCurrentDistance);
		}
	}

	private void rollRight() {
		int futureX = this.player.getPositionX() + this.player.getHitbox().getX() + this.player.getHitbox().getWidth()
				+ this.rollCurrentDistance;

		boolean rollingCondition1 = !this.player.checkCollisionWithTile(futureX,
				this.player.getPositionY() + this.player.getHitbox().getY());
		boolean rollingCondition2 = !this.player.checkCollisionWithTile(futureX,
				this.player.getPositionY() + this.player.getHitbox().getY() + this.player.getHitbox().getHeight());

		if (rollingCondition1 && rollingCondition2) {
			this.player.setX(this.player.getPositionX() + this.rollCurrentDistance);
		}
	}

	private void rollUp() {
		int futureY = this.player.getPositionY() + this.player.getHitbox().getY() - this.rollCurrentDistance;

		boolean rollingCondition1 = !this.player
				.checkCollisionWithTile(this.player.getPositionX() + this.player.getHitbox().getX(), futureY);
		boolean rollingCondition2 = !this.player.checkCollisionWithTile(
				this.player.getPositionX() + this.player.getHitbox().getX() + this.player.getHitbox().getWidth(),
				futureY);

		if (rollingCondition1 && rollingCondition2) {
			this.player.setY(this.player.getPositionY() - this.rollCurrentDistance);
		}
	}
}
