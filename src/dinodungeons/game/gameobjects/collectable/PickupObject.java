package dinodungeons.game.gameobjects.collectable;

import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;

public abstract class PickupObject extends GameObject {
	
	private static final int spriteSize = 10;

	protected int positionX;
	protected int positionY;
	
	public PickupObject(GameObjectTag tag) {
		super(tag);
	}
	
	public void setCenter(int centerX, int centerY) {
		positionX = centerX - spriteSize / 2;
		positionY = centerY - spriteSize / 2;
		updateColliderPosition();
	}
	
	protected abstract void updateColliderPosition();
}
