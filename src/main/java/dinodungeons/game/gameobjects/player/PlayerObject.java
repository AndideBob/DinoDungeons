package dinodungeons.game.gameobjects.player;

import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.utils.GameTextUtils;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;

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
	
	private int movementDirection;
	
	private boolean willTriggerTextBoxOnAPress;
	
	//Combat base Variables
	private long stateTimer;
	private GameObject weaponObject;
	
	private ItemUsageManager itemUsageManager;
	private PlayerDrawManager playerDrawManager;
	
	private PlayerState playerState;
	
	public PlayerObject(GameObjectTag tag, int startX, int startY) {
		super(tag);
		positionX = startX;
		positionY = startY;
		predictedPositionX = positionX;
		predictedPositionY = positionY;
		movementDirection = DinoDungeonsConstants.directionDown;
		movementChangeX = 0;
		movementChangeY = 0;
		movementSpeed = 0.05f;
		weaponObject = null;
		colliderXAxis = new RectCollider(startX, startY, 14, 14);
		colliderYAxis = new RectCollider(startX, startY, 14, 14);
		colliders = new HashSet<>();
		colliders.add(colliderXAxis);
		colliders.add(colliderYAxis);
		playerState = PlayerState.DEFAULT;
		stateTimer = 0;
		itemUsageManager = new ItemUsageManager();
		playerDrawManager = new PlayerDrawManager();
		willTriggerTextBoxOnAPress = false;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(stateTimer > 0){
			stateTimer -= deltaTimeInMs;
		}
		handleCollisions();
		switch (playerState) {
		case STUNNED:
			if(stateTimer <= DinoDungeonsConstants.stunTime - DinoDungeonsConstants.stunKnockbackTime) {
				movementChangeX = 0;
				movementChangeY = 0;
			}
		case DAMAGE_TAKEN:
			if(stateTimer <= 0){
				playerState = PlayerState.DEFAULT;
			}
			//VVVV Fallthrough VVVV
		case PUSHING:
		case DEFAULT:
			move();
			updateControls(deltaTimeInMs, inputInformation);
			break;
		case ITEM_COLLECTED:
			if(stateTimer <= 0){
				playerState = PlayerState.DEFAULT;
			}
			break;
		case USING_ITEM:
			if(weaponObject.shouldBeDeleted()) {
				weaponObject = null;
				playerState = PlayerState.DEFAULT;
			}
			break;
		}
		updateColliders();
		updateShownFrame(deltaTimeInMs);
	}

	private void handleCollisions() {
		//Handle Pushing
		if(playerState == PlayerState.DEFAULT || playerState == PlayerState.PUSHING){
			if(hasCollisionWithObjectWithTag(GameObjectTag.PUSHABLE)){
				playerState = PlayerState.PUSHING;
			}
			else{
				playerState = PlayerState.DEFAULT;
			}
		}
		//Handle ItemCollection
		for(GameObjectTag tag : GameObjectTag.collectableItems){
			if(hasCollisionWithObjectWithTag(tag)){
				collectItem(ItemID.getItemIDByGameObjectTag(tag));
			}
		}
		//Handle ItemCollection
		for(GameObjectTag tag : GameObjectTag.collectableDungeonItems){
			if(hasCollisionWithObjectWithTag(tag)){
				collectItem(DungeonItemID.getItemIDByGameObjectTag(tag));
			}
		}
		//Handle DamageTaking
		Collision damageCollision = null;
		GameObjectTag firstDamageObjectTag = GameObjectTag.NONE;
		for(GameObjectTag tag : GameObjectTag.playerDamagingObjects){
			for(Collision collision : getCollisionsWithObjectsWithTag(tag)){
				damageCollision = collision;
				firstDamageObjectTag = tag;
				break;
			}
			if(damageCollision != null){
				break;
			}
		}
		if(damageCollision != null){
			takeDamage(getDamageByTag(firstDamageObjectTag), damageCollision.getPositionX(), damageCollision.getPositionY());
		}
		//Handle TextTriggering
		willTriggerTextBoxOnAPress = false;
		if(hasCollisionWithObjectWithTag(GameObjectTag.TEXT_TRIGGER)){
			for(Collision collision : getCollisionsWithObjectsWithTag(GameObjectTag.TEXT_TRIGGER)){
				float offsetX = positionX + 8 - collision.getPositionX();
				float offsetY = positionY + 8 - collision.getPositionY();
				if(Math.abs(offsetX) > Math.abs(offsetY)){
					if(offsetX > 0){
						willTriggerTextBoxOnAPress = movementDirection == DinoDungeonsConstants.directionLeft;
					}
					else{
						willTriggerTextBoxOnAPress = movementDirection == DinoDungeonsConstants.directionRight;
					}
				}
				else{
					if(offsetY > 0){
						willTriggerTextBoxOnAPress = movementDirection == DinoDungeonsConstants.directionDown;
					}
					else{
						willTriggerTextBoxOnAPress = movementDirection == DinoDungeonsConstants.directionUp;
					}
				}
				if(willTriggerTextBoxOnAPress){
					break;
				}
			}
		}
	}

	private void collectItem(ItemID itemID){
		if(!PlayerInventoryManager.getInstance().getCollectedItems().contains(itemID)) {
			playerState = PlayerState.ITEM_COLLECTED;
			PlayerInventoryManager.getInstance().collectItem(itemID);
			playerDrawManager.setCollectedItem(itemID);
			stateTimer = DinoDungeonsConstants.itemCollectionCharacterFreeze;
			GameObjectManager.getInstance().queueTextBoxes(GameTextUtils.getItemCollectionTextBox(itemID));
		}
	}
	
	private void collectItem(DungeonItemID itemID){
		playerState = PlayerState.ITEM_COLLECTED;
		PlayerInventoryManager.getInstance().collectDungeonItem(itemID);
		playerDrawManager.setCollectedItem(itemID);
		stateTimer = DinoDungeonsConstants.dungeonItemCollectionCharacterFreeze;
	}
	
	private int getDamageByTag(GameObjectTag tag){
		switch(tag){
		case DAMAGING_IMMOVABLE:
			return 1;
		case ENEMY_TRICERABLOB:
		case ENEMY_BAT:
			return 1;
		
		case EXPLOSION:
			return 1;
		default:
			return 0;
		}
	}
	
	private void takeDamage(int amount, int sourceX, int sourceY) {
		if(playerState != PlayerState.DAMAGE_TAKEN && (playerState == PlayerState.STUNNED || stateTimer <= 0)){
			SoundManager.getInstance().playSoundEffect(SoundEffect.PLAYER_DAMAGE);
			PlayerStatusManager.getInstance().damage(amount);
			playerState = PlayerState.DAMAGE_TAKEN;
			if(weaponObject != null) {
				GameObjectManager.getInstance().destroyGameObjectImmediately(weaponObject);
				weaponObject = null;
			}
			movementChangeX = positionX + 8 - sourceX;
			movementChangeY = positionY + 8 - sourceY;
			stateTimer = DinoDungeonsConstants.invulnerabilityTime;
		}
	}
	
	public void tryStun(int sourceX, int sourceY) {
		if(playerState != PlayerState.DAMAGE_TAKEN && stateTimer <= 0){
			SoundManager.getInstance().playSoundEffect(SoundEffect.PLAYER_DAMAGE);
			playerState = PlayerState.STUNNED;
			if(weaponObject != null) {
				GameObjectManager.getInstance().destroyGameObjectImmediately(weaponObject);
				weaponObject = null;
			}
			movementChangeX = positionX + 8 - sourceX;
			movementChangeY = positionY + 8 - sourceY;
			stateTimer = DinoDungeonsConstants.stunTime;
		}
	}

	private void move() {
		boolean hasMoved = false;
		//Change Y Position
		if(predictedPositionY != positionY){
			boolean movementBlocked = false;
			for(GameObjectTag tag : getCollisionTagsForSpecificCollider(colliderYAxis.getID())){
				if(GameObjectTag.movementBlockers.contains(tag)){
					movementBlocked = true;
				}
			}
			if(!movementBlocked){
				if(predictedPositionY < positionY) {
					movementDirection = DinoDungeonsConstants.directionDown;
				}
				else {
					movementDirection = DinoDungeonsConstants.directionUp;
				}
				hasMoved = true;
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
				if(predictedPositionX < positionX) {
					movementDirection = DinoDungeonsConstants.directionLeft;
				}
				else {
					movementDirection = DinoDungeonsConstants.directionRight;
				}
				hasMoved = true;
				positionX = predictedPositionX;
			}
		}
		playerDrawManager.setMovemet(hasMoved, movementDirection);
		//Check for X Transitions
		if(positionX < DinoDungeonsConstants.scrollBoundryLeft){
			TransitionManager.getInstance().initiateScrollTransitionLeft((int)positionX, (int)positionY);
		}
		else if(positionX > DinoDungeonsConstants.scrollBoundryRight){
			TransitionManager.getInstance().initiateScrollTransitionRight((int)positionX, (int)positionY);
		}
	}

	private void updateControls(long deltaTimeInMs, InputInformation inputInformation){
		if(playerState == PlayerState.DEFAULT || playerState == PlayerState.PUSHING){
			movementChangeX = 0;
			movementChangeY = 0;
			boolean dontMoveAfterItemUse = false;
			if(playerState == PlayerState.DEFAULT){
				if(!willTriggerTextBoxOnAPress && inputInformation.getA().equals(ButtonState.PRESSED)) {
					dontMoveAfterItemUse = useItem(true);
				}
				if(inputInformation.getB().equals(ButtonState.PRESSED)) {
					dontMoveAfterItemUse = useItem(false);
				}
			}
			if(!dontMoveAfterItemUse) {
				if(inputInformation.getUp().equals(ButtonState.PRESSED)
						|| inputInformation.getUp().equals(ButtonState.DOWN)){
					movementChangeY = 1;
					movementDirection = DinoDungeonsConstants.directionUp;
				}
				else if(
						inputInformation.getDown().equals(ButtonState.PRESSED)
						|| inputInformation.getDown().equals(ButtonState.DOWN)){
					movementChangeY = -1;
					movementDirection = DinoDungeonsConstants.directionDown;
				}
				if(inputInformation.getLeft().equals(ButtonState.PRESSED)
						|| inputInformation.getLeft().equals(ButtonState.DOWN)){
					movementChangeX = -1;
					movementDirection = DinoDungeonsConstants.directionLeft;
				}
				else if(inputInformation.getRight().equals(ButtonState.PRESSED)
						|| inputInformation.getRight().equals(ButtonState.DOWN)){
					movementChangeX = 1;
					movementDirection = DinoDungeonsConstants.directionRight;
				}
			}
		}
		//Move diagonally at same speed
		if(movementChangeX != 0 || movementChangeY != 0){
			normalizeMovementChange();
		}
		predictedPositionX = positionX + (movementChangeX * movementSpeed * deltaTimeInMs);
		predictedPositionY = positionY + (movementChangeY * movementSpeed * deltaTimeInMs);
	}
	
	/***
	 * 
	 * @param aSlot Determines whether the A or the B button was pressed
	 * @return Returns true if the player is not allowed to move after using the item
	 */
	private boolean useItem(boolean aSlot) {
		ItemID usedItem = aSlot ? PlayerStatusManager.getInstance().getItemA() : PlayerStatusManager.getInstance().getItemB();
		itemUsageManager.setPositionX(getPositionX());
		itemUsageManager.setPositionY(getPositionY());
		itemUsageManager.setCurrentDirection(movementDirection);
		weaponObject = itemUsageManager.useItem(usedItem);
		if(weaponObject != null){
			playerState = PlayerState.USING_ITEM;
			return true;
		}
		return false;
	}
	
	private void normalizeMovementChange(){
		double vectorLength = Math.sqrt(Math.pow(movementChangeX, 2) + Math.pow(movementChangeY, 2));
		double lengthFactor = 1 / vectorLength;
		movementChangeX *= lengthFactor;
		movementChangeY *= lengthFactor;
	}
	
	private void updateColliders(){
		colliderXAxis.setPositionX((int)(predictedPositionX) + 1);
		colliderXAxis.setPositionY((int)(positionY) + 1);
		colliderYAxis.setPositionX((int)(positionX) + 1);
		colliderYAxis.setPositionY((int)(predictedPositionY) + 1);
	}
	
	private void updateShownFrame(long deltaTimeInMs){
		playerDrawManager.setPlayerState(playerState);
		playerDrawManager.update(deltaTimeInMs);
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int x = anchorX + Math.round(positionX);
		int y = anchorY + Math.round(positionY);
		playerDrawManager.draw(x, y);
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
	
	public int getPositionX() {
		return (int)Math.round(positionX);
	}
	
	public int getPositionY() {
		return (int)Math.round(positionY);
	}
	
	public int getMovementDirection(){
		return movementDirection;
	}

	public enum PlayerState{
		DEFAULT,
		PUSHING,
		ITEM_COLLECTED,
		DAMAGE_TAKEN,
		STUNNED,
		USING_ITEM;
	}
}
