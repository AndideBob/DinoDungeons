package dinodungeons.game.gameobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.gameobjects.player.PlayerObject;

public class GameObjectManager {

	private ArrayList<GameObject> lastGameObjects;
	private ArrayList<GameObject> gameObjects;
	private PlayerObject player;
	
	public GameObjectManager() {
		gameObjects = new ArrayList<>();
		lastGameObjects = new ArrayList<>();
	}
	
	private PlayerObject getPlayerObject(){
		if(player == null){
			player = new PlayerObject(GameObjectTag.PLAYER, 0, 0);
		}
		return player;
	}
	
	public void updateGameObjects(long deltaTimeInMs){
		for(GameObject o : gameObjects){
			o.update(deltaTimeInMs);
		}
	}
	
	public void drawGameObjects(int offsetX, int offsetY){
		for(GameObject o : gameObjects){
			o.draw(offsetX,offsetY);
		}
	}

	public void drawLastGameObjects(int offsetOldX, int offsetOldY) {
		for(GameObject o : lastGameObjects){
			o.draw(offsetOldX,offsetOldY);
		}
	}
	
	public void storeCurrentGameObjects(){
		lastGameObjects.addAll(gameObjects);
		lastGameObjects.remove(getPlayerObject());
	}
	
	public void clearLastGameObjects(){
		lastGameObjects.clear();
	}

	public void initGameObjects(Collection<GameObject> createGameObjectsForMap) {
		gameObjects.clear();
		gameObjects.add(getPlayerObject());
		gameObjects.addAll(createGameObjectsForMap);
	}

	public void setPlayerPosition(int destinationXPosition, int destinationYPosition) {
		getPlayerObject().setPosition(destinationXPosition, destinationYPosition);
	}
	
	public Collection<GameObject> getGameObjects(){
		return Collections.unmodifiableList(gameObjects);
	}

}
