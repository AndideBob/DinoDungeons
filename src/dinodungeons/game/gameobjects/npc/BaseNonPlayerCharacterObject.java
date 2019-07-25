package dinodungeons.game.gameobjects.npc;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.text.DirectionalTextBoxTrigger;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.game.gameobjects.text.TriggerReactor;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;

public abstract class BaseNonPlayerCharacterObject extends GameObject implements TriggerReactor{

	protected DirectionalTextBoxTrigger triggerBottom;
	protected DirectionalTextBoxTrigger triggerTop;
	protected DirectionalTextBoxTrigger triggerLeft;
	protected DirectionalTextBoxTrigger triggerRight;
	
	protected int positionX;
	protected int positionY;
	
	protected int direction;
	
	private RectCollider collider;
	
	public BaseNonPlayerCharacterObject(int positionX, int positionY, Collection<TextBoxContent> text) {
		super(GameObjectTag.GENERAL_MOVEMENT_BLOCK);
		this.positionX = positionX;
		this.positionY = positionY;
		this.direction = DinoDungeonsConstants.directionDown;
		collider = new RectCollider(positionX, positionY + 4, 16, 12);
		triggerBottom = new DirectionalTextBoxTrigger(positionX + 4, positionY, 8, 8, this, DinoDungeonsConstants.directionDown, text);
		triggerTop = new DirectionalTextBoxTrigger(positionX + 4, positionY + 12, 8, 8, this, DinoDungeonsConstants.directionUp, text);
		triggerLeft = new DirectionalTextBoxTrigger(positionX - 4, positionY + 4, 8, 8, this, DinoDungeonsConstants.directionLeft, text);
		triggerRight = new DirectionalTextBoxTrigger(positionX + 12, positionY + 4, 8, 8, this, DinoDungeonsConstants.directionRight, text);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(triggerBottom);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(triggerTop);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(triggerLeft);
		GameObjectManager.getInstance().addGameObjectToCurrentMap(triggerRight);
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		// No update
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}
	
	@Override
	public void trigger(int direction) {
		this.direction = direction;
	}

}
