package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.StaticObjectChangeButton;
import dinodungeons.editor.ui.buttons.mapchange.StaticObjectChangeButton.StaticObjectType;
import dinodungeons.game.data.map.objects.DestructibleMapObject.DestructableType;
import lwjgladapter.logging.Logger;

public class StaticObjectButtonGroup extends UIButtonGroup {
	
	private Editor editorHandle;
	
	public StaticObjectButtonGroup(final Editor editorHandle){
		super(editorHandle);
		this.editorHandle = editorHandle;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		//No Interactions
		buttons.add(new StaticObjectChangeButton(256, 208, this, StaticObjectType.IMMOVABLE_BLOCK));
		//Destructables
		buttons.add(new StaticObjectChangeButton(256, 176, this, StaticObjectType.DESTRUCTABLE_GRASS));
		buttons.add(new StaticObjectChangeButton(272, 176, this, StaticObjectType.DESTRUCTABLE_STONE));
		//Hurting Objects
		buttons.add(new StaticObjectChangeButton(256, 144, this, StaticObjectType.SPIKES_WOOD));
		buttons.add(new StaticObjectChangeButton(272, 144, this, StaticObjectType.SPIKES_METAL));
		return buttons;
	}
	
	public void setObjectType(StaticObjectType staticObjectType) {
		switch (staticObjectType) {
		case DESTRUCTABLE_GRASS:
			editorHandle.setMapChange(MapChangeType.DESTRUCTIBLE_PLACEMENT, DestructableType.BUSH_NORMAL.getStringRepresentation());
			break;
		case SPIKES_METAL:
			editorHandle.setMapChange(MapChangeType.SPIKE_PLACEMENT, "0");
			break;
		case SPIKES_WOOD:
			editorHandle.setMapChange(MapChangeType.SPIKE_PLACEMENT, "1");
			break;
		default:
			Logger.logDebug(staticObjectType.toString() + " not implemented yet!");
			break;
		}
	}
	
	@Override
	protected void onActivate() {
		buttons.get(0).setPressed(true);
		setObjectType(StaticObjectType.IMMOVABLE_BLOCK);
	}

}
