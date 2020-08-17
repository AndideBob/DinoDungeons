package dinodungeons.game.gameobjects.particles;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.base.Collider;

public abstract class BaseParticle extends GameObject {

	protected float positionX;
	protected float positionY;
	
	public BaseParticle(float startPositionX, float startPositionY) {
		super(GameObjectTag.PARTICLE);
		positionX = startPositionX;
		positionY = startPositionY;
	}

	@Override
	public final Collection<Collider> getColliders() {
		return Collections.emptyList();
	}

}
