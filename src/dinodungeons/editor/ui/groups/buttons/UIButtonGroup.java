package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public abstract class UIButtonGroup extends UIElement implements UIGroup {

	protected ArrayList<BaseButton> buttons;
	
	private boolean visible;
	
	public UIButtonGroup(final Editor editorHandle) {
		buttons = new ArrayList<>();
		buttons.addAll(initializeButtons(editorHandle));
		unpressAll();
		setActive(false);
	}

	protected abstract Collection<? extends BaseButton> initializeButtons(final Editor editorHandle);

	@Override
	public final void update(InputInformation inputInformation) {
		if(visible){
			for(BaseButton button : buttons){
				button.update(inputInformation);
			}
		}
	}

	@Override
	public final void draw() {
		if(visible){
			for(BaseButton button : buttons){
				button.draw();
			}
		}
	}

	@Override
	public final void setActive(boolean active){
		visible = active;
		for(BaseButton button : buttons){
			button.setColliderActive(active);
		}
		unpressAll();
	}
	
	protected void onActivate() {
		//Can be overwritten
	}
	
	public final void unpressAll() {
		for(BaseButton button : buttons){
			button.setPressed(false);
		}
	}

}
