package dinodungeons.editor.ui.buttons.mapsettings;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.text.DrawTextManager;

public class ButtonSetMapTransition extends BaseButton {
	
	private Editor editorHandle;
	
	private EditorMapManager mapManagerHandle;
	
	private int direction;

	public ButtonSetMapTransition(int positionX, int positionY, Editor editorHandle, EditorMapManager mapManagerHandle, int direction) {
		super(positionX, positionY, getButtonSpriteForDirection(direction));
		this.direction = direction;
		this.editorHandle = editorHandle;
		this.mapManagerHandle = mapManagerHandle;
	}
	
	private static ButtonSprite getButtonSpriteForDirection(int direction){
		switch(direction){
		case DinoDungeonsConstants.directionDown:
			return ButtonSprite.SET_ROOM_DOWN;
		case DinoDungeonsConstants.directionUp:
			return ButtonSprite.SET_ROOM_UP;
		case DinoDungeonsConstants.directionLeft:
			return ButtonSprite.SET_ROOM_LEFT;
		default:
			return ButtonSprite.SET_ROOM_RIGHT;
		}
	}

	@Override
	protected void onClick() {
		switch(direction){
		case DinoDungeonsConstants.directionDown:
			editorHandle.waitForInput("Enter map id", InputUsage.TRANSITION_DOWN);
			break;
		case DinoDungeonsConstants.directionUp:
			editorHandle.waitForInput("Enter map id", InputUsage.TRANSITION_UP);
			break;
		case DinoDungeonsConstants.directionLeft:
			editorHandle.waitForInput("Enter map id", InputUsage.TRANSITION_LEFT);
			break;
		default:
			editorHandle.waitForInput("Enter map id", InputUsage.TRANSITION_RIGHT);
			break;
		}
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		if(hovering) {
			DrawTextManager.getInstance().drawText(258, 1, mapManagerHandle.getCurrentTransitionInDirection(direction), 6);
		}
	}
}
