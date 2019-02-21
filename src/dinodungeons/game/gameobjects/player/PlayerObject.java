package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class PlayerObject extends GameObject {

	private float positionX;
	private float positionY;
	
	private float movementSpeed;
	
	private RectCollider collider;
	
	//Movement based Variables
	private float movementChangeX;
	private float movementChangeY;
	private float predictedPositionX;
	private float predictedPositionY;
	private boolean canMove;
	
	public PlayerObject(GameObjectTag tag, int startX, int startY) {
		super(tag);
		positionX = startX;
		positionY = startY;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
		canMove = false;
		movementChangeX = 0;
		movementChangeY = 0;
		movementSpeed = 0.032f;
		collider = new RectCollider(startX, startY, 16, 16);
	}

	@Override
	public void update(long deltaTimeInMs) {
		reactToCollisions();
		move();
		updateControls(deltaTimeInMs);
	}
	
	private void reactToCollisions() {
		canMove = true;
		if(hasCollisionWithObjectWithTag(GameObjectTag.WALL)){
			canMove = false;
		}
	}

	private void move() {
		if(canMove){
			positionX = predictedPositionX;
			positionY = predictedPositionY;
		}
	}

	private void updateControls(long deltaTimeInMs){
		movementChangeX = 0;
		movementChangeY = 0;
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.DOWN)){
			movementChangeY = 1;
		}
		else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.DOWN)){
			movementChangeY = -1;
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_LEFT).equals(ButtonState.DOWN)){
			movementChangeX = -1;
		}
		else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_RIGHT).equals(ButtonState.DOWN)){
			movementChangeX = 1;
		}
		//Move diagonally at same speed
		if(movementChangeX != 0 && movementChangeY != 0){
			movementChangeX *= 0.7f;
			movementChangeY *= 0.7f;
		}
		predictedPositionX = positionX + (movementChangeX * movementSpeed * deltaTimeInMs);
		predictedPositionY = positionY + (movementChangeY * movementSpeed * deltaTimeInMs);
		collider.setPositionX(Math.round(predictedPositionX));
		collider.setPositionY(Math.round(predictedPositionY));
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
