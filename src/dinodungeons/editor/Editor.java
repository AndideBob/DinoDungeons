package dinodungeons.editor;

import java.util.ArrayList;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.physics.PhysicsHelper;

public class Editor extends Game {
	
	private ArrayList<UIElement> uiElements;
	
	private InputInformation currentInput;

	public Editor() {
		uiElements = new ArrayList<>();
		uiElements.addAll(EditorUtil.initializeEditorUI());
		currentInput = new InputInformation();
	}

	@Override
	public void loadResources() throws LWJGLAdapterException {
		SpriteManager.getInstance().loadSprites();
	}

	@Override
	public void update(long deltaTimeInMs) throws LWJGLAdapterException {
		PhysicsHelper.getInstance().resetCollisions();
		PhysicsHelper.getInstance().checkCollisions();
		currentInput.update();
		for(UIElement uiElement : uiElements) {
			uiElement.update(currentInput);
		}
	}

	@Override
	public void draw() throws LWJGLAdapterException {
		for(UIElement uiElement : uiElements) {
			uiElement.draw();
		}
	}

}
