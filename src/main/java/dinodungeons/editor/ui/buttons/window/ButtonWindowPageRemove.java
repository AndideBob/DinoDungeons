package dinodungeons.editor.ui.buttons.window;

import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.PageTextInputWindow;
import dinodungeons.game.data.gameplay.InputInformation;

public class ButtonWindowPageRemove extends BaseButton {
	
	private PageTextInputWindow windowHandle;

	public ButtonWindowPageRemove(int positionX, int positionY, PageTextInputWindow windowHandle) {
		super(positionX, positionY, ButtonSprite.PAGE_REMOVE);
		this.windowHandle = windowHandle;
	}

	@Override
	protected void onClick() {
		windowHandle.deleteCurrentPage();
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
