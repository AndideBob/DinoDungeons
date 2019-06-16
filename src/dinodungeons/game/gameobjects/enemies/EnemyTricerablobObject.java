package dinodungeons.game.gameobjects.enemies;

import java.util.Collection;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.base.Collider;

public class EnemyTricerablobObject extends BaseEnemyObject {

	public EnemyTricerablobObject(int positionX, int positionY) {
		super(GameObjectTag.ENEMY_TRICERABLOB, positionX, positionY);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateAlways(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateAware(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateFleeing(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateIdle(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateStunned(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateDamaged(long deltaTimeInMs, InputInformation inputInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Collider> getColliders() {
		// TODO Auto-generated method stub
		return null;
	}

}
