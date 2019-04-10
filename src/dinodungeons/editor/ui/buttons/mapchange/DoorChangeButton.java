package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.UIButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;

public class DoorChangeButton extends BaseButton {

	private Editor editorHandle;
	
	private UIButtonGroup belongingButtonGroup;
	
	private DoorType doorType;
	
	public DoorChangeButton(int positionX, int positionY, final Editor editorHandle, final UIButtonGroup belongingButtonGroup, DoorType doorType) {
		super(positionX, positionY, getButtonSpriteForDoorType(doorType));
		this.doorType = doorType;
		this.editorHandle = editorHandle;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		editorHandle.setMapChange(MapChangeType.DOOR_PLACEMENT, doorType.getSaveRepresentation());
	}
	
	private static ButtonSprite getButtonSpriteForDoorType(DoorType doorType){
		switch (doorType) {
		case ENEMIES:
			return ButtonSprite.DOOR_ENEMY;
		case KEY:
			return ButtonSprite.DOOR_KEY_SMALL;
		case MASTER_KEY:
			return ButtonSprite.DOOR_KEY_BIG;
		case SWITCH_A:
			return ButtonSprite.DOOR_SWITCH_A;
		case SWITCH_AB:
			return ButtonSprite.DOOR_SWITCH_AB;
		case SWITCH_ABC:
			return ButtonSprite.DOOR_SWITCH_ABC;
		case SWITCH_ABCD:
			return ButtonSprite.DOOR_SWITCH_ABCD;
		case SWITCH_B:
			return ButtonSprite.DOOR_SWITCH_B;
		case SWITCH_C:
			return ButtonSprite.DOOR_SWITCH_C;
		case SWITCH_D:
			return ButtonSprite.DOOR_SWITCH_D;
		}
		return ButtonSprite.CANCEL;
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}

}
