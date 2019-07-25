package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.TransportationTypeChangeButton;
import dinodungeons.editor.ui.groups.exits.ExitPlacementUIGroup;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;

public class TransportTypeButtonGroup extends UIButtonGroup {
	
	private ExitPlacementUIGroup belongingGroup;
	
	private TransportationType selectedTransportationType;
	
	public TransportTypeButtonGroup(final Editor editorHandle, final ExitPlacementUIGroup belongingGroup){
		super(editorHandle);
		this.belongingGroup = belongingGroup;
		selectedTransportationType = TransportationType.INSTANT_TELEPORT;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new TransportationTypeChangeButton(256, 208, this, TransportationType.INSTANT_TELEPORT));
		buttons.add(new TransportationTypeChangeButton(272, 208, this, TransportationType.CAVE_ENTRY));
		buttons.add(new TransportationTypeChangeButton(288, 208, this, TransportationType.CAVE_EXIT));
		buttons.add(new TransportationTypeChangeButton(304, 208, this, TransportationType.STAIRS));
		buttons.add(new TransportationTypeChangeButton(256, 192, this, TransportationType.BLOCKED_CAVE_ENTRY));
		return buttons;
	}
	
	public void setTransportType(TransportationType transportationType) {
		selectedTransportationType = transportationType;
		belongingGroup.setTransportType(transportationType);
	}
	
	@Override
	public void unpressAll(){
		super.unpressAll();
		selectedTransportationType = null;
	}
	
	@Override
	protected void onActivate() {
		if(selectedTransportationType == null){
			unpressAll();
			return;
		}
		belongingGroup.setTransportType(selectedTransportationType);
		switch(selectedTransportationType){
		case INSTANT_TELEPORT:
			buttons.get(0).setPressed(true);
			break;
		case CAVE_ENTRY:
			buttons.get(1).setPressed(true);
			break;
		case CAVE_EXIT:
			buttons.get(2).setPressed(true);
			break;
		case STAIRS:
			buttons.get(3).setPressed(true);
			break;
		case BLOCKED_CAVE_ENTRY:
			buttons.get(4).setPressed(true);
			break;
		}
	}

}
