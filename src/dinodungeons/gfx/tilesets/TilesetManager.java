package dinodungeons.gfx.tilesets;

import java.util.HashMap;

import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.gfx.SpriteMap;

public class TilesetManager {
	
	private static TilesetManager instance;
	
	private TilesetManager() {
		instance = this;
		dungeonTileSets = new HashMap<>();
	}
	
	public static TilesetManager getInstance(){
		if(instance == null){
			return new TilesetManager();
		}
		return instance;
	}

	private HashMap<TileSet, SpriteMap> dungeonTileSets;
	
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
