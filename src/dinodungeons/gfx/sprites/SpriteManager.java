package dinodungeons.gfx.sprites;

import java.util.HashMap;

import lwjgladapter.gfx.SpriteMap;

public class SpriteManager {
	
	private static SpriteManager instance;

	private HashMap<SpriteID, SpriteMap> sprites;
	
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
			SpriteMap sprite = new SpriteMap(id.getGfxResourceID().getFilePath(), id.getWidth(), id.getHeight());
			sprites.put(id, sprite);
		}
	}
	
	public SpriteMap getSprite(SpriteID id){
		return sprites.get(id);
	}
}
