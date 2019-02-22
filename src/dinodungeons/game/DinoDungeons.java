package dinodungeons.game;

import java.util.ArrayList;

import dinodungeons.game.data.GameState;
import dinodungeons.game.data.exceptions.InvalidMapIDException;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.MapManager;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.data.transitions.ScreenTransition;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.gameobjects.GameObject;
import dinodungeons.game.gameobjects.GameObjectTag;
import dinodungeons.game.gameobjects.player.PlayerObject;
import dinodungeons.game.utils.ScreenScrollingHelper;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class DinoDungeons extends Game {
	
	private MapManager mapManager;
	private TilesetManager tileSetManager;
	private ScreenScrollingHelper scrollHelper;
	
	private ArrayList<GameObject> gameObjects;
	private PlayerObject player;
	private ScreenMap currentMap;
	private ScreenMap lastMap;
	private ArrayList<GameObject> lastMapObjects;
	
	private GameState gameState;
	
	public DinoDungeons() {
		mapManager = new MapManager();
		tileSetManager = new TilesetManager();
		scrollHelper = new ScreenScrollingHelper();
		gameObjects = new ArrayList<>();
		lastMapObjects = new ArrayList<>();
		ScreenMapUtil.setGameHandle(this);
		gameState = GameState.DEFAULT;
	}
	
	private void loadInitialGameState() throws InvalidMapIDException{
		TransitionManager.getInstance().initiateTransition("0000", 32, 32, TransitionType.INSTANT);
	}

	@Override
	public void draw() {
		switch(gameState){
		case DEFAULT:
			//DrawMap
			if(currentMap != null){
				TileSet tileSet = currentMap.getTileSet();
				for(int x = 0; x < currentMap.getSizeX(); x++){
					for(int y = 0; y < currentMap.getSizeY(); y++){
						BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
						tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
					}
				}
			}
			//DrawGameObjects
			for(GameObject o : gameObjects){
				o.draw(0,0);
			}
			break;
		case SCROLLING:
			int offsetOldX = scrollHelper.getOffsetOldX();
			int offsetOldY = scrollHelper.getOffsetOldY();
			int offsetNewX = scrollHelper.getOffsetNewX();
			int offsetNewY = scrollHelper.getOffsetNewY();
			//Draw Maps
			for(int x = 0; x < currentMap.getSizeX(); x++){
				for(int y = 0; y < currentMap.getSizeY(); y++){
					BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
					TileSet tileSet = currentMap.getTileSet();
					tileSetManager.drawTile(tile, tileSet, offsetNewX + x * 16, offsetNewY + y * 16);
					tile = lastMap.getBaseLayerTileForPosition(x, y);
					tileSet = lastMap.getTileSet();
					tileSetManager.drawTile(tile, tileSet, offsetOldX + x * 16, offsetOldY + y * 16);
				}
			}
			//DrawGameObjects
			for(GameObject o : gameObjects){
				o.draw(offsetNewX,offsetNewY);
			}
			for(GameObject o : lastMapObjects){
				o.draw(offsetOldX,offsetOldY);
			}
			break;
		}
		
	}

	@Override
	public void loadResources() throws LWJGLAdapterException{
		SpriteManager.getInstance().loadSprites();
		mapManager.loadMaps();
		tileSetManager.loadResources();
		loadInitialGameState();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		switch(gameState){
		case DEFAULT:
			switchMapIfNecessary();
			updateCollisions();
			for(GameObject o : gameObjects){
				o.update(deltaTimeInMs);
			}
			break;
		case SCROLLING:
			scrollHelper.update(deltaTimeInMs);
			if(scrollHelper.scrollingFinished()){
				gameState = GameState.DEFAULT;
				lastMap = null;
				lastMapObjects.clear();
			}
			break;
		}
		
	}
	
	private void switchMapIfNecessary() throws InvalidMapIDException {
		if(TransitionManager.getInstance().shouldTransition()){
			ScreenTransition transition = TransitionManager.getInstance().getNextTransition();
			Logger.log(transition.getTransitionType().toString() + "-Transition to Map[" + transition.getDestinationMapID() + "] at [" +
					transition.getDestinationXPosition() + "," + transition.getDestinationYPosition() + "]");
			if(transition.getTransitionType().isScrollTransition()){
				gameState = GameState.SCROLLING;
				scrollHelper.startScrolling(transition.getTransitionType());
				lastMap = currentMap;
				gameObjects.remove(player);
				lastMapObjects.addAll(gameObjects);
			}
			currentMap = mapManager.getMapById(transition.getDestinationMapID());
			gameObjects.clear();
			gameObjects.addAll(ScreenMapUtil.createGameObjectsForMap(currentMap));
			if(player == null){
				player = new PlayerObject(GameObjectTag.PLAYER, transition.getDestinationXPosition(), transition.getDestinationYPosition());
			}
			else{
				player.setPosition(transition.getDestinationXPosition(), transition.getDestinationYPosition());
			}
			gameObjects.add(player);
			TransitionManager.getInstance().setCurrentMap(currentMap);
		}
	}
	
	private void updateCollisions() throws CollisionNotSupportedException{
		PhysicsHelper.getInstance().resetCollisions();
		for(GameObject o1 : gameObjects){
			o1.clearCollisionTags();
			for(GameObject o2 : gameObjects){
				if(!o1.equals(o2)){
					for(Collider c1 : o1.getColliders()){
						for(Collider c2 : o2.getColliders()){
							if(PhysicsHelper.getInstance().checkCollisionBetween(c1, c2)){
								o1.addCollisionTag(c1.getKey(), o2.getTag());
							}
						}
					}
				}
			}
		}
	}

}
