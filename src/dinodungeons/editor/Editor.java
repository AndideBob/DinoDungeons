package dinodungeons.editor;

import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.map.MapChangeManager;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import dinodungeons.gfx.tilesets.TilesetManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
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
		uiHandler = new EditorUIHandler(this);
		mapManager = new EditorMapManager();
		mapChangeManager = new MapChangeManager();
	}

	@Override
	public void loadResources() throws LWJGLAdapterException {
		TilesetManager.getInstance().loadResources();
		SpriteManager.getInstance().loadSprites();
		DrawTextManager.getInstance();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		PhysicsHelper.getInstance().resetCollisions();
		PhysicsHelper.getInstance().checkCollisions();
		currentInput.update();
		uiHandler.update(currentState, currentInput);
		mapChangeManager.update(currentInput);
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
		uiHandler.openInputWindow(prompt, usage);
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
		case NOTHING:
			break;
		}
	}

	public void openNewMap() {
		mapManager.newMap();
	}

}
