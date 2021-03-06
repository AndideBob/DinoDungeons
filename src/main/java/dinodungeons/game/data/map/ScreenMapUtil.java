package dinodungeons.game.data.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.data.map.objects.BlockMapObject;
import dinodungeons.game.data.map.objects.BuildingMapObject;
import dinodungeons.game.data.map.objects.CandleMapObject;
import dinodungeons.game.data.map.objects.DestructibleMapObject;
import dinodungeons.game.data.map.objects.DoorMapObject;
import dinodungeons.game.data.map.objects.EnemyMapObject;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject;
import dinodungeons.game.data.map.objects.SignMapObject;
import dinodungeons.game.data.map.objects.SpikeMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.CollectableItemObject;
import dinodungeons.game.gameobjects.enemies.EnemyBatObject;
import dinodungeons.game.gameobjects.enemies.EnemyCrocDropObject;
import dinodungeons.game.gameobjects.enemies.EnemyTricerablobObject;
import dinodungeons.game.gameobjects.environment.BasicBushObject;
import dinodungeons.game.gameobjects.environment.ExplodableStone;
import dinodungeons.game.gameobjects.environment.signs.StoneSignObject;
import dinodungeons.game.gameobjects.environment.signs.WoodenSignObject;
import dinodungeons.game.gameobjects.exits.ExplodableDoorObject;
import dinodungeons.game.gameobjects.exits.InstantExitObject;
import dinodungeons.game.gameobjects.exits.TransitionExitObject;
import dinodungeons.game.gameobjects.exits.building.BuildingExitObject;
import dinodungeons.game.gameobjects.general.WallObject;
import dinodungeons.game.gameobjects.immovable.KeyDoorObject;
import dinodungeons.game.gameobjects.immovable.MetalSpikeObject;
import dinodungeons.game.gameobjects.immovable.RoomSwitchDoorObject;
import dinodungeons.game.gameobjects.immovable.UnpushableStone;
import dinodungeons.game.gameobjects.immovable.WoodenSpikeObject;
import dinodungeons.game.gameobjects.npc.DefaultNonPlayerCharacterObject;
import dinodungeons.game.gameobjects.switches.CandleSwitch;
import dinodungeons.game.gameobjects.switches.StonePushSwitch;

public class ScreenMapUtil {

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
				GameObject object = convertMapObjectToGameObject(map, map.getMapObjectForPosition(x, y), x, y);
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
			return buildItemGameObject((ItemMapObject) object, posX * 16, posY * 16);
		}
		else if(object instanceof SpikeMapObject){
			return buildSpikeGameObject((SpikeMapObject) object, posX * 16, posY * 16);
		}
		else if(object instanceof DestructibleMapObject){
			return buildDestructibleMapObject((DestructibleMapObject) object, posX * 16, posY * 16, map.getTileSet().getColorVariation());
		}
		else if(object instanceof EnemyMapObject){
			return buildEnemyMapObject((EnemyMapObject) object, posX * 16, posY * 16);
		}
		else if(object instanceof BlockMapObject){
			return buildBlockMapObject(map, (BlockMapObject) object, posX * 16, posY * 16); 
		}
		else if(object instanceof DoorMapObject){
			return buildDoorMapObject(map, (DoorMapObject) object, posX * 16, posY * 16); 
		}
		else if(object instanceof CandleMapObject) {
			return buildCandleMapObject(map, (CandleMapObject) object, posX * 16, posY * 16);
		}
		else if(object instanceof SignMapObject) {
			return buildSignGameObject((SignMapObject) object, posX * 16, posY * 16, map.getTileSet().getColorVariation());
		}
		else if(object instanceof BuildingMapObject) {
			return buildBuildingGameObject((BuildingMapObject) object, posX * 16, posY * 16);
		}
		else if(object instanceof NonPlayerCharacterMapObject) {
			return buildNPCGameObject((NonPlayerCharacterMapObject) object, posX * 16, posY * 16);
		}
		return null;
	}
	
	private static GameObject buildEnemyMapObject(EnemyMapObject enemyMapObject, int posX, int posY) {
		switch (enemyMapObject.getEnemyType()) {
		case GREEN_BAT:
			return new EnemyBatObject(posX - 4, posY);
		case TRICERABLOB:
			return new EnemyTricerablobObject(posX, posY);
		case CROCDROP:
			return new EnemyCrocDropObject(posX + 2, posY + 1);
		}
		return null;
	}
	
	private static GameObject buildBlockMapObject(ScreenMap map, BlockMapObject blockMapObject, int posX, int posY) {
		switch (blockMapObject.getBlockType()) {
		case SOLID:
			return new UnpushableStone(posX, posY, map.getTileSet().getColorVariation());
		case NO_SWITCH:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.NONE);
		case SWITCH_A:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_A);
		case SWITCH_AB:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_AB);
		case SWITCH_ABC:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_ABC);
		case SWITCH_ABCD:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_ABCD);
		case SWITCH_B:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_B);
		case SWITCH_C:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_C);
		case SWITCH_D:
			return new StonePushSwitch(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_D);
		}
		return null;
	}
	
	private static GameObject buildDoorMapObject(ScreenMap map, DoorMapObject doorMapObject, int posX, int posY) {
		switch (doorMapObject.getDoorType()) {
		case ENEMIES:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_ALL_ENEMIES);
		case KEY:
			return new KeyDoorObject(posX, posY, map.getTileSet().getColorVariation(), getEventName(map, posX, posY));
		case MASTER_KEY:
			break;
		case SWITCH_A:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_A);
		case SWITCH_AB:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_AB);
		case SWITCH_ABC:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_ABC);
		case SWITCH_ABCD:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_ABCD);
		case SWITCH_B:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_B);
		case SWITCH_C:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_C);
		case SWITCH_D:
			return new RoomSwitchDoorObject(posX, posY, map.getTileSet().getColorVariation(), RoomEvent.SWITCH_D);
		}
		return null;
	}
	
	private static GameObject buildCandleMapObject(ScreenMap map, CandleMapObject candleMapObject, int posX, int posY) {
		return new CandleSwitch(posX, posY, candleMapObject.getTriggeredSwitch());
	}

	private static GameObject buildDestructibleMapObject(DestructibleMapObject destructibleMapObject, int posX, int posY,
			int colorVariation) {
		switch (destructibleMapObject.getDestructableType()) {
		case BUSH_NORMAL:
			return new BasicBushObject(posX, posY, colorVariation);
		case EXPLODABLE_ROCK:
			return new ExplodableStone(posX, posY, colorVariation);
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
	
	private static GameObject buildSignGameObject(SignMapObject signMapObject, int posX, int posY, int colorVariation) {
		switch (signMapObject.getSignType()) {
		case SIGN:
			WoodenSignObject woodSign = new WoodenSignObject(posX, posY, colorVariation, signMapObject.getTextBoxContent());
			return woodSign;
		case STONE_BLOCK:
			StoneSignObject stoneSign = new StoneSignObject(posX, posY, colorVariation, signMapObject.getTextBoxContent());
			return stoneSign;
		}
		return null;
	}
	
	private static GameObject buildNPCGameObject(NonPlayerCharacterMapObject npcMapObject, int posX, int posY) {
		return new DefaultNonPlayerCharacterObject(posX, posY, npcMapObject.getNPCType(), npcMapObject.getTextBoxContent());
	}

	private static GameObject buildItemGameObject(ItemMapObject itemMapObject, int posX, int posY) {
		return new CollectableItemObject(posX, posY, itemMapObject.getItemID());
	}

	private static GameObject buildTransportGameObject(ScreenMap currentMap, TransportMapObject transportMapObject, int posX, int posY){
		switch(transportMapObject.getTransportationType()){
		case CAVE_ENTRY:
		case CAVE_EXIT:
		case STAIRS:
			return new TransitionExitObject(GameObjectTag.TRANSPORT, posX * 16, posY * 16,
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
				return new ExplodableDoorObject(currentMap.getTileSet(), direction, posX * 16, posY * 16, getEventName(currentMap ,posX, posY),
						transportMapObject.getDestinationMapID(), transportMapObject.getX(), transportMapObject.getY());
			}
			break;
		case INSTANT_TELEPORT:
			return new InstantExitObject(GameObjectTag.TRANSPORT, posX * 16, posY * 16,
					transportMapObject.getDestinationMapID(), transportMapObject.getX(), transportMapObject.getY());	
		}
		return null;
	}
	
	private static GameObject buildBuildingGameObject(BuildingMapObject buildingMapObject, int posX, int posY){
		BuildingExitObject building = new BuildingExitObject(posX, posY, buildingMapObject.getBuildingType(),
				buildingMapObject.getDestinationMapID(), buildingMapObject.getX() - 16, buildingMapObject.getY());
		return building;
	}
	
	private static String getPositionString(int x, int y){
		return "" + x + "," + y;
	}

	private static String getEventName(ScreenMap map, int posX, int posY){
		return map.getID() + "(" + posX + "," + posY + ")";
	}
}
