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

public abstract class BaseButton extends UIElement{

	private ButtonSprite sprite;
	
	private RectCollider buttonSurface;
	
	private int positionX;
	
	private int positionY;
	
	private boolean wasClicked;
	
	private boolean pressed;
	
	public BaseButton(int positionX, int positionY, ButtonSprite sprite) {
		this.sprite = sprite;
		this.positionX = positionX;
		this.positionY = positionY;
		buttonSurface = new RectCollider(positionX, positionY, 16, 16);
		wasClicked = false;
		pressed = false;
	}
	
	@Override
	public final void update(InputInformation inputInformation) {
		pressed = false;
		if(inputInformation.getLeftMouseButton().equals(ButtonState.DOWN) ||
				inputInformation.getLeftMouseButton().equals(ButtonState.PRESSED)){
			if(isMouseOver()){
				pressed = true;
			}
		}
		else if(inputInformation.getLeftMouseButton().equals(ButtonState.RELEASED)) {
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
	
	private boolean isMouseOver(){
		try {
			return PhysicsHelper.getInstance().checkCollisionBetween(buttonSurface, MouseHandler.getInstance().getCollider()) != null;
		} catch (CollisionNotSupportedException e) {
			Logger.logError(e);
			return false;
		}
	}

	@Override
	public final void draw() {
		if(pressed){
			SpriteManager.getInstance().getSprite(SpriteID.EDITOR_BUTTONS).setColorValues(0.6f, 0.6f, 0.6f, 1f);
		}
		SpriteManager.getInstance().getSprite(SpriteID.EDITOR_BUTTONS).draw(sprite.getPositionOnSpriteSheet(), positionX, positionY);
		if(pressed){
			SpriteManager.getInstance().getSprite(SpriteID.EDITOR_BUTTONS).setColorValues(1f, 1f, 1f, 1f);
		}
	}
	
	protected abstract void onClick();

	public void setColliderActive(boolean active) {
		buttonSurface.setActive(active);
	}

}
