package dinodungeons.game.gameobjects.general;

import java.util.Collection;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.physics.collision.base.Collider;

public abstract class BaseExplodable extends GameObject {

	private boolean exploded;
	
	public BaseExplodable() {
		super(GameObjectTag.EXPLODABLE);
		exploded = false;
	}
	
	protected final void setExplodedManually(boolean triggerExplosionEffect){
		if(!exploded){
			exploded = true;
			for(Collider c : getCollidersBeforeExplosion()) {
				c.unregister();
			}
			if(triggerExplosionEffect){
				explode();
			}
		}
	}

	@Override
	public final void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(exploded) {
			updateAfterExplosion(deltaTimeInMs, inputInformation);
		}
		else {
			updateBeforeExplosion(deltaTimeInMs, inputInformation);
		}
		checkForExplosion();
	}
	
	private void checkForExplosion() {
		if(!exploded){
			if(hasCollisionWithObjectWithTag(GameObjectTag.EXPLOSION)) {
				exploded = true;
				explode();
				for(Collider c : getCollidersBeforeExplosion()) {
					c.unregister();
				}
			}
		}
	}

	@Override
	public final void draw(int anchorX, int anchorY) {
		if(exploded) {
			drawAfterExplosion(anchorX, anchorY);
		}
		else {
			drawBeforeExplosion(anchorX, anchorY);
		}
	}

	@Override
	public final Collection<Collider> getColliders() {
		if(exploded) {
			return getCollidersAfterExplosion();
		}
		else {
			return getCollidersBeforeExplosion();
		}
	}
	
	protected abstract void explode();
	
	protected abstract GameObjectTag getTagAfterExplosion();
	
	protected abstract void drawBeforeExplosion(int anchorX, int anchorY);
	
	protected abstract void drawAfterExplosion(int anchorX, int anchorY);
	
	protected abstract void updateBeforeExplosion(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract void updateAfterExplosion(long deltaTimeInMs, InputInformation inputInformation);
	
	protected abstract Collection<Collider> getCollidersBeforeExplosion();
	
	protected abstract Collection<Collider> getCollidersAfterExplosion();

}
