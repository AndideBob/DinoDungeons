package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.SwitchObjectButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class SwitchObjectChangeButton extends BaseButton {
	
	private SwitchObjectButtonGroup belongingButtonGroup;
	
	private int switchObjectID;
	
	public SwitchObjectChangeButton(int positionX, int positionY, final SwitchObjectButtonGroup belongingButtonGroup, int switchObjectID) {
		super(positionX, positionY, getButtonSpriteForSwitchObjectID(switchObjectID));
		this.switchObjectID = switchObjectID;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setObjectID(switchObjectID);
	}
	
	private static ButtonSprite getButtonSpriteForSwitchObjectID(int switchObjectID){
		switch (switchObjectID) {
		case 0:
			return ButtonSprite.OBJECT_STONE_BLOCK;
		case 1:
			return ButtonSprite.OBJECT_CANDLE;
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
