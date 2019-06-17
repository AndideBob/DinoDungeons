package dinodungeons.game.gameobjects.enemies.utility;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class MovementChecker extends GameObject{

	private int sizeX;
	private int sizeY;
	
	private HashSet<Collider> colliders; 
	private RectCollider predictionColliderUp;
	private RectCollider predictionColliderDown;
	private RectCollider predictionColliderLeft;
	private RectCollider predictionColliderRight;
	
	public MovementChecker(int positionX, int positionY, int sizeX, int sizeY) {
		super(GameObjectTag.NONE);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		predictionColliderUp = new RectCollider(0, 0, 1, 1);
		predictionColliderDown = new RectCollider(0, 0, 1, 1);
		predictionColliderLeft = new RectCollider(0, 0, 1, 1);
		predictionColliderRight = new RectCollider(0, 0, 1, 1);
		colliders = new HashSet<>();
		colliders.add(predictionColliderUp);
		colliders.add(predictionColliderDown);
		colliders.add(predictionColliderLeft);
		colliders.add(predictionColliderRight);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		// Don't update on it's own
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		// Don't draw anything
	}

	@Override
	public Collection<Collider> getColliders() {
		return colliders;
	}
	
	public boolean canWalkInDirection(int direction){
		Collection<GameObjectTag> collisionTags;
		switch (direction) {
		case DinoDungeonsConstants.directionDown:
			if(predictionColliderDown.getPositionY() < 0) {
				return false;
			}
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderDown.getID());
			break;
		case DinoDungeonsConstants.directionLeft:
			if(predictionColliderDown.getPositionX() < 0) {
				return false;
			}
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderLeft.getID());
			break;
		case DinoDungeonsConstants.directionRight:
			if(predictionColliderDown.getPositionX() + 2 >= GameWindowConstants.DEFAULT_SCREEN_WIDTH) {
				return false;
			}
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderRight.getID());
			break;
		case DinoDungeonsConstants.directionUp:
			if(predictionColliderDown.getPositionY() + 2 >= GameWindowConstants.DEFAULT_SCREEN_HEIGHT) {
				return false;
			}
			collisionTags = getCollisionTagsForSpecificCollider(predictionColliderUp.getID());
			break;
		default:
			collisionTags = Collections.emptyList();
			break;
		}
		for(GameObjectTag tag : collisionTags) {
			if(GameObjectTag.movementBlockers.contains(tag)){
				return false;
			}
		}
		return true;
	}
	
	public void updateColliders(int x, int y){
		predictionColliderUp.setPositionX(x);
		predictionColliderUp.setPositionY(y + sizeY - 2);
		predictionColliderUp.setWidth(sizeX);
		predictionColliderUp.setHeight(4);
		
		predictionColliderDown.setPositionX(x);
		predictionColliderDown.setPositionY(y - 2);
		predictionColliderDown.setWidth(sizeX);
		predictionColliderDown.setHeight(4);
		
		predictionColliderRight.setPositionX(x + sizeX - 2);
		predictionColliderRight.setPositionY(y);
		predictionColliderRight.setWidth(4);
		predictionColliderRight.setHeight(sizeY);
		
		predictionColliderLeft.setPositionX(x - 2);
		predictionColliderLeft.setPositionY(y);
		predictionColliderLeft.setWidth(4);
		predictionColliderLeft.setHeight(sizeY);
	}

}
