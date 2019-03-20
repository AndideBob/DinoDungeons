package dinodungeons.game.gameobjects.item;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.particles.Explosion;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.physics.collision.base.Collider;

public class ItemBombObject extends GameObject {

	private int positionX;
	private int positionY;
	
	private long bombTimer;
	private boolean exploded;
	
	private long blinkTimer;
	private long nextBlinkChange;
	private boolean blinkingRed;
	
	public ItemBombObject(int spawnX, int spawnY) {
		super(GameObjectTag.ITEM_BOMB);
		positionX = spawnX;
		positionY = spawnY;
		exploded = false;
		bombTimer = DinoDungeonsConstants.bombFuseTimer;
		nextBlinkChange = DinoDungeonsConstants.bombBlinkTimerStart;
		blinkTimer = nextBlinkChange;
		blinkingRed = false;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!exploded) {
			bombTimer -= deltaTimeInMs;
			blinkTimer -= deltaTimeInMs;
			if(blinkTimer <= 0) {
				nextBlinkChange -= DinoDungeonsConstants.bombBlinkTimerReduction;
				blinkTimer = Math.max(DinoDungeonsConstants.minBombBlinkTimer, nextBlinkChange);
				blinkingRed = ! blinkingRed;
			}
			if(bombTimer <= 0) {
				exploded = true;
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new Explosion(positionX + 8, positionY + 8));
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.WEAPON_BOMB).draw(blinkingRed ? 1 : 0, anchorX + positionX, anchorY + positionY);
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.emptyList();
	}

	@Override
	public boolean shouldBeDeleted() {
		return exploded;
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}
	
}
