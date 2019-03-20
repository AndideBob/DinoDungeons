package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.base.Collider;

public class DropingWeaponObject extends GameObject {

	private long duration;
	
	public DropingWeaponObject(long duration) {
		super(GameObjectTag.NONE);
		this.duration = duration;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!shouldBeDeleted()) {
			duration -= deltaTimeInMs;
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		// Don't draw anything here
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.emptyList();
	}

	@Override
	public boolean shouldBeDeleted() {
		return duration > 0;
	}
}
