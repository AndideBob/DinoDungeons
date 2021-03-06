package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.inventory.CollectableType;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.gfx.SpriteMap;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class MoneyPickupObject extends PickupObject {

	private long animationTimer;
	
	private SpriteMap sprite;
	
	private boolean wasCollected;
	
	private RectCollider collider;
	
	public MoneyPickupObject(GameObjectTag tag, int posX, int posY) {
		super(tag);
		resetAnimationTimer();
		sprite = SpriteManager.getInstance().getSprite(SpriteID.COLLECTABLES);
		positionX = posX;
		positionY = posY;
		collider = new RectCollider(posX + 3, posY + 3, 4, 4);
		wasCollected = false;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!wasCollected){
			animationTimer -= deltaTimeInMs;
			if(animationTimer <= 0){
				resetAnimationTimer();
			}
			if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
				SoundManager.getInstance().playSoundEffect(SoundEffect.PICKUP_COIN);
				wasCollected = true;
				switch(tag){
				case COLLECTABLE_MONEY_OBJECT_VALUE_ONE:
					PlayerInventoryManager.getInstance().increase(CollectableType.MONEY, 1);
					break;
				case COLLECTABLE_MONEY_OBJECT_VALUE_FIVE:
					PlayerInventoryManager.getInstance().increase(CollectableType.MONEY, 5);
					break;
				case COLLECTABLE_MONEY_OBJECT_VALUE_TEN:
					PlayerInventoryManager.getInstance().increase(CollectableType.MONEY, 10);
					break;
				case COLLECTABLE_MONEY_OBJECT_VALUE_TWENTYFIVE:
					PlayerInventoryManager.getInstance().increase(CollectableType.MONEY, 25);
					break;
				default:
					Logger.logError("Money was incorrectly tagged: " + tag.toString());
					break;
				}
			}
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return wasCollected;
	}
	
	private void resetAnimationTimer(){
		animationTimer = 500 + DinoDungeonsConstants.random.nextInt(1000);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int currentFrame = 0;
		if(animationTimer < 100){
			currentFrame = 3;
		}
		else if(animationTimer < 200){
			currentFrame = 2;
		}
		else if(animationTimer < 300){
			currentFrame = 1;
		}
		switch(tag){
		case COLLECTABLE_MONEY_OBJECT_VALUE_ONE:
			sprite.setColorValues(0f, 0.8f, 1f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_FIVE:
			sprite.setColorValues(0f, 0.8f, 0f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_TEN:
			sprite.setColorValues(0.8f, 0f, 0f, 1f);
			break;
		case COLLECTABLE_MONEY_OBJECT_VALUE_TWENTYFIVE:
			sprite.setColorValues(0.8f, 0f, 0.8f, 1f);
			break;
		default:
			sprite.setColorValues(1f, 1f, 1f, 1f);
			break;
		}
		sprite.draw(currentFrame, anchorX + positionX, anchorY + positionY);
		sprite.setColorValues(1f, 1f, 1f, 1f);
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
