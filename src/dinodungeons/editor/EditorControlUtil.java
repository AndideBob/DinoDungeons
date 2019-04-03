package dinodungeons.editor;

import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;

public class EditorControlUtil {

	public static boolean getEscapePressed(){
		return InputManager.instance.getKeyState(KeyboardKey.KEY_ESCAPE) == ButtonState.RELEASED;
	}
	
	public static boolean getEnterPressed(){
		return InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER) == ButtonState.RELEASED;
	}

	public static boolean getUndo() {
		if(isControl()){
			return InputManager.instance.getKeyState(KeyboardKey.KEY_Z) == ButtonState.RELEASED;
		}
		return false;
	}
	
	public static boolean getRedo() {
		if(isControl()){
			return InputManager.instance.getKeyState(KeyboardKey.KEY_Y) == ButtonState.RELEASED;
		}
		return false;
	}
	
	private static boolean isControl(){
		return InputManager.instance.getKeyState(KeyboardKey.KEY_CRTL_LEFT) != ButtonState.UP
				|| InputManager.instance.getKeyState(KeyboardKey.KEY_CRTL_RIGHT) != ButtonState.UP;
	}

}
