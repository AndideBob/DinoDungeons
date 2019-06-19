package dinodungeons.game.gameobjects.enemies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

public class EnemyCrocDropObject extends BaseEnemyObject {

	private static final long timeBetweenFrames = 100L;
	private static final float movementSpeed = 8;
	
	private int direction;
	private boolean showWalkingFrame;
	private long animationTimer;
	
	private long directionChangeTimer;
	
	private RectCollider collider;
	
	private MovementChecker movementChecker;
	
	public EnemyCrocDropObject(int positionX, int positionY) {
		super(GameObjectTag.ENEMY_TRICERABLOB, positionX, positionY);
		direction = DinoDungeonsConstants.directionDown;
		showWalkingFrame = false;
		resetDirectionChangeTimer();
		movementChecker = new MovementChecker(positionX + 1, positionY + 1, 10, 11);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(movementChecker);
		collider = new RectCollider(1, 1, 10, 11);
		updateColliders();
		myState = EnemyState.IDLE;
	}
	
	private void resetDirectionChangeTimer(){
		directionChangeTimer = 500 + DinoDungeonsConstants.random.nextInt(2500);
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
		collider.setPositionX(x + 1);
		collider.setPositionY(y + 1);
		movementChecker.updateColliders(x + 1, y + 1);
	}
	
	private boolean hasDamagingCollision() {
		for(GameObjectTag tag : getCollisionTagsForSpecificCollider(collider.getID())) {
			if(GameObjectTag.enemyDamagingObjects.contains(tag)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected void updateAlways(long deltaTimeInMs, InputInformation inputInformation) {
		directionChangeTimer -= deltaTimeInMs;
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
		if(!movementChecker.canWalkInDirection(direction) || directionChangeTimer <= 0){
			resetDirectionChangeTimer();
			if(DinoDungeonsConstants.random.nextBoolean()){
				myState = EnemyState.IDLE;
			}
			else{
				direction = selectNewDirection(new ArrayList<Integer>());
				if(direction == -1){
					myState = EnemyState.IDLE;
				}
			}
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
	}

	@Override
	protected void updateIdle(long deltaTimeInMs, InputInformation inputInformation) {
		showWalkingFrame = false;
		if(directionChangeTimer <= 0){
			resetDirectionChangeTimer();
			direction = selectNewDirection(new ArrayList<Integer>());
			if(direction != -1){
				myState = EnemyState.AWARE;
			}
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
		SpriteManager.getInstance().getSprite(SpriteID.ENEMY_CROCDROP).draw(currentFrame, anchorX + x, anchorY + y);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
