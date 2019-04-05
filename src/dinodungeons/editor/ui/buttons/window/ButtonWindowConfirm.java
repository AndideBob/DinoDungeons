package dinodungeons.editor.ui.buttons.window;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.EditorWindow;
import dinodungeons.game.data.gameplay.InputInformation;

public class ButtonWindowConfirm extends BaseButton {
	
	private EditorWindow windowHandle;

	public ButtonWindowConfirm(int positionX, int positionY, EditorWindow windowHandle) {
		super(positionX, positionY, ButtonSprite.CONFIRM);
		this.windowHandle = windowHandle;
	}

	@Override
	protected void onClick() {
		windowHandle.closeConfirm();
	}
	
	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}

}
