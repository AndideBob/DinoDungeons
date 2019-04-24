package dinodungeons.game.gameobjects.switches;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.gameplay.GameEventManager;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class CandleSwitch extends GameObject {
	
	private RectCollider collider;
	
	private int positionX;
	private int positionY;
	
	private boolean isLit;
	private int frame;
	private long nextFrameCounter;
	private RoomEvent switchEvent;
	
	public CandleSwitch(int positionX, int positionY, int colorVariant, RoomEvent switchEvent) {
		super(GameObjectTag.CANDLE_BLOCK);
		collider = new RectCollider(positionX+2, positionY+2, 12, 12);
		this.positionX = positionX;
		this.positionY = positionY;
		isLit = false;
		frame = 0;
		nextFrameCounter = 0L;
		this.switchEvent = switchEvent;
	}
	
	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(isLit){
			nextFrameCounter -= deltaTimeInMs;
			if(nextFrameCounter <= 0) {
				nextFrameCounter = 100;
				frame++;
				if(frame >= 5) {
					frame = 1;
				}
			}
		}
		else{
			if(hasCollisionWithObjectWithTag(GameObjectTag.ITEM_FIRE)){
				isLit = true;
				GameEventManager.getInstance().activateRoomEvent(switchEvent);
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.PUSHABLES).draw(frame, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
