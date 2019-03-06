package dinodungeons.game.gameobjects.immovable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class WoodenSpikeObject extends GameObject{
	
	private Collider collider;
	
	private int posX;
	private int posY;
	
	private long frameTimer;
	private int currentFrame;
	
	public WoodenSpikeObject(int startX, int startY) {
		super(GameObjectTag.DAMAGING_IMMOVABLE);
		collider = new RectCollider(startX, startY, 14, 14);
		frameTimer = 0;
		currentFrame = 0;
		posX = startX;
		posY = startY;
	}

	@Override
	public void update(long deltaTimeInMs) {
		frameTimer = (frameTimer + deltaTimeInMs) % 1000;
		currentFrame = (frameTimer < 500) ? 2 : 3;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.SPIKES).draw(currentFrame, anchorX + posX, anchorY + posY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
