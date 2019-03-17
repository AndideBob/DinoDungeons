package dinodungeons.game.gameobjects.enemies;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.HealthPickupObject;
import dinodungeons.game.gameobjects.particles.EnemyDestroyParticle;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class EnemyBatObject extends BaseEnemyObject {

	private int currentFrame;
	
	private long frameCounter;
	private boolean countingFramesUp;
	
	private RectCollider collider;
	
	private long flightTime;
	private long behaviourTime;
	
	private static final float movementSpeed = 32f;
	
	private float movementX;
	private float movementY;
	
	public EnemyBatObject(int positionX, int positionY) {
		super(GameObjectTag.ENEMY_BAT, positionX, positionY);
		currentFrame = 0;
		countingFramesUp = true;
		collider =  new RectCollider(positionX, positionY, 16, 13);
	}

	@Override
	protected void updateAware(long deltaTimeInMs, InputInformation inputInformation) {
		flightTime -= deltaTimeInMs;
		if(flightTime <= 0) {
			if(getDistanceToPlayer() < 64) {
				flightTime = 1000 + DinoDungeonsConstants.random.nextInt(2000);
				updateAware(0, inputInformation);
			}
			else {
				myState = EnemyState.IDLE;
				currentFrame = 0;
			}
		}
		else {
			updateAnimationFrame(deltaTimeInMs);
			behaviourTime -= deltaTimeInMs;
			if(behaviourTime <= 0) {
				behaviourTime = 300 + DinoDungeonsConstants.random.nextInt(900);
				setNewRandomBehaviour();
			}
			else {
				float movementChange = movementSpeed / 1000 * deltaTimeInMs;
				positionX += movementX * movementChange;
				positionY += movementY * movementChange;
			}
		}
	}
	
	private void checkForDamage() {
		if(hasCollisionWithObjectWithTag(GameObjectTag.ITEM_CLUB)) {
			dead = true;
			int x = (int)Math.round(positionX);
			int y = (int)Math.round(positionY);
			dropPickup();
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new EnemyDestroyParticle(x, y));
			SoundManager.getInstance().playSoundEffect(SoundEffect.DESTROY_ENEMY);
		}
	}
	
	private void dropPickup() {
		if(PlayerStatusManager.getInstance().isHurt() && DinoDungeonsConstants.random.nextInt(3) == 0) {
			int x = (int)Math.round(positionX + 4);
			int y = (int)Math.round(positionY + 4);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new HealthPickupObject(x, y));
		}
	}

	private void setNewRandomBehaviour() {
		float movementEstimate = behaviourTime / 1000f * 0.7f * movementSpeed;
		boolean moveRight = DinoDungeonsConstants.random.nextBoolean();
		if(moveRight) {
			if(positionX + movementEstimate + 16 < DinoDungeonsConstants.mapWidth) {
				movementX = 1f;
			}
			else {
				movementX = -1f;
			}
		}
		else {
			if(positionX - movementEstimate > 0) {
				movementX = -1f;
			}
			else {
				movementX = 1f;
			}
		}
		boolean moveUp = DinoDungeonsConstants.random.nextBoolean();
		if(moveUp) {
			if(positionY + movementEstimate + 16 < DinoDungeonsConstants.mapHeight) {
				movementY = 1f;
			}
			else {
				movementY = -1f;
			}
		}
		else {
			if(positionY - movementEstimate > 0) {
				movementY = -1f;
			}
			else {
				movementY = 1f;
			}
		}
		normalizeMovementChange();
	}
	
	private void normalizeMovementChange(){
		double vectorLength = Math.sqrt(Math.pow(movementX, 2) + Math.pow(movementY, 2));
		double lengthFactor = 1 / vectorLength;
		movementX *= lengthFactor;
		movementY *= lengthFactor;
	}

	@Override
	protected void updateFleeing(long deltaTimeInMs, InputInformation inputInformation) {
		// Has no Fleeing State
		myState = EnemyState.IDLE;
	}

	@Override
	protected void updateIdle(long deltaTimeInMs, InputInformation inputInformation) {
		currentFrame = 0;
		if(getDistanceToPlayer() < 64) {
			myState = EnemyState.AWARE;
			countingFramesUp = true;
			currentFrame = 1;
			flightTime = 3000 + DinoDungeonsConstants.random.nextInt(2000);
			frameCounter = 84;
			behaviourTime = 750 + DinoDungeonsConstants.random.nextInt(750);
			setNewRandomBehaviour();
		}
	}
	
	@Override
	protected void updateDamaged(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateStunned(long deltaTimeInMs, InputInformation inputInformation) {
		// Has no Stunned State
		myState = EnemyState.IDLE;
	}

	@Override
	protected void updateAlways(long deltaTimeInMs, InputInformation inputInformation) {
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		collider.setPositionX(x);
		collider.setPositionY(y);
		checkForDamage();
	}
	
	private void updateAnimationFrame(long deltaTimeInMs) {
		frameCounter -= deltaTimeInMs;
		if(frameCounter <= 0) {
			frameCounter = 84;
			if(countingFramesUp) {
				currentFrame += 1;
				if(currentFrame >= 5) {
					countingFramesUp = false;
				}
			}
			else {
				currentFrame -= 1;
				if(currentFrame <= 1) {
					countingFramesUp = true;
				}
			}
		}
	}
	
	@Override
	public void draw(int anchorX, int anchorY) {
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		SpriteManager.getInstance().getSprite(SpriteID.ENEMY_BAT_GREEN).draw(currentFrame, anchorX + x - 4, anchorY + y);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}
	
}
