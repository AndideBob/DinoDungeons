package dinodungeons.gfx.sprites;

import java.util.HashMap;

import lwjgladapter.gfx.TileMap;

public class SpriteManager {
	
	private static SpriteManager instance;

	private HashMap<SpriteID, TileMap> sprites;
	
	private SpriteManager() {
		instance = this;
		sprites = new HashMap<>();
	}
	
	public static SpriteManager getInstance(){
		if(instance == null){
			return new SpriteManager();
		}
		return instance;
	}

	public void loadSprites(){
		for(SpriteID id : SpriteID.values()){
			TileMap sprite = new TileMap(id.getGfxResourceID().getFilePath(), id.getWidth(), id.getHeight());
			sprites.put(id, sprite);
		}
	}
	
	public TileMap getSprite(SpriteID id){
		return sprites.get(id);
	}
}
