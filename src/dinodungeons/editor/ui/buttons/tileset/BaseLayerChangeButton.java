package dinodungeons.editor.ui.buttons.tileset;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.ScreenMapConstants;
import dinodungeons.gfx.sprites.SpriteID;

public class BaseLayerChangeButton extends BaseButton {

	private Editor editorHandle;
	
	private int tileType;
	
	public BaseLayerChangeButton(int positionX, int positionY, final Editor editorHandle, int tileType) {
		super(positionX, positionY, getButtonSpriteForTileType(tileType));
		this.tileType = tileType;
		this.editorHandle = editorHandle;
	}

	@Override
	protected void onClick() {
		editorHandle.setMapChange(MapChangeType.BASE_LAYER, "" + tileType);
	}
	
	private static ButtonSprite getButtonSpriteForTileType(int tileType){
		switch (tileType) {
		case ScreenMapConstants.BASE_LAYER_WALL:
			return ButtonSprite.BASE_LAYER_WALL;
		case ScreenMapConstants.BASE_LAYER_BORDER:
			return ButtonSprite.BASE_LAYER_WALL;
		case ScreenMapConstants.BASE_LAYER_FLOOR_A:
			return ButtonSprite.BASE_LAYER_FLOOR_A;
		case ScreenMapConstants.BASE_LAYER_FLOOR_B:
			return ButtonSprite.BASE_LAYER_FLOOR_B;
		case ScreenMapConstants.BASE_LAYER_FLOOR_C:
			return ButtonSprite.BASE_LAYER_FLOOR_C;
		case ScreenMapConstants.BASE_LAYER_STAIRS:
			return ButtonSprite.BASE_LAYER_STAIRS;
		case ScreenMapConstants.BASE_LAYER_ENTRANCE_LEFT:
			return ButtonSprite.BASE_LAYER_ENTRY_LEFT;
		case ScreenMapConstants.BASE_LAYER_ENTRANCE_RIGHT:
			return ButtonSprite.BASE_LAYER_ENTRY_RIGHT;
		case ScreenMapConstants.BASE_LAYER_DOOR_DOWN:
			return ButtonSprite.BASE_LAYER_DOOR_DOWN;
		case ScreenMapConstants.BASE_LAYER_DOOR_UP:
			return ButtonSprite.BASE_LAYER_DOOR_UP;
		case ScreenMapConstants.BASE_LAYER_DOOR_RIGHT:
			return ButtonSprite.BASE_LAYER_DOOR_RIGHT;
		case ScreenMapConstants.BASE_LAYER_DOOR_LEFT:
			return ButtonSprite.BASE_LAYER_DOOR_LEFT;
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
