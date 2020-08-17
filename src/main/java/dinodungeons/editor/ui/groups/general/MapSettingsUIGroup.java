package dinodungeons.editor.ui.groups.general;

import java.util.ArrayList;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapsettings.ButtonSetMapDungeon;
import dinodungeons.editor.ui.buttons.mapsettings.ButtonSetMapTransition;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class MapSettingsUIGroup extends UIElement implements UIGroup {
	
	private boolean active;
	
	private int x;
	
	private int y;
	
	private ArrayList<BaseButton> buttons;

	public MapSettingsUIGroup(int posX, int posY, final Editor editorHandle, final EditorMapManager mapManagerHandle) {
		active = true;
		x = posX;
		y = posY;
		initializeButtons(editorHandle, mapManagerHandle);
	}
	
	private void initializeButtons(final Editor editorHandle, final EditorMapManager mapManagerHandle){
		buttons = new ArrayList<>();
		buttons.add(new ButtonSetMapTransition(x + 4, y + 24, editorHandle, mapManagerHandle, DinoDungeonsConstants.directionLeft));
		buttons.add(new ButtonSetMapTransition(x + 44, y + 24, editorHandle, mapManagerHandle, DinoDungeonsConstants.directionRight));
		buttons.add(new ButtonSetMapTransition(x + 24, y + 4, editorHandle, mapManagerHandle, DinoDungeonsConstants.directionDown));
		buttons.add(new ButtonSetMapTransition(x + 24, y + 44, editorHandle, mapManagerHandle, DinoDungeonsConstants.directionUp));
		buttons.add(new ButtonSetMapDungeon(x + 24, y + 24, editorHandle));
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(active){
			for(BaseButton button : buttons){
				button.update(inputInformation);
			}
		}
	}

	@Override
	public void draw() {
		if(active){
			SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(5, 256, 10, 64, 64);
			for(BaseButton button : buttons){
				button.draw();
			}
		}
	}

}
