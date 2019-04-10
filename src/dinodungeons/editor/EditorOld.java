//package dinodungeons.editor;
//
//import java.util.Arrays;
//import java.util.HashSet;
//
//import dinodungeons.game.data.DinoDungeonsConstants;
//import dinodungeons.game.data.exceptions.ScreenMapIndexOutOfBounds;
//import dinodungeons.game.data.map.ScreenMap;
//import dinodungeons.game.data.map.ScreenMapConstants;
//import dinodungeons.game.data.map.ScreenMapLoader;
//import dinodungeons.game.data.map.ScreenMapSaver;
//import dinodungeons.game.data.map.objects.BlockMapObject;
//import dinodungeons.game.data.map.objects.BlockMapObject.BlockType;
//import dinodungeons.game.data.map.objects.DestructibleMapObject;
//import dinodungeons.game.data.map.objects.DoorMapObject;
//import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;
//import dinodungeons.game.data.map.objects.EmptyMapObject;
//import dinodungeons.game.data.map.objects.EnemyMapObject;
//import dinodungeons.game.data.map.objects.ItemMapObject;
//import dinodungeons.game.data.map.objects.SpikeMapObject;
//import dinodungeons.game.data.map.objects.TransportMapObject;
//import dinodungeons.game.data.map.objects.EnemyMapObject.EnemyType;
//import dinodungeons.game.data.map.objects.TransportMapObject.TransportationType;
//import dinodungeons.game.gameobjects.player.ItemID;
//import dinodungeons.gfx.sprites.SpriteManager;
//import dinodungeons.gfx.tilesets.TileSet;
//import lwjgladapter.game.Game;
//import lwjgladapter.input.ButtonState;
//import lwjgladapter.input.InputManager;
//import lwjgladapter.input.KeyboardKey;
//import lwjgladapter.input.KeyboardKeyUtil;
//import lwjgladapter.input.MouseButton;
//import lwjgladapter.logging.Logger;
//
//public class EditorOld extends Game {
//	
//	ScreenMap currentMap;
//	
//	ScreenMapLoader loader;
//	ScreenMapSaver saver;
//	
//	private int[] currentMousePosition = new int[2];
//	private EditorState currentState;
//	private TextUsage textUsage;
//	
//	private String enteredText = "";
//	private String infoText = "";
//	private int selectedTileX = -1;
//	private int selectedTileY = -1;
//	
//	//Base Layer Placement
//	private int currentSelection = 0;
//	
//	//Editor States
//	private static final EditorState[] editorStates = EditorState.values();
//	//Tile Sets
//	public static final TileSet[] tileSets = TileSet.values();
//	//Entrance Types
//	public static final TransportationType[] transportationTypes = TransportationType.values();
//	//EnemyTypes Sets
//	public static final EnemyType[] enemyTypes = EnemyType.values();
//	//BlockTypes
//	public static final BlockType[] blockTypes = BlockType.values();
//	//BlockTypes
//	public static final DoorType[] doorTypes = DoorType.values();
//	private String exitMapID = "0000";
//	private int exitPosX = 0;
//	private int exitPosY = 0;
//	
//	EditorDrawManager drawManager;
//	
//	public EditorOld() {
//		loader = new ScreenMapLoader();
//		saver = new ScreenMapSaver();
//		currentMap = ScreenMap.defaultMap;
//		switchToState(EditorState.INSPECTOR);
//		drawManager = new EditorDrawManager();
//	}
//
//	@Override
//	public void draw() {
//		drawManager.setCurrentMap(currentMap);
//		//DrawCurrentMap
//		drawManager.drawMap();
//		//DrawExitLayer
//		if(currentState != EditorState.PLACE_BASELAYER){
//			drawManager.drawObjectsLayer();
//		}
//		//DrawPointer
//		if(currentState == EditorState.INSPECTOR){
//			if(selectedTileX >= 0 && selectedTileY >= 0){
//				drawManager.drawSelector(selectedTileX, selectedTileY);
//			}
//		}
//		else if(isMouseOnMap()) {
//			int x = currentMousePosition[0] / 16;
//			int y = currentMousePosition[1] / 16;
//			drawManager.drawSelector(x, y);
//		}
//		//DrawUI
//		//drawManager.drawUI(currentState, currentSelection);
//		//InfoText
//		drawManager.drawInfoText(infoText);
//		//EnterText
//		if(currentState == EditorState.ENTER_TEXT) {
//			drawManager.drawEnteredText(enteredText);
//		}
//	}
//
//	@Override
//	public void loadResources() {
//		SpriteManager.getInstance().loadSprites();
//		drawManager.loadTextures();
//	}
//
//	@Override
//	public void update(long deltaTimeInMs) {
//		currentMousePosition = getMousePosition();
//		if(checkIfModeWasSwitched()){
//			return;
//		}
//		//General Commands
//		switch(currentState) {
//		case INSPECTOR:
//			//Placing Tiles
//			if(isMouseOnMap()) {
//				int x = currentMousePosition[0] / 16;
//				int y = currentMousePosition[1] / 16;
//				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.RELEASED)) {
//					selectedTileX = x;
//					selectedTileY = y;
//					infoText = currentMap.getMapObjectForPosition(x, y).getEditorInfo();
//					if(infoText.equals("")){
//						infoText = "[" + x + "," + y + "]";
//					}
//				}
//				if(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE).equals(ButtonState.RELEASED)
//						|| InputManager.instance.getKeyState(KeyboardKey.KEY_DELETE).equals(ButtonState.RELEASED)){
//					if(selectedTileX >= 0 && selectedTileY >= 0){
//						currentMap.setMapObjectForPosition(selectedTileX, selectedTileY, new EmptyMapObject());
//					}
//				}
//			}
//			break;
//		case ENTER_TEXT:
//			if(InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER).equals(ButtonState.RELEASED)) {
//				if(enteredText.length() > 0) {
//					switch (textUsage) {
//					case LOAD:
//						currentMap = loader.loadMap(enteredText);
//						infoText = "Load successfull!";
//						switchToState(EditorState.INSPECTOR);
//						break;
//					case SAVING:
//						if(saver.saveMap(enteredText, currentMap)) {
//							infoText = "Save successfull!";
//						}
//						else {
//							infoText = "An error occured during saving!";
//						}
//						switchToState(EditorState.INSPECTOR);
//						break;
//					case EXIT_EAST:
//						switchToEnterTextMode(TextUsage.EXIT_SOUTH);
//						break;
//					case EXIT_NORTH:
//						switchToEnterTextMode(TextUsage.EXIT_EAST);
//						break;
//					case EXIT_SOUTH:
//						switchToEnterTextMode(TextUsage.EXIT_WEST);
//						break;
//					case EXIT_WEST:
//						infoText = "Exits updated!";
//						currentMap.setTransitionLeftID(enteredText);
//						switchToState(EditorState.PLACE_BASELAYER);
//						break;
//					case EXIT_MAP_ID:
//						switchToEnterTextMode(TextUsage.EXIT_MAP_X);
//						break;
//					case EXIT_MAP_X:
//						switchToEnterTextMode(TextUsage.EXIT_MAP_Y);
//						break;
//					case EXIT_MAP_Y:
//						try{
//							exitPosY = Integer.parseInt(enteredText);
//						}
//						catch(NumberFormatException e){
//							exitPosY = 20;
//						}
//						if(exitPosY < 0 || exitPosY > 11){
//							infoText = "Valid Y-Position [0-11]";
//						}
//						else{
//							switchToState(EditorState.PLACE_EXITS);
//						}
//						break;
//					case DUNGEON_ID:
//						int dungeonID;
//						try{
//							dungeonID = Integer.parseInt(enteredText);
//							if(dungeonID < 0 || dungeonID > 12){
//								throw new NumberFormatException();
//							}else{
//								switchToState(EditorState.INSPECTOR);
//								currentMap.setDungeonID(dungeonID);
//							}
//						}
//						catch(NumberFormatException e){
//							infoText = "Only [0-12] are valid!";
//						}
//						break;
//					default:
//						Logger.logError("Text usage not defined!");
//						infoText = "Error!";
//						switchToState(EditorState.PLACE_BASELAYER);
//						break;
//					}
//				}
//			}
//			else if(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE).equals(ButtonState.RELEASED)) {
//				if(enteredText.length() > 0) {
//					enteredText = enteredText.substring(0, enteredText.length()-1);
//				}
//			}
//			else if(enteredText.length() < 4){
//				enteredText += getTextInput();
//			}
//			break;
//		case PLACE_SPIKES:
//			//Selection
//			switchSelection(DinoDungeonsConstants.numberOfSpikes);
//			//Placing Spike
//			if(isMouseOnMap()) {
//				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
//					int x = currentMousePosition[0] / 16;
//					int y = currentMousePosition[1] / 16;
//					SpikeMapObject spike = new SpikeMapObject();
//					spike.setSpikeType(currentSelection);
//					currentMap.setMapObjectForPosition(x, y, spike);
//				}
//			}
//			break;
//		case PLACE_DESTRUCTABLES:
//			//Selection
//			switchSelection(DinoDungeonsConstants.numberOfDestructables);
//			//Placing Destructables
//			if(isMouseOnMap()) {
//				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
//					int x = currentMousePosition[0] / 16;
//					int y = currentMousePosition[1] / 16;
//					DestructibleMapObject destructable = new DestructibleMapObject();
//					destructable.setDestructableType(currentSelection);
//					currentMap.setMapObjectForPosition(x, y, destructable);
//				}
//			}
//			break;
//		case PLACE_ENEMIES:
//			//Selection
//			switchSelection(enemyTypes.length);
//			//Placing Destructables
//			if(isMouseOnMap()) {
//				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.PRESSED)) {
//					int x = currentMousePosition[0] / 16;
//					int y = currentMousePosition[1] / 16;
//					EnemyMapObject enemy = new EnemyMapObject();
//					enemy.setEnemyType(enemyTypes[currentSelection]);
//					currentMap.setMapObjectForPosition(x, y, enemy);
//				}
//			}
//			break;
//		case CHANGE_TILESET:
//			//Selection
//			int oldTileSetSelection = currentSelection;
//			switchSelection(tileSets.length);
//			if(oldTileSetSelection != currentSelection){
//				currentMap.setTileSet(tileSets[currentSelection]);
//			}
//			break;
//		case PLACE_EXITS:
//			//Selection
//			switchSelection(transportationTypes.length);
//			//Placing Exits
//			if(isMouseOnMap()) {
//				if(InputManager.instance.getMouseState(MouseButton.LEFT).equals(ButtonState.RELEASED)) {
//					int x = currentMousePosition[0] / 16;
//					int y = currentMousePosition[1] / 16;
//					if(transportationTypes[currentSelection] == TransportationType.BLOCKED_CAVE_ENTRY){
//						switch(currentMap.getBaseLayerTileForPosition(x, y)){
//						case DOOR_DOWN:
//						case DOOR_LEFT:
//						case DOOR_RIGHT:
//						case DOOR_UP:
//							//Placement is allowed
//							break;
//						default:
//							infoText = "Needs to be placed on a door!";
//							return;
//						}
//					}
//					TransportMapObject transport = new TransportMapObject();
//					transport.setDestinationMapID(exitMapID);
//					transport.setX(exitPosX);
//					transport.setY(exitPosY);
//					transport.setTransportationType(transportationTypes[currentSelection]);
//					currentMap.setMapObjectForPosition(x, y, transport);
//				}
//			}
//			break;
//		case PLACE_DUNGEON_ITEMS:
//			break;
//		default:
//			break;
//		}
//	}
//	
//	private boolean isMouseOnMap() {
//		if(currentState == EditorState.ENTER_TEXT) {
//			return false;
//		}
//		return currentMousePosition[1] < 192;
//	}
//	
//	private int[] getMousePosition() {
//		int[] result = new int[2];
//		result[0] = InputManager.instance.getRelativeMousePositionXAsInt();
//		result[1] = InputManager.instance.getRelativeMousePositionYAsInt();
//		return result;
//	}
//	
//	private void switchSelection(int selectionMod){
//		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_UP).equals(ButtonState.RELEASED)) {
//			currentSelection = (currentSelection + selectionMod -1) % (selectionMod);
//		}
//		else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_DOWN).equals(ButtonState.RELEASED)) {
//			currentSelection = (currentSelection + 1) % (selectionMod);
//		}
//	}
//	
//	private boolean checkIfModeWasSwitched(){
//		//Escape to Inspector
//		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ESCAPE).equals(ButtonState.RELEASED)) {
//			switchToState(EditorState.INSPECTOR);
//			return true;
//		}
//		if(currentState != EditorState.ENTER_TEXT) {
//			//Enter Save Mode
//			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F1).equals(ButtonState.RELEASED)) {
//				switchToEnterTextMode(TextUsage.SAVING);
//				return true;
//			}
//			//Enter Load Mode
//			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F2).equals(ButtonState.RELEASED)) {
//				switchToEnterTextMode(TextUsage.LOAD);
//				return true;
//			}
//			//Enter Entrance Mode
//			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F3).equals(ButtonState.RELEASED)) {
//				switchToEnterTextMode(TextUsage.EXIT_NORTH);
//				return true;
//			}
//			//Enter DungeonID Mode
//			if(InputManager.instance.getKeyState(KeyboardKey.KEY_F4).equals(ButtonState.RELEASED)) {
//				switchToEnterTextMode(TextUsage.DUNGEON_ID);
//				return true;
//			}
//			//Enter Exit Placement Edit Mode
//			if(currentState == EditorState.PLACE_EXITS && InputManager.instance.getKeyState(KeyboardKey.KEY_F5).equals(ButtonState.RELEASED)) {
//				switchToEnterTextMode(TextUsage.EXIT_MAP_ID);
//				return true;
//			}
//			//Switch Mode
//			return switchState();
//		}
//		return false;
//	}
//	
//	private void switchToEnterTextMode(TextUsage usage){
//		switchToState(EditorState.ENTER_TEXT);
//		textUsage = usage;
//		switch(textUsage){
//		case EXIT_EAST:
//			infoText = "Enter Map-ID right:";
//			currentMap.setTransitionUpID(enteredText);
//			enteredText = currentMap.getTransitionRightID();
//			break;
//		case EXIT_MAP_ID:
//			infoText = "Enter Destination Map-ID:";
//			enteredText = exitMapID;
//			break;
//		case EXIT_MAP_X:
//			infoText = "Enter Destination X-Position:";
//			exitMapID = enteredText;
//			enteredText = String.valueOf(exitPosX);
//			break;
//		case EXIT_MAP_Y:
//			try{
//				exitPosX = Integer.parseInt(enteredText);
//			}
//			catch(NumberFormatException e){
//				exitPosX = 20;
//			}
//			if(exitPosX < 0 || exitPosX > 15){
//				textUsage = TextUsage.EXIT_MAP_X;
//				infoText = "Valid X-Position [0-15]";
//			}
//			else{
//				infoText = "Enter Destination Y-Position:";
//				enteredText = String.valueOf(exitPosY);
//			}
//			break;
//		case EXIT_NORTH:
//			infoText = "Enter Map-ID upwards:";
//			enteredText = currentMap.getTransitionUpID();
//			break;
//		case EXIT_SOUTH:
//			infoText = "Enter Map-ID downwards:";
//			currentMap.setTransitionRightID(enteredText);
//			enteredText = currentMap.getTransitionDownID();
//			break;
//		case EXIT_WEST:
//			infoText = "Enter Map-ID left:";
//			currentMap.setTransitionDownID(enteredText);
//			enteredText = currentMap.getTransitionLeftID();
//			break;
//		case LOAD:
//			infoText = "Enter File-id to load:";
//			enteredText = "";
//			break;
//		case SAVING:
//			infoText = "Enter File-id to save:";
//			enteredText = currentMap.getID();
//			break;
//		case DUNGEON_ID:
//			infoText = "Enter belonging dungeon id:";
//			enteredText = "" + currentMap.getDungeonID();
//			break;
//		default:
//			break;
//		}
//	}
//	
//	private boolean switchState(){
//		int curState = 0;
//		for(int i = 0; i < editorStates.length; i++){
//			if(editorStates[i].equals(currentState)){
//				curState = i;
//				break;
//			}
//		}
//		if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_LEFT).equals(ButtonState.RELEASED)) {
//			curState = (curState + editorStates.length -1) % (editorStates.length);
//			if(editorStates[curState].equals(EditorState.ENTER_TEXT)){
//				curState = (curState + editorStates.length -1) % (editorStates.length);
//			}
//			switchToState(editorStates[curState]);
//			return true;
//		}
//		else if(InputManager.instance.getKeyState(KeyboardKey.KEY_ARROW_RIGHT).equals(ButtonState.RELEASED)) {
//			curState = (curState + 1) % (editorStates.length);
//			if(editorStates[curState].equals(EditorState.ENTER_TEXT)){
//				curState = (curState + 1) % (editorStates.length);
//			}
//			switchToState(editorStates[curState]);
//			return true;
//		}
//		return false;
//	}
//	
//	private void switchToState(EditorState state){
//		currentSelection = 0;
//		switch(state){
//		case CHANGE_TILESET:
//			for(int i = 0; i < tileSets.length; i++){
//				if(tileSets[i].equals(currentMap.getTileSet())){
//					currentSelection = i;
//				}
//			}
//			break;
//		case ENTER_TEXT:
//			break;
//		case INSPECTOR:
//			infoText = "Select a tile on the map!";
//			selectedTileX = -1;
//			selectedTileY = -1;
//			break;
//		case PLACE_BASELAYER:
//			break;
//		case PLACE_EXITS:
//			infoText = "[F5]Map:" + exitMapID + " X:" + exitPosX + " Y:" + exitPosY;
//			break;
//		case PLACE_ITEMS:
//			infoText = "Click to place item!";
//			break;
//		default:
//			break;
//		}
//		currentState = state;
//	}
//	
//	private String getTextInput() {
//		HashSet<KeyboardKey> keySet = new HashSet<>();
//		keySet.addAll(Arrays.asList(KeyboardKey.letterButtons));
//		keySet.addAll(Arrays.asList(KeyboardKey.numberButtons));
//		keySet.addAll(Arrays.asList(KeyboardKey.numPadButtons));
//		for(KeyboardKey key : keySet) {
//			if(InputManager.instance.getKeyState(key).equals(ButtonState.RELEASED)) {
//				return KeyboardKeyUtil.convertKeyboardKeyToString(key);
//			}			
//		}
//		return "";
//	}
//	
//	private enum TextUsage{
//		SAVING,
//		LOAD,
//		DUNGEON_ID,
//		EXIT_NORTH,
//		EXIT_SOUTH,
//		EXIT_EAST,
//		EXIT_WEST,
//		EXIT_MAP_ID,
//		EXIT_MAP_X,
//		EXIT_MAP_Y
//	}
//
//}
