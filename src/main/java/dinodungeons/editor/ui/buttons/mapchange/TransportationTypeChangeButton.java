package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.TransportTypeButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;

public class TransportationTypeChangeButton extends BaseButton {
	
	private TransportTypeButtonGroup belongingButtonGroup;
	
	private TransportationType transportType;
	
	public TransportationTypeChangeButton(int positionX, int positionY, final TransportTypeButtonGroup belongingButtonGroup, TransportationType transportType) {
		super(positionX, positionY, getButtonSpriteForTransportType(transportType));
		this.belongingButtonGroup = belongingButtonGroup;
		this.transportType = transportType;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setTransportType(transportType);;
	}
	
	private static ButtonSprite getButtonSpriteForTransportType(TransportationType transportType){
		switch (transportType) {
		case BLOCKED_CAVE_ENTRY:
			return ButtonSprite.EXIT_BLOCKED_CAVE_ENTRANCE;
		case CAVE_ENTRY:
			return ButtonSprite.EXIT_CAVE_ENTRANCE;
		case CAVE_EXIT:
			return ButtonSprite.EXIT_CAVE_EXIT;
		case INSTANT_TELEPORT:
			return ButtonSprite.EXIT_INSTANT;
		case STAIRS:
			return ButtonSprite.EXIT_STAIRS;
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
