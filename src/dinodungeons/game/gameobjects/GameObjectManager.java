package dinodungeons.game.gameobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectDrawComparator;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.player.PlayerObject;
import lwjgladapter.logging.Logger;

public class GameObjectManager {
	
	private static GameObjectManager instance;
	
	public static GameObjectManager getInstance(){
		if(instance == null){
			instance = new GameObjectManager();
		}
		return instance;
	}

	private HashMap<String, Collection<GameObject>> gameObjects;
	private PlayerObject player;
	
	private String currentMapID;
	
	private ArrayList<GameObject> gameObjectsToBeAddedToCurrentMap;
	private ArrayList<GameObject> gameObjectsToBeDeletedDeliberately;
	
	private GameObjectManager() {
		gameObjects = new HashMap<>();
		currentMapID = null;
		gameObjectsToBeDeletedDeliberately = new ArrayList<>();
		gameObjectsToBeAddedToCurrentMap = new ArrayList<>();
	}
	
	public PlayerObject getPlayerObject(){
		if(player == null){
			player = new PlayerObject(GameObjectTag.PLAYER, 0, 0);
		}
		return player;
	}
	
	public void updateCurrentGameObjects(long deltaTimeInMs, InputInformation inputInformation){
		if(currentMapID != null){
			for(GameObject newObject : gameObjectsToBeAddedToCurrentMap) {
				gameObjects.get(currentMapID).add(newObject);
			}
			gameObjectsToBeAddedToCurrentMap.clear();
			Iterator<GameObject> iter = gameObjects.get(currentMapID).iterator();
			while(iter.hasNext()){
				GameObject o = iter.next();
				if(o.shouldBeDeleted() || gameObjectsToBeDeletedDeliberately.contains(o)){
					gameObjectsToBeDeletedDeliberately.remove(o);
					o.delete();
					iter.remove();
				}
				else{
					o.update(deltaTimeInMs, inputInformation);
				}
			}
			player.update(deltaTimeInMs, inputInformation);
		}
	}
	
	public void drawGameObjects(ScreenMap map, int offsetX, int offsetY, boolean drawPlayer){
		if(map != null && map.getID() != null){
			ArrayList<GameObject> sortedObjects = new ArrayList<>(gameObjects.get(map.getID()));
			if(drawPlayer) {
				sortedObjects.add(player);
			}
			Collections.sort(sortedObjects, new GameObjectDrawComparator());
			for(GameObject o : sortedObjects){
				o.draw(offsetX,offsetY);
			}
		}
	}
	
	public void addGameObjectToCurrentMap(GameObject gameObject){
		gameObjectsToBeAddedToCurrentMap.add(gameObject);
	}
	
	public void setCurrentMap(ScreenMap map, boolean cleanOtherRooms){
		if(cleanOtherRooms){
			gameObjects.clear();
		}
		currentMapID = map.getID();
		if(!gameObjects.containsKey(currentMapID)){
			initGameObjects(map);
		}
		else {
			Iterator<GameObject> iter = gameObjects.get(currentMapID).iterator();
			while(iter.hasNext()){
				GameObject o = iter.next();
				if(o.isTemporary()){
					o.delete();
					iter.remove();
				}
			}
		}
	}

	private void initGameObjects(ScreenMap map) {
		Collection<GameObject> objects = ScreenMapUtil.createGameObjectsForMap(map);
		gameObjects.put(map.getID(), objects);
	}

	public void setPlayerPosition(int destinationXPosition, int destinationYPosition) {
		getPlayerObject().setPosition(destinationXPosition, destinationYPosition);
	}
	
	public Collection<GameObject> getCurrentGameObjects(){
		if(currentMapID != null){
			ArrayList<GameObject> currentGameObjects = new ArrayList<>();
			currentGameObjects.addAll(gameObjects.get(currentMapID));
			currentGameObjects.add(player);
			return currentGameObjects;
		}
		return Collections.emptyList();
	}
	
	public Collection<GameObject> getGameObjects(String mapID){
		if(gameObjects.containsKey(mapID)){
			return Collections.unmodifiableCollection(gameObjects.get(mapID));
		}
		return Collections.emptyList();
	}

	public void destroyGameObjectImmediately(GameObject gameObject) {
		gameObjectsToBeDeletedDeliberately.add(gameObject);
	}

}
