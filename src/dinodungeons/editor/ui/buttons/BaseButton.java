package dinodungeons.editor.ui.buttons;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.pointer.MouseHandler;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public abstract class BaseButton implements UIElement{

	private ButtonSprite sprite;
	
	private RectCollider buttonSurface;
	
	private int positionX;
	
	private int positionY;
	
	private boolean wasClicked;
	
	public BaseButton(int positionX, int positionY, ButtonSprite sprite) {
		this.sprite = sprite;
		this.positionX = positionX;
		this.positionY = positionY;
		buttonSurface = new RectCollider(positionX, positionY, 16, 16);
		wasClicked = false;
	}
	
	@Override
	public final void update(InputInformation inputInformation) {
		if(inputInformation.getLeftMouseButton().equals(ButtonState.RELEASED)) {
			try {
				if(!wasClicked && PhysicsHelper.getInstance().checkCollisionBetween(buttonSurface, MouseHandler.getInstance().getCollider()) != null) {
					onClick();
					wasClicked = true;
				}
			} catch (CollisionNotSupportedException e) {
				Logger.logError(e);
			}
		}
		else {
			wasClicked = false;
		}
	}

	@Override
	public final void draw() {
		SpriteManager.getInstance().getSprite(SpriteID.EDITOR_BUTTONS).draw(sprite.getPositionOnSpriteSheet(), positionX, positionY);
	}
	
	protected abstract void onClick();

}
