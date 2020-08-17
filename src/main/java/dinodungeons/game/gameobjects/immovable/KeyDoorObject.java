package dinodungeons.game.gameobjects.immovable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.GameEventManager;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.general.BaseDoorObject;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class KeyDoorObject extends BaseDoorObject {
	
	private long spawnDelayTimer;
	private RectCollider collider;
	
	private int colorVariant;
	
	private String openingEventKey;

	public KeyDoorObject(int positionX, int positionY, int colorVariant, String eventKey) {
		super(GameObjectTag.WALL, positionX, positionY);
		collider = new RectCollider(positionX, positionY, 16, 16);
		this.colorVariant = colorVariant;
		openingEventKey = eventKey;
		close(false);
	}

	@Override
	protected void checkOnRoomEntry() {
		spawnDelayTimer = DinoDungeonsConstants.doorSpawnDelay;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(spawnDelayTimer > 0){
			spawnDelayTimer -= deltaTimeInMs;
			if(GameEventManager.getInstance().hasEventOccured(openingEventKey)
					|| hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
				open(false);
			}
			else{
				close(false);
			}
		}
		else{
			if(open && !GameEventManager.getInstance().hasEventOccured(openingEventKey)){
				//Close after Player has walked off
				if(!hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
					close(true);
				}
			}
			else if(!open){
				if(GameEventManager.getInstance().hasEventOccured(openingEventKey)){
					open(true);
				}
				else{
					checkForUnlock();
				}
			}
		}
	}

	private void checkForUnlock() {
		if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
			if(PlayerInventoryManager.getInstance().hasKeysForCurrentDungeon()){
				PlayerInventoryManager.getInstance().decreaseKeysForCurrentDungeon(1);
				GameEventManager.getInstance().markEventAsOccured(openingEventKey);
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(!open){
			int positionOnSpriteSheet = 0 + colorVariant;
			SpriteManager.getInstance().getSprite(SpriteID.DOORS).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
		}
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
