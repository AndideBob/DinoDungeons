package dinodungeons.game;

import java.util.Collection;

import dinodungeons.game.data.GameState;
import dinodungeons.game.data.exceptions.InvalidMapIDException;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.RoomEvent;
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
import dinodungeons.game.gameobjects.immovable.RoomSwitchDoorObject;
import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.game.gameobjects.switches.StonePushSwitch;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.game.utils.MenuManager;
import dinodungeons.game.utils.ScreenFadingHelper;
import dinodungeons.game.utils.ScreenScrollingHelper;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import dinodungeons.gfx.ui.DrawUIManager;
import dinodungeons.sfx.sound.Song;
import dinodungeons.sfx.sound.SoundManager;
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
	private DrawUIManager drawUiManager;
	private ScreenScrollingHelper scrollHelper;
	private ScreenFadingHelper fadingHelper;
	private MenuManager menuManager;
	
	private InputInformation inputInformation;
	
	
	private ScreenMap currentMap;
	private ScreenMap lastMap;
	
	
	private GameState gameState;
	
	public DinoDungeons() {
		mapManager = new MapManager();
		drawUiManager = new DrawUIManager();
		menuManager = new MenuManager();
		scrollHelper = new ScreenScrollingHelper();
		fadingHelper = new ScreenFadingHelper();
		inputInformation = new InputInformation();
		ScreenMapUtil.setGameHandle(this);
		gameState = GameState.DEFAULT;
	}
	
	private void loadInitialGameState() throws InvalidMapIDException{
		TransitionManager.getInstance().initiateTransition("0000", 116, 104, TransitionType.INSTANT);
		SoundManager.getInstance().playMusic(Song.MAIN_THEME);
	}

	@Override
	public void draw() {
		switch(gameState){
		case MENU_TRANSITION:
		case TEXTBOX:
		case DEFAULT:
			//DrawMap
			if(currentMap != null){
				TileSet tileSet = currentMap.getTileSet();
				for(int x = 0; x < currentMap.getSizeX(); x++){
					for(int y = 0; y < currentMap.getSizeY(); y++){
						BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
						TilesetManager.getInstance().drawTile(tile, tileSet, x * 16, y * 16);
					}
				}
			}
			//DrawGameObjects
			GameObjectManager.getInstance().drawGameObjects(currentMap, 0, 0, true);
			break;
		case FADING:
			//Draw Map
			for(int x = 0; x < currentMap.getSizeX(); x++){
				for(int y = 0; y < currentMap.getSizeY(); y++){
					BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
					TileSet tileSet = currentMap.getTileSet();
					TilesetManager.getInstance().drawTile(tile, tileSet, x * 16, y * 16);
				}
			}
			GameObjectManager.getInstance().drawGameObjects(currentMap, 0, 0, true);
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
					TilesetManager.getInstance().drawTile(tile, tileSet, offsetNewX + x * 16, offsetNewY + y * 16);
					tile = lastMap.getBaseLayerTileForPosition(x, y);
					tileSet = lastMap.getTileSet();
					TilesetManager.getInstance().drawTile(tile, tileSet, offsetOldX + x * 16, offsetOldY + y * 16);
				}
			}
			//DrawGameObjects
			GameObjectManager.getInstance().drawGameObjects(currentMap, offsetNewX, offsetNewY, true);
			GameObjectManager.getInstance().getPlayerObject().draw(offsetNewX, offsetNewY);
			GameObjectManager.getInstance().drawGameObjects(lastMap, offsetOldX, offsetOldY, false);
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
		SoundManager.getInstance().loadSounds();
		mapManager.loadMaps();
		TilesetManager.getInstance().loadResources();
		drawUiManager.loadResources();
		loadInitialGameState();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		updateDebug();
		inputInformation.update();
		switch(gameState){
		case DEFAULT:
			updateCollisions();
			GameObjectManager.getInstance().updateCurrentGameObjects(deltaTimeInMs, inputInformation);
			menuManager.update(deltaTimeInMs, inputInformation);
			if(!checkMenuStatusChange()){
				if(!switchMapIfNecessary()){
					checkTextBoxTriggered();
				}
			}
			break;
		case MENU_TRANSITION:
			menuManager.update(deltaTimeInMs, inputInformation);
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
			menuManager.update(deltaTimeInMs, inputInformation);
			checkMenuStatusChange();
			break;
		case TEXTBOX:
			GameObjectManager.getInstance().updateCurrentTextBox(deltaTimeInMs, inputInformation);
			checkTextBoxTriggered();
			break;
		case FADING:
			fadingHelper.update(deltaTimeInMs);
			if(fadingHelper.shouldTransition()){
				currentMap = mapManager.getMapById(fadingHelper.getDestinationMapID());
				GameObjectManager.getInstance().setCurrentMap(currentMap, true);
				GameObjectManager.getInstance().setPlayerPosition(fadingHelper.getDestinationX(), fadingHelper.getDestinationY());
				TransitionManager.getInstance().setCurrentMap(currentMap);
			}
			else if(fadingHelper.fadeFinished()){
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
	
	private boolean switchMapIfNecessary() throws InvalidMapIDException {
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
				fadingHelper.setDestination(transition.getDestinationMapID(), transition.getDestinationXPosition(), transition.getDestinationYPosition());
				fadingHelper.startFading(transition.getTransitionType(), GameObjectManager.getInstance().getPlayerObject().getPositionX() + 8, GameObjectManager.getInstance().getPlayerObject().getPositionY() + 8);
				return true;
			}
			currentMap = mapManager.getMapById(transition.getDestinationMapID());
			GameObjectManager.getInstance().setCurrentMap(currentMap, false);
			GameObjectManager.getInstance().setPlayerPosition(transition.getDestinationXPosition(), transition.getDestinationYPosition());
			TransitionManager.getInstance().setCurrentMap(currentMap);
			return true;
		}
		return false;
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
	
	private boolean checkMenuStatusChange() {
		if(menuManager.isInTransition()){
			gameState = GameState.MENU_TRANSITION;
			return true;
		}
		return false;
	}
	
	private boolean checkTextBoxTriggered() {
		if(GameObjectManager.getInstance().isTextBoxQueued()){
			gameState = GameState.TEXTBOX;
			return true;
		}
		else{
			gameState = GameState.DEFAULT;
			return false;
		}
	}
	
	private void updateDebug(){
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_1).equals(ButtonState.RELEASED)){
			PlayerInventoryManager.getInstance().collectItem(ItemID.CLUB);
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_2).equals(ButtonState.RELEASED)){
			PlayerInventoryManager.getInstance().collectItem(ItemID.BOOMERANG);
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_3).equals(ButtonState.RELEASED)){
			PlayerInventoryManager.getInstance().collectItem(ItemID.TORCH);
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_4).equals(ButtonState.RELEASED)){
			PlayerInventoryManager.getInstance().collectItem(ItemID.BOMB);
		}
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_CRTL_RIGHT).equals(ButtonState.RELEASED)){
			TextBoxContent testTextBox = new TextBoxContent();
			testTextBox.setLine(0, "Hello, this is a test");
			testTextBox.setLine(1, "Short");
			testTextBox.setLine(2, "A looooooooooooooooooooooooooooooooong line is this");
			testTextBox.setLine(3, "Well I need more text");
			testTextBox.setLine(4, "The end!");
			GameObjectManager.getInstance().queueTextBox(testTextBox);
			TextBoxContent testTextBox2 = new TextBoxContent();
			testTextBox2.setLine(0, "Oh and this is the");
			testTextBox2.setLine(1, "second text box!");
			GameObjectManager.getInstance().queueTextBox(testTextBox2);
		}
	}

}
