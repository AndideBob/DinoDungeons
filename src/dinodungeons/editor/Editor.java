package dinodungeons.editor;

import java.util.Collection;

import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.map.MapChangeManager;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;

public class Editor extends Game {
	
	private EditorState currentState;
	
	private EditorUIHandler uiHandler;
	private EditorMapManager mapManager;
	private MapChangeManager mapChangeManager;
	
	private InputInformation currentInput;

	public Editor() {
		currentInput = new InputInformation();
		currentState = EditorState.DEFAULT;
		mapChangeManager = new MapChangeManager();
		mapManager = new EditorMapManager();
		uiHandler = new EditorUIHandler(this, mapManager);
	}

	@Override
	public void loadResources() throws LWJGLAdapterException {
		TilesetManager.getInstance().loadResources();
		SpriteManager.getInstance().loadSprites();
		DrawTextManager.getInstance();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		updateDebug();
		PhysicsHelper.getInstance().resetCollisions();
		PhysicsHelper.getInstance().checkCollisions();
		currentInput.update();
		uiHandler.update(currentState, currentInput);
		mapChangeManager.update(currentState, currentInput);
		mapManager.applyMapChanges(mapChangeManager.getNewMapChanges());
		mapManager.update();
	}

	@Override
	public void draw() throws LWJGLAdapterException {
		mapManager.draw(true);
		uiHandler.draw();
	}
	
	public void waitForInput(String prompt, InputUsage usage){
		currentState = EditorState.WAIT_FOR_INPUT;
		String prefilledInput = "";
		switch(usage){
		case DUNGEON_ID:
			prefilledInput = "" + mapManager.getCurrentDungeonID();
			break;
		case SAVING:
			prefilledInput = mapManager.getCurrentMapID();
			break;
		case TRANSITION_DOWN:
			prefilledInput = mapManager.getCurrentTransitionInDirection(DinoDungeonsConstants.directionDown);
			break;
		case TRANSITION_LEFT:
			prefilledInput = mapManager.getCurrentTransitionInDirection(DinoDungeonsConstants.directionLeft);
			break;
		case TRANSITION_RIGHT:
			prefilledInput = mapManager.getCurrentTransitionInDirection(DinoDungeonsConstants.directionRight);
			break;
		case TRANSITION_UP:
			prefilledInput = mapManager.getCurrentTransitionInDirection(DinoDungeonsConstants.directionUp);
			break;
		default:
			break;
		}
		uiHandler.openInputWindow(prompt, usage, prefilledInput);
	}
	
	public void waitForPageInput(SignType signType, String prompt, TextBoxContent prefilledInput){
		currentState = EditorState.WAIT_FOR_INPUT;
		uiHandler.openPageInputWindow(signType, prompt, prefilledInput);
	}
	
	public void waitForPageInput(NPCType npcType, String prompt, TextBoxContent prefilledInput){
		currentState = EditorState.WAIT_FOR_INPUT;
		uiHandler.openPageInputWindow(npcType, prompt, prefilledInput);
	}
	
	public void reactToInput(String input, InputUsage usage){
		currentState = EditorState.DEFAULT;
		switch(usage){
		case LOAD:
			mapManager.loadMap(input);
			break;
		case SAVING:
			mapManager.saveMap(input);
			break;
		case TRANSITION_DOWN:
			mapManager.setTransition(input, DinoDungeonsConstants.directionDown);
			break;
		case TRANSITION_LEFT:
			mapManager.setTransition(input, DinoDungeonsConstants.directionLeft);
			break;
		case TRANSITION_RIGHT:
			mapManager.setTransition(input, DinoDungeonsConstants.directionRight);
			break;
		case TRANSITION_UP:
			mapManager.setTransition(input, DinoDungeonsConstants.directionUp);
			break;
		case DUNGEON_ID:
			try{
				int dungeonID = Integer.parseInt(input);
				mapManager.setDungeonID(dungeonID);
			}catch (NumberFormatException e) {
				Logger.logError(e);
			}
			
			break;
		case NOTHING:
			break;
		}
	}
	
	public void reactToInput(SignType signType, Collection<TextBoxContent> input){
		currentState = EditorState.DEFAULT;
		String contents = TextBoxContent.parseMultipleToString(input);
		mapChangeManager.setMapChange(MapChangeType.SIGN_CHANGE, signType.getStringRepresentation(), contents);
	}
	
	public void reactToInput(NPCType npcType, Collection<TextBoxContent> input){
		currentState = EditorState.DEFAULT;
		String contents = TextBoxContent.parseMultipleToString(input);
		mapChangeManager.setMapChange(MapChangeType.NPC_PLACEMENT, npcType.getStringRepresentation(), contents);
	}
	
	public void setMapChange(MapChangeType mapChangeType, String... params) {
		mapChangeManager.setMapChange(mapChangeType, params);
	}

	public void openNewMap() {
		mapManager.newMap();
	}
	
	private void updateDebug(){
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_F5).equals(ButtonState.RELEASED)){
			waitForPageInput(SignType.SIGN, "Debug Window", new TextBoxContent());
		}
	}

}
