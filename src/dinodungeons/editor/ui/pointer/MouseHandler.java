package dinodungeons.editor.ui.pointer;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.game.data.gameplay.InputInformation;
import lwjgladapter.physics.collision.RectCollider;

public class MouseHandler implements UIElement {

	private RectCollider clickCollider;
	
	private static MouseHandler instance;
	
	public static MouseHandler getInstance() {
		if(instance == null) {
			instance = new MouseHandler();
		}
		return instance;
	}
	
	private MouseHandler() {
		clickCollider = new RectCollider(0, 0, 3, 3);
	}

	@Override
	public void update(InputInformation inputInformation) {
		clickCollider.setPositionX(inputInformation.getMouseX() - 1);
		clickCollider.setPositionY(inputInformation.getMouseY() - 1);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}
	
	public RectCollider getCollider() {
		return clickCollider;
	}

}
