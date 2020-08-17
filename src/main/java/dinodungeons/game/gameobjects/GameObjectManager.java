package dinodungeons.game.gameobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import dinodungeons.game.data.gameplay.GameEventManager;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.RoomEvent;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectDrawComparator;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.enemies.BaseEnemyObject;
import dinodungeons.game.gameobjects.item.ItemBoomerangObject;
import dinodungeons.game.gameobjects.player.PlayerObject;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.game.gameobjects.text.TextBoxObject;
import lwjgladapter.physics.collision.base.Collider;

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
	private ItemBoomerangObject boomerang;
	
	//TextBox
	private TextBoxObject currentTextBox;
	private LinkedList<TextBoxContent> queuedTextBoxes;
	
	private String currentMapID;
	
	private int lastPlayerSpawnX;
	private int lastPlayerSpawnY;
	
	private ArrayList<GameObject> gameObjectsToBeAddedToCurrentMap;
	private ArrayList<GameObject> gameObjectsToBeDeletedDeliberately;
	
	private GameObjectManager() {
		gameObjects = new HashMap<>();
		currentMapID = null;
		boomerang = null;
		gameObjectsToBeDeletedDeliberately = new ArrayList<>();
		gameObjectsToBeAddedToCurrentMap = new ArrayList<>();
		queuedTextBoxes = new LinkedList<>();
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
					checkSpecialGameObjectDeletion(o);
				}
				else{
					o.update(deltaTimeInMs, inputInformation);
				}
			}
			player.update(deltaTimeInMs, inputInformation);
		}
	}
	
	private void checkSpecialGameObjectDeletion(GameObject gameObject){
		if(gameObject.equals(boomerang)) {
			boomerang = null;
		}
		else if(gameObject instanceof BaseEnemyObject){
			if(((BaseEnemyObject) gameObject).isRelevantForRoomSwitch()){
				checkNumberOfEnemiesInRoom();
			}
		}
	}
	
	private void checkNumberOfEnemiesInRoom(){
		int enemyCount = 0;
		for(GameObject o : getCurrentGameObjects()){
			if(o instanceof BaseEnemyObject){
				if(((BaseEnemyObject) o).isRelevantForRoomSwitch()){
					enemyCount++;
				}
			}
		}
		if(enemyCount <= 0){
			GameEventManager.getInstance().activateRoomEvent(RoomEvent.SWITCH_ALL_ENEMIES);
		}
	}
	
	public void drawGameObjects(ScreenMap map, int offsetX, int offsetY, boolean drawPlayer){
		if(map != null && map.getID() != null){
			ArrayList<GameObject> sortedObjects = new ArrayList<>(gameObjects.get(map.getID()));
			if(drawPlayer) {
				sortedObjects.add(player);
			}
			if(currentTextBox != null){
				sortedObjects.add(currentTextBox);
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
	
	public void addBoomerangObjectToCurrentMap(ItemBoomerangObject boomerangGameObject){
		boomerang = boomerangGameObject;
		gameObjectsToBeAddedToCurrentMap.add(boomerangGameObject);
	}
	
	public boolean doesBoomerangExist() {
		return boomerang != null;
	}
	
	public void setCurrentMap(ScreenMap map, boolean cleanOtherRooms){
		GameEventManager.getInstance().clearRoomEvents();
		PlayerInventoryManager.getInstance().setCurrentDungeon(map.getDungeonID());
		if(cleanOtherRooms){
			for(Collection<GameObject> gos : gameObjects.values()) {
				for(GameObject o : gos) {
					o.delete();
				}
			}
			gameObjects.clear();
		}
		boomerang = null;
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
				else{
					o.resetOnRoomEntry();
				}
			}
		}
		checkNumberOfEnemiesInRoom();
	}

	private void initGameObjects(ScreenMap map) {
		Collection<GameObject> objects = ScreenMapUtil.createGameObjectsForMap(map);
		gameObjects.put(map.getID(), objects);
	}

	public void setPlayerPosition(int destinationXPosition, int destinationYPosition) {
		getPlayerObject().setPosition(destinationXPosition, destinationYPosition);
		lastPlayerSpawnX = destinationXPosition;
		lastPlayerSpawnY = destinationYPosition;
	}
	
	public void resetPlayerPositionOnCurrentMap(){
		getPlayerObject().setPosition(lastPlayerSpawnX, lastPlayerSpawnY);
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
	
	public Collection<GameObject> getGameObjectsUsingColliderWithID(long colliderID){
		ArrayList<GameObject> result = new ArrayList<>();
		for(GameObject o : getCurrentGameObjects()) {
			for(Collider c : o.getColliders()) {
				if(c.getID() == colliderID) {
					result.add(o);
					break;
				}
			}
		}
		return result;
	}

	public void destroyGameObjectImmediately(GameObject gameObject) {
		gameObjectsToBeDeletedDeliberately.add(gameObject);
	}
	
	public void queueTextBox(TextBoxContent content){
		queuedTextBoxes.addLast(content);
	}

	public boolean isTextBoxQueued() {
		return currentTextBox != null || !queuedTextBoxes.isEmpty();
	}

	public void updateCurrentTextBox(long deltaTimeInMs, InputInformation inputInformation) {
		if(currentTextBox != null){
			if(currentTextBox.shouldBeDeleted()){
				currentTextBox = null;
				if(!queuedTextBoxes.isEmpty()){
					TextBoxContent content = queuedTextBoxes.poll();
					currentTextBox = new TextBoxObject(content);
				}
			}
			else{
				currentTextBox.update(deltaTimeInMs, inputInformation);
			}
		}
		else{
			if(!queuedTextBoxes.isEmpty()){
				TextBoxContent content = queuedTextBoxes.poll();
				currentTextBox = new TextBoxObject(content);
			}
		}
	}

	public void queueTextBoxes(ArrayList<TextBoxContent> textBoxes) {
		for(TextBoxContent tbc : textBoxes) {
			queueTextBox(tbc);
		}
	}

}
