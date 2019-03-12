package dinodungeons.game.gameobjects.general;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class WallObject extends GameObject {

	private RectCollider collider;
	
	public WallObject(GameObjectTag tag, int positionX, int positionY, int width, int height) {
		super(tag);
		collider = new RectCollider(positionX, positionY, width, height);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		//Do nothing
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
