package dinodungeons.editor.ui.input;

import java.util.Arrays;
import java.util.HashSet;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.pointer.MouseHandler;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.input.KeyboardKeyUtil;
import lwjgladapter.logging.Logger;
import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.RectCollider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class TextInputLine extends UIElement {
	
	private int positionX;
	
	private int positionY;
	
	private int numberOfLetters;
	
	private String text;
	
	private boolean selected;
	
	private RectCollider selectionCollider;
	
	private boolean isSpaceAllowed;

	public TextInputLine(int positionX, int positionY, int numberOfLetters, boolean isSpaceAllowed) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.numberOfLetters = numberOfLetters;
		this.isSpaceAllowed = isSpaceAllowed;
		text = "";
		selected = false;
		selectionCollider = new RectCollider(positionX, positionY, numberOfLetters * 10, 10);
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(inputInformation.getLeftMouseButton() == ButtonState.RELEASED){
			try {
				selected = PhysicsHelper.getInstance().checkCollisionBetween(selectionCollider, MouseHandler.getInstance().getCollider()) != null;
			} catch (CollisionNotSupportedException e) {
				Logger.logError(e);
			}
		}
		if(selected){
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE).equals(ButtonState.RELEASED)) {
				if(text.length() > 0) {
					text = text.substring(0, text.length()-1);
				}
			}
			else if(text.length() < numberOfLetters){
				text += getTextInput();
			}
		}
	}

	@Override
	public void draw() {
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(selected ? 3 : 4, positionX, positionY, numberOfLetters * 10 - 2, 10);
		String shownText = text;
		if(shownText.length() < 4 && selected){
			shownText += "_";
		}
		DrawTextManager.getInstance().drawText(positionX + 1, positionY + 1, shownText, numberOfLetters);
	}
	
	public String getInput(){
		return text;
	}
	
	public void setColliderActive(boolean active) {
		selectionCollider.setActive(active);
	}
	
	private String getTextInput() {
		HashSet<KeyboardKey> keySet = new HashSet<>();
		keySet.addAll(Arrays.asList(KeyboardKey.letterButtons));
		keySet.addAll(Arrays.asList(KeyboardKey.numberButtons));
		keySet.addAll(Arrays.asList(KeyboardKey.numPadButtons));
		for(KeyboardKey key : keySet) {
			if(InputManager.instance.getKeyState(key).equals(ButtonState.RELEASED)) {
				return KeyboardKeyUtil.convertKeyboardKeyToString(key);
			}			
		}
		if(isSpaceAllowed ){
			if(InputManager.instance.getKeyState(KeyboardKey.KEY_SPACE).equals(ButtonState.RELEASED)){
				return " ";
			}
		}
		return "";
	}

	public void setInput(String text) {
		if(text.length() > numberOfLetters){
			this.text = text.substring(0,numberOfLetters);
		}
		else{
			this.text = text;
		}
	}

}
