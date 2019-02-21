package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class PlayerObject extends GameObject {

	private float positionX;
	private float positionY;
	
	private float movementSpeed;
	
	private RectCollider colliderXAxis;
	private RectCollider colliderYAxis;
	private HashSet<Collider> colliders; 
	
	//Movement based Variables
	private float movementChangeX;
	private float movementChangeY;
	private float predictedPositionX;
	private float predictedPositionY;
	private boolean hasMoved;
	
	public PlayerObject(GameObjectTag tag, int startX, int startY) {
		super(tag);
		positionX = startX;
		positionY = startY;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
		movementChangeX = 0;
		movementChangeY = 0;
		movementSpeed = 0.032f;
		colliderXAxis = new RectCollider(startX, startY, 16, 16);
		colliderYAxis = new RectCollider(startX, startY, 16, 16);
		colliders = new HashSet<>();
		colliders.add(colliderXAxis);
		colliders.add(colliderYAxis);
	}

	@Override
	public void update(long deltaTimeInMs) {
		move();
		updateControls(deltaTimeInMs);
		updateColliders();
	}

	private void move() {
		hasMoved = false;
		if(predictedPositionX != positionX){
			if(getCollisionTagForSpecificCollider(colliderXAxis.getKey()) != GameObjectTag.WALL){
				positionX = predictedPositionX;
				hasMoved = true;
			}
		}
		if(predictedPositionY != positionY){
			if(getCollisionTagForSpecificCollider(colliderYAxis.getKey()) != GameObjectTag.WALL){
				positionY = predictedPositionY;
				hasMoved = true;
			}
		}
		if(hasMoved){
			Logger.logDebug("Moved to [" + positionX + "," + positionY + "]");
		}
	}

	private void updateControls(long deltaTimeInMs){
		movementChangeX = 0;
		movementChangeY = 0;
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.PRESSED)
				|| InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.DOWN)){
			movementChangeY = 1;
		}
		else if(
				InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.PRESSED)
				|| InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.DOWN)){
			movementChangeY = -1;
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_LEFT).equals(ButtonState.PRESSED)
				|| InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_LEFT).equals(ButtonState.DOWN)){
			movementChangeX = -1;
		}
		else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_RIGHT).equals(ButtonState.PRESSED)
				|| InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_RIGHT).equals(ButtonState.DOWN)){
			movementChangeX = 1;
		}
		//Move diagonally at same speed
		if(movementChangeX != 0 && movementChangeY != 0){
			movementChangeX *= 0.7f;
			movementChangeY *= 0.7f;
		}
		predictedPositionX = positionX + (movementChangeX * movementSpeed * deltaTimeInMs);
		predictedPositionY = positionY + (movementChangeY * movementSpeed * deltaTimeInMs);
		
	}
	
	private void updateColliders(){
		colliderXAxis.setPositionX(Math.round(predictedPositionX));
		colliderXAxis.setPositionY(Math.round(positionY));
		colliderYAxis.setPositionX(Math.round(positionX));
		colliderYAxis.setPositionY(Math.round(predictedPositionY));
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Collider> getColliders() {
		return colliders;
	}

}
