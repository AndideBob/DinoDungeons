package dinodungeons.game.data.map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lwjgladapter.logging.Logger;

public class ScreenMapSaver {
	
	private static final String seperatorString = ";";
	
	private MapObjectParser mapObjectParser = new MapObjectParser();

	public ScreenMapSaver() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean saveMap(String id, ScreenMap map){
		String path =  File.separator + ScreenMapConstants.mapDirectiory + File.separator + id + ScreenMapConstants.mapFileExtension;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
            //Map Constants
            bw.write(String.valueOf(map.getSizeX()));
            bw.newLine();
            bw.write(String.valueOf(map.getSizeY()));
            bw.newLine();
            bw.write(String.valueOf("-----"));
            bw.newLine();
            //Base Layer
            for(int y = (map.getSizeY()-1); y >= 0; y--) {
            	for(int x = 0; x < map.getSizeX(); x++) {
            		bw.write(String.valueOf(map.getBaseLayerValueForPosition(x, y)));
            		if(x < map.getSizeX() - 1) {
            			bw.write(String.valueOf(seperatorString));
            		}
                }
            	bw.newLine();
            }
            bw.write(String.valueOf("-----"));
            bw.newLine();
            //Object Layer
            for(int y = (map.getSizeY()-1); y >= 0; y--) {
            	for(int x = 0; x < map.getSizeX(); x++) {
            		bw.write(String.valueOf(mapObjectParser.parseMapObjectToString(map.getMapObjectForPosition(x, y))));
            		if(x < map.getSizeX() - 1) {
            			bw.write(String.valueOf(seperatorString));
            		}
                }
            	bw.newLine();
            }
            bw.write(String.valueOf("-----"));
            bw.newLine();
            //Exits
            bw.write(String.valueOf(map.getTransitionUpID()));
            bw.newLine();
            bw.write(String.valueOf(map.getTransitionRightID()));
            bw.newLine();
            bw.write(String.valueOf(map.getTransitionDownID()));
            bw.newLine();
            bw.write(String.valueOf(map.getTransitionLeftID()));
            bw.newLine();
            bw.write(String.valueOf("-----"));
            bw.newLine();
            bw.write(String.valueOf(map.getTileSet().getRepresentationInFile()));
            bw.newLine();
            bw.write(String.valueOf("-----"));
            bw.newLine();
            bw.write(String.valueOf(map.getDungeonID()));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            Logger.logError(e);
            return false;
        }
		return true;
	}

}
