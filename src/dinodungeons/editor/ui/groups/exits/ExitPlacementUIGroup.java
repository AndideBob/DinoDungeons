package dinodungeons.editor.ui.groups.exits;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.editor.ui.groups.buttons.TransportTypeButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;

public class ExitPlacementUIGroup extends UIElement implements UIGroup{

	private Editor editorHandle;
	
	private ExitSettingsUIGroup exitSettingsUIGroup;
	
	private TransportTypeButtonGroup transportTypeButtonGroup;
	
	private String mapID;
	private int mapX;
	private int mapY;
	private TransportationType transportType;
	
	public ExitPlacementUIGroup(final Editor editorHandle) {
		this.editorHandle = editorHandle;
		exitSettingsUIGroup = new ExitSettingsUIGroup(256, 74, this);
		transportTypeButtonGroup = new TransportTypeButtonGroup(editorHandle, this);
		transportType = TransportationType.INSTANT_TELEPORT;
		mapID = "0000";
		mapX = 0;
		mapY = 0;
	}

	@Override
	public void setActive(boolean active) {
		exitSettingsUIGroup.setActive(active);
		transportTypeButtonGroup.setActive(active);
	}
	
	@Override
	public final void update(InputInformation inputInformation) {
		exitSettingsUIGroup.update(inputInformation);
		transportTypeButtonGroup.update(inputInformation);
	}

	@Override
	public final void draw() {
		exitSettingsUIGroup.draw();
		transportTypeButtonGroup.draw();
	}

	public void setTarget(String mapID, int mapX, int mapY) {
		this.mapID = mapID;
		this.mapX = mapX;
		this.mapY = mapY;
		sendSelectionInformationToEditor();
	}
	
	public void setTransportType(TransportationType transportType) {
		this.transportType = transportType;
		sendSelectionInformationToEditor();
	}

	private void sendSelectionInformationToEditor() {
		editorHandle.setMapChange(MapChangeType.EXIT_PLACEMENT, transportType.getSaveRepresentation(), mapID, "" + mapX, "" + mapY);
	}

}
