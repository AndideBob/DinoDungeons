package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.gfx.TileMap;
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
	
	//Graphics Releated Stuff
	private static final long msBetweenFrames = 150;
	private int msSinceLastFrame;
	private boolean showEvenFrame;
	private boolean hasMovedUp;
	private boolean hasMovedDown;
	private boolean hasMovedLeft;
	private boolean hasMovedRight;
	private int actionNumber;
	private int directionNumber;
	private int frameNumber;
	private TileMap characterSprite;
	
	public PlayerObject(GameObjectTag tag, int startX, int startY) {
		super(tag);
		positionX = startX;
		positionY = startY;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
		movementChangeX = 0;
		movementChangeY = 0;
		movementSpeed = 0.05f;
		colliderXAxis = new RectCollider(startX, startY, 16, 16);
		colliderYAxis = new RectCollider(startX, startY, 16, 16);
		colliders = new HashSet<>();
		colliders.add(colliderXAxis);
		colliders.add(colliderYAxis);
		characterSprite = SpriteManager.getInstance().getSprite(SpriteID.PLAYER);
	}

	@Override
	public void update(long deltaTimeInMs) {
		move();
		updateControls(deltaTimeInMs);
		updateColliders();
		updateShownFrame(deltaTimeInMs);
	}

	private void move() {
		hasMovedUp = false;
		hasMovedDown = false;
		hasMovedLeft = false;
		hasMovedRight = false;
		if(predictedPositionX != positionX){
			if(getCollisionTagForSpecificCollider(colliderXAxis.getKey()) != GameObjectTag.WALL){
				hasMovedLeft = predictedPositionX < positionX;
				hasMovedRight = !hasMovedLeft;
				positionX = predictedPositionX;
			}
		}
		if(predictedPositionY != positionY){
			if(getCollisionTagForSpecificCollider(colliderYAxis.getKey()) != GameObjectTag.WALL){
				hasMovedDown = predictedPositionY < positionY;
				hasMovedUp = !hasMovedLeft;
				positionY = predictedPositionY;
			}
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
	
	private void updateShownFrame(long deltaTimeInMs){
		actionNumber = 0;
		if(hasMovedUp
				|| hasMovedDown
				|| hasMovedLeft
				|| hasMovedRight){
			if(hasMovedDown){
				directionNumber = 0;
			}
			else if(hasMovedLeft){
				directionNumber = 1;
			}
			else if(hasMovedUp){
				directionNumber = 2;
			}
			else if(hasMovedRight){
				directionNumber = 3;
			}
			msSinceLastFrame += deltaTimeInMs;
			if(msSinceLastFrame >= msBetweenFrames){
				msSinceLastFrame -= msBetweenFrames;
				showEvenFrame = !showEvenFrame;
			}
		}		
		frameNumber = (actionNumber * 8) + (directionNumber * 2) + (showEvenFrame ? 0 : 1);
	}

	@Override
	public void draw() {
		characterSprite.draw(frameNumber, Math.round(positionX), Math.round(positionY));
	}

	@Override
	public Collection<Collider> getColliders() {
		return colliders;
	}

}
