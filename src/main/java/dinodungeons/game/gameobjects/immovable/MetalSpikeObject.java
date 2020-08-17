package dinodungeons.game.gameobjects.immovable;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class MetalSpikeObject extends GameObject{
	
	private Collider collider;
	
	private int posX;
	private int posY;
	
	private long frameTimer;
	private int currentFrame;
	
	public MetalSpikeObject(int startX, int startY) {
		super(GameObjectTag.DAMAGING_IMMOVABLE);
		collider = new RectCollider(startX, startY, 14, 14);
		frameTimer = 0;
		currentFrame = 0;
		posX = startX;
		posY = startY;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		frameTimer = (frameTimer + deltaTimeInMs) % 1000;
		currentFrame = (frameTimer < 500) ? 0 : 1;
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
