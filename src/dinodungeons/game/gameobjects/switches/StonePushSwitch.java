package dinodungeons.game.gameobjects.switches;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.GameEventManager;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.general.BasePushable;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class StonePushSwitch extends BasePushable {

	private static final float movementSpeed = 16f;
	private static final float movementAmount = 16f;

	private int colorVariant;
	
	private RectCollider collider;
	
	private int movementDirection;
	private boolean moving;
	
	private float distanceMoved;
	private float predictedX;
	private float predictedY;
	
	private int startX;
	private int startY;
	
	private boolean wasPushed;
	private RoomEvent switchEvent;
	
	public StonePushSwitch(int positionX, int positionY, int colorVariant, RoomEvent switchEvent) {
		super(positionX, positionY);
		this.colorVariant = colorVariant;
		collider = new RectCollider(positionX+2, positionY+2, 12, 12);
		moving = false;
		wasPushed = false;
		startX = positionX;
		startY = positionY;
		this.switchEvent = switchEvent;
	}

	@Override
	protected void updateSpecific(long deltaTimeInMs, InputInformation inputInformation) {
		if(moving){
			boolean movementBlocked = false;
			for(GameObjectTag tag : getCollisionTagsForSpecificCollider(collider.getID())){
				if(GameObjectTag.movementBlockers.contains(tag)){
					Logger.log("Push blocked by " + tag.toString());
					movementBlocked = true;
				}
			}
			if(!movementBlocked){
				float change = movementSpeed * deltaTimeInMs / 1000;
				distanceMoved += change;
				if(distanceMoved < movementAmount){
					positionX = (int)Math.round(predictedX);
					positionY = (int)Math.round(predictedY);
					switch(movementDirection){
					case DinoDungeonsConstants.directionDown:
						predictedY = startY - distanceMoved;
						break;
					case DinoDungeonsConstants.directionUp:
						predictedY = startY + distanceMoved;
						break;
					case DinoDungeonsConstants.directionRight:
						predictedX = startX + distanceMoved;
						break;
					case DinoDungeonsConstants.directionLeft:
						predictedX = startX - distanceMoved;
						break;
					}
					collider.setPositionX((int)Math.round(predictedX)+2);
					collider.setPositionY((int)Math.round(predictedY)+2);
				}
				else{
					switch(movementDirection){
					case DinoDungeonsConstants.directionDown:
						positionY = startY - (int)movementAmount;
						break;
					case DinoDungeonsConstants.directionUp:
						positionY = startY + (int)movementAmount;
						break;
					case DinoDungeonsConstants.directionRight:
						positionX = startX + (int)movementAmount;
						break;
					case DinoDungeonsConstants.directionLeft:
						positionX = startX - (int)movementAmount;
						break;
					}
					collider.setPositionX(positionX+2);
					collider.setPositionY(positionY+2);
					moving = false;
					GameEventManager.getInstance().activateRoomEvent(switchEvent);
				}
			}
			else{
				moving = false;
			}
		}
	}

	@Override
	protected void push(int direction) {
		moving = true;
		movementDirection = direction;
		wasPushed = true;
		predictedX = positionX;
		predictedY = positionY;
		distanceMoved = 0;
	}

	@Override
	protected boolean canBePushed() {
		return !wasPushed;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		int positionOnSpriteSheet = 0 + colorVariant;
		SpriteManager.getInstance().getSprite(SpriteID.PUSHABLES).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
