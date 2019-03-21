package dinodungeons.game.gameobjects.item;

import java.util.Collection;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.base.Collider;

public class ItemBoomerangObject extends GameObject {
	
	private float positionX;
	private float positionY;

	private boolean flyingOut;
	private int outDirection;
	
	private boolean arrivedBack;
	
	public ItemBoomerangObject(int spawnX, int spawnY, int direction) {
		super(GameObjectTag.ITEM_BOOMERANG);
		arrivedBack = false;
		flyingOut = true;
		positionX = spawnX;
		positionY = spawnY;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(int anchorX, int anchorY) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Collider> getColliders() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return arrivedBack;
	}

}
