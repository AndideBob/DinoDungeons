package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class BombPickupObject extends PickupObject {
	
	private boolean wasCollected;
	
	private RectCollider collider;
	
	public BombPickupObject(int posX, int posY) {
		super(GameObjectTag.COLLECTABLE_STATUS_GAIN);
		positionX = posX;
		positionY = posY;
		collider = new RectCollider(posX + 3, posY + 3, 4, 4);
		wasCollected = false;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!wasCollected){
			if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
				//TODO: Correct Sound
				SoundManager.getInstance().playSoundEffect(SoundEffect.PICKUP_HEALTH);
				wasCollected = true;
				PlayerStatusManager.getInstance().addBombs(1);
			}
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return wasCollected;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.COLLECTABLES).draw(4, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}
	
	@Override
	protected void updateColliderPosition() {
		collider.setPositionX(positionX + 3);
		collider.setPositionY(positionY + 3);
	}

}
