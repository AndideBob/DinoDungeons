package dinodungeons.editor.map;

import java.util.Collection;

import dinodungeons.editor.map.change.MapChange;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapLoader;
import dinodungeons.game.data.map.ScreenMapSaver;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.logging.Logger;

public class EditorMapManager {

	ScreenMap currentMap;

	ScreenMapLoader loader;
	ScreenMapSaver saver;
	
	public EditorMapManager() {
		loader = new ScreenMapLoader();
		saver = new ScreenMapSaver();
		newMap();
	}

	public void update(){
		
	}
	
	public void draw(boolean drawObjectsLayer){
		drawMap();
		if(drawObjectsLayer){
			drawObjectsLayer();
		}
	}
	
	private void drawMap(){
		TileSet tileSet = currentMap.getTileSet();
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
				TilesetManager.getInstance().drawTile(tile, tileSet, x * 16, y * 16);
			}
		}
	}
	
	private void drawObjectsLayer(){
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				MapObject mapObject = currentMap.getMapObjectForPosition(x, y);
				MapObjectDrawUtil.drawMapObject(mapObject, currentMap, x, y);
			}
		}
	}
	
	public void newMap(){
		currentMap = ScreenMap.defaultMap;
	}
	
	public void loadMap(String enteredID){
		if(enteredID != null && !enteredID.isEmpty()){
			ScreenMap loadedMap = loader.loadMap(enteredID);
			if(loadedMap == null){
				Logger.logError("Could not load map: " + enteredID);
			}
			else{
				currentMap = loadedMap;
			}
		}
	}
	
	public void saveMap(String enteredID){
		if(enteredID != null && !enteredID.isEmpty()){
			saver.saveMap(enteredID, currentMap);
		}
	}
	
	public void applyMapChanges(Collection<MapChange> changes){
		for(MapChange change : changes){
			change.applyTo(currentMap);
		}
	}
}
