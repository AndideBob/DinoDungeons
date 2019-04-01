package dinodungeons.game.gameobjects.general;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.particles.SmokeParticle;

public abstract class BaseDoorObject extends GameObject {

	protected boolean open;
	
	protected int positionX;
	protected int positionY;
	
	private GameObjectTag defaultTag;
	
	public BaseDoorObject(GameObjectTag tag, int positionX, int positionY) {
		super(tag);
		defaultTag = tag;
		this.positionX = positionX;
		this.positionY = positionY;
		checkOnRoomEntry();
	}
	
	protected final void open(boolean showEffects){
		open = true;
		tag = GameObjectTag.NONE;
		if(showEffects){
			dispenseDustParticles();
		}
	}
	
	protected final void close(boolean showEffects){
		open = false;
		tag = defaultTag;
		if(showEffects){
			dispenseDustParticles();
		}
	}
	
	private void dispenseDustParticles(){
		int particleAmount = 4 + DinoDungeonsConstants.random.nextInt(5);
		for(int i = 0; i < particleAmount; i++){
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new SmokeParticle(positionX, positionY));
		}
	}
	
	@Override
	public void resetOnRoomEntry() {
		checkOnRoomEntry();
	}
	
	protected abstract void checkOnRoomEntry();

}
