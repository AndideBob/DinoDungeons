package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.HashSet;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.gfx.SpriteMap;
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
	private int lastDirection;
	private int frameNumber;
	private boolean blinking;
	private boolean wasBlinking;
	private SpriteMap characterSprite;
	
	private PlayerState playerState;
	
	private ItemID justCollectedItem;
	
	public PlayerObject(GameObjectTag tag, int startX, int startY) {
		super(tag);
		positionX = startX;
		positionY = startY;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
		movementChangeX = 0;
		movementChangeY = 0;
		movementSpeed = 0.05f;
		colliderXAxis = new RectCollider(startX, startY, 14, 14);
		colliderYAxis = new RectCollider(startX, startY, 14, 14);
		colliders = new HashSet<>();
		colliders.add(colliderXAxis);
		colliders.add(colliderYAxis);
		characterSprite = SpriteManager.getInstance().getSprite(SpriteID.PLAYER);
		playerState = PlayerState.DEFAULT;
	}

	@Override
	public void update(long deltaTimeInMs) {
		handleCollisions();
		switch (playerState) {
		case DAMAGE_TAKEN:
			if(msSinceLastFrame >= DinoDungeonsConstants.itemCollectionCharacterFreeze){
				msSinceLastFrame = 0;
				playerState = PlayerState.DEFAULT;
				characterSprite.setColorValues(1f, 1f, 1f, 1f);
				wasBlinking = false;
				blinking = false;
			}
			//VVVV Fall through VVVV
		case DEFAULT:
			move();
			updateControls(deltaTimeInMs);
			break;
		case ITEM_COLLECTED:
			if(msSinceLastFrame >= DinoDungeonsConstants.itemCollectionCharacterFreeze){
				msSinceLastFrame = 0;
				playerState = PlayerState.DEFAULT;
			}
			break;
		}
		updateColliders();
		updateShownFrame(deltaTimeInMs);
	}

	private void handleCollisions() {
		for(GameObjectTag tag : GameObjectTag.collectableItems){
			if(hasCollisionWithObjectWithTag(tag)){
				Logger.logDebug("Player collided with: " + tag.toString());
				switch(tag){
				case COLLECTABLE_ITEM_CLUB:
					collectItem(ItemID.CLUB);
					break;
				case COLLECTABLE_ITEM_ITEM_1:
					collectItem(ItemID.ITEM_1);
					break;
				case COLLECTABLE_ITEM_ITEM_2:
					collectItem(ItemID.ITEM_2);
					break;
				case COLLECTABLE_ITEM_ITEM_3:
					collectItem(ItemID.ITEM_3);
					break;
				case COLLECTABLE_ITEM_ITEM_4:
					collectItem(ItemID.ITEM_4);
					break;
				case COLLECTABLE_ITEM_ITEM_5:
					collectItem(ItemID.ITEM_5);
					break;
				case COLLECTABLE_ITEM_ITEM_6:
					collectItem(ItemID.ITEM_6);
					break;
				case COLLECTABLE_ITEM_ITEM_7:
					collectItem(ItemID.ITEM_7);
					break;
				case COLLECTABLE_ITEM_ITEM_8:
					collectItem(ItemID.ITEM_8);
					break;
				case COLLECTABLE_ITEM_ITEM_9:
					collectItem(ItemID.ITEM_9);
					break;
				case COLLECTABLE_ITEM_ITEM_A:
					collectItem(ItemID.ITEM_A);
					break;
				case COLLECTABLE_ITEM_ITEM_B:
					collectItem(ItemID.ITEM_B);
					break;
				case COLLECTABLE_ITEM_MIRROR:
					collectItem(ItemID.MIRROR);
					break;
				case COLLECTABLE_ITEM_ITEM_D:
					collectItem(ItemID.ITEM_D);
					break;
				case COLLECTABLE_ITEM_ITEM_E:
					collectItem(ItemID.ITEM_E);
					break;
				case COLLECTABLE_ITEM_ITEM_F:
					collectItem(ItemID.ITEM_F);
					break;
				case DAMAGING_IMMOVABLE:
					takeDamage(1);
					break;
				default:
					break;
				}
			}
		}
	}

	private void collectItem(ItemID itemID){
		justCollectedItem = itemID;
		playerState = PlayerState.ITEM_COLLECTED;
		PlayerStatusManager.getInstance().collectItem(justCollectedItem);
		msSinceLastFrame = 0;
	}
	
	private void takeDamage(int amount) {
		if(playerState != PlayerState.DAMAGE_TAKEN){
			PlayerStatusManager.getInstance().damage(amount);
			playerState = PlayerState.DAMAGE_TAKEN;
			msSinceLastFrame = 0;
		}
	}

	private void move() {
		hasMovedUp = false;
		hasMovedDown = false;
		hasMovedLeft = false;
		hasMovedRight = false;
		//Change Y Position
		if(predictedPositionY != positionY){
			boolean movementBlocked = false;
			for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderYAxis.getID())){
				if(GameObjectTag.movementBlockers.contains(tag)){
					movementBlocked = true;
				}
			}
			if(!movementBlocked){
				hasMovedDown = predictedPositionY < positionY;
				hasMovedUp = !hasMovedLeft;
				positionY = predictedPositionY;
			}
		}
		//Check for Y Transitions
		if(positionY < DinoDungeonsConstants.scrollBoundryDown){
			TransitionManager.getInstance().initiateScrollTransitionDown((int)positionX, (int)positionY);
			predictedPositionX = positionX; //Reset XPosition to avoid double Scrolling
		}
		else if(positionY > DinoDungeonsConstants.scrollBoundryUp){
			TransitionManager.getInstance().initiateScrollTransitionUp((int)positionX, (int)positionY);
			predictedPositionX = positionX; //Reset XPosition to avoid double Scrolling
		}
		//Change X Position
		if(predictedPositionX != positionX){
			boolean movementBlocked = false;
			for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderXAxis.getID())){
				if(GameObjectTag.movementBlockers.contains(tag)){
					movementBlocked = true;
				}
			}
			if(!movementBlocked){
				hasMovedLeft = predictedPositionX < positionX;
				hasMovedRight = !hasMovedLeft;
				positionX = predictedPositionX;
			}
		}
		//Check for X Transitions
		if(positionX < DinoDungeonsConstants.scrollBoundryLeft){
			TransitionManager.getInstance().initiateScrollTransitionLeft((int)positionX, (int)positionY);
		}
		else if(positionX > DinoDungeonsConstants.scrollBoundryRight){
			TransitionManager.getInstance().initiateScrollTransitionRight((int)positionX, (int)positionY);
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
		colliderXAxis.setPositionX((int)(predictedPositionX) + 1);
		colliderXAxis.setPositionY((int)(positionY) + 1);
		colliderYAxis.setPositionX((int)(positionX) + 1);
		colliderYAxis.setPositionY((int)(predictedPositionY) + 1);
	}
	
	private void updateShownFrame(long deltaTimeInMs){
		int actionNumber = 0;
		int directionNumber = lastDirection;
		msSinceLastFrame += deltaTimeInMs;
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
			lastDirection = directionNumber;
		}	
		switch(playerState){
		case DEFAULT:
			if(msSinceLastFrame >= msBetweenFrames){
				msSinceLastFrame -= msBetweenFrames;
				showEvenFrame = !showEvenFrame;
			}
			break;
		case DAMAGE_TAKEN:
			showEvenFrame = ((int) Math.floor(msSinceLastFrame / msBetweenFrames)) % 2 == 0;
			blinking = ((int) Math.floor(msSinceLastFrame / 60)) % 2 == 0;
			if(blinking != wasBlinking){
				float value = blinking ? 0f : 1f;
				characterSprite.setColorValues(1f, value, value, 1f);
				wasBlinking = blinking;
			}
			break;
		case ITEM_COLLECTED:
			actionNumber = 3;
			directionNumber = 0;
			showEvenFrame = false;
			break;
		}
		frameNumber = (actionNumber * 8) + (directionNumber * 2) + (showEvenFrame ? 0 : 1);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int x = anchorX + Math.round(positionX);
		int y = anchorY + Math.round(positionY);
		characterSprite.draw(frameNumber, x, y);
		switch (playerState) {
		case ITEM_COLLECTED:
			SpriteManager.getInstance().getSprite(SpriteID.ITEMS).draw(justCollectedItem.getSpriteSheetPosition(), x, y + 16);
		default:
			break;
		}
	}

	@Override
	public Collection<Collider> getColliders() {
		return colliders;
	}
	
	public void setPosition(int x, int y){
		positionX = x;
		positionY = y;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
	}

	private enum PlayerState{
		DEFAULT,
		ITEM_COLLECTED,
		DAMAGE_TAKEN;
	}
}
