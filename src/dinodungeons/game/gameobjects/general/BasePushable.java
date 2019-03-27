package dinodungeons.game.gameobjects.general;

import java.util.Collection;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.base.Collision;

public abstract class BasePushable extends GameObject {
	
	private int pushDirection;
	private long pushTimer;
	
	protected int positionX;
	protected int positionY;

	public BasePushable(int positionX, int positionY) {
		super(GameObjectTag.PUSHABLE);
		this.positionX = positionX;
		this.positionY = positionY;
		pushDirection = DinoDungeonsConstants.directionDown;
	}

	@Override
	public final void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(canBePushed()){
			updatePushTimer(deltaTimeInMs);
			if(pushTimer <= 0){
				Logger.log("Push!");
				push(pushDirection);
			}
		}
		updateSpecific(deltaTimeInMs, inputInformation);
	}
	
	protected abstract void updateSpecific(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void push(int direction);
	
	protected abstract boolean canBePushed();
	
	private void updatePushTimer(long deltaTimeInMs){
		boolean isPushing = false;
		Collection<Collision> collisionsWithPlayer = getCollisionsWithObjectsWithTag(GameObjectTag.PLAYER);
		if(!collisionsWithPlayer.isEmpty()){
			Collision firstCollision = collisionsWithPlayer.iterator().next();
			int differenceX = positionX + 8 - firstCollision.getPositionX();
			int differenceY = positionY + 8 - firstCollision.getPositionY();
			Logger.log("Pushing: " + differenceX + "," + differenceY);
			if(differenceX != differenceY){
				isPushing = true;
				if(Math.abs(differenceX) > Math.abs(differenceY)){
					if(differenceX < 0){
						pushDirection = DinoDungeonsConstants.directionLeft;
					}
					else{
						pushDirection = DinoDungeonsConstants.directionRight;
					}
				}
				else{
					if(differenceY < 0){
						pushDirection = DinoDungeonsConstants.directionDown;
					}
					else{
						pushDirection = DinoDungeonsConstants.directionUp;
					}
				}
			}
		}
		if(isPushing){
			pushTimer -= deltaTimeInMs;
		}
		else{
			pushTimer = DinoDungeonsConstants.pushDelay;
		}
	}

}
