package dinodungeons.game.gameobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import dinodungeons.game.data.gameplay.PlayerStatusManager;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.gameobjects.base.GameObject;
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
	
	private GameObjectManager() {
		gameObjects = new HashMap<>();
		currentMapID = null;
	}
	
	public PlayerObject getPlayerObject(){
		if(player == null){
			player = new PlayerObject(GameObjectTag.PLAYER, 0, 0);
		}
		return player;
	}
	
	public void updateCurrentGameObjects(long deltaTimeInMs){
		if(currentMapID != null){
			Iterator<GameObject> iter = gameObjects.get(currentMapID).iterator();
			while(iter.hasNext()){
				GameObject o = iter.next();
				if(o.shouldBeDeleted()){
					o.delete();
					iter.remove();
				}
				else{
					o.update(deltaTimeInMs);
				}
			}
			player.update(deltaTimeInMs);
		}
	}
	
	public void drawGameObjects(ScreenMap map, int offsetX, int offsetY){
		if(map != null && map.getID() != null){
			for(GameObject o : gameObjects.get(map.getID())){
				o.draw(offsetX,offsetY);
			}
		}
	}
	
	public void addGameObjectToCurrentMap(GameObject gameObject){
		if(currentMapID != null && gameObjects.containsKey(currentMapID)){
			gameObjects.get(currentMapID).add(gameObject);
		}
		else{
			Logger.logError("Current Map is not initialized! Object " + gameObject.getClass().getName() + " not added!");
		}
	}
	
	public void setCurrentMap(ScreenMap map, boolean cleanOtherRooms){
		if(cleanOtherRooms){
			gameObjects.clear();
		}
		currentMapID = map.getID();
		if(!gameObjects.containsKey(currentMapID)){
			initGameObjects(map);
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

}
