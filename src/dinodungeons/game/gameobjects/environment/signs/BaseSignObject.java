package dinodungeons.game.gameobjects.environment.signs;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.data.gameplay.inventory.CollectableType;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.BombPickupObject;
import dinodungeons.game.gameobjects.collectable.HealthPickupObject;
import dinodungeons.game.gameobjects.collectable.MoneyPickupObject;
import dinodungeons.game.gameobjects.particles.LeafParticle;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.game.gameobjects.text.TextBoxTrigger;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public abstract class BaseSignObject extends GameObject {

	protected int positionX;
	protected int positionY;
	
	private RectCollider collider;
	
	public BaseSignObject(int positionX, int positionY, int colorVariant, TextBoxContent text) {
		super(GameObjectTag.GENERAL_MOVEMENT_BLOCK);
		this.positionX = positionX;
		this.positionY = positionY;
		collider = new RectCollider(positionX, positionY + 4, 16, 12);
		TextBoxTrigger trigger = new TextBoxTrigger(positionX + 4, positionY, 8, 8, text);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(trigger);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		// No update
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
