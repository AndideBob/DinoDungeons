package dinodungeons.game.gameobjects.enemies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class EnemyTricerablobObject extends BaseEnemyObject {

	private static final long timeBetweenFrames = 100L;
	private static final float movementSpeed = 16;
	private static final float movementPerPhase = 16;
	
	private int direction;
	private boolean showWalkingFrame;
	private long animationTimer;
	private float movementInThisPhase;
	
	private long waitTimer;
	
	private RectCollider colliderHead;
	private RectCollider colliderBack;
	private RectCollider predictionColliderUp;
	private RectCollider predictionColliderDown;
	private RectCollider predictionColliderLeft;
	private RectCollider predictionColliderRight;
	private HashSet<Collider> colliders; 
	
	public EnemyTricerablobObject(int positionX, int positionY) {
		super(GameObjectTag.ENEMY_TRICERABLOB, positionX, positionY);
		direction = DinoDungeonsConstants.directionDown;
		showWalkingFrame = false;
		resetWaitTimer();
		colliderHead = new RectCollider(0, 0, 16, 16);
		colliderBack = new RectCollider(0, 0, 16, 16);
		predictionColliderUp = new RectCollider(0, 0, 16, 16);
		predictionColliderDown = new RectCollider(0, 0, 16, 16);
		predictionColliderLeft = new RectCollider(0, 0, 16, 16);
		predictionColliderRight = new RectCollider(0, 0, 16, 16);
		colliders = new HashSet<>();
		colliders.add(colliderHead);
		colliders.add(colliderBack);
		colliders.add(predictionColliderUp);
		colliders.add(predictionColliderDown);
		colliders.add(predictionColliderLeft);
		colliders.add(predictionColliderRight);
		updateColliders();
		myState = EnemyState.IDLE;
	}
	
	private void resetWaitTimer(){
		waitTimer = 500 + DinoDungeonsConstants.random.nextInt(1500);
	}
	
	private int selectNewDirection(int... blockedDirections){
		List<Integer> possibleDirections = Arrays.asList(DinoDungeonsConstants.directionDown, DinoDungeonsConstants.directionLeft,
				DinoDungeonsConstants.directionRight, DinoDungeonsConstants.directionUp);
		possibleDirections.removeAll(Arrays.asList(blockedDirections));
		if(possibleDirections.isEmpty()){
			return -1;
		}
		int newDir = possibleDirections.get(DinoDungeonsConstants.random.nextInt(possibleDirections.size()));
		Collection<GameObjectTag> collisionTags;
		switch (newDir) {
		case DinoDungeonsConstants.directionDown:
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderDown.getID());
			break;
		case DinoDungeonsConstants.directionLeft:
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderLeft.getID());
			break;
		case DinoDungeonsConstants.directionRight:
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderRight.getID());
			break;
		case DinoDungeonsConstants.directionUp:
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderUp.getID());
			break;
		default:
			collisionTags = Collections.emptyList();
			break;
		}
		boolean hasHinderingCollision = false;
		for(GameObjectTag tag : collisionTags) {
			if(GameObjectTag.movementBlockers.contains(tag)){
				hasHinderingCollision = true;
				break;
			}
		}
		if(!hasHinderingCollision){
			return newDir;
		}
		else{
			int[] newBlockedDirs = new int[blockedDirections.length + 1];
			for(int i = 0; i < blockedDirections.length; i++){
				newBlockedDirs[i] = blockedDirections[i];
			}
			newBlockedDirs[blockedDirections.length] = newDir;
			return selectNewDirection(newBlockedDirs);
		}
	}
	
	private void updateColliders(){
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		switch(direction){
		case DinoDungeonsConstants.directionDown:
			colliderHead.setPositionX(x + 1);
			colliderHead.setPositionY(y + 1);
			colliderHead.setWidth(14);
			colliderHead.setHeight(10);
			colliderBack.setPositionX(x + 3);
			colliderBack.setPositionY(y + 15);
			colliderBack.setWidth(10);
			colliderBack.setHeight(14);
			break;
		case DinoDungeonsConstants.directionUp:
			colliderHead.setPositionX(x + 1);
			colliderHead.setPositionY(y + 10);
			colliderHead.setWidth(14);
			colliderHead.setHeight(6);
			colliderBack.setPositionX(x + 2);
			colliderBack.setPositionY(y);
			colliderBack.setWidth(12);
			colliderBack.setHeight(10);
			break;
		case DinoDungeonsConstants.directionLeft:
			colliderHead.setPositionX(x);
			colliderHead.setPositionY(y + 1);
			colliderHead.setWidth(11);
			colliderHead.setHeight(14);
			colliderBack.setPositionX(x + 8);
			colliderBack.setPositionY(y + 1);
			colliderBack.setWidth(8);
			colliderBack.setHeight(10);
			break;
		case DinoDungeonsConstants.directionRight:
			colliderHead.setPositionX(x + 5);
			colliderHead.setPositionY(y + 1);
			colliderHead.setWidth(11);
			colliderHead.setHeight(14);
			colliderBack.setPositionX(x);
			colliderBack.setPositionY(y + 1);
			colliderBack.setWidth(8);
			colliderBack.setHeight(10);
			break;
		}
		predictionColliderUp.setPositionX(x + 4);
		predictionColliderUp.setPositionY(y + 8);
		predictionColliderUp.setWidth(8);
		predictionColliderUp.setHeight(12);
		predictionColliderDown.setPositionX(x + 4);
		predictionColliderDown.setPositionY(y - 4);
		predictionColliderDown.setWidth(8);
		predictionColliderDown.setHeight(12);
		predictionColliderRight.setPositionX(x + 8);
		predictionColliderRight.setPositionY(y + 4);
		predictionColliderRight.setWidth(12);
		predictionColliderRight.setHeight(8);
		predictionColliderLeft.setPositionX(x - 4);
		predictionColliderLeft.setPositionY(y + 4);
		predictionColliderLeft.setWidth(12);
		predictionColliderLeft.setHeight(8);
	}
	
	private boolean hasDamagingCollision() {
		boolean blockDamage = false;
		for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderHead.getID())) {
			if(GameObjectTag.enemyDamagingObjects.contains(tag)){
				blockDamage = true;
				break;
			}
		}
		for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderBack.getID())) {
			if(GameObjectTag.enemyDamagingObjects.contains(tag)){
				return !blockDamage;
			}
		}
		return false;
	}

	@Override
	protected void updateAlways(long deltaTimeInMs, InputInformation inputInformation) {
		updateColliders();
		checkForDamage();
	}
	
	private void checkForDamage() {
		if(hasDamagingCollision()) {
			die();
		}
	}

	@Override
	protected void updateAware(long deltaTimeInMs, InputInformation inputInformation) {
		animationTimer -= deltaTimeInMs;
		if(animationTimer <= 0){
			animationTimer = timeBetweenFrames;
			showWalkingFrame = !showWalkingFrame;
		}
		float movementChange = movementSpeed / 1000 * deltaTimeInMs;
		switch(direction){
		case DinoDungeonsConstants.directionDown:
			positionY -= movementChange;
			break;
		case DinoDungeonsConstants.directionUp:
			positionY += movementChange;
			break;
		case DinoDungeonsConstants.directionLeft:
			positionX -= movementChange;
			break;
		case DinoDungeonsConstants.directionRight:
			positionX += movementChange;
			break;
		}
		movementInThisPhase += movementChange;
		if(movementInThisPhase >= movementPerPhase){
			myState = EnemyState.IDLE;
		}
	}

	@Override
	protected void updateIdle(long deltaTimeInMs, InputInformation inputInformation) {
		showWalkingFrame = false;
		waitTimer -= deltaTimeInMs;
		if(waitTimer <= 0){
			direction = selectNewDirection();
			if(direction != -1){
				movementInThisPhase = 0f;
				myState = EnemyState.AWARE;
			}
			resetWaitTimer();
		}
	}
	
	@Override
	protected void updateFleeing(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateStunned(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateDamaged(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		int currentFrame = 2 * direction + (showWalkingFrame ? 1 : 0);
		SpriteManager.getInstance().getSprite(SpriteID.ENEMY_TRICERABLOB).draw(currentFrame, anchorX + x, anchorY + y);
	}

	@Override
	public Collection<Collider> getColliders() {
		return colliders;
	}

}
