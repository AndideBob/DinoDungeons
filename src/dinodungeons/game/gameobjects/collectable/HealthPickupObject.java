package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.gfx.SpriteMap;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class HealthPickupObject extends GameObject {
	
	private SpriteMap sprite;
	
	private int positionX;
	private int positionY;
	
	private boolean wasCollected;
	
	private RectCollider collider;
	
	public HealthPickupObject(int posX, int posY) {
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
				SoundManager.getInstance().playSoundEffect(SoundEffect.PICKUP_HEALTH);
				wasCollected = true;
				PlayerStatusManager.getInstance().heal(2);
			}
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return wasCollected;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.COLLECTABLES).draw(5, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
