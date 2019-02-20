package dinodungeons.game.data.map;

import java.io.File;
import java.util.HashMap;

import lwjgladapter.logging.Logger;

public class MapManager {

	private ScreenMapLoader loader;
	private HashMap<String, ScreenMap> maps;
	
	public MapManager() {
		loader = new ScreenMapLoader();
		maps = new HashMap<>();
	}
	
	public void loadMaps(){
		File dir = new File(ScreenMapConstants.mapDirectiory);
		File[] listOfFiles = dir.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				if(fileName.endsWith(".ddm"))
				{
					String mapId = fileName.substring(0, fileName.lastIndexOf('.'));
					ScreenMap map = loader.loadMap(mapId);
					if(map != null){
						Logger.log("Map(" + mapId + ") loaded!");
						maps.put(mapId, map);
					}
					else{
						Logger.logError("Failed to load Map(" + mapId + ")!");
					}
				}
				
			}
		}
	}

}
