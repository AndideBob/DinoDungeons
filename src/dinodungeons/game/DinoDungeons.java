package dinodungeons.game;

import dinodungeons.game.data.GameState;
import dinodungeons.game.data.exceptions.InvalidMapIDException;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.MapManager;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapUtil;
import dinodungeons.game.data.transitions.ScreenTransition;
import dinodungeons.game.data.transitions.TransitionType;
import dinodungeons.game.data.transitions.TransitionManager;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.CollisionInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.utils.ScreenFadingHelper;
import dinodungeons.game.utils.ScreenScrollingHelper;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import dinodungeons.gfx.ui.UIManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class DinoDungeons extends Game {
	
	private MapManager mapManager;
	private TilesetManager tileSetManager;
	private UIManager uiManager;
	private ScreenScrollingHelper scrollHelper;
	private ScreenFadingHelper fadingHelper;
	
	private GameObjectManager gameObjectManager;
	
	
	private ScreenMap currentMap;
	private ScreenMap lastMap;
	
	
	private GameState gameState;
	
	public DinoDungeons() {
		gameObjectManager = new GameObjectManager();
		mapManager = new MapManager();
		tileSetManager = new TilesetManager();
		uiManager = new UIManager();
		scrollHelper = new ScreenScrollingHelper();
		ScreenMapUtil.setGameHandle(this);
		gameState = GameState.DEFAULT;
	}
	
	private void loadInitialGameState() throws InvalidMapIDException{
		TransitionManager.getInstance().initiateTransition("0000", 116, 104, TransitionType.INSTANT);
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
			gameObjectManager.drawGameObjects(0, 0);
			break;
		case FADING:
			//Draw Map
			for(int x = 0; x < currentMap.getSizeX(); x++){
				for(int y = 0; y < currentMap.getSizeY(); y++){
					ScreenMap relevantMap = fadingHelper.fadingIn() ? lastMap : currentMap;
					BaseLayerTile tile = relevantMap.getBaseLayerTileForPosition(x, y);
					TileSet tileSet = relevantMap.getTileSet();
					tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
				}
			}
			if(fadingHelper.fadingIn()) {
				gameObjectManager.drawLastGameObjects(0,0);
			}
			else {
				gameObjectManager.drawGameObjects(0,0);
			}
			fadingHelper.drawFade();
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
			gameObjectManager.drawGameObjects(offsetNewX,offsetNewY);
			gameObjectManager.drawLastGameObjects(offsetOldX,offsetOldY);
			break;
		}
		uiManager.draw(192, false);
	}

	@Override
	public void loadResources() throws LWJGLAdapterException{
		SpriteManager.getInstance().loadSprites();
		mapManager.loadMaps();
		tileSetManager.loadResources();
		uiManager.loadResources();
		loadInitialGameState();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		switch(gameState){
		case DEFAULT:
			switchMapIfNecessary();
			updateCollisions();
			gameObjectManager.updateGameObjects(deltaTimeInMs);
			break;
		case FADING:
			fadingHelper.update(deltaTimeInMs);
			if(fadingHelper.fadeFinished()){
				gameState = GameState.DEFAULT;
				lastMap = null;
				gameObjectManager.clearLastGameObjects();
			}
			break;
		case SCROLLING:
			scrollHelper.update(deltaTimeInMs);
			if(scrollHelper.scrollingFinished()){
				gameState = GameState.DEFAULT;
				lastMap = null;
				gameObjectManager.clearLastGameObjects();
			}
			break;
		}
		
	}
	
	private void switchMapIfNecessary() throws InvalidMapIDException {
		if(TransitionManager.getInstance().shouldTransition()){
			ScreenTransition transition = TransitionManager.getInstance().getNextTransition();
			Logger.logDebug(transition.getTransitionType().toString() + "-Transition to Map[" + transition.getDestinationMapID() + "] at [" +
					transition.getDestinationXPosition() + "," + transition.getDestinationYPosition() + "]");
			if(transition.getTransitionType().isScrollTransition()){
				gameState = GameState.SCROLLING;
				scrollHelper.startScrolling(transition.getTransitionType());
				lastMap = currentMap;
				gameObjectManager.storeCurrentGameObjects();
			}
			else if(transition.getTransitionType() == TransitionType.TELEPORT) {
				gameState = GameState.FADING;
				fadingHelper.startFading(transition.getTransitionType(), gameObjectManager.getPlayerObject().getPositionX() + 8, gameObjectManager.getPlayerObject().getPositionY() + 8);
				lastMap = currentMap;
				gameObjectManager.storeCurrentGameObjects();
			}
			currentMap = mapManager.getMapById(transition.getDestinationMapID());
			gameObjectManager.initGameObjects(ScreenMapUtil.createGameObjectsForMap(currentMap));
			gameObjectManager.setPlayerPosition(transition.getDestinationXPosition(), transition.getDestinationYPosition());
			TransitionManager.getInstance().setCurrentMap(currentMap);
		}
	}
	
	private void updateCollisions() throws CollisionNotSupportedException{
		PhysicsHelper.getInstance().resetCollisions();
		PhysicsHelper.getInstance().checkCollisions();
		for(GameObject o1 : gameObjectManager.getGameObjects()){
			o1.clearCollisionInformation();
			for(GameObject o2 : gameObjectManager.getGameObjects()){
				if(!o1.equals(o2)){
					for(Collider c1 : o1.getColliders()){
						for(Collider c2 : o2.getColliders()){
							Collision collision = PhysicsHelper.getInstance().checkCollisionBetween(c1, c2);
							if(collision != null){
								CollisionInformation collisionInformation = new CollisionInformation(o2.getTag(), collision);
								o1.addCollisionInformation(c1.getID(), collisionInformation);
							}
						}
					}
				}
			}
		}
	}

}
