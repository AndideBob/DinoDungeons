package dinodungeons.editor.ui.groups.exits;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.editor.ui.groups.buttons.BuildingTypeButtonGroup;
import dinodungeons.editor.ui.groups.buttons.TransportTypeButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
import dinodungeons.game.gameobjects.exits.building.BuildingType;

public class ExitPlacementUIGroup extends UIElement implements UIGroup{

	private Editor editorHandle;
	
	private ExitSettingsUIGroup exitSettingsUIGroup;
	
	private TransportTypeButtonGroup transportTypeButtonGroup;
	private BuildingTypeButtonGroup buildingTypeButtonGroup;
	
	private String mapID;
	private int mapX;
	private int mapY;
	private TransportationType transportType;
	private BuildingType buildingType;
	
	private boolean wasSetToBuilding;
	
	public ExitPlacementUIGroup(final Editor editorHandle) {
		this.editorHandle = editorHandle;
		exitSettingsUIGroup = new ExitSettingsUIGroup(256, 74, this);
		transportTypeButtonGroup = new TransportTypeButtonGroup(editorHandle, this);
		buildingTypeButtonGroup = new BuildingTypeButtonGroup(editorHandle, this);
		transportType = TransportationType.INSTANT_TELEPORT;
		buildingType = BuildingType.BASIC_HUT;
		mapID = "0000";
		mapX = 0;
		mapY = 0;
		wasSetToBuilding = false;
	}

	@Override
	public void setActive(boolean active) {
		exitSettingsUIGroup.setActive(active);
		transportTypeButtonGroup.setActive(active);
		buildingTypeButtonGroup.setActive(active);
	}
	
	@Override
	public final void update(InputInformation inputInformation) {
		exitSettingsUIGroup.update(inputInformation);
		transportTypeButtonGroup.update(inputInformation);
		buildingTypeButtonGroup.update(inputInformation);
	}

	@Override
	public final void draw() {
		exitSettingsUIGroup.draw();
		transportTypeButtonGroup.draw();
		buildingTypeButtonGroup.draw();
	}

	public void setTarget(String mapID, int mapX, int mapY) {
		this.mapID = mapID;
		this.mapX = mapX;
		this.mapY = mapY;
		sendSelectionInformationToEditor(wasSetToBuilding);
	}
	
	public void setTransportType(TransportationType transportType) {
		this.transportType = transportType;
		buildingTypeButtonGroup.unpressAll();
		sendSelectionInformationToEditor(false);
	}
	
	public void setBuildingType(BuildingType buildingType) {
		this.buildingType = buildingType;
		transportTypeButtonGroup.unpressAll();
		sendSelectionInformationToEditor(true);
	}

	private void sendSelectionInformationToEditor(boolean isBuilding) {
		wasSetToBuilding = isBuilding;
		if(isBuilding){
			editorHandle.setMapChange(MapChangeType.BUILDING_PLACEMENT, buildingType.getStringRepresentation(), mapID, "" + mapX, "" + mapY);
		}
		else{
			editorHandle.setMapChange(MapChangeType.EXIT_PLACEMENT, transportType.getSaveRepresentation(), mapID, "" + mapX, "" + mapY);
		}
		
	}

}
