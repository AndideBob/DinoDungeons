package dinodungeons.game.gameobjects.enemies;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.player.PlayerObject;

public abstract class BaseEnemyObject extends GameObject {

	protected EnemyState myState;
	
	protected float positionX;
	protected float positionY;
	
	protected boolean dead;
	
	public BaseEnemyObject(GameObjectTag tag, int positionX, int positionY) {
		super(tag);
		myState = EnemyState.IDLE;
		this.positionX = positionX;
		this.positionY = positionY;
		dead = false;
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return dead;
	}

	@Override
	public final void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!dead) {
			switch(myState) {
			case AWARE:
				updateAware(deltaTimeInMs, inputInformation);
				break;
			case FLEEING:
				updateFleeing(deltaTimeInMs, inputInformation);
				break;
			case IDLE:
				updateIdle(deltaTimeInMs, inputInformation);
				break;
			case STUNNED:
				updateStunned(deltaTimeInMs, inputInformation);
				break;
			case DAMAGED:
				updateDamaged(deltaTimeInMs, inputInformation);
			}
			updateAlways(deltaTimeInMs, inputInformation);
		}
	}

	protected abstract void updateAlways(long deltaTimeInMs, InputInformation inputInformation);

	protected abstract void updateAware(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void updateFleeing(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void updateIdle(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void updateStunned(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void updateDamaged(long deltaTimeInMs, InputInformation inputInformation);
	
	protected final double getDistanceToPlayer() {
		PlayerObject playerObject = GameObjectManager.getInstance().getPlayerObject();
		if(playerObject != null) {
			float distX = playerObject.getPositionX() - positionX;
			float distY = playerObject.getPositionY() - positionY;
			return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		}
		return Float.MAX_VALUE;
	}

}
