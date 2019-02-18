package dinodungeons.editor;

import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.MapID;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapLoader;
import dinodungeons.gfx.GFXResourceID;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.DungeonTileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.game.Game;

public class Editor extends Game {

	TilesetManager tileSetManager = new TilesetManager();
	DrawTextManager textManager;
	ScreenMap map;
	
	public Editor() {
		ScreenMapLoader loader = new ScreenMapLoader();
		map = loader.loadMap(MapID.TESTROOM);
	}

	@Override
	public void draw() {
		//DrawCurrentMap
		for(int x = 0; x < map.getSizeX(); x++){
			for(int y = 0; y < map.getSizeY(); y++){
				BaseLayerTile tile = map.getBaseLayerTileForPosition(x, y);
				DungeonTileSet tileSet = DungeonTileSet.TEST;
				tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
			}
		}
		//DrawUI
		textManager.DrawText(0, 248, "[F1]Save", 9);
		textManager.DrawText(96, 248, "[</>]Switch Tool", 16);
		textManager.DrawText(0, 248, "[F1]Save", 9);
	}

	@Override
	public void loadResources() {
		tileSetManager.loadResources();
		textManager = new DrawTextManager(GFXResourceID.TEXT.getFilePath());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
