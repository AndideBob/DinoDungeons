package dinodungeons.game.gameobjects.collectable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class CollectableItemObject extends GameObject {

	private int positionX;
	
	private int positionY;
	
	private int itemID;
	
	private RectCollider collider;
	
	private boolean isValid;
	
	public CollectableItemObject(GameObjectTag tag, int positionX, int positionY, int itemID) {
		super(tag);
		collider = new RectCollider(positionX, positionY, 16, 16);
		//TODO: If player already owns the item don't spawn again
		isValid = true;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	@Override
	public void update(long deltaTimeInMs) {
		//TODO: Actually collecting the item
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(isValid){
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(itemID, anchorX + positionX, anchorY + positionY);
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return !isValid;
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
