package dinodungeons.game.gameobjects.particles;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.CircleCollider;
import lwjgladapter.physics.collision.base.Collider;

public class Explosion extends GameObject {

	private long timer;
	
	private int animationFrame;
	
	private boolean doneExploding;
	
	private CircleCollider collider;
	
	private int originX;
	private int originY;
	
	public Explosion(int originX, int originY) {
		super(GameObjectTag.EXPLOSION);
		doneExploding = false;
		timer = 0;
		animationFrame = 0;
		this.originX = originX;
		this.originY = originY;
		collider = new CircleCollider(6f);
		collider.setPosition(originX, originY);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!doneExploding) {
			timer += deltaTimeInMs;
			if(timer >= DinoDungeonsConstants.explosionDurationTime) {
				doneExploding = true;
				collider.setRadius(0f);
			}
			else if(timer >= DinoDungeonsConstants.explosionDurationTime * 0.75f) {
				animationFrame = 3;
				collider.setRadius(16f);
			}
			else if(timer >= DinoDungeonsConstants.explosionDurationTime * 0.5f) {
				animationFrame = 2;
				collider.setRadius(11f);
			}
			else if(timer >= DinoDungeonsConstants.explosionDurationTime * 0.25f) {
				animationFrame = 1;
				collider.setRadius(11f);
			}
			else {
				animationFrame = 0;
				collider.setRadius(6f);
			}
		}
		
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(!doneExploding) {
			SpriteManager.getInstance().getSprite(SpriteID.EXPLOSION).draw(animationFrame, anchorX + originX - 16, anchorY + originY - 16);
		}
	}

	@Override
	public Collection<Collider> getColliders() {
		if(!doneExploding) {
			return Collections.singleton(collider);
		}
		return Collections.emptyList();
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return doneExploding;
	}

}
