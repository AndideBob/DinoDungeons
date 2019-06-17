package dinodungeons.game.gameobjects.enemies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.enemies.utility.MovementChecker;
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
	private HashSet<Collider> colliders;
	
	private MovementChecker movementChecker;
	
	public EnemyTricerablobObject(int positionX, int positionY) {
		super(GameObjectTag.ENEMY_TRICERABLOB, positionX, positionY);
		direction = DinoDungeonsConstants.directionDown;
		showWalkingFrame = false;
		resetWaitTimer();
		movementChecker = new MovementChecker(positionX, positionY, 16, 16);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(movementChecker);
		colliderHead = new RectCollider(0, 0, 16, 16);
		colliderBack = new RectCollider(0, 0, 16, 16);
		colliders = new HashSet<>();
		colliders.add(colliderHead);
		colliders.add(colliderBack);
		updateColliders();
		myState = EnemyState.IDLE;
	}
	
	private void resetWaitTimer(){
		waitTimer = 500 + DinoDungeonsConstants.random.nextInt(1500);
	}
	
	private int selectNewDirection(Collection<Integer> blockedDirections){
		List<Integer> possibleDirections = new ArrayList<>();
		possibleDirections.addAll(Arrays.asList(DinoDungeonsConstants.directionDown, DinoDungeonsConstants.directionLeft,
				DinoDungeonsConstants.directionRight, DinoDungeonsConstants.directionUp));
		possibleDirections.removeAll(blockedDirections);
		if(possibleDirections.isEmpty()){
			return -1;
		}
		int newDir = possibleDirections.get(DinoDungeonsConstants.random.nextInt(possibleDirections.size()));
		if(movementChecker.canWalkInDirection(newDir)){
			return newDir;
		}
		else{
			blockedDirections.add(newDir);
			return selectNewDirection(blockedDirections);
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
			colliderHead.setHeight(7);
			colliderBack.setPositionX(x + 1);
			colliderBack.setPositionY(y + 8);
			colliderBack.setWidth(14);
			colliderBack.setHeight(7);
			break;
		case DinoDungeonsConstants.directionUp:
			colliderHead.setPositionX(x + 1);
			colliderHead.setPositionY(y + 8);
			colliderHead.setWidth(14);
			colliderHead.setHeight(7);
			colliderBack.setPositionX(x + 1);
			colliderBack.setPositionY(y + 1);
			colliderBack.setWidth(14);
			colliderBack.setHeight(7);
			break;
		case DinoDungeonsConstants.directionLeft:
			colliderHead.setPositionX(x + 1);
			colliderHead.setPositionY(y + 1);
			colliderHead.setWidth(7);
			colliderHead.setHeight(14);
			colliderBack.setPositionX(x + 8);
			colliderBack.setPositionY(y + 1);
			colliderBack.setWidth(7);
			colliderBack.setHeight(14);
			break;
		case DinoDungeonsConstants.directionRight:
			colliderHead.setPositionX(x + 8);
			colliderHead.setPositionY(y + 1);
			colliderHead.setWidth(7);
			colliderHead.setHeight(14);
			colliderBack.setPositionX(x + 1);
			colliderBack.setPositionY(y + 1);
			colliderBack.setWidth(7);
			colliderBack.setHeight(14);
			break;
		}
		movementChecker.updateColliders(x, y);
	}
	
	private boolean hasDamagingCollision() {
		boolean blockDamage = false;
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderHead.getID())) {
			if(GameObjectTag.enemyDamagingObjects.contains(tag)){
				blockDamage = true;
				GameObjectManager.getInstance().getPlayerObject().tryStun(x + 8, y + 8);
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
			direction = selectNewDirection(new ArrayList<Integer>());
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
