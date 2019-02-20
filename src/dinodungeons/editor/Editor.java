package dinodungeons.editor;

import java.util.Arrays;
import java.util.HashSet;

import dinodungeons.game.data.exceptions.ScreenMapIndexOutOfBounds;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.ScreenMapConstants;
import dinodungeons.game.data.map.ScreenMapLoader;
import dinodungeons.game.data.map.ScreenMapSaver;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.TransportMapObject;
import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
import dinodungeons.gfx.GFXResourceID;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.TileSet;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.game.Game;
import lwjgladapter.gfx.Texture;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.input.MouseButton;
import lwjgladapter.logging.Logger;

public class Editor extends Game {

	
	
	ScreenMap currentMap;
	
	ScreenMapLoader loader;
	ScreenMapSaver saver;
	
	private int[] currentMousePosition = new int[2];
	private EditorState currentState;
	private TextUsage textUsage;
	
	private String enteredText = "";
	private String infoText = "";
	
	//Base Layer Placement
	private int currentSelection = 0;
	
	//Tile Sets
	public static final TileSet[] tileSets = TileSet.values();
	//Entrance Types
	public static final TransportationType[] transportationTypes = TransportationType.values();
	private String exitMapID = "0000";
	private int exitPosX = 0;
	private int exitPosY = 0;
	
	EditorDrawManager drawManager;
	
	public Editor() {
		loader = new ScreenMapLoader();
		saver = new ScreenMapSaver();
		currentMap = new ScreenMap("000", 16, 12);
		switchToState(EditorState.INSPECTOR);
		drawManager = new EditorDrawManager();
	}

	@Override
	public void draw() {
		drawManager.setCurrentMap(currentMap);
		//DrawCurrentMap
		drawManager.drawMap();
		//DrawExitLayer
		if(currentState == EditorState.PLACE_EXITS ||
				currentState == EditorState.INSPECTOR){
			drawManager.drawObjectsLayer();
		}
		//DrawPointer
		if(isMouseOnMap()) {
			int x = currentMousePosition[0] / 16;
			int y = currentMousePosition[1] / 16;
			drawManager.drawSelector(x, y);
		}
		//DrawUI
		drawManager.drawUI(currentState, currentSelection);
		//InfoText
		drawManager.drawInfoText(infoText);
		//EnterText
		if(currentState == EditorState.ENTER_TEXT) {
			drawManager.drawEnteredText(enteredText);
		}
	}

	

	private boolean isMouseOnMap() {
		if(currentState == EditorState.ENTER_TEXT) {
			return false;
		}
		return currentMousePosition[1] < 192;
	}
	
	private int[] getMousePosition() {
		int[] result = new int[2];
		result[0] = InputManager.instance.getRelativeMousePositionXAsInt();
		result[1] = InputManager.instance.getRelativeMousePositionYAsInt();
		return result;
	}

	@Override
	public void loadResources() {
		drawManager.loadTextures();
	}

	@Override
	public void update(long deltaTimeInMs) {
		currentMousePosition = getMousePosition();
		if(currentState != EditorState.ENTER_TEXT) {
			//Enter Save Mode
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F1).equals(ButtonState.RELEASED)) {
				switchToState(EditorState.ENTER_TEXT);
				textUsage = TextUsage.SAVING;
				infoText = "Enter File-id to save:";
				enteredText = "";
				return;
			}
			//Enter Load Mode
			if(currentState != EditorState.ENTER_TEXT) {
				if(InputManager.instance.getKeyState(KeyboardKey.KEY_F2).equals(ButtonState.RELEASED)) {
					switchToState(EditorState.ENTER_TEXT);
					textUsage = TextUsage.LOAD;
					infoText = "Enter File-id to load:";
					enteredText = "";
					return;
				}
			}
			//Enter Entrance Mode
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F3).equals(ButtonState.RELEASED)) {
				switchToState(EditorState.ENTER_TEXT);
				textUsage = TextUsage.EXIT_NORTH;
				infoText = "Enter Map-ID upwards:";
				enteredText = currentMap.getTransitionUpID();
				return;
			}
			//Enter Exit Placement Edit Mode
			if(currentState == EditorState.PLACE_EXITS && InputManager.instance.getKeyState(KeyboardKey.KEY_F5).equals(ButtonState.RELEASED)) {
				switchToState(EditorState.ENTER_TEXT);
				textUsage = TextUsage.EXIT_MAP_ID;
				infoText = "Enter Destination Map-ID:";
				enteredText = exitMapID;
				return;
			}
			//Switch Mode
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_LEFT).equals(ButtonState.RELEASED)) {
				switchToPreviousState();
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_RIGHT).equals(ButtonState.RELEASED)) {
				switchToNextState();
			}
		}
		//General Commands
		switch(currentState) {
		case INSPECTOR:
			//Placing Tiles
			if(isMouseOnMap()) {
				int x = currentMousePosition[0] / 16;
				int y = currentMousePosition[1] / 16;
				MapObject mapObject = currentMap.getMapObjectForPosition(x, y);
				if(mapObject instanceof TransportMapObject){
					TransportMapObject transport = (TransportMapObject)mapObject;
					infoText = "";
					switch(transport.getTransportationType()){
					case CAVE_ENTRY:
						infoText += "Cave En";
						break;
					case CAVE_EXIT:
						infoText += "Cave Ex";
						break;
					case DUNGEON_EXIT:
						infoText += "Dngn Ex";
						break;
					case INSTANT_TELEPORT:
						infoText += "Instant";
						break;
					case STAIRS:
						infoText += "Stairs";
						break;
					}
					infoText += " Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
				}
				else{
					infoText = "";
				}
			}
			else{
				infoText = "Select a tile on the map!";
			}
			break;
		case ENTER_TEXT:
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER).equals(ButtonState.RELEASED)) {
				if(enteredText.length() > 0) {
					switch (textUsage) {
					case LOAD:
						currentMap = loader.loadMap(enteredText);
						infoText = "Load successfull!";
						switchToState(EditorState.PLACE_BASELAYER);
						break;
					case SAVING:
						if(saver.saveMap(enteredText, currentMap)) {
							infoText = "Save successfull!";
						}
						else {
							infoText = "An error occured during saving!";
						}
						switchToState(EditorState.PLACE_BASELAYER);
						break;
					case EXIT_EAST:
						switchToState(EditorState.ENTER_TEXT);
						textUsage = TextUsage.EXIT_SOUTH;
						infoText = "Enter Map-ID downwards:";
						currentMap.setTransitionRightID(enteredText);
						enteredText = currentMap.getTransitionDownID();
						break;
					case EXIT_NORTH:
						switchToState(EditorState.ENTER_TEXT);
						textUsage = TextUsage.EXIT_EAST;
						infoText = "Enter Map-ID right:";
						currentMap.setTransitionUpID(enteredText);
						enteredText = currentMap.getTransitionRightID();
						break;
					case EXIT_SOUTH:
						switchToState(EditorState.ENTER_TEXT);
						textUsage = TextUsage.EXIT_WEST;
						infoText = "Enter Map-ID left:";
						currentMap.setTransitionDownID(enteredText);
						enteredText = currentMap.getTransitionLeftID();
						break;
					case EXIT_WEST:
						infoText = "Exits updated!";
						currentMap.setTransitionLeftID(enteredText);
						switchToState(EditorState.PLACE_BASELAYER);
						break;
					case EXIT_MAP_ID:
						switchToState(EditorState.ENTER_TEXT);
						textUsage = TextUsage.EXIT_MAP_X;
						infoText = "Enter Destination X-Position:";
						exitMapID = enteredText;
						enteredText = String.valueOf(exitPosX);
						break;
					case EXIT_MAP_X:
						switchToState(EditorState.ENTER_TEXT);
						textUsage = TextUsage.EXIT_MAP_Y;
						infoText = "Enter Destination Y-Position:";
						exitPosX = Integer.parseInt(enteredText);
						enteredText = String.valueOf(exitPosY);
						break;
					case EXIT_MAP_Y:
						switchToState(EditorState.PLACE_EXITS);
						exitPosY = Integer.parseInt(enteredText);
						infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
						break;
					default:
						Logger.logError("Text usage not defined!");
						infoText = "Error!";
						switchToState(EditorState.PLACE_BASELAYER);
						break;
					}
				}
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE).equals(ButtonState.RELEASED)) {
				if(enteredText.length() > 0) {
					enteredText = enteredText.substring(0, enteredText.length()-1);
				}
			}
			else if(enteredText.length() < 4){
				enteredText += getTextInput();
			}
			break;
		case PLACE_BASELAYER:
			//Selection
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + ScreenMapConstants.maxBaseLayerSelection) % (ScreenMapConstants.maxBaseLayerSelection + 1);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + 1) % (ScreenMapConstants.maxBaseLayerSelection + 1);
			}
			//Placing Tiles
			if(isMouseOnMap()) {
				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
					int x = currentMousePosition[0] / 16;
					int y = currentMousePosition[1] / 16;
					int tileValue = currentMap.getBaseLayerValueForPosition(x, y);
					if(tileValue != currentSelection) {
						try {
							currentMap.setBaseLayer(x, y, currentSelection);
							currentMap.updateBaseLayerTiles();
						} catch (ScreenMapIndexOutOfBounds e) {
							Logger.logError(e);
						}
					}
				}
			}
			break;
		case CHANGE_TILESET:
			//Selection
			int oldTileSetSelection = currentSelection;
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + tileSets.length -1) % (tileSets.length);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + 1) % (tileSets.length);
			}
			if(oldTileSetSelection != currentSelection){
				currentMap.setTileSet(tileSets[currentSelection]);
			}
			break;
		case PLACE_EXITS:
			//Selection
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + transportationTypes.length -1) % (transportationTypes.length);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentSelection = (currentSelection + 1) % (transportationTypes.length);
			}
			//Placing Exits
			if(isMouseOnMap()) {
				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.RELEASED)) {
					int x = currentMousePosition[0] / 16;
					int y = currentMousePosition[1] / 16;
					TransportMapObject transport = new TransportMapObject();
					transport.setDestinationMapID(exitMapID);
					transport.setX(exitPosX);
					transport.setY(exitPosY);
					transport.setTransportationType(transportationTypes[currentSelection]);
					currentMap.setMapObjectForPosition(x, y, transport);
				}
			}
			break;
		}
	}
	
	private void switchToState(EditorState state){
		currentSelection = 0;
		switch(state){
		case CHANGE_TILESET:
			for(int i = 0; i < tileSets.length; i++){
				if(tileSets[i].equals(currentMap.getTileSet())){
					currentSelection = i;
				}
			}
			break;
		case ENTER_TEXT:
			break;
		case INSPECTOR:
			break;
		case PLACE_BASELAYER:
			break;
		case PLACE_EXITS:
			infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
			break;
		default:
			break;
		}
		currentState = state;
	}
	
	private void switchToPreviousState() {
		switch(currentState) {
		case ENTER_TEXT:
			break;
		case INSPECTOR:
			switchToState(EditorState.PLACE_EXITS);
			break;
		case PLACE_BASELAYER:
			switchToState(EditorState.INSPECTOR);
			break;
		case CHANGE_TILESET:
			switchToState(EditorState.PLACE_BASELAYER);
			break;
		case PLACE_EXITS:
			switchToState(EditorState.CHANGE_TILESET);
			break;
		}
	}
	
	private void switchToNextState() {
		switch(currentState) {
		case ENTER_TEXT:
			break;
		case INSPECTOR:
			switchToState(EditorState.PLACE_BASELAYER);
			break;
		case PLACE_BASELAYER:
			switchToState(EditorState.CHANGE_TILESET);
			break;
		case CHANGE_TILESET:
			switchToState(EditorState.PLACE_EXITS);
			break;
		case PLACE_EXITS:
			switchToState(EditorState.INSPECTOR);
			break;
		}
	}
	
	private String getTextInput() {
		HashSet<KeyboardKey> keySet = new HashSet<>();
		keySet.addAll(Arrays.asList(KeyboardKey.letterButtons));
		keySet.addAll(Arrays.asList(KeyboardKey.numberButtons));
		for(KeyboardKey key : keySet) {
			if(InputManager.instance.getKeyState(key).equals(ButtonState.RELEASED)) {
				switch (key) {
				case KEY_0:
					return "0";
				case KEY_1:
					return "1";
				case KEY_2:
					return "2";
				case KEY_3:
					return "3";
				case KEY_4:
					return "4";
				case KEY_5:
					return "5";
				case KEY_6:
					return "6";
				case KEY_7:
					return "7";
				case KEY_8:
					return "8";
				case KEY_9:
					return "9";
				case KEY_A:
					return "A";
				case KEY_B:
					return "B";
				case KEY_C:
					return "C";
				case KEY_D:
					return "D";
				case KEY_E:
					return "E";
				case KEY_F:
					return "F";
				case KEY_G:
					return "G";
				case KEY_H:
					return "H";
				case KEY_I:
					return "I";
				case KEY_J:
					return "J";
				case KEY_K:
					return "K";
				case KEY_L:
					return "L";
				case KEY_M:
					return "M";
				case KEY_N:
					return "N";
				case KEY_O:
					return "O";
				case KEY_P:
					return "P";
				case KEY_Q:
					return "Q";
				case KEY_R:
					return "R";
				case KEY_S:
					return "S";
				case KEY_T:
					return "T";
				case KEY_U:
					return "U";
				case KEY_V:
					return "V";
				case KEY_W:
					return "W";
				case KEY_X:
					return "X";
				case KEY_Y:
					return "Y";
				case KEY_Z:
					return "Z";
				default:
					break;
				}
			}			
		}
		return "";
	}
	
	private enum TextUsage{
		SAVING,
		LOAD,
		EXIT_NORTH,
		EXIT_SOUTH,
		EXIT_EAST,
		EXIT_WEST,
		EXIT_MAP_ID,
		EXIT_MAP_X,
		EXIT_MAP_Y
	}

}
