package dinodungeons.editor.ui.input;

import java.util.Arrays;
import java.util.HashSet;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.input.KeyboardKeyUtil;

public class TextInputLine extends UIElement {
	
	private int positionX;
	
	private int positionY;
	
	private int numberOfLetters;
	
	private String text;

	public TextInputLine(int positionX, int positionY, int numberOfLetters) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.numberOfLetters = numberOfLetters;
		text = "";
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE).equals(ButtonState.RELEASED)) {
			if(text.length() > 0) {
				text = text.substring(0, text.length()-1);
			}
		}
		else if(text.length() < numberOfLetters){
			text += getTextInput();
		}
	}

	@Override
	public void draw() {
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(3, positionX, positionY, numberOfLetters * 10, 10);
		DrawTextManager.getInstance().drawText(positionX, positionY + 1, text, numberOfLetters);
	}
	
	public String getInput(){
		return text;
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
		return "";
	}

}
