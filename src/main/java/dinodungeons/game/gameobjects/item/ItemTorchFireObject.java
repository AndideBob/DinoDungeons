package dinodungeons.game.gameobjects.item;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class ItemTorchFireObject extends GameObject {
	
	private static final float movementSpeed = 32f;
	private static final float movementDistance = 16f;
	private static final long fireLifeTime = 2000L;
	
	private float positionX;
	private float positionY;
	
	private int outDirection;
	private float movementAmount;
	
	private long lifeTime;
	
	private int frame;
	private long nextFrameCounter;
	
	private RectCollider collider;
	
	public ItemTorchFireObject(int spawnX, int spawnY, int direction) {
		super(GameObjectTag.ITEM_FIRE);
		positionX = spawnX;
		positionY = spawnY;
		switch(direction) {
		case DinoDungeonsConstants.directionUp:
			positionY += 8;
			break;
		case DinoDungeonsConstants.directionDown:
			positionY -= 8;
			break;
		case DinoDungeonsConstants.directionLeft:
			positionX -= 8;
			break;
		case DinoDungeonsConstants.directionRight:
			positionX += 8;
			break;
		}
		lifeTime = 0L;
		outDirection = direction;
		movementAmount = 0f;
		collider = new RectCollider(spawnX, spawnY, 14, 10);
		updateColliderPosition();
		frame = 0;
		nextFrameCounter = 100L;
		//TODO: Fire sound effect
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		lifeTime += deltaTimeInMs;
		nextFrameCounter -= deltaTimeInMs;
		if(nextFrameCounter <= 0) {
			nextFrameCounter = 100;
			frame++;
			if(frame >= 4) {
				frame = 0;
			}
		}
		float timeChange = deltaTimeInMs / 1000f;
		float xChange = 0f;
		float yChange = 0f;
		if(movementAmount < movementDistance) {
			if(hasCollisionWithObjectWithTag(GameObjectTag.WALL, GameObjectTag.GENERAL_MOVEMENT_BLOCK)
					|| positionX <= 0f
					|| positionX >= DinoDungeonsConstants.mapWidth
					|| positionY <= 0f
					|| positionY >= DinoDungeonsConstants.mapHeight) {
				movementAmount = movementDistance;
			}
			else {
				switch (outDirection) {
				case DinoDungeonsConstants.directionDown:
					yChange = -1f;
					break;
				case DinoDungeonsConstants.directionUp:
					yChange = 1;
					break;
				case DinoDungeonsConstants.directionLeft:
					xChange = -1;
					break;
				case DinoDungeonsConstants.directionRight:
					xChange = 1;
					break;
				}
			}
			moveNormalized(xChange, yChange, timeChange);
		}
		updateColliderPosition();
	}

	private void moveNormalized(float changeX, float changeY, float timeChange){
		if(changeX == 0f && changeY == 0f) {
			return;
		}
		double vectorLength = Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2));
		double lengthFactor = 1 / vectorLength;
		changeX *= lengthFactor;
		changeY *= lengthFactor;
		positionX += changeX * movementSpeed * timeChange;
		positionY += changeY * movementSpeed * timeChange;
		movementAmount += Math.max(Math.abs(changeX), Math.abs(changeY)) * movementSpeed * timeChange;
		Logger.logDebug("FireMovement: " + movementAmount);
	}
	
	private void updateColliderPosition() {
		int x = Math.round(positionX);
		int y = Math.round(positionY);
		collider.setPositionX(x + 1);
		collider.setPositionY(y);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int x = anchorX + Math.round(positionX);
		int y = anchorY + Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.FIRE).draw(frame, x, y);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return lifeTime >= fireLifeTime;
	}

	@Override
	public boolean isTemporary() {
		return true;
	}
}
