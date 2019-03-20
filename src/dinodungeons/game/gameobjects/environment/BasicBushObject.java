package dinodungeons.game.gameobjects.environment;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.BombPickupObject;
import dinodungeons.game.gameobjects.collectable.HealthPickupObject;
import dinodungeons.game.gameobjects.collectable.MoneyObject;
import dinodungeons.game.gameobjects.particles.LeafParticle;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public class BasicBushObject extends GameObject {

	private int positionX;
	private int positionY;
	
	private RectCollider collider;
	
	private int colorVariant;
	
	private boolean destroyed;
	
	public BasicBushObject(int positionX, int positionY, int colorVariant) {
		super(GameObjectTag.WALL);
		this.positionX = positionX;
		this.positionY = positionY;
		this.colorVariant = colorVariant;
		destroyed = false;
		collider = new RectCollider(positionX, positionY, 16, 16);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(hasDestructiveCollision() && !destroyed) {
			destroyed = true;
			spawnParticles();
			spawnLoot();
			SoundManager.getInstance().playSoundEffect(SoundEffect.DESTROY_BUSH);
		}
	}
	
	private boolean hasDestructiveCollision() {
		for(GameObjectTag tag : GameObjectTag.enemyDamagingObjects) {
			if(hasCollisionWithObjectWithTag(tag)) {
				return true;
			}
		}
		return false;
	}
	
	private void spawnLoot() {
		if(DinoDungeonsConstants.random.nextInt(4) == 0) {
			int x = positionX + 4;
			int y = positionY + 4;
			int item = DinoDungeonsConstants.random.nextInt(3);
			if(item == 0 && PlayerStatusManager.getInstance().isHurt()) {
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new HealthPickupObject(x, y));
			}
			else if(item == 1 && PlayerStatusManager.getInstance().needsBombs()) {
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new BombPickupObject(x, y));
			}
			else{
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new MoneyObject(GameObjectTag.COLLECTABLE_MONEY_OBJECT_VALUE_ONE, x, y));
			}
		}
	}

	private void spawnParticles() {
		int particleAmount = 3 + DinoDungeonsConstants.random.nextInt(5);
		for(int i = 0; i < particleAmount; i++) {
			int x = positionX + DinoDungeonsConstants.random.nextInt(9);
			int y = positionY + DinoDungeonsConstants.random.nextInt(9);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new LeafParticle(x, y, colorVariant));
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		if(!destroyed) {
			int positionOnSpriteSheet = 0 + colorVariant;
			SpriteManager.getInstance().getSprite(SpriteID.DESTRUCTABLES).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
		}
	}
	
	@Override
	public boolean shouldBeDeleted() {
		return destroyed;
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
