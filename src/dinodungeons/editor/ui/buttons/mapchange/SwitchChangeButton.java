package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.SwitchSelectionButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.RoomEvent;

public class SwitchChangeButton extends BaseButton {
	
	private SwitchSelectionButtonGroup belongingButtonGroup;
	
	private RoomEvent switchType;
	
	public SwitchChangeButton(int positionX, int positionY, final SwitchSelectionButtonGroup belongingButtonGroup, RoomEvent switchType) {
		super(positionX, positionY, getButtonSpriteForRoomEvent(switchType));
		this.switchType = switchType;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setSwitch(switchType);
	}
	
	private static ButtonSprite getButtonSpriteForRoomEvent(RoomEvent switchType){
		switch (switchType) {
		case SWITCH_A:
			return ButtonSprite.SWITCH_A;
		case SWITCH_AB:
			return ButtonSprite.SWITCH_AB;
		case SWITCH_ABC:
			return ButtonSprite.SWITCH_ABC;
		case SWITCH_ABCD:
			return ButtonSprite.SWITCH_ABCD;
		case SWITCH_B:
			return ButtonSprite.SWITCH_B;
		case SWITCH_C:
			return ButtonSprite.SWITCH_C;
		case SWITCH_D:
			return ButtonSprite.SWITCH_D;
		default:
			return ButtonSprite.CANCEL;
		}
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
