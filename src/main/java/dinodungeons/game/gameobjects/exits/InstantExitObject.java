package dinodungeons.game.gameobjects.exits;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class InstantExitObject extends GameObject {
	
	private RectCollider collider;
	
	private String targetMap;
	private int targetX;
	private int targetY;
	
	public InstantExitObject(GameObjectTag tag, int positionX, int positionY, String targetMap, int targetX, int targetY) {
		super(tag);
		collider = new RectCollider(positionX + 6, positionY + 6, 4, 4);
		this.targetMap = targetMap;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
			TransitionManager.getInstance().initiateTransition(targetMap, targetX * 16, targetY * 16, TransitionType.INSTANT);
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		// Do nothing
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
