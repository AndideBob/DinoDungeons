package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.BuildingTypeButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.exits.building.BuildingType;

public class BuildingTypeChangeButton extends BaseButton {
	
	private BuildingTypeButtonGroup belongingButtonGroup;
	
	private BuildingType buildingType;
	
	public BuildingTypeChangeButton(int positionX, int positionY, final BuildingTypeButtonGroup belongingButtonGroup, BuildingType buildingType) {
		super(positionX, positionY, getButtonSpriteForBuildingType(buildingType));
		this.belongingButtonGroup = belongingButtonGroup;
		this.buildingType = buildingType;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setBuildingType(buildingType);
	}
	
	private static ButtonSprite getButtonSpriteForBuildingType(BuildingType buildingType){
		switch (buildingType) {
		case BASIC_HUT:
			return ButtonSprite.BUILDING_BASIC_HUT;
		case STORE_A:
			return ButtonSprite.BUILDING_STORE_A;
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
