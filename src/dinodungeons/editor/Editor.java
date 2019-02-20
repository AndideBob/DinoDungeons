package dinodungeons.editor;

import java.util.Arrays;
import java.util.HashSet;

import dinodungeons.game.data.exceptions.ScreenMapIndexOutOfBounds;
import dinodungeons.game.data.map.BaseLayerTile;
import dinodungeons.game.data.map.ScreenMap;
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

	TilesetManager tileSetManager = new TilesetManager();
	DrawTextManager textManager;
	ScreenMap currentMap;
	
	ScreenMapLoader loader;
	ScreenMapSaver saver;
	
	private int[] currentMousePosition = new int[2];
	private EditorState currentState;
	private TextUsage textUsage;
	
	private String enteredText = "";
	private String infoText = "";
	
	//Base Layer Placement
	private static final int minBaseLayerSelection = 0;
	private static final int maxBaseLayerSelection = 5;
	private int currentTileSelection = minBaseLayerSelection;
	private int currentTileSetSelection = minBaseLayerSelection;
	private int currentExitSelection = 0;
	private Texture selectorTexture;
	
	//Tile Sets
	private final TileSet[] tileSets = TileSet.values();
	//Entrance Types
	private final TransportationType[] transportationTypes = TransportationType.values();
	private String exitMapID = "0000";
	private int exitPosX = 0;
	private int exitPosY = 0;
	
	public Editor() {
		loader = new ScreenMapLoader();
		saver = new ScreenMapSaver();
		currentMap = new ScreenMap("000", 16, 12);
		currentState = EditorState.PLACE_BASELAYER;
	}

	@Override
	public void draw() {
		//DrawCurrentMap
		for(int x = 0; x < currentMap.getSizeX(); x++){
			for(int y = 0; y < currentMap.getSizeY(); y++){
				BaseLayerTile tile = currentMap.getBaseLayerTileForPosition(x, y);
				TileSet tileSet = currentMap.getTileSet();
				tileSetManager.drawTile(tile, tileSet, x * 16, y * 16);
			}
		}
		//DrawExitLayer
		if(currentState == EditorState.PLACE_EXITS){
			for(int x = 0; x < currentMap.getSizeX(); x++){
				for(int y = 0; y < currentMap.getSizeY(); y++){
					MapObject mapObject = currentMap.getMapObjectForPosition(x, y);
					drawMapObject(mapObject, x, y);
				}
			}
		}
		//DrawPointer
		if(isMouseOnMap()) {
			int x = currentMousePosition[0] / 16;
			int y = currentMousePosition[1] / 16;
			selectorTexture.draw(x * 16, y * 16);
		}
		//DrawUI
		textManager.DrawText(0, 247, "[F1]Save", 9);
		textManager.DrawText(0, 238, "[F2]Load", 9);
		textManager.DrawText(0, 229, "[F3]Exits", 10);
		textManager.DrawText(96, 247, "[</>]", 5);
		//ToolSelection
		int optionsShown = 3;
		int lowest;
		int highest;
		switch(currentState) {
		case PLACE_BASELAYER:
			textManager.DrawText(146, 247, "Base layer", 10);
			optionsShown = 3;
			lowest = Math.max(minBaseLayerSelection, currentTileSelection-optionsShown);
			highest = Math.min(maxBaseLayerSelection, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentTileSelection ? ">" : " ";
				text += "[" + i + "]";
				switch(i) {
				case 0:
					text += "wall";
					break;
				case 1:
					text += "border";
					break;
				case 2:
					text += "floor a";
					break;
				case 3:
					text += "floor b";
					break;
				case 4:
					text += "floor c";
					break;
				case 5:
					text += "stairs";
					break;
				}
				textManager.DrawText(136, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case CHANGE_TILESET:
			textManager.DrawText(146, 247, "Tile Set", 10);
			lowest = Math.max(0, currentTileSetSelection-optionsShown);
			highest = Math.min(tileSets.length -1, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentTileSetSelection ? ">" : " ";
				text += "[" + i + "]";
				text += tileSets[i].getRepresentationInFile();
				textManager.DrawText(126, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case PLACE_EXITS:
			textManager.DrawText(146, 247, "Exits", 10);
			lowest = Math.max(0, currentExitSelection-optionsShown);
			highest = Math.min(transportationTypes.length -1, lowest+optionsShown);
			for(int i = lowest; i <= highest; i++) {
				String text = i == currentExitSelection ? ">" : " ";
				text += "[" + i + "]";
				switch(transportationTypes[i]){
				case CAVE_ENTRY:
					text += "Cave_En";
					break;
				case CAVE_EXIT:
					text += "Cave_Ex";
					break;
				case DUNGEON_EXIT:
					text += "Dngn_Ex";
					break;
				case INSTANT_TELEPORT:
					text += "Instant";
					break;
				case STAIRS:
					text += "Stairs";
					break;
				}
				
				textManager.DrawText(126, 211 + 9*optionsShown, text, 16);
				optionsShown--;
			}
			break;
		case ENTER_TEXT:
		default:
			break;
		}
		//InfoText
		textManager.DrawText(0, 202, infoText, 25);
		//EnterText
		if(currentState == EditorState.ENTER_TEXT) {
			textManager.DrawText(0, 192, enteredText, 4);
			if(enteredText.length() < 4) {
				textManager.DrawText(enteredText.length() * 10, 192, "|", 1);
			}
		}
	}

	private void drawMapObject(MapObject mapObject, int x, int y) {
		if(mapObject instanceof EmptyMapObject){
			//Do not draw anything
			return;
		}
		if(mapObject instanceof TransportMapObject){
			TransportMapObject transport = (TransportMapObject) mapObject;
			String txtUp = "";
			String txtLow = "";
			switch(transport.getTransportationType()){
			case CAVE_ENTRY:
				txtUp = "CV";
				txtLow = "EN";
				break;
			case CAVE_EXIT:
				txtUp = "CV";
				txtLow = "EX";
				break;
			case DUNGEON_EXIT:
				txtUp = "DN";
				txtLow = "EX";
				break;
			case INSTANT_TELEPORT:
				txtUp = "TP";
				break;
			case STAIRS:
				txtUp = "ST";
				txtLow = "RS";
				break;
			}
			textManager.DrawText(x + 3, y + 13, txtUp, 2);
			textManager.DrawText(x + 3, y + 3, txtLow, 2);
			return;
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
		tileSetManager.loadResources();
		textManager = new DrawTextManager(GFXResourceID.TEXT.getFilePath());
		selectorTexture = new Texture(GFXResourceID.EDITOR_SELECTOR.getFilePath());
	}

	@Override
	public void update(long deltaTimeInMs) {
		currentMousePosition = getMousePosition();
		if(currentState != EditorState.ENTER_TEXT) {
			//Enter Save Mode
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F1).equals(ButtonState.RELEASED)) {
				currentState = EditorState.ENTER_TEXT;
				textUsage = TextUsage.SAVING;
				infoText = "Enter File-id to save:";
				enteredText = "";
				return;
			}
			//Enter Load Mode
			if(currentState != EditorState.ENTER_TEXT) {
				if(InputManager.instance.getKeyState(KeyboardKey.KEY_F2).equals(ButtonState.RELEASED)) {
					currentState = EditorState.ENTER_TEXT;
					textUsage = TextUsage.LOAD;
					infoText = "Enter File-id to load:";
					enteredText = "";
					return;
				}
			}
			//Enter Entrance Mode
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F3).equals(ButtonState.RELEASED)) {
				currentState = EditorState.ENTER_TEXT;
				textUsage = TextUsage.EXIT_NORTH;
				infoText = "Enter Map-ID upwards:";
				enteredText = currentMap.getTransitionUpID();
				return;
			}
			//Enter Exit Placement Edit Mode
			if(currentState == EditorState.PLACE_EXITS && InputManager.instance.getKeyState(KeyboardKey.KEY_F5).equals(ButtonState.RELEASED)) {
				currentState = EditorState.ENTER_TEXT;
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
		case ENTER_TEXT:
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER).equals(ButtonState.RELEASED)) {
				if(enteredText.length() > 0) {
					switch (textUsage) {
					case LOAD:
						currentMap = loader.loadMap(enteredText);
						for(int i = 0; i < tileSets.length; i++){
							if(tileSets[i].equals(currentMap.getTileSet())){
								currentTileSetSelection = i;
							}
						}
						infoText = "Load successfull!";
						currentState = EditorState.PLACE_BASELAYER;
						break;
					case SAVING:
						if(saver.saveMap(enteredText, currentMap)) {
							infoText = "Save successfull!";
						}
						else {
							infoText = "An error occured during saving!";
						}
						currentState = EditorState.PLACE_BASELAYER;
						break;
					case EXIT_EAST:
						currentState = EditorState.ENTER_TEXT;
						textUsage = TextUsage.EXIT_SOUTH;
						infoText = "Enter Map-ID downwards:";
						currentMap.setTransitionRightID(enteredText);
						enteredText = currentMap.getTransitionDownID();
						break;
					case EXIT_NORTH:
						currentState = EditorState.ENTER_TEXT;
						textUsage = TextUsage.EXIT_EAST;
						infoText = "Enter Map-ID right:";
						currentMap.setTransitionUpID(enteredText);
						enteredText = currentMap.getTransitionRightID();
						break;
					case EXIT_SOUTH:
						currentState = EditorState.ENTER_TEXT;
						textUsage = TextUsage.EXIT_WEST;
						infoText = "Enter Map-ID left:";
						currentMap.setTransitionDownID(enteredText);
						enteredText = currentMap.getTransitionLeftID();
						break;
					case EXIT_WEST:
						infoText = "Exits updated!";
						currentMap.setTransitionLeftID(enteredText);
						currentState = EditorState.PLACE_BASELAYER;
						break;
					case EXIT_MAP_ID:
						currentState = EditorState.ENTER_TEXT;
						textUsage = TextUsage.EXIT_MAP_X;
						infoText = "Enter Destination X-Position:";
						exitMapID = enteredText;
						enteredText = String.valueOf(exitPosX);
						break;
					case EXIT_MAP_X:
						currentState = EditorState.ENTER_TEXT;
						textUsage = TextUsage.EXIT_MAP_Y;
						infoText = "Enter Destination Y-Position:";
						exitPosX = Integer.parseInt(enteredText);
						enteredText = String.valueOf(exitPosY);
						break;
					case EXIT_MAP_Y:
						currentState = EditorState.PLACE_EXITS;
						infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
						exitPosY = Integer.parseInt(enteredText);
						break;
					default:
						Logger.logError("Text usage not defined!");
						infoText = "Error!";
						currentState = EditorState.PLACE_BASELAYER;
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
				currentTileSelection = (currentTileSelection + maxBaseLayerSelection) % (maxBaseLayerSelection + 1);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentTileSelection = (currentTileSelection + 1) % (maxBaseLayerSelection + 1);
			}
			//Quick Selection
			for(KeyboardKey key : KeyboardKey.numberButtons) {
				if(InputManager.instance.getKeyState(key).equals(ButtonState.RELEASED)) {
					switch (key) {
					case KEY_0:
						currentTileSelection = 0;
						break;
					case KEY_1:
						currentTileSelection = 1;
						break;
					case KEY_2:
						currentTileSelection = 2;
						break;
					case KEY_3:
						currentTileSelection = 3;
						break;
					case KEY_4:
						currentTileSelection = 4;
						break;
					case KEY_5:
						currentTileSelection = 5;
						break;
					default:
						break;
					}
				}
			}
			//Placing Tiles
			if(isMouseOnMap()) {
				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
					int x = currentMousePosition[0] / 16;
					int y = currentMousePosition[1] / 16;
					int tileValue = currentMap.getBaseLayerValueForPosition(x, y);
					if(tileValue != currentTileSelection) {
						try {
							currentMap.setBaseLayer(x, y, currentTileSelection);
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
			int oldTileSetSelection = currentTileSetSelection;
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
				currentTileSetSelection = (currentTileSetSelection + tileSets.length -1) % (tileSets.length);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentTileSetSelection = (currentTileSetSelection + 1) % (tileSets.length);
			}
			if(oldTileSetSelection != currentTileSetSelection){
				currentMap.setTileSet(tileSets[currentTileSetSelection]);
			}
			break;
		case PLACE_EXITS:
			//Selection
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
				currentExitSelection = (currentExitSelection + transportationTypes.length -1) % (transportationTypes.length);
			}
			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
				currentExitSelection = (currentExitSelection + 1) % (transportationTypes.length);
			}
			//Placing Exits
			if(isMouseOnMap()) {
				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
					int x = currentMousePosition[0] / 16;
					int y = currentMousePosition[1] / 16;
					TransportMapObject transport = new TransportMapObject();
					transport.setDestinationMapID(exitMapID);
					transport.setX(exitPosX);
					transport.setY(exitPosY);
					transport.setTransportationType(transportationTypes[currentExitSelection]);
					currentMap.setMapObjectForPosition(x, y, transport);
				}
			}
			break;
		}
	}
	
	private void switchToPreviousState() {
		switch(currentState) {
		case ENTER_TEXT:
			break;
		case PLACE_BASELAYER:
			currentState = EditorState.PLACE_EXITS;
			infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
			break;
		case CHANGE_TILESET:
			currentState = EditorState.PLACE_BASELAYER;
			break;
		case PLACE_EXITS:
			currentState = EditorState.CHANGE_TILESET;
			break;
		}
	}
	
	private void switchToNextState() {
		switch(currentState) {
		case ENTER_TEXT:
			break;
		case PLACE_BASELAYER:
			currentState = EditorState.CHANGE_TILESET;
			break;
		case CHANGE_TILESET:
			currentState = EditorState.PLACE_EXITS;
			infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
			break;
		case PLACE_EXITS:
			currentState = EditorState.PLACE_BASELAYER;
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
	
	private enum EditorState{
		PLACE_BASELAYER,
		CHANGE_TILESET,
		PLACE_EXITS,
		ENTER_TEXT
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
