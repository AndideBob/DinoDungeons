package dinodungeons.game.data.gameplay;

import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
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
	
	public InputInformation() {
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		left = cumulateButtonStates(KeyboardKey.KEY_ARROW_LEFT, GamepadButton.D_LEFT);
		right = cumulateButtonStates(KeyboardKey.KEY_ARROW_RIGHT, GamepadButton.D_RIGHT);
		up = cumulateButtonStates(KeyboardKey.KEY_ARROW_UP, GamepadButton.D_UP);
		down = cumulateButtonStates(KeyboardKey.KEY_ARROW_DOWN, GamepadButton.D_DOWN);
		start = cumulateButtonStates(KeyboardKey.KEY_ESCAPE, GamepadButton.START);
		a = cumulateButtonStates(KeyboardKey.KEY_SPACE, GamepadButton.XBOX_A);
		b = cumulateButtonStates(KeyboardKey.KEY_CRTL_LEFT, GamepadButton.XBOX_B);
	}
	
	private ButtonState cumulateButtonStates(KeyboardKey key, GamepadButton button){
		ButtonState result = InputManager.instance.getKeyState(key);
		if(result == ButtonState.UP && InputManager.instance.isGamepadPluggedIn(GamepadID.GAMEPAD_1)){
			result = InputManager.instance.getGamepadButtonState(GamepadID.GAMEPAD_1, button);
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
	
}
