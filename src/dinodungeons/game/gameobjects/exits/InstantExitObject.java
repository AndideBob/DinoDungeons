package dinodungeons.game.gameobjects.exits;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.DinoDungeons;
import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class InstantExitObject extends GameObject {

	private DinoDungeons gameHandle;
	
	private RectCollider collider;
	
	private String targetMap;
	private int targetX;
	private int targetY;
	
	public InstantExitObject(GameObjectTag tag, int positionX, int positionY, DinoDungeons gameHandle, String targetMap, int targetX, int targetY) {
		super(tag);
		this.gameHandle = gameHandle;
		collider = new RectCollider(positionX * 16 + 6, positionY * 16 + 6, 4, 4);
		this.targetMap = targetMap;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public void update(long deltaTimeInMs) {
		if(hasCollisionWithObjectWithTag(GameObjectTag.PLAYER)){
			Logger.log("Teleporting Player to [" + targetMap + "] at [" + targetX + "," + targetY + "]");
			gameHandle.switchMapTeleport(targetMap, targetX * 16, targetY * 16);
		}
	}

	@Override
	public void draw() {
		SpriteManager.getInstance().getSprite(SpriteID.PLAYER).draw(0, collider.getPositionX(), collider.getPositionY());
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
