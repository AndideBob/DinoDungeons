package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;

public abstract class SelectionButton extends BaseButton {

	private boolean selected;
	
	private EditorUIHandler uiHandler;
	
	public SelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, ButtonSprite sprite) {
		super(positionX, positionY, sprite);
		selected = false;
		this.uiHandler = uiHandler;
	}

	@Override
	protected void onClick() {
		selected = !selected;
		if(selected){
			uiHandler.switchOffAllSelections();
		}
		select(selected);
	}
	
	public abstract void select(boolean select);

}
