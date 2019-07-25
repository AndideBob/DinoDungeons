package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapsettings.ButtonSetMapTileSet;
import dinodungeons.gfx.tilesets.TileSet;

public class TilesetSelectionButtonGroup extends UIButtonGroup {
	
	private EditorMapManager mapManagerHandle;

	public TilesetSelectionButtonGroup(Editor editorHandle, final EditorMapManager mapManagerHandle) {
		super(editorHandle);
		this.mapManagerHandle = mapManagerHandle;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new ButtonSetMapTileSet(256, 208, this, TileSet.CAVE_GREEN));
		buttons.add(new ButtonSetMapTileSet(272, 208, this, TileSet.CAVE_RED));
		buttons.add(new ButtonSetMapTileSet(288, 208, this, TileSet.DUNGEON_ORANGE));
		buttons.add(new ButtonSetMapTileSet(304, 208, this, TileSet.DUNGEON_PINK));
		buttons.add(new ButtonSetMapTileSet(320, 208, this, TileSet.INTERIOR_HUT));
		return buttons;
	}
	
	@Override
	protected void onActivate() {
		unpressAll();
		switch(mapManagerHandle.getCurrentTileset()){
		case CAVE_GREEN:
			buttons.get(0).setPressed(true);
			break;
		case CAVE_RED:
			buttons.get(1).setPressed(true);
			break;
		case DUNGEON_ORANGE:
			buttons.get(2).setPressed(true);
			break;
		case DUNGEON_PINK:
			buttons.get(3).setPressed(true);
			break;
		}
	}

	public void setTileset(TileSet tileSet) {
		mapManagerHandle.setTileset(tileSet);
	}

}
