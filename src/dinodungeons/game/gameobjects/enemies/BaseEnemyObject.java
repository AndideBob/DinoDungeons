package dinodungeons.game.gameobjects.enemies;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.HealthPickupObject;
import dinodungeons.game.gameobjects.particles.EnemyDestroyParticle;
import dinodungeons.game.gameobjects.player.PlayerObject;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;

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
	
	public boolean isRelevantForRoomSwitch(){
		return true;
	}
	
	protected final void die(){
		dead = true;
		int x = (int)Math.round(positionX);
		int y = (int)Math.round(positionY);
		dropPickup();
		GameObjectManager.getInstance().addGameObjectToCurrentMap(new EnemyDestroyParticle(x, y));
		SoundManager.getInstance().playSoundEffect(SoundEffect.DESTROY_ENEMY);
	}
	
	private void dropPickup() {
		if(PlayerStatusManager.getInstance().isHurt() && DinoDungeonsConstants.random.nextInt(3) == 0) {
			int x = (int)Math.round(positionX + 4);
			int y = (int)Math.round(positionY + 4);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new HealthPickupObject(x, y));
		}
	}
	
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
