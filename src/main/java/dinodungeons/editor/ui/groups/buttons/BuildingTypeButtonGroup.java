package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.BuildingTypeChangeButton;
import dinodungeons.editor.ui.groups.exits.ExitPlacementUIGroup;
import dinodungeons.game.gameobjects.exits.building.BuildingType;

public class BuildingTypeButtonGroup extends UIButtonGroup {
	
	private ExitPlacementUIGroup belongingGroup;
	
	private BuildingType selectedBuildingType;
	
	public BuildingTypeButtonGroup(final Editor editorHandle, final ExitPlacementUIGroup belongingGroup){
		super(editorHandle);
		this.belongingGroup = belongingGroup;
		selectedBuildingType = null;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new BuildingTypeChangeButton(256, 160, this, BuildingType.BASIC_HUT));
		buttons.add(new BuildingTypeChangeButton(272, 160, this, BuildingType.STORE_A));
		return buttons;
	}
	
	public void setBuildingType(BuildingType buildingType) {
		selectedBuildingType = buildingType;
		belongingGroup.setBuildingType(buildingType);
	}
	
	@Override
	public void unpressAll(){
		super.unpressAll();
		selectedBuildingType = null;
	}
	
	@Override
	protected void onActivate() {
		if(selectedBuildingType == null){
			unpressAll();
			return;
		}
		belongingGroup.setBuildingType(selectedBuildingType);
		switch(selectedBuildingType){
		case BASIC_HUT:
			buttons.get(0).setPressed(true);
			break;
		case STORE_A:
			buttons.get(1).setPressed(true);
			break;
		}
	}

}
