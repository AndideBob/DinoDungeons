package dinodungeons.gfx.tilesets;

import java.util.HashMap;

import dinodungeons.game.data.map.BaseLayerTile;
import lwjgladapter.gfx.SpriteMap;

public class TilesetManager {

	private HashMap<TileSet, SpriteMap> dungeonTileSets;
	
	public TilesetManager() {
		dungeonTileSets = new HashMap<>();
	}
	
	public void loadResources(){
		for(TileSet tileSet : TileSet.values()){
			dungeonTileSets.put(tileSet, new SpriteMap(tileSet.getResourceID().getFilePath(), 16, 16));
		}
	}

	public void drawTile(BaseLayerTile tile, TileSet tileSet, int x, int y) {
		SpriteMap tileMap = dungeonTileSets.get(tileSet);
		tileMap.draw(tile.getTileSetPosition(), x, y);
	}

}
