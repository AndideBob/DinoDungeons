package dinodungeons.editor.ui.buttons.mapsettings;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.TilesetSelectionButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.tilesets.TileSet;

public class ButtonSetMapTileSet extends BaseButton {
	
	private TilesetSelectionButtonGroup belongingButtonGroup;
	
	private TileSet tileSet;

	public ButtonSetMapTileSet(int positionX, int positionY, final TilesetSelectionButtonGroup belongingButtonGroup, TileSet tileSet) {
		super(positionX, positionY, getButtonSpriteForTileSet(tileSet));
		this.tileSet = tileSet;
		this.belongingButtonGroup = belongingButtonGroup;
	}
	
	private static ButtonSprite getButtonSpriteForTileSet(TileSet tileSet){
		switch (tileSet) {
		case CAVE_GREEN:
			return ButtonSprite.TILESET_CAVE_GREEN;
		case CAVE_RED:
			return ButtonSprite.TILESET_CAVE_RED;
		case DUNGEON_ORANGE:
			return ButtonSprite.TILESET_DUNGEON_ORANGE;
		case DUNGEON_PINK:
			return ButtonSprite.TILESET_DUNGEON_PINK;
		case INTERIOR_HUT:
			return ButtonSprite.BUILDING_BASIC_HUT;
		}
		return ButtonSprite.CANCEL;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		belongingButtonGroup.setTileset(tileSet);
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
