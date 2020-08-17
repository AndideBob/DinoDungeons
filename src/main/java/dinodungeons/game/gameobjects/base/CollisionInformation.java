package dinodungeons.game.gameobjects.base;

import lwjgladapter.physics.collision.base.Collision;

public class CollisionInformation {

	private GameObjectTag tagOfOther;
	
	private Collision actualCollision;

	public CollisionInformation(GameObjectTag tagOfOther, Collision actualCollision) {
		this.tagOfOther = tagOfOther;
		this.actualCollision = actualCollision;
	}

	public GameObjectTag getTagOfOther() {
		return tagOfOther;
	}

	public Collision getActualCollision() {
		return actualCollision;
	}

}
