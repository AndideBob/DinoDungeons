package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class CollectableItemObject extends GameObject {

	private int positionX;
	
	private int positionY;
	
	private ItemID itemID;
	
	private RectCollider collider;
	
	private boolean isValid;
	
	public CollectableItemObject(int positionX, int positionY, ItemID itemID) {
		super(getGameObjectTagForItemID(itemID));
		collider = new RectCollider(positionX, positionY, 16, 16);
		updateValidity();
		this.positionX = positionX;
		this.positionY = positionY;
		this.itemID = itemID;
	}
	
	private void updateValidity() {
		if(PlayerInventoryManager.getInstance() != null) {
			isValid = !PlayerInventoryManager.getInstance().getCollectedItems().contains(itemID);
		}
		else {
			isValid = true;
		}
	}

	private static GameObjectTag getGameObjectTagForItemID(ItemID itemID) {
		switch (itemID) {
		case CLUB:
			return GameObjectTag.COLLECTABLE_ITEM_CLUB;
		case BOOMERANG:
			return GameObjectTag.COLLECTABLE_ITEM_BOOMERANG;
		case TORCH:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_2;
		case BOMB:
			return GameObjectTag.COLLECTABLE_ITEM_BOMB;
		case ITEM_4:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_4;
		case ITEM_5:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_5;
		case ITEM_6:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_6;
		case ITEM_7:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_7;
		case ITEM_8:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_8;
		case ITEM_9:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_9;
		case ITEM_A:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_A;
		case ITEM_B:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_B;
		case ITEM_D:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_D;
		case ITEM_E:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_E;
		case ITEM_F:
			return GameObjectTag.COLLECTABLE_ITEM_ITEM_F;
		case MIRROR:
			return GameObjectTag.COLLECTABLE_ITEM_MIRROR;
		}
		return GameObjectTag.COLLECTABLE_ITEM_CLUB;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		
		if(isValid) {
			updateValidity();
			if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)) {
				isValid = false;
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(isValid){
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(itemID.getSpriteSheetPosition(), anchorX + positionX, anchorY + positionY);
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return !isValid;
	}

	@Override
	public Collection<Collider> getColliders() {
		return isValid ? Collections.singleton(collider) : Collections.emptyList();
	}

}
