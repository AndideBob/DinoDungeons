package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.StaticObjectButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class StaticObjectChangeButton extends BaseButton {
	
	private StaticObjectButtonGroup belongingButtonGroup;
	
	private StaticObjectType staticObjectType;
	
	public StaticObjectChangeButton(int positionX, int positionY, final StaticObjectButtonGroup belongingButtonGroup, StaticObjectType staticObjectType) {
		super(positionX, positionY, getButtonSpriteForStaticObjectType(staticObjectType));
		this.staticObjectType = staticObjectType;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setObjectType(staticObjectType);
	}
	
	private static ButtonSprite getButtonSpriteForStaticObjectType(StaticObjectType staticObjectType){
		switch (staticObjectType) {
		case IMMOVABLE_BLOCK:
			return ButtonSprite.OBJECT_STONE_BLOCK;
		case DESTRUCTABLE_GRASS:
			return ButtonSprite.DESTRUTCTABLE_BUSH;
		case DESTRUCTABLE_STONE:
			return ButtonSprite.DESTRUTCTABLE_BLOCK;
		case SPIKES_METAL:
			return ButtonSprite.SPIKES_METAL;
		case SPIKES_WOOD:
			return ButtonSprite.SPIKES_WOOD;
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
	
	public enum StaticObjectType{
		IMMOVABLE_BLOCK,
		SPIKES_METAL,
		SPIKES_WOOD,
		DESTRUCTABLE_GRASS,
		DESTRUCTABLE_STONE
	}

}
