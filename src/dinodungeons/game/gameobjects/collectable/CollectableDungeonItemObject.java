package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.player.DungeonItemID;
import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class CollectableDungeonItemObject extends GameObject {

	private int positionX;
	
	private int positionY;
	
	private DungeonItemID itemID;
	
	private RectCollider collider;
	
	private boolean collected;
	
	public CollectableDungeonItemObject(int positionX, int positionY, DungeonItemID itemID) {
		super(getGameObjectTagForItemID(itemID));
		collider = new RectCollider(positionX, positionY, 16, 16);
		collected = false;
		this.positionX = positionX;
		this.positionY = positionY;
		this.itemID = itemID;
	}

	private static GameObjectTag getGameObjectTagForItemID(DungeonItemID itemID) {
		switch (itemID) {
		case KEY_SMALL:
			return GameObjectTag.COLLECTABLE_KEY_SMALL;
		case KEY_BIG:
			return GameObjectTag.COLLECTABLE_KEY_BIG;
		case MAP:
			return GameObjectTag.COLLECTABLE_MAP;
		}
		return GameObjectTag.COLLECTABLE_KEY_SMALL;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)) {
			collected = true;
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(!collected){
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(itemID.getSpriteSheetPosition(), anchorX + positionX, anchorY + positionY);
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return collected;
	}

	@Override
	public Collection<Collider> getColliders() {
		return !collected ? Collections.singleton(collider) : Collections.emptyList();
	}

}
