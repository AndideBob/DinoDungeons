package dinodungeons.gfx.tilesets;

import java.util.HashMap;

import dinodungeons.game.data.map.BaseLayerTile;
import lwjgladapter.gfx.TileMap;

public class TilesetManager {

	private HashMap<DungeonTileSet, TileMap> dungeonTileSets;
	
	public TilesetManager() {
		dungeonTileSets = new HashMap<>();
	}
	
	public void loadResources(){
		for(DungeonTileSet tileSet : DungeonTileSet.values()){
			dungeonTileSets.put(tileSet, new TileMap(tileSet.getResourceID().getFilePath(), 16, 16));
		}
	}

	public void drawTile(BaseLayerTile tile, DungeonTileSet tileSet, int x, int y) {
		TileMap tileMap = dungeonTileSets.get(tileSet);
		tileMap.draw(tile.getTileSetPosition(), x, y);
	}

}
