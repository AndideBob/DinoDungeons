package dinodungeons.game.data.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import dinodungeons.game.data.exceptions.DinoDungeonsException;
import dinodungeons.gfx.tilesets.TileSet;
import lwjgladapter.logging.Logger;

public class ScreenMapLoader {
	
	private static final String seperatorString = ";";
	

	public ScreenMapLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public ScreenMap loadMap(String id){
		String path = File.separator + ScreenMapConstants.mapDirectiory + id + ScreenMapConstants.mapFileExtension;
		try {
			ScreenMap result = ScreenMap.defaultMap;
			File file = new File(path);
			if (!file.exists()) {
				return result;
			}
			FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int lineIndex = 0;
            //Map Constants
            int width = 1;
            int height = 1;
            while(line != null){
            	if(lineIndex == 0){
            		width = Integer.parseInt(line);
            	}
            	else if(lineIndex == 1){
            		height = Integer.parseInt(line);
            	}
            	else if(lineIndex == 2){
            		//Create Map
            		result = new ScreenMap(id, width, height);
            	}
            	else if(lineIndex-3 < height){
            		int y = lineIndex - 3;
            		String[] tiles = line.split(seperatorString);
            		for(int x = 0; x < width; x++){
            			parseTileOntoMap(x,height-y-1,tiles[x],result);
            		}
            	}
            	else if(lineIndex-3 == height){
            		result.updateBaseLayerTiles();
            		//TODO: InitializeActual Tiles & Colliders
            		//Switch to Object Layer
            	}
            	else if(lineIndex-height-4 < height){
            		int y = lineIndex-height-4;
            		String[] tiles = line.split(seperatorString);
            		for(int x = 0; x < width; x++){
            			parseObjectOntoMap(x,height-y-1,tiles[x],result);
            		}
            	}
            	else if(lineIndex-height-4 == height){
            		//TODO: Initialize Map Objects
            	}
            	else if(lineIndex == height*2+5) {
            		//ExitNorth
            		String exit = line.length() > 4 ? line.substring(0, 3) : line;
            		result.setTransitionUpID(exit);
            	}
            	else if(lineIndex == height*2+6) {
            		//ExitEast
            		String exit = line.length() > 4 ? line.substring(0, 3) : line;
            		result.setTransitionRightID(exit);
            	}
            	else if(lineIndex == height*2+7) {
            		//ExitSouth
            		String exit = line.length() > 4 ? line.substring(0, 3) : line;
            		result.setTransitionDownID(exit);
            	}
            	else if(lineIndex == height*2+8) {
            		//ExitWest
            		String exit = line.length() > 4 ? line.substring(0, 3) : line;
            		result.setTransitionLeftID(exit);
            	}
            	else if(lineIndex == height*2+10){
            		//TileSet
            		result.setTileSet(TileSet.getTileSetFromRepesentation(line));
            	}
            	lineIndex++;
            	line = br.readLine();
            }
            br.close();
            return result;
        } catch (IOException e) {
            Logger.logError(e);
        } catch (DinoDungeonsException e){
        	Logger.logError(e);
        }
		return null;
	}
	
	private void parseTileOntoMap(int x, int y, String tileString, ScreenMap map) throws DinoDungeonsException{
		int tileType = Integer.parseInt(tileString);
		map.setBaseLayer(x, y, tileType);
	}
	
	private void parseObjectOntoMap(int x, int y, String tileString, ScreenMap map) throws DinoDungeonsException{
		
	}

}
