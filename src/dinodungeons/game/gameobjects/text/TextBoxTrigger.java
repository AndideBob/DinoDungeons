package dinodungeons.game.gameobjects.text;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import lwjgladapter.input.ButtonState;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;

public class TextBoxTrigger extends GameObject {

	private RectCollider collider;
	
	private int centerX;
	private int centerY;
	
	private LinkedList<TextBoxContent> textBoxContents;
	
	public TextBoxTrigger(GameObjectTag tag, int posX, int posY, int width, int height, TextBoxContent... textBoxContents) {
		super(GameObjectTag.TEXT_TRIGGER);
		centerX = posX + (width / 2);
		centerY = posY + (height / 2);
		this.textBoxContents = new LinkedList<>();
		for(TextBoxContent tbc : textBoxContents){
			this.textBoxContents.addLast(tbc);
		}
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(inputInformation.getA().equals(ButtonState.PRESSED)){
			boolean triggerText = false;
			for(Collision collision : getCollisionsWithObjectsWithTag(GameObjectTag.PLAYER)){
				float offsetX = centerX - collision.getPositionX();
				float offsetY = centerY - collision.getPositionY();
				if(Math.abs(offsetX) > Math.abs(offsetY)){
					if(offsetX > 0){
						triggerText = GameObjectManager.getInstance().getPlayerObject().getMovementDirection() == DinoDungeonsConstants.directionRight;
					}
					else{
						triggerText = GameObjectManager.getInstance().getPlayerObject().getMovementDirection() == DinoDungeonsConstants.directionLeft;
					}
				}
				else{
					if(offsetY > 0){
						triggerText = GameObjectManager.getInstance().getPlayerObject().getMovementDirection() == DinoDungeonsConstants.directionUp;
					}
					else{
						triggerText = GameObjectManager.getInstance().getPlayerObject().getMovementDirection() == DinoDungeonsConstants.directionDown;
					}
				}
				break;
			}
			if(triggerText){
				for(TextBoxContent tbc : textBoxContents){
					GameObjectManager.getInstance().queueTextBox(tbc);
				}
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		//Trigger does not draw
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.singleton(collider);
	}

}
