package dinodungeons.game.data.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.game.gameobjects.general.WallObject;
import lwjgladapter.logging.Logger;

public class ScreenMapUtil {

	public static Collection<GameObject> createGameObjectsForMap(ScreenMap map){
		ArrayList<GameObject> gameObjects = new ArrayList<>();
		gameObjects.addAll(createWallsForMap(map));
		return gameObjects;
	}
	
	private static Collection<GameObject> createWallsForMap(ScreenMap map){
		ArrayList<GameObject> walls = new ArrayList<>();
		HashSet<String> coveredPositions = new HashSet<>();
		for(int y = 0; y < map.getSizeY(); y++){
			for(int x = 0; x < map.getSizeX(); x++){
				if(map.getBaseLayerValueForPosition(x, y) == ScreenMapConstants.BASE_LAYER_WALL
						&& !coveredPositions.contains(getPositionString(x,y))){
					int posX = x;
					int posY = y;
					int actualWidth = 1;
					int actualHeight = 1;
					//Extend X
					for(int width = 0; width + posX < map.getSizeX(); width++){
						if(map.getBaseLayerValueForPosition(width + posX, y) == ScreenMapConstants.BASE_LAYER_WALL
								&& !coveredPositions.contains(getPositionString(width + posX, y))){
							coveredPositions.add(getPositionString(width + posX, y));
							actualWidth = width;
						}
						else{
							break;
						}
					}
					//Extend Y
					boolean loopBroken = false;
					HashSet<String> maybeCoveredPositions = new HashSet<>();
					for(int height = 1; height + posY < map.getSizeY(); height++){
						maybeCoveredPositions.clear();
						for(int x2 = posX; x2 <= posX + actualWidth; x2++){
							if(map.getBaseLayerValueForPosition(x2, height + posY) == ScreenMapConstants.BASE_LAYER_WALL
									&& !coveredPositions.contains(getPositionString(x2, height + posY))){
								maybeCoveredPositions.add(getPositionString(x2, height + posY));
							}
							else{
								loopBroken = true;
								break;
							}
						}
						if(loopBroken){
							break;
						}
						else{
							coveredPositions.addAll(maybeCoveredPositions);
							actualHeight = height;
						}
					}
					Logger.logDebug("Wall Created:(" + posX + "," + posY + "," + (posX + actualWidth) + "," + (posY + actualHeight) + ")");
					walls.add(new WallObject(GameObjectTag.WALL, posX * 16, posY * 16, (actualWidth + 1) * 16, (actualHeight + 1) * 16));
				}
			}
		}
		return walls;
	}
	
	private static String getPositionString(int x, int y){
		return "" + x + "," + y;
	}

}
