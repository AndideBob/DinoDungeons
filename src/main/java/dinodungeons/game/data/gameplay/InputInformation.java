package dinodungeons.game.data.gameplay;

import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.input.MouseButton;
import lwjgladapter.input.gamepad.GamepadButton;
import lwjgladapter.input.gamepad.GamepadID;

public class InputInformation {

	private ButtonState left;
	private ButtonState right;
	private ButtonState up;
	private ButtonState down;
	
	private ButtonState start;
	private ButtonState a;
	private ButtonState b;
	
	private int mouseX;
	private int mouseY;
	private ButtonState leftMouseButton;
	
	public InputInformation() {
		left = ButtonState.UP;
		right = ButtonState.UP;
		up = ButtonState.UP;
		down = ButtonState.UP;
		start = ButtonState.UP;
		a = ButtonState.UP;
		b = ButtonState.UP;
		mouseX = 0;
		mouseY = 0;
		leftMouseButton = ButtonState.UP;
	}
	
	public void update(){
		left = cumulateButtonStates(left, KeyboardKey.KEY_ARROW_LEFT, GamepadButton.D_LEFT);
		right = cumulateButtonStates(right, KeyboardKey.KEY_ARROW_RIGHT, GamepadButton.D_RIGHT);
		up = cumulateButtonStates(up, KeyboardKey.KEY_ARROW_UP, GamepadButton.D_UP);
		down = cumulateButtonStates(down, KeyboardKey.KEY_ARROW_DOWN, GamepadButton.D_DOWN);
		start = cumulateButtonStates(start, KeyboardKey.KEY_ESCAPE, GamepadButton.START);
		a = cumulateButtonStates(a, KeyboardKey.KEY_SPACE, GamepadButton.XBOX_A);
		b = cumulateButtonStates(b, KeyboardKey.KEY_CRTL_LEFT, GamepadButton.XBOX_B);
		mouseX = InputManager.instance.getRelativeMousePositionXAsInt();
		mouseY = InputManager.instance.getRelativeMousePositionYAsInt();
		leftMouseButton = InputManager.instance.getMouseState(MouseButton.LEFT);
	}
	
	private ButtonState cumulateButtonStates(ButtonState oldState, KeyboardKey key, GamepadButton button){
		ButtonState result = InputManager.instance.getKeyState(key);
		if(result == ButtonState.UP && InputManager.instance.isGamepadPluggedIn(GamepadID.GAMEPAD_1)){
			ButtonState tempResult = InputManager.instance.getGamepadButtonState(GamepadID.GAMEPAD_1, button); 
			if(tempResult != null && tempResult != ButtonState.UP) {
				result = tempResult;
			}
		}
		if(oldState != null && result != null) {
			switch(result) {
			case DOWN:
				switch(oldState) {
				case DOWN:
					return ButtonState.DOWN;
				case PRESSED:
					return ButtonState.DOWN;
				case RELEASED:
					return ButtonState.PRESSED;
				case UP:
					return ButtonState.PRESSED;
				}
				break;
			case PRESSED:
				switch(oldState) {
				case DOWN:
					return ButtonState.DOWN;
				case PRESSED:
					return ButtonState.DOWN;
				case RELEASED:
					return ButtonState.PRESSED;
				case UP:
					return ButtonState.PRESSED;
				}
				break;
			case RELEASED:
				switch(oldState) {
				case DOWN:
					return ButtonState.RELEASED;
				case PRESSED:
					return ButtonState.RELEASED;
				case RELEASED:
					return ButtonState.UP;
				case UP:
					return ButtonState.UP;
				}
				break;
			case UP:
				switch(oldState) {
				case DOWN:
					return ButtonState.RELEASED;
				case PRESSED:
					return ButtonState.RELEASED;
				case RELEASED:
					return ButtonState.UP;
				case UP:
					return ButtonState.UP;
				}
				break;
			}
		}
		return result;
	}

	public ButtonState getLeft() {
		return left;
	}

	public ButtonState getRight() {
		return right;
	}

	public ButtonState getUp() {
		return up;
	}

	public ButtonState getDown() {
		return down;
	}

	public ButtonState getStart() {
		return start;
	}

	public ButtonState getA() {
		return a;
	}

	public ButtonState getB() {
		return b;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public ButtonState getLeftMouseButton() {
		return leftMouseButton;
	}
}
