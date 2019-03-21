package dinodungeons.game.data.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.DinoDungeons;
import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.map.objects.DestructibleMapObject;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.SpikeMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.CollectableItemObject;
import dinodungeons.game.gameobjects.environment.BasicBushObject;
import dinodungeons.game.gameobjects.exits.ExplodableDoorObject;
import dinodungeons.game.gameobjects.exits.InstantExitObject;
import dinodungeons.game.gameobjects.exits.TransitionExitObject;
import dinodungeons.game.gameobjects.general.WallObject;
import dinodungeons.game.gameobjects.immovable.MetalSpikeObject;
import dinodungeons.game.gameobjects.immovable.WoodenSpikeObject;
import dinodungeons.gfx.tilesets.TileSet;
import lwjgladapter.logging.Logger;

public class ScreenMapUtil {
	
	private static DinoDungeons gameHandle = null;
	
	public static void setGameHandle(DinoDungeons newGameHandle){
		gameHandle = newGameHandle;
	}

	public static Collection<GameObject> createGameObjectsForMap(ScreenMap map){
		ArrayList<GameObject> gameObjects = new ArrayList<>();
		gameObjects.addAll(createWallsForMap(map));
		gameObjects.addAll(createObjectsForMap(map));
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
							actualHeight = height - 1;
							break;
						}
						else{
							coveredPositions.addAll(maybeCoveredPositions);
							actualHeight = height;
						}
					}
					//Logger.logDebug("Wall Created:(" + posX + "," + posY + "," + (posX + actualWidth) + "," + (posY + actualHeight) + ")");
					walls.add(new WallObject(GameObjectTag.WALL, posX * 16, posY * 16, (actualWidth + 1) * 16, (actualHeight + 1) * 16));
				}
			}
		}
		return walls;
	}
	
	private static Collection<GameObject> createObjectsForMap(ScreenMap map){
		ArrayList<GameObject> objects = new ArrayList<>();
		for(int y = 0; y < map.getSizeY(); y++){
			for(int x = 0; x < map.getSizeX(); x++){
				GameObject object = convertMapObjectToGameObject(map, map.getMapObjectForPosition(x, y), x * 16, y * 16);
				if(object != null){
					objects.add(object);
				}
			}
		}
		return objects;
	}
	
	private static GameObject convertMapObjectToGameObject(ScreenMap map, MapObject object, int posX, int posY){
		if(object instanceof TransportMapObject){
			return buildTransportGameObject(map, (TransportMapObject) object, posX, posY);
		}
		else if(object instanceof ItemMapObject){
			return buildItemGameObject((ItemMapObject) object, posX, posY);
		}
		else if(object instanceof SpikeMapObject){
			return buildSpikeGameObject((SpikeMapObject) object, posX, posY);
		}
		else if(object instanceof DestructibleMapObject){
			return buildDestructibleMapObject((DestructibleMapObject) object, posX, posY, map.getTileSet().getColorVariation());
		}
		return null;
	}
	
	private static GameObject buildDestructibleMapObject(DestructibleMapObject destructibleMapObject, int posX, int posY,
			int colorVariation) {
		switch (destructibleMapObject.getDestructableType()) {
		case 0:
			return new BasicBushObject(posX, posY, colorVariation);
		}
		return null;
	}

	private static GameObject buildSpikeGameObject(SpikeMapObject spikeMapObject, int posX, int posY) {
		switch (spikeMapObject.getSpikeType()) {
		case 0:
			return new MetalSpikeObject(posX, posY);
		case 1:
			return new WoodenSpikeObject(posX, posY);
		}
		return null;
	}

	private static GameObject buildItemGameObject(ItemMapObject itemMapObject, int posX, int posY) {
		return new CollectableItemObject(posX, posY, itemMapObject.getItemID());
	}

	private static GameObject buildTransportGameObject(ScreenMap currentMap, TransportMapObject transportMapObject, int posX, int posY){
		switch(transportMapObject.getTransportationType()){
		case CAVE_ENTRY:
		case CAVE_EXIT:
		case STAIRS:
			return new TransitionExitObject(GameObjectTag.TRANSPORT, posX, posY,
					transportMapObject.getDestinationMapID(), transportMapObject.getX(), transportMapObject.getY());
		case BLOCKED_CAVE_ENTRY:
			int direction = DinoDungeonsConstants.directionDown;
			boolean validDirection = true;
			switch(currentMap.getBaseLayerTileForPosition(posX, posY)){
			case DOOR_DOWN:
				direction = DinoDungeonsConstants.directionDown;
				break;
			case DOOR_LEFT:
				direction = DinoDungeonsConstants.directionLeft;
				break;
			case DOOR_RIGHT:
				direction = DinoDungeonsConstants.directionRight;
				break;
			case DOOR_UP:
				direction = DinoDungeonsConstants.directionUp;
				break;
			default:
				validDirection = false;
				break;
			}
			if(validDirection){
				return new ExplodableDoorObject(currentMap.getTileSet(), direction, posX, posY, getEventName(currentMap ,posX, posY),
						transportMapObject.getDestinationMapID(), transportMapObject.getX(), transportMapObject.getY());
			}
			break;
		case INSTANT_TELEPORT:
			return new InstantExitObject(GameObjectTag.TRANSPORT, posX, posY,
					transportMapObject.getDestinationMapID(), transportMapObject.getX(), transportMapObject.getY());	
		}
		return null;
	}
	
	private static String getPositionString(int x, int y){
		return "" + x + "," + y;
	}

	private static String getEventName(ScreenMap map, int posX, int posY){
		return map.getID() + "(" + posX + "," + posY + ")";
	}
}
