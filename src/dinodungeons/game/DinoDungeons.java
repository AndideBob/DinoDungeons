package dinodungeons.game;

import java.util.Collection;

import dinodungeons.game.data.DinoDungeonsConstants;
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
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.game.gameobjects.collectable.MoneyObject;
import dinodungeons.game.utils.MenuManager;
import dinodungeons.game.utils.ScreenFadingHelper;
import dinodungeons.game.utils.ScreenScrollingHelper;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import dinodungeons.gfx.ui.DrawUIManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class DinoDungeons extends Game {
	
	private MapManager mapManager;
	private TilesetManager tileSetManager;
	private DrawUIManager drawUiManager;
	private ScreenScrollingHelper scrollHelper;
	private ScreenFadingHelper fadingHelper;
	private MenuManager menuManager;
	
	
	private ScreenMap currentMap;
	private ScreenMap lastMap;
	
	
	private GameState gameState;
	
	public DinoDungeons() {
		mapManager = new MapManager();
		tileSetManager = new TilesetManager();
		drawUiManager = new DrawUIManager();
		menuManager = new MenuManager();
		scrollHelper = new ScreenScrollingHelper();
		fadingHelper = new ScreenFadingHelper();
		ScreenMapUtil.setGameHandle(this);
		gameState = GameState.DEFAULT;
	}
	
	private void loadInitialGameState() throws InvalidMapIDException{
		TransitionManager.getInstance().initiateTransition("0000", 116, 104, TransitionType.INSTANT);
	}

	@Override
	public void draw() {
		switch(gameState){
		case MENU_TRANSITION:
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
			GameObjectManager.getInstance().drawGameObjects(currentMap, 0, 0);
			GameObjectManager.getInstance().getPlayerObject().draw(0, 0);
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
				GameObjectManager.getInstance().drawGameObjects(lastMap, 0, 0);
			}
			else {
				GameObjectManager.getInstance().drawGameObjects(currentMap, 0,0);
				GameObjectManager.getInstance().getPlayerObject().draw(0, 0);
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
			GameObjectManager.getInstance().drawGameObjects(currentMap, offsetNewX, offsetNewY);
			GameObjectManager.getInstance().getPlayerObject().draw(offsetNewX, offsetNewY);
			GameObjectManager.getInstance().drawGameObjects(lastMap, offsetOldX, offsetOldY);
			break;
		case MENU:
			//Menu will be drawn either way!
			break;
		}
		drawUiManager.draw(menuManager);
	}

	@Override
	public void loadResources() throws LWJGLAdapterException{
		SpriteManager.getInstance().loadSprites();
		mapManager.loadMaps();
		tileSetManager.loadResources();
		drawUiManager.loadResources();
		loadInitialGameState();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		updateDebug();
		switch(gameState){
		case DEFAULT:
			switchMapIfNecessary();
			updateCollisions();
			GameObjectManager.getInstance().updateCurrentGameObjects(deltaTimeInMs);
			menuManager.update(deltaTimeInMs);
			checkMenuStatusChange();
			break;
		case MENU_TRANSITION:
			menuManager.update(deltaTimeInMs);
			if(!menuManager.isInTransition()) {
				if(menuManager.isInMenu()) {
					gameState = GameState.MENU;
				}
				else {
					gameState = GameState.DEFAULT;
				}
			}
			break;
		case MENU:
			menuManager.update(deltaTimeInMs);
			checkMenuStatusChange();
			break;
		case FADING:
			fadingHelper.update(deltaTimeInMs);
			if(fadingHelper.fadeFinished()){
				gameState = GameState.DEFAULT;
				lastMap = null;
			}
			break;
		case SCROLLING:
			scrollHelper.update(deltaTimeInMs);
			if(scrollHelper.scrollingFinished()){
				gameState = GameState.DEFAULT;
				lastMap = null;
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
			}
			else if(transition.getTransitionType() == TransitionType.TELEPORT) {
				gameState = GameState.FADING;
				fadingHelper.startFading(transition.getTransitionType(), GameObjectManager.getInstance().getPlayerObject().getPositionX() + 8, GameObjectManager.getInstance().getPlayerObject().getPositionY() + 8);
				lastMap = currentMap;
				GameObjectManager.getInstance().setCurrentMap(lastMap, true);
			}
			currentMap = mapManager.getMapById(transition.getDestinationMapID());
			GameObjectManager.getInstance().setCurrentMap(currentMap, false);
			GameObjectManager.getInstance().setPlayerPosition(transition.getDestinationXPosition(), transition.getDestinationYPosition());
			TransitionManager.getInstance().setCurrentMap(currentMap);
		}
	}
	
	private void updateCollisions() throws CollisionNotSupportedException{
		PhysicsHelper.getInstance().resetCollisions();
		PhysicsHelper.getInstance().checkCollisions();
		Collection<GameObject> gameObjects = GameObjectManager.getInstance().getCurrentGameObjects();
		for(GameObject o1 : gameObjects){
			o1.clearCollisionInformation();
			for(GameObject o2 : gameObjects){
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
	
	private void checkMenuStatusChange() {
		if(menuManager.isInTransition()){
			gameState = GameState.MENU_TRANSITION;
		}
	}
	
	private void updateDebug(){
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_SPACE).equals(ButtonState.RELEASED)){
			int x = DinoDungeonsConstants.random.nextInt(DinoDungeonsConstants.mapWidth - 10);
			int y = DinoDungeonsConstants.random.nextInt(DinoDungeonsConstants.mapHeight - 10);
			switch(DinoDungeonsConstants.random.nextInt(4)){
			case 0:
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new MoneyObject(GameObjectTag.COLLECTABLE_MONEY_OBJECT_VALUE_ONE, x, y));
				break;
			case 1:
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new MoneyObject(GameObjectTag.COLLECTABLE_MONEY_OBJECT_VALUE_FIVE, x, y));
				break;
			case 2:
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new MoneyObject(GameObjectTag.COLLECTABLE_MONEY_OBJECT_VALUE_TEN, x, y));
				break;
			case 3:
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new MoneyObject(GameObjectTag.COLLECTABLE_MONEY_OBJECT_VALUE_TWENTYFIVE, x, y));
				break;
			}
			
		}
	}

}
